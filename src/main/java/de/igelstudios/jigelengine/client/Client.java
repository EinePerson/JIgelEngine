package de.igelstudios.jigelengine.client;

import de.igelstudios.jigelengine.client.keys.HIDInput;
import de.igelstudios.jigelengine.client.rendering.ObjectRenderer;
import de.igelstudios.jigelengine.client.rendering.data.DataLoader;
import de.igelstudios.jigelengine.client.rendering.shader.Shader;
import de.igelstudios.jigelengine.client.rendering.shader.ShaderData;
import de.igelstudios.jigelengine.client.rendering.terrain.Terrain;
import de.igelstudios.jigelengine.client.rendering.terrain.TerrainRenderer;
import de.igelstudios.jigelengine.common.engine.EngineNotifier;
import de.igelstudios.jigelengine.common.init.ClientInitializer;
import de.igelstudios.jigelengine.common.io.EngineSettings;
import de.igelstudios.jigelengine.common.scene.Camera;
import de.igelstudios.jigelengine.common.scene.Scene;
import de.igelstudios.jigelengine.common.util.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client implements EngineNotifier {

    public static final Logger LOGGER = LoggerFactory.getLogger(EngineSettings.parser("info.json").read().getName() + ":Client_Main");
    private static Client instance;
    private Window window;
    private ClientEngine engine;
    private HIDInput input;
    private ClientInitializer initializer;

    private ObjectRenderer renderer;
    private EngineSettings defaultSet;
    private Scene scene;
    private Camera camera;
    private TerrainRenderer terrainRenderer;
    @Test
    private Terrain terrain;
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
        terrain = new Terrain(0,0,DataLoader.loadTexture("blendMap.png"),DataLoader.loadTexture("grass2.png"),DataLoader.loadTexture("grassFlowers.png"),DataLoader.loadTexture("mud.png"),
                DataLoader.loadTexture("path.png"));
        System.out.println(DataLoader.loadTexture("grass.png").isTransparent());
        renderer = new ObjectRenderer(scene,camera);
        renderer.resize(window.getWidth(), window.getHeight());
        terrainRenderer = new TerrainRenderer(terrain,scene,camera);
        terrainRenderer.resize(window.getWidth(),window.getHeight());
        engine = new ClientEngine(window, input,defaultSet.getTPS(),renderer,terrainRenderer);
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
        terrainRenderer.delete();
        scene.delete();
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
