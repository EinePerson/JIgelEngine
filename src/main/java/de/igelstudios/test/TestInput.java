package de.igelstudios.test;

import de.igelstudios.jigelengine.client.Client;
import de.igelstudios.jigelengine.client.keys.KeyHandler;
import de.igelstudios.jigelengine.client.keys.KeyListener;
import de.igelstudios.jigelengine.client.keys.MouseMoveListener;
import de.igelstudios.jigelengine.common.scene.Camera;
import de.igelstudios.jigelengine.common.util.Test;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

@Test
public class TestInput implements KeyListener, MouseMoveListener {

    @KeyHandler("W")
    public void w(){
        Camera cam = Client.get().getCamera();
        cam.getViewMatrix().positiveZ(cam.getDirection()).negate().mul(1.0f);
        cam.getPosition().add(new Vector3f(cam.getDirection().x,0.0f,cam.getDirection().z));
        cam.recalculate();
        //Client.get().getCamera().moveForward(1f);
    }

    @KeyHandler("A")
    public void a(){
        Client.get().getCamera().moveLeft(1f);
    }

    @KeyHandler("S")
    public void s(){
        Client.get().getCamera().moveBack(1f);
    }

    @KeyHandler("D")
    public void d(){
        Client.get().getCamera().moveRight(1f);
    }

    @Override
    public void mouseMove(double x, double y) {
        Client.get().getCamera().addRotation((float) Math.toRadians((y - Client.get().getWindow().getHeight() / 2.0f) * 0.1f), (float) Math.toRadians((x - Client.get().getWindow().getWidth() / 2.0f) * 0.1f));
        GLFW.glfwSetInputMode(Client.get().getWindow().getWindow(),GLFW.GLFW_CURSOR,GLFW.GLFW_CURSOR_DISABLED);
        GLFW.glfwSetCursorPos(Client.get().getWindow().getWindow(), Client.get().getWindow().getWidth() / 2.0f, Client.get().getWindow().getHeight() / 2.0f);
    }
}
