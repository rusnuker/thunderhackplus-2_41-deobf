//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { GuiConnecting.class }, priority = 999)
public class MixinGuiConnecting extends MixinGuiScreen
{
    @Inject(method = { "connect" }, at = { @At("HEAD") })
    private void connectHook(final String ip, final int port, final CallbackInfo ci) {
        final ConnectToServerEvent event = new ConnectToServerEvent(ip);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
