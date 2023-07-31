package de.igelstudios.jigelengine.common.init;

import de.igelstudios.jigelengine.client.keys.*;

import java.util.*;

public class KeyInitializer {
    private Map<Integer, String> keys = new HashMap<>();

    private List<KeyListener> keyListeners = new ArrayList<>();

    private List<MouseMoveListener> moveListeners = new ArrayList<>();

    private List<MouseDragListener> dragListeners = new ArrayList<>();
    private Map<String,List<SingleKeyListener>> singleListeners = new HashMap<>();
    private Map<String,List<SingleContinuousKeyListener>> singleContListener = new HashMap<>();

    public void add(String key,SingleKeyListener listener){
        if(!singleListeners.containsKey(key))singleListeners.put(key,new ArrayList<>());
        singleListeners.get(key).add(listener);
    }

    public void add(String key,SingleContinuousKeyListener listener){
        if(!singleContListener.containsKey(key))singleContListener.put(key,new ArrayList<>());
        singleContListener.get(key).add(listener);
    }

    public void add(int key, String name) {
        this.keys.put(Integer.valueOf(key), name);
    }

    public void add(KeyListener listener) {
        this.keyListeners.add(listener);
    }

    public void add(MouseMoveListener listener) {
        this.moveListeners.add(listener);
    }

    public void add(MouseDragListener listener) {
        this.dragListeners.add(listener);
    }

    public void register(HIDInput input) {
        Objects.requireNonNull(input);
        this.keys.forEach(input::registerKey);
        Objects.requireNonNull(input);
        this.keyListeners.forEach(input::registerKeyListener);
        Objects.requireNonNull(input);
        this.moveListeners.forEach(input::registerMoveListener);
        Objects.requireNonNull(input);
        this.dragListeners.forEach(input::registerDragListener);
    }
}
