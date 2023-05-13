//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import com.mrzak34.thunderhack.mixin.ducks.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.modules.render.*;
import java.util.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.phobos.*;

@Mixin({ Entity.class })
public abstract class MixinEntity implements IEntity
{
    private final Timer pseudoTimer;
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    public float rotationYaw;
    @Shadow
    public float rotationPitch;
    @Shadow
    public boolean onGround;
    @Shadow
    public World world;
    @Shadow
    public float stepHeight;
    @Shadow
    public double prevPosX;
    @Shadow
    public double prevPosZ;
    @Shadow
    public double lastTickPosX;
    @Shadow
    public double lastTickPosY;
    @Shadow
    public double lastTickPosZ;
    @Shadow
    public boolean isDead;
    @Shadow
    public float width;
    @Shadow
    public float prevRotationYaw;
    @Shadow
    public float height;
    private boolean pseudoDead;
    private long stamp;
    public List<PlayerTrails.Trail> trails;
    public List<BackTrack.Box> position_history;
    @Shadow
    protected boolean inPortal;
    
    public MixinEntity() {
        this.pseudoTimer = new Timer();
        this.trails = new ArrayList<PlayerTrails.Trail>();
        this.position_history = new ArrayList<BackTrack.Box>();
    }
    
    @Accessor("isInWeb")
    public abstract boolean isInWeb();
    
    @Shadow
    public abstract boolean isSneaking();
    
    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();
    
    @Inject(method = { "move" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", shift = At.Shift.BEFORE, ordinal = 0) })
    public void onMove(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        if (((Entity)this).equals((Object)Util.mc.player)) {
            final StepEvent event = new StepEvent(this.getEntityBoundingBox(), this.stepHeight);
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCanceled()) {
                this.stepHeight = event.getHeight();
            }
        }
    }
    
    @Inject(method = { "turn" }, at = { @At("HEAD") }, cancellable = true)
    public void onTurn(final float yaw, final float pitch, final CallbackInfo ci) {
        final TurnEvent event = new TurnEvent(yaw, pitch);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "applyEntityCollision" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void addVelocityHook(final Entity entity, final double x, final double y, final double z) {
        final PushEvent event = new PushEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "getCollisionBorderSize" }, at = { @At("HEAD") }, cancellable = true)
    private void getCollisionBorderSize(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (((HitBoxes)Thunderhack.moduleManager.getModuleByClass((Class)HitBoxes.class)).isEnabled()) {
            callbackInfoReturnable.setReturnValue((Object)(0.1f + ((HitBoxes)Thunderhack.moduleManager.getModuleByClass((Class)HitBoxes.class)).expand.getValue()));
        }
    }
    
    @Shadow
    @Override
    public abstract boolean equals(final Object p0);
    
    @Shadow
    public abstract String getName();
    
    public boolean isPseudoDeadT() {
        if (this.pseudoDead && !this.isDead && this.pseudoTimer.passedMs(500L)) {
            this.pseudoDead = false;
        }
        return this.pseudoDead;
    }
    
    public void setPseudoDeadT(final boolean pseudoDead) {
        this.pseudoDead = pseudoDead;
        if (pseudoDead) {
            this.pseudoTimer.reset();
        }
    }
    
    public void setInPortal(final boolean bool) {
        this.inPortal = bool;
    }
    
    public Timer getPseudoTimeT() {
        return this.pseudoTimer;
    }
    
    public List<BackTrack.Box> getPosition_history() {
        return this.position_history;
    }
    
    public List<PlayerTrails.Trail> getTrails() {
        return this.trails;
    }
    
    public long getTimeStampT() {
        return this.stamp;
    }
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    public void ctrHook(final CallbackInfo info) {
        this.stamp = System.currentTimeMillis();
    }
    
    @Inject(method = { "setPositionAndRotation" }, at = { @At("RETURN") })
    public void setPositionAndRotationHook(final double x, final double y, final double z, final float yaw, final float pitch, final CallbackInfo ci) {
        if (this instanceof IEntityNoInterp) {
            ((IEntityNoInterp)Util.mc.player).setNoInterpX(x);
            ((IEntityNoInterp)Util.mc.player).setNoInterpY(y);
            ((IEntityNoInterp)Util.mc.player).setNoInterpZ(z);
        }
    }
}
