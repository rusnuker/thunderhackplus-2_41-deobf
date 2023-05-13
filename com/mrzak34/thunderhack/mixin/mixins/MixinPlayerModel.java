//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;

@Mixin({ ModelPlayer.class })
public class MixinPlayerModel extends ModelBiped
{
    @Shadow
    public ModelRenderer bipedLeftArmwear;
    @Shadow
    public ModelRenderer bipedRightArmwear;
    
    public MixinPlayerModel(final float modelSize) {
        super(modelSize);
    }
    
    @Inject(at = { @At("TAIL") }, method = { "Lnet/minecraft/client/model/ModelPlayer;setRotationAngles(FFFFFFLnet/minecraft/entity/Entity;)V" })
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn, final CallbackInfo ci) {
        if (entityIn instanceof EntityPlayerSP && ((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).eatAnim.getValue()) {
            this.eatingAnimationRightHand(EnumHand.MAIN_HAND, (EntityPlayerSP)entityIn, ageInTicks);
            this.eatingAnimationLeftHand(EnumHand.OFF_HAND, (EntityPlayerSP)entityIn, ageInTicks);
        }
    }
    
    public void eatingAnimationRightHand(final EnumHand hand, final EntityPlayerSP entity, final float ageInTicks) {
        final ItemStack itemstack = entity.getHeldItem(hand);
        final boolean drinkingoreating = itemstack.getItemUseAction() == EnumAction.EAT || itemstack.getItemUseAction() == EnumAction.DRINK;
        if (entity.getItemInUseCount() > 0 && drinkingoreating && entity.getActiveHand() == hand) {
            this.bipedRightArm.rotateAngleY = -0.5f;
            this.bipedRightArm.rotateAngleX = -1.3f;
            this.bipedRightArm.rotateAngleZ = MathHelper.cos(ageInTicks) * 0.1f;
            this.copyModelAngles2(this.bipedRightArm, this.bipedRightArmwear);
            this.bipedHead.rotateAngleX = MathHelper.cos(ageInTicks) * 0.2f;
            this.bipedHead.rotateAngleY = this.bipedHeadwear.rotateAngleY;
            this.copyModelAngles2(this.bipedHead, this.bipedHeadwear);
        }
    }
    
    public void eatingAnimationLeftHand(final EnumHand hand, final EntityPlayerSP entity, final float ageInTicks) {
        final ItemStack itemstack = entity.getHeldItem(hand);
        final boolean drinkingoreating = itemstack.getItemUseAction() == EnumAction.EAT || itemstack.getItemUseAction() == EnumAction.DRINK;
        if (entity.getItemInUseCount() > 0 && drinkingoreating && entity.getActiveHand() == hand) {
            this.bipedLeftArm.rotateAngleY = 0.5f;
            this.bipedLeftArm.rotateAngleX = -1.3f;
            this.bipedLeftArm.rotateAngleZ = MathHelper.cos(ageInTicks) * 0.1f;
            this.copyModelAngles2(this.bipedLeftArm, this.bipedLeftArmwear);
            this.bipedHead.rotateAngleX = MathHelper.cos(ageInTicks) * 0.2f;
            this.bipedHead.rotateAngleY = this.bipedHeadwear.rotateAngleY;
            this.copyModelAngles2(this.bipedHead, this.bipedHeadwear);
        }
    }
    
    void copyModelAngles2(final ModelRenderer source, final ModelRenderer dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }
}
