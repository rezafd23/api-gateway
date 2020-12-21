package com.gateway.database;

import com.gateway.database.rabbitmq.DatabaseReceiver;

public class DatabaseMain {
    private static DatabaseReceiver receiver = new DatabaseReceiver();

    public static void main(String[] args) {

        try {
            System.out.println(" [*] Waiting for messages..");
            receiver.register();
            receiver.user();
        } catch (Exception e) {
            System.out.println("Error DatabaseMain = " + e);
        }
    }
}
