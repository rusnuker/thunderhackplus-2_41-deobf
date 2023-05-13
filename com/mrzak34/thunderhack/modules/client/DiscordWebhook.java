//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.client;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import org.apache.commons.lang3.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.modules.combat.*;
import java.net.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.*;
import com.google.gson.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.math.*;

public class DiscordWebhook extends Module
{
    private static final String CLIENT_ID = "efce6070269a7f1";
    public Setting<Boolean> ochat;
    public Setting<Boolean> sendToDiscord;
    public Setting<Boolean> SDescr;
    public ByteArrayOutputStream byteArrayOutputStream;
    int killz;
    
    public DiscordWebhook() {
        super("DiscordWebhook", "DiscordWebhook", Category.CLIENT);
        this.ochat = (Setting<Boolean>)this.register(new Setting("OpenChat", (T)false));
        this.sendToDiscord = (Setting<Boolean>)this.register(new Setting("SendToDiscord", (T)true));
        this.SDescr = (Setting<Boolean>)this.register(new Setting("ScreenDescription", (T)true));
        this.killz = 0;
    }
    
    public static String readurl() {
        try {
            final File file = new File("ThunderHack/misc/WHOOK.txt");
            if (!file.exists()) {
                return "none";
            }
            try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                if (reader.ready()) {
                    return reader.readLine();
                }
            }
        }
        catch (Exception ex) {}
        return "none";
    }
    
    public static void saveurl(final String rat) {
        final File file = new File("ThunderHack/misc/WHOOK.txt");
        try {
            new File("ThunderHack").mkdirs();
            file.createNewFile();
            try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(rat + '\n');
            }
            catch (Exception ex) {}
        }
        catch (Exception ex2) {}
    }
    
    public static void sendMsg(final String message, final String webhook) {
        PrintWriter out = null;
        BufferedReader in = null;
        final StringBuilder result = new StringBuilder();
        try {
            final URL realUrl = new URL(webhook);
            final URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            final String postData = URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
            out.print(postData);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append("/n").append(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        System.out.println(result);
    }
    
    public static void sendAuraMsg(final EntityPlayer name, final int killz) {
        final int caps = AutoPot.neededCap;
        new Thread(() -> sendMsg("```" + DiscordWebhook.mc.player.getName() + getAuraWord() + name.getName() + " c \u043f\u043e\u043c\u043e\u0449\u044c\u044e " + DiscordWebhook.mc.player.getHeldItemMainhand().getDisplayName() + "\n\u0423\u0431\u0438\u0439\u0441\u0442\u0432 \u0437\u0430 \u0441\u0435\u0433\u043e\u0434\u043d\u044f: " + killz + "\n\u041a\u0430\u043f\u043f\u0443\u0447\u0438\u043d\u043e \u043f\u043e\u0442\u0440\u0435\u0431\u043e\u0432\u0430\u043b\u043e\u0441\u044c: " + caps + "```", readurl())).start();
        AutoPot.neededCap = 0;
    }
    
    public static String getAuraWord() {
        final int n2 = RandomUtils.nextInt(0, 5);
        switch (n2) {
            case 0: {
                return " \u0443\u0431\u0438\u043b ";
            }
            case 1: {
                return " \u043f\u043e\u043f\u0443\u0441\u0442\u0438\u043b ";
            }
            case 2: {
                return " \u043a\u0438\u043b\u044c\u043d\u0443\u043b ";
            }
            case 3: {
                return " \u0434\u0440\u043e\u043f\u043d\u0443\u043b ";
            }
            case 4: {
                return " \u043e\u0442\u044b\u043c\u0435\u043b ";
            }
            case 5: {
                return " \u043f\u0440\u043e\u043f\u0435\u043d\u0438\u043b ";
            }
            default: {
                return "";
            }
        }
    }
    
    @Override
    public void onEnable() {
        Command.sendMessage("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u043d\u0438\u0435: ");
        Command.sendMessage("1. \u0412\u0432\u0435\u0434\u0438 \u0441\u0441\u044b\u043b\u043a\u0443 \u043d\u0430 \u0432\u0435\u0431\u0445\u0443\u043a (\u0441\u043e \u0441\u0432\u043e\u0435\u0433\u043e \u0434\u0438\u0441\u043a\u043e\u0440\u0434 \u0441\u0435\u0440\u0432\u0435\u0440\u0430) \u0441 \u043f\u043e\u043c\u043e\u0449\u044c\u044e \u043a\u043e\u043c\u0430\u043d\u0434\u044b .whook <\u0441\u0441\u044b\u043b\u043a\u0430> ");
        Command.sendMessage("2. \u0412\u044b\u0431\u0435\u0440\u0438 \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u044f \u0432 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430\u0445 \u043c\u043e\u0434\u0443\u043b\u044f (\u043f\u043e \u0443\u043c\u043e\u043b\u0447\u0430\u043d\u0438\u044e \u043e\u0442\u043f\u0440\u0430\u0432\u043b\u044f\u0435\u0442 \u0441\u043a\u0440\u0438\u043d\u044b, \u043a\u0438\u043b\u044b \u0430\u0443\u0440\u044b, \u043a\u0438\u043b\u044b \u043b\u0443\u043a\u0430, \u0437\u0430\u0445\u043e\u0434\u044b \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0430) ");
        Command.sendMessage("Using: ");
        Command.sendMessage("1. Enter the link to the webhook (from your discord server) using the command .whook <link>");
        Command.sendMessage("2. Choose actions in the module settings (by default sends screenshots, aura kills, bow kills, server joins)");
    }
    
    @SubscribeEvent
    public void onScreenshotEvent(final ScreenshotEvent screenshotEvent) {
        Command.sendMessage("SS Getted!");
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.uploadToImgur(screenshotEvent.getImage());
    }
    
    @SubscribeEvent
    public void onConnectionEvent(final ConnectToServerEvent e) {
        final String msg;
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(6000L);
            }
            catch (InterruptedException var2) {
                var2.printStackTrace();
            }
            msg = "```" + DiscordWebhook.mc.player.getName() + " \u0437\u0430\u0448\u0451\u043b \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440 " + e.getIp() + "```";
            sendMsg(msg, readurl());
        }).start();
    }
    
    @Override
    public void onUpdate() {
        if (Aura.target != null && Aura.target.getHealth() <= 0.0f && Aura.target instanceof EntityPlayer) {
            ++this.killz;
            sendAuraMsg((EntityPlayer)Aura.target, this.killz);
        }
    }
    
    public void uploadToImgur(final BufferedImage bufferedImage) {
        URL uRL;
        HttpURLConnection httpURLConnection;
        byte[] byArray;
        String string2;
        OutputStreamWriter outputStreamWriter;
        String string3;
        final BufferedReader bufferedReader2;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        String string4;
        final Object o;
        JsonObject jsonObject;
        String string5;
        String ip;
        Date date;
        String description;
        new Thread(() -> {
            try {
                uRL = new URL("https://api.imgur.com/3/image");
                httpURLConnection = (HttpURLConnection)uRL.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Authorization", "Client-ID efce6070269a7f1");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.connect();
                ImageIO.write(bufferedImage, "png", this.byteArrayOutputStream);
                this.byteArrayOutputStream.flush();
                byArray = this.byteArrayOutputStream.toByteArray();
                string2 = Base64.getEncoder().encodeToString(byArray);
                outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                string3 = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(string2, "UTF-8");
                outputStreamWriter.write(string3);
                outputStreamWriter.flush();
                new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                bufferedReader = bufferedReader2;
                stringBuilder = new StringBuilder();
                while (true) {
                    string4 = bufferedReader.readLine();
                    if (o != null) {
                        stringBuilder.append(string4).append("\n");
                    }
                    else {
                        break;
                    }
                }
                outputStreamWriter.close();
                bufferedReader.close();
                jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
                string5 = jsonObject.get("data").getAsJsonObject().get("link").getAsString();
                if (this.ochat.getValue()) {
                    DiscordWebhook.mc.displayGuiScreen((GuiScreen)new GuiChat(string5));
                }
                ip = "\u043e\u0448\u0438\u0431\u043a\u0430";
                try {
                    ip = Objects.requireNonNull(DiscordWebhook.mc.getCurrentServerData()).serverIP;
                }
                catch (Exception ex) {}
                date = new Date(System.currentTimeMillis());
                description = "```\u0421\u043a\u0440\u0438\u043d \u0441\u0434\u0435\u043b\u0430\u043d \u0438\u0433\u0440\u043e\u043a\u043e\u043c " + DiscordWebhook.mc.player.getName() + "\n" + date + "\n\u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0435 " + ip + "```";
                if (this.sendToDiscord.getValue()) {
                    sendMsg(string5, readurl());
                    if (this.SDescr.getValue()) {
                        sendMsg(description, readurl());
                    }
                }
            }
            catch (Exception exception) {
                Command.sendMessage(exception.getMessage());
            }
        }).start();
    }
    
    @SubscribeEvent
    public void onTotemPop(final TotemPopEvent e) {
        if (Aura.target == e.getEntity() || C4Aura.target == e.getEntity() || this.getEntityUnderMouse(100) == e.getEntity()) {
            final String str;
            new Thread(() -> {
                str = "```" + DiscordWebhook.mc.player.getName() + " " + this.getWord() + e.getEntity().getName() + "```";
                sendMsg(str, readurl());
            }).start();
        }
    }
    
    public String getWord() {
        final int n2 = RandomUtils.nextInt(0, 3);
        switch (n2) {
            case 0: {
                return " \u0434\u0430\u043b \u0442\u043e\u0442\u0435\u043c ";
            }
            case 1: {
                return " \u0441\u043d\u044f\u043b \u0442\u043e\u0442\u0435\u043c ";
            }
            case 2: {
                return " \u043e\u0442\u0436\u0430\u043b \u0442\u043e\u0442\u0435\u043c \u0443 ";
            }
            case 3: {
                return " \u043f\u043e\u043f\u043d\u0443\u043b ";
            }
            default: {
                return "";
            }
        }
    }
    
    public EntityPlayer getEntityUnderMouse(final int range) {
        final Entity entity = DiscordWebhook.mc.getRenderViewEntity();
        if (entity != null) {
            Vec3d pos = DiscordWebhook.mc.player.getPositionEyes(1.0f);
            for (float i = 0.0f; i < range; i += 0.5f) {
                pos = pos.add(DiscordWebhook.mc.player.getLookVec().scale(0.5));
                for (final EntityPlayer player : DiscordWebhook.mc.world.playerEntities) {
                    if (player == DiscordWebhook.mc.player) {
                        continue;
                    }
                    AxisAlignedBB bb = player.getEntityBoundingBox();
                    if (bb == null) {
                        continue;
                    }
                    if (player.getDistance((Entity)DiscordWebhook.mc.player) > 6.0f) {
                        bb = bb.grow(0.5);
                    }
                    if (bb.contains(pos)) {
                        return player;
                    }
                }
            }
        }
        return null;
    }
}
