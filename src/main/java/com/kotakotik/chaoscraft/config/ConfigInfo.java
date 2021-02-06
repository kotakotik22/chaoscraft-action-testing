package com.kotakotik.chaoscraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class ConfigInfo {
    public abstract static class Info<T> {
        public final T defauld;
        public final String name;
        public final String desc;
        public final ForgeConfigSpec.Builder builder;
        public final ForgeConfigSpec.ConfigValue<T> configValue;

        public Info(ForgeConfigSpec.Builder BUILDER, String name,
                    String description, T defauld, ForgeConfigSpec.ConfigValue<T> configValue) {
            this.defauld = defauld;
            this.name = name;
            desc = description;
            this.builder = BUILDER;
            this.configValue = configValue;
        }

        public abstract CType getType();

        public void register() {
            Config.registeredConfig.add(this);
        }

    }

    public static class IntInfo extends Info<Integer> {
        public final int min;
        public final int max;

        public IntInfo(ForgeConfigSpec.Builder BUILDER, String name, String description, Integer defauld, ForgeConfigSpec.ConfigValue<Integer> configValue, int min, int max) {
            super(BUILDER, name, description, defauld, configValue);
            this.min = min;
            this.max = max;
        }

        @Override
        public CType getType() {
            return CType.INT;
        }
    }

    public static class BoolInfo extends Info<Boolean> {
        public BoolInfo(ForgeConfigSpec.Builder BUILDER, String name, String description, Boolean defauld, ForgeConfigSpec.ConfigValue<Boolean> configValue) {
            super(BUILDER, name, description, defauld, configValue);
        }

        @Override
        public CType getType() {
            return CType.BOOL;
        }
    }

    public static class ListInfo extends Info<List<? extends String>> {
        public ListInfo(ForgeConfigSpec.Builder BUILDER, String name, String description, List<String> defauld, ForgeConfigSpec.ConfigValue<List<? extends String>> configValue) {
            super(BUILDER, name, description, defauld, configValue);
        }

        public ListInfo(ForgeConfigSpec.Builder BUILDER, String name, String description, ForgeConfigSpec.ConfigValue<List<? extends String>> configValue) {
            super(BUILDER, name, description, new ArrayList<>(), configValue);
        }

        @Override
        public CType getType() {
            return CType.LIST;
        }
    }

    public static class StringInfo extends Info<String> {

        public StringInfo(ForgeConfigSpec.Builder BUILDER, String name, String description, String defauld, ForgeConfigSpec.ConfigValue<String> configValue) {
            super(BUILDER, name, description, defauld, configValue);
        }

        @Override
        public CType getType() {
            return null;
        }
    }
}
