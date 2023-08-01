package de.igelstudios.jigelengine.client.gui;

import de.igelstudios.jigelengine.client.keys.HIDInput;
import de.igelstudios.jigelengine.client.keys.KeyHandler;
import de.igelstudios.jigelengine.client.keys.KeyListener;
import de.igelstudios.jigelengine.client.keys.MouseMoveListener;

import static org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT;

public class GUIManager implements KeyListener, MouseMoveListener {
    private static GUIManager instance;
    private double x,y;
    private int selText;
    private GUI gui;

    private GUIManager(){
        instance = this;
    }

    @KeyHandler("LMB")
    public void lmb(boolean pressed){
        if(!pressed)return;
        if(gui == null)return;
        /*gui.getButtons().forEach(button -> {
            if(button.getPosition().x <= x && button.getPosition().x + button.getSize().x > x && button.getPosition().y <= y && button.getPosition().y + button.getSize().y > y){
                button.invoke(MouseButton.LMB);
            }
        });
        if(gui == null)return;
        boolean set = false;
        for (int i = 0; i < gui.getTextFields().size(); i++) {
            TextField field = gui.getTextFields().get(i);
            if(field.getPosition().x <= x && field.getPosition().x + field.getSize().x > x && field.getPosition().y <= y && field.getPosition().y + field.getSize().y > y){
                selText = i;
                set = true;
                break;
            }
        }*/
        //if(!set)selText = -1;
    }

    @KeyHandler("RMB")
    public void rmb(boolean pressed){
        /*if(!pressed)return;
        if(gui == null)return;
        gui.getButtons().forEach(button -> {
            if(button.getPosition().x <= x && button.getPosition().x + button.getSize().x > x && button.getPosition().y <= y && button.getPosition().y + button.getSize().y > y){
                button.invoke(MouseButton.RMB);
            }
        });*/
    }

    @KeyHandler("MMB")
    public void mmb(boolean pressed){
        /*if(!pressed)return;
        if(gui == null)return;
        gui.getButtons().forEach(button -> {
            if(button.getPosition().x <= x && button.getPosition().x + button.getSize().x > x && button.getPosition().y <= y && button.getPosition().y + button.getSize().y > y){
                button.invoke(MouseButton.MMB);
            }
        });*/
    }

    @Override
    public void mouseMove(double x, double y) {
        this.x = x;
        this.y = y;
    }



    public static void setGUI(GUI gui){
        instance.setGui(gui);
    }

    public void addText(int c){
        /*if(gui == null)return;
        if(selText != -1){
            if(c == 259 && gui.getTextFields().get(selText).getLength() > 0)gui.getTextFields().get(selText).remove();
            if(c >= 46 && c <= 122) gui.getTextFields().get(selText).add((char) c);
        }*/
    }

    public boolean hasSelText(){
        return selText != -1;
    }

    private void setGui(GUI gui) {
        selText = -1;
        if(this.gui != null) removeGUI();
        this.gui = gui;
    }

    public static void removeGui(){
        if(instance.gui != null)instance.removeGUI();
    }

    private void removeGUI(){
        /*gui.getTextFields().forEach(textfield -> textfield.getText().setLifeTime(0));
        gui.getTexts().forEach(text -> text.setLifeTime(0));
        gui.getObjects().forEach(SceneObject::remove);
        this.gui = null;*/
    }

    public static boolean handle(int mods,int key) {
        if (instance.hasSelText()) {
            int keyS = key;
            if ((mods & GLFW_MOD_SHIFT) == 0) {
                keyS = Character.toLowerCase(key);
            } else if (key == 47) keyS = 95;
            else if (key == 46) keyS = 58;
            instance.addText(keyS);
            return true;
        }
        return false;
    }

    public static void register(HIDInput input){
        new GUIManager();
        input.registerKeyListener(instance);
        input.registerMoveListener(instance);
    }
}
