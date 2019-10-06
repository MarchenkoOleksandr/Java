package server;

import main.Const;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private final List<Connection> connections = Collections.synchronizedList(new ArrayList<>());
    private ServerSocket server = null;

    public Server() {
        try {
            server = new ServerSocket(Const.Port);
            System.out.println("Сервер запущен ...");

            while (true) {
                Socket socket = server.accept();
                Connection con = new Connection(socket);
                connections.add(con);
                con.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    private void closeAll() {
        try {
            server.close();

            synchronized (connections) {
                for (Connection connection : connections) {
                    connection.close();
                }
            }
        } catch (Exception e) {
            System.err.println("Потоки не были закрыты!");
        }
    }

    private class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;

        private String name = "";
        DateTimeFormatter formatter;

        Connection(Socket socket) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            this.socket = socket;

            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        @Override
        public void run() {
            try {
                name = in.readLine();
                // Отправляем всем клиентам сообщение о том, что зашёл новый пользователь
                synchronized (connections) {
                    for (Connection connection : connections) {
                        connection.out.println("Вошёл(ла) " + name);
                    }
                }

                String str;
                while (true) {
                    str = in.readLine();
                    if (str.equals("exit"))
                        break;

                    synchronized (connections) {
                        for (Connection connection : connections) {
                            connection.out.println(name + " [" + LocalTime.now().format(formatter) + "]: " + str);
                        }
                    }
                }

                synchronized (connections) {
                    for (Connection connection : connections) {
                        connection.out.println(name + " вышел(ла)");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        void close() {
            try {
                in.close();
                out.close();
                socket.close();
                connections.remove(this);

                if (connections.size() == 0) {
                    Server.this.closeAll();
                    System.exit(0);
                }
            } catch (Exception e) {
                System.err.println("Потоки не были закрыты!");
            }
        }
    }
}