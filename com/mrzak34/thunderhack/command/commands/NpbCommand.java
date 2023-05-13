//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;

public class NpbCommand extends Command
{
    public NpbCommand() {
        super("npb");
    }
    
    public void execute(final String[] args) {
        if (args.length >= 3) {
            final String text = String.join(" ", (CharSequence[])Arrays.copyOfRange(args, 2, args.length - 1));
            Command.sendMessageWithoutTH(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + ChatFormatting.BOLD + "*" + ChatFormatting.RESET + ChatFormatting.GRAY + "] \u0418\u0433\u0440\u043e\u043a " + ChatFormatting.AQUA + ChatFormatting.BOLD + args[0] + ChatFormatting.RESET + ChatFormatting.GRAY + " \u0437\u0430\u0431\u0430\u043d\u0438\u043b " + ChatFormatting.RED + ChatFormatting.BOLD + args[1] + ChatFormatting.RESET + ChatFormatting.GRAY + " \u043d\u0430 10 \u043c\u0438\u043d\u0443\u0442 \u043f\u043e \u043f\u0440\u0438\u0447\u0438\u043d\u0435" + ChatFormatting.GREEN + " " + ChatFormatting.BOLD + text + ChatFormatting.RESET);
        }
    }
}
