package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto2trim.databinding.ActivityMenuBinding;
public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_menu);
        ActivityMenuBinding binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Instanciar los elementos
        Button Activity = binding.buttonCreate;
        int createLobby = binding.numberCreate;


        // Configurar el listener para el clic del botón usando una expresión lambda
        Activity.setOnClickListener(view ->

        {
            // Crear un Intent para cambiar a la segunda Activity
            Intent intent = new Intent(menu.this, dificultad.class);
            startActivity(intent);
        });
    }
}
