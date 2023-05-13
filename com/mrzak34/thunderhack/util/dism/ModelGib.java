//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.dism;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;

public class ModelGib extends ModelBase
{
    public ModelRenderer skeleLeg;
    public ModelRenderer skeleArm;
    public ModelRenderer creeperFoot;
    public ModelRenderer head64;
    public ModelRenderer body64;
    public ModelRenderer leg64;
    public ModelRenderer arm64;
    public ModelRenderer head32;
    public ModelRenderer body32;
    
    public ModelGib() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        (this.leg64 = new ModelRenderer((ModelBase)this, 0, 16)).setTextureSize(64, 64);
        this.leg64.addBox(-2.0f, -6.0f, -2.0f, 4, 12, 4);
        this.leg64.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.leg64.rotateAngleX = 0.0f;
        this.leg64.rotateAngleY = 0.0f;
        this.leg64.rotateAngleZ = 0.0f;
        this.leg64.mirror = false;
        (this.arm64 = new ModelRenderer((ModelBase)this, 40, 16)).setTextureSize(64, 64);
        this.arm64.addBox(-2.0f, -6.0f, -2.0f, 4, 12, 4);
        this.arm64.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.arm64.rotateAngleX = 0.0f;
        this.arm64.rotateAngleY = 0.0f;
        this.arm64.rotateAngleZ = 0.0f;
        this.arm64.mirror = false;
        (this.skeleArm = new ModelRenderer((ModelBase)this, 40, 16)).setTextureSize(64, 32);
        this.skeleArm.addBox(-1.0f, -6.0f, -1.0f, 2, 12, 2);
        this.skeleArm.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.skeleArm.rotateAngleX = 0.0f;
        this.skeleArm.rotateAngleY = 0.0f;
        this.skeleArm.rotateAngleZ = 0.0f;
        this.skeleArm.mirror = false;
        (this.skeleLeg = new ModelRenderer((ModelBase)this, 0, 16)).setTextureSize(64, 32);
        this.skeleLeg.addBox(-1.0f, -6.0f, -1.0f, 2, 12, 2);
        this.skeleLeg.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.skeleLeg.rotateAngleX = 0.0f;
        this.skeleLeg.rotateAngleY = 0.0f;
        this.skeleLeg.rotateAngleZ = 0.0f;
        this.skeleLeg.mirror = false;
        (this.creeperFoot = new ModelRenderer((ModelBase)this, 0, 16)).setTextureSize(64, 32);
        this.creeperFoot.addBox(-2.0f, -3.0f, -2.0f, 4, 6, 4);
        this.creeperFoot.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.creeperFoot.rotateAngleX = 0.0f;
        this.creeperFoot.rotateAngleY = 0.0f;
        this.creeperFoot.rotateAngleZ = 0.0f;
        this.creeperFoot.mirror = false;
        (this.head64 = new ModelRenderer((ModelBase)this, 0, 0)).setTextureSize(64, 64);
        this.head64.addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.head64.setRotationPoint(0.0f, 20.0f, 0.0f);
        this.head64.rotateAngleX = 0.0f;
        this.head64.rotateAngleY = 0.0f;
        this.head64.rotateAngleZ = 0.0f;
        this.head64.mirror = false;
        this.head64.setTextureOffset(32, 0);
        this.head64.addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8, 1.1f);
        this.head64.setRotationPoint(0.0f, 20.0f, 0.0f);
        this.head64.rotateAngleX = 0.0f;
        this.head64.rotateAngleY = 0.0f;
        this.head64.rotateAngleZ = 0.0f;
        this.head64.mirror = false;
        (this.body64 = new ModelRenderer((ModelBase)this, 16, 16)).setTextureSize(64, 64);
        this.body64.addBox(-4.0f, -6.0f, -2.0f, 8, 12, 4);
        this.body64.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.body64.rotateAngleX = 0.0f;
        this.body64.rotateAngleY = 0.0f;
        this.body64.rotateAngleZ = 0.0f;
        this.body64.mirror = false;
        (this.head32 = new ModelRenderer((ModelBase)this, 0, 0)).setTextureSize(64, 32);
        this.head32.addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.head32.setRotationPoint(0.0f, 20.0f, 0.0f);
        this.head32.rotateAngleX = 0.0f;
        this.head32.rotateAngleY = 0.0f;
        this.head32.rotateAngleZ = 0.0f;
        this.head32.mirror = false;
        (this.body32 = new ModelRenderer((ModelBase)this, 16, 16)).setTextureSize(64, 32);
        this.body32.addBox(-4.0f, -6.0f, -2.0f, 8, 12, 4);
        this.body32.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.body32.rotateAngleX = 0.0f;
        this.body32.rotateAngleY = 0.0f;
        this.body32.rotateAngleZ = 0.0f;
        this.body32.mirror = false;
    }
    
    public void render(final Entity ent, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
        if (ent instanceof EntityGib) {
            final EntityGib gib = (EntityGib)ent;
            if (gib.type == -1) {}
            if (gib.type == 0) {
                if (gib.parent instanceof EntityZombie || gib.parent instanceof EntityPlayer) {
                    this.head64.render(f5);
                }
                else {
                    this.head32.render(f5);
                }
            }
            else if (gib.type == 1 || gib.type == 2) {
                if (gib.parent instanceof EntityZombie || gib.parent instanceof EntityPlayer) {
                    this.arm64.render(f5);
                }
                else {
                    this.skeleArm.render(f5);
                }
            }
            else if (gib.type == 3) {
                if (gib.parent instanceof EntityZombie || gib.parent instanceof EntityPlayer) {
                    this.body64.render(f5);
                }
                else {
                    this.body32.render(f5);
                }
            }
            else if (gib.type == 4 || gib.type == 5) {
                if (gib.parent instanceof EntityZombie || gib.parent instanceof EntityPlayer) {
                    this.leg64.render(f5);
                }
                else {
                    this.skeleLeg.render(f5);
                }
            }
            else if (gib.type >= 6) {
                this.creeperFoot.render(f5);
            }
        }
    }
    
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity ent) {
        this.arm64.rotateAngleY = f3 / 57.29578f;
        this.arm64.rotateAngleX = f4 / 57.29578f;
        this.leg64.rotateAngleY = f3 / 57.29578f;
        this.leg64.rotateAngleX = f4 / 57.29578f;
        this.skeleArm.rotateAngleY = f3 / 57.29578f;
        this.skeleArm.rotateAngleX = f4 / 57.29578f;
        this.skeleLeg.rotateAngleY = f3 / 57.29578f;
        this.skeleLeg.rotateAngleX = f4 / 57.29578f;
        this.creeperFoot.rotateAngleY = f3 / 57.29578f;
        this.creeperFoot.rotateAngleX = f4 / 57.29578f;
        this.head64.rotateAngleY = f3 / 57.29578f;
        this.head64.rotateAngleX = f4 / 57.29578f;
        this.body64.rotateAngleY = f3 / 57.29578f;
        this.body64.rotateAngleX = f4 / 57.29578f;
        this.head32.rotateAngleY = f3 / 57.29578f;
        this.head32.rotateAngleX = f4 / 57.29578f;
        this.body32.rotateAngleY = f3 / 57.29578f;
        this.body32.rotateAngleX = f4 / 57.29578f;
    }
}
