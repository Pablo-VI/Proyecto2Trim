package com.example.proyecto2trim;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client implements Runnable {
    private int port; // Puerto para conectarse al servidor
    private PropertyChangeSupport support; // Soporte para notificar cambios a los observadores
    private String name;
    private String color;
    private Table position; // La casilla donde está el jugador

    /**
     * Constructor para inicializar el cliente.
     *
     * @param port El port en el que el servidor está escuchando.
     */
    public Client(int port, String name, String color, Table position)
    {
        this.port = port; // Asignar el port
        this.support = new PropertyChangeSupport(this); // Inicializar el support para notificaciones
        this.name = name;
        this.color = color;
        this.position = position; // Se asigna una casilla existente
    }

    //Serialización
    protected Client(Parcel in) {
        name = in.readString();
        color = in.readString();
        position = in.readParcelable(Table.class.getClassLoader()); // Leer Table como Parcelable
    }

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

    public void moveTo(Table newPosition) {
        this.position = newPosition;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(color);
        parcel.writeParcelable(position, i); // Escribir Table como Parcelable
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
        //if(codigoPartida)
        //{

        //}
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
                Client jugador = (Client) ois.readObject();

                // Notificar a los observadores sobre el cambio
                support.firePropertyChange("jugador", null, jugador);
            }
        } catch (IOException | ClassNotFoundException ex) {
            // Manejar errores de conexión o de lectura de objetos
            ex.printStackTrace(); // Imprimir la traza de la excepción
        }
    }
}