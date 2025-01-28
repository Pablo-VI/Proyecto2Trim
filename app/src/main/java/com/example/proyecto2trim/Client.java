package com.example.proyecto2trim;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    private final int puerto;
    private final PropertyChangeSupport soporte;

    public Client(int puerto) {
        this.puerto = puerto;
        this.soporte = new PropertyChangeSupport(this);
    }

    public void addObserver(PropertyChangeListener listener) {
        soporte.addPropertyChangeListener(listener);
    }

    @Override
    public void run() {
        final String HOST = "127.0.0.1";
        try {
            Socket sc = new Socket(HOST, puerto);
            ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());

            while (true) {
                Gasolinera g = (Gasolinera) ois.readObject();

                // Notificar cambios a los observadores
                soporte.firePropertyChange("gasolinera", null, g);
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}