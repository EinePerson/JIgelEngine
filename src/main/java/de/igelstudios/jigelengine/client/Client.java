package de.igelstudios.jigelengine.client;

import de.igelstudios.jigelengine.client.keys.HIDInput;
import de.igelstudios.jigelengine.client.rendering.Renderer;
import de.igelstudios.jigelengine.common.engine.EngineNotifier;
import de.igelstudios.jigelengine.common.init.ClientInitializer;
import de.igelstudios.jigelengine.common.io.EngineSettings;

public class Client implements EngineNotifier {
    private Window window;
    private ClientEngine engine;
    private HIDInput input;
    private ClientInitializer initializer;
    private EngineSettings defaultSet;
    private Renderer renderer;

    public Client(){
        defaultSet = EngineSettings.parser("info.json").read();
        try {
            this.initializer = (ClientInitializer) Class.forName(defaultSet.getMain()).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (ClassNotFoundException|java.lang.reflect.InvocationTargetException|InstantiationException|IllegalAccessException|NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The Main class specified in info.json has to implement EngineInitializer");
        }catch (NullPointerException e){
            throw new IllegalArgumentException("There has to be a class set as main in info.json");
        }
        initializer.init();
        window = new Window(defaultSet.getName());
        input = new HIDInput(initializer);
        input.registerGLFWListeners(window.getWindow());
        renderer = new Renderer();
        engine = new ClientEngine(window,input,renderer,defaultSet.getTPS());
        engine.addNotifiable(this);
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
        this.initializer.onEnd();
    }
}
