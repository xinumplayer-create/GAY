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
 */
package com.clanmod.mod.gui;

import com.clanmod.mod.config.ModConfig;
import com.clanmod.mod.events.ClanChatHandler;
import com.clanmod.mod.gui.ClanModScreen2;
import com.clanmod.mod.gui.ClanModScreen3;
import com.clanmod.mod.gui.ClanModScreen4;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

public class ClanModScreen
extends class_437 {
    private ModConfig config = ModConfig.getInstance();
    private static final int LEFT_X = 6;
    private static final int PAD = 6;
    private static final int BTN_W = 170;
    private static final int BTN_H = 20;
    private static final int SPACING = 3;
    private static final int MID_X = 185;
    private static final int MID_SEP = 4;
    private static final int ROW_H = 22;
    private static final int CON_PAD = 4;
    private static final int LINE_H = 10;
    private int logScrollOffset = 0;
    private int scrollOffset = 0;
    private int blacklistScrollOffset = 0;
    private class_342 addKwField;
    private class_342 addCmdField;
    private boolean addRowVisible = false;
    private class_342 addNickField;
    private int blHeaderY = 0;
    private int blacklistListStartY = 0;
    private final List<class_342> kwFields = new ArrayList<class_342>();
    private final List<class_342> cmdFields = new ArrayList<class_342>();

    public ClanModScreen() {
        super((class_2561)class_2561.method_43470((String)"ClanMod"));
    }

    private int fieldW() {
        int available = this.consoleX() - 6 - 185 - 20 - 12;
        return Math.max(80, available / 2);
    }

    private int consoleX() {
        return Math.max(405, this.field_22789 - 280);
    }

    protected void method_25426() {
        int conW;
        int fw;
        this.kwFields.clear();
        this.cmdFields.clear();
        int leftY = 10;
        this.method_37063((class_364)class_4185.method_46430((class_2561)this.getAutoGiveLabel(), btn -> {
            this.config.autoGiveEnabled = !this.config.autoGiveEnabled;
            this.config.save();
            btn.method_25355(this.getAutoGiveLabel());
        }).method_46434(6, leftY, 170, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)this.getJoinLabel(), btn -> {
            this.config.balanceOnJoinEnabled = !this.config.balanceOnJoinEnabled;
            this.config.save();
            btn.method_25355(this.getJoinLabel());
        }).method_46434(6, leftY += 23, 170, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)this.getLeaveLabel(), btn -> {
            this.config.balanceOnLeaveEnabled = !this.config.balanceOnLeaveEnabled;
            this.config.save();
            btn.method_25355(this.getLeaveLabel());
        }).method_46434(6, leftY += 23, 170, 20).method_46431());
        this.blHeaderY = (leftY += 23) + 5;
        int blInputY = this.blHeaderY + 13;
        int nickFieldW = 148;
        this.addNickField = new class_342(this.field_22793, 6, blInputY, nickFieldW, 20, (class_2561)class_2561.method_43470((String)""));
        this.addNickField.method_47404((class_2561)class_2561.method_43470((String)"\u041d\u0438\u043a \u0438\u0433\u0440\u043e\u043a\u0430"));
        this.method_37063((class_364)this.addNickField);
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u2713"), btn -> {
            String nick = this.addNickField.method_1882().trim();
            if (!nick.isEmpty()) {
                String lower = nick.toLowerCase();
                if (!this.config.blacklist.contains(lower)) {
                    this.config.blacklist.add(lower);
                }
                this.config.save();
                this.addNickField.method_1852("");
                this.rebuildScreen();
            }
        }).method_46434(6 + nickFieldW + 2, blInputY, 20, 20).method_46431());
        this.blacklistListStartY = blInputY + 20 + 4;
        int blListAreaH = this.field_22790 - this.blacklistListStartY - 30 - 46 - 8;
        int maxBlVisible = Math.max(1, blListAreaH / 22);
        int blEnd = Math.min(this.config.blacklist.size(), this.blacklistScrollOffset + maxBlVisible);
        int blBtnY2 = this.blacklistScrollOffset;
        while (blBtnY2 < blEnd) {
            fw = this.blacklistListStartY + (blBtnY2 - this.blacklistScrollOffset) * 22;
            int finalBlBtnY2 = blBtnY2++;
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u2717"), btn -> {
                String removedNick = this.config.blacklist.get(finalBlBtnY2);
                this.config.blacklist.remove(finalBlBtnY2);
                this.config.save();
                ClanChatHandler.clearCommandFlood(removedNick);
                if (this.blacklistScrollOffset > 0 && this.blacklistScrollOffset >= this.config.blacklist.size()) {
                    --this.blacklistScrollOffset;
                }
                this.rebuildScreen();
            }).method_46434(6 + nickFieldW + 2, fw, 20, 18).method_46431());
        }
        if (this.config.blacklist.size() > maxBlVisible) {
            int blScrollX = 162;
            fw = this.blacklistListStartY + maxBlVisible * 22 + 2;
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u25b2"), btn -> {
                if (this.blacklistScrollOffset > 0) {
                    --this.blacklistScrollOffset;
                    this.rebuildScreen();
                }
            }).method_46434(blScrollX - 16, fw, 14, 14).method_46431());
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u25bc"), btn -> {
                if (this.blacklistScrollOffset + maxBlVisible < this.config.blacklist.size()) {
                    ++this.blacklistScrollOffset;
                    this.rebuildScreen();
                }
            }).method_46434(blScrollX, fw, 14, 14).method_46431());
        }
        blBtnY2 = this.field_22790 - 26 - 46;
        this.method_37063((class_364)class_4185.method_46430((class_2561)this.getBlacklistJoinLabel(), btn -> {
            this.config.blacklistJoinEnabled = !this.config.blacklistJoinEnabled;
            this.config.save();
            btn.method_25355(this.getBlacklistJoinLabel());
        }).method_46434(6, blBtnY2, 170, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)this.getBlacklistLeaveLabel(), btn -> {
            this.config.blacklistLeaveEnabled = !this.config.blacklistLeaveEnabled;
            this.config.save();
            btn.method_25355(this.getBlacklistLeaveLabel());
        }).method_46434(6, blBtnY2 + 20 + 3, 170, 20).method_46431());
        fw = this.fieldW();
        int midBtnW = fw * 2 + 4;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"+ \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043a\u043e\u043c\u0430\u043d\u0434\u0443"), btn -> {
            boolean bl = this.addRowVisible = !this.addRowVisible;
            if (this.addKwField != null) {
                this.addKwField.method_1862(this.addRowVisible);
                this.addCmdField.method_1862(this.addRowVisible);
            }
        }).method_46434(185, 10, midBtnW, 20).method_46431());
        int addRowY = 32;
        this.addKwField = new class_342(this.field_22793, 185, addRowY, fw, 20, (class_2561)class_2561.method_43470((String)""));
        this.addKwField.method_1880(Short.MAX_VALUE);
        this.addKwField.method_47404((class_2561)class_2561.method_43470((String)"\u0441\u043b\u043e\u0432\u0430 \u0447\u0435\u0440\u0435\u0437 \u0437\u0430\u043f\u044f\u0442\u0443\u044e"));
        this.addKwField.method_1862(this.addRowVisible);
        this.method_37063((class_364)this.addKwField);
        this.addCmdField = new class_342(this.field_22793, 185 + fw + 4, addRowY, fw, 20, (class_2561)class_2561.method_43470((String)""));
        this.addCmdField.method_1880(Short.MAX_VALUE);
        this.addCmdField.method_47404((class_2561)class_2561.method_43470((String)"/\u043a\u043e\u043c\u0430\u043d\u0434\u0430 %\u043d\u0438\u043a"));
        this.addCmdField.method_1862(this.addRowVisible);
        this.method_37063((class_364)this.addCmdField);
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u2713"), btn -> {
            if (this.addRowVisible) {
                String kw = this.addKwField.method_1882().trim();
                String cmd = this.addCmdField.method_1882().trim();
                if (!kw.isEmpty() && !cmd.isEmpty()) {
                    ModConfig.CustomCommand cc = new ModConfig.CustomCommand();
                    cc.keywords = kw;
                    cc.command = cmd;
                    this.config.customCommands.add(cc);
                    this.config.save();
                    this.addKwField.method_1852("");
                    this.addCmdField.method_1852("");
                    this.scrollOffset = 0;
                    this.rebuildScreen();
                }
            }
        }).method_46434(185 + midBtnW + 2, addRowY, 20, 20).method_46431());
        int listStartY = addRowY + 22 + 14;
        int maxVisible = Math.max(1, (this.field_22790 - listStartY - 30) / 22);
        int end = Math.min(this.config.customCommands.size(), this.scrollOffset + maxVisible);
        int bottomY = this.scrollOffset;
        while (bottomY < end) {
            ModConfig.CustomCommand cc = this.config.customCommands.get(bottomY);
            conW = listStartY + (bottomY - this.scrollOffset) * 22;
            class_342 kwField = new class_342(this.field_22793, 185, conW, fw, 18, (class_2561)class_2561.method_43470((String)""));
            kwField.method_1880(Short.MAX_VALUE);
            kwField.method_1852(cc.keywords);
            kwField.method_1863(val -> {
                cc.keywords = val;
            });
            this.method_37063((class_364)kwField);
            this.kwFields.add(kwField);
            class_342 cmdField = new class_342(this.field_22793, 185 + fw + 4, conW, fw, 18, (class_2561)class_2561.method_43470((String)""));
            cmdField.method_1880(Short.MAX_VALUE);
            cmdField.method_1852(cc.command);
            cmdField.method_1863(val -> {
                cc.command = val;
            });
            this.method_37063((class_364)cmdField);
            this.cmdFields.add(cmdField);
            int finalBottomY = bottomY++;
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u2717"), btn -> {
                this.config.customCommands.remove(finalBottomY);
                if (this.scrollOffset > 0 && this.scrollOffset >= this.config.customCommands.size()) {
                    --this.scrollOffset;
                }
                this.rebuildScreen();
            }).method_46434(185 + midBtnW + 2, conW, 20, 18).method_46431());
        }
        bottomY = this.field_22790 - 26;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Close"), btn -> this.method_25419()).method_46434(this.field_22789 / 2 - 25, bottomY, 50, 20).method_46431());
        int navSize = 20;
        int navX = 6;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7e1"), btn -> {}).method_46434(navX, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"2"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen2())).method_46434(navX + navSize + 2, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"3"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen3())).method_46434(navX + (navSize + 2) * 2, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"4"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen4())).method_46434(navX + (navSize + 2) * 3, bottomY, navSize, navSize).method_46431());
        int actionBtnH = 20;
        int actionFolderW = 70;
        int actionReloadW = 80;
        int actionBtnX = 189;
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
            catch (Exception e) {
                e.printStackTrace();
            }
        }).method_46434(actionBtnX, bottomY, actionFolderW, actionBtnH).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7a\ud83d\udd04 \u041e\u0431\u043d\u043e\u0432\u0438\u0442\u044c"), btn -> {
            ModConfig.reloadInstance();
            this.config = ModConfig.getInstance();
            this.rebuildScreen();
        }).method_46434(actionBtnX + actionFolderW + 2, bottomY, actionReloadW, actionBtnH).method_46431());
        int cx = this.consoleX();
        conW = this.field_22789 - cx - 4;
        int clearW = 70;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7c\u00a7lClear logs"), btn -> {
            ClanChatHandler.COMMAND_LOG.clear();
            ClanChatHandler.COMMAND_LOG_COLORS.clear();
            ClanChatHandler.totalCommandCount = 0;
            this.logScrollOffset = 0;
        }).method_46434(cx, bottomY, clearW, 20).method_46431());
    }

    public void method_25420(class_332 g, int mouseX, int mouseY, float partialTick) {
        g.method_25294(0, 0, this.field_22789, this.field_22790, -1342177280);
    }

    public void method_25394(class_332 g, int mouseX, int mouseY, float delta) {
        Object entry;
        int counterX;
        int sbH;
        int conBottom;
        int blBtnRenderY;
        super.method_25394(g, mouseX, mouseY, delta);
        int fw = this.fieldW();
        int addRowY = 32;
        int listStart = addRowY + 22 + 14;
        int cx = this.consoleX();
        int conW = this.field_22789 - cx - 4;
        int nickFieldW = 148;
        g.method_25294(179, 5, 180, this.field_22790 - 5, -2002081110);
        g.method_25294(cx - 5, 5, cx - 4, this.field_22790 - 5, -2002081110);
        g.method_27535(this.field_22793, (class_2561)class_2561.method_43470((String)"\u0427\u0401\u0420\u041d\u042b\u0419 \u0421\u041f\u0418\u0421\u041e\u041a").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), 6, this.blHeaderY, -30720);
        int blListAreaH = this.field_22790 - this.blacklistListStartY - 30 - 46 - 8;
        int maxBlVisible = Math.max(1, blListAreaH / 22);
        int blEnd = Math.min(this.config.blacklist.size(), this.blacklistScrollOffset + maxBlVisible);
        for (blBtnRenderY = this.blacklistScrollOffset; blBtnRenderY < blEnd; ++blBtnRenderY) {
            String nick = this.config.blacklist.get(blBtnRenderY);
            conBottom = this.blacklistListStartY + (blBtnRenderY - this.blacklistScrollOffset) * 22;
            g.method_25294(6, conBottom, 6 + nickFieldW, conBottom + 20 - 2, 0x55000000);
            g.method_51433(this.field_22793, nick, 10, conBottom + 5, -1, false);
        }
        blBtnRenderY = this.field_22790 - 26 - 46;
        g.method_27535(this.field_22793, (class_2561)class_2561.method_43470((String)"\u0427\u0451\u0440\u043d\u044b\u0439 \u0441\u043f\u0438\u0441\u043e\u043a:").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), 6, blBtnRenderY - 10, -30720);
        g.method_27535(this.field_22793, (class_2561)class_2561.method_43470((String)"\u041a\u043b\u044e\u0447\u0435\u0432\u044b\u0435 \u0441\u043b\u043e\u0432\u0430").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), 185, listStart - 10, -22016);
        g.method_27535(this.field_22793, (class_2561)class_2561.method_43470((String)"\u041a\u043e\u043c\u0430\u043d\u0434\u0430 (/\u043a\u043e\u043c\u0430\u043d\u0434\u0430 %\u043d\u0438\u043a)").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), 185 + fw + 4, listStart - 10, -22016);
        int conTop = 5;
        conBottom = this.field_22790 - 28;
        int maxLines = Math.max(1, (conBottom - conTop) / 10);
        g.method_25294(cx, conTop, cx + conW, conBottom, -1442840576);
        List<String> log = ClanChatHandler.COMMAND_LOG;
        List<Integer> colors = ClanChatHandler.COMMAND_LOG_COLORS;
        int totalLines = log.size();
        int maxOffset = Math.max(0, totalLines - maxLines);
        if (this.logScrollOffset > maxOffset) {
            this.logScrollOffset = maxOffset;
        }
        int startIdx = Math.max(0, totalLines - maxLines - this.logScrollOffset);
        int endIdx = Math.min(totalLines, startIdx + maxLines);
        for (sbH = startIdx; sbH < endIdx; ++sbH) {
            int color;
            counterX = conTop + 4 + (sbH - startIdx) * 10;
            entry = log.get(sbH);
            int n = color = sbH < colors.size() ? colors.get(sbH) : -16711783;
            if (this.field_22793.method_1727((String)entry) > conW - 8 - 4) {
                while (((String)entry).length() > 0 && this.field_22793.method_1727((String)entry + "\u2026") > conW - 8 - 4) {
                    entry = ((String)entry).substring(0, ((String)entry).length() - 1);
                }
                entry = (String)entry + "\u2026";
            }
            g.method_51433(this.field_22793, (String)entry, cx + 4, counterX, color, false);
        }
        if (totalLines > maxLines) {
            sbH = conBottom - conTop;
            counterX = Math.max(10, sbH * maxLines / totalLines);
            int thumbY = conTop + (this.logScrollOffset == 0 ? sbH - counterX : (sbH - counterX) * (maxOffset - this.logScrollOffset) / maxOffset);
            g.method_25294(cx + conW - 3, conTop, cx + conW, conBottom, 0x33FFFFFF);
            g.method_25294(cx + conW - 3, thumbY, cx + conW, thumbY + counterX, -1426063361);
        }
        entry = "\u0412\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u043e: " + ClanChatHandler.totalCommandCount;
        counterX = cx + conW - this.field_22793.method_1727((String)entry) - 4;
        g.method_51433(this.field_22793, (String)entry, counterX, conBottom + 4, -30720, false);
    }

    public boolean method_25401(double mouseX, double mouseY, double scrollX, double scrollY) {
        int cx = this.consoleX();
        if (mouseX >= (double)cx) {
            int addRowY = 5;
            int maxBlVisible = this.field_22790 - 28;
            int maxVisible = Math.max(1, (maxBlVisible - addRowY) / 10);
            int blListAreaH = Math.max(0, ClanChatHandler.COMMAND_LOG.size() - maxVisible);
            if (scrollY > 0.0) {
                this.logScrollOffset = Math.min(this.logScrollOffset + 1, blListAreaH);
            } else if (scrollY < 0.0) {
                this.logScrollOffset = Math.max(0, this.logScrollOffset - 1);
            }
            return true;
        }
        if (mouseX >= 185.0) {
            int addRowY = 32;
            int maxBlVisible = addRowY + 22 + 14;
            int maxVisible = Math.max(1, (this.field_22790 - maxBlVisible - 30) / 22);
            if (scrollY < 0.0 && this.scrollOffset + maxVisible < this.config.customCommands.size()) {
                ++this.scrollOffset;
                this.rebuildScreen();
                return true;
            }
            if (scrollY > 0.0 && this.scrollOffset > 0) {
                --this.scrollOffset;
                this.rebuildScreen();
                return true;
            }
            return false;
        }
        if (mouseY >= (double)this.blacklistListStartY) {
            int blListAreaH = this.field_22790 - this.blacklistListStartY - 30 - 46 - 8;
            int maxBlVisible = Math.max(1, blListAreaH / 22);
            if (scrollY < 0.0 && this.blacklistScrollOffset + maxBlVisible < this.config.blacklist.size()) {
                ++this.blacklistScrollOffset;
                this.rebuildScreen();
                return true;
            }
            if (scrollY > 0.0 && this.blacklistScrollOffset > 0) {
                --this.blacklistScrollOffset;
                this.rebuildScreen();
                return true;
            }
        }
        return super.method_25401(mouseX, mouseY, scrollX, scrollY);
    }

    private void rebuildScreen() {
        this.method_37067();
        this.method_25426();
    }

    public boolean method_25421() {
        return false;
    }

    private class_2561 getAutoGiveLabel() {
        return class_2561.method_43470((String)("\u0410\u0432\u0442\u043e-\u0432\u044b\u0434\u0430\u0447\u0430 [\u0447\u0430\u0442]: " + (this.config.autoGiveEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")));
    }

    private class_2561 getJoinLabel() {
        return class_2561.method_43470((String)("\u0412\u0445\u043e\u0434 [GM1+10T]: " + (this.config.balanceOnJoinEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")));
    }

    private class_2561 getLeaveLabel() {
        return class_2561.method_43470((String)("\u0412\u044b\u0445\u043e\u0434 [3.5M]: " + (this.config.balanceOnLeaveEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")));
    }

    private class_2561 getBlacklistJoinLabel() {
        return class_2561.method_43470((String)("\u0412\u0445\u043e\u0434 [GM1+10T]: " + (this.config.blacklistJoinEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")));
    }

    private class_2561 getBlacklistLeaveLabel() {
        return class_2561.method_43470((String)("\u0412\u044b\u0445\u043e\u0434 [3.5M]: " + (this.config.blacklistLeaveEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")));
    }
}
