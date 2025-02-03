package com.example.proyecto2trim;

public class Table
{
    private String row;
    private int position;
    private String typeBox;


    public Table(String row, int position, String typeBox) {
        this.row = row;
        this.position = position;
        this.typeBox = typeBox;
    }

    public String getRow() {
        return row;
    }

    public int getPosition() {
        return position;
    }

    public String getTypeBox()
    {
        return typeBox;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTypeBox(String typeBox)
    {
        this.typeBox = typeBox;
    }
}
