package com.example.proyecto2trim;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Player implements Parcelable {
    private String name;
    private String color;
    private Table position; // La casilla donde está el jugador
    private int numThrows;

    public Player(String name, String color, Table position, int numThrows) {
        this.name = name;
        this.color = color;
        this.position = position; // Se asigna una casilla existente
        this.numThrows = numThrows;
    }

    //Serialización
    protected Player(Parcel in) {
        name = in.readString();
        color = in.readString();
        position = in.readParcelable(Table.class.getClassLoader()); // Leer Table como Parcelable
        numThrows = in.readInt();
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
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

    public int getNumThrows() {
        return numThrows;
    }

    public void setNumThrows(int numThrows) {
        this.numThrows = numThrows;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(color);
        parcel.writeParcelable(position, i); // Escribir Table como Parcelable
        parcel.writeInt(numThrows);
    }
}