package com.example.proyecto2trim;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable
{
    private final ArrayList<Socket> clients; // Lista para almacenar los sockets de los clients conectados
    private final int port; // Puerto en el que el servidor escuchará las conexiones

    /**
     * Constructor para inicializar el servidor.
     *
     * @param port El port en el que el servidor escuchará las conexiones.
     */
    public Server(int port)
    {
        this.port = port; // Asignar el port
        this.clients = new ArrayList<>(); // Inicializar la lista de clients
    }

    @Override
    public void run()
    {
        try (// Crear un ServerSocket para escuchar conexiones en el port especificado
                ServerSocket servidor = new ServerSocket(port))
        {
            System.out.println("Servidor iniciado en el port " + port);

            // Bucle infinito para aceptar conexiones de clients
            while (true)
            {
                // Esperar a que un cliente se conecte
                Socket cliente = servidor.accept();

                // Agregar el socket del cliente a la lista (de manera sincronizada)
                synchronized (clients)
                {
                    clients.add(cliente);
                }

                // Mostrar la dirección IP del cliente que se ha conectado
                System.out.println("Nuevo cliente conectado: " + cliente.getInetAddress());

                // Iniciar un hilo para manejar la comunicación con el cliente
                new Thread(() -> manejarCliente(cliente)).start();
            }
        } catch (IOException ex) {
            // Manejar errores de entrada/salida
            ex.printStackTrace(); // Imprimir la traza de la excepción
        }
    }

    /**
     * Método para manejar la comunicación con un cliente específico.
     *
     * @param cliente El socket del cliente que se está manejando.
     */
    private void manejarCliente(Socket cliente)
    {
        try (// Crear un flujo de salida para enviar objetos al cliente
                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                // Crear un flujo de entrada para recibir objetos del cliente
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream()))
        {
            // Bucle infinito para recibir y enviar actualizaciones
            while (true)
            {
                // Recibir la posición actualizada del jugador
                Player jugador = (Player) ois.readObject();

                // Enviar la actualización a todos los clients (excepto al que envió la actualización)
                synchronized (clients) {
                    for (Socket c : clients)
                    {
                        if (!c.equals(cliente))
                        { // No enviar la actualización al mismo cliente
                            // Crear un flujo de salida para el cliente actual
                            ObjectOutputStream out = new ObjectOutputStream(c.getOutputStream());
                            out.writeObject(jugador); // Enviar el objeto Player
                            out.flush(); // Asegurar que los datos se envíen
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            // Manejar errores de entrada/salida o de lectura de objetos
            ex.printStackTrace(); // Imprimir la traza de la excepción
        }
    }
}