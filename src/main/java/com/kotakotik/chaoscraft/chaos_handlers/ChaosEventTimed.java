package com.kotakotik.chaoscraft.chaos_handlers;

public abstract class ChaosEventTimed extends ChaosEvent {
    public abstract int getDuration();
    public abstract void end();
}
