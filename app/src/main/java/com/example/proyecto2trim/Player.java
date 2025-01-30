package com.example.proyecto2trim;

public class Player extends Table
{
    private String nombre;
    private String color;

    public Player(String fila, int posicion, String tipoCasilla, String nombre, String color)
    {
        super(fila, posicion, tipoCasilla);
        this.nombre = nombre;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
