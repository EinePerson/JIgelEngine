package de.igelstudios.jigelengine.client;

import de.igelstudios.jigelengine.client.keys.HIDInput;
import de.igelstudios.jigelengine.client.rendering.Renderer;
import de.igelstudios.jigelengine.client.rendering.data.ModelCache;
import de.igelstudios.jigelengine.common.engine.Engine;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


/**
 * This class has to be initialised before anything else should be done on the client side
 */
public class ClientEngine extends Engine {
    private final Window window;
    private final HIDInput input;
    private final Renderer renderer;

    //private static GLFont defaultFont = null;

    /*public static GLFont getDefaultFont() {
        return defaultFont;
    }*/

    int fps = 0;

    /**
     * This creates a new Client Engine <br> Every parameter can be null until this is {@link ClientEngine#start()}
     * @param window the {@link Window} of this engine
     * @param input the {@link HIDInput} of this engine
     */
    public ClientEngine(Window window, HIDInput input, Renderer renderer, int tps){
        super(tps);
        this.window = window;
        this.input = input;
        this.renderer = renderer;
        GL11.glClearColor(1.0f,1.0f,1.0f,1.0f);
        //scene = new ClientScene();
        //addTickable(Renderer.get().getTextBatch());
        //if(defaultFont == null) defaultFont = new GLFont("calibri");
    }

    @Override
    public boolean shouldRun() {
        return !window.shouldClose();
    }

    @Override
    public void tick() {
        input.invokeContinuous();
    }

    @Override
    public void loop() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT  | GL11.GL_DEPTH_BUFFER_BIT);
        renderer.render();
        GLFW.glfwSwapBuffers(window.getWindow());
        window.pollEvents();
        fps++;
    }

    @Override
    public void second() {
        Client.LOGGER.info("FPS:" + fps);
        fps = 0;
    }

    @Override
    public void stopSub() {
        //this.initializer.onEnd();
        this.window.close();
        this.input.close();
        ModelCache.delete();
    }

    public int getFPS() {
        return fps;
    }
}
