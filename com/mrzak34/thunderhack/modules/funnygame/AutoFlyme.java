//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.math.*;

public class AutoFlyme extends Module
{
    public final Setting<Boolean> space;
    public final Setting<Boolean> instantSpeed;
    public final Setting<Boolean> criticals;
    public final Setting<Boolean> hover;
    public Setting<Float> hoverY;
    private final Timer timer;
    boolean cancelSomePackets;
    
    public AutoFlyme() {
        super("AutoFlyme", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043f\u0438\u0448\u0435\u0442 /flyme", Category.FUNNYGAME);
        this.space = (Setting<Boolean>)this.register(new Setting("OnlySpace", (T)true));
        this.instantSpeed = (Setting<Boolean>)this.register(new Setting("InstantSpeed", (T)true));
        this.criticals = (Setting<Boolean>)this.register(new Setting("criticals", (T)true));
        this.hover = (Setting<Boolean>)this.register(new Setting("hover", (T)false));
        this.hoverY = (Setting<Float>)this.register(new Setting("hoverY", (T)0.228f, (T)0.0f, (T)1.0f, v -> this.hover.getValue()));
        this.timer = new Timer();
        this.cancelSomePackets = false;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            return;
        }
        AutoFlyme.mc.player.sendChatMessage("/flyme");
    }
    
    @Override
    public void onUpdate() {
        if (!AutoFlyme.mc.player.capabilities.isFlying && this.timer.passedMs(1000L) && !AutoFlyme.mc.player.onGround && (!this.space.getValue() || AutoFlyme.mc.gameSettings.keyBindJump.isKeyDown())) {
            AutoFlyme.mc.player.sendChatMessage("/flyme");
            this.timer.reset();
        }
        if (this.hover.getValue() && AutoFlyme.mc.player.capabilities.isFlying && !AutoFlyme.mc.player.onGround && AutoFlyme.mc.world.getCollisionBoxes((Entity)AutoFlyme.mc.player, AutoFlyme.mc.player.getEntityBoundingBox().offset(0.0, (double)(-this.hoverY.getValue()), 0.0)).isEmpty()) {
            AutoFlyme.mc.player.motionY = -0.05000000074505806;
        }
    }
    
    @SubscribeEvent
    public void onAttack(final AttackEvent attackEvent) {
        if (this.criticals.getValue() && attackEvent.getStage() == 0) {
            AutoFlyme.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoFlyme.mc.player.posX, AutoFlyme.mc.player.posY + 0.1100013579, AutoFlyme.mc.player.posZ, false));
            AutoFlyme.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoFlyme.mc.player.posX, AutoFlyme.mc.player.posY - 1.3579E-6, AutoFlyme.mc.player.posZ, false));
            this.cancelSomePackets = true;
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketPlayer && this.cancelSomePackets) {
            this.cancelSomePackets = false;
            e.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final EventSync event) {
        if (!this.instantSpeed.getValue() || !AutoFlyme.mc.player.capabilities.isFlying) {
            return;
        }
        final double[] dir = MathUtil.directionSpeed(1.0499999523162842);
        if (AutoFlyme.mc.player.movementInput.moveStrafe != 0.0f || AutoFlyme.mc.player.movementInput.moveForward != 0.0f) {
            AutoFlyme.mc.player.motionX = dir[0];
            AutoFlyme.mc.player.motionZ = dir[1];
        }
        else {
            AutoFlyme.mc.player.motionX = 0.0;
            AutoFlyme.mc.player.motionZ = 0.0;
        }
    }
}
