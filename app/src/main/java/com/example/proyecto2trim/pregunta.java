package com.example.proyecto2trim;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto2trim.databinding.ActivityPreguntaBinding;
import com.example.proyecto2trim.databinding.ActivitySelectionBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class pregunta extends AppCompatActivity {

    private List<ClasePregunta> listaPreguntas;
    private TextView preguntaTextView;
    private Button boton1, boton2, boton3, boton4;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPreguntaBinding binding = ActivityPreguntaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        TextView TextView = binding.respuesta1;
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

        // Inicializamos el TextView y los botones para mostrar la pregunta y las opciones
        preguntaTextView = findViewById(R.id.pregunta);
        boton1 = (Button) binding.respuesta1;
        boton2 = (Button) binding.respuesta2;
        boton3 = (Button) binding.respuesta3;
        boton4 = (Button) binding.respuesta4;

        // Crear lista de preguntas
        listaPreguntas = new ArrayList<>();
        listaPreguntas.add(new ClasePregunta(1, "¿Cuál es la capital de Francia?",
                Arrays.asList("París", "Madrid", "Roma", "Berlín"), "París"));
        listaPreguntas.add(new ClasePregunta(2, "¿Cuántos continentes hay en el mundo?",
                Arrays.asList("5", "6", "7", "8"), "7"));
        listaPreguntas.add(new ClasePregunta(3, "¿Quién pintó la Mona Lisa?",
                Arrays.asList("Picasso", "Da Vinci", "Van Gogh", "Rembrandt"), "Da Vinci"));

        // Botón para generar una pregunta aleatoria
        Button generarPreguntaButton = findViewById(R.id.button_tirar);
        generarPreguntaButton.setOnClickListener(v -> generarPreguntaAleatoria());

        // Setear los listeners de las opciones
        boton1.setOnClickListener(v -> verificarRespuesta(boton1.getText().toString()));
        boton2.setOnClickListener(v -> verificarRespuesta(boton2.getText().toString()));
        boton3.setOnClickListener(v -> verificarRespuesta(boton3.getText().toString()));
        boton4.setOnClickListener(v -> verificarRespuesta(boton4.getText().toString()));

    }
    // Método para obtener el índice de la pregunta actual (puedes usar algún tipo de lógica para llevar el seguimiento)
    private int indicePreguntaActual () {
        return 0; // Para este ejemplo simple, solo devuelve un valor estático.
    }

    private void verificarRespuesta (String respuestaSeleccionada){
        // Verificar si la respuesta seleccionada es correcta
        ClasePregunta preguntaActual = listaPreguntas.get(indicePreguntaActual());
        if (respuestaSeleccionada.equals(preguntaActual.getRespuestaCorrecta())) {
            Toast.makeText(this, "¡Respuesta correcta!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Respuesta incorrecta. Intenta de nuevo.", Toast.LENGTH_SHORT).show();
        }
    }

    private void generarPreguntaAleatoria () {
        // Generar un índice aleatorio para seleccionar una pregunta
        Random random = new Random();
        int indiceAleatorio = random.nextInt(listaPreguntas.size());
        ClasePregunta preguntaAleatoria = listaPreguntas.get(indiceAleatorio);

        // Mostrar la pregunta y las opciones
        preguntaTextView.setText(preguntaAleatoria.getClasePregunta());
        List<String> opciones = preguntaAleatoria.getClaseOpciones();
        boton1.setText(opciones.get(0));
        boton2.setText(opciones.get(1));
        boton3.setText(opciones.get(2));
        boton4.setText(opciones.get(3));
    }

    @Override
    protected void onResume () {
        super.onResume();
        // Asegurarse de que la pantalla completa se mantenga al volver a la actividad
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}