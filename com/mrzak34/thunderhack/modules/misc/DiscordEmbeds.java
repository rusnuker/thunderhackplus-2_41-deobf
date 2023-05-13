//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;
import java.awt.image.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.event.*;
import net.minecraft.util.text.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.modules.player.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.util.concurrent.atomic.*;
import net.minecraft.client.network.*;

public class DiscordEmbeds extends Module
{
    public static boolean nado;
    public static boolean once;
    public static float nigw;
    public static float nigh;
    public static Timer timer;
    static String lasturl;
    static String filename;
    static String formatfila;
    public Setting<Integer> multip;
    public Setting<Integer> posY;
    public Setting<Integer> posX;
    public Setting<Boolean> fgbypass;
    public ResourceLocation logo;
    String discord;
    String last;
    int xvalue;
    int yvalue;
    
    public DiscordEmbeds() {
        super("DiscordEmbeds", "DiscordEmbeds", Category.MISC);
        this.multip = (Setting<Integer>)this.register(new Setting("Scale", (T)200, (T)50, (T)1280));
        this.posY = (Setting<Integer>)this.register(new Setting("PosY", (T)0, (T)0, (T)1000));
        this.posX = (Setting<Integer>)this.register(new Setting("PosX", (T)0, (T)(-1000), (T)1000));
        this.fgbypass = (Setting<Boolean>)this.register(new Setting("FunnyGame", (T)false));
        this.logo = PNGtoResourceLocation.getTexture(DiscordEmbeds.filename, DiscordEmbeds.formatfila);
        this.discord = "";
        this.last = "";
        this.xvalue = 0;
        this.yvalue = 0;
    }
    
    public static String codeFGBypass(final String raw) {
        String final_string = raw.replace("https://cdn.discordapp.com/attachments/", "THCRYPT");
        final_string = final_string.replace("0", "\u0430");
        final_string = final_string.replace("1", "\u0431");
        final_string = final_string.replace("2", "\u0432");
        final_string = final_string.replace("3", "\u0433");
        final_string = final_string.replace("4", "\u0434");
        final_string = final_string.replace("5", "\u0435");
        final_string = final_string.replace("6", "\u0436");
        final_string = final_string.replace("7", "\u0437");
        final_string = final_string.replace("8", "\u0438");
        final_string = final_string.replace("9", "\u0439");
        final_string = final_string.replace("/", "\u043a");
        final_string = final_string.replace(".png", "\u043e");
        final_string = final_string.replace(".", "\u043b");
        final_string = final_string.replace("-", "\u043c");
        final_string = final_string.replace("_", "\u043d");
        return final_string;
    }
    
    public static void saveDickPick(final String s, final String format) {
        if (Objects.equals(DiscordEmbeds.lasturl, s) && DiscordEmbeds.nado) {
            return;
        }
        try {
            DiscordEmbeds.lasturl = s;
            final URL url = new URL(s);
            final URLConnection openConnection = url.openConnection();
            boolean check = true;
            try {
                openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                openConnection.connect();
                if (openConnection.getContentLength() > 8000000) {
                    System.out.println(" file size is too big.");
                    check = false;
                }
            }
            catch (Exception e) {
                System.out.println("Couldn't create a connection to the link, please recheck the link.");
                check = false;
                e.printStackTrace();
            }
            if (check) {
                BufferedImage img = null;
                try {
                    final InputStream in = new BufferedInputStream(openConnection.getInputStream());
                    final ByteArrayOutputStream out = new ByteArrayOutputStream();
                    final byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    final byte[] response = out.toByteArray();
                    img = ImageIO.read(new ByteArrayInputStream(response));
                }
                catch (Exception e2) {
                    System.out.println(" couldn't read an image from this link.");
                    e2.printStackTrace();
                }
                try {
                    final int niggermod = (int)(Math.random() * 10000.0);
                    ImageIO.write(img, format, new File("ThunderHack/temp/embeds/" + niggermod + "." + format));
                    DiscordEmbeds.filename = String.valueOf(niggermod);
                    DiscordEmbeds.formatfila = format;
                    DiscordEmbeds.once = true;
                }
                catch (IOException e3) {
                    System.out.println("Couldn't create/send the output image.");
                    e3.printStackTrace();
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketChatMessage) {
            final CPacketChatMessage pac = (CPacketChatMessage)e.getPacket();
            if (((ICPacketChatMessage)pac).getMessage().contains("https://cdn.discordapp.com/attachments/") && this.fgbypass.getValue()) {
                ((ICPacketChatMessage)pac).setMessage("!" + codeFGBypass(((ICPacketChatMessage)pac).getMessage().replace("!", "")));
            }
        }
    }
    
    public String decodeFGBypass(final String coded) {
        String final_string = coded.split("THCRYPT")[1];
        final_string = final_string.replace("\u0430", "0");
        final_string = final_string.replace("\u0431", "1");
        final_string = final_string.replace("\u0432", "2");
        final_string = final_string.replace("\u0433", "3");
        final_string = final_string.replace("\u0434", "4");
        final_string = final_string.replace("\u0435", "5");
        final_string = final_string.replace("\u0436", "6");
        final_string = final_string.replace("\u0437", "7");
        final_string = final_string.replace("\u0438", "8");
        final_string = final_string.replace("\u0439", "9");
        final_string = final_string.replace("\u043a", "/");
        final_string = final_string.replace("\u043e", ".png");
        final_string = final_string.replace("\u043b", ".");
        final_string = final_string.replace("\u043c", "-");
        final_string = final_string.replace("\u043d", "_");
        Command.sendMessage("https://cdn.discordapp.com/attachments/" + final_string);
        return "https://cdn.discordapp.com/attachments/" + final_string;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            if (packet.getType() == ChatType.GAME_INFO || this.check(packet.getChatComponent().getFormattedText())) {}
        }
    }
    
    private boolean check(final String message) {
        if (message.contains("THCRYPT")) {
            this.check(this.decodeFGBypass(message));
        }
        if (message.contains("discordapp") && message.contains(".png")) {
            this.discord = message;
            try {
                final String[] splitted = this.discord.split("https://");
                final String url = "https://" + splitted[1];
                final String[] splitted2 = url.split(".png");
                this.last = splitted2[0] + ".png";
                final ITextComponent cancel2 = (ITextComponent)new TextComponentString(this.last);
                if (Objects.equals(this.solvename(message), "err")) {
                    final ITextComponent cancel3 = (ITextComponent)new TextComponentString("\u041f\u043e\u043b\u0443\u0447\u0435\u043d\u0430 \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0430 \u0447\u0435\u0440\u0435\u0437 THCRYPT [\u041f\u041e\u041a\u0410\u0417\u0410\u0422\u042c]");
                    cancel3.setStyle(cancel3.getStyle().setColor(TextFormatting.AQUA).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cancel2)));
                    Command.sendIText(cancel3);
                }
                else {
                    final ITextComponent cancel3 = (ITextComponent)new TextComponentString("<" + this.solvename(message) + "> \u041e\u0442\u043f\u0440\u0430\u0432\u0438\u043b \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0443 [Show Discord Image]");
                    cancel3.setStyle(cancel3.getStyle().setColor(TextFormatting.AQUA).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cancel2)));
                    Command.sendIText(cancel3);
                }
            }
            catch (Exception ex) {}
        }
        if (message.contains("discordapp") && message.contains(".jpg")) {
            this.discord = message;
            try {
                final String[] splitted = this.discord.split("https://");
                final String url = "https://" + splitted[1];
                final String[] splitted2 = url.split(".jpg");
                this.last = splitted2[0] + ".jpg";
                final ITextComponent cancel2 = (ITextComponent)new TextComponentString(this.last);
                final ITextComponent cancel3 = (ITextComponent)new TextComponentString("<" + this.solvename(message) + "> \u041e\u0442\u043f\u0440\u0430\u0432\u0438\u043b \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0443 [Show Discord Image]");
                cancel3.setStyle(cancel3.getStyle().setColor(TextFormatting.AQUA).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cancel2)));
                Command.sendIText(cancel3);
            }
            catch (Exception ex2) {}
        }
        return true;
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final GuiScreen guiscreen = DiscordEmbeds.mc.currentScreen;
        if (DiscordEmbeds.nado && guiscreen instanceof GuiChat && !Objects.equals(DiscordEmbeds.filename, "") && !Objects.equals(DiscordEmbeds.formatfila, "")) {
            if (DiscordEmbeds.once) {
                this.logo = PNGtoResourceLocation.getTexture(DiscordEmbeds.filename, DiscordEmbeds.formatfila);
                DiscordEmbeds.once = false;
            }
            if (this.logo != null) {
                Util.mc.getTextureManager().bindTexture(this.logo);
                ElytraSwap.drawCompleteImage(this.xvalue - DiscordEmbeds.nigw / 2.0f + this.posX.getValue(), this.yvalue - DiscordEmbeds.nigh / 2.0f + this.posY.getValue(), (int)DiscordEmbeds.nigw, (int)DiscordEmbeds.nigh);
            }
        }
        if (!DiscordEmbeds.nado) {
            this.logo = null;
        }
        if (DiscordEmbeds.timer.passedMs(500L)) {
            DiscordEmbeds.nado = false;
        }
        final ScaledResolution sr = new ScaledResolution(DiscordEmbeds.mc);
        this.xvalue = Mouse.getX() / 2;
        this.yvalue = (sr.getScaledHeight() - Mouse.getY()) / 2;
    }
    
    public String solvename(final String notsolved) {
        final AtomicReference<String> mb = new AtomicReference<String>("err");
        final AtomicReference<String> atomicReference;
        Objects.requireNonNull(Util.mc.getConnection()).getPlayerInfoMap().forEach(player -> {
            if (notsolved.contains(player.getGameProfile().getName())) {
                atomicReference.set(player.getGameProfile().getName());
            }
            return;
        });
        return mb.get();
    }
    
    static {
        DiscordEmbeds.nado = false;
        DiscordEmbeds.once = false;
        DiscordEmbeds.nigw = 0.0f;
        DiscordEmbeds.nigh = 0.0f;
        DiscordEmbeds.timer = new Timer();
        DiscordEmbeds.lasturl = "";
        DiscordEmbeds.filename = "";
        DiscordEmbeds.formatfila = "";
    }
}
