//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;

public class ScannerCommand extends Command
{
    public ScannerCommand() {
        super("scanner");
    }
    
    public void execute(final String[] commands) {
        Command.sendMessage("scanner gui loaded");
        final NoCom noCom = Thunderhack.moduleManager.getModuleByClass(NoCom.class);
        NoCom.getgui();
    }
}
