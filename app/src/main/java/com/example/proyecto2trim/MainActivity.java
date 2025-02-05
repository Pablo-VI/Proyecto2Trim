package com.example.proyecto2trim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto2trim.databinding.ActivityMainBinding;
import com.example.proyecto2trim.databinding.ActivityTirarDadoBindingImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializamos el ImageButton
        ImageButton cambiarActivityBtn = findViewById(R.id.button_Spanish);

        // Configuramos el listener del ImageButton
        cambiarActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar a la segunda Activity
                Intent intent = new Intent(MainActivity.this, menu.class);
                startActivity(intent);
            }
        });
    }
}