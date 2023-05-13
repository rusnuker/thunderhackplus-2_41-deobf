//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.*;

public class UnloadCommand extends Command
{
    public UnloadCommand() {
        super("unload");
    }
    
    public void execute(final String[] commands) {
        Thunderhack.unload(true);
    }
}
