package de.igelstudios.jigelengine.common.util;


/**
 * Classes implementing this interface shall not have a constructor but should use the {@link #init()} method<br>
 * the {@link #init()} should also reset everything so that calling it resembles creating a new instance
 */
public interface Initializeble {

    /**
     * The recallable constructor of this class
     */
    void init();
}
