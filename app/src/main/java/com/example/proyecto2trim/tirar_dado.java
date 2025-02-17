package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto2trim.databinding.ActivityTirarDadoBinding;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class tirar_dado extends AppCompatActivity {
    private ImageView imageView; // Vista para mostrar la imagen del dado
    private ImageView generarNumeroBtn; // Botón para lanzar el dado
    private Client cliente; // Cliente para conectarse al servidor

    // Variables para gestionar la tirada del dado
    private int resultadoDado;
    private boolean dadoLanzado;

    private TextView etiquetaJugador;    // Muestra el nombre del jugador



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

        // Obtener los datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            String playerName = intent.getStringExtra("PLAYER_NAME");
            String playerColor = intent.getStringExtra("PLAYER_COLOR");

            if (playerName != null && playerColor != null) {
                System.out.println("Nombre del jugador: " + playerName);
                System.out.println("Color del jugador: " + playerColor);
            }
        }

        // Crear todas las casillas del tablero para el jugador
        //Inicializar todas las casillas del tablero
        Table [] avances = new Table[36]; //35 casillas

        avances [0] = new Table(0, "Inicio");
        avances [1] = new Table (1, "Ficha Azul");
        avances [2] = new Table (2, "Historia");
        avances [3] = new Table (3, "Tirar Dado");
        avances [4] = new Table (4, "Ciencias y Naturaleza");
        avances [5] = new Table (5, "Entretenimiento");
        avances [6] = new Table (6, "Tirar Dado");
        avances [7] = new Table (7, "Deportes y Pasatiempos");
        avances [8] = new Table (8, "Ficha Morada");
        avances [9] = new Table (9, "Deportes y Pasatiempos");
        avances [10] = new Table (10, "Tirar Dado");
        avances [11] = new Table (11, "Historia");
        avances [12] = new Table (12, "Geografía");
        avances [13] = new Table (13, "Tirar Dado");
        avances [14] = new Table (14, "Entretenimiento");
        avances [15] = new Table (15, "Ficha Verde");
        avances [16] = new Table (16, "Entretenimiento");
        avances [17] = new Table (17, "Tirar Dado");
        avances [18] = new Table (18, "Deportes y Pasatiempos");
        avances [19] = new Table (19, "Arte y Literatura");
        avances [20] = new Table (20, "Tirar Dado");
        avances [21]= new Table (21, "Geografía");
        avances [22] = new Table (22, "Ficha Amarilla");
        avances [23] = new Table (23, "Geografía");
        avances [24] = new Table (24, "Tirar Dado");
        avances [25] = new Table (25, "Entretenimiento");
        avances [26] = new Table (26, "Ciencias y Naturaleza");
        avances [27] = new Table (27, "Tirar Dado");
        avances [28] = new Table (28, "Arte y Literatura");
        avances [29] = new Table (29, "Ficha Naranja");
        avances [30] = new Table (30, "Arte y Literatura");
        avances [31] = new Table (31, "Tirar Dado");
        avances [32] = new Table (32, "Geografía");
        avances [33] = new Table (33, "Historia");
        avances [34] = new Table (34, "Tirar Dado");
        avances [35] = new Table (35, "Ciencias y Naturaleza");

        // Inicializar vistas
        imageView = binding.dice; // Obtener la referencia al ImageView del dado
        generarNumeroBtn = binding.buttonTirar; // Obtener la referencia al botón de lanzar

        generarNumeroBtn.setOnClickListener(v -> tirarDado());

        /*
        Adaptarlo para que cambie despues de tirar dado
        // Configuramos el listener del Button
        generarNumeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar a la segunda Activity
                Intent intent = new Intent(tirar_dado.this, pregunta.class);
                startActivity(intent);
            }
        });*/
    }

    public int esperarTirada()
    {
        // Activar la tirada en la interfaz
        activarTirada();
        synchronized(this)
        {
            while (!dadoLanzado)
            {
                try
                {
                    // Esperar hasta que el dado sea lanzado
                    wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            // Reiniciar la bandera para la siguiente tirada
            dadoLanzado = false;
            return resultadoDado;
        }
    }

    /**
     * Activa el botón de "Lanzar Dado" para que el jugador pueda realizar su tirada.
     * Se llama cuando el servidor notifica que es el turno del jugador.
     */
    public void activarTirada() {
        runOnUiThread(() -> {
            generarNumeroBtn.setEnabled(true);
        });
    }

    // Método para notificar que el dado ha sido lanzado
    public void onDadoLanzado(int numeroAleatorio) {
        synchronized(this) {
            resultadoDado = numeroAleatorio;
            dadoLanzado = true;
            notify(); // Despertar el hilo en espera
        }
    }

    ///Método para tirar de dado
    public void tirarDado() {
        Random rand = new Random();

        // Deshabilitar el botón para evitar múltiples clics
        generarNumeroBtn.setEnabled(false);

        int numeroAleatorio = rand.nextInt(6) + 1;

        // Notificar que el dado ha sido lanzado
        onDadoLanzado(numeroAleatorio);
        // Cambiar imagen del dado
        cambiarImagenDado(numeroAleatorio);
    }

    private void cambiarImagenDado(int numeroAleatorio) {
        int[] recursos = {
                R.drawable.dado_1, R.drawable.dado_2, R.drawable.dado_3,
                R.drawable.dado_4, R.drawable.dado_5, R.drawable.dado_6
        };
        imageView.setImageResource(recursos[numeroAleatorio - 1]);
    }

    private void ocultarBarrasDeSistema() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ocultarBarrasDeSistema();
    }
}


