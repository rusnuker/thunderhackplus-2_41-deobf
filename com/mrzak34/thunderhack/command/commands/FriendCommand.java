//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("friend");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Thunderhack.friendManager.getFriends().isEmpty()) {
                sendMessage("Friend list empty D:.");
            }
            else {
                String f = "Friends: ";
                for (final String friend : Thunderhack.friendManager.getFriends()) {
                    try {
                        f = f + friend + ", ";
                    }
                    catch (Exception ex) {}
                }
                sendMessage(f);
            }
            return;
        }
        if (commands.length != 2) {
            if (commands.length >= 2) {
                final String s = commands[0];
                switch (s) {
                    case "add": {
                        Thunderhack.friendManager.addFriend(commands[1]);
                        sendMessage(ChatFormatting.GREEN + commands[1] + " has been friended");
                        this.mc.player.sendChatMessage("/w " + commands[1] + " i friended u at ThunderHack");
                    }
                    case "del": {
                        Thunderhack.friendManager.removeFriend(commands[1]);
                        sendMessage(ChatFormatting.RED + commands[1] + " has been unfriended");
                    }
                    default: {
                        sendMessage("Unknown Command, try friend add/del (name)");
                        break;
                    }
                }
            }
            return;
        }
        if (commands[0].equals("reset")) {
            Thunderhack.friendManager.clear();
            sendMessage("Friends got reset.");
            return;
        }
        sendMessage(commands[0] + (Thunderhack.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
    }
}
