//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.command;

import net.minecraft.client.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.modules.*;
import net.minecraft.util.text.*;
import java.util.regex.*;

public abstract class Command
{
    protected String name;
    public Minecraft mc;
    
    public Command(final String name) {
        this.name = name;
        this.mc = Util.mc;
    }
    
    public static void sendMessage(final String message) {
        sendSilentMessage(Thunderhack.commandManager.getClientMessage() + " " + ChatFormatting.GRAY + message);
    }
    
    public static void sendMessageWithoutTH(final String message) {
        sendSilentMessage(ChatFormatting.GRAY + message);
    }
    
    public static void sendSilentMessage(final String message) {
        if (Module.fullNullCheck()) {
            return;
        }
        Util.mc.player.sendMessage((ITextComponent)new ChatMessage(message));
    }
    
    public static void sendIText(final ITextComponent message) {
        if (Module.fullNullCheck()) {
            return;
        }
        Util.mc.player.sendMessage(message);
    }
    
    public static String getCommandPrefix() {
        return Thunderhack.commandManager.getPrefix();
    }
    
    public abstract void execute(final String[] p0);
    
    public String getName() {
        return this.name;
    }
    
    public static class ChatMessage extends TextComponentBase
    {
        private final String text;
        
        public ChatMessage(final String text) {
            final Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
            final Matcher matcher = pattern.matcher(text);
            final StringBuffer stringBuffer = new StringBuffer();
            while (matcher.find()) {
                final String replacement = matcher.group().substring(1);
                matcher.appendReplacement(stringBuffer, replacement);
            }
            matcher.appendTail(stringBuffer);
            this.text = stringBuffer.toString();
        }
        
        public String getUnformattedComponentText() {
            return this.text;
        }
        
        public ITextComponent createCopy() {
            return null;
        }
        
        public ITextComponent shallowCopy() {
            return (ITextComponent)new ChatMessage(this.text);
        }
    }
}
