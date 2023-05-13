//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.gui.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.*;

public class AutoRespawn extends Module
{
    private final Timer timer;
    public Setting<Boolean> deathcoords;
    public Setting<Boolean> autokit;
    public Setting<String> kit;
    public Setting<Boolean> autohome;
    
    public AutoRespawn() {
        super("AutoRespawn", "\u0430\u0432\u0442\u043e\u0440\u0435\u0441\u043f\u0430\u0432\u043d \u0441 \u0430\u0432\u0442\u043e\u043a\u0438\u0442\u043e\u043c", Category.PLAYER);
        this.deathcoords = (Setting<Boolean>)this.register(new Setting("deathcoords", (T)true));
        this.autokit = (Setting<Boolean>)this.register(new Setting("Auto Kit", (T)false));
        this.kit = (Setting<String>)this.register(new Setting("kit name", (T)"kitname", v -> this.autokit.getValue()));
        this.autohome = (Setting<Boolean>)this.register(new Setting("Auto Home", (T)false));
        this.timer = new Timer();
    }
    
    @Override
    public void onTick() {
        if (fullNullCheck()) {
            return;
        }
        if (this.timer.passedMs(2100L)) {
            this.timer.reset();
        }
        if (AutoRespawn.mc.currentScreen instanceof GuiGameOver) {
            AutoRespawn.mc.player.respawnPlayer();
            AutoRespawn.mc.displayGuiScreen((GuiScreen)null);
        }
        if (AutoRespawn.mc.currentScreen instanceof GuiGameOver && this.timer.getPassedTimeMs() > 200L) {
            if (this.autokit.getValue()) {
                AutoRespawn.mc.player.sendChatMessage("/kit " + this.kit.getValue());
            }
            if (this.deathcoords.getValue()) {
                Command.sendMessage(ChatFormatting.GOLD + "[PlayerDeath] " + ChatFormatting.YELLOW + (int)AutoRespawn.mc.player.posX + " " + (int)AutoRespawn.mc.player.posY + " " + (int)AutoRespawn.mc.player.posZ);
            }
            this.timer.reset();
        }
        if (AutoRespawn.mc.currentScreen instanceof GuiGameOver && this.timer.getPassedTimeMs() > 1000L) {
            if (this.autohome.getValue()) {
                AutoRespawn.mc.player.sendChatMessage("/home");
            }
            if (this.deathcoords.getValue()) {
                Command.sendMessage(ChatFormatting.GOLD + "[PlayerDeath] " + ChatFormatting.YELLOW + (int)AutoRespawn.mc.player.posX + " " + (int)AutoRespawn.mc.player.posY + " " + (int)AutoRespawn.mc.player.posZ);
            }
            this.timer.reset();
        }
    }
}
