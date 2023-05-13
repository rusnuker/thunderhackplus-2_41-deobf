//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.modules.*;

public class DrawCommand extends Command
{
    public DrawCommand() {
        super("draw");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage("\u041d\u0430\u043f\u0438\u0448\u0438 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043c\u043e\u0434\u0443\u043b\u044f");
            return;
        }
        final String moduleName = commands[0];
        final Module module = Thunderhack.moduleManager.getModuleByName(moduleName);
        if (module == null) {
            Command.sendMessage("\u041d\u0435\u0438\u0437\u0432\u0435\u0441\u0442\u043d\u044b\u0439 \u043c\u043e\u0434\u0443\u043b\u044c'" + module + "'!");
            return;
        }
        module.setDrawn(!module.isDrawn());
        BindCommand.sendMessage("\u041c\u043e\u0434\u0443\u043b\u044c " + ChatFormatting.GREEN + module.getName() + ChatFormatting.WHITE + " \u0442\u0435\u043f\u0435\u0440\u044c " + (module.isDrawn() ? "\u0432\u0438\u0434\u0435\u043d \u0432 ArrayList" : "\u043d\u0435 \u0432\u0438\u0434\u0435\u043d \u0432 ArrayList"));
    }
}
