//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class StaffCommand extends Command
{
    public static List<String> staffNames;
    
    public StaffCommand() {
        super("staff");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (StaffCommand.staffNames.isEmpty()) {
                sendMessage("Staff list empty");
            }
            else {
                final StringBuilder f = new StringBuilder("Staff: ");
                for (final String staff : StaffCommand.staffNames) {
                    try {
                        f.append(staff).append(", ");
                    }
                    catch (Exception ex) {}
                }
                sendMessage(f.toString());
            }
            return;
        }
        if (commands.length != 2) {
            if (commands.length >= 2) {
                final String s = commands[0];
                switch (s) {
                    case "add": {
                        StaffCommand.staffNames.add(commands[1]);
                        sendMessage(ChatFormatting.GREEN + commands[1] + " added to staff list");
                    }
                    case "del": {
                        StaffCommand.staffNames.remove(commands[1]);
                        sendMessage(ChatFormatting.GREEN + commands[1] + " removed from staff list");
                    }
                    default: {
                        sendMessage("Unknown Command, try staff add/del (name)");
                        break;
                    }
                }
            }
            return;
        }
        if ("reset".equals(commands[0])) {
            StaffCommand.staffNames.clear();
            sendMessage("staff list got reset.");
        }
    }
    
    static {
        StaffCommand.staffNames = new ArrayList<String>();
    }
}
