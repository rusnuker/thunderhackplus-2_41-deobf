//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.text.*;
import net.minecraft.util.math.*;

public class HClipCommand extends Command
{
    public HClipCommand() {
        super("hclip");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("\u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439 .hclip <\u0447\u0438\u0441\u043b\u043e>");
            }
            else {
                Command.sendMessage("Try .hclip <number>");
            }
            return;
        }
        if (commands.length == 2) {
            try {
                if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                    Command.sendMessage(TextFormatting.GREEN + "\u043a\u043b\u0438\u043f\u0430\u0435\u043c\u0441\u044f \u043d\u0430  " + Double.valueOf(commands[0]) + " \u0431\u043b\u043e\u043a\u043e\u0432.");
                }
                else {
                    Command.sendMessage(TextFormatting.GREEN + "clipping to  " + Double.valueOf(commands[0]) + " blocks.");
                }
                final float f = this.mc.player.rotationYaw * 0.017453292f;
                final double speed = Double.valueOf(commands[0]);
                final double x = -(MathHelper.sin(f) * speed);
                final double z = MathHelper.cos(f) * speed;
                this.mc.player.setPosition(this.mc.player.posX + x, this.mc.player.posY, this.mc.player.posZ + z);
            }
            catch (Exception ex) {}
        }
    }
}
