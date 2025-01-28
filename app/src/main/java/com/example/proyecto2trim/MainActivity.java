package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto2trim.databinding.ActivityMainBinding;
import com.example.proyecto2trim.databinding.ActivityMenuBinding;

public class MainActivity extends AppCompatActivity {
//Eduardo marica
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Forzar orientación horizontal
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        //Iniciar la app en Español
        binding.buttonSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(this, activity_menu.class); //Creamos el intent
                startActivity(intent);  //Lanzamos el intent
            }
        });



    }
}