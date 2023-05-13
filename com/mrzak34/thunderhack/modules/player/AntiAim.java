//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.math.*;

public class AntiAim extends Module
{
    private final Setting<Mode> pitchMode;
    private final Setting<Mode> yawMode;
    public Setting<Integer> Speed;
    public Setting<Integer> yawDelta;
    public Setting<Integer> pitchDelta;
    public final Setting<Boolean> bodySync;
    public final Setting<Boolean> allowInteract;
    private float rotationYaw;
    private float rotationPitch;
    private float pitch_sinus_step;
    private float yaw_sinus_step;
    
    public AntiAim() {
        super("AntiAim", "\u0443\u0442\u0440\u043e 1 \u044f\u043d\u0432\u0430\u0440\u044f", "can break CA predict", Module.Category.PLAYER);
        this.pitchMode = (Setting<Mode>)this.register(new Setting("PitchMode", (T)Mode.None));
        this.yawMode = (Setting<Mode>)this.register(new Setting("YawMode", (T)Mode.None));
        this.Speed = (Setting<Integer>)this.register(new Setting("Speed", (T)1, (T)1, (T)45));
        this.yawDelta = (Setting<Integer>)this.register(new Setting("YawDelta", (T)60, (T)(-360), (T)360));
        this.pitchDelta = (Setting<Integer>)this.register(new Setting("PitchDelta", (T)10, (T)(-90), (T)90));
        this.bodySync = (Setting<Boolean>)this.register(new Setting("BodySync", (T)true));
        this.allowInteract = (Setting<Boolean>)this.register(new Setting("AllowInteract", (T)true));
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onSync(final EventSync e) {
        if (this.allowInteract.getValue() && (AntiAim.mc.gameSettings.keyBindAttack.isKeyDown() || AntiAim.mc.gameSettings.keyBindUseItem.isKeyDown())) {
            return;
        }
        if (this.yawMode.getValue() != Mode.None) {
            AntiAim.mc.player.rotationYaw = this.rotationYaw;
            if (this.bodySync.getValue()) {
                AntiAim.mc.player.renderYawOffset = this.rotationYaw;
            }
        }
        if (this.pitchMode.getValue() != Mode.None) {
            AntiAim.mc.player.rotationPitch = this.rotationPitch;
        }
    }
    
    @SubscribeEvent
    public void onCalc(final PlayerUpdateEvent e) {
        if (this.pitchMode.getValue() == Mode.RandomAngle && AntiAim.mc.player.ticksExisted % this.Speed.getValue() == 0) {
            this.rotationPitch = MathUtil.random(90.0f, -90.0f);
        }
        if (this.yawMode.getValue() == Mode.RandomAngle && AntiAim.mc.player.ticksExisted % this.Speed.getValue() == 0) {
            this.rotationYaw = MathUtil.random(0.0f, 360.0f);
        }
        if (this.yawMode.getValue() == Mode.Spin && AntiAim.mc.player.ticksExisted % this.Speed.getValue() == 0) {
            this.rotationYaw += this.yawDelta.getValue();
            if (this.rotationYaw > 360.0f) {
                this.rotationYaw = 0.0f;
            }
            if (this.rotationYaw < 0.0f) {
                this.rotationYaw = 360.0f;
            }
        }
        if (this.pitchMode.getValue() == Mode.Spin && AntiAim.mc.player.ticksExisted % this.Speed.getValue() == 0) {
            this.rotationPitch += this.pitchDelta.getValue();
            if (this.rotationPitch > 90.0f) {
                this.rotationPitch = -90.0f;
            }
            if (this.rotationPitch < -90.0f) {
                this.rotationPitch = 90.0f;
            }
        }
        if (this.pitchMode.getValue() == Mode.Sinus) {
            this.pitch_sinus_step += this.Speed.getValue() / 10.0f;
            this.rotationPitch = (float)(AntiAim.mc.player.rotationPitch + this.pitchDelta.getValue() * Math.sin(this.pitch_sinus_step));
            this.rotationPitch = MathUtil.clamp(this.rotationPitch, -90.0f, 90.0f);
        }
        if (this.yawMode.getValue() == Mode.Sinus) {
            this.yaw_sinus_step += this.Speed.getValue() / 10.0f;
            this.rotationYaw = (float)(AntiAim.mc.player.rotationYaw + this.yawDelta.getValue() * Math.sin(this.yaw_sinus_step));
        }
        if (this.pitchMode.getValue() == Mode.Fixed) {
            this.rotationPitch = this.pitchDelta.getValue();
        }
        if (this.yawMode.getValue() == Mode.Fixed) {
            this.rotationYaw = this.yawDelta.getValue();
        }
        if (this.pitchMode.getValue() == Mode.Static) {
            this.rotationPitch = AntiAim.mc.player.rotationPitch + this.pitchDelta.getValue();
            this.rotationPitch = MathUtil.clamp(this.rotationPitch, -90.0f, 90.0f);
        }
        if (this.yawMode.getValue() == Mode.Static) {
            this.rotationYaw = AntiAim.mc.player.rotationYaw % 360.0f + this.yawDelta.getValue();
        }
    }
    
    public enum Mode
    {
        None, 
        RandomAngle, 
        Spin, 
        Sinus, 
        Fixed, 
        Static;
    }
}
