/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
 *  net.minecraft.class_2960
 *  net.minecraft.class_304
 *  net.minecraft.class_304$class_11900
 *  net.minecraft.class_3675$class_307
 *  net.minecraft.class_437
 */
package com.clanmod.mod;

import com.clanmod.mod.config.ModConfig;
import com.clanmod.mod.events.ClanChatHandler;
import com.clanmod.mod.gui.ClanModScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.class_2960;
import net.minecraft.class_304;
import net.minecraft.class_3675;
import net.minecraft.class_437;

public class ClanModClient
implements ClientModInitializer {
    private static class_304 openGuiKey;
    private static final class_304.class_11900 CLANMOD_CATEGORY;

    public void onInitializeClient() {
        ModConfig.load();
        ClanChatHandler.register();
        openGuiKey = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.clanmod.open_gui", class_3675.class_307.field_1672, 268, CLANMOD_CATEGORY));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.method_1436()) {
                if (client.field_1755 != null) continue;
                client.method_1507((class_437)new ClanModScreen());
            }
        });
    }

    static {
        CLANMOD_CATEGORY = class_304.class_11900.method_74698((class_2960)class_2960.method_60655((String)"clanmod", (String)"clanmod"));
    }
}
