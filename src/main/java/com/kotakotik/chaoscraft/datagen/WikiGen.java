package com.kotakotik.chaoscraft.datagen;

import com.google.common.base.Utf8;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.utils.FileUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WikiGen implements IDataProvider {
    public final List<ChaosEvent> events;
    public final DataGenerator gen;
    public static final String folder = "../../../chaoscraft.wiki/";

    @Override
    public void act(DirectoryCache cache) throws IOException {
        save("hello world", "hello");
        generateEventPages();
        generateSidebar();
    }

    private String getPageName(ChaosEvent event) {
        return event.wikiReplace(event.getEnglish());
    }

    private void generateEventPages() throws IOException {
        for(ChaosEvent event : events) {
            saveEvent(event.generateWikiPage(), getPageName(event));
        }
    }

    private void generateSidebar() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(read("Sidebar_pre.md"));
        builder.append("\n\n");

        builder.append("### Events");

        for(ChaosEvent event : events) {
            builder.append("\n* [[")
                .append(getPageName(event))
                    .append("]]");
        }

        save(builder.toString(), "_Sidebar");
    }

    private void save(String content, String name) throws IOException {
        save(content, gen.getOutputFolder().resolve(folder + name + ".md"));
    }

    private void saveEvent(String content, String name) throws IOException {
        save(content, gen.getOutputFolder().resolve(folder + "events/" + name + ".md"));
    }

    private String read(String name) throws IOException {
        return read(gen.getOutputFolder().resolve(folder + name));
    }

    private String read(Path target) throws IOException {
        return FileUtils.readFile(target);
    }

    private void save(String content, Path target) throws IOException {
        try (BufferedWriter bufferedwriter = Files.newBufferedWriter(target)) {
            bufferedwriter.write(content);
        }
    }

    @Override
    public String getName() {
        return "ChaosCraft wiki";
    }

    public WikiGen(DataGenerator gen, List<ChaosEvent> events) {
        super();
        this.events = events;
        this.gen = gen;
    }
}
