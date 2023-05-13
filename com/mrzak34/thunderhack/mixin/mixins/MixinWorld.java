//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.block.state.*;
import com.mrzak34.thunderhack.modules.misc.*;

@Mixin({ World.class })
public class MixinWorld
{
    @Shadow
    @Final
    public boolean isRemote;
    double nigga1;
    double nigga2;
    double nigga3;
    
    @Inject(method = { "updateEntities" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 2) })
    public void updateEntitiesHook(final CallbackInfo ci) {
        if (this.isRemote) {
            final UpdateEntitiesEvent event = new UpdateEntitiesEvent();
            MinecraftForge.EVENT_BUS.post((Event)event);
        }
    }
    
    @Inject(method = { "checkLightFor" }, at = { @At("HEAD") }, cancellable = true)
    private void updateLightmapHook(final EnumSkyBlock lightType, final BlockPos pos, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final NoRender noRender = (NoRender)Thunderhack.moduleManager.getModuleByClass((Class)NoRender.class);
        if (lightType == EnumSkyBlock.SKY && noRender.isEnabled() && noRender.SkyLight.getValue() && !Util.mc.isSingleplayer()) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "onEntityAdded" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityAdded(final Entity entity, final CallbackInfo callbackInfo) {
        final EntityAddedEvent event = new EntityAddedEvent(entity);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "onEntityRemoved" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityRemoved(final Entity entity, final CallbackInfo callbackInfo) {
        final EntityRemovedEvent event = new EntityRemovedEvent(entity);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "handleMaterialAcceleration" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isPushedByWater()Z"))
    public boolean isPushedbyWaterHook(final Entity entity) {
        final PushEvent event = new PushEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        return entity.isPushedByWater() && !event.isCanceled();
    }
    
    @Inject(method = { "spawnEntity" }, at = { @At("HEAD") }, cancellable = true)
    public void spawnEntityFireWork(final Entity entityIn, final CallbackInfoReturnable<Boolean> cir) {
        final EventEntitySpawn ees = new EventEntitySpawn(entityIn);
        if (ees.isCanceled()) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "updateEntityWithOptionalForce" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V", shift = At.Shift.AFTER) })
    public void updateEntityWithOptionalForceHookPost(final Entity entityIn, final boolean forceUpdate, final CallbackInfo ci) {
        if (this.nigga1 != entityIn.posX || this.nigga2 != entityIn.posY || this.nigga3 != entityIn.posZ) {
            final EventEntityMove event = new EventEntityMove(entityIn, new Vec3d(this.nigga1, this.nigga2, this.nigga3));
            MinecraftForge.EVENT_BUS.post((Event)event);
        }
    }
    
    @Inject(method = { "updateEntityWithOptionalForce" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V") })
    public void updateEntityWithOptionalForceHookPre(final Entity entityIn, final boolean forceUpdate, final CallbackInfo ci) {
        this.nigga1 = entityIn.posX;
        this.nigga2 = entityIn.posY;
        this.nigga3 = entityIn.posZ;
    }
    
    @Inject(method = { "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z" }, at = { @At("HEAD") }, cancellable = true)
    private void setBlockState(final BlockPos pos, final IBlockState newState, final int flags, final CallbackInfoReturnable<Boolean> cir) {
        final NoGlitchBlock noGlitchBlock = (NoGlitchBlock)Thunderhack.moduleManager.getModuleByClass((Class)NoGlitchBlock.class);
        if (noGlitchBlock.isEnabled() && flags != 3) {
            cir.cancel();
            cir.setReturnValue((Object)false);
        }
    }
}
