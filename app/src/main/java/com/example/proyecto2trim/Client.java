package com.example.proyecto2trim;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client implements Runnable {
    private final int port; // Puerto para conectarse al servidor
    private final PropertyChangeSupport support; // Soporte para notificar cambios a los observadores

    /**
     * Constructor para inicializar el cliente.
     *
     * @param port El port en el que el servidor está escuchando.
     */
    public Client(int port)
    {
        this.port = port; // Asignar el port
        this.support = new PropertyChangeSupport(this); // Inicializar el support para notificaciones
    }

    /**
     * Método para agregar un observador (listener) que escuchará los cambios.
     *
     * @param listener El observador que se agregará.
     */
    public void addObserver(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener); // Agregar el observador
    }

    @Override
    public void run()
    {
        final String HOST = "127.0.0.1"; // Dirección IP del servidor (localhost)
        try (// Crear un socket para conectarse al servidor
                Socket sc = new Socket(HOST, port);
                // Crear un flujo de entrada para recibir objetos del servidor
                ObjectInputStream ois = new ObjectInputStream(sc.getInputStream()))
        {
            // Bucle infinito para recibir actualizaciones del servidor
            while (true)
            {
                // Recibir la actualización del jugador (objeto Player)
                Player jugador = (Player) ois.readObject();

                // Notificar a los observadores sobre el cambio
                support.firePropertyChange("jugador", null, jugador);
            }
        } catch (IOException | ClassNotFoundException ex) {
            // Manejar errores de conexión o de lectura de objetos
            ex.printStackTrace(); // Imprimir la traza de la excepción
        }
    }
}