package homework4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * ИНСТРУКЦИЯ:
 * Сначала запускаем сервер, а потом клиентов
 */

public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(1234);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        byte[] buffer = new byte[65535];
        System.out.println("Сервер запущен ...");

        while (true) {
            ds.receive(new DatagramPacket(buffer, buffer.length));
            ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(buffer));

            try {
                Message msg = (Message) is.readObject();

                if (msg.getMessage().equals("exit")) {
                    System.out.println(msg.getClientName() + " вышел(ла)!");
                    break;
                }
                System.out.println(msg.getClientName() + " [" + LocalTime.now().format(formatter) + "]: " +
                        msg.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            buffer = new byte[65535];
        }
    }
}
