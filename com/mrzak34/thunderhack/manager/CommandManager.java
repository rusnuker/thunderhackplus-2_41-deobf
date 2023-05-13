//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.command.commands.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class CommandManager
{
    private final ArrayList<Command> commands;
    private String prefix;
    
    public CommandManager() {
        this.commands = new ArrayList<Command>();
        this.prefix = ".";
        this.commands.add((Command)new TpCommand());
        this.commands.add((Command)new BackCommand());
        this.commands.add((Command)new MacroCommand());
        this.commands.add((Command)new BindCommand());
        this.commands.add((Command)new ModuleCommand());
        this.commands.add((Command)new PrefixCommand());
        this.commands.add((Command)new NpbCommand());
        this.commands.add((Command)new loginCommand());
        this.commands.add((Command)new ConfigCommand());
        this.commands.add((Command)new CfgCommand());
        this.commands.add((Command)new SearchCommand());
        this.commands.add((Command)new FriendCommand());
        this.commands.add((Command)new ChangeSkinCommand());
        this.commands.add((Command)new HelpCommand());
        this.commands.add((Command)new StaffCommand());
        this.commands.add((Command)new ReloadCommand());
        this.commands.add((Command)new RPCCommand());
        this.commands.add((Command)new GpsCommand());
        this.commands.add((Command)new KitCommand());
        this.commands.add((Command)new UnloadCommand());
        this.commands.add((Command)new ScannerCommand());
        this.commands.add((Command)new ReloadSoundCommand());
        this.commands.add((Command)new EclipCommand());
        this.commands.add((Command)new HClipCommand());
        this.commands.add((Command)new VClipCommand());
        this.commands.add((Command)new WebHookSetCommand());
        this.commands.add((Command)new DrawCommand());
        this.commands.add((Command)new AutoBuyCommand());
    }
    
    public static String[] removeElement(final String[] input, final int indexToDelete) {
        final LinkedList<String> result = new LinkedList<String>();
        for (int i = 0; i < input.length; ++i) {
            if (i != indexToDelete) {
                result.add(input[i]);
            }
        }
        return result.toArray(input);
    }
    
    private static String strip(final String str, final String key) {
        if (str.startsWith(key) && str.endsWith(key)) {
            return str.substring(key.length(), str.length() - key.length());
        }
        return str;
    }
    
    public void executeCommand(final String command) {
        final String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        final String name = parts[0].substring(1);
        final String[] args = removeElement(parts, 0);
        for (int i = 0; i < args.length; ++i) {
            if (args[i] != null) {
                args[i] = strip(args[i], "\"");
            }
        }
        for (final Command c : this.commands) {
            if (!c.getName().equalsIgnoreCase(name)) {
                continue;
            }
            c.execute(parts);
            return;
        }
        Command.sendMessage(ChatFormatting.GRAY + "Command not found, type 'help' for the commands list.");
    }
    
    public ArrayList<Command> getCommands() {
        return this.commands;
    }
    
    public String getClientMessage() {
        return "[ThunderHack+]";
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
}
