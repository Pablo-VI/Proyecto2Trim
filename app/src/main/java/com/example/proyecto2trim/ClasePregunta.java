// Pregunta.java
package com.example.proyecto2trim;

import java.util.List;

public class ClasePregunta {
    private int id;
    private String pregunta;
    private List<String> opciones;  // Opciones de respuesta
    private String respuestaCorrecta;  // Respuesta correcta

    // Constructor
    public ClasePregunta(int id, String pregunta, List<String> opciones, String respuestaCorrecta) {
        this.id = id;
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getClasePregunta() {
        return pregunta;
    }

    public List<String> getClaseOpciones() {
        return opciones;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
}
