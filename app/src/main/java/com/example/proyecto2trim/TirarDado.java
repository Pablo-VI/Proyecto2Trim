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

public class TirarDado extends AppCompatActivity {
    private ImageView imageView; // Vista para mostrar la imagen del dado
    private Button generarNumeroBtn; // Botón para lanzar el dado
    private Client cliente; // Cliente para conectarse al servidor

    Button boton = findViewById(R.id.button_tirar);

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
