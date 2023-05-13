//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import org.apache.commons.lang3.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.notification.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoAuth extends Module
{
    private String password;
    private final Setting<Mode> passwordMode;
    public Setting<String> cpass;
    public Setting<Boolean> showPasswordInChat;
    
    public AutoAuth() {
        super("AutoAuth", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438-\u043b\u043e\u0433\u0438\u043d\u0438\u0442\u0441\u044f \u043d\u0430 -\u0441\u0435\u0440\u0432\u0435\u0440\u0430\u0445", "AutoAuth", Category.MISC);
        this.passwordMode = (Setting<Mode>)this.register(new Setting("Password Mode", (T)Mode.Custom));
        this.cpass = (Setting<String>)this.register(new Setting("Password", (T)"babidjon777", v -> this.passwordMode.getValue() == Mode.Custom));
        this.showPasswordInChat = (Setting<Boolean>)this.register(new Setting("Show Password In Chat", (T)true));
    }
    
    @Override
    public void onEnable() {
        Command.sendMessage(ChatFormatting.RED + "\u0412\u043d\u0438\u043c\u0430\u043d\u0438\u0435!!! " + ChatFormatting.RESET + "\u041f\u0430\u0440\u043e\u043b\u044c \u0441\u043e\u0445\u0440\u0430\u043d\u044f\u0435\u0442\u0441\u044f \u0432 \u043a\u043e\u043d\u0444\u0438\u0433\u0435, \u043f\u0435\u0440\u0435\u0434 \u043f\u0435\u0440\u0435\u0434\u0430\u0447\u0435\u0439 \u043a\u043e\u043d\u0444\u0438\u0433\u0430 " + ChatFormatting.RED + " \u0412\u042b\u041a\u041b\u042e\u0427\u0418 \u041c\u041e\u0414\u0423\u041b\u042c!");
        Command.sendMessage(ChatFormatting.RED + "\u0412\u043d\u0438\u043c\u0430\u043d\u0438\u0435!!! " + ChatFormatting.RESET + "\u041f\u0430\u0440\u043e\u043b\u044c \u0441\u043e\u0445\u0440\u0430\u043d\u044f\u0435\u0442\u0441\u044f \u0432 \u043a\u043e\u043d\u0444\u0438\u0433\u0435, \u043f\u0435\u0440\u0435\u0434 \u043f\u0435\u0440\u0435\u0434\u0430\u0447\u0435\u0439 \u043a\u043e\u043d\u0444\u0438\u0433\u0430 " + ChatFormatting.RED + " \u0412\u042b\u041a\u041b\u042e\u0427\u0418 \u041c\u041e\u0414\u0423\u041b\u042c!");
        Command.sendMessage(ChatFormatting.RED + "\u0412\u043d\u0438\u043c\u0430\u043d\u0438\u0435!!! " + ChatFormatting.RESET + "\u041f\u0430\u0440\u043e\u043b\u044c \u0441\u043e\u0445\u0440\u0430\u043d\u044f\u0435\u0442\u0441\u044f \u0432 \u043a\u043e\u043d\u0444\u0438\u0433\u0435, \u043f\u0435\u0440\u0435\u0434 \u043f\u0435\u0440\u0435\u0434\u0430\u0447\u0435\u0439 \u043a\u043e\u043d\u0444\u0438\u0433\u0430 " + ChatFormatting.RED + " \u0412\u042b\u041a\u041b\u042e\u0427\u0418 \u041c\u041e\u0414\u0423\u041b\u042c!");
        Command.sendMessage(ChatFormatting.RED + "Attention!!! " + ChatFormatting.RESET + "The password is saved in the config, before sharing the config " + ChatFormatting.RED + " TURN OFF THIS MODULE!");
        Command.sendMessage(ChatFormatting.RED + "Attention!!! " + ChatFormatting.RESET + "The password is saved in the config, before sharing the config " + ChatFormatting.RED + " TURN OFF THIS MODULE!");
        Command.sendMessage(ChatFormatting.RED + "Attention!!! " + ChatFormatting.RESET + "The password is saved in the config, before sharing the config " + ChatFormatting.RED + " TURN OFF THIS MODULE!");
    }
    
    @Override
    public void onDisable() {
        Command.sendMessage(ChatFormatting.RED + "AutoAuth " + ChatFormatting.RESET + "reseting password...");
        this.cpass.setValue("none");
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat pac = (SPacketChat)event.getPacket();
            if (this.passwordMode.getValue() == Mode.Custom) {
                this.password = this.cpass.getValue();
            }
            else if (this.passwordMode.getValue() == Mode.Qwerty) {
                this.password = "qwerty123";
            }
            else if (this.passwordMode.getValue() == Mode.Random) {
                final String str1 = RandomStringUtils.randomAlphabetic(5);
                final String str2 = RandomStringUtils.randomPrint(5);
                this.password = str1 + str2;
            }
            if (this.passwordMode.getValue() == Mode.Custom && (this.password == null || this.password.isEmpty())) {
                return;
            }
            if (pac.getChatComponent().getFormattedText().contains("/reg") || pac.getChatComponent().getFormattedText().contains("/register") || pac.getChatComponent().getFormattedText().contains("\u0417\u0430\u0440\u0435\u0433\u0435\u0441\u0442\u0440\u0438\u0440\u0443\u0439\u0442\u0435\u0441\u044c")) {
                AutoAuth.mc.player.sendChatMessage("/reg " + this.password + " " + this.password);
                if (this.showPasswordInChat.getValue()) {
                    Command.sendMessage("Your password: " + ChatFormatting.RED + this.password);
                }
                if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass((Class)NotificationManager.class)).isEnabled()) {
                    NotificationManager.publicity("You are successfully registered!", 4, Notification.Type.SUCCESS);
                }
            }
            else if (pac.getChatComponent().getFormattedText().contains("\u0410\u0432\u0442\u043e\u0440\u0438\u0437\u0443\u0439\u0442\u0435\u0441\u044c") || pac.getChatComponent().getFormattedText().contains("/l")) {
                AutoAuth.mc.player.sendChatMessage("/login " + this.password);
                if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass((Class)NotificationManager.class)).isEnabled()) {
                    NotificationManager.publicity("You are successfully login!", 4, Notification.Type.SUCCESS);
                }
            }
        }
    }
    
    private enum Mode
    {
        Custom, 
        Random, 
        Qwerty;
    }
}
