package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto2trim.databinding.ActivityMenuBinding;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_menu);
        ActivityMenuBinding binding = ActivityMenuBinding.inflate(getLayoutInflater());
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

        // Configurar el botón "flecha_atras" para volver a la pantalla anterior
        binding.flechaAtras.setOnClickListener(v -> finish());

        // Instanciar los elementos
        Button Crear = binding.buttonCreate;
        Button Unir =  binding.buttonJoin;

        // Obtener el valor del EditText convertirlo a int de manera segura
        String createCode = binding.numberCreate.getText().toString().trim();
        int createLobby = createCode.isEmpty() ? 0 : Integer.parseInt(createCode);
        String joinCode = binding.numberCreate.getText().toString().trim();
        int joinLobby = joinCode.isEmpty() ? 0 : Integer.parseInt(joinCode);


        //Interacción con botón Crear partida
        Crear.setOnClickListener(v -> {
            if (!createCode.matches("\\d{4}"))
            {
                String toastMessage = getString(R.string.toastCreateLobby);
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Llamar al método para abrir la Activity dificultad
                openDetailActivity(createCode);
            }
        });

        if (createLobby == joinLobby)
        {
            openDetailActivity(createCode);
        }
        else
        {
            String toastMessage = getString(R.string.toastJoinLobby);
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }

    // Método para abrir la Activity y pasar el código
    private void openDetailActivity(String createCode) {
        Intent intent = new Intent(menu.this, dificultad.class); // Crear el Intent

        // Pasar el valor a la nueva Activity
        intent.putExtra("CREATE_CODE", createCode); // Clave-valor para pasar datos

        startActivity(intent);  // Lanzar la nueva Activity
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
