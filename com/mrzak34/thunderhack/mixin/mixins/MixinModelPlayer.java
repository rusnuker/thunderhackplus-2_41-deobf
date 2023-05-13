//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.model.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.setting.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;

@Mixin({ ModelPlayer.class })
public class MixinModelPlayer extends ModelBiped
{
    @Shadow
    public ModelRenderer bipedLeftArmwear;
    @Shadow
    public ModelRenderer bipedRightArmwear;
    @Shadow
    public ModelRenderer bipedLeftLegwear;
    @Shadow
    public ModelRenderer bipedRightLegwear;
    @Shadow
    public ModelRenderer bipedBodyWear;
    public ModelRenderer left_leg;
    public ModelRenderer right_leg;
    public ModelRenderer body;
    public ModelRenderer eye;
    public ModelRenderer rabbitBone;
    public ModelRenderer rabbitRleg;
    public ModelRenderer rabbitLarm;
    public ModelRenderer rabbitRarm;
    public ModelRenderer rabbitLleg;
    public ModelRenderer rabbitHead;
    public ModelRenderer fredhead;
    public ModelRenderer armLeft;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    public ModelRenderer armRight;
    public ModelRenderer fredbody;
    public ModelRenderer armLeftpad2;
    public ModelRenderer torso;
    public ModelRenderer earRightpad_1;
    public ModelRenderer armRightpad2;
    public ModelRenderer legLeftpad;
    public ModelRenderer hat;
    public ModelRenderer legLeftpad2;
    public ModelRenderer armRight2;
    public ModelRenderer legRight2;
    public ModelRenderer earRightpad;
    public ModelRenderer armLeft2;
    public ModelRenderer frednose;
    public ModelRenderer earLeft;
    public ModelRenderer footRight;
    public ModelRenderer legRightpad2;
    public ModelRenderer legRightpad;
    public ModelRenderer armLeftpad;
    public ModelRenderer legLeft2;
    public ModelRenderer footLeft;
    public ModelRenderer hat2;
    public ModelRenderer armRightpad;
    public ModelRenderer earRight;
    public ModelRenderer crotch;
    public ModelRenderer jaw;
    public ModelRenderer handRight;
    public ModelRenderer handLeft;
    
    public MixinModelPlayer(final float modelSize, final boolean smallArmsIn) {
        super(modelSize, 0.0f, 64, 64);
        (this.body = new ModelRenderer((ModelBase)this)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.body.setTextureOffset(34, 8).addBox(-4.0f, 6.0f, -3.0f, 8, 12, 6);
        this.body.setTextureOffset(15, 10).addBox(-3.0f, 9.0f, 3.0f, 6, 8, 3);
        this.body.setTextureOffset(26, 0).addBox(-3.0f, 5.0f, -3.0f, 6, 1, 6);
        this.eye = new ModelRenderer((ModelBase)this);
        this.eye.setTextureOffset(0, 10).addBox(-3.0f, 7.0f, -4.0f, 6, 4, 1);
        (this.left_leg = new ModelRenderer((ModelBase)this)).setRotationPoint(-2.0f, 18.0f, 0.0f);
        this.left_leg.setTextureOffset(0, 0).addBox(2.9f, 0.0f, -1.5f, 3, 6, 3, 0.0f);
        (this.right_leg = new ModelRenderer((ModelBase)this)).setRotationPoint(2.0f, 18.0f, 0.0f);
        this.right_leg.setTextureOffset(13, 0).addBox(-5.9f, 0.0f, -1.5f, 3, 6, 3);
        final ModelRenderer footRight = new ModelRenderer((ModelBase)this, 22, 39);
        (this.footRight = footRight).setRotationPoint(0.0f, 8.0f, 0.0f);
        this.footRight.addBox(-2.5f, 0.0f, -6.0f, 5, 3, 8, 0.0f);
        this.setRotationAngle(this.footRight, -0.034906585f, 0.0f, 0.0f);
        final ModelRenderer earRight = new ModelRenderer((ModelBase)this, 8, 0);
        (this.earRight = earRight).setRotationPoint(-4.5f, -5.5f, 0.0f);
        this.earRight.addBox(-1.0f, -3.0f, -0.5f, 2, 3, 1, 0.0f);
        this.setRotationAngle(this.earRight, 0.05235988f, 0.0f, -1.0471976f);
        final ModelRenderer legLeftpad = new ModelRenderer((ModelBase)this, 48, 39);
        (this.legLeftpad = legLeftpad).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.legLeftpad.addBox(-3.0f, 0.0f, -3.0f, 6, 9, 6, 0.0f);
        final ModelRenderer earRightpad_1 = new ModelRenderer((ModelBase)this, 40, 39);
        (this.earRightpad_1 = earRightpad_1).setRotationPoint(0.0f, -1.0f, 0.0f);
        this.earRightpad_1.addBox(-2.0f, -5.0f, -1.0f, 4, 4, 2, 0.0f);
        final ModelRenderer legLeft = new ModelRenderer((ModelBase)this, 54, 10);
        (this.legLeft = legLeft).setRotationPoint(3.3f, 12.5f, 0.0f);
        this.legLeft.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2, 0.0f);
        final ModelRenderer armRightpad2 = new ModelRenderer((ModelBase)this, 0, 26);
        (this.armRightpad2 = armRightpad2).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.armRightpad2.addBox(-2.5f, 0.0f, -2.5f, 5, 7, 5, 0.0f);
        final ModelRenderer handLeft = new ModelRenderer((ModelBase)this, 58, 56);
        (this.handLeft = handLeft).setRotationPoint(0.0f, 8.0f, 0.0f);
        this.handLeft.addBox(-2.0f, 0.0f, -2.5f, 4, 4, 5, 0.0f);
        this.setRotationAngle(this.handLeft, 0.0f, 0.0f, 0.05235988f);
        final ModelRenderer armLeft = new ModelRenderer((ModelBase)this, 62, 10);
        (this.armLeft = armLeft).setRotationPoint(6.5f, -8.0f, 0.0f);
        this.armLeft.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2, 0.0f);
        this.setRotationAngle(this.armLeft, 0.0f, 0.0f, -0.2617994f);
        final ModelRenderer legRight = new ModelRenderer((ModelBase)this, 90, 8);
        (this.legRight = legRight).setRotationPoint(-3.3f, 12.5f, 0.0f);
        this.legRight.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2, 0.0f);
        final ModelRenderer armLeft2 = new ModelRenderer((ModelBase)this, 90, 48);
        (this.armLeft2 = armLeft2).setRotationPoint(0.0f, 9.6f, 0.0f);
        this.armLeft2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, 0.0f);
        this.setRotationAngle(this.armLeft2, -0.17453292f, 0.0f, 0.0f);
        final ModelRenderer legRight2 = new ModelRenderer((ModelBase)this, 20, 35);
        (this.legRight2 = legRight2).setRotationPoint(0.0f, 9.6f, 0.0f);
        this.legRight2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, 0.0f);
        this.setRotationAngle(this.legRight2, 0.034906585f, 0.0f, 0.0f);
        final ModelRenderer armLeftpad2 = new ModelRenderer((ModelBase)this, 0, 58);
        (this.armLeftpad2 = armLeftpad2).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.armLeftpad2.addBox(-2.5f, 0.0f, -2.5f, 5, 7, 5, 0.0f);
        final ModelRenderer legLeft2 = new ModelRenderer((ModelBase)this, 72, 48);
        (this.legLeft2 = legLeft2).setRotationPoint(0.0f, 9.6f, 0.0f);
        this.legLeft2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, 0.0f);
        this.setRotationAngle(this.legLeft2, 0.034906585f, 0.0f, 0.0f);
        final ModelRenderer hat = new ModelRenderer((ModelBase)this, 70, 24);
        (this.hat = hat).setRotationPoint(0.0f, -8.4f, 0.0f);
        this.hat.addBox(-3.0f, -0.5f, -3.0f, 6, 1, 6, 0.0f);
        this.setRotationAngle(this.hat, -0.017453292f, 0.0f, 0.0f);
        final ModelRenderer earRightpad = new ModelRenderer((ModelBase)this, 85, 0);
        (this.earRightpad = earRightpad).setRotationPoint(0.0f, -1.0f, 0.0f);
        this.earRightpad.addBox(-2.0f, -5.0f, -1.0f, 4, 4, 2, 0.0f);
        final ModelRenderer crotch = new ModelRenderer((ModelBase)this, 56, 0);
        (this.crotch = crotch).setRotationPoint(0.0f, 9.5f, 0.0f);
        this.crotch.addBox(-5.5f, 0.0f, -3.5f, 11, 3, 7, 0.0f);
        final ModelRenderer torso = new ModelRenderer((ModelBase)this, 8, 0);
        (this.torso = torso).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.torso.addBox(-6.0f, -9.0f, -4.0f, 12, 18, 8, 0.0f);
        this.setRotationAngle(this.torso, 0.017453292f, 0.0f, 0.0f);
        final ModelRenderer armRight2 = new ModelRenderer((ModelBase)this, 90, 20);
        (this.armRight2 = armRight2).setRotationPoint(0.0f, 9.6f, 0.0f);
        this.armRight2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, 0.0f);
        this.setRotationAngle(this.armRight2, -0.17453292f, 0.0f, 0.0f);
        final ModelRenderer handRight = new ModelRenderer((ModelBase)this, 20, 26);
        (this.handRight = handRight).setRotationPoint(0.0f, 8.0f, 0.0f);
        this.handRight.addBox(-2.0f, 0.0f, -2.5f, 4, 4, 5, 0.0f);
        this.setRotationAngle(this.handRight, 0.0f, 0.0f, -0.05235988f);
        final ModelRenderer fredbody = new ModelRenderer((ModelBase)this, 0, 0);
        (this.fredbody = fredbody).setRotationPoint(0.0f, -9.0f, 0.0f);
        this.fredbody.addBox(-1.0f, -14.0f, -1.0f, 2, 24, 2, 0.0f);
        final ModelRenderer fredhead = new ModelRenderer((ModelBase)this, 39, 22);
        (this.fredhead = fredhead).setRotationPoint(0.0f, -13.0f, -0.5f);
        this.fredhead.addBox(-5.5f, -8.0f, -4.5f, 11, 8, 9, 0.0f);
        final ModelRenderer legRightpad = new ModelRenderer((ModelBase)this, 73, 33);
        (this.legRightpad = legRightpad).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.legRightpad.addBox(-3.0f, 0.0f, -3.0f, 6, 9, 6, 0.0f);
        final ModelRenderer frednose = new ModelRenderer((ModelBase)this, 17, 67);
        (this.frednose = frednose).setRotationPoint(0.0f, -2.0f, -4.5f);
        this.frednose.addBox(-4.0f, -2.0f, -3.0f, 8, 4, 3, 0.0f);
        final ModelRenderer legLeftpad2 = new ModelRenderer((ModelBase)this, 16, 50);
        (this.legLeftpad2 = legLeftpad2).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.legLeftpad2.addBox(-2.5f, 0.0f, -3.0f, 5, 7, 6, 0.0f);
        final ModelRenderer armRightpad3 = new ModelRenderer((ModelBase)this, 70, 10);
        (this.armRightpad = armRightpad3).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.armRightpad.addBox(-2.5f, 0.0f, -2.5f, 5, 9, 5, 0.0f);
        final ModelRenderer armLeftpad3 = new ModelRenderer((ModelBase)this, 38, 54);
        (this.armLeftpad = armLeftpad3).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.armLeftpad.addBox(-2.5f, 0.0f, -2.5f, 5, 9, 5, 0.0f);
        final ModelRenderer hat2 = new ModelRenderer((ModelBase)this, 78, 61);
        (this.hat2 = hat2).setRotationPoint(0.0f, 0.1f, 0.0f);
        this.hat2.addBox(-2.0f, -4.0f, -2.0f, 4, 4, 4, 0.0f);
        this.setRotationAngle(this.hat2, -0.017453292f, 0.0f, 0.0f);
        final ModelRenderer legRightpad2 = new ModelRenderer((ModelBase)this, 0, 39);
        (this.legRightpad2 = legRightpad2).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.legRightpad2.addBox(-2.5f, 0.0f, -3.0f, 5, 7, 6, 0.0f);
        final ModelRenderer jaw = new ModelRenderer((ModelBase)this, 49, 65);
        (this.jaw = jaw).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.jaw.addBox(-5.0f, 0.0f, -4.5f, 10, 3, 9, 0.0f);
        this.setRotationAngle(this.jaw, 0.08726646f, 0.0f, 0.0f);
        final ModelRenderer armRight3 = new ModelRenderer((ModelBase)this, 48, 0);
        (this.armRight = armRight3).setRotationPoint(-6.5f, -8.0f, 0.0f);
        this.armRight.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2, 0.0f);
        this.setRotationAngle(this.armRight, 0.0f, 0.0f, 0.2617994f);
        final ModelRenderer footLeft = new ModelRenderer((ModelBase)this, 72, 50);
        (this.footLeft = footLeft).setRotationPoint(0.0f, 8.0f, 0.0f);
        this.footLeft.addBox(-2.5f, 0.0f, -6.0f, 5, 3, 8, 0.0f);
        this.setRotationAngle(this.footLeft, -0.034906585f, 0.0f, 0.0f);
        final ModelRenderer earLeft = new ModelRenderer((ModelBase)this, 40, 0);
        (this.earLeft = earLeft).setRotationPoint(4.5f, -5.5f, 0.0f);
        this.earLeft.addBox(-1.0f, -3.0f, -0.5f, 2, 3, 1, 0.0f);
        this.setRotationAngle(this.earLeft, 0.05235988f, 0.0f, 1.0471976f);
        this.legRight2.addChild(this.footRight);
        this.fredhead.addChild(this.earRight);
        this.legLeft.addChild(this.legLeftpad);
        this.earLeft.addChild(this.earRightpad_1);
        this.fredbody.addChild(this.legLeft);
        this.armRight2.addChild(this.armRightpad2);
        this.armLeft2.addChild(this.handLeft);
        this.fredbody.addChild(this.armLeft);
        this.fredbody.addChild(this.legRight);
        this.armLeft.addChild(this.armLeft2);
        this.legRight.addChild(this.legRight2);
        this.armLeft2.addChild(this.armLeftpad2);
        this.legLeft.addChild(this.legLeft2);
        this.fredhead.addChild(this.hat);
        this.earRight.addChild(this.earRightpad);
        this.fredbody.addChild(this.crotch);
        this.fredbody.addChild(this.torso);
        this.armRight.addChild(this.armRight2);
        this.armRight2.addChild(this.handRight);
        this.fredbody.addChild(this.fredhead);
        this.legRight.addChild(this.legRightpad);
        this.fredhead.addChild(this.frednose);
        this.legLeft2.addChild(this.legLeftpad2);
        this.armRight.addChild(this.armRightpad);
        this.armLeft.addChild(this.armLeftpad);
        this.hat.addChild(this.hat2);
        this.legRight2.addChild(this.legRightpad2);
        this.fredhead.addChild(this.jaw);
        this.fredbody.addChild(this.armRight);
        this.legLeft2.addChild(this.footLeft);
        this.fredhead.addChild(this.earLeft);
    }
    
    @Inject(method = { "setRotationAngles" }, at = { @At("RETURN") })
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn, final CallbackInfo callbackInfo) {
        if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null && entityIn instanceof EntityPlayer) {
            Skeleton.addEntity((EntityPlayer)entityIn, ModelPlayer.class.cast(this));
        }
    }
    
    public void generatemodel() {
        (this.body = new ModelRenderer((ModelBase)this)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.body.setTextureOffset(34, 8).addBox(-4.0f, 6.0f, -3.0f, 8, 12, 6);
        this.body.setTextureOffset(15, 10).addBox(-3.0f, 9.0f, 3.0f, 6, 8, 3);
        this.body.setTextureOffset(26, 0).addBox(-3.0f, 5.0f, -3.0f, 6, 1, 6);
        this.eye = new ModelRenderer((ModelBase)this);
        this.eye.setTextureOffset(0, 10).addBox(-3.0f, 7.0f, -4.0f, 6, 4, 1);
        (this.left_leg = new ModelRenderer((ModelBase)this)).setRotationPoint(-2.0f, 18.0f, 0.0f);
        this.left_leg.setTextureOffset(0, 0).addBox(2.9f, 0.0f, -1.5f, 3, 6, 3, 0.0f);
        (this.right_leg = new ModelRenderer((ModelBase)this)).setRotationPoint(2.0f, 18.0f, 0.0f);
        this.right_leg.setTextureOffset(13, 0).addBox(-5.9f, 0.0f, -1.5f, 3, 6, 3);
        (this.rabbitBone = new ModelRenderer((ModelBase)this)).setRotationPoint(0.0f, 24.0f, 0.0f);
        this.rabbitBone.cubeList.add(new ModelBox(this.rabbitBone, 28, 45, -5.0f, -13.0f, -5.0f, 10, 11, 8, 0.0f, false));
        (this.rabbitRleg = new ModelRenderer((ModelBase)this)).setRotationPoint(-3.0f, -2.0f, -1.0f);
        this.rabbitBone.addChild(this.rabbitRleg);
        this.rabbitRleg.cubeList.add(new ModelBox(this.rabbitRleg, 0, 0, -2.0f, 0.0f, -2.0f, 4, 2, 4, 0.0f, false));
        (this.rabbitLarm = new ModelRenderer((ModelBase)this)).setRotationPoint(5.0f, -13.0f, -1.0f);
        this.setRotationAngle(this.rabbitLarm, 0.0f, 0.0f, -0.0873f);
        this.rabbitBone.addChild(this.rabbitLarm);
        this.rabbitLarm.cubeList.add(new ModelBox(this.rabbitLarm, 0, 0, 0.0f, 0.0f, -2.0f, 2, 8, 4, 0.0f, false));
        (this.rabbitRarm = new ModelRenderer((ModelBase)this)).setRotationPoint(-5.0f, -13.0f, -1.0f);
        this.setRotationAngle(this.rabbitRarm, 0.0f, 0.0f, 0.0873f);
        this.rabbitBone.addChild(this.rabbitRarm);
        this.rabbitRarm.cubeList.add(new ModelBox(this.rabbitRarm, 0, 0, -2.0f, 0.0f, -2.0f, 2, 8, 4, 0.0f, false));
        (this.rabbitLleg = new ModelRenderer((ModelBase)this)).setRotationPoint(3.0f, -2.0f, -1.0f);
        this.rabbitBone.addChild(this.rabbitLleg);
        this.rabbitLleg.cubeList.add(new ModelBox(this.rabbitLleg, 0, 0, -2.0f, 0.0f, -2.0f, 4, 2, 4, 0.0f, false));
        (this.rabbitHead = new ModelRenderer((ModelBase)this)).setRotationPoint(0.0f, -14.0f, -1.0f);
        this.rabbitBone.addChild(this.rabbitHead);
        this.rabbitHead.cubeList.add(new ModelBox(this.rabbitHead, 0, 0, -3.0f, 0.0f, -4.0f, 6, 1, 6, 0.0f, false));
        this.rabbitHead.cubeList.add(new ModelBox(this.rabbitHead, 56, 0, -5.0f, -9.0f, -5.0f, 2, 3, 2, 0.0f, false));
        this.rabbitHead.cubeList.add(new ModelBox(this.rabbitHead, 56, 0, 3.0f, -9.0f, -5.0f, 2, 3, 2, 0.0f, true));
        this.rabbitHead.cubeList.add(new ModelBox(this.rabbitHead, 0, 45, -4.0f, -11.0f, -4.0f, 8, 11, 8, 0.0f, false));
        this.rabbitHead.cubeList.add(new ModelBox(this.rabbitHead, 46, 0, 1.0f, -20.0f, 0.0f, 3, 9, 1, 0.0f, false));
        this.rabbitHead.cubeList.add(new ModelBox(this.rabbitHead, 46, 0, -4.0f, -20.0f, 0.0f, 3, 9, 1, 0.0f, false));
        this.textureWidth = 100;
        this.textureHeight = 80;
        final ModelRenderer footRight = new ModelRenderer((ModelBase)this, 22, 39);
        (this.footRight = footRight).setRotationPoint(0.0f, 8.0f, 0.0f);
        this.footRight.addBox(-2.5f, 0.0f, -6.0f, 5, 3, 8, 0.0f);
        this.setRotationAngle(this.footRight, -0.034906585f, 0.0f, 0.0f);
        final ModelRenderer earRight = new ModelRenderer((ModelBase)this, 8, 0);
        (this.earRight = earRight).setRotationPoint(-4.5f, -5.5f, 0.0f);
        this.earRight.addBox(-1.0f, -3.0f, -0.5f, 2, 3, 1, 0.0f);
        this.setRotationAngle(this.earRight, 0.05235988f, 0.0f, -1.0471976f);
        final ModelRenderer legLeftpad = new ModelRenderer((ModelBase)this, 48, 39);
        (this.legLeftpad = legLeftpad).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.legLeftpad.addBox(-3.0f, 0.0f, -3.0f, 6, 9, 6, 0.0f);
        final ModelRenderer earRightpad_1 = new ModelRenderer((ModelBase)this, 40, 39);
        (this.earRightpad_1 = earRightpad_1).setRotationPoint(0.0f, -1.0f, 0.0f);
        this.earRightpad_1.addBox(-2.0f, -5.0f, -1.0f, 4, 4, 2, 0.0f);
        final ModelRenderer legLeft = new ModelRenderer((ModelBase)this, 54, 10);
        (this.legLeft = legLeft).setRotationPoint(3.3f, 12.5f, 0.0f);
        this.legLeft.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2, 0.0f);
        final ModelRenderer armRightpad2 = new ModelRenderer((ModelBase)this, 0, 26);
        (this.armRightpad2 = armRightpad2).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.armRightpad2.addBox(-2.5f, 0.0f, -2.5f, 5, 7, 5, 0.0f);
        final ModelRenderer handLeft = new ModelRenderer((ModelBase)this, 58, 56);
        (this.handLeft = handLeft).setRotationPoint(0.0f, 8.0f, 0.0f);
        this.handLeft.addBox(-2.0f, 0.0f, -2.5f, 4, 4, 5, 0.0f);
        this.setRotationAngle(this.handLeft, 0.0f, 0.0f, 0.05235988f);
        final ModelRenderer armLeft = new ModelRenderer((ModelBase)this, 62, 10);
        (this.armLeft = armLeft).setRotationPoint(6.5f, -8.0f, 0.0f);
        this.armLeft.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2, 0.0f);
        this.setRotationAngle(this.armLeft, 0.0f, 0.0f, -0.2617994f);
        final ModelRenderer legRight = new ModelRenderer((ModelBase)this, 90, 8);
        (this.legRight = legRight).setRotationPoint(-3.3f, 12.5f, 0.0f);
        this.legRight.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2, 0.0f);
        final ModelRenderer armLeft2 = new ModelRenderer((ModelBase)this, 90, 48);
        (this.armLeft2 = armLeft2).setRotationPoint(0.0f, 9.6f, 0.0f);
        this.armLeft2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, 0.0f);
        this.setRotationAngle(this.armLeft2, -0.17453292f, 0.0f, 0.0f);
        final ModelRenderer legRight2 = new ModelRenderer((ModelBase)this, 20, 35);
        (this.legRight2 = legRight2).setRotationPoint(0.0f, 9.6f, 0.0f);
        this.legRight2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, 0.0f);
        this.setRotationAngle(this.legRight2, 0.034906585f, 0.0f, 0.0f);
        final ModelRenderer armLeftpad2 = new ModelRenderer((ModelBase)this, 0, 58);
        (this.armLeftpad2 = armLeftpad2).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.armLeftpad2.addBox(-2.5f, 0.0f, -2.5f, 5, 7, 5, 0.0f);
        final ModelRenderer legLeft2 = new ModelRenderer((ModelBase)this, 72, 48);
        (this.legLeft2 = legLeft2).setRotationPoint(0.0f, 9.6f, 0.0f);
        this.legLeft2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, 0.0f);
        this.setRotationAngle(this.legLeft2, 0.034906585f, 0.0f, 0.0f);
        final ModelRenderer hat = new ModelRenderer((ModelBase)this, 70, 24);
        (this.hat = hat).setRotationPoint(0.0f, -8.4f, 0.0f);
        this.hat.addBox(-3.0f, -0.5f, -3.0f, 6, 1, 6, 0.0f);
        this.setRotationAngle(this.hat, -0.017453292f, 0.0f, 0.0f);
        final ModelRenderer earRightpad = new ModelRenderer((ModelBase)this, 85, 0);
        (this.earRightpad = earRightpad).setRotationPoint(0.0f, -1.0f, 0.0f);
        this.earRightpad.addBox(-2.0f, -5.0f, -1.0f, 4, 4, 2, 0.0f);
        final ModelRenderer crotch = new ModelRenderer((ModelBase)this, 56, 0);
        (this.crotch = crotch).setRotationPoint(0.0f, 9.5f, 0.0f);
        this.crotch.addBox(-5.5f, 0.0f, -3.5f, 11, 3, 7, 0.0f);
        final ModelRenderer torso = new ModelRenderer((ModelBase)this, 8, 0);
        (this.torso = torso).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.torso.addBox(-6.0f, -9.0f, -4.0f, 12, 18, 8, 0.0f);
        this.setRotationAngle(this.torso, 0.017453292f, 0.0f, 0.0f);
        final ModelRenderer armRight2 = new ModelRenderer((ModelBase)this, 90, 20);
        (this.armRight2 = armRight2).setRotationPoint(0.0f, 9.6f, 0.0f);
        this.armRight2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, 0.0f);
        this.setRotationAngle(this.armRight2, -0.17453292f, 0.0f, 0.0f);
        final ModelRenderer handRight = new ModelRenderer((ModelBase)this, 20, 26);
        (this.handRight = handRight).setRotationPoint(0.0f, 8.0f, 0.0f);
        this.handRight.addBox(-2.0f, 0.0f, -2.5f, 4, 4, 5, 0.0f);
        this.setRotationAngle(this.handRight, 0.0f, 0.0f, -0.05235988f);
        final ModelRenderer fredbody = new ModelRenderer((ModelBase)this, 0, 0);
        (this.fredbody = fredbody).setRotationPoint(0.0f, -9.0f, 0.0f);
        this.fredbody.addBox(-1.0f, -14.0f, -1.0f, 2, 24, 2, 0.0f);
        final ModelRenderer fredhead = new ModelRenderer((ModelBase)this, 39, 22);
        (this.fredhead = fredhead).setRotationPoint(0.0f, -13.0f, -0.5f);
        this.fredhead.addBox(-5.5f, -8.0f, -4.5f, 11, 8, 9, 0.0f);
        final ModelRenderer legRightpad = new ModelRenderer((ModelBase)this, 73, 33);
        (this.legRightpad = legRightpad).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.legRightpad.addBox(-3.0f, 0.0f, -3.0f, 6, 9, 6, 0.0f);
        final ModelRenderer frednose = new ModelRenderer((ModelBase)this, 17, 67);
        (this.frednose = frednose).setRotationPoint(0.0f, -2.0f, -4.5f);
        this.frednose.addBox(-4.0f, -2.0f, -3.0f, 8, 4, 3, 0.0f);
        final ModelRenderer legLeftpad2 = new ModelRenderer((ModelBase)this, 16, 50);
        (this.legLeftpad2 = legLeftpad2).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.legLeftpad2.addBox(-2.5f, 0.0f, -3.0f, 5, 7, 6, 0.0f);
        final ModelRenderer armRightpad3 = new ModelRenderer((ModelBase)this, 70, 10);
        (this.armRightpad = armRightpad3).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.armRightpad.addBox(-2.5f, 0.0f, -2.5f, 5, 9, 5, 0.0f);
        final ModelRenderer armLeftpad3 = new ModelRenderer((ModelBase)this, 38, 54);
        (this.armLeftpad = armLeftpad3).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.armLeftpad.addBox(-2.5f, 0.0f, -2.5f, 5, 9, 5, 0.0f);
        final ModelRenderer hat2 = new ModelRenderer((ModelBase)this, 78, 61);
        (this.hat2 = hat2).setRotationPoint(0.0f, 0.1f, 0.0f);
        this.hat2.addBox(-2.0f, -4.0f, -2.0f, 4, 4, 4, 0.0f);
        this.setRotationAngle(this.hat2, -0.017453292f, 0.0f, 0.0f);
        final ModelRenderer legRightpad2 = new ModelRenderer((ModelBase)this, 0, 39);
        (this.legRightpad2 = legRightpad2).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.legRightpad2.addBox(-2.5f, 0.0f, -3.0f, 5, 7, 6, 0.0f);
        final ModelRenderer jaw = new ModelRenderer((ModelBase)this, 49, 65);
        (this.jaw = jaw).setRotationPoint(0.0f, 0.5f, 0.0f);
        this.jaw.addBox(-5.0f, 0.0f, -4.5f, 10, 3, 9, 0.0f);
        this.setRotationAngle(this.jaw, 0.08726646f, 0.0f, 0.0f);
        final ModelRenderer armRight3 = new ModelRenderer((ModelBase)this, 48, 0);
        (this.armRight = armRight3).setRotationPoint(-6.5f, -8.0f, 0.0f);
        this.armRight.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2, 0.0f);
        this.setRotationAngle(this.armRight, 0.0f, 0.0f, 0.2617994f);
        final ModelRenderer footLeft = new ModelRenderer((ModelBase)this, 72, 50);
        (this.footLeft = footLeft).setRotationPoint(0.0f, 8.0f, 0.0f);
        this.footLeft.addBox(-2.5f, 0.0f, -6.0f, 5, 3, 8, 0.0f);
        this.setRotationAngle(this.footLeft, -0.034906585f, 0.0f, 0.0f);
        final ModelRenderer earLeft = new ModelRenderer((ModelBase)this, 40, 0);
        (this.earLeft = earLeft).setRotationPoint(4.5f, -5.5f, 0.0f);
        this.earLeft.addBox(-1.0f, -3.0f, -0.5f, 2, 3, 1, 0.0f);
        this.setRotationAngle(this.earLeft, 0.05235988f, 0.0f, 1.0471976f);
        this.legRight2.addChild(this.footRight);
        this.fredhead.addChild(this.earRight);
        this.legLeft.addChild(this.legLeftpad);
        this.earLeft.addChild(this.earRightpad_1);
        this.fredbody.addChild(this.legLeft);
        this.armRight2.addChild(this.armRightpad2);
        this.armLeft2.addChild(this.handLeft);
        this.fredbody.addChild(this.armLeft);
        this.fredbody.addChild(this.legRight);
        this.armLeft.addChild(this.armLeft2);
        this.legRight.addChild(this.legRight2);
        this.armLeft2.addChild(this.armLeftpad2);
        this.legLeft.addChild(this.legLeft2);
        this.fredhead.addChild(this.hat);
        this.earRight.addChild(this.earRightpad);
        this.fredbody.addChild(this.crotch);
        this.fredbody.addChild(this.torso);
        this.armRight.addChild(this.armRight2);
        this.armRight2.addChild(this.handRight);
        this.fredbody.addChild(this.fredhead);
        this.legRight.addChild(this.legRightpad);
        this.fredhead.addChild(this.frednose);
        this.legLeft2.addChild(this.legLeftpad2);
        this.armRight.addChild(this.armRightpad);
        this.armLeft.addChild(this.armLeftpad);
        this.hat.addChild(this.hat2);
        this.legRight2.addChild(this.legRightpad2);
        this.fredhead.addChild(this.jaw);
        this.fredbody.addChild(this.armRight);
        this.legLeft2.addChild(this.footLeft);
        this.fredhead.addChild(this.earLeft);
    }
    
    public void setRotationAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
    public void renderHook(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale, final CallbackInfo ci) {
        final Models customModel = (Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class);
        if (customModel.isOn()) {
            ci.cancel();
            this.renderCustom(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }
    
    public void renderCustom(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (this.left_leg == null) {
            this.generatemodel();
        }
        final Models customModel = (Models)Thunderhack.moduleManager.getModuleByClass((Class)Models.class);
        GlStateManager.pushMatrix();
        if ((!customModel.onlySelf.getValue() || entityIn == Minecraft.getMinecraft().player || (Thunderhack.friendManager.isFriend(entityIn.getName()) && customModel.friends.getValue())) && customModel.isEnabled()) {
            if (customModel.Mode.getValue() == Models.mode.Amogus) {
                final boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
                this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292f;
                if (flag) {
                    this.bipedHead.rotateAngleX = -0.7853982f;
                }
                else {
                    this.bipedHead.rotateAngleX = headPitch * 0.017453292f;
                }
                this.bipedBody.rotateAngleY = 0.0f;
                this.bipedRightArm.rotationPointZ = 0.0f;
                this.bipedRightArm.rotationPointX = -5.0f;
                this.bipedLeftArm.rotationPointZ = 0.0f;
                this.bipedLeftArm.rotationPointX = 5.0f;
                float f = 1.0f;
                if (flag) {
                    f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
                    f /= 0.2f;
                    f *= f * f;
                }
                if (f < 1.0f) {
                    f = 1.0f;
                }
                this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 2.0f * limbSwingAmount * 0.5f / f;
                this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 2.0f * limbSwingAmount * 0.5f / f;
                this.bipedRightArm.rotateAngleZ = 0.0f;
                this.bipedLeftArm.rotateAngleZ = 0.0f;
                this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount / f;
                this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount / f;
                this.right_leg.rotateAngleY = 0.0f;
                this.left_leg.rotateAngleY = 0.0f;
                this.right_leg.rotateAngleZ = 0.0f;
                this.left_leg.rotateAngleZ = 0.0f;
                Color bodyColor = customModel.bodyColor.getValue().getColorObject();
                Color eyeColor = customModel.eyeColor.getValue().getColorObject();
                Color legsColor = customModel.legsColor.getValue().getColorObject();
                if (customModel.friendHighlight.getValue() && Thunderhack.friendManager.isFriend(entityIn.getName())) {
                    bodyColor = Color.GREEN;
                    eyeColor = Color.WHITE;
                    legsColor = Color.GREEN;
                }
                if (this.isChild) {
                    GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    GlStateManager.translate(0.0f, 24.0f * scale, 0.0f);
                    this.body.render(scale);
                    this.left_leg.render(scale);
                    this.right_leg.render(scale);
                }
                else {
                    GlStateManager.translate(0.0, -0.8, 0.0);
                    GlStateManager.scale(1.8, 1.6, 1.6);
                    RenderUtil.glColor(bodyColor);
                    GlStateManager.translate(0.0, 0.15, 0.0);
                    this.body.render(scale);
                    RenderUtil.glColor(eyeColor);
                    this.eye.render(scale);
                    RenderUtil.glColor(legsColor);
                    GlStateManager.translate(0.0, -0.15, 0.0);
                    this.left_leg.render(scale);
                    this.right_leg.render(scale);
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                }
            }
            else if (customModel.isEnabled() && customModel.Mode.getValue() == Models.mode.Rabbit) {
                GlStateManager.pushMatrix();
                GlStateManager.scale(1.25, 1.25, 1.25);
                GlStateManager.translate(0.0, -0.3, 0.0);
                this.rabbitHead.rotateAngleX = this.bipedHead.rotateAngleX;
                this.rabbitHead.rotateAngleY = this.bipedHead.rotateAngleY;
                this.rabbitHead.rotateAngleZ = this.bipedHead.rotateAngleZ;
                this.rabbitLarm.rotateAngleX = this.bipedLeftArm.rotateAngleX;
                this.rabbitLarm.rotateAngleY = this.bipedLeftArm.rotateAngleY;
                this.rabbitLarm.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
                this.rabbitRarm.rotateAngleX = this.bipedRightArm.rotateAngleX;
                this.rabbitRarm.rotateAngleY = this.bipedRightArm.rotateAngleY;
                this.rabbitRarm.rotateAngleZ = this.bipedRightArm.rotateAngleZ;
                this.rabbitRleg.rotateAngleX = this.bipedRightLeg.rotateAngleX;
                this.rabbitRleg.rotateAngleY = this.bipedRightLeg.rotateAngleY;
                this.rabbitRleg.rotateAngleZ = this.bipedRightLeg.rotateAngleZ;
                this.rabbitLleg.rotateAngleX = this.bipedLeftLeg.rotateAngleX;
                this.rabbitLleg.rotateAngleY = this.bipedLeftLeg.rotateAngleY;
                this.rabbitLleg.rotateAngleZ = this.bipedLeftLeg.rotateAngleZ;
                this.rabbitBone.render(scale);
                GlStateManager.popMatrix();
            }
            else if (customModel.isEnabled() && customModel.Mode.getValue() == Models.mode.Freddy) {
                this.fredhead.rotateAngleX = this.bipedHead.rotateAngleX;
                this.fredhead.rotateAngleY = this.bipedHead.rotateAngleY;
                this.fredhead.rotateAngleZ = this.bipedHead.rotateAngleZ;
                this.armLeft.rotateAngleX = this.bipedLeftArm.rotateAngleX;
                this.armLeft.rotateAngleY = this.bipedLeftArm.rotateAngleY;
                this.armLeft.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
                this.legRight.rotateAngleX = this.bipedRightLeg.rotateAngleX;
                this.legRight.rotateAngleY = this.bipedRightLeg.rotateAngleY;
                this.legRight.rotateAngleZ = this.bipedRightLeg.rotateAngleZ;
                this.legLeft.rotateAngleX = this.bipedLeftLeg.rotateAngleX;
                this.legLeft.rotateAngleY = this.bipedLeftLeg.rotateAngleY;
                this.legLeft.rotateAngleZ = this.bipedLeftLeg.rotateAngleZ;
                this.armRight.rotateAngleX = this.bipedRightArm.rotateAngleX;
                this.armRight.rotateAngleY = this.bipedRightArm.rotateAngleY;
                this.armRight.rotateAngleZ = this.bipedRightArm.rotateAngleZ;
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.75, 0.65, 0.75);
                GlStateManager.translate(0.0, 0.85, 0.0);
                this.fredbody.render(scale);
                GlStateManager.popMatrix();
            }
        }
        else {
            super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            if (this.isChild) {
                final float f2 = 2.0f;
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                GlStateManager.translate(0.0f, 24.0f * scale, 0.0f);
                this.bipedLeftLegwear.render(scale);
                this.bipedRightLegwear.render(scale);
                this.bipedLeftArmwear.render(scale);
                this.bipedRightArmwear.render(scale);
                this.bipedBodyWear.render(scale);
            }
            else {
                if (entityIn.isSneaking()) {
                    GlStateManager.translate(0.0f, 0.2f, 0.0f);
                }
                this.bipedLeftLegwear.render(scale);
                this.bipedRightLegwear.render(scale);
                this.bipedLeftArmwear.render(scale);
                this.bipedRightArmwear.render(scale);
                this.bipedBodyWear.render(scale);
            }
        }
        GlStateManager.popMatrix();
    }
}
