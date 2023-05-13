//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.command.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import net.minecraft.network.play.client.*;

public class AntiTPhere extends Module
{
    public Setting<Integer> delay;
    Timer timer;
    Timer checktimer;
    private final Setting<Modes> mode;
    private boolean flag;
    
    public AntiTPhere() {
        super("AntiTPhere", "AntiTPhere", Category.FUNNYGAME);
        this.delay = (Setting<Integer>)this.register(new Setting("delay", (T)100, (T)1, (T)1000));
        this.timer = new Timer();
        this.checktimer = new Timer();
        this.mode = (Setting<Modes>)this.register(new Setting("Mode", (T)Modes.Back));
        this.flag = false;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            if (packet.getChatComponent().getFormattedText().contains("\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435...") && this.check(packet.getChatComponent().getFormattedText())) {
                this.flag = true;
                this.timer.reset();
            }
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.flag && this.timer.passedMs(this.delay.getValue())) {
            final StringBuilder log = new StringBuilder("\u0422\u0435\u0431\u044f \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u043b\u0438 \u0432 X: " + (int)AntiTPhere.mc.player.posX + " Z: " + (int)AntiTPhere.mc.player.posZ + ". \u0411\u043b\u0438\u0436\u0430\u0439\u0448\u0438\u0435 \u0438\u0433\u0440\u043e\u043a\u0438 : ");
            for (final Entity entity : AntiTPhere.mc.world.loadedEntityList) {
                if (entity instanceof EntityPlayer) {
                    if (entity == AntiTPhere.mc.player) {
                        continue;
                    }
                    log.append(entity.getName()).append(" ");
                }
            }
            Command.sendMessage(String.valueOf(log));
            switch (this.mode.getValue()) {
                case RTP: {
                    AntiTPhere.mc.player.sendChatMessage("/rtp");
                    break;
                }
                case Back: {
                    AntiTPhere.mc.player.sendChatMessage("/back");
                    break;
                }
                case Home: {
                    AntiTPhere.mc.player.sendChatMessage("/home");
                    break;
                }
                case Spawn: {
                    AntiTPhere.mc.player.sendChatMessage("/spawn");
                    break;
                }
            }
            this.flag = false;
        }
    }
    
    public boolean check(final String checkstring) {
        return this.checktimer.passedMs(3000L) && Objects.equals(ThunderUtils.solvename(checkstring), "err");
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketChatMessage) {
            this.checktimer.reset();
        }
    }
    
    public enum Modes
    {
        Back, 
        Home, 
        RTP, 
        Spawn;
    }
}
