package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
    /**
     * Método principal que inicia el servidor y gestiona las interacciones con el cliente.
     * @param args
     * @throws IOException
     *
     */
    public static void main(String[] args) {
        ServerSocket servidor = null;
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;

        int puerto = 5000;

        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            while (true) {
                socket = servidor.accept();
                System.out.println("Cliente conectado");
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                boolean finalizar = false;
                int estado = 0; // Controla el flujo de mensajes

                while (!finalizar) {
                    String mensaje = in.readUTF();
                    System.out.println("Cliente: " + mensaje);

                    switch (estado) {
                        case 0:
                            if (mensaje.equals("¿Quién es?")) {
                                out.writeUTF("Soy yo");
                                estado = 1; // Avanzamos al siguiente estado
                            } else {
                                out.writeUTF("Error");
                            }
                            break;

                        case 1:
                            if (mensaje.equals("¿Qué vienes a buscar?")) {
                                out.writeUTF("A ti");
                                estado = 2; // Avanzamos al siguiente estado
                            } else {
                                out.writeUTF("Error");
                            }
                            break;

                        case 2:
                            if (mensaje.equals("Ya es tarde")) {
                                out.writeUTF("¿Por qué?");
                                estado = 3; // Avanzamos al siguiente estado
                            } else {
                                out.writeUTF("Error");
                            }
                            break;

                        case 3:
                            if (mensaje.equals("Porque ahora soy yo la que quiere estar sin ti")) {
                                out.writeUTF("Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta");
                                finalizar = true; // Terminamos la interacción
                            } else {
                                out.writeUTF("Error");
                            }
                            break;

                        default:
                            out.writeUTF("Error");
                            break;
                    }
                }

                System.out.println("Cliente desconectado");
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
