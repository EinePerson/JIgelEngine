package de.igelstudios.jigelengine.common.init;

import de.igelstudios.jigelengine.client.keys.*;

import java.util.*;

/**
 * This method is used for {@link ClientInitializer} to register wanted Key Bindings
 */
public class KeyInitializer {
    private final Map<Integer, String> keys = new HashMap<>();

    private final List<KeyListener> keyListeners = new ArrayList<>();

    private final List<MouseMoveListener> moveListeners = new ArrayList<>();

    private final List<MouseDragListener> dragListeners = new ArrayList<>();
    private final Map<String,List<SingleKeyListener>> singleListeners = new HashMap<>();
    private final Map<String,List<SingleContinuousKeyListener>> singleContListener = new HashMap<>();

    /**
     * Adds a Listener which can only contain one event for when a Key is pressed
     * @param key the specific key which notifies the Listener
     * @param listener the Listener to be notified
     */
    public void add(String key,SingleKeyListener listener){
        if(!singleListeners.containsKey(key))singleListeners.put(key,new ArrayList<>());
        singleListeners.get(key).add(listener);
    }

    /**
     * Adds a Listener which can only contain one event for while a Key is held down
     * @param key the specific key which notifies the Listener
     * @param listener the Listener to be notified
     */
    public void add(String key,SingleContinuousKeyListener listener){
        if(!singleContListener.containsKey(key))singleContListener.put(key,new ArrayList<>());
        singleContListener.get(key).add(listener);
    }

    /**
     * This adds a Key to be registered
     * @param key The default Key in ASCII/GLFW number (obtainable with GLFW.GLFW_KEY_ and then the wanted key)
     * @param name the name under which this key is referenced
     * @see HIDInput#registerKey(int, String)
     */
    public void add(int key, String name) {
        this.keys.put(Integer.valueOf(key), name);
    }

    /**
     * Adds a specific class to listen for key events
     * @param listener The listener to be notified
     * @see HIDInput#registerKeyListener(KeyListener)
     */
    public void add(KeyListener listener) {
        this.keyListeners.add(listener);
    }

    /**
     * Adds a specific class to Listen for Mouse Movement
     * @param listener the Listener to be notified
     */
    public void add(MouseMoveListener listener) {
        this.moveListeners.add(listener);
    }

    /**
     * Adds a Listener to be notified when the mouse is dragged while a specific key is Held
     * @param listener the listener to be notified
     */
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
