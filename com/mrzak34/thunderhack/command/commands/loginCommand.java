//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command.commands;

import com.mrzak34.thunderhack.command.*;
import net.minecraft.client.*;
import java.lang.reflect.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;

public class loginCommand extends Command
{
    public loginCommand() {
        super("login");
    }
    
    public static void login(final String string) {
        try {
            final Field field = Minecraft.class.getDeclaredField("session");
            field.setAccessible(true);
            final Field field2 = Field.class.getDeclaredField("modifiers");
            field2.setAccessible(true);
            field2.setInt(field, field.getModifiers() & 0xFFFFFFEF);
            field.set(Util.mc, new Session(string, "", "", "mojang"));
        }
        catch (Exception exception) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("\u041d\u0435\u0432\u0435\u0440\u043d\u043e\u0435 \u0438\u043c\u044f! " + exception);
            }
            else {
                Command.sendMessage("Wrong name! " + exception);
            }
        }
    }
    
    public void execute(final String[] var1) {
        try {
            login(var1[0]);
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("\u0410\u043a\u043a\u0430\u0443\u043d\u0442 \u0438\u0437\u043c\u0435\u043d\u0435\u043d \u043d\u0430: " + Util.mc.getSession().getUsername());
            }
            else {
                Command.sendMessage("Account switched to: " + Util.mc.getSession().getUsername());
            }
        }
        catch (Exception exception) {
            if (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).language.getValue() == MainSettings.Language.RU) {
                Command.sendMessage("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435: .login nick");
            }
            else {
                Command.sendMessage("Try: .login nick");
            }
        }
    }
}
