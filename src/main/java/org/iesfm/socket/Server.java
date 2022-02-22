package org.iesfm.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private static ServerSocket server;
    private static Socket socket;
    private static int port = 4000;
    private static Scanner scanner = new Scanner(System.in);
    private static File file;


    public static void main(String[] args) {

        try {
            server = new ServerSocket(port);
            System.out.println("Esperando conexión");
            socket = server.accept();
            System.out.println("Conectado, especifíque la ruta");
            askPath();
            if (!file.exists()) {
                System.out.println("ERROR");
                System.exit(0);
            } else {
                System.out.println("Perfecto empezamos a copiar!");
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            writer.append(line).append("\n");
                        }
                        writer.append(":end");
                        System.out.println("Copiado perfectamente, cerrando servidor");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File askPath() {
        String path = scanner.nextLine();
        return file = new File(path);
    }

}
