/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_634
 *  net.minecraft.class_7439
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.clanmod.mod.mixin;

import com.clanmod.mod.events.ClanChatHandler;
import net.minecraft.class_634;
import net.minecraft.class_7439;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_634.class})
public class ChatPacketMixin {
    @Inject(method={"method_43596(Lnet/minecraft/class_7439;)V"}, at={@At(value="HEAD")})
    private void onSystemChat(class_7439 packet, CallbackInfo ci) {
        String text = packet.comp_763().getString();
        ClanChatHandler.handlePublic(text);
    }
}
