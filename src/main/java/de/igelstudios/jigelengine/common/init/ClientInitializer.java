package de.igelstudios.jigelengine.common.init;

public interface ClientInitializer {
    void registerKeys(KeyInitializer paramKeyInitializer);

    /**
     * this method is basically the first thing executed when the game is run and is only for making configurations to the core components.
     */
    void init();

    /**
     * this method is called when the game should initialize and everything else is initialized
     */
    void onInitialize();

    void onEnd();
}
