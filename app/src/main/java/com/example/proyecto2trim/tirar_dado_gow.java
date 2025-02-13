package com.example.proyecto2trim;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class tirar_dado_gow extends AppCompatActivity {

    private ImageView imageView;
    private ImageView btnChangeImage;
    private int index = 0; // Índice para rastrear la imagen actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tirar_dado_gow);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.dado);
        btnChangeImage = findViewById(R.id.button_lanzar);

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int numeroAleatorio = rand.nextInt(6) ;

                switch (numeroAleatorio) {
                    case 0:
                        imageView.setImageResource(R.drawable.dado1_gow);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.dado2_gow);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.dado3_gow);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.dado4_gow);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.dado5_gow);
                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.dado6_gow);
                        break;
                }
            }

        });
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