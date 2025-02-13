package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import java.util.logging.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class pregunta_gow extends AppCompatActivity {

    //Para determinar boton correcto y cambio de activity
    private TextView pregunta;
    private TextView respuesta1, respuesta2, respuesta3, respuesta4;
    private List<pregunta> preguntas;
    private pregunta preguntaActual;
    private int correctOption;

    //Para contador de reloj
    private TextView contador;
    private int count = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    //-----------------------------------CONTADOR-------------------------------------------------------------
        contador = findViewById(R.id.contador);  // Asegúrate de tener un TextView con el ID 'textView'
        // Crear un Handler para ejecutar el decremento en intervalos
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (count >= 0) {
                    contador.setText(String.valueOf(count));
                    count--;
                    handler.postDelayed(this, 1000); // Esperar 1 segundo (1000 ms)
                }
            }
        };

        handler.post(runnable); // Iniciar el ciclo de decremento
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
