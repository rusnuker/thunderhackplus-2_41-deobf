//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import net.minecraft.util.math.*;

public class GpsCommand extends Command
{
    public GpsCommand() {
        super("gps");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("\u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439 .gps off / .gps x y");
            }
            else {
                Command.sendMessage("Try .gps off / .gps x y");
            }
        }
        else if (commands.length == 2) {
            if (Objects.equals(commands[0], "off")) {
                Thunderhack.gps_position = null;
            }
        }
        else if (commands.length > 2) {
            final BlockPos pos = Thunderhack.gps_position = new BlockPos(Integer.parseInt(commands[0]), 0, Integer.parseInt(commands[1]));
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("GPS \u043d\u0430\u0441\u0442\u0440\u043e\u0435\u043d \u043d\u0430 X: " + pos.getX() + " Z: " + pos.getZ());
            }
            else {
                Command.sendMessage("GPS is set to X: " + pos.getX() + " Z: " + pos.getZ());
            }
        }
    }
}
