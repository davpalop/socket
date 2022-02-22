package org.iesfm.socket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static Socket socket;
    private static String host = "localhost";
    private static int port = 4000;
    private static File file;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            socket = new Socket(host, port);
            System.out.println("Conexión establecida, especifique la ruta");
            askPath();
            if (!file.exists()) {
                System.out.println("Creado, esperando a que copien el contenido");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            writer.append(line).append("\n");
                        }
                        System.out.println("Contenido copiado");
                        if ((reader.readLine()) == ":end") {
                            System.exit(0);
                        }
                    }
                }
            } else {
                System.out.println("ERROR");
                System.exit(0);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File askPath() {
        String path = scanner.nextLine();
        return file = new File(path);
    }
}


