package com.example.proyecto2trim;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private final ArrayList<Socket> clientes;

    private int puerto;

    public Server(int puerto) {
        this.puerto = puerto;
        this.clientes = new ArrayList();
    }

    @Override
    public void run() {

        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;

        try {
            //Creamos el socket del servidor
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            //Siempre estara escuchando peticiones
            while (true) {

                //Espero a que un cliente se conecte
                sc = servidor.accept();

                System.out.println("Cliente conectado desde " + sc.getInetAddress());

                synchronized (clientes)
                {
                    clientes.add(sc);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void enviarInfo(Gasolinera g) {

        synchronized (clientes)
        {
            for (Socket sock : clientes)
            {

                try
                {
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(g);
                    oos.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}