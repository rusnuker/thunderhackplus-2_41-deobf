//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;

public class Velocity extends Module
{
    public Setting<Boolean> onlyAura;
    public Setting<Boolean> ice;
    public Setting<Boolean> autoDisable;
    private final Setting<modeEn> mode;
    public Setting<Float> horizontal;
    public Setting<Float> vertical;
    private boolean flag;
    
    public Velocity() {
        super("Velocity", "\u0430\u043a\u044d\u0431\u044d\u0448\u043a\u0430", Category.MOVEMENT);
        this.onlyAura = (Setting<Boolean>)this.register(new Setting("OnlyAura", (T)false));
        this.ice = (Setting<Boolean>)this.register(new Setting("Ice", (T)false));
        this.autoDisable = (Setting<Boolean>)this.register(new Setting("DisableOnVerify", (T)false));
        this.mode = (Setting<modeEn>)this.register(new Setting("Mode", (T)modeEn.Matrix));
        this.horizontal = (Setting<Float>)this.register(new Setting("Horizontal", (T)0.0f, (T)0.0f, (T)100.0f, v -> this.mode.getValue() == modeEn.Custom));
        this.vertical = (Setting<Float>)this.register(new Setting("Vertical", (T)0.0f, (T)0.0f, (T)100.0f, v -> this.mode.getValue() == modeEn.Custom));
    }
    
    @Override
    public void onUpdate() {
        if (this.ice.getValue()) {
            Blocks.ICE.slipperiness = 0.6f;
            Blocks.PACKED_ICE.slipperiness = 0.6f;
            Blocks.FROSTED_ICE.slipperiness = 0.6f;
        }
    }
    
    @Override
    public void onDisable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
    
    @SubscribeEvent
    public void onPacketReceived(final PacketEvent.Receive event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketChat && this.autoDisable.getValue()) {
            final String text = ((SPacketChat)event.getPacket()).getChatComponent().getFormattedText();
            if (text.contains("\u0422\u0435\u0431\u044f \u043f\u0440\u043e\u0432\u0435\u0440\u044f\u044e\u0442 \u043d\u0430 \u0447\u0438\u0442 \u0410\u041a\u0411, \u043d\u0438\u043a \u0445\u0435\u043b\u043f\u0435\u0440\u0430 - ")) {
                this.toggle();
            }
        }
        final SPacketEntityStatus packet;
        final Entity entity;
        if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).getOpCode() == 31 && (entity = packet.getEntity((World)Velocity.mc.world)) instanceof EntityFishHook) {
            final EntityFishHook fishHook = (EntityFishHook)entity;
            if (fishHook.caughtEntity == Velocity.mc.player) {
                event.setCanceled(true);
            }
        }
        if (event.getPacket() instanceof SPacketExplosion) {
            final SPacketExplosion velocity_ = (SPacketExplosion)event.getPacket();
            if (this.mode.getValue() == modeEn.Custom) {
                ((ISPacketExplosion)velocity_).setMotionX(((ISPacketExplosion)velocity_).getMotionX() * this.horizontal.getValue() / 100.0f);
                ((ISPacketExplosion)velocity_).setMotionZ(((ISPacketExplosion)velocity_).getMotionZ() * this.horizontal.getValue() / 100.0f);
                ((ISPacketExplosion)velocity_).setMotionY(((ISPacketExplosion)velocity_).getMotionY() * this.vertical.getValue() / 100.0f);
            }
            else if (this.mode.getValue() == modeEn.Cancel) {
                ((ISPacketExplosion)velocity_).setMotionX(0.0f);
                ((ISPacketExplosion)velocity_).setMotionY(0.0f);
                ((ISPacketExplosion)velocity_).setMotionZ(0.0f);
            }
        }
        if (this.onlyAura.getValue() && ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).isDisabled()) {
            return;
        }
        if (this.mode.getValue() == modeEn.Cancel && event.getPacket() instanceof SPacketEntityVelocity) {
            final SPacketEntityVelocity pac = (SPacketEntityVelocity)event.getPacket();
            if (pac.getEntityID() == Velocity.mc.player.getEntityId()) {
                event.setCanceled(true);
                return;
            }
        }
        final SPacketEntityVelocity velocity;
        if (this.mode.getValue() == modeEn.Custom && event.getPacket() instanceof SPacketEntityVelocity && (velocity = (SPacketEntityVelocity)event.getPacket()).getEntityID() == Velocity.mc.player.getEntityId()) {
            ((ISPacketEntityVelocity)velocity).setMotionX((int)(velocity.getMotionX() * this.horizontal.getValue() / 100.0f));
            ((ISPacketEntityVelocity)velocity).setMotionY((int)(velocity.getMotionY() * this.vertical.getValue() / 100.0f));
            ((ISPacketEntityVelocity)velocity).setMotionZ((int)(velocity.getMotionZ() * this.horizontal.getValue() / 100.0f));
        }
        if (this.mode.getValue() == modeEn.Matrix) {
            if (event.getPacket() instanceof SPacketEntityStatus) {
                final SPacketEntityStatus var9 = (SPacketEntityStatus)event.getPacket();
                if (var9.getOpCode() == 2 && var9.getEntity((World)Velocity.mc.world) == Velocity.mc.player) {
                    this.flag = true;
                }
            }
            if (event.getPacket() instanceof SPacketEntityVelocity) {
                final SPacketEntityVelocity var10 = (SPacketEntityVelocity)event.getPacket();
                if (var10.getEntityID() == Velocity.mc.player.getEntityId()) {
                    if (!this.flag) {
                        event.setCanceled(true);
                    }
                    else {
                        this.flag = false;
                        ((ISPacketEntityVelocity)var10).setMotionX((int)(var10.getMotionX() * -0.1));
                        ((ISPacketEntityVelocity)var10).setMotionZ((int)(var10.getMotionZ() * -0.1));
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPreMotion(final EventSync var1) {
        if (this.mode.getValue() == modeEn.Matrix && Velocity.mc.player.hurtTime > 0 && !Velocity.mc.player.onGround) {
            final double var2 = Velocity.mc.player.rotationYaw * 0.017453292f;
            final double var3 = Math.sqrt(Velocity.mc.player.motionX * Velocity.mc.player.motionX + Velocity.mc.player.motionZ * Velocity.mc.player.motionZ);
            Velocity.mc.player.motionX = -Math.sin(var2) * var3;
            Velocity.mc.player.motionZ = Math.cos(var2) * var3;
            Velocity.mc.player.setSprinting(Velocity.mc.player.ticksExisted % 2 != 0);
        }
    }
    
    @SubscribeEvent
    public void onPush(final PushEvent event) {
        event.setCanceled(true);
    }
    
    public enum modeEn
    {
        Matrix, 
        Cancel, 
        Custom;
    }
}
