package com.kotakotik.chaoscraft.chaos_handlers;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public abstract class ChaosEventTemp extends ChaosEvent {
    private boolean ended = false;

    public abstract int getDuration();
    public void end(MinecraftServer server) { }

    public void reset() {
        ended = false;
        ticksSinceEnable = 0;
    }

    @Override
    public void start(MinecraftServer server) {
        // overridden so it is optional for extending classes
    }

    private int ticksSinceEnable = 0;

    public void tick(MinecraftServer server) {
        if(ticksSinceEnable >= getDuration() * 20) {
            end(server);
            ended = true;
        } else {
            ticksSinceEnable++;
        }
    }

    public boolean hasEnded() {
        return ended;
    }

    public List<ChaosEventTemp> incompatibleWith() {
        return new ArrayList<>();
    }

    public boolean incompatibleWith(ChaosEventTemp eventTemp) {
        return incompatibleWith().contains(eventTemp);
    }

    public boolean isCompatibleWith(ChaosEventTemp eventTemp) {
        return !(incompatibleWith(eventTemp) || eventTemp.incompatibleWith(this));
    }

    public boolean isIncompatibleWith(ChaosEventTemp eventTemp) {
        return !isCompatibleWith(eventTemp);
    }
}
