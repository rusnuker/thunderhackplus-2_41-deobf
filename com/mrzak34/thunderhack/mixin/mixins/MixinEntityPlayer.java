//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.phobos.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mrzak34.thunderhack.modules.movement.*;
import com.mrzak34.thunderhack.*;

@Mixin({ EntityPlayer.class })
public abstract class MixinEntityPlayer extends EntityLivingBase implements IEntityPlayer
{
    @Unique
    private MotionTracker motionTrackerT;
    @Unique
    private MotionTracker breakMotionTrackerT;
    @Unique
    private MotionTracker blockMotionTrackerT;
    
    public MixinEntityPlayer(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn);
    }
    
    public MotionTracker getMotionTrackerT() {
        return this.motionTrackerT;
    }
    
    public void setMotionTrackerT(final MotionTracker motionTracker) {
        this.motionTrackerT = motionTracker;
    }
    
    public MotionTracker getBreakMotionTrackerT() {
        return this.breakMotionTrackerT;
    }
    
    public void setBreakMotionTrackerT(final MotionTracker breakMotionTracker) {
        this.breakMotionTrackerT = breakMotionTracker;
    }
    
    public MotionTracker getBlockMotionTrackerT() {
        return this.blockMotionTrackerT;
    }
    
    public void setBlockMotionTrackerT(final MotionTracker blockMotionTracker) {
        this.blockMotionTrackerT = blockMotionTracker;
    }
    
    @Inject(method = { "travel" }, at = { @At("HEAD") }, cancellable = true)
    public void travel(final float strafe, final float vertical, final float forward, final CallbackInfo info) {
        EntityPlayer us = null;
        if (Util.mc.player != null) {
            us = (EntityPlayer)Util.mc.player;
        }
        if (us == null) {
            return;
        }
        final EventPlayerTravel event = new EventPlayerTravel(strafe, vertical, forward);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            info.cancel();
        }
    }
    
    @Inject(method = { "attackTargetEntityWithCurrentItem" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setSprinting(Z)V", shift = At.Shift.AFTER) })
    public void onAttackTargetEntityWithCurrentItem(final CallbackInfo callbackInfo) {
        final KeepSprint ks = (KeepSprint)Thunderhack.moduleManager.getModuleByClass((Class)KeepSprint.class);
        if (ks.isEnabled()) {
            final float multiplier = 0.6f + 0.4f * ks.motion.getValue();
            this.motionX = this.motionX / 0.6 * multiplier;
            this.motionZ = this.motionZ / 0.6 * multiplier;
            if (ks.sprint.getValue()) {
                this.setSprinting(true);
            }
        }
    }
}
