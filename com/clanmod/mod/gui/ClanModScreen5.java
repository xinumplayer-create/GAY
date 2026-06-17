/*
 * ClanMod — Screen 5: Small Caps Toggle
 */
package com.clanmod.mod.gui;

import com.clanmod.mod.gui.ClanModScreen;
import com.clanmod.mod.gui.ClanModScreen2;
import com.clanmod.mod.gui.ClanModScreen3;
import com.clanmod.mod.gui.ClanModScreen4;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

public class ClanModScreen5
extends class_437 {

    private static final int BTN_H = 16;
    private static final int PAD   = 6;

    // Флаги
    public static boolean ruMixEnabled      = false; // частичная замена: к→ᴋ е→ᴇ з→ɜ а→ᴀ р→ᴘ б→ҕ
    public static boolean enSmallCapsEnabled = false; // полная EN small caps

    // RU Mix: только 6 букв заменяются, остальные остаются
    // нижний регистр
    private static final char[] RU_MIX_FROM = {'к','е','з','а','р','б','К','Е','З','А','Р','Б'};
    private static final char[] RU_MIX_TO   = {'\u1d0b','\u1d07','\u025c','\u1d00','\u1d18','\u0495',
                                                '\u1d0b','\u1d07','\u025c','\u1d00','\u1d18','\u0495'};

    // EN full small caps
    private static final String EN_NORMAL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String EN_SMALL  = "\u1d00\u0299\u1d04\u1d05\u1d07\ua730\u0262\u029c\u026a\u1d0a\u1d0b\u029f\u1d0d\u0274\u1d0f\u1d18\u01eb\u0280\u0073\u1d1b\u1d1c\u1d20\u1d21\u0078\u028f\u1d22\u1d00\u0299\u1d04\u1d05\u1d07\ua730\u0262\u029c\u026a\u1d0a\u1d0b\u029f\u1d0d\u0274\u1d0f\u1d18\u01eb\u0280\u0073\u1d1b\u1d1c\u1d20\u1d21\u0078\u028f\u1d22";

    private class_4185 ruMixBtn;
    private class_4185 enToggleBtn;

    public ClanModScreen5() {
        super((class_2561)class_2561.method_43470((String)"ClanMod \u2014 Small Caps"));
    }

    protected void method_25426() {
        int halfW  = this.field_22789 / 2;
        int colW   = halfW - 12;
        int leftX  = 6;
        int rightX = halfW + 6;
        int y      = 60;

        // --- Кнопка RU Mix ---
        this.ruMixBtn = class_4185.method_46430(
            (class_2561)this.getRuMixLabel(),
            btn -> {
                ruMixEnabled = !ruMixEnabled;
                btn.method_25355(this.getRuMixLabel());
            }
        ).method_46434(leftX, y, colW, BTN_H).method_46431();
        this.method_37063((class_364)this.ruMixBtn);

        // --- Кнопка EN Small Caps ---
        this.enToggleBtn = class_4185.method_46430(
            (class_2561)this.getEnLabel(),
            btn -> {
                enSmallCapsEnabled = !enSmallCapsEnabled;
                btn.method_25355(this.getEnLabel());
            }
        ).method_46434(rightX, y, colW, BTN_H).method_46431();
        this.method_37063((class_364)this.enToggleBtn);

        // --- Навигация снизу слева ---
        int bottomY = this.field_22790 - 26;
        int navSize = 20;

        this.method_37063((class_364)class_4185.method_46430(
            (class_2561)class_2561.method_43470((String)"1"),
            btn -> this.field_22787.method_1507((class_437)new ClanModScreen())
        ).method_46434(PAD, bottomY, navSize, navSize).method_46431());

        this.method_37063((class_364)class_4185.method_46430(
            (class_2561)class_2561.method_43470((String)"2"),
            btn -> this.field_22787.method_1507((class_437)new ClanModScreen2())
        ).method_46434(PAD + (navSize + 2), bottomY, navSize, navSize).method_46431());

        this.method_37063((class_364)class_4185.method_46430(
            (class_2561)class_2561.method_43470((String)"3"),
            btn -> this.field_22787.method_1507((class_437)new ClanModScreen3())
        ).method_46434(PAD + (navSize + 2) * 2, bottomY, navSize, navSize).method_46431());

        this.method_37063((class_364)class_4185.method_46430(
            (class_2561)class_2561.method_43470((String)"4"),
            btn -> this.field_22787.method_1507((class_437)new ClanModScreen4())
        ).method_46434(PAD + (navSize + 2) * 3, bottomY, navSize, navSize).method_46431());

        this.method_37063((class_364)class_4185.method_46430(
            (class_2561)class_2561.method_43470((String)"\u00a7e5"),
            btn -> {}
        ).method_46434(PAD + (navSize + 2) * 4, bottomY, navSize, navSize).method_46431());

        // Close
        this.method_37063((class_364)class_4185.method_46430(
            (class_2561)class_2561.method_43470((String)"Close"),
            btn -> this.method_25419()
        ).method_46434(this.field_22789 / 2 - 25, bottomY, 50, BTN_H).method_46431());
    }

    @Override
    public void method_25420(class_332 g, int mouseX, int mouseY, float partialTick) {
        g.method_25294(0, 0, this.field_22789, this.field_22790, -872415232);
    }

    @Override
    public void method_25394(class_332 g, int mouseX, int mouseY, float delta) {
        super.method_25394(g, mouseX, mouseY, delta);

        int halfW = this.field_22789 / 2;
        int colW  = halfW - 12;
        int leftX = 6;
        int rightX = halfW + 6;

        // Разделитель
        g.method_25294(halfW - 1, 10, halfW + 1, this.field_22790 - 35, 0x66FFFFFF);

        // Заголовок
        g.method_27534(this.field_22793,
            (class_2561)class_2561.method_43470((String)"\u2014 SMALL CAPS \u2014")
                .method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))),
            this.field_22789 / 2, 12, -10496);

        // Подзаголовки
        g.method_27534(this.field_22793,
            (class_2561)class_2561.method_43470((String)"\u0420\u0423\u0421\u0421\u041a\u0418\u0415 \u0411\u0423\u041a\u0412\u042b (mix)")
                .method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))),
            leftX + colW / 2, 28, -10748030);

        g.method_27534(this.field_22793,
            (class_2561)class_2561.method_43470((String)"ENGLISH LETTERS")
                .method_27696(class_2583.field_24360.method_10982(Boolean.valueOf(true))),
            rightX + colW / 2, 28, -10772737);

        // Примеры — левая колонка
        g.method_51439(this.field_22793,
            (class_2561)class_2561.method_43470((String)"\u00a77\u043f\u0440\u0438\u043c\u0435\u0440: \u043f\u0440\u0438\u0432\u0435\u0442"),
            leftX, 85, -5592406, false);
        g.method_51439(this.field_22793,
            (class_2561)class_2561.method_43470((String)"\u00a77\u2192 \u043f\u1d18\u0438\u0432\u1d07\u0442"),
            leftX, 97, -5592406, false);
        g.method_51439(this.field_22793,
            (class_2561)class_2561.method_43470((String)"\u00a77\u043a\u0430\u043a \u0434\u0435\u043b\u0430 \u2192 \u1d0b\u1d00\u1d0b \u0434\u1d07\u043b\u1d00"),
            leftX, 109, -5592406, false);

        // Примеры — правая колонка
        g.method_51439(this.field_22793,
            (class_2561)class_2561.method_43470((String)"\u00a77Example: hello"),
            rightX, 85, -5592406, false);
        g.method_51439(this.field_22793,
            (class_2561)class_2561.method_43470((String)"\u00a77\u2192 \u029c\u1d07\u029f\u029f\u1d0f"),
            rightX, 97, -5592406, false);

        // Статусы
        String ruStatus = ruMixEnabled      ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b";
        String enStatus = enSmallCapsEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b";

        g.method_51439(this.field_22793,
            (class_2561)class_2561.method_43470((String)("RU mix: " + ruStatus)),
            6, this.field_22790 - 52, -1, false);
        g.method_51439(this.field_22793,
            (class_2561)class_2561.method_43470((String)("EN small caps: " + enStatus)),
            6, this.field_22790 - 42, -1, false);
    }

    @Override
    public boolean method_25421() {
        return false;
    }

    private class_2561 getRuMixLabel() {
        return class_2561.method_43470((String)(
            "RU Mix: " + (ruMixEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")
        ));
    }

    private class_2561 getEnLabel() {
        return class_2561.method_43470((String)(
            "EN Small Caps: " + (enSmallCapsEnabled ? "\u00a7a\u0412\u041a\u041b" : "\u00a7c\u0412\u042b\u041a\u041b")
        ));
    }

    /**
     * Вызывай из ChatPacketMixin перед отправкой сообщения.
     */
    public static String transform(String input) {
        if (!ruMixEnabled && !enSmallCapsEnabled) return input;

        StringBuilder sb = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            boolean replaced = false;

            // EN small caps (полная замена)
            if (enSmallCapsEnabled) {
                int idx = EN_NORMAL.indexOf(c);
                if (idx >= 0) {
                    sb.append(EN_SMALL.charAt(idx));
                    replaced = true;
                }
            }

            // RU mix (частичная замена: к е з а р б)
            if (!replaced && ruMixEnabled) {
                for (int j = 0; j < RU_MIX_FROM.length; j++) {
                    if (c == RU_MIX_FROM[j]) {
                        sb.append(RU_MIX_TO[j]);
                        replaced = true;
                        break;
                    }
                }
            }

            if (!replaced) sb.append(c);
        }
        return sb.toString();
    }
}