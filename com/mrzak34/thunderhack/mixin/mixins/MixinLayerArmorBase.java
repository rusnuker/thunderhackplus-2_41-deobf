//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.util.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.inventory.*;

@Mixin({ LayerArmorBase.class })
public class MixinLayerArmorBase
{
    @Inject(method = { "doRenderLayer" }, at = { @At("HEAD") }, cancellable = true)
    public void doRenderLayer(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale, final CallbackInfo ci) {
        if (NoRender.getInstance().isEnabled() && NoRender.getInstance().noArmor.getValue() == NoRender.NoArmor.ALL) {
            ci.cancel();
        }
        if (Thunderhack.moduleManager == null) {
            return;
        }
        if (Thunderhack.friendManager == null) {
            return;
        }
        if (((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).isOn() && ((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).onlySelf.getValue() && entitylivingbaseIn == Util.mc.player) {
            ci.cancel();
        }
        else if (((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).isOn() && !((Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class)).onlySelf.getValue()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderArmorLayer" }, at = { @At("HEAD") }, cancellable = true)
    public void renderArmorLayer(final EntityLivingBase entityLivingBaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale, final EntityEquipmentSlot slotIn, final CallbackInfo ci) {
        if (NoRender.getInstance().isEnabled() && NoRender.getInstance().noArmor.getValue() == NoRender.NoArmor.HELMET && slotIn == EntityEquipmentSlot.HEAD) {
            ci.cancel();
        }
    }
}
