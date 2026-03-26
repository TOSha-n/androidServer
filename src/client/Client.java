package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9123);
            Scanner scanner = new Scanner(System.in);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream is = new DataInputStream(socket.getInputStream());

            Thread thread = new Thread(() -> {
                while (true){
                    System.out.println("Введите сообщение");
                    String userMsg = scanner.nextLine();
                    try {
                        out.writeUTF(userMsg);
                    } catch (IOException e) {
                        System.out.println("Соединение потеряно");
                    }
                }
            });
            thread.start();

            while (true) {
                String response = is.readUTF();
                System.out.println(response);
            }

        } catch (IOException e) {
            System.out.println("Невозможно подключиться к серверу");
            throw new RuntimeException(e);
        }
    }
}
