package de.igelstudios.jigelengine.common.scene;

import org.joml.Vector3f;

public class Light {
    private final Vector3f position;
    private final Vector3f color;

    public Light(Vector3f position,Vector3f color){
        this.position = position;
        this.color = color;
    }

    public Light setPosition(Vector3f position){
        this.position.x = position.x;
        this.position.y = position.y;
        this.position.z = position.z;
        return this;
    }

    public Light setColor(Vector3f color){
        this.color.x = color.x;
        this.color.y = color.y;
        this.color.z = color.z;
        return this;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }
}
