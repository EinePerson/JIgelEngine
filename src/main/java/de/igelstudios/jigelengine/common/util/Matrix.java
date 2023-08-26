package de.igelstudios.jigelengine.common.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Matrix {

    public static Matrix4f transform(Vector3f pos){
        Matrix4f mat = new Matrix4f();
        mat.translationRotateScale(pos.x,pos.y,pos.z,0.0f,0.0f,0.0f,0.0f,1.0f);
        return mat;
    }
}
