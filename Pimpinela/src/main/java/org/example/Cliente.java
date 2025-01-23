package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    /**
     *Método principal que gestiona la conexión con el servidor y permite enviar mensajes.
     * @param args
     * @throws IOException
     * @throws UnknownHostException
     *
     */
    public static void main(String[] args) {

        String host = "127.0.0.1";
        int puerto = 5000;
        DataInputStream in;
        DataOutputStream out;

        try {
            Socket socket = new Socket(host, puerto);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            boolean finalizar = false;
            while (!finalizar) {
                System.out.print("Escribe un mensaje: ");
                String mensaje = scanner.nextLine();
                out.writeUTF(mensaje);

                String respuesta = in.readUTF();
                System.out.println("Servidor: " + respuesta);

                if (respuesta.contains("pega la vuelta")) {
                    System.out.println("El servidor ha cerrado la conexión.");
                    finalizar = true;
                }
            }
            socket.close();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
