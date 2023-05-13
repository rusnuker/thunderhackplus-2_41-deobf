//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import com.mrzak34.thunderhack.util.phobos.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mrzak34.thunderhack.events.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.movement.*;

@Mixin({ EntityLivingBase.class })
public abstract class MixinEntityLivingBase extends Entity implements IEntityLivingBase
{
    @Shadow
    public float moveStrafing;
    @Shadow
    public float moveForward;
    @Shadow
    public int jumpTicks;
    protected float lowestDura;
    
    public MixinEntityLivingBase(final World worldIn) {
        super(worldIn);
        this.lowestDura = Float.MAX_VALUE;
    }
    
    public void setLowestDura(final float lowest) {
        this.lowestDura = lowest;
    }
    
    public float getLowestDurability() {
        return this.lowestDura;
    }
    
    @Accessor("ticksSinceLastSwing")
    public abstract int getTicksSinceLastSwing();
    
    @Accessor("ticksSinceLastSwing")
    public abstract void setTicksSinceLastSwing(final int p0);
    
    @Accessor("activeItemStackUseCount")
    public abstract int getActiveItemStackUseCount();
    
    @Accessor("activeItemStackUseCount")
    public abstract void setActiveItemStackUseCount(final int p0);
    
    @Inject(method = { "travel" }, at = { @At("HEAD") }, cancellable = true)
    public void onTravelPre(final float strafe, final float vertical, final float forward, final CallbackInfo ci) {
        final ElytraEvent event = new ElytraEvent((Entity)this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (Util.mc.player != null && (EntityLivingBase)this == Util.mc.player) {
            final EventMoveDirection event2 = new EventMoveDirection(false);
            MinecraftForge.EVENT_BUS.post((Event)event2);
        }
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "travel" }, at = { @At("RETURN") }, cancellable = true)
    public void onTravelPost(final float strafe, final float vertical, final float forward, final CallbackInfo ci) {
        if (Util.mc.player != null && (EntityLivingBase)this == Util.mc.player) {
            final EventMoveDirection event = new EventMoveDirection(true);
            MinecraftForge.EVENT_BUS.post((Event)event);
        }
    }
    
    @Inject(method = { "handleJumpWater" }, at = { @At("HEAD") }, cancellable = true)
    private void handleJumpWater(final CallbackInfo ci) {
        final HandleLiquidJumpEvent event = new HandleLiquidJumpEvent();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "handleJumpLava" }, at = { @At("HEAD") }, cancellable = true)
    private void handleJumpLava(final CallbackInfo ci) {
        final HandleLiquidJumpEvent event = new HandleLiquidJumpEvent();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "jump" }, at = { @At("HEAD") }, cancellable = true)
    public void jumphook(final CallbackInfo ci) {
        final EventJump event = new EventJump(this.rotationYaw);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "onItemUseFinish" }, at = { @At("HEAD") }, cancellable = true)
    public void finishHook(final CallbackInfo ci) {
        final FinishUseItemEvent event = new FinishUseItemEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "getArmSwingAnimationEnd" }, at = { @At("HEAD") }, cancellable = true)
    private void getArmSwingAnimationEnd(final CallbackInfoReturnable<Integer> info) {
        if (((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).isEnabled() && ((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).rMode.getValue() == Animations.rmode.Slow) {
            info.setReturnValue((Object)Animations.getInstance().slowValue.getValue());
        }
    }
    
    @Inject(method = { "onLivingUpdate" }, at = { @At("HEAD") })
    public void onLivingUpdate(final CallbackInfo ci) {
        if (((NoJumpDelay)Thunderhack.moduleManager.getModuleByClass((Class)NoJumpDelay.class)).isEnabled()) {
            this.jumpTicks = 0;
        }
    }
}
