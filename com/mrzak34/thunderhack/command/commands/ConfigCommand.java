//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.manager.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class ConfigCommand extends Command
{
    public ConfigCommand() {
        super("config");
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                sendMessage("\u041a\u043e\u043d\u0444\u0438\u0433\u0438 \u0441\u043e\u0445\u0440\u0430\u043d\u044f\u044e\u0442\u0441\u044f \u0432  ThunderHack/configs/");
            }
            else {
                sendMessage("Configurations are saved in ThunderHack/configs/");
            }
            return;
        }
        if (commands.length == 2) {
            if ("list".equals(commands[0])) {
                final StringBuilder configs = new StringBuilder("Configs: ");
                for (final String str : Objects.requireNonNull(ConfigManager.getConfigList())) {
                    configs.append("\n- ").append(str);
                }
                sendMessage(configs.toString());
            }
            else if ("dir".equals(commands[0])) {
                try {
                    Desktop.getDesktop().browse(new File("ThunderHack/configs/").toURI());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                sendMessage("\u041d\u0435\u0442 \u0442\u0430\u043a\u043e\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u044b!... \u041c\u043e\u0436\u0435\u0442 list ?");
            }
            else {
                sendMessage("Wrong command!... Maybe list?");
            }
        }
        if (commands.length >= 3) {
            final String s = commands[0];
            switch (s) {
                case "save":
                case "create": {
                    ConfigManager.save(commands[1]);
                }
                case "set":
                case "load": {
                    ConfigManager.load(commands[1]);
                }
                default: {
                    if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                        sendMessage("\u041d\u0435\u0442 \u0442\u0430\u043a\u043e\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u044b! \u041f\u0440\u0438\u043c\u0435\u0440 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f: <save/load>");
                        break;
                    }
                    sendMessage("Wrong command! try: <save/load>");
                    break;
                }
            }
        }
    }
}
