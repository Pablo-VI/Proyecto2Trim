package com.example.proyecto2trim;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Random;

public class Table implements Parcelable {
    private int position;
    private String typeBox;


    public Table(int position, String typeBox) {
        this.position = position;
        this.typeBox = typeBox;
    }

    //Serialización
    protected Table(Parcel in) {
        position = in.readInt();
        typeBox = in.readString();
    }

    public static final Parcelable.Creator<Table> CREATOR = new Parcelable.Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };


    public int getPosition() {
        return position;
    }

    public String getTypeBox()
    {
        return typeBox;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTypeBox(String typeBox)
    {
        this.typeBox = typeBox;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(position);
        parcel.writeString(typeBox);
    }

    // Método para tirar el dado y mover al jugador
    public int tirarDado() {
        Random rand = new Random();
        int resultadoDado = rand.nextInt(6) + 1;  // Genera un número aleatorio entre 1 y 6
        setPosition(getPosition() + resultadoDado);  // Mueve al jugador en el tablero

        // Verifica si el jugador ha pasado el final del tablero
        if (getPosition() >= 35) {
            setPosition(getPosition() % 35); // Restaura la posición si se pasa del final
        }

        // Devolver el resultado del dado para hacer algo con él en la UI (si es necesario)
        return resultadoDado;
    }



}
