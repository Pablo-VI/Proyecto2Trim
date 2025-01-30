package com.example.proyecto2trim;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    // Lista para almacenar los sockets de los clientes conectados
    private final ArrayList<Socket> clientes;

    // Puerto en el que el servidor escuchará las conexiones
    private int puerto;

    // Constructor del servidor
    public Server(int puerto) {
        this.puerto = puerto; // Asigna el puerto
        this.clientes = new ArrayList(); // Inicializa la lista de clientes
    }

    // Método run() que se ejecuta cuando el servidor inicia
    @Override
    public void run() {

        ServerSocket servidor = null; // Socket del servidor
        Socket sc = null; // Socket para manejar la conexión con un cliente
        DataInputStream in; // Flujo de entrada para recibir datos del cliente

        try {
            // Creamos el socket del servidor en el puerto especificado
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            // Bucle infinito para escuchar constantemente nuevas conexiones
            while (true) {

                // Espera a que un cliente se conecte
                sc = servidor.accept();

                // Muestra la dirección IP del cliente que se ha conectado
                System.out.println("Cliente conectado desde " + sc.getInetAddress());

                // Bloque sincronizado para evitar problemas de concurrencia al agregar clientes
                synchronized (clientes) {
                    clientes.add(sc); // Agrega el socket del cliente a la lista
                }
            }

        } catch (IOException ex) {
            // Manejo de excepciones: registra errores en el log
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Método para enviar información a todos los clientes conectados
    public void enviarInfo(Gasolinera g) {

        // Bloque sincronizado para evitar problemas de concurrencia al acceder a la lista de clientes
        synchronized (clientes) {
            // Recorre todos los sockets de los clientes conectados
            for (Socket sock : clientes) {

                try {
                    // Crea un flujo de salida para enviar objetos al cliente
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(g); // Envía el objeto Gasolinera al cliente
                    oos.flush(); // Limpia el flujo para asegurar que los datos se envíen
                } catch (IOException ex) {
                    // Manejo de excepciones: registra errores en el log
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}