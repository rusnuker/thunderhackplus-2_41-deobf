//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.dism;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.math.*;

public class RenderGib extends Render<EntityGib>
{
    private static final ResourceLocation zombieTexture;
    private static final ResourceLocation skeletonTexture;
    private static final ResourceLocation creeperTexture;
    public ModelGib modelGib;
    
    public RenderGib(final RenderManager manager) {
        super(manager);
        this.modelGib = new ModelGib();
    }
    
    public static float interpolateRotation(final float par1, final float par2, final float par3) {
        float f3;
        for (f3 = par2 - par1; f3 < -180.0f; f3 += 360.0f) {}
        while (f3 >= 180.0f) {
            f3 -= 360.0f;
        }
        return par1 + par3 * f3;
    }
    
    public ResourceLocation getEntityTexture(final EntityGib gib) {
        if (gib.parent instanceof EntityPlayer) {
            return ((AbstractClientPlayer)gib.parent).getLocationSkin();
        }
        if (gib.parent instanceof EntityZombie) {
            return RenderGib.zombieTexture;
        }
        if (gib.parent instanceof EntitySkeleton) {
            return RenderGib.skeletonTexture;
        }
        if (gib.parent instanceof EntityCreeper) {
            return RenderGib.creeperTexture;
        }
        return RenderGib.zombieTexture;
    }
    
    public void doRender(final EntityGib gib, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GlStateManager.disableCull();
        GlStateManager.pushMatrix();
        this.bindEntityTexture((Entity)gib);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0f, 1.0f, 1.0f, MathHelper.clamp((gib.groundTime >= (int)((Dismemberment)Thunderhack.moduleManager.getModuleByClass((Class)Dismemberment.class)).gibGroundTime.getValue()) ? (1.0f - (gib.groundTime - (int)((Dismemberment)Thunderhack.moduleManager.getModuleByClass((Class)Dismemberment.class)).gibGroundTime.getValue() + par9) / 20.0f) : 1.0f, 0.0f, 1.0f));
        GlStateManager.alphaFunc(516, 0.003921569f);
        GlStateManager.translate(par2, par4, par6);
        GlStateManager.translate(0.0f, (gib.type == 0) ? 0.25f : ((gib.type <= 2 && gib.parent instanceof EntitySkeleton) ? 0.0625f : 0.125f), 0.0f);
        GlStateManager.rotate(interpolateRotation(gib.prevRotationYaw, gib.rotationYaw, par9), 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(interpolateRotation(gib.prevRotationPitch, gib.rotationPitch, par9), -1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0f, 1.5f - gib.height * 0.5f, 0.0f);
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        this.modelGib.render((Entity)gib, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.enableCull();
    }
    
    static {
        zombieTexture = new ResourceLocation("textures/entity/zombie/zombie.png");
        skeletonTexture = new ResourceLocation("textures/entity/skeleton/skeleton.png");
        creeperTexture = new ResourceLocation("textures/entity/creeper/creeper.png");
    }
}
