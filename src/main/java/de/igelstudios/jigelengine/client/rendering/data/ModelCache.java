package de.igelstudios.jigelengine.client.rendering.data;

import java.util.HashMap;
import java.util.Map;

public class ModelCache {
    private final static Map<String, Model> models = new HashMap<>();

    public static Model get(String name){
        if(!models.containsKey(name))throw new IllegalArgumentException("The model " + name + " has to first be loaded before it can be used");
        return models.get(name);
    }

    public static Model load(String name,String mesh,String tex){
        Model model = DataLoader.loadModel(name,mesh,tex);
        models.put(name,model);
        return model;
    }


    public static void delete(){
        models.values().forEach(Model::delete);
        models.clear();
    }

    public static Map<String, Model> getModels() {
        return models;
    }
}
