//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import org.lwjgl.input.*;
import java.util.*;
import com.mrzak34.thunderhack.macro.*;
import com.mrzak34.thunderhack.manager.*;

public class MacroCommand extends Command
{
    public MacroCommand() {
        super("macro");
    }
    
    public void execute(final String[] args) {
        if (args[0] == null) {
            Command.sendMessage(this.usage());
        }
        if (args[0].equals("list")) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                sendMessage("\u041c\u0430\u043a\u0440\u043e\u0441\u044b:");
            }
            else {
                sendMessage("Macro list:");
            }
            sendMessage(" ");
            String string;
            final StringBuilder sb;
            Thunderhack.macromanager.getMacros().forEach(macro -> {
                new StringBuilder().append(macro.getName());
                if (macro.getBind() != 0) {
                    string = " [" + Keyboard.getKeyName(macro.getBind()) + "]";
                }
                else {
                    string = "";
                }
                sendMessage(sb.append(string).append(" {").append(macro.getText()).append("}").toString());
                return;
            });
        }
        if (args[0].equals("remove")) {
            if (Thunderhack.macromanager.getMacroByName(args[1]) != null) {
                final Macro macro2 = Thunderhack.macromanager.getMacroByName(args[1]);
                Thunderhack.macromanager.removeMacro(macro2);
                if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                    sendMessage("\u0423\u0434\u0430\u043b\u0435\u043d \u043c\u0430\u043a\u0440\u043e\u0441 " + macro2.getName());
                }
                else {
                    sendMessage("Deleted macro " + macro2.getName());
                }
            }
            else if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                sendMessage("\u041d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442 \u043c\u0430\u043a\u0440\u043e\u0441\u0430 \u0441 \u0438\u043c\u0435\u043d\u0435\u043c " + args[1]);
            }
            else {
                sendMessage("Macro " + args[1] + " not exist!");
            }
        }
        if (args.length >= 4) {
            if (args[0].equals("add")) {
                final String name = args[1];
                final String bind = args[2].toUpperCase();
                final String text = String.join(" ", (CharSequence[])Arrays.copyOfRange(args, 3, args.length - 1));
                if (Keyboard.getKeyIndex(bind) == 0) {
                    if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                        sendMessage("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439 \u0431\u0438\u043d\u0434!");
                    }
                    else {
                        sendMessage("Wrong button!");
                    }
                    return;
                }
                final Macro macro3 = new Macro(name, text, Keyboard.getKeyIndex(bind));
                MacroManager.addMacro(macro3);
                if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                    sendMessage("\u0414\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u043c\u0430\u043a\u0440\u043e\u0441 " + name + " \u043d\u0430 \u043a\u043d\u043e\u043f\u043a\u0443 " + Keyboard.getKeyName(macro3.getBind()));
                }
                else {
                    sendMessage("Added macros " + name + " on button " + Keyboard.getKeyName(macro3.getBind()));
                }
            }
            else {
                sendMessage(this.usage());
            }
        }
    }
    
    String usage() {
        return "macro add/remove/list (macro add name key text), (macro remove name)";
    }
}
