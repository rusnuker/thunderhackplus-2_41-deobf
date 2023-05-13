//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AntiHunger extends Module
{
    public Setting<Boolean> sprint;
    public Setting<Boolean> noGround;
    public Setting<Boolean> grPacket;
    private boolean isOnGround;
    
    public AntiHunger() {
        super("AntiHunger", "\u0443\u043c\u0435\u043d\u044c\u0448\u0430\u0435\u0442 \u043f\u043e\u0442\u0440\u0435\u0431\u043b\u0435\u043d\u0438\u0435-\u0433\u043e\u043b\u043e\u0434\u0430", Module.Category.PLAYER);
        this.sprint = (Setting<Boolean>)this.register(new Setting("Sprint", (T)true));
        this.noGround = (Setting<Boolean>)this.register(new Setting("Ground", (T)true));
        this.grPacket = (Setting<Boolean>)this.register(new Setting("GroundPacket", (T)true));
        this.isOnGround = false;
    }
    
    public void onEnable() {
        if (this.sprint.getValue() && AntiHunger.mc.player != null) {
            AntiHunger.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiHunger.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
    }
    
    public void onDisable() {
        if (this.sprint.getValue() && AntiHunger.mc.player != null && AntiHunger.mc.player.isSprinting()) {
            AntiHunger.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiHunger.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketEntityAction) {
            final CPacketEntityAction action = (CPacketEntityAction)event.getPacket();
            if (this.sprint.getValue() && (action.getAction() == CPacketEntityAction.Action.START_SPRINTING || action.getAction() == CPacketEntityAction.Action.STOP_SPRINTING)) {
                event.setCanceled(true);
            }
        }
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer player = (CPacketPlayer)event.getPacket();
            final boolean ground = AntiHunger.mc.player.onGround;
            if (this.noGround.getValue() && this.isOnGround && ground && player.getY(0.0) == (AntiHunger.mc.player.isSprinting() ? AntiHunger.mc.player.posY : 0.0)) {
                if (this.grPacket.getValue()) {
                    ((ICPacketPlayer)player).setOnGround(false);
                }
                else {
                    AntiHunger.mc.player.onGround = false;
                }
            }
            this.isOnGround = ground;
        }
    }
}
