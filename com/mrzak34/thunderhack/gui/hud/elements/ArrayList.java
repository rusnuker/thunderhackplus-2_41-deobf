//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.modules.*;
import java.awt.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ArrayList extends HudElement
{
    private final Setting<Mode> mode;
    private final Setting<ColorSetting> color;
    private final Setting<Float> rainbowSpeed;
    private final Setting<Float> saturation;
    private final Setting<Integer> gste;
    private final Setting<Boolean> glow;
    private final Setting<cMode> cmode;
    private final Setting<Boolean> hrender;
    private final Setting<Boolean> hhud;
    private final Setting<ColorSetting> color2;
    private final Setting<ColorSetting> color3;
    private final Setting<ColorSetting> color4;
    boolean reverse;
    
    public ArrayList() {
        super("ArrayList", "arraylist", 50, 30);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.ColorText));
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.rainbowSpeed = (Setting<Float>)this.register(new Setting("Speed", (T)10.0f, (T)1.0f, (T)20.0f));
        this.saturation = (Setting<Float>)this.register(new Setting("Saturation", (T)0.5f, (T)0.1f, (T)1.0f));
        this.gste = (Setting<Integer>)this.register(new Setting("GS", (T)30, (T)1, (T)50));
        this.glow = (Setting<Boolean>)this.register(new Setting("glow", (T)false));
        this.cmode = (Setting<cMode>)this.register(new Setting("ColorMode", (T)cMode.Rainbow));
        this.hrender = (Setting<Boolean>)this.register(new Setting("HideHud", (T)true));
        this.hhud = (Setting<Boolean>)this.register(new Setting("HideRender", (T)true));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(237176633)));
        this.color3 = (Setting<ColorSetting>)this.register(new Setting("RectColor", (T)new ColorSetting(-16777216)));
        this.color4 = (Setting<ColorSetting>)this.register(new Setting("SideRectColor", (T)new ColorSetting(-16777216)));
    }
    
    public static void drawRect(double left, double top, double right, double bottom, final int color) {
        if (left < right) {
            final double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f4, f5, f6, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, top, 0.0).endVertex();
        bufferbuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        this.reverse = (this.getPosX() > (float)(e.getScreenWidth() / 2.0));
        int offset = 0;
        int offset2 = 0;
        int yTotal = 0;
        for (int i = 0; i < Thunderhack.moduleManager.sortedModules.size(); ++i) {
            yTotal += FontRender.getFontHeight6() + 3;
        }
        this.setHeight(yTotal);
        if (this.mode.getValue() == Mode.ColorText) {
            for (int k = 0; k < Thunderhack.moduleManager.sortedModules.size(); ++k) {
                final Module module = Thunderhack.moduleManager.sortedModules.get(k);
                if (module.isDrawn()) {
                    if (!this.hrender.getValue() || module.getCategory() != Category.RENDER) {
                        if (!this.hhud.getValue() || module.getCategory() != Category.HUD) {
                            Color color1 = null;
                            if (this.cmode.getValue() == cMode.Rainbow) {
                                color1 = PaletteHelper.astolfo((float)offset2, (float)yTotal, this.saturation.getValue(), this.rainbowSpeed.getValue());
                            }
                            else if (this.cmode.getValue() == cMode.DoubleColor) {
                                color1 = RenderUtil.TwoColoreffect(this.color.getValue().getColorObject(), this.color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + offset2 * ((20.0f - this.rainbowSpeed.getValue()) / 200.0f));
                            }
                            else {
                                color1 = new Color(this.color.getValue().getColor()).darker();
                            }
                            if (!this.reverse) {
                                final int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
                                if (this.glow.getValue()) {
                                    RenderHelper.drawBlurredShadow(this.getPosX() - 3.0f, this.getPosY() + offset2 - 1.0f, stringWidth + 4.0f, 9.0f, this.gste.getValue(), color1);
                                }
                            }
                            if (this.reverse) {
                                final int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
                                if (this.glow.getValue()) {
                                    RenderHelper.drawBlurredShadow(this.getPosX() - stringWidth - 3.0f, this.getPosY() + offset2 - 1.0f, (float)(stringWidth + 4), 9.0f, this.gste.getValue(), color1);
                                }
                            }
                            offset2 += 8;
                        }
                    }
                }
            }
        }
        for (int k = 0; k < Thunderhack.moduleManager.sortedModules.size(); ++k) {
            final Module module = Thunderhack.moduleManager.sortedModules.get(k);
            if (module.isDrawn()) {
                if (!this.hrender.getValue() || module.getCategory() != Category.RENDER) {
                    if (!this.hhud.getValue() || module.getCategory() != Category.HUD) {
                        Color color1 = null;
                        if (this.cmode.getValue() == cMode.Rainbow) {
                            color1 = PaletteHelper.astolfo((float)offset, (float)yTotal, this.saturation.getValue(), this.rainbowSpeed.getValue());
                        }
                        else if (this.cmode.getValue() == cMode.DoubleColor) {
                            color1 = RenderUtil.TwoColoreffect(this.color.getValue().getColorObject(), this.color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + offset * ((20.0f - this.rainbowSpeed.getValue()) / 200.0f));
                        }
                        else {
                            color1 = new Color(this.color.getValue().getColor()).darker();
                        }
                        if (this.mode.getValue() == Mode.ColorRect) {
                            if (!this.reverse) {
                                final int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
                                if (this.glow.getValue()) {
                                    RenderHelper.drawBlurredShadow(this.getPosX() - 3.0f, this.getPosY() + offset - 1.0f, stringWidth + 4.0f, 9.0f, this.gste.getValue(), color1);
                                }
                                drawRect(this.getPosX(), this.getPosY() + offset, this.getPosX() + stringWidth + 1.0f, this.getPosY() + offset + 8.0f, color1.getRGB());
                                drawRect(this.getPosX() - 2.0f, this.getPosY() + offset, this.getPosX() + 1.0f, this.getPosY() + offset + 8.0f, this.color4.getValue().getColor());
                                FontRender.drawString6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : ""), this.getPosX() + 3.0f, this.getPosY() + 2.0f + offset, -1, false);
                            }
                            if (this.reverse) {
                                final int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
                                if (this.glow.getValue()) {
                                    RenderHelper.drawBlurredShadow(this.getPosX() - stringWidth - 3.0f, this.getPosY() + offset - 1.0f, (float)(stringWidth + 4), 9.0f, this.gste.getValue(), color1);
                                }
                                drawRect(this.getPosX() - stringWidth, this.getPosY() + offset, this.getPosX() + 1.0f, this.getPosY() + offset + 8.0f, color1.getRGB());
                                drawRect(this.getPosX() + 1.0f, this.getPosY() + offset, this.getPosX() + 4.0f, this.getPosY() + offset + 8.0f, this.color4.getValue().getColor());
                                FontRender.drawString6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : ""), this.getPosX() - stringWidth + 2.0f, this.getPosY() + 2.0f + offset, -1, false);
                            }
                        }
                        else {
                            if (!this.reverse) {
                                final int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
                                drawRect(this.getPosX(), this.getPosY() + offset, this.getPosX() + stringWidth + 1.0f, this.getPosY() + offset + 8.0f, this.color3.getValue().getColor());
                                drawRect(this.getPosX() - 2.0f, this.getPosY() + offset, this.getPosX() + 1.0f, this.getPosY() + offset + 8.0f, color1.getRGB());
                                FontRender.drawString6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : ""), this.getPosX() + 3.0f, this.getPosY() + 2.0f + offset, color1.getRGB(), false);
                            }
                            if (this.reverse) {
                                final int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
                                drawRect(this.getPosX() - stringWidth, this.getPosY() + offset, this.getPosX() + 1.0f, this.getPosY() + offset + 8.0f, this.color3.getValue().getColor());
                                drawRect(this.getPosX() + 1.0f, this.getPosY() + offset, this.getPosX() + 4.0f, this.getPosY() + offset + 8.0f, color1.getRGB());
                                FontRender.drawString6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : ""), this.getPosX() - stringWidth + 2.0f, this.getPosY() + 2.0f + offset, color1.getRGB(), false);
                            }
                        }
                        offset += 8;
                    }
                }
            }
        }
    }
    
    private enum cMode
    {
        Rainbow, 
        Custom, 
        DoubleColor;
    }
    
    private enum Mode
    {
        ColorText, 
        ColorRect;
    }
}
