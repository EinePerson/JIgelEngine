package de.igelstudios.jigelengine.common.scene;

import de.igelstudios.jigelengine.client.rendering.data.Model;
import de.igelstudios.jigelengine.client.rendering.data.RenderCache;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * this class is representing any 3D object that can ve rendered
 */
public class SceneObject {
    private final Matrix4f mat;
    private final Model model;
    private final Vector3f position;
    private final Quaternionf rotation;
    private final Vector3f scale;

    /**
     * This constructs a new 3D renderer object with the given values
     * @param pos the position of the Object, this will always be the same
     * @param model the model of the Object obtainable with: <br> {@link RenderCache#get(String)}
     *              <br> {@link RenderCache#load(String, String, String)}
     * @see SceneObject#SceneObject(Model)  SceneObject
     */
    public SceneObject(Vector3f pos,Model model){
        this.mat = new Matrix4f();
        this.model = model;
        this.position = pos;
        rotation = new Quaternionf();
        scale = new Vector3f(1.0f);
        updateMatrix();
    }

    /**
     * This constructs a new 3D renderer object with the given Model and position at 0,0,0
     * @param model the model of the Object obtainable with: <br> {@link RenderCache#get(String)}
     *              <br> {@link RenderCache#load(String, String, String)}
     * @see SceneObject#SceneObject(Vector3f, Model)  SceneObject
     */
    public SceneObject(Model model){
        this(new Vector3f(),model);
    }


    /**
     * @return the Matrix of this Object
     */
    public Matrix4f getMat() {
        return mat;
    }

    /**
     * @return the Model representing this object
     */
    public Model getModel() {
        return model;
    }

    /**
     * Sets the scale for every axis
     * @param scale the scale
     * @return this
     * @see #setScale(Vector3f)
     */
    public SceneObject setScale(float scale) {
        this.scale.x = scale;
        this.scale.y = scale;
        this.scale.z = scale;
        updateMatrix();
        return this;
    }

    /**
     * Sets the scale on a per-Axis basis
     * @param scale the vector containing the axis scales, values are only copied to the scale {@link Vector3f} of this object
     * @return this
     */
    public SceneObject setScale(Vector3f scale) {
        this.scale.x = scale.x;
        this.scale.y = scale.y;
        this.scale.z = scale.z;
        updateMatrix();
        return this;
    }

    /**
     * Sets the position of this object
     * @param position the new Position of the object, values are only copied to the scale {@link Vector3f} of this object
     * @return this
     */
    public SceneObject setPosition(Vector3f position) {
        this.position.x = position.x;
        this.position.y = position.y;
        this.position.z = position.z;
        updateMatrix();
        return this;
    }

    /**
     * @return the Rotation {@link Quaternionf} of this Object
     */
    public Quaternionf getRotation() {
        return rotation;
    }

    /**
     * @return the specific Position of this Object
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Updates the Matrix of this Object<br>
     * MUST be called after the {@link #rotation}, the {@link #position} or the {@link #scale} are changed!<br>
     * Methods that are directly changing these values do this automatically
     */
    public void updateMatrix() {
        mat.translationRotateScale(position, rotation, scale);
    }
}
