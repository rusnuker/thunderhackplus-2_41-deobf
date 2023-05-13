//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import java.util.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;

public class ChangeSkinCommand extends Command
{
    private static ChangeSkinCommand INSTANCE;
    public ArrayList<String> changedplayers;
    
    public ChangeSkinCommand() {
        super("skinset");
        this.changedplayers = new ArrayList<String>();
        this.setInstance();
    }
    
    public static ChangeSkinCommand getInstance() {
        if (ChangeSkinCommand.INSTANCE == null) {
            ChangeSkinCommand.INSTANCE = new ChangeSkinCommand();
        }
        return ChangeSkinCommand.INSTANCE;
    }
    
    private void setInstance() {
        ChangeSkinCommand.INSTANCE = this;
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("skinset \u0438\u043c\u044f\u0438\u0433\u0440\u043e\u043a\u0430 \u0438\u043c\u044f\u0441\u043a\u0438\u043d\u0430");
            }
            else {
                Command.sendMessage("skinset playername skinname");
            }
            return;
        }
        if (commands.length == 2) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("skinset \u0438\u043c\u044f\u0438\u0433\u0440\u043e\u043a\u0430 \u0438\u043c\u044f\u0441\u043a\u0438\u043d\u0430");
            }
            else {
                Command.sendMessage("skinset playername skinname");
            }
            return;
        }
        if (commands.length == 3) {
            ThunderUtils.savePlayerSkin("https://minotar.net/skin/" + commands[1], commands[0]);
            this.changedplayers.add(commands[0]);
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("\u0421\u043a\u0438\u043d \u0438\u0433\u0440\u043e\u043a\u0430 " + commands[0] + " \u0438\u0437\u043c\u0435\u043d\u0435\u043d \u043d\u0430 " + commands[1]);
            }
            else {
                Command.sendMessage("Player " + commands[0] + "'s skin has been changed to " + commands[1]);
            }
        }
    }
    
    static {
        ChangeSkinCommand.INSTANCE = new ChangeSkinCommand();
    }
}
