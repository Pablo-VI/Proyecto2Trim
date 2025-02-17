package com.example.proyecto2trim;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable, Parcelable, Serializable {
    private static final long serialVersionUID = 1L;

    private boolean fin;
    private String name;
    private String color;
    private Table position; // La casilla donde está el jugador

    /**
     * Constructor para inicializar el cliente.
     *
     * @param name El nombre del jugador.
     * @param color El color del jugador.
     * @param position La posición en la tabla del jugador.
     */
    public Client(String name, String color, Table position)
    {
        this.name = name;
        this.color = color;
        this.position = position;
        this.fin = false;
    }


    // Constructor para Parcel (deserialización)
    protected Client(Parcel in) {
        name = in.readString();
        color = in.readString();
        position = in.readParcelable(Table.class.getClassLoader()); // Leer Table como Parcelable
    }

    // Crear un objeto Client desde un Parcel
    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Table getPosition() {
        return position;
    }

    public void setPosition(Table position) {
        this.position = position;
    }

    // Método para mover al jugador
    public void moveTo(Table newPosition) {
        this.position = newPosition;
    }

    // Métodos de Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(color);
        parcel.writeParcelable(position, flags);
    }

    @Override
    public void run()
    {
        try
        {
            // Conexión al servidor (en el localhost y puerto 5555)
            Socket socket = new Socket(InetAddress.getLocalHost(), 5555);
            System.out.println("[" + name + "] Conectado al servidor en puerto local: " + socket.getLocalPort());

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // 1) Enviar el nombre del jugador al servidor
            out.writeUTF(name);
            out.flush();

            // 2) Recibir confirmación de aceptación
            String respuesta = in.readUTF();
            if ("aceptado".equals(respuesta)) {
                System.out.println("[" + name + "] El servidor le ha aceptado en la partida.");
            }

            // 3) Recibir la lista de jugadores
            String listaJugadores = in.readUTF();
            System.out.println("[" + name + "] Lista de jugadores: " + listaJugadores);

            // Se muestra la ventanaGame gráfica de la partida (**Revisar funcionamiento**)
            game ventanaGame = new game();
            ventanaGame.setNombresJugadores(listaJugadores);
            ventanaGame.setVisible(true);

            //Se instancia la ventanaDado para llamar metodos de la clase tirar_dado.java
            tirar_dado ventanaDado = new tirar_dado();

            // 4) Bucle principal de comunicación con el servidor
            while (!fin)
            {
                // Se lee el primer entero enviado por el servidor
                int codigo = in.readInt();

                if (codigo == -2)
                {
                    // Código -2: El servidor indica "¡Es tu turno, lanza el dado!"
                    System.out.println("[" + name + "] Es mi turno. Lanza el dado.");
                    // Se espera la tirada en la ventanaGame (método bloqueante)
                    //HAcer codigo para llamar a tirar_dado.java y pasar los datos de la tirada
                    int dado = ventanaDado.esperarTirada();
                    // Se envía el valor del dado al servidor
                    out.writeInt(dado);
                    out.flush();
                }
                else if (codigo == 0 || codigo > 0)
                {
                    // En este caso se reciben los avances de los jugadores.
                    // El servidor envía 4 enteros que representan los avances,
                    // y luego un código de control (0 = carrera en curso).
                    int[] avances = new int[4];
                    avances[0] = codigo; // El primer valor ya leído
                    avances[1] = in.readInt();
                    avances[2] = in.readInt();
                    avances[3] = in.readInt();
                    int control = in.readInt(); // control = 0
                    // Se actualiza la interfaz gráfica con los avances
                    ventanaGame.avance(avances);
                }
                else if (codigo == -1)
                {
                    // Código -1: Fin de la carrera.
                    // Se reciben las posiciones finales de los camellos.
                    int[] posiciones = new int[4];
                    posiciones[0] = in.readInt();
                    posiciones[1] = in.readInt();
                    posiciones[2] = in.readInt();
                    posiciones[3] = in.readInt();
                    ventanaGame.setPosicionesFinales(posiciones);
                    fin = true;
                }
            }

            socket.close();
            System.out.println("[" + name + "] Cliente finalizado.");

        } catch (Exception e) {
            System.out.println("Error en cliente " + name + ": " + e);
        }
    }

    /**
     * Método para iniciar la conexión al servidor.

    @Override
    public void run() {
        final String HOST = "127.0.0.1"; // Dirección IP del servidor (localhost)
        try {
            Socket sc = new Socket(HOST, 5555);
            ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
            // Bucle infinito para recibir actualizaciones del servidor
            while (true)
            {
                Client jugador = (Client) ois.readObject();
                support.firePropertyChange("jugador", null, jugador);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }*/
}
