//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.client;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.network.play.server.*;
import org.apache.commons.lang3.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import club.minnced.discord.rpc.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.modules.misc.*;
import java.util.*;
import java.io.*;

public class RPC extends Module
{
    public Setting<mode> Mode;
    public Setting<Boolean> showIP;
    public Setting<Boolean> queue;
    public Setting<smode> sMode;
    public Setting<String> state;
    public Setting<Boolean> nickname;
    public static boolean inQ;
    private static final DiscordRPC rpc;
    public static DiscordRichPresence presence;
    private static Thread thread;
    public static boolean started;
    static String String1;
    public static String position;
    private int kills;
    
    public RPC() {
        super("DiscordRPC", "\u043a\u0440\u0443\u0442\u0430\u044f \u0440\u043f\u0441", Category.CLIENT);
        this.Mode = (Setting<mode>)this.register(new Setting("Picture", (T)mode.MegaCute));
        this.showIP = (Setting<Boolean>)this.register(new Setting("ShowIP", (T)true));
        this.queue = (Setting<Boolean>)this.register(new Setting("Queue", (T)true));
        this.sMode = (Setting<smode>)this.register(new Setting("StateMode", (T)smode.Stats));
        this.state = (Setting<String>)this.register(new Setting("State", (T)"ThunderHack+"));
        this.nickname = (Setting<Boolean>)this.register(new Setting("Nickname", (T)true));
        this.kills = 0;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof SPacketChat && this.queue.getValue()) {
            final SPacketChat packchat = (SPacketChat)e.getPacket();
            final String wtf = packchat.getChatComponent().getUnformattedText();
            RPC.position = StringUtils.substringBetween(wtf, "Position in queue: ", "\nYou can purchase");
            if (wtf.contains("Position in queue")) {
                RPC.inQ = true;
            }
        }
        if (RPC.mc.player.posY < 63.0 || RPC.mc.player.posY > 64.0) {
            RPC.inQ = false;
        }
    }
    
    @Override
    public void onLogout() {
        RPC.inQ = false;
        RPC.position = "";
    }
    
    @Override
    public void onDisable() {
        RPC.started = false;
        if (RPC.thread != null && !RPC.thread.isInterrupted()) {
            RPC.thread.interrupt();
        }
        RPC.rpc.Discord_Shutdown();
    }
    
    @SubscribeEvent
    public void onPlayerDeath(final DeathEvent e) {
        if (Aura.target != null && Aura.target == e.player) {
            ++this.kills;
            return;
        }
        if (C4Aura.target != null && C4Aura.target == e.player) {
            ++this.kills;
            return;
        }
        if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).target != null && ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).target == e.player) {
            ++this.kills;
        }
    }
    
    @Override
    public void onUpdate() {
        if (!RPC.started) {
            RPC.started = true;
            final DiscordEventHandlers handlers = new DiscordEventHandlers();
            RPC.rpc.Discord_Initialize("939112431488225280", handlers, true, "");
            RPC.presence.startTimestamp = System.currentTimeMillis() / 1000L;
            RPC.presence.largeImageText = "v2.41 by Pan4ur#2144";
            RPC.rpc.Discord_UpdatePresence(RPC.presence);
            DiscordRichPresence presence;
            String string;
            DiscordRichPresence presence2;
            String string2;
            String string3;
            final StringBuilder sb;
            (RPC.thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    RPC.rpc.Discord_RunCallbacks();
                    if (!RPC.inQ) {
                        presence = RPC.presence;
                        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu || Minecraft.getMinecraft().currentScreen instanceof GuiScreenServerList || Minecraft.getMinecraft().currentScreen instanceof GuiScreenAddServer) {
                            string = "\u0412 \u0433\u043b\u0430\u0432\u043d\u043e\u043c \u043c\u0435\u043d\u044e";
                        }
                        else if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                            if (this.showIP.getValue()) {
                                string = "\u0418\u0433\u0440\u0430\u0435\u0442 \u043d\u0430 " + Minecraft.getMinecraft().getCurrentServerData().serverIP;
                            }
                            else {
                                string = "\u041d\u041d \u0441\u0435\u0440\u0432\u0435\u0440";
                            }
                        }
                        else {
                            string = "\u0412\u044b\u0431\u0438\u0440\u0430\u0435\u0442 \u0441\u0435\u0440\u0432\u0435\u0440";
                        }
                        presence.details = string;
                    }
                    else {
                        RPC.presence.details = "In queue: " + RPC.position;
                    }
                    presence2 = RPC.presence;
                    if (Util.mc.currentScreen instanceof GuiMainMenu) {
                        string2 = "\u0412 \u0433\u043b\u0430\u0432\u043d\u043e\u043c \u043c\u0435\u043d\u044e";
                    }
                    else {
                        new StringBuilder().append("\u0418\u0433\u0440\u0430\u0435\u0442 ");
                        if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                            if (this.showIP.getValue()) {
                                if (Minecraft.getMinecraft().getCurrentServerData().serverIP.equals("localhost")) {
                                    string3 = "\u043d\u0430 2bt2.org via 2bored2wait";
                                }
                                else {
                                    string3 = "\u043d\u0430 " + Minecraft.getMinecraft().getCurrentServerData().serverIP;
                                }
                            }
                            else {
                                string3 = " \u041d\u041d \u0441\u0435\u0440\u0432\u0435\u0440";
                            }
                        }
                        else {
                            string3 = " \u0427\u0438\u0442\u0435\u0440\u0438\u0442 \u0432 \u043e\u0434\u0438\u043d\u043e\u0447\u043a\u0435";
                        }
                        string2 = sb.append(string3).toString();
                    }
                    presence2.details = string2;
                    if (this.sMode.getValue() == smode.Custom) {
                        RPC.presence.state = this.state.getValue();
                    }
                    else {
                        RPC.presence.state = "Kills: " + this.kills + " | Hacks: " + Thunderhack.moduleManager.getEnabledModules().size() + " / " + Thunderhack.moduleManager.modules.size();
                    }
                    if (this.nickname.getValue()) {
                        if (((NameProtect)Thunderhack.moduleManager.getModuleByClass((Class)NameProtect.class)).isDisabled()) {
                            RPC.presence.smallImageText = "logged as - " + Util.mc.getSession().getUsername();
                        }
                        else {
                            RPC.presence.smallImageText = "logged as - Protected";
                        }
                        RPC.presence.smallImageKey = "https://minotar.net/helm/" + Util.mc.getSession().getUsername() + "/100.png";
                    }
                    switch (this.Mode.getValue()) {
                        case Thlogo: {
                            RPC.presence.largeImageKey = "aboba3";
                            break;
                        }
                        case minecraft: {
                            RPC.presence.largeImageKey = "minecraft";
                            break;
                        }
                        case Unknown: {
                            RPC.presence.largeImageKey = "th";
                            break;
                        }
                        case Konas: {
                            RPC.presence.largeImageKey = "2213";
                            break;
                        }
                        case Astolfo: {
                            RPC.presence.largeImageKey = "astolf";
                            break;
                        }
                        case SlivSRC: {
                            RPC.presence.largeImageKey = "hhh";
                            break;
                        }
                        case pic: {
                            RPC.presence.largeImageKey = "pic";
                            break;
                        }
                        case MegaCute: {
                            RPC.presence.largeImageKey = "https://media1.tenor.com/images/6bcbfcc0be97d029613b54f97845bc59/tenor.gif?itemid=26823781";
                            break;
                        }
                        case Hunger: {
                            RPC.presence.largeImageKey = "https://media.tenor.com/nUNorsu3_RIAAAAd/cat-sweet.gif";
                            break;
                        }
                        case Custom: {
                            readFile();
                            RPC.presence.largeImageKey = RPC.String1.split("SEPARATOR")[0];
                            if (!Objects.equals(RPC.String1.split("SEPARATOR")[1], "none")) {
                                RPC.presence.smallImageKey = RPC.String1.split("SEPARATOR")[1];
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                    }
                    RPC.rpc.Discord_UpdatePresence(RPC.presence);
                    try {
                        Thread.sleep(2000L);
                    }
                    catch (InterruptedException ex) {}
                }
            }, "RPC-Callback-Handler")).start();
        }
    }
    
    public static void readFile() {
        try {
            final File file = new File("ThunderHack/misc/RPC.txt");
            if (file.exists()) {
                try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    while (reader.ready()) {
                        RPC.String1 = reader.readLine();
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void WriteFile(final String url1, final String url2) {
        final File file = new File("ThunderHack/misc/RPC.txt");
        try {
            file.createNewFile();
            try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(url1 + "SEPARATOR" + url2 + '\n');
            }
            catch (Exception ex) {}
        }
        catch (Exception ex2) {}
    }
    
    static {
        RPC.inQ = false;
        rpc = DiscordRPC.INSTANCE;
        RPC.presence = new DiscordRichPresence();
        RPC.String1 = "none";
        RPC.position = "";
    }
    
    public enum mode
    {
        Konas, 
        Custom, 
        Thlogo, 
        Unknown, 
        minecraft, 
        pic, 
        SlivSRC, 
        Astolfo, 
        MegaCute, 
        Hunger;
    }
    
    public enum smode
    {
        Custom, 
        Stats;
    }
}
