package com.example.proyecto2trim;

public class Table
{
    private String fila;
    private int posicion;
    private String tipoCasilla;


    public Table(String fila, int posicion, String tipoCasilla) {
        this.fila = fila;
        this.posicion = posicion;
        this.tipoCasilla = tipoCasilla;
    }

    public String getFila() {
        return fila;
    }

    public int getPosicion() {
        return posicion;
    }

    public String getTipoCasilla()
    {
        return tipoCasilla;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void setTipoCasilla(String tipoCasilla)
    {
        this.tipoCasilla = tipoCasilla;
    }

    public static void main(String[] args)
    {
        //Inicializar todas las casillas del tablero
        Table [] fila1 = new Table[6]; //6 casillas
        Table [] fila2 = new Table[6]; //6 casillas
        Table [] fila3 = new Table[6]; //6 casillas
        Table [] fila4 = new Table[6]; //6 casillas
        Table [] fila5 = new Table[6]; //6 casillas
        Table [] fila6 = new Table[6]; //6 casillas
        Table [] main = new Table[42]; //42 casillas

        fila1[0] = new Table("1", 1, "Entretenimiento");
        fila1[1] = new Table("1", 2, "Arte y Literatura");
        fila1[2] = new Table("1", 3, "Ciencias y Naturaleza");
        fila1[3] = new Table("1", 4, "Deportes y Pasatiempos");
        fila1[4] = new Table("1", 5, "Historia");
        fila1[5] = new Table("1", 6, "Ficha Azul");

        fila2 [0] = new Table("2",1, "Geografía");
        fila2 [1] = new Table("2", 2, "Ciencias y Naturaleza");
        fila2 [2] = new Table("2", 3, "Historia");
        fila2 [3] = new Table("2", 4, "Entretenimiento");
        fila2 [4] = new Table("2", 5, "Deportes y Pasatiempos");
        fila2 [5] = new Table("2", 6, "Ficha Morada");

        fila3 [0] = new Table("3",1, "Arte y Literatura");
        fila3 [1] = new Table("3", 2, "Historia");
        fila3 [2] = new Table("3", 3, "Deportes y Pasatiempos");
        fila3 [3] = new Table("3", 4, "Geografía");
        fila3 [4] = new Table("3", 5, "Entretenimiento");
        fila3 [5] = new Table("3", 6, "Ficha Verde");

        fila4 [0] = new Table("4", 1, "Ciencias y Naturaleza");
        fila4 [1] = new Table("4", 2, "Deportes y Pasatiempos");
        fila4 [2] = new Table("4", 3, "Entretenimiento");
        fila4 [3] = new Table("4", 4, "Arte y Literatura");
        fila4 [4] = new Table("4", 5, "Geografía");
        fila4 [5] = new Table("4", 6, "Ficha Amarilla");

        fila5 [0] = new Table("5",1, "Historia");
        fila5 [1] = new Table("5", 2, "Entretenimiento");
        fila5 [2] = new Table("5", 3, "Geografía");
        fila5 [3] = new Table("5", 4, "Ciencias y Naturaleza");
        fila5 [4] = new Table("5", 5, "Arte y Literatura");
        fila5 [5] = new Table("5", 6, "Ficha Naranja");

        fila6 [0] = new Table("6",1, "Deportes y Pasatiempos");
        fila6 [1] = new Table("6", 2, "Geografía");
        fila6 [2] = new Table("6", 3, "Arte y Literatura");
        fila6 [3] = new Table("6", 4, "Historia");
        fila6 [4] = new Table("6", 5, "Ciencias y Naturaleza");
        fila6 [5] = new Table("6", 6, "Ficha Rosa");

        main [0] = new Table ("main", 1, "Ficha Azul");
        main [1] = new Table ("main", 2, "Historia");
        main [2] = new Table ("main", 3, "Tirar Dado");
        main [3] = new Table ("main", 4, "Ciencias y Naturaleza");
        main [4] = new Table ("main", 5, "Entretenimiento");
        main [5] = new Table ("main", 6, "Tirar Dado");
        main [6] = new Table ("main", 7, "Deportes y Pasatiempos");

        main [7] = new Table ("main", 8, "Ficha Morada");
        main [8] = new Table ("main", 9, "Deportes y Pasatiempos");
        main [9] = new Table ("main", 10, "Tirar Dado");
        main [10] = new Table ("main", 11, "Historia");
        main [11] = new Table ("main", 12, "Geografía");
        main [12] = new Table ("main", 13, "Tirar Dado");
        main [13] = new Table ("main", 14, "Entretenimiento");

        main [14] = new Table ("main", 15, "Ficha Verde");
        main [15] = new Table ("main", 16, "Entretenimiento");
        main [16] = new Table ("main", 17, "Tirar Dado");
        main [17] = new Table ("main", 18, "Deportes y Pasatiempos");
        main [18] = new Table ("main", 19, "Arte y Literatura");
        main [19] = new Table ("main", 20, "Tirar Dado");
        main [20]= new Table ("main", 21, "Geografía");

        main [21] = new Table ("main", 22, "Ficha Amarilla");
        main [22] = new Table ("main", 23, "Geografía");
        main [23] = new Table ("main", 24, "Tirar Dado");
        main [24] = new Table ("main", 25, "Entretenimiento");
        main [25] = new Table ("main", 26, "Ciencias y Naturaleza");
        main [26] = new Table ("main", 27, "Tirar Dado");
        main [27] = new Table ("main", 28, "Arte y Literatura");

        main [28] = new Table ("main", 29, "Ficha Naranja");
        main [29] = new Table ("main", 30, "Arte y Literatura");
        main [30] = new Table ("main", 31, "Tirar Dado");
        main [31] = new Table ("main", 32, "Geografía");
        main [32] = new Table ("main", 33, "Historia");
        main [33] = new Table ("main", 34, "Tirar Dado");
        main [34] = new Table ("main", 35, "Ciencias y Naturaleza");

        main [35] = new Table ("main", 36, "Ficha Rosa");
        main [36] = new Table ("main", 37, "Ciencias y Naturaleza");
        main [37] = new Table ("main", 38, "Tirar Dado");
        main [38] = new Table ("main", 39, "Arte y Literatura");
        main [39] = new Table ("main", 40, "Deportes y Pasatiempos");
        main [40] = new Table ("main", 41, "Tirar Dado");
        main [41] = new Table ("main", 42, "Historia");

        //Igualar las casillas que son iguales
        fila1[5] = main[0]; //Ficha Azul
        fila2[5] = main[7]; //Ficha Morada
        fila3[5] = main[14]; //Ficha Verde
        fila4[5] = main[21]; //Ficha Amarilla
        fila5[5] = main[28]; //Ficha Naranja
        fila6[5] = main[35]; //Ficha Rosa
    }
}
