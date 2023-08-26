package de.igelstudios.jigelengine.common.scene;

import de.igelstudios.jigelengine.common.util.Initializeble;
import de.igelstudios.jigelengine.common.util.Test;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class represents a Scene, the Scene in the Client class contains every object that is rendered
 */
public class Scene implements Initializeble {
    private boolean ambDirty;
    private Vector3f ambientLig;
    @Test
    private Light light;
    private Map<String,List<SceneObject>> objs;
    private Map<String,List<SceneObject>> tobjs;
    private float fogDensity = 0.0035f;
    private float fogGradient = 5.0f;
    private Vector3f skyColor = new Vector3f(0.5f,0.5f,0.5f);
    private boolean skyDirty = true;


    /**
     * This creates the Scene and empties every value as the scene should not be created newly when the Scene is changed
     */
    @Override
    public void init(){
        objs = new HashMap<>();
        tobjs = new HashMap<>();
        ambientLig = new Vector3f();
        ambDirty = true;
    }

    public Map<String, List<SceneObject>> getObjs() {
        return objs;
    }

    public Map<String, List<SceneObject>> getTobjs() {
        return tobjs;
    }

    /**
     * Adds a specific object to the Scene which will be rendered
     * @param object The object to be added
     */
    public void addObj(SceneObject object){
        Map<String,List<SceneObject>> map = object.getModel().isTransparent()? tobjs:objs;
        if(!map.containsKey(object.getModel().getId()))map.put(object.getModel().getId(),new ArrayList<>());
        map.get(object.getModel().getId()).add(object);
    }

    /**
     * Removes a specific object from the scene which will not be rendered anymore
     * @param object the object to be removed
     */
    public void remove(SceneObject object){
        Map<String,List<SceneObject>> map = object.getModel().isTransparent()? tobjs:objs;
        if(!map.containsKey(object.getModel().getId()))return;
        map.get(object.getModel().getId()).remove(object);
        if(map.get(object.getModel().getId()).isEmpty())map.remove(object.getModel().getId());
    }

    @Test
    public Light getLight() {
        return light;
    }

    @Test
    public void setLight(Light light) {
        this.light = light;
    }

    public void setAmbientLight(Vector3f ambientLig) {
        this.ambientLig.x = ambientLig.x;
        this.ambientLig.y = ambientLig.y;
        this.ambientLig.z = ambientLig.z;
        ambDirty = true;
    }

    public Vector3f getAmbientLig() {
        return ambientLig;
    }

    public boolean isAmbientDirty(){
        return ambDirty;
    }

    public void unMarkAmbientDirty(){
        ambDirty = false;
    }

    public void delete(){
        objs.values().forEach(List::clear);
        objs.clear();
    }

    public float getFogDensity() {
        return fogDensity;
    }

    public float getFogGradient() {
        return fogGradient;
    }

    public Vector3f getSkyColor() {
        return skyColor;
    }

    public void setFogDensity(float fogDensity) {
        this.fogDensity = fogDensity;
    }

    public void setFogGradient(float fogGradient) {
        this.fogGradient = fogGradient;
    }

    public void setSkyColor(Vector3f skyColor) {
        this.skyColor = skyColor;
    }

    public boolean isSkyDirty() {
        return skyDirty;
    }
}
