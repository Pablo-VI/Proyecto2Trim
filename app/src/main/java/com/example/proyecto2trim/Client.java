package com.example.proyecto2trim;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

public class Client implements Runnable, Parcelable, Serializable {
    private static final long serialVersionUID = 1L;

    private int port; // Puerto para conectarse al servidor
    private transient PropertyChangeSupport support; // Soporte para notificar cambios a los observadores (no serializable)
    private String name;
    private String color;
    private Table position; // La casilla donde está el jugador

    /**
     * Constructor para inicializar el cliente.
     *
     * @param port El puerto en el que el servidor está escuchando.
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

    // Constructor sobrecargado que asigna valores por defecto
    public Client(int port) {
        this(port, "JugadorDefault", "Rojo", new Table("A", 1, "Normal"));
    }

    // Constructor para Parcel (deserialización)
    protected Client(Parcel in) {
        name = in.readString();
        color = in.readString();
        position = in.readParcelable(Table.class.getClassLoader()); // Leer Table como Parcelable
        support = new PropertyChangeSupport(this);
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

    // Métodos de Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(color);
        parcel.writeParcelable(position, flags);
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
        try (Socket sc = new Socket(HOST, port);
             ObjectInputStream ois = new ObjectInputStream(sc.getInputStream())) {
            // Bucle infinito para recibir actualizaciones del servidor
            while (true) {
                Client jugador = (Client) ois.readObject();
                support.firePropertyChange("jugador", null, jugador);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
