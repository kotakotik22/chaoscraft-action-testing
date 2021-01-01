package com.kotakotik.chaoscraft;

import net.minecraft.world.World;

public abstract class ChaosEventTimed extends ChaosEvent {
    public abstract int getDuration();
    public abstract void end();
}
