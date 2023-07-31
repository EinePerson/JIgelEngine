package de.igelstudios.jigelengine.common.engine;

public interface EngineNotifier {

    /**
     * Called every Second
     */
    public void second();

    /**
     * Called Every Time a tick occurs
     */

    public abstract void tick();

    /**
     * called every iteration of the loop
     */
    public void loop();

    /**
     * Called when the engine Stops
     */
    public void stop();
}
