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
import java.util.Iterator;
import java.util.List;

public class RankConfig {
    private static final File FILE = new File("config/clanmod_ranks.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static RankConfig instance;
    public boolean autoRankEnabled = false;
    public List<RankEntry> ranks = new ArrayList<RankEntry>();

    public static RankConfig getInstance() {
        if (instance == null) {
            instance = new RankConfig();
        }
        return instance;
    }

    public static void load() {
        try {
            if (FILE.exists()) {
                FileReader reader = new FileReader(FILE);
                RankConfig loaded = (RankConfig)GSON.fromJson((Reader)reader, RankConfig.class);
                ((Reader)reader).close();
                if (loaded != null) {
                    instance = loaded;
                    if (RankConfig.instance.ranks == null) {
                        RankConfig.instance.ranks = new ArrayList<RankEntry>();
                    }
                }
            }
        }
        catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public static void reload() {
        instance = null;
        RankConfig.load();
        if (instance == null) {
            instance = new RankConfig();
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

    public RankEntry getRankFor(int playerKills) {
        RankEntry best = null;
        Iterator<RankEntry> var3 = this.ranks.iterator();
        while (var3.hasNext()) {
            RankEntry r = var3.next();
            if (playerKills < r.kills || best != null && r.kills <= best.kills) continue;
            best = r;
        }
        return best;
    }

    public RankEntry getNextRank(int playerKills) {
        RankEntry next = null;
        Iterator<RankEntry> var3 = this.ranks.iterator();
        while (var3.hasNext()) {
            RankEntry r = var3.next();
            if (r.kills <= playerKills || next != null && r.kills >= next.kills) continue;
            next = r;
        }
        return next;
    }

    public static class RankEntry {
        public String rankName;
        public int kills;

        public RankEntry() {
        }

        public RankEntry(String rankName, int kills) {
            this.rankName = rankName;
            this.kills = kills;
        }
    }
}
