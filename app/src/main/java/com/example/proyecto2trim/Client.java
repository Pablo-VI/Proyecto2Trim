package com.example.proyecto2trim;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client implements Runnable, Parcelable {
    private int port; // Puerto para conectarse al servidor
    private PropertyChangeSupport support; // Soporte para notificar cambios a los observadores
    private String name;
    private String color;
    private Table position; // La casilla donde está el jugador

    /**
     * Constructor para inicializar el cliente.
     *
     * @param port El port en el que el servidor está escuchando.
     * @param name El nombre del jugador.
     * @param color El color del jugador.
     * @param position La posición en la tabla del jugador.
     */
    public Client(int port, String name, String color, Table position) {
        this.port = port;
        this.support = new PropertyChangeSupport(this);
        this.name = name;
        this.color = color;
        this.position = position;
    }

    // Constructor para Parcel (deserialización)
    protected Client(Parcel in) {
        name = in.readString();
        color = in.readString();
        position = in.readParcelable(Table.class.getClassLoader()); // Leer Table como Parcelable
    }

    // Crear un objeto Client desde un Parcel
    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Table getPosition() {
        return position;
    }

    public void setPosition(Table position) {
        this.position = position;
    }

    // Método para mover al jugador
    public void moveTo(Table newPosition) {
        this.position = newPosition;
    }

    // Descripción de los contenidos para la serialización
    @Override
    public int describeContents() {
        return 0;
    }

    // Escribir el objeto Client en un Parcel
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(color);
        parcel.writeParcelable(position, flags); // Escribir la posición como Parcelable
    }

    /**
     * Método para agregar un observador (listener) que escuchará los cambios.
     *
     * @param listener El observador que se agregará.
     */
    public void addObserver(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Método para iniciar la conexión al servidor.
     */
    @Override
    public void run() {
        final String HOST = "127.0.0.1"; // Dirección IP del servidor (localhost)
        try (Socket sc = new Socket(HOST, port); // Crear un socket para conectarse al servidor
             ObjectInputStream ois = new ObjectInputStream(sc.getInputStream())) {
            // Bucle infinito para recibir actualizaciones del servidor
            while (true) {
                Client jugador = (Client) ois.readObject(); // Recibir el objeto jugador del servidor
                support.firePropertyChange("jugador", null, jugador); // Notificar a los observadores
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Manejar errores de conexión o de lectura de objetos
        }
    }
}
