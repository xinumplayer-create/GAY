/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 */
package com.clanmod.mod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    private static final File FILE = new File("config/chatclanbot.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ModConfig instance;
    public boolean autoGiveEnabled = false;
    public boolean balanceOnJoinEnabled = false;
    public boolean balanceOnLeaveEnabled = false;
    public boolean blacklistJoinEnabled = false;
    public boolean blacklistLeaveEnabled = true;
    public String adCommand = "";
    public List<CustomCommand> customCommands = new ArrayList<CustomCommand>();
    public List<String> blacklist = new ArrayList<String>();

    public static ModConfig getInstance() {
        if (instance == null) {
            instance = new ModConfig();
        }
        return instance;
    }

    public static void reloadInstance() {
        instance = null;
        ModConfig.load();
    }

    public static void load() {
        try {
            if (FILE.exists()) {
                FileReader reader = new FileReader(FILE);
                ModConfig loaded = (ModConfig)GSON.fromJson((Reader)reader, ModConfig.class);
                ((Reader)reader).close();
                if (loaded != null) {
                    instance = loaded;
                    if (ModConfig.instance.customCommands == null) {
                        ModConfig.instance.customCommands = new ArrayList<CustomCommand>();
                    }
                    if (ModConfig.instance.blacklist == null) {
                        ModConfig.instance.blacklist = new ArrayList<String>();
                    }
                }
            }
        }
        catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public void save() {
        try {
            FILE.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(FILE);
            GSON.toJson((Object)this, (Appendable)writer);
            ((Writer)writer).close();
        }
        catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public static class CustomCommand {
        public String keywords;
        public String command;
    }
}
