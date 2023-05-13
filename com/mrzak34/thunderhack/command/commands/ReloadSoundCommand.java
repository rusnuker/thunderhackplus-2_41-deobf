//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mojang.realmsclient.gui.*;

public class ReloadSoundCommand extends Command
{
    public ReloadSoundCommand() {
        super("sound");
    }
    
    public void execute(final String[] commands) {
        try {
            ((ISoundHandler)this.mc.getSoundHandler()).getSoundManager().reloadSoundSystem();
            Command.sendMessage(ChatFormatting.GREEN + "Reloaded Sound System.");
        }
        catch (Exception e) {
            Command.sendMessage(ChatFormatting.RED + "Couldnt Reload Sound System!");
        }
    }
}
