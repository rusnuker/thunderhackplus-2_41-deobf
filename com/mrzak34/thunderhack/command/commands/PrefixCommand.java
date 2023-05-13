//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;

public class PrefixCommand extends Command
{
    public PrefixCommand() {
        super("prefix");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage(ChatFormatting.GREEN + "\u0422\u0435\u043a\u0443\u0449\u0438\u0439 \u043f\u0440\u0435\u0444\u0438\u043a\u0441:" + Thunderhack.commandManager.getPrefix());
            }
            else {
                Command.sendMessage(ChatFormatting.GREEN + "current prefix:" + Thunderhack.commandManager.getPrefix());
            }
            return;
        }
        Thunderhack.commandManager.setPrefix(commands[0]);
        if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
            Command.sendMessage("\u041f\u0440\u0435\u0444\u0438\u043a\u0441 \u0438\u0437\u043c\u0435\u043d\u0435\u043d \u043d\u0430  " + ChatFormatting.GRAY + commands[0]);
        }
        else {
            Command.sendMessage("Prefix changed to  " + ChatFormatting.GRAY + commands[0]);
        }
    }
}
