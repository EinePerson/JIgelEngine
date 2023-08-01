package de.igelstudios.jigelengine.client;

import de.igelstudios.jigelengine.client.keys.HIDInput;
import de.igelstudios.jigelengine.client.rendering.Renderer;
import de.igelstudios.jigelengine.common.engine.EngineNotifier;
import de.igelstudios.jigelengine.common.init.ClientInitializer;
import de.igelstudios.jigelengine.common.io.EngineSettings;
import de.igelstudios.jigelengine.common.scene.Camera;
import de.igelstudios.jigelengine.common.scene.Scene;
import de.igelstudios.jigelengine.common.util.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Client implements EngineNotifier {

    public static final Logger LOGGER = LoggerFactory.getLogger(EngineSettings.parser("info.json").read().getName() + ":Client_Main");
    private static Client instance;
    private Window window;
    private ClientEngine engine;
    private HIDInput input;
    private ClientInitializer initializer;

    private Renderer renderer;
    private EngineSettings defaultSet;
    private Scene scene;
    private Camera camera;
    public Client() {
        defaultSet = EngineSettings.parser("info.json").read();
        try {
            this.initializer = (ClientInitializer) Class.forName(defaultSet.getMain()).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (ClassNotFoundException | java.lang.reflect.InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The Main class specified in info.json has to implement EngineInitializer");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("There has to be a class set as main in info.json");
        }
        initializer.init();
        window = new Window(defaultSet.getName());
        input = new HIDInput(initializer);
        input.registerGLFWListeners(window.getWindow());
        scene = new Scene();
        camera = new Camera();
        renderer = new Renderer(scene,camera);
        renderer.resize(window.getWidth(), window.getHeight());
        engine = new ClientEngine(window, input, renderer, defaultSet.getTPS());
        engine.addNotifiable(this);
        scene.init();
        camera.init();
        instance = this;
    }

    public void init(){
        initializer.onInitialize();
    }

    public void start(){
        engine.start();
    }

    public Window getWindow() {
        return window;
    }

    @Override
    public void second() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {
        renderer.delete();
        this.initializer.onEnd();
    }

    public Scene getScene() {
        return scene;
    }

    public static Client get() {
        return instance;
    }

    public Camera getCamera() {
        return camera;
    }

    public ClientEngine getEngine() {
        return engine;
    }
}
