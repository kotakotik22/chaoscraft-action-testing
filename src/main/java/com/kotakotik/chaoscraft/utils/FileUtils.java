package com.kotakotik.chaoscraft.utils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileUtils {
    public static String readFile(Path target) throws IOException {
        try (BufferedReader bufferedreader = Files.newBufferedReader(target)) {
            return bufferedreader.lines().collect(Collectors.joining("\n"));
        }
    }

    public static String  readFile(String target) throws IOException {
        return readFile(Paths.get(target));
    }
}
