/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_2583
 *  net.minecraft.class_310
 *  net.minecraft.class_332
 *  net.minecraft.class_342
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5250
 */
package com.clanmod.mod.gui;

import com.clanmod.mod.config.RankConfig;
import com.clanmod.mod.gui.ClanModScreen;
import com.clanmod.mod.gui.ClanModScreen2;
import com.clanmod.mod.gui.ClanModScreen4;
import java.io.File;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5250;

public class ClanModScreen3
extends class_437 {
    private static final int BTN_H = 20;
    private static final int PAD = 16;
    private static final int ROW_H = 20;
    private static final int VISIBLE = 14;
    private int scrollOffset = 0;
    private int editingIndex = -1;
    private class_342 rankNameField;
    private class_342 rankKillsField;

    public ClanModScreen3() {
        super((class_2561)class_2561.method_43470((String)"ClanMod \u2014 \u0410\u0432\u0442\u043e-\u0440\u0430\u043d\u043a"));
        RankConfig.reload();
    }

    protected void method_25426() {
        int w = this.field_22789;
        int h = this.field_22790;
        int halfW = w / 2;
        int leftW = halfW - 32;
        int leftX = 16;
        int rightX = halfW + 16;
        int rightW = halfW - 32;
        this.method_37063((class_364)class_4185.method_46430((class_2561)this.getToggleLabel(), btn -> {
            RankConfig cfg = RankConfig.getInstance();
            cfg.autoRankEnabled = !cfg.autoRankEnabled;
            cfg.save();
            btn.method_25355(this.getToggleLabel());
        }).method_46434(rightX, 40, rightW, 20).method_46431());
        int addY = 40;
        int killsW = 55;
        int addBtnW = 24;
        int nameW = leftW - killsW - addBtnW * 2 - 14;
        this.rankNameField = new class_342(this.field_22793, leftX, addY, nameW, 20, (class_2561)class_2561.method_43470((String)""));
        this.rankNameField.method_1880(256);
        this.rankNameField.method_47404((class_2561)class_2561.method_43470((String)"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0440\u0430\u043d\u0433\u0430"));
        this.method_37063((class_364)this.rankNameField);
        this.rankKillsField = new class_342(this.field_22793, leftX + nameW + 4, addY, killsW, 20, (class_2561)class_2561.method_43470((String)""));
        this.rankKillsField.method_1880(10);
        this.rankKillsField.method_47404((class_2561)class_2561.method_43470((String)"\u041a\u0438\u043b\u043b\u043e\u0432"));
        this.method_37063((class_364)this.rankKillsField);
        class_5250 addBtnLabel = this.editingIndex >= 0 ? class_2561.method_43470((String)"\u00a7e\u2713") : class_2561.method_43470((String)"\u00a7a+");
        this.method_37063((class_364)class_4185.method_46430((class_2561)addBtnLabel, btn -> this.addRank()).method_46434(leftX + nameW + 4 + killsW + 4, addY, addBtnW, 20).method_46431());
        if (this.editingIndex >= 0) {
            List<RankConfig.RankEntry> allRanks = RankConfig.getInstance().ranks;
            if (this.editingIndex < allRanks.size()) {
                RankConfig.RankEntry editing = allRanks.get(this.editingIndex);
                this.rankNameField.method_1852(editing.rankName);
                this.rankKillsField.method_1852(String.valueOf(editing.kills));
            } else {
                this.editingIndex = -1;
            }
        }
        int listStartY = addY + 20 + 14;
        List<RankConfig.RankEntry> ranks = RankConfig.getInstance().ranks;
        int editBtnW = addBtnW;
        int rightEdge = halfW - 16 - 2;
        int deleteX = rightEdge - addBtnW;
        int editX = deleteX - addBtnW - 2;
        for (int rowI = this.scrollOffset; rowI < Math.min(this.scrollOffset + 14, ranks.size()); ++rowI) {
            int idx = rowI;
            int y = listStartY + (rowI - this.scrollOffset) * 20;
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7e\u270e"), btn -> {
                this.editingIndex = idx;
                this.rebuildScreen();
            }).method_46434(editX, y + 1, editBtnW, 16).method_46431());
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7cX"), btn -> {
                RankConfig cfg = RankConfig.getInstance();
                if (idx < cfg.ranks.size()) {
                    cfg.ranks.remove(idx);
                    cfg.save();
                    if (this.editingIndex == idx) {
                        this.editingIndex = -1;
                    } else if (this.editingIndex > idx) {
                        --this.editingIndex;
                    }
                    if (this.scrollOffset > 0 && this.scrollOffset >= cfg.ranks.size()) {
                        --this.scrollOffset;
                    }
                    this.rebuildScreen();
                }
            }).method_46434(deleteX, y + 1, editBtnW, 16).method_46431());
        }
        if (ranks.size() > 14) {
            int scrollBtnX = halfW - 16 - 16;
            int listH = 280;
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u25b2"), btn -> {
                if (this.scrollOffset > 0) {
                    --this.scrollOffset;
                    this.rebuildScreen();
                }
            }).method_46434(scrollBtnX, listStartY, 14, 20).method_46431());
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u25bc"), btn -> {
                if (this.scrollOffset + 14 < RankConfig.getInstance().ranks.size()) {
                    ++this.scrollOffset;
                    this.rebuildScreen();
                }
            }).method_46434(scrollBtnX, listStartY + listH - 20, 14, 20).method_46431());
        }
        int bottomY = h - 26;
        int navSize = 20;
        int navX = 6;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"1"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen())).method_46434(navX, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"2"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen2())).method_46434(navX + navSize + 2, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7e3"), btn -> {}).method_46434(navX + (navSize + 2) * 2, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"4"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen4())).method_46434(navX + (navSize + 2) * 3, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Close"), btn -> this.method_25419()).method_46434(w / 2 - 25, bottomY, 50, 20).method_46431());
        int rightX2 = halfW + 16;
        int rightW2 = halfW - 32;
        int btnW2 = (rightW2 - 4) / 2;
        int actionY = bottomY - 20 - 4;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7e\ud83d\udcc1 \u041f\u0430\u043f\u043a\u0430"), btn -> {
            try {
                File configDir = new File(class_310.method_1551().field_1697, "config");
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    new ProcessBuilder("explorer.exe", configDir.getAbsolutePath()).start();
                } else if (os.contains("mac")) {
                    new ProcessBuilder("open", configDir.getAbsolutePath()).start();
                } else {
                    new ProcessBuilder("xdg-open", configDir.getAbsolutePath()).start();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }).method_46434(rightX2, actionY, btnW2, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7a\ud83d\udd04 \u041e\u0431\u043d\u043e\u0432\u0438\u0442\u044c"), btn -> {
            RankConfig.reload();
            this.scrollOffset = 0;
            this.rebuildScreen();
        }).method_46434(rightX2 + btnW2 + 4, actionY, btnW2, 20).method_46431());
    }

    private void rebuildScreen() {
        this.method_37067();
        this.method_25426();
    }

    private void addRank() {
        String name = this.rankNameField.method_1882().trim();
        String kills = this.rankKillsField.method_1882().trim();
        if (!name.isEmpty() && !kills.isEmpty()) {
            int k;
            try {
                k = Integer.parseInt(kills);
            }
            catch (NumberFormatException var5) {
                return;
            }
            RankConfig cfg = RankConfig.getInstance();
            if (this.editingIndex >= 0 && this.editingIndex < cfg.ranks.size()) {
                cfg.ranks.set(this.editingIndex, new RankConfig.RankEntry(name, k));
                this.editingIndex = -1;
            } else {
                cfg.ranks.removeIf(r -> r.rankName.equalsIgnoreCase(name));
                cfg.ranks.add(new RankConfig.RankEntry(name, k));
            }
            cfg.ranks.sort((a, b) -> Integer.compare(b.kills, a.kills));
            cfg.save();
            this.rankNameField.method_1852("");
            this.rankKillsField.method_1852("");
            this.scrollOffset = 0;
            this.rebuildScreen();
        }
    }

    public void method_25420(class_332 g, int mouseX, int mouseY, float partialTick) {
        g.method_25294(0, 0, this.field_22789, this.field_22790, -872415232);
    }

    public void method_25394(class_332 g, int mouseX, int mouseY, float delta) {
        int i;
        super.method_25394(g, mouseX, mouseY, delta);
        int w = this.field_22789;
        int halfW = w / 2;
        int leftX = 16;
        int leftW = halfW - 32;
        int rightX = halfW + 16;
        int rightW = halfW - 32;
        g.method_25294(halfW - 1, 10, halfW + 1, this.field_22790 - 35, 0x66FFFFFF);
        g.method_27534(this.field_22793, (class_2561)class_2561.method_43470((String)"\u2014 \u0410\u0412\u0422\u041e-\u0420\u0410\u041d\u041a \u2014").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), w / 2, 12, -10496);
        g.method_27534(this.field_22793, (class_2561)class_2561.method_43470((String)"\u0421\u041f\u0418\u0421\u041e\u041a \u0420\u0410\u041d\u0413\u041e\u0412").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), leftX + leftW / 2, 28, -10748030);
        int addY = 40;
        int killsW = 55;
        int addBtnW = 24;
        int nameW = leftW - killsW - addBtnW * 2 - 14;
        int listStartY = addY + 20 + 2;
        if (this.editingIndex >= 0) {
            g.method_51433(this.field_22793, "\u00a7e\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435...", leftX, listStartY, -8960, false);
        } else {
            g.method_51433(this.field_22793, "\u00a77\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435", leftX, listStartY, -5592406, false);
            g.method_51433(this.field_22793, "\u00a77\u041a\u0438\u043b\u043b\u043e\u0432", leftX + nameW + 4, listStartY, -5592406, false);
        }
        List<RankConfig.RankEntry> ranks = RankConfig.getInstance().ranks;
        int rowStartY = listStartY + 12;
        for (i = this.scrollOffset; i < Math.min(this.scrollOffset + 14, ranks.size()); ++i) {
            RankConfig.RankEntry r = ranks.get(i);
            int y = rowStartY + (i - this.scrollOffset) * 20;
            if (i == this.editingIndex) {
                g.method_25294(leftX, y - 1, halfW - 16 - 18, y + 20 - 3, 1157618944);
            } else if (i % 2 == 0) {
                g.method_25294(leftX, y - 1, halfW - 16 - 18, y + 20 - 3, 0x22FFFFFF);
            }
            Object displayName = r.rankName;
            while (((String)displayName).length() > 1 && this.field_22793.method_1727("\u00a7f" + (String)displayName) > nameW - 4) {
                displayName = ((String)displayName).substring(0, ((String)displayName).length() - 1);
            }
            if (!((String)displayName).equals(r.rankName)) {
                displayName = (String)displayName + "\u2026";
            }
            g.method_51433(this.field_22793, "\u00a7f" + (String)displayName, leftX + 2, y + 4, -1, false);
            g.method_51433(this.field_22793, "\u00a7e" + r.kills, leftX + nameW + 6, y + 4, -1, false);
        }
        i = ranks.isEmpty() ? 0 : Math.min(this.scrollOffset + 14, ranks.size()) - this.scrollOffset;
        g.method_51433(this.field_22793, "\u00a77\u0412\u0441\u0435\u0433\u043e: \u00a7f" + ranks.size() + "  \u00a77\u041f\u043e\u043a\u0430\u0437\u0430\u043d\u043e: \u00a7f" + (ranks.isEmpty() ? 0 : this.scrollOffset + 1) + "\u2013" + (this.scrollOffset + i), leftX, this.field_22790 - 50, -5592406, false);
        g.method_27534(this.field_22793, (class_2561)class_2561.method_43470((String)"\u0423\u041f\u0420\u0410\u0412\u041b\u0415\u041d\u0418\u0415").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), rightX + rightW / 2, 28, -10772737);
        int ry = 70;
        g.method_51433(this.field_22793, "\u00a77\u0418\u0433\u0440\u043e\u043a \u043f\u0438\u0448\u0435\u0442 \u00a7frankup\u00a77 \u0432 \u043a\u043b\u0430\u043d-\u0447\u0430\u0442.", rightX, ry, -5592406, false);
        g.method_51433(this.field_22793, "\u00a77\u041c\u043e\u0434 \u0437\u0430\u043f\u0440\u0430\u0448\u0438\u0432\u0430\u0435\u0442 \u00a7f/c stats \u043d\u0438\u043a\u00a77,", rightX, ry + 12, -5592406, false);
        g.method_51433(this.field_22793, "\u00a77\u043e\u043f\u0440\u0435\u0434\u0435\u043b\u044f\u0435\u0442 \u043a\u0438\u043b\u043b\u044b \u0438 \u0432\u044b\u0434\u0430\u0451\u0442 \u0440\u0430\u043d\u0433.", rightX, ry + 24, -5592406, false);
        g.method_51433(this.field_22793, "\u00a77\u0415\u0441\u043b\u0438 \u043d\u0435 \u0445\u0432\u0430\u0442\u0430\u0435\u0442 \u2014 \u043f\u0438\u0448\u0435\u0442 \u0441\u043a\u043e\u043b\u044c\u043a\u043e \u043d\u0443\u0436\u043d\u043e.", rightX, ry + 36, -5592406, false);
        g.method_25294(rightX, ry + 52, rightX + rightW, ry + 53, 0x44FFFFFF);
        g.method_51433(this.field_22793, "\u00a77\u0424\u043e\u0440\u043c\u0430\u0442\u044b \u043a\u043b\u0430\u043d-\u0447\u0430\u0442\u0430:", rightX, ry + 58, -5592406, false);
        g.method_51433(this.field_22793, "\u00a7f\u041a\u041b\u0410\u041d:  \u00a7b\u041d\u0438\u043a\u00a77: rankup", rightX, ry + 70, -3355444, false);
        g.method_51433(this.field_22793, "\u00a7f\u041a\u041b\u0410\u041d: \u00a77[\u041f\u0440\u0435\u0444\u0438\u043a\u0441] \u00a7b\u041d\u0438\u043a\u00a77: rankup", rightX, ry + 82, -3355444, false);
        g.method_25294(rightX, ry + 96, rightX + rightW, ry + 97, 0x44FFFFFF);
        g.method_51433(this.field_22793, "\u00a77\u0424\u0430\u0439\u043b: \u00a7fconfig/clanmod_ranks.json", rightX, ry + 103, -5592406, false);
        g.method_51433(this.field_22793, "\u00a77\u041f\u0435\u0440\u0435\u0434\u0430\u0439\u0442\u0435 \u0434\u0440\u0443\u0433\u0443 \u0434\u043b\u044f \u0441\u0438\u043d\u0445\u0440\u043e\u043d\u0438\u0437\u0430\u0446\u0438\u0438.", rightX, ry + 115, -5592406, false);
    }

    public boolean method_25401(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (mouseX < (double)this.field_22789 / 2.0) {
            int size = RankConfig.getInstance().ranks.size();
            if (scrollY < 0.0 && this.scrollOffset + 14 < size) {
                ++this.scrollOffset;
                this.rebuildScreen();
                return true;
            }
            if (scrollY > 0.0 && this.scrollOffset > 0) {
                --this.scrollOffset;
                this.rebuildScreen();
                return true;
            }
        }
        return super.method_25401(mouseX, mouseY, scrollX, scrollY);
    }

    public boolean method_25421() {
        return false;
    }

    private class_2561 getToggleLabel() {
        boolean en = RankConfig.getInstance().autoRankEnabled;
        return class_2561.method_43470((String)("\u0410\u0432\u0442\u043e-\u0440\u0430\u043d\u043a: " + (en ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")));
    }
}
