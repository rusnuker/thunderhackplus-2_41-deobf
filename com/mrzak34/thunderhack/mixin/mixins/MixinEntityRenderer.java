//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.*;
import javax.vecmath.*;
import java.awt.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import java.nio.*;
import net.minecraft.client.multiplayer.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.util.*;
import javax.annotation.*;
import com.google.common.base.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.input.*;
import java.util.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.events.*;
import org.lwjgl.util.glu.*;
import net.minecraftforge.client.*;
import com.mrzak34.thunderhack.modules.misc.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.*;
import net.minecraft.block.state.*;
import com.mrzak34.thunderhack.modules.player.*;
import com.mrzak34.thunderhack.modules.movement.*;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer
{
    @Shadow
    public Entity pointedEntity;
    @Shadow
    public boolean debugView;
    @Shadow
    public float farPlaneDistance;
    @Final
    @Shadow
    public ItemRenderer itemRenderer;
    @Shadow
    public float thirdPersonDistancePrev;
    @Shadow
    public boolean cloudFog;
    @Shadow
    private ItemStack itemActivationItem;
    @Shadow
    @Final
    private int[] lightmapColors;
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;clear(I)V", ordinal = 1, shift = At.Shift.BEFORE) })
    private void renderWorldPass(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo ci) {
        if (Display.isActive() || Display.isVisible()) {
            final PreRenderEvent render3dEvent = new PreRenderEvent(partialTicks);
            MinecraftForge.EVENT_BUS.post((Event)render3dEvent);
        }
    }
    
    @Inject(method = { "renderItemActivation" }, at = { @At("HEAD") }, cancellable = true)
    public void renderItemActivationHook(final CallbackInfo info) {
        if (this.itemActivationItem != null && NoRender.getInstance().isOn() && NoRender.getInstance().totemPops.getValue() && this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderItemActivation" }, at = { @At("HEAD") }, cancellable = true)
    public void renderItemActivationHook(final int p_190563_1_, final int p_190563_2_, final float p_190563_3_, final CallbackInfo ci) {
        if (this.itemActivationItem != null && NoRender.getInstance().isOn() && NoRender.getInstance().totemPops.getValue() && this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderRainSnow(F)V") })
    public void weatherHook(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo ci) {
        if (((Weather)Thunderhack.moduleManager.getModuleByClass((Class)Weather.class)).isOn()) {
            ((Weather)Thunderhack.moduleManager.getModuleByClass((Class)Weather.class)).render(partialTicks);
        }
    }
    
    @Inject(method = { "updateLightmap" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/DynamicTexture;updateDynamicTexture()V", shift = At.Shift.BEFORE) })
    private void updateTextureHook(final float partialTicks, final CallbackInfo ci) {
        final Ambience ambience = (Ambience)Thunderhack.moduleManager.getModuleByClass((Class)Ambience.class);
        if (ambience.isEnabled()) {
            for (int i = 0; i < this.lightmapColors.length; ++i) {
                final Color ambientColor = ambience.colorLight.getValue().getColorObject();
                final int alpha = ambientColor.getAlpha();
                final float modifier = alpha / 255.0f;
                final int color = this.lightmapColors[i];
                final int[] bgr = this.toRGBAArray(color);
                final Vector3f values = new Vector3f(bgr[2] / 255.0f, bgr[1] / 255.0f, bgr[0] / 255.0f);
                final Vector3f newValues = new Vector3f(ambientColor.getRed() / 255.0f, ambientColor.getGreen() / 255.0f, ambientColor.getBlue() / 255.0f);
                final Vector3f finalValues = this.mix(values, newValues, modifier);
                final int red = (int)(finalValues.x * 255.0f);
                final int green = (int)(finalValues.y * 255.0f);
                final int blue = (int)(finalValues.z * 255.0f);
                this.lightmapColors[i] = (0xFF000000 | red << 16 | green << 8 | blue);
            }
        }
    }
    
    private int[] toRGBAArray(final int colorBuffer) {
        return new int[] { colorBuffer >> 16 & 0xFF, colorBuffer >> 8 & 0xFF, colorBuffer & 0xFF };
    }
    
    private Vector3f mix(final Vector3f first, final Vector3f second, final float factor) {
        return new Vector3f(first.x * (1.0f - factor) + second.x * factor, first.y * (1.0f - factor) + second.y * factor, first.z * (1.0f - factor) + first.z * factor);
    }
    
    @Redirect(method = { "setupCameraTransform" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;prevTimeInPortal:F"))
    public float prevTimeInPortalHook(final EntityPlayerSP entityPlayerSP) {
        if ((NoRender.getInstance().isOn() && NoRender.getInstance().nausea.getValue()) || NoRender.getInstance().portal.getValue()) {
            return -3.4028235E38f;
        }
        return entityPlayerSP.prevTimeInPortal;
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffectHook(final float ticks, final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().hurtcam.getValue()) {
            info.cancel();
        }
    }
    
    @Shadow
    public abstract FloatBuffer setFogColorBuffer(final float p0, final float p1, final float p2, final float p3);
    
    @Inject(method = { "setupFogColor" }, at = { @At("HEAD") }, cancellable = true)
    public void setupFogColoHook(final boolean black, final CallbackInfo ci) {
        if (((FogColor)Thunderhack.moduleManager.getModuleByClass((Class)FogColor.class)).isOn()) {
            ci.cancel();
            final Color fogColor = ((FogColor)Thunderhack.moduleManager.getModuleByClass((Class)FogColor.class)).color.getValue().getColorObject();
            GlStateManager.glFog(2918, this.setFogColorBuffer(fogColor.getRed() / 255.0f, fogColor.getGreen() / 255.0f, fogColor.getBlue() / 255.0f, fogColor.getAlpha() / 255.0f));
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
        if (NoEntityTrace.getINSTANCE().isOn() && NoEntityTrace.getINSTANCE().noTrace) {
            return new ArrayList<Entity>();
        }
        return (List<Entity>)worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }
    
    @Inject(method = { "getMouseOver" }, at = { @At("HEAD") }, cancellable = true)
    public void getMouseOverHook(final float partialTicks, final CallbackInfo ci) {
        final Reach reach = (Reach)Thunderhack.moduleManager.getModuleByClass((Class)Reach.class);
        final BackTrack bt = (BackTrack)Thunderhack.moduleManager.getModuleByClass((Class)BackTrack.class);
        if (bt.isOn() || reach.isOn()) {
            ci.cancel();
            this.getMouseOverCustom(partialTicks);
        }
    }
    
    public void getMouseOverCustom(final float partialTicks) {
        final Reach reach = (Reach)Thunderhack.moduleManager.getModuleByClass((Class)Reach.class);
        final BackTrack bt = (BackTrack)Thunderhack.moduleManager.getModuleByClass((Class)BackTrack.class);
        final Entity entity = Util.mc.getRenderViewEntity();
        if (entity != null && Util.mc.world != null) {
            Util.mc.profiler.startSection("pick");
            Util.mc.pointedEntity = null;
            double d0 = Util.mc.playerController.getBlockReachDistance();
            if (reach.isOn()) {
                d0 += reach.add.getValue();
            }
            Util.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
            final Vec3d vec3d = entity.getPositionEyes(partialTicks);
            boolean flag = false;
            double d2 = d0;
            if (Util.mc.playerController.extendedReach()) {
                d2 = (d0 = 6.0);
            }
            else if (d0 > 3.0) {
                flag = true;
            }
            if (Util.mc.objectMouseOver != null) {
                d2 = Util.mc.objectMouseOver.hitVec.distanceTo(vec3d);
            }
            final Vec3d vec3d2 = entity.getLook(1.0f);
            final Vec3d vec3d3 = vec3d.add(vec3d2.x * d0, vec3d2.y * d0, vec3d2.z * d0);
            this.pointedEntity = null;
            Vec3d vec3d4 = null;
            final List<Entity> list = (List<Entity>)Util.mc.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(vec3d2.x * d0, vec3d2.y * d0, vec3d2.z * d0).grow(1.0, 1.0, 1.0), Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate)new Predicate<Entity>() {
                public boolean apply(@Nullable final Entity p_apply_1_) {
                    return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
                }
            }));
            double d3 = d2;
            if (!Thunderhack.class.getName().toLowerCase().contains("der")) {
                Minecraft.getMinecraft().shutdown();
            }
            for (final Entity value : list) {
                final AxisAlignedBB axisalignedbb = value.getEntityBoundingBox().grow((double)value.getCollisionBorderSize());
                final RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d3);
                if (axisalignedbb.contains(vec3d)) {
                    if (d3 < 0.0) {
                        continue;
                    }
                    this.pointedEntity = value;
                    vec3d4 = ((raytraceresult == null) ? vec3d : raytraceresult.hitVec);
                    d3 = 0.0;
                }
                else {
                    if (raytraceresult == null) {
                        continue;
                    }
                    final double d4 = vec3d.distanceTo(raytraceresult.hitVec);
                    if (d4 >= d3 && d3 != 0.0) {
                        continue;
                    }
                    if (value.getLowestRidingEntity() == entity.getLowestRidingEntity() && !value.canRiderInteract()) {
                        if (d3 != 0.0) {
                            continue;
                        }
                        this.pointedEntity = value;
                        vec3d4 = raytraceresult.hitVec;
                    }
                    else {
                        this.pointedEntity = value;
                        vec3d4 = raytraceresult.hitVec;
                        d3 = d4;
                    }
                }
            }
            if (this.pointedEntity != null && flag && vec3d.distanceTo(vec3d4) > 3.0) {
                this.pointedEntity = null;
                Util.mc.objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d4, (EnumFacing)null, new BlockPos(vec3d4));
            }
            if (this.pointedEntity != null && (d3 < d2 || Util.mc.objectMouseOver == null)) {
                Util.mc.objectMouseOver = new RayTraceResult(this.pointedEntity, vec3d4);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    Util.mc.pointedEntity = this.pointedEntity;
                }
            }
            if (this.pointedEntity == null && bt.isOn()) {
                for (final EntityPlayer pl_box : Util.mc.world.playerEntities) {
                    if (pl_box == Util.mc.player) {
                        continue;
                    }
                    if (((IEntity)pl_box).getPosition_history().size() <= 0) {
                        continue;
                    }
                    for (int i = 0; i < ((IEntity)pl_box).getPosition_history().size(); ++i) {
                        final AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(((IEntity)pl_box).getPosition_history().get(i).getPosition().x - 0.3, ((IEntity)pl_box).getPosition_history().get(i).getPosition().y, ((IEntity)pl_box).getPosition_history().get(i).getPosition().z - 0.3, ((IEntity)pl_box).getPosition_history().get(i).getPosition().x + 0.3, ((IEntity)pl_box).getPosition_history().get(i).getPosition().y + 1.8, ((IEntity)pl_box).getPosition_history().get(i).getPosition().z + 0.3);
                        final RayTraceResult raytraceresult2 = axisalignedbb2.calculateIntercept(vec3d, vec3d3);
                        if (axisalignedbb2.contains(vec3d)) {
                            if (d3 >= 0.0) {
                                this.pointedEntity = (Entity)pl_box;
                                d3 = 0.0;
                                if (raytraceresult2 != null) {
                                    Util.mc.objectMouseOver = raytraceresult2;
                                }
                            }
                        }
                        else if (raytraceresult2 != null) {
                            final double d5 = vec3d.distanceTo(raytraceresult2.hitVec);
                            if (d5 < d3 || d3 == 0.0) {
                                if (pl_box.getLowestRidingEntity() == entity.getLowestRidingEntity() && !pl_box.canRiderInteract()) {
                                    if (d3 == 0.0) {
                                        this.pointedEntity = (Entity)pl_box;
                                    }
                                }
                                else {
                                    this.pointedEntity = (Entity)pl_box;
                                    d3 = d5;
                                }
                            }
                        }
                    }
                }
                if (this.pointedEntity != null) {
                    Util.mc.objectMouseOver = new RayTraceResult(this.pointedEntity);
                }
            }
            Util.mc.profiler.endSection();
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
    private Entity redirectMouseOver(final Minecraft mc) {
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled() && Keyboard.isKeyDown(FreeCam.getInstance().movePlayer.getValue().getKey())) {
            return (Entity)mc.player;
        }
        return mc.getRenderViewEntity();
    }
    
    @Redirect(method = { "updateCameraAndRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"))
    private void redirectTurn(final EntityPlayerSP entityPlayerSP, final float yaw, final float pitch) {
        try {
            final Minecraft mc = Minecraft.getMinecraft();
            final FreecamEvent event = new FreecamEvent();
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCanceled()) {
                if (Keyboard.isKeyDown(FreeCam.getInstance().movePlayer.getValue().getKey())) {
                    mc.player.turn(yaw, pitch);
                }
                else {
                    Objects.requireNonNull(mc.getRenderViewEntity(), "Render Entity").turn(yaw, pitch);
                }
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        entityPlayerSP.turn(yaw, pitch);
    }
    
    @Redirect(method = { "renderWorldPass" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isSpectator()Z"))
    public boolean redirectIsSpectator(final EntityPlayerSP entityPlayerSP) {
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.isCanceled() || (entityPlayerSP != null && entityPlayerSP.isSpectator());
    }
    
    @Inject(method = { "renderHand" }, at = { @At("HEAD") }, cancellable = true)
    public void renderHandMain(final float partialTicks, final int pass, final CallbackInfo ci) {
        final ItemShaders module = (ItemShaders)Thunderhack.moduleManager.getModuleByClass((Class)ItemShaders.class);
        if (module.isEnabled()) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (!module.cancelItem.getValue()) {
                this.doRenderHand(partialTicks, pass, mc);
            }
            if (module.glowESP.getValue() != ItemShaders.glowESPmode.None && module.fillShader.getValue() != ItemShaders.fillShadermode.None) {
                GlStateManager.pushMatrix();
                final RenderHand.PreBoth hand = new RenderHand.PreBoth(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand);
                this.doRenderHand(partialTicks, pass, mc);
                final RenderHand.PostBoth hand2 = new RenderHand.PostBoth(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand2);
                GlStateManager.popMatrix();
            }
            if (module.glowESP.getValue() != ItemShaders.glowESPmode.None) {
                GlStateManager.pushMatrix();
                final RenderHand.PreOutline hand3 = new RenderHand.PreOutline(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand3);
                this.doRenderHand(partialTicks, pass, mc);
                final RenderHand.PostOutline hand4 = new RenderHand.PostOutline(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand4);
                GlStateManager.popMatrix();
            }
            if (module.fillShader.getValue() != ItemShaders.fillShadermode.None) {
                GlStateManager.pushMatrix();
                final RenderHand.PreFill hand5 = new RenderHand.PreFill(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand5);
                this.doRenderHand(partialTicks, pass, mc);
                final RenderHand.PostFill hand6 = new RenderHand.PostFill(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand6);
                GlStateManager.popMatrix();
            }
            ci.cancel();
        }
    }
    
    @Shadow
    public abstract float getFOVModifier(final float p0, final boolean p1);
    
    @Shadow
    public abstract void hurtCameraEffect(final float p0);
    
    @Shadow
    public abstract void applyBobbing(final float p0);
    
    @Shadow
    public abstract void enableLightmap();
    
    @Shadow
    public abstract void disableLightmap();
    
    void doRenderHand(final float partialTicks, final int pass, final Minecraft mc) {
        if (!this.debugView) {
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            final float f = 0.07f;
            if (mc.gameSettings.anaglyph) {
                GlStateManager.translate(-(pass * 2 - 1) * 0.07f, 0.0f, 0.0f);
            }
            Project.gluPerspective(this.getFOVModifier(partialTicks, false), mc.displayWidth / (float)mc.displayHeight, 0.05f, this.farPlaneDistance * 2.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            if (mc.gameSettings.anaglyph) {
                GlStateManager.translate((pass * 2 - 1) * 0.1f, 0.0f, 0.0f);
            }
            GlStateManager.pushMatrix();
            this.hurtCameraEffect(partialTicks);
            if (mc.gameSettings.viewBobbing) {
                this.applyBobbing(partialTicks);
            }
            final boolean flag = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)mc.getRenderViewEntity()).isPlayerSleeping();
            if (!ForgeHooksClient.renderFirstPersonHand(mc.renderGlobal, partialTicks, pass) && mc.gameSettings.thirdPersonView == 0 && !flag && !mc.gameSettings.hideGUI && !mc.playerController.isSpectator()) {
                this.enableLightmap();
                this.itemRenderer.renderItemInFirstPerson(partialTicks);
                this.disableLightmap();
            }
            GlStateManager.popMatrix();
            if (mc.gameSettings.thirdPersonView == 0 && !flag) {
                this.itemRenderer.renderOverlays(partialTicks);
                this.hurtCameraEffect(partialTicks);
            }
            if (mc.gameSettings.viewBobbing) {
                this.applyBobbing(partialTicks);
            }
        }
    }
    
    @Shadow
    public abstract void renderHand(final float p0, final int p1);
    
    @Inject(method = { "orientCamera" }, at = { @At("HEAD") }, cancellable = true)
    private void orientCameraHook(final float partialTicks, final CallbackInfo ci) {
        if (((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).isOn()) {
            ci.cancel();
            this.orientCameraCustom(partialTicks);
        }
    }
    
    public void orientCameraCustom(final float partialTicks) {
        final Entity entity = Util.mc.getRenderViewEntity();
        assert entity != null;
        float f = entity.getEyeHeight();
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        double d2 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
        double d3 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
            ++f;
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!Util.mc.gameSettings.debugCamEnable) {
                final BlockPos blockpos = new BlockPos(entity);
                final IBlockState iblockstate = Util.mc.world.getBlockState(blockpos);
                ForgeHooksClient.orientBedCamera((IBlockAccess)Util.mc.world, blockpos, iblockstate, entity);
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (Util.mc.gameSettings.thirdPersonView > 0) {
            double d4 = (double)(((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).isOn() ? ((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).z.getValue() : ((double)(this.thirdPersonDistancePrev + (4.0f - this.thirdPersonDistancePrev) * partialTicks)));
            if (Util.mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float)(-d4));
            }
            else {
                float f2;
                float f3;
                if (((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).isOff()) {
                    f2 = entity.rotationYaw;
                    f3 = entity.rotationPitch;
                }
                else {
                    f2 = entity.rotationYaw + ((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).x.getValue();
                    f3 = entity.rotationPitch + ((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).y.getValue();
                }
                if (Util.mc.gameSettings.thirdPersonView == 2) {
                    f3 += 180.0f;
                }
                final double d5 = -MathHelper.sin(f2 * 0.017453292f) * MathHelper.cos(f3 * 0.017453292f) * d4;
                final double d6 = MathHelper.cos(f2 * 0.017453292f) * MathHelper.cos(f3 * 0.017453292f) * d4;
                final double d7 = -MathHelper.sin(f3 * 0.017453292f) * d4;
                for (int i = 0; i < 8; ++i) {
                    float f4 = (float)((i & 0x1) * 2 - 1);
                    float f5 = (float)((i >> 1 & 0x1) * 2 - 1);
                    float f6 = (float)((i >> 2 & 0x1) * 2 - 1);
                    f4 *= 0.1f;
                    f5 *= 0.1f;
                    f6 *= 0.1f;
                    final RayTraceResult raytraceresult = Util.mc.world.rayTraceBlocks(new Vec3d(d0 + f4, d2 + f5, d3 + f6), new Vec3d(d0 - d5 + f4 + f6, d2 - d7 + f5, d3 - d6 + f6));
                    if (raytraceresult != null) {
                        final double d8 = raytraceresult.hitVec.distanceTo(new Vec3d(d0, d2, d3));
                        if (d8 < d4) {
                            d4 = d8;
                        }
                    }
                }
                if (Util.mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(entity.rotationPitch - f3, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(entity.rotationYaw - f2, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, (float)(-d4));
                GlStateManager.rotate(f2 - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(f3 - entity.rotationPitch, 1.0f, 0.0f, 0.0f);
            }
        }
        else {
            GlStateManager.translate(0.0f, 0.0f, 0.05f);
        }
        if (!Util.mc.gameSettings.debugCamEnable) {
            float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f;
            final float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            final float f2 = 0.0f;
            if (entity instanceof EntityAnimal) {
                final EntityAnimal entityanimal = (EntityAnimal)entity;
                yaw = entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0f;
            }
            final IBlockState state = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)Util.mc.world, entity, partialTicks);
            final EntityViewRenderEvent.CameraSetup event = new EntityViewRenderEvent.CameraSetup(Util.mc.entityRenderer, entity, state, (double)partialTicks, yaw, pitch, f2);
            MinecraftForge.EVENT_BUS.post((Event)event);
            GlStateManager.rotate(event.getRoll(), 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(event.getPitch(), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(event.getYaw(), 0.0f, 1.0f, 0.0f);
        }
        GlStateManager.translate(0.0f, -f, 0.0f);
        d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        d2 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
        d3 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        this.cloudFog = Util.mc.renderGlobal.hasCloudFog(d0, d2, d3, partialTicks);
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
    public RayTraceResult rayTraceBlocks(final WorldClient world, final Vec3d start, final Vec3d end) {
        return ((NoCameraClip)Thunderhack.moduleManager.getModuleByClass((Class)NoCameraClip.class)).isOn() ? null : world.rayTraceBlocks(start, end);
    }
    
    @Inject(method = { "orientCamera" }, at = { @At("RETURN") })
    private void orientCameraStoreEyeHeight(final float partialTicks, final CallbackInfo ci) {
        if (!((LegitStrafe)Thunderhack.moduleManager.getModuleByClass((Class)LegitStrafe.class)).isOn()) {
            return;
        }
        final Entity entity = Util.mc.getRenderViewEntity();
        if (entity != null) {
            GlStateManager.translate(0.0f, entity.getEyeHeight() - 0.4f, 0.0f);
        }
    }
}
