package de.igelstudios.jigelengine.common.init;

public interface ServerInitializer {

    /**
     * this method is called when the game should initialize and everything else is initialized
     */
    void onInitialize();

    /**
     * This is the last thing executed in the Program
     */
    void onEnd();
}
