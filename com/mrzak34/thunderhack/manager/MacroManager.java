//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import java.util.concurrent.*;
import com.mrzak34.thunderhack.macro.*;
import java.io.*;
import java.util.*;

public class MacroManager
{
    private static CopyOnWriteArrayList<Macro> macros;
    
    public static void addMacro(final Macro macro) {
        if (!MacroManager.macros.contains(macro)) {
            MacroManager.macros.add(macro);
        }
    }
    
    public static void onLoad() {
        MacroManager.macros = new CopyOnWriteArrayList<Macro>();
        try {
            final File file = new File("ThunderHack/misc/macro.txt");
            if (file.exists()) {
                try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    while (reader.ready()) {
                        final String[] nameandkey = reader.readLine().split(":");
                        final String name = nameandkey[0];
                        final String key = nameandkey[1];
                        final String command = nameandkey[2];
                        addMacro(new Macro(name, command, Integer.parseInt(key)));
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void saveMacro() {
        final File file = new File("ThunderHack/misc/macro.txt");
        try {
            new File("ThunderHack").mkdirs();
            file.createNewFile();
        }
        catch (Exception ex) {}
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (final Macro macro : MacroManager.macros) {
                writer.write(macro.getName() + ":" + macro.getBind() + ":" + macro.getText() + "\n");
            }
        }
        catch (Exception ex2) {}
    }
    
    public void removeMacro(final Macro macro) {
        MacroManager.macros.remove(macro);
    }
    
    public CopyOnWriteArrayList<Macro> getMacros() {
        return MacroManager.macros;
    }
    
    public Macro getMacroByName(final String name) {
        for (final Macro macro : this.getMacros()) {
            if (macro.getName().equalsIgnoreCase(name)) {
                return macro;
            }
        }
        return null;
    }
    
    static {
        MacroManager.macros = new CopyOnWriteArrayList<Macro>();
    }
}
