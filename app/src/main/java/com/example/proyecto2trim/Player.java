package com.example.proyecto2trim;

public class Player {
    private String name;
    private String color;
    private Table position; // La casilla donde está el jugador

    public Player(String name, String color, Table position) {
        this.name = name;
        this.color = color;
        this.position = this.position; // Se asigna una casilla existente
    }

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
}
