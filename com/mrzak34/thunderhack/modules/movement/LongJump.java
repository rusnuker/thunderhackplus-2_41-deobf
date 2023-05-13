//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.notification.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import java.util.*;

public class LongJump extends Module
{
    public double Field1990;
    public double Field1991;
    public int Field1992;
    public int Field1993;
    public boolean jumped;
    public double speedXZ;
    public double distance;
    public int stage;
    public int ticks;
    boolean flag;
    private final Setting<ModeEn> Mode;
    public Setting<Boolean> usetimer;
    public Setting<Boolean> usver;
    public Setting<Boolean> ongr;
    public Setting<Boolean> ongr2;
    public Setting<Float> speed2;
    public Setting<Float> jumpTimer;
    public Setting<Float> spd;
    public Setting<Boolean> dmgkick;
    public Setting<Boolean> noGround;
    public Setting<Boolean> YSpoof;
    private final Setting<Float> timr;
    private final Setting<Float> speed;
    private int boostMotion;
    private float startY;
    
    public LongJump() {
        super("LongJump", "\u0414\u043e\u0433\u043e\u043d\u044f\u0442\u044c \u043f\u043e\u043f\u0443\u0441\u043a\u043e\u0432-\u043d\u0430 ez", Module.Category.MOVEMENT);
        this.Field1992 = 0;
        this.Field1993 = 0;
        this.jumped = false;
        this.stage = 0;
        this.ticks = 2;
        this.flag = false;
        this.Mode = (Setting<ModeEn>)this.register(new Setting("Mode", (T)ModeEn.FunnyGame));
        this.usetimer = (Setting<Boolean>)this.register(new Setting("Timer", (T)true, v -> this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame));
        this.usver = (Setting<Boolean>)this.register(new Setting("JumpBoost", (T)false, v -> this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame));
        this.ongr = (Setting<Boolean>)this.register(new Setting("groundSpoof", (T)false, v -> this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame));
        this.ongr2 = (Setting<Boolean>)this.register(new Setting("groundSpoofVal", (T)false, v -> this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame));
        this.speed2 = (Setting<Float>)this.register(new Setting("Speed", (T)1.44f, (T)0.0f, (T)3.0f, v -> this.Mode.getValue() == ModeEn.MatrixCustom));
        this.jumpTimer = (Setting<Float>)this.register(new Setting("JumpTimer", (T)0.6f, (T)0.1f, (T)2.0f, v -> this.Mode.getValue() == ModeEn.MatrixCustom));
        this.spd = (Setting<Float>)this.register(new Setting("Speed2", (T)1.49f, (T)0.1f, (T)2.0f, v -> this.Mode.getValue() == ModeEn.MatrixCustom));
        this.dmgkick = (Setting<Boolean>)this.register(new Setting("DmgKickProtection", (T)true, v -> this.Mode.getValue() == ModeEn.MatrixCustom));
        this.noGround = (Setting<Boolean>)this.register(new Setting("Ground", (T)true, v -> this.Mode.getValue() == ModeEn.MatrixCustom));
        this.YSpoof = (Setting<Boolean>)this.register(new Setting("YSpoof", (T)true, v -> this.Mode.getValue() == ModeEn.MatrixCustom));
        this.timr = (Setting<Float>)this.register(new Setting("TimerSpeed", (T)1.0f, (T)0.5f, (T)3.0f, v -> this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)16.7f, (T)5.0f, (T)30.0f, v -> this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame));
        this.boostMotion = 0;
        this.startY = 0.0f;
    }
    
    public static void strafe(final float speed) {
        if (!MovementUtil.isMoving()) {
            return;
        }
        final double yaw = direction();
        LongJump.mc.player.motionX = -Math.sin(yaw) * speed;
        LongJump.mc.player.motionZ = Math.cos(yaw) * speed;
    }
    
    static double direction() {
        double rotationYaw = LongJump.mc.player.rotationYaw;
        if (LongJump.mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0;
        }
        double forward = 1.0;
        if (LongJump.mc.player.moveForward < 0.0f) {
            forward = -0.5;
        }
        else if (LongJump.mc.player.moveForward > 0.0f) {
            forward = 0.5;
        }
        if (LongJump.mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0 * forward;
        }
        if (LongJump.mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0 * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    
    @SubscribeEvent
    public void onMove(final EventMove f4p2) {
        if (this.Mode.getValue() == ModeEn.Default) {
            this.DefaultOnMove(f4p2);
        }
        else if (this.Mode.getValue() == ModeEn.FunnyGame) {
            this.FunnyGameOnMove(f4p2);
        }
    }
    
    @SubscribeEvent
    public void onPacketRecive(final PacketEvent.Receive e) {
        if (LongJump.mc.world != null && LongJump.mc.player != null && (this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame)) {
            if (e.getPacket() instanceof SPacketPlayerPosLook) {
                this.toggle();
            }
        }
        else if ((this.Mode.getValue() == ModeEn.NexusGrief || this.Mode.getValue() == ModeEn.MatrixCustom) && LongJump.mc.currentScreen == null && e.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
            ((ISPacketPlayerPosLook)packet).setPitch(LongJump.mc.player.rotationPitch);
            ((ISPacketPlayerPosLook)packet).setYaw(LongJump.mc.player.rotationYaw);
        }
    }
    
    public void onUpdate() {
        if (LongJump.mc.world != null && LongJump.mc.player != null && this.Mode.getValue() == ModeEn.Default) {
            if (LongJump.mc.player.onGround && this.jumped) {
                this.toggle();
            }
        }
        else if (this.Mode.getValue() == ModeEn.MatrixCustom) {
            if (LongJump.mc.player.hurtTime > 0 && this.dmgkick.getValue()) {
                NotificationManager.publicity("Kick Protection", 2, Notification.Type.ERROR);
                this.toggle();
            }
            if (LongJump.mc.player.onGround) {
                this.flag = true;
                return;
            }
            if (this.boostMotion == 0) {
                final double yaw = Math.toRadians(LongJump.mc.player.rotationYaw);
                if (!this.noGround.getValue()) {
                    LongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.player.posX, LongJump.mc.player.posY, LongJump.mc.player.posZ, true));
                }
                if (this.YSpoof.getValue()) {
                    LongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.player.posX + -Math.sin(yaw) * this.spd.getValue(), LongJump.mc.player.posY + 0.41999998688697815, LongJump.mc.player.posZ + Math.cos(yaw) * this.spd.getValue(), false));
                }
                else {
                    LongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.player.posX + -Math.sin(yaw) * this.spd.getValue(), LongJump.mc.player.posY, LongJump.mc.player.posZ + Math.cos(yaw) * this.spd.getValue(), false));
                }
                this.boostMotion = 1;
                Thunderhack.TICK_TIMER = this.jumpTimer.getValue();
                this.flag = false;
            }
            else if (this.boostMotion == 2) {
                strafe(this.speed2.getValue());
                LongJump.mc.player.motionY = 0.41999998688697815;
                this.boostMotion = 3;
            }
            else if (this.boostMotion < 5) {
                ++this.boostMotion;
            }
            else {
                Thunderhack.TICK_TIMER = 1.0f;
                if (this.flag) {
                    this.boostMotion = 0;
                }
            }
        }
        else if (this.Mode.getValue() == ModeEn.NexusGrief) {
            if (LongJump.mc.player.hurtTime > 0) {
                NotificationManager.publicity("Kick Protection", 2, Notification.Type.ERROR);
                this.toggle();
            }
            if (LongJump.mc.player.onGround) {
                this.flag = true;
                return;
            }
            if (this.boostMotion == 0) {
                final double yaw = Math.toRadians(LongJump.mc.player.rotationYaw);
                LongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.player.posX + -Math.sin(yaw) * 1.5, LongJump.mc.player.posY + 0.41999998688697815, LongJump.mc.player.posZ + Math.cos(yaw) * 1.5, false));
                this.boostMotion = 1;
                Thunderhack.TICK_TIMER = 0.6f;
                this.flag = false;
            }
            else if (this.boostMotion == 2) {
                strafe(1.44f);
                LongJump.mc.player.motionY = 0.41999998688697815;
                this.boostMotion = 3;
            }
            else if (this.boostMotion < 5) {
                ++this.boostMotion;
            }
            else {
                Thunderhack.TICK_TIMER = 1.0f;
                if (this.flag) {
                    this.boostMotion = 0;
                }
            }
        }
        else if (this.Mode.getValue() == ModeEn.FunnyGame) {
            if (LongJump.mc.player == null || LongJump.mc.world == null) {
                return;
            }
            if (LongJump.mc.player.onGround && this.jumped) {
                Thunderhack.TICK_TIMER = 1.0f;
                this.toggle();
            }
        }
    }
    
    public void onEnable() {
        this.boostMotion = 0;
        this.startY = (float)LongJump.mc.player.posY;
    }
    
    public void onDisable() {
        this.Field1990 = 0.0;
        this.Field1991 = 0.0;
        this.Field1992 = 0;
        this.Field1993 = 0;
        Thunderhack.TICK_TIMER = 1.0f;
        this.speedXZ = 0.0;
        this.distance = 0.0;
        this.stage = 0;
        this.ticks = 2;
        this.jumped = false;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPost(final EventPostSync f4u2) {
        if (this.Mode.getValue() == ModeEn.Default) {
            this.DefaultOnPreMotion(f4u2);
        }
        else if (this.Mode.getValue() == ModeEn.FunnyGame) {
            this.FGPostMotion(f4u2);
        }
    }
    
    public void DefaultOnPreMotion(final EventPostSync f4u2) {
        final double d = LongJump.mc.player.posX - LongJump.mc.player.prevPosX;
        final double d2 = LongJump.mc.player.posZ - LongJump.mc.player.prevPosZ;
        this.Field1991 = Math.sqrt(d * d + d2 * d2);
        if (this.ongr2.getValue()) {
            LongJump.mc.player.onGround = this.ongr.getValue();
        }
    }
    
    public void DefaultOnMove(final EventMove f4p2) {
        if (!LongJump.mc.player.collidedHorizontally && this.Field1993 <= 0 && (LongJump.mc.player.moveForward != 0.0f || LongJump.mc.player.moveStrafing != 0.0f)) {
            if (this.usetimer.getValue()) {
                Thunderhack.TICK_TIMER = this.timr.getValue();
            }
            else {
                Thunderhack.TICK_TIMER = 1.0f;
            }
            if (this.Field1992 == 1 && LongJump.mc.player.collidedVertically) {
                this.Field1990 = 1.0 + this.getBaseMoveSpeed() - 0.05;
            }
            else if (this.Field1992 == 2 && LongJump.mc.player.collidedVertically) {
                f4p2.set_y(LongJump.mc.player.motionY = 0.415);
                this.jumped = true;
                this.Field1990 *= this.speed.getValue() / 10.0f;
            }
            else if (this.Field1992 == 3) {
                final double d = 0.66 * (this.Field1991 - this.getBaseMoveSpeed());
                this.Field1990 = this.Field1991 - d;
            }
            else {
                this.Field1990 = this.Field1991 - this.Field1991 / 159.0;
                if (LongJump.mc.player.collidedVertically && this.Field1992 > 3) {
                    this.Field1993 = 10;
                    this.Field1992 = 1;
                }
            }
            this.Method744(f4p2, this.Field1990 = Math.max(this.Field1990, this.getBaseMoveSpeed()));
            f4p2.setCanceled(true);
            ++this.Field1992;
        }
        else {
            if (this.Field1993 > 0) {
                --this.Field1993;
            }
            this.Field1992 = 0;
            this.Field1990 = 0.0;
            f4p2.set_z(this.Field1991 = 0.0);
            f4p2.set_x(0.0);
            f4p2.setCanceled(true);
        }
    }
    
    public void Method744(final EventMove event, final double d) {
        final MovementInput movementInput = LongJump.mc.player.movementInput;
        double d2 = movementInput.moveForward;
        double d3 = movementInput.moveStrafe;
        float f = LongJump.mc.player.rotationYaw;
        if (d2 == 0.0 && d3 == 0.0) {
            event.set_x(0.0);
            event.set_z(0.0);
        }
        else {
            if (d2 != 0.0) {
                if (d3 > 0.0) {
                    f += ((d2 > 0.0) ? -45 : 45);
                }
                else if (d3 < 0.0) {
                    f += ((d2 > 0.0) ? 45 : -45);
                }
                d3 = 0.0;
                if (d2 > 0.0) {
                    d2 = 1.0;
                }
                else if (d2 < 0.0) {
                    d2 = -1.0;
                }
            }
            event.set_x(d2 * d * Math.cos(Math.toRadians(f + 90.0f)) + d3 * d * Math.sin(Math.toRadians(f + 90.0f)));
            event.set_z(d2 * d * Math.sin(Math.toRadians(f + 90.0f)) - d3 * d * Math.cos(Math.toRadians(f + 90.0f)));
        }
    }
    
    public double getBaseMoveSpeed() {
        if (LongJump.mc.player == null || LongJump.mc.world == null) {
            return 0.2873;
        }
        double d = 0.2873;
        if (LongJump.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int n = LongJump.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            d *= 1.0 + 0.2 * (n + 1);
        }
        if (LongJump.mc.player.isPotionActive(MobEffects.JUMP_BOOST) && this.usver.getValue()) {
            final int n = LongJump.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
            d /= 1.0 + 0.2 * (n + 1);
        }
        return d;
    }
    
    public void FunnyGameOnMove(final EventMove f4p2) {
        if (LongJump.mc.player.collidedHorizontally || !this.isMovingClient()) {
            this.stage = 0;
            this.ticks = 2;
            f4p2.set_z(0.0);
            f4p2.set_x(0.0);
            f4p2.setCanceled(true);
            return;
        }
        if (this.usetimer.getValue()) {
            Thunderhack.TICK_TIMER = this.speed2.getValue();
        }
        if (this.ticks > 0 && this.isMovingClient()) {
            this.speedXZ = 0.09;
            --this.ticks;
        }
        else if (this.stage == 1 && LongJump.mc.player.collidedVertically && this.isMovingClient()) {
            this.speedXZ = 1.0 + this.getBaseMoveSpeed() - 0.05;
        }
        else if (this.stage == 2 && LongJump.mc.player.collidedVertically && this.isMovingClient()) {
            LongJump.mc.player.motionY = 0.415 + this.isJumpBoost();
            f4p2.set_y(0.415 + this.isJumpBoost());
            this.speedXZ *= this.speed.getValue() / 10.0f;
            this.jumped = true;
        }
        else if (this.stage == 3) {
            final double d = 0.66 * (this.distance - this.getBaseMoveSpeed());
            this.speedXZ = this.distance - d;
        }
        else {
            this.speedXZ = this.distance - this.distance / 159.0;
        }
        f4p2.setCanceled(true);
        this.Method744(f4p2, this.speedXZ);
        final List list = LongJump.mc.world.getCollisionBoxes((Entity)LongJump.mc.player, LongJump.mc.player.getEntityBoundingBox().offset(0.0, LongJump.mc.player.motionY, 0.0));
        final List list2 = LongJump.mc.world.getCollisionBoxes((Entity)LongJump.mc.player, LongJump.mc.player.getEntityBoundingBox().offset(0.0, -0.4, 0.0));
        Label_0492: {
            if (!LongJump.mc.player.collidedVertically) {
                if (list.size() <= 0) {
                    if (list2.size() <= 0) {
                        break Label_0492;
                    }
                }
                if (this.stage > 10) {
                    if (this.stage >= 98) {
                        f4p2.set_y(LongJump.mc.player.motionY = -0.4);
                        this.stage = 0;
                        this.ticks = 5;
                    }
                    else {
                        f4p2.set_y(LongJump.mc.player.motionY = -0.001);
                    }
                }
            }
        }
        if (this.ticks <= 0 && this.isMovingClient()) {
            ++this.stage;
        }
    }
    
    private boolean isMovingClient() {
        return LongJump.mc.player.moveForward != 0.0f || LongJump.mc.player.moveStrafing != 0.0f;
    }
    
    public double isJumpBoost() {
        if (LongJump.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            return 0.2;
        }
        return 0.0;
    }
    
    public void FGPostMotion(final EventPostSync f4u2) {
        if (this.startY > LongJump.mc.player.posY) {
            Thunderhack.TICK_TIMER = 1.0f;
            this.toggle();
        }
        final double d = LongJump.mc.player.posX - LongJump.mc.player.prevPosX;
        final double d2 = LongJump.mc.player.posZ - LongJump.mc.player.prevPosZ;
        this.distance = Math.sqrt(d * d + d2 * d2);
    }
    
    public enum ModeEn
    {
        FunnyGame, 
        Default, 
        NexusGrief, 
        MatrixCustom;
    }
}
