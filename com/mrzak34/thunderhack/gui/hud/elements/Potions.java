//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.client.gui.inventory.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class Potions extends HudElement
{
    int zLevel;
    private final Setting<Modes> mode;
    public final Setting<ColorSetting> color;
    public Setting<Float> grange;
    public Setting<Float> gmult;
    public final Setting<ColorSetting> shadowColor;
    public final Setting<ColorSetting> color2;
    public final Setting<ColorSetting> color3;
    public final Setting<ColorSetting> textColor;
    public final Setting<ColorSetting> oncolor;
    
    public Potions() {
        super("Potions", "Potions", 100, 100);
        this.zLevel = 0;
        this.mode = (Setting<Modes>)this.register(new Setting("Mode", (T)Modes.New));
        this.color = (Setting<ColorSetting>)this.register(new Setting("WexColor", (T)new ColorSetting(-2013200640), v -> this.mode.getValue() != Modes.New));
        this.grange = (Setting<Float>)this.register(new Setting("GlowRange", (T)3.6f, (T)0.0f, (T)10.0f, v -> this.mode.getValue() == Modes.Wexside));
        this.gmult = (Setting<Float>)this.register(new Setting("GlowMultiplier", (T)3.6f, (T)0.0f, (T)10.0f, v -> this.mode.getValue() == Modes.Wexside));
        this.shadowColor = (Setting<ColorSetting>)this.register(new Setting("ShadowColor", (T)new ColorSetting(-15724528), v -> this.mode.getValue() == Modes.New));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-15724528), v -> this.mode.getValue() == Modes.New));
        this.color3 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-979657829), v -> this.mode.getValue() == Modes.New));
        this.textColor = (Setting<ColorSetting>)this.register(new Setting("TextColor", (T)new ColorSetting(12500670), v -> this.mode.getValue() == Modes.New));
        this.oncolor = (Setting<ColorSetting>)this.register(new Setting("TextColor", (T)new ColorSetting(12500670), v -> this.mode.getValue() == Modes.New));
    }
    
    public static String getDuration(final PotionEffect potionEffect) {
        if (potionEffect.getIsPotionDurationMax()) {
            return "**:**";
        }
        return StringUtils.ticksToElapsedTime(potionEffect.getDuration());
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        if (this.mode.getValue() == Modes.New) {
            this.drawNew();
        }
        else {
            this.drawWexside(e);
        }
    }
    
    private void drawNew() {
        int y_offset1 = 0;
        final ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
        for (final PotionEffect potionEffect : Potions.mc.player.getActivePotionEffects()) {
            if (potionEffect.getDuration() != 0 && !potionEffect.getPotion().getName().contains("effect.nightVision")) {
                effects.add(potionEffect);
                y_offset1 += 10;
            }
        }
        GlStateManager.pushMatrix();
        RenderUtil.drawBlurredShadow(this.getPosX(), this.getPosY(), 100.0f, (float)(20 + y_offset1), 20, this.shadowColor.getValue().getColorObject());
        RoundedShader.drawRound(this.getPosX(), this.getPosY(), 100.0f, (float)(20 + y_offset1), 7.0f, this.color2.getValue().getColorObject());
        FontRender.drawCentString6("Potions", this.getPosX() + 50.0f, this.getPosY() + 5.0f, this.textColor.getValue().getColor());
        RoundedShader.drawRound(this.getPosX() + 2.0f, this.getPosY() + 13.0f, 96.0f, 1.0f, 0.5f, this.color3.getValue().getColorObject());
        int y_offset2 = 0;
        for (final PotionEffect potionEffect2 : effects) {
            final Potion potion = potionEffect2.getPotion();
            String power = "";
            if (potionEffect2.getAmplifier() == 0) {
                power = "I";
            }
            else if (potionEffect2.getAmplifier() == 1) {
                power = "II";
            }
            else if (potionEffect2.getAmplifier() == 2) {
                power = "III";
            }
            else if (potionEffect2.getAmplifier() == 3) {
                power = "IV";
            }
            else if (potionEffect2.getAmplifier() == 4) {
                power = "V";
            }
            final String s = potionEffect2.getPotion().getName().replace("effect.", "") + " " + power;
            final String s2 = getDuration(potionEffect2) + "";
            GlStateManager.pushMatrix();
            GlStateManager.resetColor();
            FontRender.drawString6(s + "  " + s2, this.getPosX() + 5.0f, this.getPosY() + 20.0f + y_offset2, this.oncolor.getValue().getColor(), false);
            GlStateManager.resetColor();
            GlStateManager.popMatrix();
            y_offset2 += 10;
        }
        GlStateManager.popMatrix();
    }
    
    private void drawWexside(final Render2DEvent e) {
        final int i = 0;
        final ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
        for (final PotionEffect potionEffect : Potions.mc.player.getActivePotionEffects()) {
            if (potionEffect.getDuration() != 0 && !potionEffect.getPotion().getName().contains("effect.nightVision")) {
                effects.add(potionEffect);
            }
        }
        int j = e.scaledResolution.getScaledHeight() / 2 - effects.size() * 24 / 2;
        for (final PotionEffect potionEffect2 : effects) {
            final Potion potion = potionEffect2.getPotion();
            String power = "";
            if (potionEffect2.getAmplifier() == 0) {
                power = "I";
            }
            else if (potionEffect2.getAmplifier() == 1) {
                power = "II";
            }
            else if (potionEffect2.getAmplifier() == 2) {
                power = "III";
            }
            else if (potionEffect2.getAmplifier() == 3) {
                power = "IV";
            }
            else if (potionEffect2.getAmplifier() == 4) {
                power = "V";
            }
            final String s = I18n.format(potionEffect2.getPotion().getName(), new Object[0]) + " " + power;
            final String s2 = getDuration(potionEffect2) + "";
            final float maxWidth = (float)(Math.max(FontRender.getStringWidth6(s), FontRender.getStringWidth6(s2)) + 32);
            DrawHelper.drawRectWithGlow(i + 2, j + 5, maxWidth - 4.0f + i + 2.0f, 18.5f + j + 5.0f, this.grange.getValue(), this.gmult.getValue(), this.color.getValue().getColorObject());
            Potions.mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
            if (potion.hasStatusIcon()) {
                final int i2 = potion.getStatusIconIndex();
                this.drawTexturedModalRect(i + 5, j + 7, i2 % 8 * 18, 198 + i2 / 8 * 18, 18, 18);
            }
            FontRender.drawString6(s, (float)(i + 28), j + 11.5f, new Color(205, 205, 205, 205).getRGB(), false);
            FontRender.drawString6(s2, (float)(i + 28), j + 18.5f, new Color(205, 205, 205, 205).getRGB(), false);
            j += 24;
        }
    }
    
    public void drawTexturedModalRect(final int x, final int y, final int textureX, final int textureY, final int width, final int height) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), (double)this.zLevel).tex((double)(textureX * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((textureX + width) * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, (double)this.zLevel).tex((double)((textureX + width) * 0.00390625f), (double)(textureY * 0.00390625f)).endVertex();
        bufferbuilder.pos((double)x, (double)y, (double)this.zLevel).tex((double)(textureX * 0.00390625f), (double)(textureY * 0.00390625f)).endVertex();
        tessellator.draw();
    }
    
    public enum Modes
    {
        Wexside, 
        New;
    }
}
