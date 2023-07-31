package de.igelstudios.jigelengine.common.io;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class EngineSettings {
    private String main;
    private String server_main;

    private String name;

    private String build;
    private String player;
    private String server_error_handler;
    private String server_event_listener;
    private String client_event_listener;
    private String default_lang;
    private String tps;
    private int iTPS = -1;

    public static EngineSettingsParser parser(String fileName) {
        return new EngineSettingsParser(fileName);
    }

    public String getBuild() {
        return this.build;
    }

    public String getName() {
        return this.name;
    }

    public String getMain() {
        return this.main;
    }

    public int getTPS(){
        if(iTPS == -1) {
            if(tps.equals(""))throw new IllegalArgumentException("There is no value set as tps (Ticks per Second");
            try {
                iTPS = Integer.parseInt(tps);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("The value at tps in info.json has to be a valid number");
            }
        }
        return iTPS;
    }

    public String getPlayer() {
        return player;
    }

    public String getServerMain() {
        return server_main;
    }

    public String getServerErrorHandler() {
        return server_error_handler;
    }

    public String getClientEventListener() {
        return client_event_listener;
    }

    public String getServerEventListener() {
        return server_event_listener;
    }

    public String getDefaultLang() {
        return default_lang;
    }

    public static class EngineSettingsParser {
        private String fileName;

        public EngineSettingsParser(String fileName) {
            this.fileName = fileName;
        }

        public EngineSettings read() {
            EngineSettings settings;
            try {
                settings = new EngineSettings();
                JsonReader reader = (new Gson()).newJsonReader(new InputStreamReader(Objects.<InputStream>requireNonNull(EngineSettings.class.getClassLoader().getResourceAsStream(this.fileName))));
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    EngineSettings.class.getDeclaredField(name).set(settings, reader.nextString());
                }
                reader.endObject();
            } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return settings;
        }
    }
}
