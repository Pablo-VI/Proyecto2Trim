package com.example.proyecto2trim;

public class Table
{
    private String fila;
    private int posicion;

    public Table(String fila, int posicion) {
        this.fila = fila;
        this.posicion = posicion;
    }

    public String getFila() {
        return fila;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public static void main(String[] args)
    {
        //Inicializar todas las casillas del tablero

        Table fila1_1 = new Table("1", 1);
        Table fila1_2 = new Table("1", 2);
    }
}
