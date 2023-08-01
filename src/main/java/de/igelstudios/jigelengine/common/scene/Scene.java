package de.igelstudios.jigelengine.common.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class represents a Scene, the Scene in the Client class contains every object that is rendered
 */
public class Scene {
    private Map<String,List<SceneObject>> objs;

    public void init(){
        objs = new HashMap<>();
    }

    public Map<String, List<SceneObject>> getObjs() {
        return objs;
    }

    /**
     * Adds a specific object to the Scene which will be rendered
     * @param object The object to be added
     */
    public void addObj(SceneObject object){
        if(!objs.containsKey(object.getModel().getId()))objs.put(object.getModel().getId(),new ArrayList<>());
        objs.get(object.getModel().getId()).add(object);
    }


    /**
     * Removes a specific object from the scene which will not be rendered anymore
     * @param object the object to be removed
     */
    public void remove(SceneObject object){
        if(!objs.containsKey(object.getModel().getId()))return;
        objs.get(object.getModel().getId()).remove(object);
        if(objs.get(object.getModel().getId()).isEmpty())objs.remove(object.getModel().getId());
    }
}
