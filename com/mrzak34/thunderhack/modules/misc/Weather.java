//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import java.util.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.biome.*;

public class Weather extends Module
{
    private static final Random RANDOM;
    private static final ResourceLocation RAIN_TEXTURES;
    private static final ResourceLocation SNOW_TEXTURES;
    private final Setting<Integer> height;
    private final Setting<Float> strength;
    public Setting<Boolean> snow;
    
    public Weather() {
        super("Weather", "\u0438\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u043f\u043e\u0433\u043e\u0434\u0443 \u0432 \u043c\u0438\u0440\u0435-\u043d\u0430 \u043a\u043b\u0438\u0435\u043d\u0442\u0441\u043a\u043e\u0439 \u0441\u0442\u043e\u0440\u043e\u043d\u0435", Category.MISC);
        this.height = (Setting<Integer>)this.register(new Setting("Height", (T)80, (T)0, (T)255));
        this.strength = (Setting<Float>)this.register(new Setting("Strength", (T)0.8f, (T)0.1f, (T)2.0f));
        this.snow = (Setting<Boolean>)this.register(new Setting("Snow", (T)true));
    }
    
    public void render(final float partialTicks) {
        final float f = this.strength.getValue();
        final EntityRenderer renderer = Weather.mc.entityRenderer;
        renderer.enableLightmap();
        final Entity entity = Weather.mc.getRenderViewEntity();
        if (entity == null) {
            return;
        }
        final World world = (World)Weather.mc.world;
        final int i = MathHelper.floor(entity.posX);
        final int j = MathHelper.floor(entity.posY);
        final int k = MathHelper.floor(entity.posZ);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.disableCull();
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.alphaFunc(516, 0.1f);
        final double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        final double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
        final double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        final int l = MathHelper.floor(d2);
        int i2 = 5;
        if (Weather.mc.gameSettings.fancyGraphics) {
            i2 = 10;
        }
        int j2 = -1;
        final float f2 = ((IEntityRenderer)renderer).getRendererUpdateCount() + partialTicks;
        bufferbuilder.setTranslation(-d0, -d2, -d3);
        final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for (int k2 = k - i2; k2 <= k + i2; ++k2) {
            for (int l2 = i - i2; l2 <= i + i2; ++l2) {
                final int i3 = (k2 - k + 16) * 32 + l2 - i + 16;
                final double d4 = ((IEntityRenderer)renderer).getRainXCoords()[i3] * 0.5;
                final double d5 = ((IEntityRenderer)renderer).getRainYCoords()[i3] * 0.5;
                blockpos$mutableblockpos.setPos(l2, 0, k2);
                final Biome biome = world.getBiome((BlockPos)blockpos$mutableblockpos);
                final int j3 = this.height.getValue();
                int k3 = j - i2;
                int l3 = j + i2;
                if (k3 < j3) {
                    k3 = j3;
                }
                if (l3 < j3) {
                    l3 = j3;
                }
                final int i4 = Math.max(j3, l);
                if (k3 != l3) {
                    Weather.RANDOM.setSeed(l2 * (long)l2 * 3121L + l2 * 45238971L ^ k2 * (long)k2 * 418711L + k2 * 13761L);
                    blockpos$mutableblockpos.setPos(l2, k3, k2);
                    final float f3 = biome.getTemperature((BlockPos)blockpos$mutableblockpos);
                    if (!this.snow.getValue()) {
                        if (j2 != 0) {
                            if (j2 >= 0) {
                                tessellator.draw();
                            }
                            j2 = 0;
                            Weather.mc.getTextureManager().bindTexture(Weather.RAIN_TEXTURES);
                            bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }
                        final double d6 = -((((IEntityRenderer)renderer).getRendererUpdateCount() + l2 * l2 * 3121 + l2 * 45238971 + k2 * k2 * 418711 + k2 * 13761 & 0x1F) + (double)partialTicks) / 32.0 * (3.0 + Weather.RANDOM.nextDouble());
                        final double d7 = l2 + 0.5f - entity.posX;
                        final double d8 = k2 + 0.5f - entity.posZ;
                        final float f4 = MathHelper.sqrt(d7 * d7 + d8 * d8) / i2;
                        final float f5 = ((1.0f - f4 * f4) * 0.5f + 0.5f) * f;
                        blockpos$mutableblockpos.setPos(l2, i4, k2);
                        final int j4 = world.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0);
                        final int k4 = j4 >> 16 & 0xFFFF;
                        final int l4 = j4 & 0xFFFF;
                        bufferbuilder.pos(l2 - d4 + 0.5, (double)l3, k2 - d5 + 0.5).tex(0.0, k3 * 0.25 + d6).color(1.0f, 1.0f, 1.0f, f5).lightmap(k4, l4).endVertex();
                        bufferbuilder.pos(l2 + d4 + 0.5, (double)l3, k2 + d5 + 0.5).tex(1.0, k3 * 0.25 + d6).color(1.0f, 1.0f, 1.0f, f5).lightmap(k4, l4).endVertex();
                        bufferbuilder.pos(l2 + d4 + 0.5, (double)k3, k2 + d5 + 0.5).tex(1.0, l3 * 0.25 + d6).color(1.0f, 1.0f, 1.0f, f5).lightmap(k4, l4).endVertex();
                        bufferbuilder.pos(l2 - d4 + 0.5, (double)k3, k2 - d5 + 0.5).tex(0.0, l3 * 0.25 + d6).color(1.0f, 1.0f, 1.0f, f5).lightmap(k4, l4).endVertex();
                    }
                    else {
                        if (j2 != 1) {
                            if (j2 >= 0) {
                                tessellator.draw();
                            }
                            j2 = 1;
                            Weather.mc.getTextureManager().bindTexture(Weather.SNOW_TEXTURES);
                            bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }
                        final double d9 = -((((IEntityRenderer)renderer).getRendererUpdateCount() & 0x1FF) + partialTicks) / 512.0f;
                        final double d10 = Weather.RANDOM.nextDouble() + f2 * 0.01 * (float)Weather.RANDOM.nextGaussian();
                        final double d11 = Weather.RANDOM.nextDouble() + f2 * (float)Weather.RANDOM.nextGaussian() * 0.001;
                        final double d12 = l2 + 0.5f - entity.posX;
                        final double d13 = k2 + 0.5f - entity.posZ;
                        final float f6 = MathHelper.sqrt(d12 * d12 + d13 * d13) / i2;
                        final float f7 = ((1.0f - f6 * f6) * 0.3f + 0.5f) * f;
                        blockpos$mutableblockpos.setPos(l2, i4, k2);
                        final int i5 = (world.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
                        final int j5 = i5 >> 16 & 0xFFFF;
                        final int k5 = i5 & 0xFFFF;
                        bufferbuilder.pos(l2 - d4 + 0.5, (double)l3, k2 - d5 + 0.5).tex(0.0 + d10, k3 * 0.25 + d9 + d11).color(1.0f, 1.0f, 1.0f, f7).lightmap(j5, k5).endVertex();
                        bufferbuilder.pos(l2 + d4 + 0.5, (double)l3, k2 + d5 + 0.5).tex(1.0 + d10, k3 * 0.25 + d9 + d11).color(1.0f, 1.0f, 1.0f, f7).lightmap(j5, k5).endVertex();
                        bufferbuilder.pos(l2 + d4 + 0.5, (double)k3, k2 + d5 + 0.5).tex(1.0 + d10, l3 * 0.25 + d9 + d11).color(1.0f, 1.0f, 1.0f, f7).lightmap(j5, k5).endVertex();
                        bufferbuilder.pos(l2 - d4 + 0.5, (double)k3, k2 - d5 + 0.5).tex(0.0 + d10, l3 * 0.25 + d9 + d11).color(1.0f, 1.0f, 1.0f, f7).lightmap(j5, k5).endVertex();
                    }
                }
            }
        }
        if (j2 >= 0) {
            tessellator.draw();
        }
        bufferbuilder.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1f);
        renderer.disableLightmap();
    }
    
    static {
        RANDOM = new Random();
        RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");
        SNOW_TEXTURES = new ResourceLocation("textures/environment/snow.png");
    }
}
