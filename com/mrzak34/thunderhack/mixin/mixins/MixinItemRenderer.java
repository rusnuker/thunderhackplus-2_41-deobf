//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.render.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.modules.render.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.modules.combat.*;

@Mixin(value = { ItemRenderer.class }, priority = 9998)
public abstract class MixinItemRenderer
{
    @Shadow
    public ItemStack itemStackOffHand;
    @Shadow
    public float prevEquippedProgressMainHand;
    @Shadow
    public float equippedProgressMainHand;
    private float spin;
    
    @Inject(method = { "transformSideFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void transformSideFirstPersonHook(final EnumHandSide hand, final float p_187459_2_, final CallbackInfo cancel) {
        final RenderItemEvent event = new RenderItemEvent(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (ViewModel.getInstance().isEnabled()) {
            final boolean bob = ViewModel.getInstance().isDisabled() || ViewModel.getInstance().doBob.getValue();
            final int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
            if (!ViewModel.getInstance().XBob.getValue()) {
                GlStateManager.translate(i * 0.56f, -0.52f + (bob ? p_187459_2_ : 0.0f) * -0.6f, -0.72f);
            }
            else {
                GlStateManager.translate(i * 0.56f, -0.52f, -0.72f - p_187459_2_ * -ViewModel.getInstance().zbobcorr.getValue());
            }
            if (hand == EnumHandSide.RIGHT) {
                GlStateManager.translate(event.getMainX(), event.getMainY(), event.getMainZ());
                RenderUtil.rotationHelper(event.getMainRotX(), event.getMainRotY(), event.getMainRotZ());
            }
            else {
                GlStateManager.translate(event.getOffX(), event.getOffY(), event.getOffZ());
                RenderUtil.rotationHelper(event.getOffRotX(), event.getOffRotY(), event.getOffRotZ());
            }
            cancel.cancel();
        }
    }
    
    @Inject(method = { "renderFireInFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void renderFireInFirstPersonHook(final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().fire.getValue()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "transformEatFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    private void transformEatFirstPersonHook(final float p_187454_1_, final EnumHandSide hand, final ItemStack stack, final CallbackInfo cancel) {
        if (ViewModel.getInstance().isEnabled()) {
            if (!ViewModel.getInstance().noEatAnimation.getValue()) {
                final float f = Minecraft.getMinecraft().player.getItemInUseCount() - p_187454_1_ + 1.0f;
                final float f2 = f / stack.getMaxItemUseDuration();
                if (f2 < 0.8f) {
                    final float f3 = MathHelper.abs(MathHelper.cos(f / 4.0f * 3.1415927f) * 0.1f);
                    GlStateManager.translate(0.0f, f3, 0.0f);
                }
                if (Thunderhack.class.getName().length() != 35) {
                    Minecraft.getMinecraft().shutdown();
                }
                final float f3 = 1.0f - (float)Math.pow(f2, 27.0);
                final int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
                GlStateManager.translate(f3 * 0.6f * i * ViewModel.getInstance().eatX.getValue(), f3 * 0.5f * -ViewModel.getInstance().eatY.getValue(), 0.0f);
                GlStateManager.rotate(i * f3 * 90.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(f3 * 10.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(i * f3 * 30.0f, 0.0f, 0.0f, 1.0f);
            }
            cancel.cancel();
        }
    }
    
    @Inject(method = { "renderSuffocationOverlay" }, at = { @At("HEAD") }, cancellable = true)
    public void renderSuffocationOverlay(final CallbackInfo ci) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().blocks.getValue()) {
            ci.cancel();
        }
    }
    
    @Shadow
    protected abstract void transformSideFirstPerson(final EnumHandSide p0, final float p1);
    
    @Shadow
    protected abstract void renderArmFirstPerson(final float p0, final float p1, final EnumHandSide p2);
    
    @Shadow
    protected abstract void transformEatFirstPerson(final float p0, final EnumHandSide p1, final ItemStack p2);
    
    @Shadow
    protected abstract void transformFirstPerson(final EnumHandSide p0, final float p1);
    
    @Shadow
    public abstract void renderItemSide(final EntityLivingBase p0, final ItemStack p1, final ItemCameraTransforms.TransformType p2, final boolean p3);
    
    @Shadow
    protected abstract void renderMapFirstPersonSide(final float p0, final EnumHandSide p1, final float p2, final ItemStack p3);
    
    @Shadow
    protected abstract void renderMapFirstPerson(final float p0, final float p1, final float p2);
    
    @Inject(method = { "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V" }, at = { @At("HEAD") }, cancellable = true)
    public void renderItemInFirstPersonHook(final AbstractClientPlayer player, final float p_187457_2_, final float p_187457_3_, final EnumHand hand, final float p_187457_5_, final ItemStack stack, final float p_187457_7_, final CallbackInfo ci) {
        if (((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).isEnabled()) {
            ci.cancel();
            this.renderAnimations(player, p_187457_2_, p_187457_3_, hand, p_187457_5_, stack, p_187457_7_);
        }
    }
    
    public void renderAnimations(final AbstractClientPlayer player, final float p_187457_2_, final float p_187457_3_, final EnumHand hand, final float p_187457_5_, final ItemStack stack, final float p_187457_7_) {
        final boolean flag = hand == EnumHand.MAIN_HAND;
        final EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
        GlStateManager.pushMatrix();
        if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
                this.renderArmFirstPerson(p_187457_7_, p_187457_5_, enumhandside);
            }
        }
        else if (stack.getItem() instanceof ItemMap) {
            if (flag && this.itemStackOffHand.isEmpty()) {
                this.renderMapFirstPerson(p_187457_3_, p_187457_7_, p_187457_5_);
            }
            else {
                this.renderMapFirstPersonSide(p_187457_7_, enumhandside, p_187457_5_, stack);
            }
        }
        else {
            final boolean flag2 = enumhandside == EnumHandSide.RIGHT;
            Label_1736: {
                if (player.isHandActive() && player.getItemInUseCount() > 0 && player.getActiveHand() == hand) {
                    final int j = flag2 ? 1 : -1;
                    switch (stack.getItemUseAction()) {
                        case NONE: {
                            this.transformSideFirstPerson(enumhandside, p_187457_7_);
                            break;
                        }
                        case EAT:
                        case DRINK: {
                            this.transformEatFirstPerson(p_187457_2_, enumhandside, stack);
                            this.transformSideFirstPerson(enumhandside, p_187457_7_);
                            break;
                        }
                        case BLOCK: {
                            this.transformSideFirstPerson(enumhandside, p_187457_7_);
                            break;
                        }
                        case BOW: {
                            this.transformSideFirstPerson(enumhandside, p_187457_7_);
                            GlStateManager.translate(j * -0.2785682f, 0.18344387f, 0.15731531f);
                            GlStateManager.rotate(-13.935f, 1.0f, 0.0f, 0.0f);
                            GlStateManager.rotate(j * 35.3f, 0.0f, 1.0f, 0.0f);
                            GlStateManager.rotate(j * -9.785f, 0.0f, 0.0f, 1.0f);
                            final float f5 = stack.getMaxItemUseDuration() - (Util.mc.player.getItemInUseCount() - p_187457_2_ + 1.0f);
                            float f6 = f5 / 20.0f;
                            f6 = (f6 * f6 + f6 * 2.0f) / 3.0f;
                            if (f6 > 1.0f) {
                                f6 = 1.0f;
                            }
                            if (f6 > 0.1f) {
                                final float f7 = MathHelper.sin((f5 - 0.1f) * 1.3f);
                                final float f8 = f6 - 0.1f;
                                final float f9 = f7 * f8;
                                GlStateManager.translate(f9 * 0.0f, f9 * 0.004f, f9 * 0.0f);
                            }
                            GlStateManager.translate(f6 * 0.0f, f6 * 0.0f, f6 * 0.04f);
                            GlStateManager.scale(1.0f, 1.0f, 1.0f + f6 * 0.2f);
                            GlStateManager.rotate(j * 45.0f, 0.0f, -1.0f, 0.0f);
                            break;
                        }
                    }
                }
                else {
                    final float f10 = -0.4f * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * 3.1415927f);
                    final float f11 = 0.2f * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * 6.2831855f);
                    final float f12 = -0.2f * MathHelper.sin(p_187457_5_ * 3.1415927f);
                    final int i = flag2 ? 1 : -1;
                    final float equipProgress = 1.0f - (this.prevEquippedProgressMainHand + (this.equippedProgressMainHand - this.prevEquippedProgressMainHand) * p_187457_2_);
                    final float swingprogress = Util.mc.player.getSwingProgress(p_187457_2_);
                    final Animations.rmode mode = ((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).rMode.getValue();
                    if (((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).isEnabled() && ((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).rMode.getValue() != Animations.rmode.Slow) {
                        Label_0731: {
                            if (!((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).isEnabled() || ((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).auraOnly.getValue()) {
                                if (((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).isEnabled() && ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).isEnabled()) {
                                    final Aura aura = (Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class);
                                    if (Aura.target != null) {
                                        break Label_0731;
                                    }
                                }
                                GlStateManager.translate(i * f10, f11, f12);
                                this.transformSideFirstPerson(enumhandside, p_187457_7_);
                                this.transformFirstPerson(enumhandside, p_187457_5_);
                                break Label_1736;
                            }
                        }
                        Label_0828: {
                            if (!((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).isEnabled() || ((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).auraOnly.getValue()) {
                                if (((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).isEnabled() && ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).isEnabled()) {
                                    final Aura aura2 = (Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class);
                                    if (Aura.target != null) {
                                        break Label_0828;
                                    }
                                }
                                this.transformSideFirstPerson(enumhandside, p_187457_7_);
                                this.transformFirstPerson(enumhandside, p_187457_5_);
                                break Label_1736;
                            }
                        }
                        if (enumhandside != (Util.mc.gameSettings.mainHand.equals((Object)EnumHandSide.LEFT) ? EnumHandSide.RIGHT : EnumHandSide.LEFT)) {
                            if (mode == Animations.rmode.Default) {
                                this.transformSideFirstPerson2(enumhandside, p_187457_7_);
                                final float var3 = MathHelper.sin(swingprogress * swingprogress * 3.1415927f);
                                final float var4 = MathHelper.sin(MathHelper.sqrt(swingprogress) * 3.1415927f);
                                GlStateManager.rotate(var4 * -20.0f, 0.0f, 0.0f, 2.0f);
                                GlStateManager.rotate(var4 * -75.0f, 1.0f, 0.0f, 0.0f);
                            }
                            else if (mode == Animations.rmode.Swipe) {
                                this.transformFirstPersonItem(equipProgress / 3.0f, swingprogress);
                                this.translate();
                                final float var3 = MathHelper.sin(swingprogress * swingprogress * 3.1415927f);
                                final float var4 = MathHelper.sin(MathHelper.sqrt(swingprogress) * 3.1415927f);
                                GlStateManager.rotate(var3 * -20.0f, 0.0f, 1.0f, 0.0f);
                                GlStateManager.rotate(var4 * -20.0f, 0.0f, 0.0f, 2.0f);
                                GlStateManager.rotate(var4 * -75.0f, 1.0f, 0.0f, 0.0f);
                            }
                            else if (mode == Animations.rmode.Rich) {
                                this.transformSideFirstPerson2(enumhandside, p_187457_7_);
                                this.translate4();
                                final float var3 = MathHelper.sin(swingprogress * swingprogress * 3.1415927f);
                                final float var4 = MathHelper.sin(MathHelper.sqrt(swingprogress) * 3.1415927f);
                                GlStateManager.rotate(var4 * -20.0f, 0.0f, 0.0f, 2.0f);
                                GlStateManager.rotate(var4 * -75.0f, 1.0f, 0.0f, 0.0f);
                            }
                            else if (mode == Animations.rmode.New) {
                                this.transformSideFirstPerson2(enumhandside, p_187457_7_);
                                this.translate3();
                                final float var3 = MathHelper.sin(swingprogress * swingprogress * 3.1415927f);
                                final float var4 = MathHelper.sin(MathHelper.sqrt(swingprogress) * 3.1415927f);
                                GlStateManager.rotate(var4 * -70.0f, var4 * 40.0f, 0.0f, 0.0f);
                                GlStateManager.rotate(40.0f, -30.0f, 0.0f, 0.0f);
                            }
                            else if (mode == Animations.rmode.Oblique) {
                                this.transformSideFirstPerson2(enumhandside, p_187457_7_);
                                final float var5 = MathHelper.sin(MathHelper.sqrt(swingprogress) * 3.1415927f);
                                GlStateManager.rotate(var5 * -70.0f, var5 * 70.0f, 0.0f, var5 * -90.0f);
                            }
                            else if (mode == Animations.rmode.Glide) {
                                this.transformFirstPersonItem(equipProgress / 2.0f, 0.0f);
                                this.translate();
                            }
                            else if (mode == Animations.rmode.Fap) {
                                this.transformSideFirstPerson2(enumhandside, p_187457_7_);
                                GlStateManager.translate(0.96f, -0.02f, -0.71999997f);
                                GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
                                final float var3 = MathHelper.sin(0.0f);
                                final float var4 = MathHelper.sin(MathHelper.sqrt(0.0f) * 3.1415927f);
                                GlStateManager.rotate(var3 * -20.0f, 0.0f, 1.0f, 0.0f);
                                GlStateManager.rotate(var4 * -20.0f, 0.0f, 0.0f, 1.0f);
                                GlStateManager.rotate(var4 * -80.0f, 1.0f, 0.0f, 0.0f);
                                GlStateManager.translate(-0.5f, 0.2f, 0.0f);
                                GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
                                GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
                                GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
                                final int alpha = (int)Math.min(255L, ((System.currentTimeMillis() % 255L > 127L) ? Math.abs(Math.abs(System.currentTimeMillis()) % 255L - 255L) : (System.currentTimeMillis() % 255L)) * 2L);
                                final float f13 = (f11 > 0.5) ? (1.0f - f11) : f11;
                                GlStateManager.translate(0.3f, -0.0f, 0.4f);
                                GlStateManager.rotate(0.0f, 0.0f, 0.0f, 1.0f);
                                GlStateManager.translate(0.0f, 0.5f, 0.0f);
                                GlStateManager.rotate(90.0f, 1.0f, 0.0f, -1.0f);
                                GlStateManager.translate(0.6f, 0.5f, 0.0f);
                                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, -1.0f);
                                GlStateManager.rotate(-10.0f, 1.0f, 0.0f, -1.0f);
                                GlStateManager.rotate(-f13 * 10.0f, 10.0f, 10.0f, -9.0f);
                                GlStateManager.rotate(10.0f, -1.0f, 0.0f, 0.0f);
                                GlStateManager.translate(0.0, 0.0, -0.5);
                                GlStateManager.rotate(((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).abobka228 ? (-alpha / ((Animations)Thunderhack.moduleManager.getModuleByClass((Class)Animations.class)).fapSmooth.getValue()) : 1.0f, 1.0f, -0.0f, 1.0f);
                                GlStateManager.translate(0.0, 0.0, 0.5);
                            }
                        }
                        else {
                            GlStateManager.translate(i * f10, f11, f12);
                            this.transformSideFirstPerson(enumhandside, p_187457_7_);
                            this.transformFirstPerson(enumhandside, p_187457_5_);
                        }
                    }
                    else {
                        GlStateManager.translate(i * f10, f11, f12);
                        this.transformSideFirstPerson(enumhandside, p_187457_7_);
                        this.transformFirstPerson(enumhandside, p_187457_5_);
                    }
                }
            }
            this.renderItemSide((EntityLivingBase)player, stack, flag2 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag2);
        }
        GlStateManager.popMatrix();
    }
    
    private void transformSideFirstPerson2(final EnumHandSide enumHandSide, final float p_187459_2_) {
        final RenderItemEvent event = new RenderItemEvent(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (ViewModel.getInstance().isEnabled()) {
            final boolean bob = ViewModel.getInstance().isDisabled() || ViewModel.getInstance().doBob.getValue();
            final int i = (enumHandSide == EnumHandSide.RIGHT) ? 1 : -1;
            if (!ViewModel.getInstance().XBob.getValue()) {
                GlStateManager.translate(i * 0.56f, -0.52f + (bob ? p_187459_2_ : 0.0f) * -0.6f, -0.72f);
            }
            else {
                GlStateManager.translate(i * 0.56f, -0.52f, -0.72f - p_187459_2_ * -ViewModel.getInstance().zbobcorr.getValue());
            }
            if (enumHandSide == EnumHandSide.RIGHT) {
                GlStateManager.translate(event.getMainX(), event.getMainY(), event.getMainZ());
                RenderUtil.rotationHelper(event.getMainRotX(), event.getMainRotY(), event.getMainRotZ());
            }
            else {
                GlStateManager.translate(event.getOffX(), event.getOffY(), event.getOffZ());
                RenderUtil.rotationHelper(event.getOffRotX(), event.getOffRotY(), event.getOffRotZ());
            }
        }
        final int j = (enumHandSide == EnumHandSide.RIGHT) ? 1 : -1;
        GlStateManager.translate((float)j, -0.52f, -0.72f);
    }
    
    private void transformFirstPersonItem(final float equipProgress, final float swingProgress) {
        final RenderItemEvent event = new RenderItemEvent(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (ViewModel.getInstance().isEnabled()) {
            final boolean bob = ViewModel.getInstance().isDisabled() || ViewModel.getInstance().doBob.getValue();
            if (!ViewModel.getInstance().XBob.getValue()) {
                GlStateManager.translate(0.56f, -0.52f + (bob ? equipProgress : 0.0f) * -0.6f, -0.72f);
            }
            else {
                GlStateManager.translate(0.56f, -0.52f, -0.72f - equipProgress * -ViewModel.getInstance().zbobcorr.getValue());
            }
            GlStateManager.translate(event.getMainX(), event.getMainY(), event.getMainZ());
            RenderUtil.rotationHelper(event.getMainRotX(), event.getMainRotY(), event.getMainRotZ());
        }
        GlStateManager.translate(0.56f, -0.44f, -0.71999997f);
        GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927f);
        final float f2 = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927f);
        GlStateManager.rotate(f * -20.0f, 0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(f2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(f2 * -80.0f, 0.01f, 0.0f, 0.0f);
        GlStateManager.translate(0.4f, 0.2f, 0.2f);
    }
    
    private void translate() {
        GlStateManager.rotate(20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(20.0f, 0.0f, 1.0f, 0.0f);
    }
    
    private void translate3() {
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(70.0f, 0.0f, 1.0f, 0.0f);
    }
    
    private void translate4() {
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-70.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
    }
    
    private void translate2() {
        GlStateManager.rotate(50.0f, 10.0f, 0.0f, 0.0f);
    }
}
