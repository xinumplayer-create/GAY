/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_2583
 *  net.minecraft.class_332
 *  net.minecraft.class_342
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 */
package com.clanmod.mod.gui;

import com.clanmod.mod.events.AutoManager;
import com.clanmod.mod.gui.ClanModScreen;
import com.clanmod.mod.gui.ClanModScreen3;
import com.clanmod.mod.gui.ClanModScreen4;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

public class ClanModScreen2
extends class_437 {
    private static final int BTN_H = 16;
    private static final int PAD = 6;
    private class_4185 adToggleBtn;
    private class_4185 clickToggleBtn;
    private class_342 adCmdField;

    public ClanModScreen2() {
        super((class_2561)class_2561.method_43470((String)"ClanMod \u2014 \u0410\u0432\u0442\u043e"));
    }

    protected void method_25426() {
        int halfW = this.field_22789 / 2;
        int colW = halfW - 12;
        int leftX = 6;
        int rightX = halfW + 6;
        int y = 50;
        this.adCmdField = new class_342(this.field_22793, leftX, y, colW, 16, (class_2561)class_2561.method_43470((String)""));
        this.adCmdField.method_1880(Short.MAX_VALUE);
        this.adCmdField.method_47404((class_2561)class_2561.method_43470((String)"/\u043a\u043e\u043c\u0430\u043d\u0434\u0430 \u0438\u043b\u0438 \u0442\u0435\u043a\u0441\u0442"));
        this.adCmdField.method_1852(AutoManager.adCommand);
        this.adCmdField.method_1863(val -> {
            AutoManager.adCommand = val;
        });
        this.method_37063((class_364)this.adCmdField);
        this.adToggleBtn = class_4185.method_46430((class_2561)this.getAdLabel(), btn -> {
            AutoManager.adEnabled = !AutoManager.adEnabled;
            btn.method_25355(this.getAdLabel());
        }).method_46434(leftX, y += 24, colW, 16).method_46431();
        this.method_37063((class_364)this.adToggleBtn);
        y = 50;
        this.clickToggleBtn = class_4185.method_46430((class_2561)this.getClickerLabel(), btn -> {
            AutoManager.clickEnabled = !AutoManager.clickEnabled;
            btn.method_25355(this.getClickerLabel());
        }).method_46434(rightX, y, colW, 16).method_46431();
        this.method_37063((class_364)this.clickToggleBtn);
        int bottomY = this.field_22790 - 26;
        int navSize = 20;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"1"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen())).method_46434(6, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u00a7e2"), btn -> {}).method_46434(6 + navSize + 2, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"3"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen3())).method_46434(6 + (navSize + 2) * 2, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"4"), btn -> this.field_22787.method_1507((class_437)new ClanModScreen4())).method_46434(6 + (navSize + 2) * 3, bottomY, navSize, navSize).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Close"), btn -> this.method_25419()).method_46434(this.field_22789 / 2 - 25, bottomY, 50, 16).method_46431());
    }

    public void method_25420(class_332 g, int mouseX, int mouseY, float partialTick) {
        g.method_25294(0, 0, this.field_22789, this.field_22790, -872415232);
    }

    public void method_25394(class_332 g, int mouseX, int mouseY, float delta) {
        super.method_25394(g, mouseX, mouseY, delta);
        int halfW = this.field_22789 / 2;
        int colW = halfW - 12;
        int leftX = 6;
        int rightX = halfW + 6;
        g.method_25294(halfW - 1, 10, halfW + 1, this.field_22790 - 35, 0x66FFFFFF);
        g.method_27534(this.field_22793, (class_2561)class_2561.method_43470((String)"\u2014 \u0410\u0412\u0422\u041e-\u041f\u0410\u041d\u0415\u041b\u042c \u2014").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), this.field_22789 / 2, 12, -10496);
        g.method_27534(this.field_22793, (class_2561)class_2561.method_43470((String)"\u0420\u0415\u041a\u041b\u0410\u041c\u0410 \u041a\u041b\u0410\u041d\u0410").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), leftX + colW / 2, 28, -10748030);
        g.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)"\u041a\u043e\u043c\u0430\u043d\u0434\u0430 / \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435:"), leftX, 38, -5592406, false);
        g.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)"\u00a77\u0418\u043d\u0442\u0435\u0440\u0432\u0430\u043b: 2\u20133 \u043c\u0438\u043d\u0443\u0442\u044b (\u0441\u043b\u0443\u0447\u0430\u0439\u043d\u043e)"), leftX, 100, -5592406, false);
        int var10002 = AutoManager.adCount;
        g.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)("\u00a77\u041e\u0442\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u043e \u0440\u0435\u043a\u043b\u0430\u043c: \u00a7f" + var10002)), leftX, 114, -5592406, false);
        g.method_27534(this.field_22793, (class_2561)class_2561.method_43470((String)"\u0410\u0412\u0422\u041e \u041a\u041b\u0418\u041a\u0415\u0420 \u041d\u0410 \u0411\u041e\u0422\u0410").method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))), rightX + colW / 2, 28, -10772737);
        g.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)"\u00a77\u2022 \u041a\u043b\u0438\u043a \u043f\u043e \u0446\u0435\u043d\u0442\u0440\u0443 \u044d\u043a\u0440\u0430\u043d\u0430 (\u041b\u041a\u041c)"), rightX, 76, -5592406, false);
        g.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)"\u00a77\u2022 \u041f\u0430\u0443\u0437\u0430 \u2192 \u0441\u0434\u0432\u0438\u0433 -100px \u2192 \u041b\u041a\u041c"), rightX, 88, -5592406, false);
        g.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)"\u00a77\u2022 \u0418\u043d\u0442\u0435\u0440\u0432\u0430\u043b: 2\u20133 \u043c\u0438\u043d (\u0441\u043b\u0443\u0447\u0430\u0439\u043d\u043e)"), rightX, 100, -5592406, false);
        var10002 = AutoManager.clickCount;
        g.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)("\u00a77\u041a\u043b\u0438\u043a\u043e\u0432 \u0432\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u043e: \u00a7f" + var10002)), rightX, 114, -5592406, false);
        String adStatus = AutoManager.adEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b";
        String clickStatus = AutoManager.clickEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b";
        String adWork = AutoManager.isAdRunning() ? " \u00a7a[\u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442]" : " \u00a77[\u043e\u0436\u0438\u0434. \u0437\u0430\u043a\u0440\u044b\u0442\u0438\u044f GUI]";
        String clickWork = AutoManager.isClickRunning() ? " \u00a7a[\u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442]" : " \u00a77[\u043e\u0436\u0438\u0434. \u0437\u0430\u043a\u0440\u044b\u0442\u0438\u044f GUI]";
        g.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)("\u0420\u0435\u043a\u043b\u0430\u043c\u0430: " + adStatus + adWork)), 6, this.field_22790 - 52, -1, false);
        g.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)("\u041a\u043b\u0438\u043a\u0435\u0440: " + clickStatus + clickWork)), 6, this.field_22790 - 42, -1, false);
    }

    public void method_25419() {
        AutoManager.startAdOnClose();
        AutoManager.startClickOnClose();
        super.method_25419();
    }

    public boolean method_25421() {
        return false;
    }

    private class_2561 getAdLabel() {
        return class_2561.method_43470((String)("\u0420\u0435\u043a\u043b\u0430\u043c\u0430: " + (AutoManager.adEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")));
    }

    private class_2561 getClickerLabel() {
        return class_2561.method_43470((String)("\u0410\u0432\u0442\u043e-\u043a\u043b\u0438\u043a\u0435\u0440: " + (AutoManager.clickEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")));
    }
}
