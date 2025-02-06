package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto2trim.databinding.ActivityTirarDadoBinding;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class TirarDado extends AppCompatActivity {
    private ImageView imageView; // Vista para mostrar la imagen del dado
    private Button generarNumeroBtn; // Botón para lanzar el dado
    private Client cliente; // Cliente para conectarse al servidor
    private Player jugador; // Jugador que está usando la aplicación

    Button boton = findViewById(R.id.button_tirar);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tirar_dado); // Establecer el layout de la actividad
        ActivityTirarDadoBinding binding = ActivityTirarDadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Forzar orientación horizontal
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Ocultar la barra de estado y la barra de navegación
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        // Ocultar la ActionBar si está presente
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Crear todas las casillas del tablero para el jugador
        //Inicializar todas las casillas del tablero
        Table [] fila1 = new Table[6]; //6 casillas
        Table [] fila2 = new Table[6]; //6 casillas
        Table [] fila3 = new Table[6]; //6 casillas
        Table [] fila4 = new Table[6]; //6 casillas
        Table [] fila5 = new Table[6]; //6 casillas
        Table [] fila6 = new Table[6]; //6 casillas
        Table [] main = new Table[42]; //42 casillas

        Table inicio = new Table("Inicio", 0, "Inicio");

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



        // Inicializar vistas
        imageView = findViewById(R.id.dice); // Obtener la referencia al ImageView del dado
        generarNumeroBtn = findViewById(R.id.button_tirar); // Obtener la referencia al botón de lanzar

        // Inicializar los botones que se mostrarán dinámicamente
        ImageButton buttonRow1 = findViewById(R.id.boton_fila_1);
        ImageButton buttonRow2 = findViewById(R.id.boton_fila_2);
        ImageButton buttonRow3 = findViewById(R.id.boton_fila_3);
        ImageButton buttonRow4 = findViewById(R.id.boton_fila_4);
        ImageButton buttonRow5 = findViewById(R.id.boton_fila_5);
        ImageButton buttonRow6 = findViewById(R.id.boton_fila_6);
        TextView textChooseRow = findViewById(R.id.text_chooseRow);

        // Ocultar los botones inicialmente
        buttonRow1.setVisibility(View.GONE);
        buttonRow2.setVisibility(View.GONE);
        buttonRow3.setVisibility(View.GONE);
        buttonRow4.setVisibility(View.GONE);
        buttonRow5.setVisibility(View.GONE);
        buttonRow6.setVisibility(View.GONE);
        textChooseRow.setVisibility(View.GONE);

        // Inicializar jugador con valores predeterminados
        jugador = new Player("player1", "Azul", inicio, 0);

        // Conectar al servidor en el puerto 12345
        cliente = new Client(12345);
        new Thread(cliente).start(); // Iniciar el cliente en un hilo separado

        // Escuchar actualizaciones del servidor
        cliente.addObserver(evt -> {
            if ("jugador".equals(evt.getPropertyName()))
            { // Si el evento es sobre un jugador
                Player actualizado = (Player) evt.getNewValue(); // Obtener el jugador actualizado
                runOnUiThread(() -> actualizarInterfaz(actualizado)); // Actualizar la interfaz en el hilo principal
            }
        });

        // Configurar el listener para el botón de lanzar el dado
        generarNumeroBtn.setOnClickListener(v -> {
            // Generar un número aleatorio entre 1 y 6
            Random rand = new Random();
            int numeroAleatorio = rand.nextInt(6) + 1;
            jugador.setNumThrows(jugador.getNumThrows()+1);

            // Actualizar la posición del jugador sumando el número aleatorio
            //jugador.moveTo(jugador.getPosition() + numeroAleatorio);

            // Enviar la actualización al servidor en un hilo separado
            new Thread(() -> {
                try (Socket sc = new Socket("127.0.0.1", 12345); // Conectar al servidor
                     ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream()))
                { // Crear flujo de salida
                    oos.writeObject(jugador); // Enviar el objeto jugador al servidor
                } catch (IOException ex) {
                    ex.printStackTrace(); // Manejar errores de conexión o envío
                }
            }).start();

            // Cambiar la imagen del dado según el número generado
            cambiarImagenDado(numeroAleatorio);

            // Mostrar los botones solo en la primera tirada
            if (jugador.getNumThrows() == 1) {
                buttonRow1.setVisibility(View.VISIBLE);
                buttonRow2.setVisibility(View.VISIBLE);
                buttonRow3.setVisibility(View.VISIBLE);
                buttonRow4.setVisibility(View.VISIBLE);
                buttonRow5.setVisibility(View.VISIBLE);
                buttonRow6.setVisibility(View.VISIBLE);
                textChooseRow.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Método para cambiar la imagen del dado según el número generado.
     *
     * @param numero El número generado (1-6).
     */
    private void cambiarImagenDado(int numero)
    {
        int[] recursos = {
                R.drawable.dado_1, // Imagen para el número 1
                R.drawable.dado_2, // Imagen para el número 2
                R.drawable.dado_3, // Imagen para el número 3
                R.drawable.dado_4, // Imagen para el número 4
                R.drawable.dado_5, // Imagen para el número 5
                R.drawable.dado_6  // Imagen para el número 6
        };
        imageView.setImageResource(recursos[numero - 1]); // Establecer la imagen correspondiente
    }

    /**
     * Método para actualizar la interfaz con la nueva posición del jugador.
     *
     * @param jugador El jugador con la posición actualizada.
     */
    private void actualizarInterfaz(Player jugador)
    {
        // Aquí se puede actualizar la interfaz de usuario con la nueva posición del jugador.
        // Por ejemplo, mostrar la posición en un TextView o mover una ficha en el tablero.
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Asegurarse de que la pantalla completa se mantenga al volver a la actividad
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}