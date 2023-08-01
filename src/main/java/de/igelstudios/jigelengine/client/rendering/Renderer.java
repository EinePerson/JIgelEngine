package de.igelstudios.jigelengine.client.rendering;

import de.igelstudios.jigelengine.client.Projection;
import de.igelstudios.jigelengine.client.rendering.data.Mesh;
import de.igelstudios.jigelengine.client.rendering.data.Model;
import de.igelstudios.jigelengine.client.rendering.data.ModelCache;
import de.igelstudios.jigelengine.client.rendering.shader.Shader;
import de.igelstudios.jigelengine.common.scene.Camera;
import de.igelstudios.jigelengine.common.scene.Scene;
import de.igelstudios.jigelengine.common.scene.SceneObject;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
    private Projection projection;
    private Shader defaultS;
    private Scene scene;
    private Camera camera;

    public Renderer(Scene scene, Camera camera){
        defaultS = new Shader("default");
        this.scene = scene;
        this.camera = camera;
        projection = new Projection();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void render(){
        defaultS.use();
        if(projection.isDirty()){
            defaultS.putMat("projection",projection.getMatrix());
            projection.unDirty();
        }
        if(camera.isDirty()){
            defaultS.putMat("view",camera.getViewMatrix());
            camera.unMarkDirty();
        }
        scene.getObjs().forEach(this::render);
        defaultS.unUse();
    }

    public void render(String id,List<SceneObject> objs){
        Model model = ModelCache.get(id);
        glBindVertexArray(model.getMesh().getVao());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,model.getTexture().getID());

        objs.forEach(object ->{
            defaultS.putMat("transform",object.getMat());
            glDrawElements(GL_TRIANGLES,object.getModel().getMesh().getVertexCount(),GL_UNSIGNED_INT,0);
        });

        glBindTexture(GL_TEXTURE_2D,0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    public void delete() {
        defaultS.delete();
    }

    public void resize(int width,int height){
        projection.resize(width, height);
    }
}
