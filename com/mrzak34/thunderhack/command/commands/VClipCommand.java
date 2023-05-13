//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.text.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class VClipCommand extends Command
{
    public VClipCommand() {
        super("vclip");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("\u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439 .vclip <\u0447\u0438\u0441\u043b\u043e>");
            }
            else {
                Command.sendMessage("Try .vclip <number>");
            }
            return;
        }
        if (commands.length == 2) {
            try {
                if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                    Command.sendMessage(TextFormatting.GREEN + "\u041a\u043b\u0438\u043f\u0430\u0435\u043c\u0441\u044f \u043d\u0430 " + Double.valueOf(commands[0]) + " \u0431\u043b\u043e\u043a\u043e\u0432");
                }
                else {
                    Command.sendMessage(TextFormatting.GREEN + "clipping to  " + Double.valueOf(commands[0]) + " blocks.");
                }
                for (int i = 0; i < 10; ++i) {
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
                }
                for (int i = 0; i < 10; ++i) {
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + Double.parseDouble(commands[0]), this.mc.player.posZ, false));
                }
                this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + Double.parseDouble(commands[0]), this.mc.player.posZ);
            }
            catch (Exception ex) {}
        }
    }
}
