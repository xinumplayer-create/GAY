/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1041
 *  net.minecraft.class_11905
 *  net.minecraft.class_11908
 *  net.minecraft.class_11909
 *  net.minecraft.class_124
 *  net.minecraft.class_2561
 *  net.minecraft.class_2583
 *  net.minecraft.class_268
 *  net.minecraft.class_269
 *  net.minecraft.class_309
 *  net.minecraft.class_310
 *  net.minecraft.class_332
 *  net.minecraft.class_342
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5251
 *  net.minecraft.class_640
 */
package com.clanmod.mod.gui;

import com.clanmod.mod.events.AutoManager;
import com.clanmod.mod.events.ClanChatHandler;
import com.clanmod.mod.gui.ClanModScreen;
import com.clanmod.mod.gui.ClanModScreen2;
import com.clanmod.mod.gui.ClanModScreen3;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.class_1041;
import net.minecraft.class_11905;
import net.minecraft.class_11908;
import net.minecraft.class_11909;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_268;
import net.minecraft.class_269;
import net.minecraft.class_309;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5251;
import net.minecraft.class_640;

public class ClanModScreen4
extends class_437 {
    private static final int LINE_H = 10;
    private static final int TEXT_PAD = 4;
    private String editorText = "";
    private int editorScrollY = 0;
    private int textAreaH = 0;
    private int textAreaY = 0;
    private int textAreaX = 0;
    private int textAreaW = 0;
    private int cursorPos = 0;
    private int selStart = -1;
    private boolean editorFocused = false;
    private boolean isDraggingText = false;
    private class_342 delayField;
    private boolean useMillis;
    private class_342 nickField;
    private String lastInput = "";
    private List<String> suggestions = new ArrayList<String>();
    private String foundNick = "";
    private String foundPrefix = "";
    private String foundSuffix = "";
    private String copiedLabel = "";
    private long copiedTime = 0L;

    public ClanModScreen4() {
        super((class_2561)class_2561.method_43470((String)"ClanMod \u2014 Utilities"));
        this.useMillis = AutoManager.multiSendUseMillis;
        StringBuilder sb = new StringBuilder();
        List<String> saved = AutoManager.multiSendEditorLines;
        for (int i = 0; i < saved.size(); ++i) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(saved.get(i));
        }
        this.editorText = sb.toString();
    }

    protected void method_25426() {
        int infoY;
        int w = this.field_22789;
        int h = this.field_22790;
        int halfW = w / 2;
        int bottomY = h - 26;
        int navSize = 20;
        int navX = 6;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"1"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen())).method_46434(navX, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"2"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen2())).method_46434(navX + navSize + 2, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"3"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen3())).method_46434(navX + (navSize + 2) * 2, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7e4"), btn -> {}).method_46434(navX + (navSize + 2) * 3, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Close"), btn -> this.method_25419()).method_46434(w / 2 - 25, bottomY, 50, 16).method_46431());
        int leftX = 6;
        int leftW = halfW - 12;
        int belowControls = 68;
        this.textAreaX = leftX;
        this.textAreaY = 28;
        this.textAreaW = leftW;
        this.textAreaH = h - (h - bottomY) - belowControls - this.textAreaY - 10;
        if (this.textAreaH < 60) {
            this.textAreaH = 60;
        }
        AutoManager.onMultiSendFinish = this::rebuildScreen;
        int belowArea = this.textAreaY + this.textAreaH + 8;
        int delayFieldW = leftW - 70;
        this.delayField = new class_342(this.field_22793, leftX, belowArea, delayFieldW, 16, (class_2561)class_2561.method_43470((String)""));
        this.delayField.method_1880(12);
        this.delayField.method_47404((class_2561)class_2561.method_43470((String)"\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430..."));
        this.delayField.method_1852(String.valueOf(AutoManager.multiSendDelayRaw));
        this.delayField.method_1863(val -> {
            try {
                AutoManager.multiSendDelayRaw = Long.parseLong(val.trim());
            }
            catch (Exception exception) {
                // empty catch block
            }
        });
        this.method_37063((class_364)this.delayField);
        String unitLabel = this.useMillis ? "\u00a7b\u043c\u0441" : "\u00a7a\u0441\u0435\u043a";
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)unitLabel), btn -> {
            AutoManager.multiSendUseMillis = this.useMillis = !this.useMillis;
            btn.method_25355((class_2561)class_2561.method_43470((String)(this.useMillis ? "\u00a7b\u043c\u0441" : "\u00a7a\u0441\u0435\u043a")));
        }).method_46434(leftX + delayFieldW + 4, belowArea, 66, 16).method_46431());
        int sendBtnY = belowArea + 20;
        boolean running = AutoManager.multiSendRunning;
        String sendLabel = running ? "\u00a7c\u041e\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u043e\u0442\u043f\u0440\u0430\u0432\u043a\u0443" : "\u00a7a\u041e\u0442\u043f\u0440\u0430\u0432\u0438\u0442\u044c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435";
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)sendLabel), btn -> {
            if (AutoManager.multiSendRunning) {
                AutoManager.stopMultiSend();
                AutoManager.multiSendIndex = 0;
            } else {
                ArrayList<String> lines = new ArrayList<String>();
                for (String line : this.editorText.split("\n", -1)) {
                    String t = line.trim();
                    if (t.isEmpty()) continue;
                    lines.add(t);
                }
                if (!lines.isEmpty()) {
                    AutoManager.multiSendIndex = 0;
                    long raw = AutoManager.multiSendDelayRaw;
                    long ms = this.useMillis ? Math.max(raw, 1L) : raw * 1000L;
                    AutoManager.startMultiSend(lines, ms);
                }
            }
            this.rebuildScreen();
        }).method_46434(leftX, sendBtnY, leftW, 16).method_46431());
        int row2Y = sendBtnY + 20;
        int halfWLeft = leftW / 2 - 2;
        String loopLabel = AutoManager.multiSendLoop ? "\u00a7a\u0426\u0438\u043a\u043b: \u0412\u041a\u041b" : "\u00a7c\u0426\u0438\u043a\u043b: \u0412\u042b\u041a\u041b";
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)loopLabel), btn -> {
            AutoManager.multiSendLoop = !AutoManager.multiSendLoop;
            btn.method_25355((class_2561)class_2561.method_43470((String)(AutoManager.multiSendLoop ? "\u00a7a\u0426\u0438\u043a\u043b: \u0412\u041a\u041b" : "\u00a7c\u0426\u0438\u043a\u043b: \u0412\u042b\u041a\u041b")));
        }).method_46434(leftX, row2Y, halfWLeft, 16).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7c\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0442\u0435\u043a\u0441\u0442"), btn -> {
            this.editorText = "";
            this.cursorPos = 0;
            this.selStart = -1;
            this.editorScrollY = 0;
            this.syncToManager();
            this.rebuildScreen();
        }).method_46434(leftX + halfWLeft + 4, row2Y, leftW - halfWLeft - 4, 16).method_46431());
        int rightX = halfW + 6;
        int rightW = halfW - 12;
        int halfRW = rightW / 2 - 2;
        int col2X = rightX + halfRW + 4;
        int col2W = rightW - halfRW - 4;
        int block1Y = 20;
        int row2YRight = block1Y + 16 + 3;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7b\u041f\u043e\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u041f\u0440\u0435\u0444\u0438\u043a\u0441"), btn -> {
            if (this.field_22787.field_1724 != null) {
                String cmd = this.foundPrefix.trim().isEmpty() ? "tab prefix clear" : "tab prefix set " + this.foundPrefix;
                this.field_22787.field_1724.field_3944.method_45730(cmd);
                ClanChatHandler.addLog("[TAB] /" + cmd, -16711766);
            }
        }).method_46434(rightX, block1Y, halfRW, 16).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7d\u041f\u043e\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u0421\u0443\u0444\u0444\u0438\u043a\u0441"), btn -> {
            if (this.field_22787.field_1724 != null) {
                String cmd = this.foundSuffix.trim().isEmpty() ? "tab suffix clear" : "tab suffix set " + this.foundSuffix;
                this.field_22787.field_1724.field_3944.method_45730(cmd);
                ClanChatHandler.addLog("[TAB] /" + cmd, -16711766);
            }
        }).method_46434(rightX, row2YRight, halfRW, 16).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7aCopy Tab"), btn -> this.copyTabToFile()).method_46434(col2X, block1Y, col2W, 16).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7e\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043f\u0430\u043f\u043a\u0443"), btn -> {
            try {
                File dir = new File("config/clanmod");
                dir.mkdirs();
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    new ProcessBuilder("explorer.exe", dir.getAbsolutePath()).start();
                } else if (os.contains("mac")) {
                    new ProcessBuilder("open", dir.getAbsolutePath()).start();
                } else {
                    new ProcessBuilder("xdg-open", dir.getAbsolutePath()).start();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }).method_46434(col2X, row2YRight, col2W, 16).method_46431());
        int nickY = row2YRight + 16 + 14;
        this.nickField = new class_342(this.field_22793, rightX, nickY, rightW, 16, (class_2561)class_2561.method_43470((String)""));
        this.nickField.method_1880(64);
        this.nickField.method_47404((class_2561)class_2561.method_43470((String)"\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043d\u0438\u043a..."));
        this.nickField.method_1852(this.foundNick);
        this.nickField.method_1863(val -> {
            String trimmed = val.trim();
            if (!trimmed.equals(this.lastInput)) {
                this.lastInput = trimmed;
                this.suggestions.clear();
                if (!trimmed.isEmpty() && this.field_22787.method_1562() != null) {
                    String lower = trimmed.toLowerCase();
                    for (class_640 info : this.field_22787.method_1562().method_2880()) {
                        String nick = ClanModScreen4.getNick(info);
                        if (!nick.toLowerCase().startsWith(lower) || nick.equalsIgnoreCase(trimmed)) continue;
                        this.suggestions.add(nick);
                        if (this.suggestions.size() < 5) continue;
                        break;
                    }
                }
                this.lookupPlayer(trimmed);
                this.rebuildScreen();
            }
        });
        this.method_37063((class_364)this.nickField);
        int suggY = nickY + 16 + 2;
        for (infoY = 0; infoY < this.suggestions.size(); ++infoY) {
            String chosen = this.suggestions.get(infoY);
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)("\u00a7f" + chosen)), btn -> {
                this.nickField.method_1852(chosen);
                this.lastInput = chosen;
                this.suggestions.clear();
                this.lookupPlayer(chosen);
                this.rebuildScreen();
            }).method_46434(rightX, suggY + infoY * 18, rightW, 16).method_46431());
        }
        infoY = suggY + 90 + 4;
        if (!this.foundNick.isEmpty()) {
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7fCopy Prefix"), btn -> {
                if (!this.foundPrefix.trim().isEmpty()) {
                    class_310.method_1551().field_1774.method_1455(this.foundPrefix);
                    this.copiedLabel = "prefix";
                    this.copiedTime = System.currentTimeMillis();
                }
            }).method_46434(rightX, infoY + 30, halfRW, 16).method_46431());
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7fCopy Suffix"), btn -> {
                if (!this.foundSuffix.trim().isEmpty()) {
                    class_310.method_1551().field_1774.method_1455(this.foundSuffix);
                    this.copiedLabel = "suffix";
                    this.copiedTime = System.currentTimeMillis();
                }
            }).method_46434(rightX, infoY + 30 + 18, halfRW, 16).method_46431());
        }
    }

    public void method_25420(class_332 g, int mouseX, int mouseY, float partialTick) {
        g.method_25294(0, 0, this.field_22789, this.field_22790, -872415232);
    }

    public void method_25394(class_332 g, int mouseX, int mouseY, float delta) {
        int posInLine;
        Object line;
        int lineY;
        int li;
        super.method_25394(g, mouseX, mouseY, delta);
        g.method_25294(this.textAreaX, this.textAreaY, this.textAreaX + this.textAreaW, this.textAreaY + this.textAreaH, -1442840576);
        String[] lines = this.editorText.split("\n", -1);
        int visibleStart = this.editorScrollY / 12;
        int visibleEnd = Math.min(lines.length, visibleStart + this.textAreaH / 12 + 2);
        int charOff = 0;
        for (li = 0; li < lines.length; ++li) {
            lineY = this.textAreaY + 4 + li * 12 - this.editorScrollY;
            if (lineY + 12 >= this.textAreaY && lineY <= this.textAreaY + this.textAreaH) {
                int cx;
                line = lines[li];
                if (this.selStart != -1) {
                    posInLine = Math.min(this.selStart, this.cursorPos);
                    cx = Math.max(this.selStart, this.cursorPos);
                    int lineEnd = charOff + ((String)line).length();
                    if (posInLine < lineEnd && cx > charOff) {
                        int hA = Math.max(posInLine, charOff) - charOff;
                        int hB = Math.min(cx, lineEnd) - charOff;
                        int x1 = this.textAreaX + 4 + this.field_22793.method_1727(((String)line).substring(0, hA));
                        int x2 = this.textAreaX + 4 + this.field_22793.method_1727(((String)line).substring(0, hB));
                        g.method_25294(x1, lineY - 1, x2, lineY + 10, 0x664444FF);
                    }
                }
                g.method_51433(this.field_22793, (String)line, this.textAreaX + 4, lineY, -1, false);
                if (this.editorFocused && this.cursorPos >= charOff && this.cursorPos <= charOff + ((String)line).length()) {
                    posInLine = this.cursorPos - charOff;
                    cx = this.textAreaX + 4 + this.field_22793.method_1727(((String)line).substring(0, posInLine));
                    if (System.currentTimeMillis() / 500L % 2L == 0L) {
                        g.method_25294(cx, lineY - 1, cx + 1, lineY + 10, -1);
                    }
                }
                charOff += ((String)line).length() + 1;
                continue;
            }
            charOff += lines[li].length() + 1;
        }
        g.method_25294(this.textAreaX, this.textAreaY, this.textAreaX + this.textAreaW, this.textAreaY + 1, -8355712);
        g.method_25294(this.textAreaX, this.textAreaY + this.textAreaH - 1, this.textAreaX + this.textAreaW, this.textAreaY + this.textAreaH, -8355712);
        g.method_25294(this.textAreaX, this.textAreaY, this.textAreaX + 1, this.textAreaY + this.textAreaH, -8355712);
        g.method_25294(this.textAreaX + this.textAreaW - 1, this.textAreaY, this.textAreaX + this.textAreaW, this.textAreaY + this.textAreaH, -8355712);
        li = this.field_22789 / 2;
        lineY = li + 6;
        g.method_25294(li, 5, li + 1, this.field_22790 - 5, -2002081110);
        g.method_27535(this.field_22793, (class_2561)class_2561.method_43470((String)"\u00a7e\u041c\u0443\u043b\u044c\u0442\u0438-\u043e\u0442\u043f\u0440\u0430\u0432\u043a\u0430").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), this.textAreaX, 18, -1);
        g.method_27535(this.field_22793, (class_2561)class_2561.method_43470((String)"\u00a7b\u041f\u043e\u0438\u0441\u043a \u043f\u043e TAB-\u043b\u0438\u0441\u0442\u0443").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), lineY, 10, -1);
        if (!this.foundNick.isEmpty()) {
            int nickY = this.field_22790 / 2 - 40;
            posInLine = nickY + 16 + 2 + 90 + 4;
            String prefixDisplay = this.foundPrefix.isEmpty() ? "\u00a77(\u043f\u0443\u0441\u0442\u043e)" : this.foundPrefix;
            String suffixDisplay = this.foundSuffix.isEmpty() ? "\u00a77(\u043f\u0443\u0441\u0442\u043e)" : this.foundSuffix;
            g.method_51433(this.field_22793, "\u00a77\u041d\u0438\u043a: \u00a7f" + this.foundNick, lineY, posInLine, -1, false);
            g.method_51433(this.field_22793, "\u00a77\u041f\u0440\u0435\u0444\u0438\u043a\u0441: " + prefixDisplay, lineY, posInLine + 12, -1, false);
            g.method_51433(this.field_22793, "\u00a77\u0421\u0443\u0444\u0444\u0438\u043a\u0441: " + suffixDisplay, lineY, posInLine + 24, -1, false);
            if (!this.copiedLabel.isEmpty() && System.currentTimeMillis() - this.copiedTime < 1500L) {
                g.method_51433(this.field_22793, "\u00a7a\u0421\u043a\u043e\u043f\u0438\u0440\u043e\u0432\u0430\u043d\u043e: " + this.copiedLabel, lineY, posInLine + 40, -1, false);
            }
        }
        if (AutoManager.multiSendRunning) {
            line = "\u00a7e\u041e\u0442\u043f\u0440\u0430\u0432\u043a\u0430: " + AutoManager.multiSendIndex + " \u043a\u043e\u043c\u0430\u043d\u0434";
            g.method_51433(this.field_22793, (String)line, this.textAreaX, this.textAreaY + this.textAreaH + 48, -1, false);
        }
    }

    private int getCharPosAt(double mouseX, double mouseY) {
        int relY = (int)(mouseY - (double)this.textAreaY - 4.0) + this.editorScrollY;
        int lineIdx = relY / 12;
        String[] ls = this.editorText.split("\n", -1);
        if (lineIdx < 0) {
            lineIdx = 0;
        }
        if (lineIdx >= ls.length) {
            lineIdx = ls.length - 1;
        }
        int charOffset = 0;
        for (int i = 0; i < lineIdx; ++i) {
            charOffset += ls[i].length() + 1;
        }
        String line = ls[lineIdx];
        int relX = (int)(mouseX - (double)this.textAreaX - 4.0);
        int best = 0;
        int bestDist = Integer.MAX_VALUE;
        for (int c = 0; c <= line.length(); ++c) {
            int dist = Math.abs(this.field_22793.method_1727(line.substring(0, c)) - relX);
            if (dist >= bestDist) continue;
            bestDist = dist;
            best = c;
        }
        return charOffset + best;
    }

    private void processClick(double mouseX, double mouseY) {
        if (mouseX >= (double)this.textAreaX && mouseX < (double)(this.textAreaX + this.textAreaW) && mouseY >= (double)this.textAreaY && mouseY < (double)(this.textAreaY + this.textAreaH)) {
            if (this.delayField != null) {
                this.delayField.method_25365(false);
            }
            if (this.nickField != null) {
                this.nickField.method_25365(false);
            }
            this.editorFocused = true;
            this.selStart = this.cursorPos = this.getCharPosAt(mouseX, mouseY);
            this.isDraggingText = true;
        } else {
            this.editorFocused = false;
            this.isDraggingText = false;
        }
    }

    private void processScroll(double mouseX, double mouseY, double scrollY) {
        if (mouseX >= (double)this.textAreaX && mouseX < (double)(this.textAreaX + this.textAreaW) && mouseY >= (double)this.textAreaY && mouseY < (double)(this.textAreaY + this.textAreaH)) {
            this.editorScrollY -= (int)(scrollY * 12.0 * 3.0);
            if (this.editorScrollY < 0) {
                this.editorScrollY = 0;
            }
        }
    }

    private double[] getAllDoubles(Object obj) {
        int i;
        ArrayList<Double> list = new ArrayList<Double>();
        for (Class<?> clazz = obj.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] var4 = clazz.getDeclaredFields();
            i = var4.length;
            for (int var6 = 0; var6 < i; ++var6) {
                Field f = var4[var6];
                if (f.getType() != Double.TYPE) continue;
                f.setAccessible(true);
                try {
                    list.add(f.getDouble(obj));
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        double[] arr = new double[list.size()];
        for (i = 0; i < list.size(); ++i) {
            arr[i] = (Double)list.get(i);
        }
        return arr;
    }

    private int[] getAllInts(Object obj) {
        int i;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (Class<?> clazz = obj.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] var4 = clazz.getDeclaredFields();
            i = var4.length;
            for (int var6 = 0; var6 < i; ++var6) {
                Field f = var4[var6];
                if (f.getType() != Integer.TYPE) continue;
                f.setAccessible(true);
                try {
                    list.add(f.getInt(obj));
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        int[] arr = new int[list.size()];
        for (i = 0; i < list.size(); ++i) {
            arr[i] = (Integer)list.get(i);
        }
        return arr;
    }

    private char[] getAllChars(Object obj) {
        int i;
        ArrayList<Character> list = new ArrayList<Character>();
        for (Class<?> clazz = obj.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] var4 = clazz.getDeclaredFields();
            i = var4.length;
            for (int var6 = 0; var6 < i; ++var6) {
                Field f = var4[var6];
                if (f.getType() != Character.TYPE) continue;
                f.setAccessible(true);
                try {
                    list.add(Character.valueOf(f.getChar(obj)));
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        char[] arr = new char[list.size()];
        for (i = 0; i < list.size(); ++i) {
            arr[i] = ((Character)list.get(i)).charValue();
        }
        return arr;
    }

    public boolean method_25402(class_11909 event, boolean bl) {
        int button;
        double[] d = this.getAllDoubles(event);
        int[] i = this.getAllInts(event);
        double mouseX = d.length > 0 ? d[0] : 0.0;
        double mouseY = d.length > 1 ? d[1] : 0.0;
        int n = button = i.length > 0 ? i[0] : 0;
        if (button == 0) {
            this.processClick(mouseX, mouseY);
        }
        return super.method_25402(event, bl);
    }

    public boolean method_25406(class_11909 event) {
        double mouseY;
        double[] d = this.getAllDoubles(event);
        double mouseX = d.length > 0 ? d[0] : 0.0;
        double d2 = mouseY = d.length > 1 ? d[1] : 0.0;
        if (this.editorFocused && this.isDraggingText) {
            this.cursorPos = this.getCharPosAt(mouseX, mouseY);
            this.scrollToCursor();
            return true;
        }
        return super.method_25406(event);
    }

    public boolean method_25403(class_11909 event, double mouseX, double mouseY) {
        int button;
        int[] i = this.getAllInts(event);
        int n = button = i.length > 0 ? i[0] : 0;
        if (button == 0 && this.isDraggingText) {
            this.isDraggingText = false;
            if (this.selStart == this.cursorPos) {
                this.selStart = -1;
            }
        }
        return super.method_25403(event, mouseX, mouseY);
    }

    public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        this.processScroll(mouseX, mouseY, verticalAmount);
        return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public boolean method_25404(class_11908 event) {
        boolean shift;
        int modifiers;
        int[] i = this.getAllInts(event);
        int keyCode = i.length > 0 ? i[0] : 0;
        int n = modifiers = i.length > 0 ? i[i.length - 1] : 0;
        if (!this.editorFocused) {
            return super.method_25404(event);
        }
        boolean ctrl = (modifiers & 2) != 0;
        boolean bl = shift = (modifiers & 1) != 0;
        if (ctrl && keyCode == 65) {
            this.selStart = 0;
            this.cursorPos = this.editorText.length();
            return true;
        }
        if (ctrl && keyCode == 67) {
            if (this.selStart != -1) {
                int a = Math.min(this.selStart, this.cursorPos);
                int b = Math.max(this.selStart, this.cursorPos);
                class_310.method_1551().field_1774.method_1455(this.editorText.substring(a, b));
            }
            return true;
        }
        if (ctrl && keyCode == 88) {
            if (this.selStart != -1) {
                int a = Math.min(this.selStart, this.cursorPos);
                int b = Math.max(this.selStart, this.cursorPos);
                class_310.method_1551().field_1774.method_1455(this.editorText.substring(a, b));
                String var10001 = this.editorText.substring(0, a);
                this.editorText = var10001 + this.editorText.substring(b);
                this.cursorPos = a;
                this.selStart = -1;
                this.syncToManager();
            }
            return true;
        }
        if (ctrl && keyCode == 86) {
            String clip = this.getClipboard();
            if (clip != null && !clip.isEmpty()) {
                this.deleteSelection();
                this.editorText = this.editorText.substring(0, this.cursorPos) + clip + this.editorText.substring(this.cursorPos);
                this.cursorPos += clip.length();
                this.syncToManager();
                this.scrollToCursor();
            }
            return true;
        }
        if (keyCode == 259) {
            if (this.selStart != -1) {
                this.deleteSelection();
            } else if (this.cursorPos > 0) {
                String var10001 = this.editorText.substring(0, this.cursorPos - 1);
                this.editorText = var10001 + this.editorText.substring(this.cursorPos);
                --this.cursorPos;
                this.syncToManager();
                this.scrollToCursor();
            }
            return true;
        }
        if (keyCode == 261) {
            if (this.selStart != -1) {
                this.deleteSelection();
            } else if (this.cursorPos < this.editorText.length()) {
                String var10001 = this.editorText.substring(0, this.cursorPos);
                this.editorText = var10001 + this.editorText.substring(this.cursorPos + 1);
                this.syncToManager();
            }
            return true;
        }
        if (keyCode != 257 && keyCode != 335) {
            if (keyCode == 263) {
                if (this.cursorPos > 0) {
                    if (!shift) {
                        this.selStart = -1;
                    } else if (this.selStart == -1) {
                        this.selStart = this.cursorPos;
                    }
                    --this.cursorPos;
                }
                return true;
            }
            if (keyCode == 262) {
                if (this.cursorPos < this.editorText.length()) {
                    if (!shift) {
                        this.selStart = -1;
                    } else if (this.selStart == -1) {
                        this.selStart = this.cursorPos;
                    }
                    ++this.cursorPos;
                }
                return true;
            }
            if (keyCode == 265) {
                this.moveCursorVertical(-1, shift);
                return true;
            }
            if (keyCode == 264) {
                this.moveCursorVertical(1, shift);
                return true;
            }
            if (keyCode == 268) {
                if (!shift) {
                    this.selStart = -1;
                } else if (this.selStart == -1) {
                    this.selStart = this.cursorPos;
                }
                this.cursorPos = this.getLineStart(this.cursorPos);
                return true;
            }
            if (keyCode == 269) {
                if (!shift) {
                    this.selStart = -1;
                } else if (this.selStart == -1) {
                    this.selStart = this.cursorPos;
                }
                this.cursorPos = this.getLineEnd(this.cursorPos);
                return true;
            }
            return super.method_25404(event);
        }
        this.deleteSelection();
        String var10001 = this.editorText.substring(0, this.cursorPos);
        this.editorText = var10001 + "\n" + this.editorText.substring(this.cursorPos);
        ++this.cursorPos;
        this.syncToManager();
        this.scrollToCursor();
        return true;
    }

    public boolean method_25400(class_11905 event) {
        char chr = '\u0000';
        char[] c = this.getAllChars(event);
        if (c.length > 0) {
            chr = c[0];
        } else {
            int[] ints = this.getAllInts(event);
            if (ints.length > 0) {
                chr = (char)ints[0];
            }
        }
        if (this.editorFocused && chr >= ' ') {
            this.deleteSelection();
            this.editorText = this.editorText.substring(0, this.cursorPos) + chr + this.editorText.substring(this.cursorPos);
            ++this.cursorPos;
            this.syncToManager();
            this.scrollToCursor();
            return true;
        }
        return super.method_25400(event);
    }

    private String getClipboard() {
        class_309 keyboard = class_310.method_1551().field_1774;
        for (String name : new String[]{"method_1456", "method_1454", "getClipboard"}) {
            try {
                Method m = keyboard.getClass().getMethod(name, new Class[0]);
                Object r = m.invoke((Object)keyboard, new Object[0]);
                if (!(r instanceof String)) continue;
                return (String)r;
            }
            catch (Exception m) {
                // empty catch block
            }
        }
        try {
            long handle = this.getWindowHandle();
            if (handle != 0L) {
                Class<?> glfw = Class.forName("org.lwjgl.glfw.GLFW");
                Method m = glfw.getMethod("glfwGetClipboardString", Long.TYPE);
                Object r = m.invoke(null, handle);
                if (r instanceof String) {
                    return (String)r;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    private long getWindowHandle() {
        try {
            class_1041 window = class_310.method_1551().method_22683();
            for (Field f : window.getClass().getDeclaredFields()) {
                if (f.getType() != Long.TYPE) continue;
                f.setAccessible(true);
                long v = (Long)f.get(window);
                if (v == 0L) continue;
                return v;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return 0L;
    }

    private void deleteSelection() {
        if (this.selStart != -1) {
            int a = Math.min(this.selStart, this.cursorPos);
            int b = Math.max(this.selStart, this.cursorPos);
            String var10001 = this.editorText.substring(0, a);
            this.editorText = var10001 + this.editorText.substring(b);
            this.cursorPos = a;
            this.selStart = -1;
            this.syncToManager();
        }
    }

    private void syncToManager() {
        String[] lines = this.editorText.split("\n", -1);
        AutoManager.multiSendEditorLines.clear();
        String[] var2 = lines;
        int var3 = lines.length;
        for (int var4 = 0; var4 < var3; ++var4) {
            String l = var2[var4];
            AutoManager.multiSendEditorLines.add(l);
        }
    }

    private void scrollToCursor() {
        String[] lines = this.editorText.split("\n", -1);
        int charOff = 0;
        for (int i = 0; i < lines.length; ++i) {
            if (this.cursorPos <= charOff + lines[i].length()) {
                int lineY = i * 12;
                if (lineY - this.editorScrollY < 0) {
                    this.editorScrollY = lineY;
                }
                if (lineY + 10 - this.editorScrollY <= this.textAreaH - 8) break;
                this.editorScrollY = lineY + 10 - this.textAreaH + 8;
                break;
            }
            charOff += lines[i].length() + 1;
        }
    }

    private void moveCursorVertical(int delta, boolean shift) {
        if (!shift) {
            this.selStart = -1;
        } else if (this.selStart == -1) {
            this.selStart = this.cursorPos;
        }
        String[] lines = this.editorText.split("\n", -1);
        int charOff = 0;
        for (int i = 0; i < lines.length; ++i) {
            if (this.cursorPos <= charOff + lines[i].length()) {
                int posInLine = this.cursorPos - charOff;
                int targetLine = i + delta;
                if (targetLine < 0) {
                    this.cursorPos = 0;
                    return;
                }
                if (targetLine >= lines.length) {
                    this.cursorPos = this.editorText.length();
                    return;
                }
                int targetOff = 0;
                for (int j = 0; j < targetLine; ++j) {
                    targetOff += lines[j].length() + 1;
                }
                this.cursorPos = targetOff + Math.min(posInLine, lines[targetLine].length());
                this.scrollToCursor();
                return;
            }
            charOff += lines[i].length() + 1;
        }
    }

    private int getLineStart(int pos) {
        String[] lines = this.editorText.split("\n", -1);
        int off = 0;
        String[] var4 = lines;
        int var5 = lines.length;
        for (int var6 = 0; var6 < var5; ++var6) {
            String l = var4[var6];
            if (pos <= off + l.length()) {
                return off;
            }
            off += l.length() + 1;
        }
        return 0;
    }

    private int getLineEnd(int pos) {
        String[] lines = this.editorText.split("\n", -1);
        int off = 0;
        String[] var4 = lines;
        int var5 = lines.length;
        for (int var6 = 0; var6 < var5; ++var6) {
            String l = var4[var6];
            if (pos <= off + l.length()) {
                return off + l.length();
            }
            off += l.length() + 1;
        }
        return this.editorText.length();
    }

    private void rebuildScreen() {
        this.method_37067();
        this.method_25426();
    }

    private void lookupPlayer(String nick) {
        class_268 team;
        this.foundNick = nick;
        this.foundPrefix = "";
        this.foundSuffix = "";
        if (!nick.isEmpty() && this.field_22787.field_1687 != null && (team = this.field_22787.field_1687.method_8428().method_1164(nick)) != null) {
            this.foundPrefix = ClanModScreen4.toLegacy(team.method_1144());
            this.foundSuffix = ClanModScreen4.toLegacy(team.method_1136());
        }
    }

    private void copyTabToFile() {
        if (this.field_22787 != null && this.field_22787.method_1562() != null) {
            File dir = new File("config/clanmod");
            dir.mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dir, "tab_players.txt"), false));){
                class_269 scoreboard = this.field_22787.field_1687 != null ? this.field_22787.field_1687.method_8428() : null;
                Iterator var4 = this.field_22787.method_1562().method_2880().iterator();
                while (true) {
                    class_268 team;
                    if (!var4.hasNext()) {
                        ClanChatHandler.addLog("[TAB] \u0421\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u043e " + this.field_22787.method_1562().method_2880().size() + " \u2192 config/clanmod/tab_players.txt", -16711766);
                        break;
                    }
                    class_640 info = (class_640)var4.next();
                    String nick = ClanModScreen4.getNick(info);
                    String prefix = "";
                    String suffix = "";
                    if (scoreboard != null && (team = scoreboard.method_1164(nick)) != null) {
                        prefix = ClanModScreen4.toLegacy(team.method_1144());
                        suffix = ClanModScreen4.toLegacy(team.method_1136());
                    }
                    pw.println((prefix + nick + suffix).trim());
                }
            }
            catch (IOException var12) {
                ClanChatHandler.addLog("[TAB] \u041e\u0448\u0438\u0431\u043a\u0430: " + var12.getMessage(), -48060);
            }
        } else {
            ClanChatHandler.addLog("[TAB] \u041d\u0435\u0442 \u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u044f \u043a \u0441\u0435\u0440\u0432\u0435\u0440\u0443", -48060);
        }
    }

    private static String getNick(class_640 info) {
        try {
            Field f = info.method_2966().getClass().getDeclaredField("name");
            f.setAccessible(true);
            Object v = f.get(info.method_2966());
            return v != null ? v.toString() : "";
        }
        catch (Exception var3) {
            return "";
        }
    }

    private static String toLegacy(class_2561 component) {
        StringBuilder sb = new StringBuilder();
        component.method_27658((style, text) -> {
            if (style.method_10973() != null) {
                class_5251 tc = style.method_10973();
                class_124 fmt = class_124.method_533((String)tc.method_27721());
                if (fmt != null) {
                    sb.append("&").append(fmt.method_36145());
                } else {
                    sb.append(String.format("&#%06X", tc.method_27716() & 0xFFFFFF));
                }
            }
            if (Boolean.TRUE.equals(style.method_10984())) {
                sb.append("&l");
            }
            if (Boolean.TRUE.equals(style.method_10966())) {
                sb.append("&o");
            }
            if (Boolean.TRUE.equals(style.method_10965())) {
                sb.append("&n");
            }
            if (Boolean.TRUE.equals(style.method_10986())) {
                sb.append("&m");
            }
            if (Boolean.TRUE.equals(style.method_10987())) {
                sb.append("&k");
            }
            sb.append(text);
            return Optional.empty();
        }, class_2583.field_24360);
        return sb.toString();
    }

    public boolean method_25421() {
        return false;
    }
}
