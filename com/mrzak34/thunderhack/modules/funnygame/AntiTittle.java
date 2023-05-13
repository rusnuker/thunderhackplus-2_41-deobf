//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.command.*;

public class AntiTittle extends Module
{
    public Setting<Boolean> tittle;
    public Setting<Boolean> armorstands;
    public Setting<Boolean> scoreBoard;
    public Setting<Integer> waterMarkZ1;
    public Setting<Integer> waterMarkZ2;
    public Setting<Boolean> counter;
    public Setting<Boolean> chat;
    public Setting<Boolean> donators;
    int count;
    int y1;
    int x1;
    ScaledResolution sr;
    
    public AntiTittle() {
        super("Adblock", "\u0410\u0434\u0431\u043b\u043e\u043a \u0434\u043b\u044f \u0435\u0431\u0443\u0447\u0435\u0433\u043e-\u0444\u0430\u043d\u0438\u0433\u0435\u0439\u043c\u0430", Category.FUNNYGAME);
        this.tittle = (Setting<Boolean>)this.register(new Setting("AntiTitle", (T)true));
        this.armorstands = (Setting<Boolean>)this.register(new Setting("AntiSpawnLag", (T)true));
        this.scoreBoard = (Setting<Boolean>)this.register(new Setting("ScoreBoard", (T)true));
        this.waterMarkZ1 = (Setting<Integer>)this.register(new Setting("Y", (T)10, (T)0, (T)524));
        this.waterMarkZ2 = (Setting<Integer>)this.register(new Setting("X", (T)20, (T)0, (T)862));
        this.counter = (Setting<Boolean>)this.register(new Setting("Counter", (T)false));
        this.chat = (Setting<Boolean>)this.register(new Setting("ChatAds", (T)true));
        this.donators = (Setting<Boolean>)this.register(new Setting("Donators", (T)true));
        this.count = 0;
        this.y1 = 0;
        this.x1 = 0;
        this.sr = new ScaledResolution(AntiTittle.mc);
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        if (this.counter.getValue()) {
            this.y1 = (int)(this.sr.getScaledHeight() / (1000.0f / this.waterMarkZ1.getValue()));
            this.x1 = (int)(this.sr.getScaledWidth() / (1000.0f / this.waterMarkZ2.getValue()));
            RenderUtil.drawSmoothRect(this.waterMarkZ2.getValue(), this.waterMarkZ1.getValue(), (float)(75 + this.waterMarkZ2.getValue()), (float)(11 + this.waterMarkZ1.getValue()), new Color(35, 35, 40, 230).getRGB());
            Util.fr.drawStringWithShadow("Ads Blocked : ", (float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 1), PaletteHelper.astolfo(false, 1).getRGB());
            Util.fr.drawStringWithShadow(String.valueOf(this.count), (float)(this.waterMarkZ2.getValue() + 6 + Util.fr.getStringWidth("Ads Blocked : ")), (float)(this.waterMarkZ1.getValue() + 1), -1);
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (this.tittle.getValue() && e.getPacket() instanceof SPacketTitle) {
            ++this.count;
            e.setCanceled(true);
        }
        if (this.chat.getValue() && e.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)e.getPacket();
            if (this.shouldCancel(packet.getChatComponent().getFormattedText())) {
                e.setCanceled(true);
            }
        }
    }
    
    private boolean shouldCancel(String message) {
        if (message.contains("\u0412\u0441\u0435 \u043e\u0447\u0438\u0441\u0442\u0438\u0442\u0441\u044f \u0447\u0435\u0440\u0435\u0437")) {
            return true;
        }
        if (message.contains("\u041f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u043d\u0430 \u043a\u0430\u0440\u0442\u0435 \u0443\u0441\u043f\u0435\u0448\u043d\u043e")) {
            return true;
        }
        if (message.contains("\u041e\u0431\u044b\u0447\u043d\u044b\u0439 \u0447\u0430\u0442 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043d\u0430")) {
            return true;
        }
        if (message.contains("\u0425\u043e\u0447\u0435\u0448\u044c \u0432\u044b\u0434\u0435\u043b\u0438\u0442\u044c\u0441\u044f \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0435?")) {
            return true;
        }
        if (message.contains("\u0423\u0441\u043f\u0435\u0439 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c \u043f\u0440\u043e\u043c\u043e-\u043a\u043e\u0434")) {
            return true;
        }
        if (message.contains("\u0412 \u0434\u0430\u043d\u043d\u044b\u0439 \u043c\u043e\u043c\u0435\u043d\u0442 \u0434\u0435\u0439\u0441\u0442\u0432\u0443\u044e\u0442 \u0431\u043e\u043b\u044c\u0448\u0438\u0435")) {
            return true;
        }
        if (message.contains("\u0435\u0441\u0442\u044c \u043b\u044e\u0431\u044b\u0435 \u0441\u043f\u043e\u0441\u043e\u0431\u044b \u043e\u043f\u043b\u0430\u0442\u044b")) {
            return true;
        }
        if (message.contains("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043a\u0443\u043f\u043b\u0435\u043d\u043d\u044b\u0435 \u043a\u043b\u044e\u0447\u0438")) {
            return true;
        }
        if (message.contains("\u0413\u0440\u0443\u043f\u043f\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u0412\u041a\u043e\u043d\u0442\u0430\u043a\u0442\u0435")) {
            return true;
        }
        if (message.contains("\u0447\u0435\u043c \u0431\u043e\u043b\u044c\u0448\u0435 \u043a\u043b\u044e\u0447\u0435\u0439 \u0432\u044b \u043f\u043e\u043a\u0443\u043f\u0430\u0435\u0442\u0435")) {
            return true;
        }
        if (message.contains("\u041d\u0435 \u0445\u0432\u0430\u0442\u0430\u0435\u0442 \u0434\u0435\u043d\u0435\u0433 \u043d\u0430 \u043f\u0440\u0438\u0432\u0438\u043b\u0435\u0433\u0438\u044e")) {
            return true;
        }
        if (message.contains("\u041f\u0440\u043e\u0434\u0430\u0432\u0430\u0442\u044c \u0447\u0442\u043e-\u043b\u0438\u0431\u043e \u0437\u0430 \u0440\u0435\u0430\u043b\u044c\u043d\u0443\u044e \u0432\u0430\u043b\u044e\u0442\u0443")) {
            return true;
        }
        if (message.contains("\u0421\u0435\u0439\u0447\u0430\u0441 \u0434\u0435\u0439\u0441\u0442\u0432\u0443\u044e\u0442 \u0431\u043e\u043b\u044c\u0448\u0438\u0435 \u0441\u043a\u0438\u0434\u043a\u0438")) {
            return true;
        }
        if (message.contains("/donate")) {
            return true;
        }
        if (message.contains("\u0427\u0442\u043e\u0431\u044b \u0438\u0437\u0431\u0435\u0436\u0430\u0442\u044c \u0432\u0437\u043b\u043e\u043c\u0430")) {
            return true;
        }
        if (message.contains("\u041e\u0441\u043a\u043e\u0440\u0431\u043b\u0435\u043d\u0438\u0435 \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u0438 \u0441\u0442\u0440\u043e\u0433\u043e")) {
            return true;
        }
        if (message.contains("\u0412\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u043f\u0432\u043f \u0432 \u0441\u0432\u043e\u0435\u043c \u0440\u0435\u0433\u0438\u043e\u043d\u0435")) {
            return true;
        }
        if (message.contains("/trade")) {
            return true;
        }
        if (message.contains("\u041f\u043e\u0441\u043b\u0435 \u0432\u0430\u0439\u043f\u0430 \u043e\u0441\u0442\u0430\u0435\u0442\u0441\u044f \u043f\u0430\u0440\u043e\u043b\u044c+\u043f\u0440\u0438\u0432\u0438\u043b\u0435\u0433\u0438\u044f")) {
            return true;
        }
        if (message.contains("FunnyGame.su")) {
            return true;
        }
        if (this.donators.getValue()) {
            final String premessage = message;
            message = message.replace("§r§6§l[§r§b§l\u041f\u0420\u0415\u0417\u0418\u0414\u0415\u041d\u0422§r§6§l]§r", "§r");
            message = message.replace("§r§d§l[§r§5§l\u0410\u0434\u043c\u0438\u043d§r§d§l]§r", "§r");
            message = message.replace("§r§b§l[§r§3§l\u0413\u043b.\u0410\u0434\u043c\u0438\u043d§r§b§l]§r", "§r");
            message = message.replace("§8[§r§6\u0418\u0433\u0440\u043e\u043a§r§8]§r", "§r");
            message = message.replace("§r§5§l[§r§e§l\u0411\u041e\u0413§r§5§l]§r", "§r");
            message = message.replace("§r§a§l[§r§2§l\u041a\u0440\u0435\u0430\u0442\u0438\u0432§r§a§l]", "§r");
            message = message.replace("§r§4§l[§r§c§l\u0412\u043b\u0430\u0434\u0435\u043b\u0435\u0446§r§4§l]", "§r");
            message = message.replace("§r§5§l[§r§d§l\u041e\u0441\u043d\u043e\u0432\u0430\u0442\u0435\u043b\u044c§r§5§l]", "§r");
            message = message.replace("§r§b§l[§r§e§l?§r§d§l\u0421\u041f\u041e\u041d\u0421\u041e\u0420§r§e§l?§r§b§l]", "§r");
            message = message.replace("§r§6§l[§r§e§l\u041b\u043e\u0440\u0434§r§6§l]", "§r");
            message = message.replace("§r§4§l[§r§2§l\u0412\u041b\u0410\u0414\u042b\u041a\u0410§r§4§l]", "§r");
            if (!message.equals(premessage)) {
                Command.sendMessageWithoutTH(message);
                return true;
            }
        }
        return false;
    }
}
