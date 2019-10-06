package main;

import client.Client;
import server.Server;

import java.util.Scanner;

/**
 * ИНСТРУКЦИЯ: запускаем только этот класс (классы Server и Client будут запускаться отсюда)
 * Сервер можно запустить только вначале и только один раз!
 */
public class Main {

    /**
     * Спрашивает пользователя о режиме работы (сервер или клиент) и
     * передаёт управление соответствующему классу
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Запустить программу в режиме сервера или клиента? (S(erver) / C(lient))");
        while (true) {
            char answer = Character.toLowerCase(in.nextLine().charAt(0));
            if (answer == 's') {
                new Server();
                break;
            } else if (answer == 'c') {
                new Client();
                break;
            } else {
                System.out.println("Некорректный ввод. Введите букву 's' или 'c'");
            }
        }
    }
}