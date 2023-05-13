//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.entity.item.*;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.math.*;

@Mixin({ RenderEnderCrystal.class })
public abstract class MixinRenderEnderCrystal extends Render<EntityEnderCrystal>
{
    @Shadow
    @Final
    private ModelBase modelEnderCrystal;
    @Shadow
    @Final
    private ModelBase modelEnderCrystalNoBase;
    private float scale;
    
    @Deprecated
    protected MixinRenderEnderCrystal(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Inject(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, locals = LocalCapture.CAPTURE_FAILHARD, at = { @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityEnderCrystal;shouldShowBottom()Z", shift = At.Shift.BEFORE) }, cancellable = true)
    public void preRenderHook(final EntityEnderCrystal entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci, final float f, final float f1) {
        final float limbSwing = 0.0f;
        final float limbSwingAmount = f * 3.0f;
        final float ageInTicks = f1 * 0.2f;
        final float netHeadYaw = 0.0f;
        final float headPitch = 0.0f;
        final float scale = 0.0625f;
        final ModelBase modelBase = entity.shouldShowBottom() ? this.modelEnderCrystal : this.modelEnderCrystalNoBase;
        final RenderEnderCrystal renderLiving = RenderEnderCrystal.class.cast(this);
        final CrystalRenderEvent.Pre pre = new CrystalRenderEvent.Pre(renderLiving, (Entity)entity, modelBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        MinecraftForge.EVENT_BUS.post((Event)pre);
        if (pre.isCanceled()) {
            final CrystalRenderEvent.Post post = new CrystalRenderEvent.Post(renderLiving, (Entity)entity, modelBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            MinecraftForge.EVENT_BUS.post((Event)post);
            this.exitDoRender(entity, x, y, z, entityYaw, partialTicks, f1);
            ci.cancel();
        }
    }
    
    @Inject(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderEnderCrystal;renderOutlines:Z", ordinal = 1, shift = At.Shift.BEFORE) }, locals = LocalCapture.CAPTURE_FAILHARD)
    public void postRenderHook(final EntityEnderCrystal entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci, final float f, final float f1) {
        final float limbSwingAmount = f * 3.0f;
        final float ageInTicks = f1 * 0.2f;
        final ModelBase modelBase = entity.shouldShowBottom() ? this.modelEnderCrystal : this.modelEnderCrystalNoBase;
        final RenderEnderCrystal renderLiving = RenderEnderCrystal.class.cast(this);
        final CrystalRenderEvent.Post post = new CrystalRenderEvent.Post(renderLiving, (Entity)entity, modelBase, 0.0f, limbSwingAmount, ageInTicks, 0.0f, 0.0f, 0.0625f);
        MinecraftForge.EVENT_BUS.post((Event)post);
    }
    
    private void exitDoRender(final EntityEnderCrystal entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final float f1) {
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.popMatrix();
        final BlockPos blockpos = entity.getBeamTarget();
        if (blockpos != null) {
            this.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
            final float f2 = blockpos.getX() + 0.5f;
            final float f3 = blockpos.getY() + 0.5f;
            final float f4 = blockpos.getZ() + 0.5f;
            final double d0 = f2 - entity.posX;
            final double d2 = f3 - entity.posY;
            final double d3 = f4 - entity.posZ;
            RenderDragon.renderCrystalBeams(x + d0, y - 0.3 + f1 * 0.4f + d2, z + d3, partialTicks, (double)f2, (double)f3, (double)f4, entity.innerRotation, entity.posX, entity.posY, entity.posZ);
        }
        super.doRender((Entity)entity, x, y, z, entityYaw, partialTicks);
    }
}
