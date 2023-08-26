package de.igelstudios.jigelengine.client.rendering.renderers;

import de.igelstudios.jigelengine.client.rendering.Projection;
import de.igelstudios.jigelengine.client.rendering.shader.Shader;
import de.igelstudios.jigelengine.common.scene.Camera;
import de.igelstudios.jigelengine.common.scene.Scene;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public abstract class Renderer {
    protected Projection projection;
    private Shader[] shaders;
    protected Scene scene;
    protected Camera camera;
    private boolean mark = false;

    public Renderer(Scene scene,Camera camera,Shader ... shaders){
        this.scene = scene;
        this.camera = camera;
        this.projection = new Projection();
        if(shaders.length == 0)throw new IllegalArgumentException("At least one Shader is needed");
        else this.shaders = shaders;
    }

    public final void render(){
        for (Shader sShader : shaders) {
            sShader.use();
            if (projection.isDirty()) {
                sShader.putMat("projection", projection.getMatrix());
                projection.unDirty();
            }
            if (camera.isDirty()) {
                sShader.putMat("view", camera.getViewMatrix());
                if (mark) camera.unMarkDirty();
            }
            if (scene.isAmbientDirty()) {
                sShader.putVec3("ambLig", scene.getAmbientLig());
                if (mark) scene.unMarkAmbientDirty();
            }
            renderSub(sShader);
            sShader.unUse();
        }
    }

    protected abstract void renderSub(Shader shader);

    protected final void actCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    protected final void deActCulling(){
        glDisable(GL_CULL_FACE);
    }

    public final void delete(){
        deleteSub();
        if(shaders != null){
            for (Shader shader1 : shaders) {
                shader1.delete();
            }
        }
    }

    protected void deleteSub(){

    }

    public void resize(int width,int height){
        projection.resize(width, height);
    }

    public void mark(){
        mark = true;
    }
}
