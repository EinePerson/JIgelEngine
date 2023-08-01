package de.igelstudios.jigelengine.common.init;

public interface ClientInitializer {
    /**
     * This method is called during the Initialization of the {@link de.igelstudios.jigelengine.client.keys.HIDInput}
     * @param paramKeyInitializer the object where keys and Key Listeners can be added
     */
    void registerKeys(KeyInitializer paramKeyInitializer);

    /**
     * this method is basically the first thing executed when the game is run and is only for making configurations to the core components.
     */
    void init();

    /**
     * this method is called when the game should initialize and everything else is initialized
     */
    void onInitialize();

    /**
     * This is the last thing executed in the Program
     */
    void onEnd();
}
