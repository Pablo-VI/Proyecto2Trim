package com.example.proyecto2trim;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto2trim.databinding.ActivitySelectionBinding;
import java.util.Random;

public class tirar_dado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelectionBinding binding = ActivitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView imageView;
        Button generarNumeroBtn;

        // Forzar orientación horizontal
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Inicializar vistas
        imageView = findViewById(R.id.dice);
        generarNumeroBtn = findViewById(R.id.button_tirar);

        // Configurar el listener para el botón
        generarNumeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generar un número aleatorio entre 1 y 6
                Random rand = new Random();
                int numeroAleatorio = rand.nextInt(6) + 1;


                // Cambiar la imagen dependiendo del número aleatorio
                switch (numeroAleatorio) {
                    case 1:
                        imageView.setImageResource(R.drawable.dado_1);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.dado_2);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.dado_3);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.dado_4);
                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.dado_5);
                        break;
                    case 6:
                        imageView.setImageResource(R.drawable.dado_6);
                        break;
                }
            }
        });
    }
}