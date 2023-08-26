package de.igelstudios.jigelengine.client.rendering;

import org.joml.Matrix4f;

public class Projection {
    private float fov = 60;
    private float zNear = 1.0f;
    private float zFar = 500.0f;
    private Matrix4f mat;
    private int width,height;
    private boolean dirty;

    public Projection(){
        mat = new Matrix4f();
        recalculate();
    }

    public void resize(int width,int height){
        this.width = width;
        this.height = height;
        recalculate();
    }

    public Matrix4f getMatrix() {
        return mat;
    }

    public void setFov(float fov) {
        this.fov = fov;
        recalculate();
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
        recalculate();
    }

    public void setzNear(float zNear) {
        this.zNear = zNear;
        recalculate();
    }

    public void recalculate(){
        mat.identity();
        mat.setPerspective((float) Math.toRadians(fov), (float) width / (float) height, zNear, zFar);
        dirty = true;
    }

    public void unDirty(){
        dirty = false;
    }

    public boolean isDirty() {
        return dirty;
    }
}
