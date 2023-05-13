//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import com.google.gson.*;

public class ModuleCommand extends Command
{
    public ModuleCommand() {
        super("module");
    }
    
    public static void setCommandValue(final Module feature, final Setting setting, final JsonElement element) {
        for (final Setting setting2 : feature.getSettings()) {
            if (Objects.equals(setting.getName(), setting2.getName())) {
                final String type = setting2.getType();
                switch (type) {
                    case "Parent": {}
                    case "Boolean": {
                        setting2.setValue(element.getAsBoolean());
                    }
                    case "Double": {
                        setting2.setValue(element.getAsDouble());
                    }
                    case "Float": {
                        setting2.setValue(element.getAsFloat());
                    }
                    case "Integer": {
                        setting2.setValue(element.getAsInt());
                    }
                    case "String": {
                        final String str = element.getAsString();
                        setting2.setValue(str.replace("_", " "));
                    }
                    case "Bind": {
                        final JsonArray array4 = element.getAsJsonArray();
                        setting2.setValue(new Bind.BindConverter().doBackward(array4.get(0)));
                        setting2.getValue().setHold(array4.get(1).getAsBoolean());
                    }
                    case "ColorSetting": {
                        final JsonArray array5 = element.getAsJsonArray();
                        setting2.getValue().setColor(array5.get(0).getAsInt());
                        setting2.getValue().setCycle(array5.get(1).getAsBoolean());
                        setting2.getValue().setGlobalOffset(array5.get(2).getAsInt());
                    }
                    case "PositionSetting": {
                        final JsonArray array6 = element.getAsJsonArray();
                        setting2.getValue().setX(array6.get(0).getAsFloat());
                        setting2.getValue().setY(array6.get(1).getAsFloat());
                    }
                    case "SubBind": {
                        setting2.setValue(new SubBind.SubBindConverter().doBackward(element));
                    }
                    case "Enum": {
                        try {
                            final EnumConverter converter = new EnumConverter(setting2.getValue().getClass());
                            final Enum value = converter.doBackward(element);
                            setting2.setValue((value == null) ? setting2.getDefaultValue() : value);
                        }
                        catch (Exception ex) {}
                        continue;
                    }
                }
            }
        }
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            sendMessage("Modules: ");
            for (final Module.Category category : Thunderhack.moduleManager.getCategories()) {
                String modules = category.getName() + ": ";
                for (final Module module1 : Thunderhack.moduleManager.getModulesByCategory(category)) {
                    modules = modules + (module1.isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED) + module1.getName() + ChatFormatting.WHITE + ", ";
                }
                sendMessage(modules);
            }
            return;
        }
        Module module2 = Thunderhack.moduleManager.getModuleByDisplayName(commands[0]);
        if (module2 == null) {
            module2 = Thunderhack.moduleManager.getModuleByName(commands[0]);
            if (module2 == null) {
                sendMessage("This module doesnt exist.");
                return;
            }
            sendMessage(" This is the original name of the module. Its current name is: " + module2.getDisplayName());
        }
        else {
            if (commands.length == 2) {
                sendMessage(module2.getDisplayName() + " : " + module2.getDescription());
                for (final Setting setting2 : module2.getSettings()) {
                    sendMessage(setting2.getName() + " : " + setting2.getValue() + ", " + setting2.getDescription());
                }
                return;
            }
            if (commands.length == 3) {
                if (commands[1].equalsIgnoreCase("set")) {
                    sendMessage("Please specify a setting.");
                }
                else if (commands[1].equalsIgnoreCase("reset")) {
                    for (final Setting setting3 : module2.getSettings()) {
                        setting3.setValue(setting3.getDefaultValue());
                    }
                }
                else {
                    sendMessage("This command doesnt exist.");
                }
                return;
            }
            if (commands.length == 4) {
                sendMessage("Please specify a value.");
                return;
            }
            final Setting setting4;
            if (commands.length == 5 && (setting4 = module2.getSettingByName(commands[2])) != null) {
                final JsonParser jp = new JsonParser();
                if (setting4.getType().equalsIgnoreCase("String")) {
                    setting4.setValue(commands[3]);
                    sendMessage(ChatFormatting.DARK_GRAY + module2.getName() + " " + setting4.getName() + " has been set to " + commands[3] + ".");
                    return;
                }
                try {
                    if (setting4.getName().equalsIgnoreCase("Enabled")) {
                        if (commands[3].equalsIgnoreCase("true")) {
                            module2.enable();
                        }
                        if (commands[3].equalsIgnoreCase("false")) {
                            module2.disable();
                        }
                    }
                    setCommandValue(module2, setting4, jp.parse(commands[3]));
                }
                catch (Exception e) {
                    sendMessage("Bad Value! This setting requires a: " + setting4.getType() + " value.");
                    return;
                }
                sendMessage(ChatFormatting.GRAY + module2.getName() + " " + setting4.getName() + " has been set to " + commands[3] + ".");
            }
        }
    }
}
