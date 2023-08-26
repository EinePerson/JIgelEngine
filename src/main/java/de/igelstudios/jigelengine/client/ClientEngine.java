package de.igelstudios.jigelengine.client;

import de.igelstudios.jigelengine.client.keys.HIDInput;
import de.igelstudios.jigelengine.client.rendering.ObjectRenderer;
import de.igelstudios.jigelengine.client.rendering.data.RenderCache;
import de.igelstudios.jigelengine.client.rendering.renderers.Renderer;
import de.igelstudios.jigelengine.common.engine.Engine;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


/**
 * This class has to be initialised before anything else should be done on the client side
 */
public class ClientEngine extends Engine {
    private final Window window;
    private final HIDInput input;
    private final Renderer[] renderers;

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
    public ClientEngine(Window window, HIDInput input, int tps, Renderer ... renderers){
        super(tps);
        this.window = window;
        this.input = input;
        this.renderers = renderers;
        renderers[renderers.length - 1].mark();
        GL11.glClearColor(1.0f,1.0f,1.0f,1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //scene = new ClientScene();
        //addTickable(ObjectRenderer.get().getTextBatch());
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
        for (Renderer renderer : renderers) {
            renderer.render();
        }
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
        RenderCache.delete();
    }

    public int getFPS() {
        return fps;
    }
}
