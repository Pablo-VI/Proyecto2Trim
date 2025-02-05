package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto2trim.databinding.ActivitySelectionBinding;

public class selection extends AppCompatActivity {

    private String color = ""; // Variable para almacenar el color seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelectionBinding binding = ActivitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String playerName = binding.escribetunombre.getText().toString();

        private void openDetailActivity(String playerName, String color, Table position, int numThrows)
        {
            Intent intent = new Intent(this, Client.class); //Creamos el intent

            //Creamos nuevo jugador
            Player player = new Player (playerName, color, position, numThrows);

            //Capturamos el objeto player a entregar
            intent.putExtra(Client.PLAYER, player); //Almacenamos variables con el putExtra

            startActivity(intent);  //Lanzamos el intent
        }

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

        // Obtener referencia al RadioGroup
        RadioGroup radioGroup = binding.radioGroup;

        // Configurar el listener para el RadioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Determinar qué RadioButton fue seleccionado
                if (checkedId == R.id.marron) {
                    color = "marrón";
                } else if (checkedId == R.id.amarillo) {
                    color = "amarillo";
                } else if (checkedId == R.id.azul) {
                    color = "azul";
                } else if (checkedId == R.id.morado) {
                    color = "morado";
                } else if (checkedId == R.id.naranja) {
                    color = "naranja";
                } else if (checkedId == R.id.verde) {
                    color = "verde";
                }

                // Aquí puedes usar la variable `color` como necesites
                // Por ejemplo, mostrarla en un Log o usarla en otra parte de tu aplicación
                System.out.println("Color seleccionado: " + color);
            }
        });
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