package org.iesfm.sockets.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static int port = 4000;
    private static Scanner scanner = new Scanner(System.in);
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;


    public void showChat() {
        String st = "";
        try {
            do {
                st = (String) dataInputStream.readUTF();
                System.out.println(st);
                System.out.print("-");
            } while (!st.equals("finish"));
        } catch (IOException e) {
            System.exit(0);
        }
    }

    public void sendChat(String s) {
        try {
            dataOutputStream.writeUTF(s);
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Error en enviar(): " + e.getMessage());
        }
    }

    public void writeChat() {
        while (true) {
            System.out.print("+");
            sendChat(scanner.nextLine());
        }
    }

    public void executeConection(int puerto) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        serverSocket = new ServerSocket(port);
                        System.out.println("Esperando conexión");
                        socket = serverSocket.accept();
                        System.out.println("Conectado");
                        dataInputStream = new DataInputStream(socket.getInputStream());
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.flush();
                        showChat();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        System.exit(0);
                    }
                }
            }
        });
        hilo.start();
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.executeConection(port);
        s.writeChat();
    }

}
