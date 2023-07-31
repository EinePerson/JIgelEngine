package de.igelstudios.jigelengine.common.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Engine {
    private final List<Tickable> tickables;
    private final List<EngineNotifier> notifiers;
    private boolean running;
    private final int tps;

    public Engine(int tps){
        this.tps = tps;
        tickables = new ArrayList<>();
        notifiers = new ArrayList<>();
    }
    public void start(){
        running = true;
        run();
    }

    public final void run() {
        running = true;
        double ticks = 1000000000d / tps;
        double delta = 0d;
        long org = System.nanoTime();
        long sTimer = 0;
        while (running && shouldRun()){
            long t = System.nanoTime();
            delta += (t - org) / ticks;
            sTimer += t - org;
            org = t;
            if(delta >= 1){
                --delta;
                tick();
                tickables.forEach(Tickable::tick);
                notifiers.forEach(EngineNotifier::tick);
            }

            if(sTimer >= 1000000000){
                sTimer -= 1000000000;
                notifiers.forEach(EngineNotifier::second);
                second();
            }
            loop();
            notifiers.forEach(EngineNotifier::loop);
        }
        notifiers.forEach(EngineNotifier::stop);
        stopSub();
        System.exit(0);
    }


    /**
     * Called every second
     */
    public void second(){

    }


    /**
     * an overridable method for a child class to say if the engine should stop
     * @return weather the engine should continue to run
     */
    public boolean shouldRun(){
        return true;
    }

    /**
     * Called Every Time a tick occurs
     */

    public abstract void tick();

    /**
     * called every iteration of the loop
     */
    public void loop(){

    }

    public void stopSub(){

    }

    public final void stop(){
        running = false;
    }

    public void addTickable(Tickable tickable){
        tickables.add(tickable);
    }

    public void addNotifiable(EngineNotifier notifier){
        notifiers.add(notifier);
    }

    public void removeNotifiable(EngineNotifier notifier){
        notifiers.remove(notifier);
    }

    public void removeTickable(Tickable tickable){
        tickables.remove(tickable);
    }
}
