//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import java.util.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.block.model.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ItemPhysics extends Module
{
    public static long Field1898;
    private static double Field1899;
    private static final Random Field1900;
    public Setting<Float> rotatespeed;
    
    public ItemPhysics() {
        super("ItemPhysics", "\u043e\u043f\u0438\u0441\u0430\u043d\u0438\u0435", Category.RENDER);
        this.rotatespeed = (Setting<Float>)this.register(new Setting("RotateSpeed", (T)1.0f, (T)0.1f, (T)5.0f));
    }
    
    private static int Method2280(final ItemStack itemStack) {
        int n = 1;
        if (itemStack.getCount() > 48) {
            n = 5;
        }
        else if (itemStack.getCount() > 32) {
            n = 4;
        }
        else if (itemStack.getCount() > 16) {
            n = 3;
        }
        else if (itemStack.getCount() > 1) {
            n = 2;
        }
        return n;
    }
    
    public void Method2279(final Entity entity, final double d, final double d2, final double d3) {
        ItemPhysics.Field1899 = (System.nanoTime() - ItemPhysics.Field1898) / 2500000.0 * this.rotatespeed.getValue();
        if (!ItemPhysics.mc.inGameHasFocus) {
            ItemPhysics.Field1899 = 0.0;
        }
        final EntityItem entityItem;
        final ItemStack itemStack;
        final int n = ((itemStack = (entityItem = (EntityItem)entity).getItem()) != null && itemStack.getItem() != null) ? (Item.getIdFromItem(itemStack.getItem()) + itemStack.getMetadata()) : 187;
        ItemPhysics.Field1900.setSeed(n);
        ItemPhysics.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        ItemPhysics.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        final IBakedModel iBakedModel = ItemPhysics.mc.getRenderItem().getItemModelMesher().getItemModel(itemStack);
        final boolean bl = iBakedModel.isGui3d();
        final boolean bl2 = iBakedModel.isGui3d();
        final int n2 = Method2280(itemStack);
        GlStateManager.translate((float)d, (float)d2, (float)d3);
        if (iBakedModel.isGui3d()) {
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
        }
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(entityItem.rotationYaw, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(0.0, 0.0, bl2 ? -0.08 : -0.04);
        if (bl2 || ItemPhysics.mc.getRenderManager().options != null) {
            if (bl2) {
                if (!entityItem.onGround) {
                    final double d4 = ItemPhysics.Field1899 * 2.0;
                    entityItem.rotationPitch += (float)d4;
                }
            }
            else if (!Double.isNaN(entityItem.posX) && !Double.isNaN(entityItem.posY) && !Double.isNaN(entityItem.posZ) && entityItem.world != null) {
                if (entityItem.onGround) {
                    entityItem.rotationPitch = 0.0f;
                }
                else {
                    final double d4 = ItemPhysics.Field1899 * 2.0;
                    entityItem.rotationPitch += (float)d4;
                }
            }
            GlStateManager.rotate(entityItem.rotationPitch, 1.0f, 0.0f, 0.0f);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        for (int i = 0; i < n2; ++i) {
            GlStateManager.pushMatrix();
            if (bl) {
                if (i > 0) {
                    final float f = (ItemPhysics.Field1900.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    final float f2 = (ItemPhysics.Field1900.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    final float f3 = (ItemPhysics.Field1900.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    GlStateManager.translate(f, f2, f3);
                }
                ItemPhysics.mc.getRenderItem().renderItem(itemStack, iBakedModel);
                GlStateManager.popMatrix();
            }
            else {
                ItemPhysics.mc.getRenderItem().renderItem(itemStack, iBakedModel);
                GlStateManager.popMatrix();
                GlStateManager.translate(0.0f, 0.0f, 0.05375f);
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        ItemPhysics.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        ItemPhysics.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent e) {
        ItemPhysics.Field1898 = System.nanoTime();
    }
    
    static {
        Field1900 = new Random();
    }
}
