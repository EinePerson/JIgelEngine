package de.igelstudios;

import de.igelstudios.jigelengine.client.Client;

public class EngineClient {
    private static Client client;

    public static void main(String[] args) {
        EngineClient.client = new Client();
        client.start();
    }

    public static Client getClient() {
        return client;
    }
}