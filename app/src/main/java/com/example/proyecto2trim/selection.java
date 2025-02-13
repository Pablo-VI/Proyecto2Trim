package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto2trim.databinding.ActivitySelectionBinding;

import java.io.Serializable;

public class selection extends AppCompatActivity {

    private String color = ""; // Variable para almacenar el color seleccionado
    private String playerName = ""; // Variable para almacenar el nombre del jugador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelectionBinding binding = ActivitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

<<<<<<< HEAD
        // Obtener el nombre del jugador
        playerName = binding.escribetunombre.getText().toString();

=======
>>>>>>> 20af1a3e4d90d477ba1925a1ffcad935b929b789
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

        // Configurar el botón "flecha_atras" para volver a la pantalla anterior
        binding.flechaAtras.setOnClickListener(v -> finish());

        // Obtener referencia al RadioGroup
        RadioGroup radioGroup = binding.radioGroup;

        // Configurar el listener para el RadioGroup
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
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
        });

        // Configurar el botón de "check" para capturar los datos y abrir la siguiente actividad
        binding.botoncheck.setOnClickListener(v -> {
            // Obtener el texto del EditText
            playerName = binding.escribetunombre.getText().toString();

            // Verificar si se ha seleccionado un color y un nombre
            if (!playerName.isEmpty() && !color.isEmpty()) {
                // Llamar a la función para abrir la actividad con los datos
                openDetailActivity(playerName, color);
            } else {
                // Mostrar un mensaje de error si faltan datos
                System.out.println("Por favor, selecciona nombre y color.");
            }
            System.out.println("Nombre introducido: " + playerName);
        });
    }

    private void openDetailActivity(String playerName, String color) {
        Intent intent = new Intent(this, tirar_dado.class);

        // Pasamos los datos al Intent
        intent.putExtra("PLAYER_NAME", playerName);
        intent.putExtra("PLAYER_COLOR", color);

        // Iniciamos la actividad
        startActivity(intent);
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
