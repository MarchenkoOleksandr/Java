package homework4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Message msg = new Message();
        DatagramSocket ds = new DatagramSocket();

        System.out.print("Введите своё имя: ");
        msg.setClientName(sc.nextLine());

        while (true) {
            System.out.print("Введите сообщение: ");
            msg.setMessage(sc.nextLine());

            ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(msg);

            DatagramPacket DpSend = new DatagramPacket(bos.toByteArray(), bos.size(),
                    InetAddress.getLocalHost(), 1234);
            ds.send(DpSend);

            if (msg.getMessage().equals("exit"))
                break;
        }
    }
}
