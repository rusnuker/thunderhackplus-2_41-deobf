//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import com.mrzak34.thunderhack.modules.player.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Step extends Module
{
    private final Timer stepTimer;
    public Setting<Float> height;
    public Setting<Boolean> entityStep;
    public Setting<Boolean> useTimer;
    public Setting<Boolean> strict;
    public Setting<Integer> stepDelay;
    private final Setting<Mode> mode;
    private boolean timer;
    private Entity entityRiding;
    
    public Step() {
        super("Step", "Step", Module.Category.MOVEMENT);
        this.stepTimer = new Timer();
        this.height = (Setting<Float>)this.register(new Setting("Height", (T)2.0f, (T)1.0f, (T)2.5f));
        this.entityStep = (Setting<Boolean>)this.register(new Setting("EntityStep", (T)false));
        this.useTimer = (Setting<Boolean>)this.register(new Setting("Timer", (T)true));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.stepDelay = (Setting<Integer>)this.register(new Setting("StepDelay", (T)200, (T)0, (T)1000));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.NORMAL));
    }
    
    public void onDisable() {
        super.onDisable();
        Step.mc.player.stepHeight = 0.6f;
        if (this.entityRiding != null) {
            if (this.entityRiding instanceof EntityHorse || this.entityRiding instanceof EntityLlama || this.entityRiding instanceof EntityMule || (this.entityRiding instanceof EntityPig && this.entityRiding.isBeingRidden() && ((EntityPig)this.entityRiding).canBeSteered())) {
                this.entityRiding.stepHeight = 1.0f;
            }
            else {
                this.entityRiding.stepHeight = 0.5f;
            }
        }
    }
    
    public void onUpdate() {
        if (Step.mc.player.capabilities.isFlying || ((FreeCam)Thunderhack.moduleManager.getModuleByClass((Class)FreeCam.class)).isOn()) {
            Step.mc.player.stepHeight = 0.6f;
            return;
        }
        if (Jesus.isInLiquid()) {
            Step.mc.player.stepHeight = 0.6f;
            return;
        }
        if (this.timer && Step.mc.player.onGround) {
            Thunderhack.TICK_TIMER = 1.0f;
            this.timer = false;
        }
        if (Step.mc.player.onGround && this.stepTimer.passedMs(this.stepDelay.getValue())) {
            if (Step.mc.player.isRiding() && Step.mc.player.getRidingEntity() != null) {
                this.entityRiding = Step.mc.player.getRidingEntity();
                if (this.entityStep.getValue()) {
                    Step.mc.player.getRidingEntity().stepHeight = this.height.getValue();
                }
            }
            else {
                Step.mc.player.stepHeight = this.height.getValue();
            }
        }
        else if (Step.mc.player.isRiding() && Step.mc.player.getRidingEntity() != null) {
            this.entityRiding = Step.mc.player.getRidingEntity();
            if (this.entityRiding != null) {
                if (this.entityRiding instanceof EntityHorse || this.entityRiding instanceof EntityLlama || this.entityRiding instanceof EntityMule || (this.entityRiding instanceof EntityPig && this.entityRiding.isBeingRidden() && ((EntityPig)this.entityRiding).canBeSteered())) {
                    this.entityRiding.stepHeight = 1.0f;
                }
                else {
                    this.entityRiding.stepHeight = 0.5f;
                }
            }
        }
        else {
            Step.mc.player.stepHeight = 0.6f;
        }
    }
    
    @SubscribeEvent
    public void onStep(final StepEvent event) {
        if (this.mode.getValue().equals(Mode.NORMAL)) {
            final double stepHeight = event.getAxisAlignedBB().minY - Step.mc.player.posY;
            if (stepHeight <= 0.0 || stepHeight > this.height.getValue()) {
                return;
            }
            final double[] offsets = this.getOffset(stepHeight);
            if (offsets != null && offsets.length > 1) {
                if (this.useTimer.getValue()) {
                    Thunderhack.TICK_TIMER = 1.0f / offsets.length;
                    this.timer = true;
                }
                for (final double offset : offsets) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + offset, Step.mc.player.posZ, false));
                }
            }
            this.stepTimer.reset();
        }
    }
    
    public double[] getOffset(final double height) {
        if (height == 0.75) {
            if (this.strict.getValue()) {
                return new double[] { 0.42, 0.753, 0.75 };
            }
            return new double[] { 0.42, 0.753 };
        }
        else if (height == 0.8125) {
            if (this.strict.getValue()) {
                return new double[] { 0.39, 0.7, 0.8125 };
            }
            return new double[] { 0.39, 0.7 };
        }
        else if (height == 0.875) {
            if (this.strict.getValue()) {
                return new double[] { 0.39, 0.7, 0.875 };
            }
            return new double[] { 0.39, 0.7 };
        }
        else if (height == 1.0) {
            if (this.strict.getValue()) {
                return new double[] { 0.42, 0.753, 1.0 };
            }
            return new double[] { 0.42, 0.753 };
        }
        else {
            if (height == 1.5) {
                return new double[] { 0.42, 0.75, 1.0, 1.16, 1.23, 1.2 };
            }
            if (height == 2.0) {
                return new double[] { 0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43 };
            }
            if (height == 2.5) {
                return new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 };
            }
            return null;
        }
    }
    
    public enum Mode
    {
        NORMAL, 
        VANILLA;
    }
}
