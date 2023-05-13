//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import com.mrzak34.thunderhack.mixin.ducks.*;
import org.spongepowered.asm.mixin.*;
import com.mrzak34.thunderhack.modules.player.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.events.*;

@Mixin({ PlayerControllerMP.class })
public abstract class MixinPlayerControllerMP implements IPlayerControllerMP
{
    @Inject(method = { "getBlockReachDistance" }, at = { @At("RETURN") }, cancellable = true)
    private void getReachDistanceHook(final CallbackInfoReturnable<Float> distance) {
        if (Reach.getInstance().isOn()) {
            final float range = (float)distance.getReturnValue();
            distance.setReturnValue((Object)(range + Reach.getInstance().add.getValue()));
        }
    }
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void clickBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        final ClickBlockEvent event2 = new ClickBlockEvent(pos, face);
        MinecraftForge.EVENT_BUS.post((Event)event2);
        if (event2.isCanceled()) {
            info.cancel();
        }
    }
    
    @Invoker("syncCurrentPlayItem")
    public abstract void syncItem();
    
    @Inject(method = { "attackEntity" }, at = { @At("HEAD") }, cancellable = true)
    public void attackEntityPre(final EntityPlayer playerIn, final Entity targetEntity, final CallbackInfo info) {
        final AttackEvent event = new AttackEvent(targetEntity, (short)0);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "attackEntity" }, at = { @At("RETURN") }, cancellable = true)
    public void attackEntityPost(final EntityPlayer playerIn, final Entity targetEntity, final CallbackInfo info) {
        final AttackEvent event = new AttackEvent(targetEntity, (short)1);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "processRightClickBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void clickBlockHook(final EntityPlayerSP player, final WorldClient worldIn, final BlockPos pos, final EnumFacing direction, final Vec3d vec, final EnumHand hand, final CallbackInfoReturnable<EnumActionResult> info) {
        final ClickBlockEvent.Right event = new ClickBlockEvent.Right(pos, direction, vec, hand);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "resetBlockRemoving" }, at = { @At("HEAD") }, cancellable = true)
    public void resetBlockRemovingHook(final CallbackInfo info) {
        final ResetBlockEvent event = new ResetBlockEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "onStoppedUsingItem" }, at = { @At("HEAD") }, cancellable = true)
    public void stopHook(final CallbackInfo info) {
        final StopUsingItemEvent event = new StopUsingItemEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V") }, cancellable = true)
    private void onPlayerDestroyBlock(final BlockPos pos, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final NoGlitchBlock noGlitchBlock = (NoGlitchBlock)Thunderhack.moduleManager.getModuleByClass((Class)NoGlitchBlock.class);
        if (noGlitchBlock.isEnabled()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue((Object)false);
        }
        MinecraftForge.EVENT_BUS.post((Event)new DestroyBlockEvent(pos));
    }
    
    @Inject(method = { "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final DamageBlockEvent event = new DamageBlockEvent(posBlock, directionFacing);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }
}
