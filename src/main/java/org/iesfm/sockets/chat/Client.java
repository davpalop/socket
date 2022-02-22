package org.iesfm.sockets.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static Socket socket;
    private static String host = "localhost";
    private static int port = 4000;
    private static Scanner scanner = new Scanner(System.in);
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public void writeChat() {
        while (true) {
            System.out.print("-");
            sendChat(scanner.nextLine());
        }
    }

    public void sendChat(String s) {
        try {
            dataOutputStream.writeUTF(s);
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("IOException on enviar");
        }
    }

    public void showChat() {
        String st = "";
        try {
            do {
                st = (String) dataInputStream.readUTF();
                System.out.println(st);
                System.out.print("+");
            } while (!st.equals("finish"));
        } catch (IOException e) {

        }
    }


    public void executeConection() {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(host, port);
                    System.out.println("Conectado");
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.flush();
                    showChat();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.exit(0);
                }
            }
        });
        hilo.start();
    }

    public static void main(String[] argumentos) {
        Client client = new Client();
        client.executeConection();
        client.writeChat();
    }

}
