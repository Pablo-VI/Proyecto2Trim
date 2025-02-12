package com.example.proyecto2trim;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    //Numero maximo de jugadores que pueden conectarse
    private final int NUM_MAX_PLAYER = 6;

    private ArrayList<Socket> clients; // Lista para almacenar los sockets de los clients conectados

    //Datos de la partida
    private int contadorPosicionFinal;   // Se usará para asignar posiciones finales (1º, 2º, …)
    private int[] posicionesFinales;       // Posiciones finales de cada jugador (índice = id del jugador)
    private int[] avances;                 // Avances actuales de cada jugador (de 0 a 35)
    private int numJugadoresAcabados;        // Número de jugadores que han finalizado
    private boolean finPartida;            // Indica si la partida ha terminado
    private String nombresJugadores;         // Cadena con los nombres de los jugadores separados por comas

    // Variable para controlar el turno. Indica el id del jugador que tiene el turno.
    private int turnoActual;

    /**
     * Constructor para inicializar el servidor.
     *
     */
    public Server()
    {
        contadorPosicionFinal = 1;
        numJugadoresAcabados = 0;
        finPartida = false;
        nombresJugadores = "";
        turnoActual = 0; // El primer turno se asigna al jugador 0.
    }

    /**
     * Método principal que ejecuta el servidor.
     */
    public void ejecutarServidor()
    {
        posicionesFinales = new int[NUM_MAX_PLAYER];
        avances = new int[NUM_MAX_PLAYER];

        clients = new ArrayList<>();

        System.out.println("Servidor iniciado en puerto 5555... Esperando " + NUM_MAX_PLAYER + " clientes.");

        try (ServerSocket serverSocket = new ServerSocket(5555)) {

            // Esperamos a que se conecten los 6 clientes
            while (clients.size() < NUM_MAX_PLAYER) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + socketCliente.getInetAddress());

                // Se crea un canal de comunicación para leer el nombre del jugador
                DataInputStream in = new DataInputStream(socketCliente.getInputStream());
                DataOutputStream out = new DataOutputStream(socketCliente.getOutputStream());
                String nombreJugador = in.readUTF();

                // Se acumulan los nombres separados por comas
                if (!nombresJugadores.isEmpty()) {
                    nombresJugadores += ",";
                }
                nombresJugadores += nombreJugador;

                // Se envía un acuse de recibo al cliente
                out.writeUTF("aceptado");
                out.flush();

                // Se guarda el socket del cliente
                clients.add(socketCliente);
            }

            // Se lanzan los hilos de gestión para cada cliente
            for (int i = 0; i < NUM_MAX_PLAYER; i++) {
                Socket s = clients.get(i);
                GestionClientes hilo = new GestionClientes(this, s, i);
                hilo.start();
            }

            // Espera a que la partida finalice (cuando todos los jugadores hayan terminado)
            while (!finPartida) {
                synchronized (this) {
                    wait();
                }
            }

            // Una vez finalizada la carrera, se notifica a todos para que puedan finalizar
            synchronized (this) {
                notifyAll();
            }

            System.out.println("Partida finalizada. Cerrando servidor.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el avance de un jugador usando el valor obtenido de la tirada del dado.
     * @param idJugador Identificador del jugador.
     * @param avance Valor obtenido en la tirada del dado (1 a 6).
     */
    public synchronized void realizarAvance(int idJugador, int avance)
    {
        // Si el jugador llega al final del tablero se le devolverá a la posición inicial.
        if (avances[idJugador] >= 35)
        {
            avances[idJugador] = 0;
            //Revisar funcionamiento del return
            return;
        }

        // Actualizamos el avance
        /**
         * Modificar este if para finalizar cuando todos los jugadores menos 1 hayan conseguido los quesos
         */
        avances[idJugador] += avance;
        if (avances[idJugador] >= 35)
        {
            avances[idJugador] = 35;
            // Se asigna la posición final
            posicionesFinales[idJugador] = contadorPosicionFinal;
            contadorPosicionFinal++;
            numJugadoresAcabados++;
            System.out.println("Jugador " + idJugador + " ha terminado en la posición " + posicionesFinales[idJugador]);
        }
        else
        {
            System.out.println("Jugador " + idJugador + " avanza " + avance + " (Total: " + avances[idJugador] + ")");
        }

        // Si todos han finalizado, se marca el fin de la partida
        if (numJugadoresAcabados == NUM_MAX_PLAYER) {
            finPartida = true;
            notifyAll();
        }
    }

    /**
     * Devuelve el array de avances actuales.
     */
    public synchronized int[] getAvances()
    {
        return avances;
    }

    /**
     * Devuelve el array de posiciones finales. Si el jugador aún no tiene asignada su posición,
     * se le asigna en ese momento.
     */
    public synchronized int[] getPosicionesFinales(int IdJugador)
    {
        if (avances[IdJugador] >= 100 && posicionesFinales[IdJugador] == 0)
        {
            posicionesFinales[IdJugador] = contadorPosicionFinal;
            contadorPosicionFinal++;
        }
        return posicionesFinales;
    }

    /**
     * Indica si la partida ha finalizado.
     */
    public synchronized boolean isFinPartida() {
        return finPartida;
    }

    /**
     * Devuelve la lista de nombres de jinetes.
     */
    public synchronized String getNombresJugadores() {
        return nombresJugadores;
    }

    // *************** Métodos para gestionar el turno ***************

    /**
     * Devuelve el id del jugador que tiene el turno actual.
     */
    public synchronized int getTurnoActual() {
        return turnoActual;
    }

    /**
     * Cambia el turno al siguiente camello que aún no haya finalizado.
     * Se utiliza un bucle para saltar a aquellos que ya han terminado.
     */
    public synchronized void siguienteTurno() {
        do
        {
            turnoActual = (turnoActual + 1) % NUM_MAX_PLAYER;
        }
        while (avances[turnoActual] >= 100 && !finPartida);
        // Se notifica a todos los hilos que el turno ha cambiado.
        notifyAll();
    }

    /*
    @Override
    public void run()
    {
        int port = 5555;
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
    */

    /**
     * Método para manejar la comunicación con un cliente específico.
     *
     * @param cliente El socket del cliente que se está manejando.
     *
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
                try {
                    Client jugador = (Client) ois.readObject();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }


                // Enviar la actualización a todos los clients (excepto al que envió la actualización)
                synchronized (clients) {
                    for (Socket c : clients)
                    {
                        if (!c.equals(cliente))
                        { // No enviar la actualización al mismo cliente
                            // Crear un flujo de salida para el cliente actual
                            ObjectOutputStream out = new ObjectOutputStream(c.getOutputStream());
                            out.writeObject(cliente); // Enviar el objeto Player
                            out.flush(); // Asegurar que los datos se envíen
                        }
                    }
                }
            }
        } catch (IOException ex) {
            // Manejar errores de entrada/salida o de lectura de objetos
            ex.printStackTrace(); // Imprimir la traza de la excepción
        }
    }
    */
}