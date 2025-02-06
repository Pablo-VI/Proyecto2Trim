package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto2trim.databinding.ActivityTirarDadoBinding;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class tirar_dado extends AppCompatActivity {
    private ImageView imageView; // Vista para mostrar la imagen del dado
    private Button generarNumeroBtn; // Botón para lanzar el dado
    private Client cliente; // Cliente para conectarse al servidor
    private Client jugador; // Jugador que está usando la aplicación


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTirarDadoBinding binding = ActivityTirarDadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ocultarBarrasDeSistema();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Crear todas las casillas del tablero para el jugador
        //Inicializar todas las casillas del tablero
        Table [] main = new Table[35]; //35 casillas
        Table inicio = new Table("Inicio", 0, "Inicio");

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
  
        inicializarTablero();

        imageView = findViewById(R.id.dice);
        generarNumeroBtn = findViewById(R.id.button_tirar);
        ocultarBotonesFilas();

        // Se crea el cliente usando el constructor que asigna valores por defecto
        cliente = new Client(12345);
        new Thread(cliente).start();

        // Se añade un listener para recibir actualizaciones desde el servidor
        cliente.addObserver(evt -> {
            if ("jugador".equals(evt.getPropertyName())) {
                Client actualizado = (Client) evt.getNewValue();
                runOnUiThread(() -> actualizarInterfaz(actualizado));
            }
        });

        generarNumeroBtn.setOnClickListener(v -> tirarDado());

        // Inicializamos el Button para cambiar de Activity
        Button cambiarActivity = findViewById(R.id.button_tirar);

        // Configuramos el listener del Button
        cambiarActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar a la segunda Activity
                Intent intent = new Intent(tirar_dado.this, pregunta.class);
                startActivity(intent);
            }
        });
    }

    private void ocultarBarrasDeSistema() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void ocultarBotonesFilas() {
        int[] botonesFilas = {
                R.id.boton_fila_1, R.id.boton_fila_2, R.id.boton_fila_3,
                R.id.boton_fila_4, R.id.boton_fila_5, R.id.boton_fila_6
        };
        for (int botonId : botonesFilas) {
            findViewById(botonId).setVisibility(View.GONE);
        }
        findViewById(R.id.text_chooseRow).setVisibility(View.GONE);
    }

    private void tirarDado() {
        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(6) + 1;

        new Thread(() -> {
            try (Socket sc = new Socket("127.0.0.1", 12345);
                 ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream())) {
                // Se envía el objeto cliente (con sus datos actuales) al servidor
                oos.writeObject(cliente);
                oos.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();

        cambiarImagenDado(numeroAleatorio);
    }

    private void cambiarImagenDado(int numero) {
        int[] recursos = {
                R.drawable.dado_1, R.drawable.dado_2, R.drawable.dado_3,
                R.drawable.dado_4, R.drawable.dado_5, R.drawable.dado_6
        };
        imageView.setImageResource(recursos[numero - 1]);
    }

    private void actualizarInterfaz(Client jugador) {
        // Actualizar la interfaz según el estado del cliente/jugador
    }

    @Override
    protected void onResume() {
        super.onResume();
        ocultarBarrasDeSistema();
    }

    private void inicializarTablero() {
        // Inicializar el tablero si es necesario
    }
}
