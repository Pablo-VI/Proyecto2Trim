package com.example.proyecto2trim;

import android.content.pm.ActivityInfo;
import android.database.Observable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto2trim.databinding.ActivityGameBinding;

public class game extends AppCompatActivity {

    private String[] nombresJugadores; // Array con los nombres de los jugadores
    private int[] posicionesFinales;  // Posiciones finales de los jugadores
    private TextView[] etiquetasNombres; // Etiquetas con los nombres de los jugadores

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_game);
        ActivityGameBinding binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Forzar orientación horizontal
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Instanciar elementos
        TextView nombreJugador1 = binding.jugador1;
        TextView nombreJugador2 = binding.jugador2;
        TextView nombreJugador3 = binding.jugador3;
        TextView nombreJugador4 = binding.jugador4;
        TextView nombreJugador5 = binding.jugador5;
        TextView nombreJugador6 = binding.jugador6;

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Establece los nombres de los jugadores en las etiquetas.
     * @param nombres Cadena de nombres separados por comas.
     */
    public void setNombresJugadores(String nombres) {
        this.nombresJugadores = nombres.split("\\s*,\\s*");
        for (int i = 0; i < nombresJugadores.length && i < etiquetasNombres.length; i++) {
            etiquetasNombres[i].setText(nombresJugadores[i].trim());
        }
    }

    /**
     * Actualiza los avances de los jugadores en el tablero.
     * @param avances Array de avances.
     */
    public void avance(int[] avances)
    {
        for (int i = 0; i < 6; i++)
        {
            int val = Math.min(avances[i], 35);;
        }
    }

    /**
     * Establece las posiciones finales de los camellos.
     * @param posiciones Array con las posiciones finales.
     */
    public void setPosicionesFinales(int[] posiciones) {
        this.posicionesFinales = posiciones;
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

    /*public void update (Observable o, Object arg)
    {
        if (arg instanceof Client) {
            Client jugador = (Client) arg;

            runOnUiThread(() -> {
                this.txtDiesel.setText(jugador.getDiesel() + "");
                this.txtGasolinaPlomo.setText(jugador.getGasolinaPlomo() + "");
                this.txtDieselOptima.setText(jugador.getDieselOptima() + "");
            });
    }*/
}