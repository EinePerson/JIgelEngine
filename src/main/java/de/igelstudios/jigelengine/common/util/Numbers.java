package de.igelstudios.jigelengine.common.util;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

/**
 * This class is to convert numbers and number formats
 */
public class Numbers {
    private Numbers(){

    }

    /**
     * this convert an {@link Integer} to a {@link Byte} array
     * @param i the Integer to be converted
     * @return the byte Array representation of the Integer with a fixed length of 4
     * @see #toInt(byte[])
     */
    public static byte[] toBytes(int i){
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    /**
     * This converts an array of {@link Byte} with a mandatory length of 4 to a Single {@link Integer}
     * @param b The Byte Array to be converted
     * @return the Integer representation of the Byte Array
     * @see #toBytes(int)
     */
    public static int toInt(byte[] b){
        if(b.length > 4)throw new IllegalArgumentException("An Integer only consist of 4 bytes");
        return ByteBuffer.wrap(b).getInt();
    }


    /**
     * This stores the given Array into a Float Buffer <br> same as {@link #store(int[])}
     * @param f the Array to be stored
     * @return the Readable Buffer containing the Array
     */
    public static FloatBuffer store(float[] f){
        FloatBuffer fp = BufferUtils.createFloatBuffer(f.length);
        fp.put(f);
        fp.flip();
        return fp;
    }

    /**
     * This stores the given Array into a Float Buffer <br> same as {@link #store(float[])}
     * @param i the Array to be stored
     * @return the Readable Buffer containing the Array
     */
    public static IntBuffer store(int[] i){
        IntBuffer ip = BufferUtils.createIntBuffer(i.length);
        ip.put(i);
        ip.flip();
        return ip;
    }

    /**
     * This converts an array of {@link Vector2f} to a float Array
     * @param vec the array to be converted
     * @return the return array
     */
    public static float[] toFArr(Vector2f[] vec){
        float[] f = new float[vec.length * 2];
        for (int i = 0; i < vec.length * 2; i++) {
            if((i & 1) == 0)f[i] = vec[i / 2].x;
            else f[i] = vec[i / 2].y;
        }
        return f;
    }

    /**
     * This converts a list of {@link Float} to an array <br> same as {@link #toIArr(List)}
     * @param list the list to be converted
     * @return the List in Array form
     */
    public static float[] toFArr(List<Float> list){
        float[] arr = new float[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    /**
     * This converts a list of {@link Float} to an array <br> same as {@link #toFArr(List)}
     * @param list the list to be converted
     * @return the List in Array form
     */
    public static int[] toIArr(List<Integer> list){
        int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
