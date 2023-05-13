//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.phobos.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.modules.misc.*;

public class CoolCrosshair extends Module
{
    public final Setting<ColorSetting> color;
    private final Setting<Boolean> smt;
    public Setting<Float> car;
    public Setting<Float> lwid;
    public Setting<Float> rounded2;
    int status;
    int santi;
    float animation;
    
    public CoolCrosshair() {
        super("CoolCrosshair", "CoolCrosshair", Category.HUD);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.smt = (Setting<Boolean>)this.register(new Setting("smooth", (T)Boolean.FALSE));
        this.car = (Setting<Float>)this.register(new Setting("otstup", (T)0.0f, (T)0.1f, (T)1.0f));
        this.lwid = (Setting<Float>)this.register(new Setting("otstup2", (T)0.0f, (T)0.1f, (T)1.0f));
        this.rounded2 = (Setting<Float>)this.register(new Setting("Round2", (T)0.0f, (T)0.5f, (T)20.0f));
        this.status = 0;
        this.santi = 0;
        this.animation = 0.0f;
    }
    
    public static void drawPartialCircle(final float x, final float y, final float radius, int startAngle, int endAngle, final float thickness, final Color colour, final boolean smooth) {
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        if (startAngle > endAngle) {
            final int temp = startAngle;
            startAngle = endAngle;
            endAngle = temp;
        }
        if (startAngle < 0) {
            startAngle = 0;
        }
        if (endAngle > 360) {
            endAngle = 360;
        }
        if (smooth) {
            GL11.glEnable(2848);
        }
        else {
            GL11.glDisable(2848);
        }
        GL11.glLineWidth(thickness);
        GL11.glColor4f(colour.getRed() / 255.0f, colour.getGreen() / 255.0f, colour.getBlue() / 255.0f, colour.getAlpha() / 255.0f);
        GL11.glBegin(3);
        final float ratio = 0.01745328f;
        for (int i = startAngle; i <= endAngle; ++i) {
            final float radians = (i - 90) * ratio;
            GL11.glVertex2f(x + (float)Math.cos(radians) * radius, y + (float)Math.sin(radians) * radius);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @SubscribeEvent
    public void onRenderAttackIndicator(final RenderAttackIndicatorEvent event) {
        event.setCanceled(true);
    }
    
    @Override
    public void onUpdate() {
        if (EZbowPOP.delayTimer.getPassedTimeMs() < Thunderhack.moduleManager.getModuleByClass(EZbowPOP.class).delay.getValue() * 1000.0f && this.animation < 20.0f) {
            ++this.animation;
        }
        if (this.santi < this.status) {
            this.santi += 60;
        }
        if (this.status < this.santi) {
            this.santi -= 360;
        }
        if (this.santi < 0) {
            this.santi = 0;
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent event) {
        super.onRender2D(event);
        final float x1 = (float)(event.scaledResolution.getScaledWidth_double() / 2.0);
        final float y1 = (float)(event.scaledResolution.getScaledHeight_double() / 2.0);
        final boolean blend = GL11.glIsEnabled(3042);
        GL11.glEnable(3042);
        if (EZbowPOP.delayTimer.getPassedTimeMs() > Thunderhack.moduleManager.getModuleByClass(EZbowPOP.class).delay.getValue() * 1000.0f || CoolCrosshair.mc.player.getHeldItemMainhand().getItem() != Items.BOW) {
            this.animation = 0.0f;
            this.status = ((this.getCooledAttackStrength() == 0.0f) ? 0 : ((int)(360.0f / (1.0f / this.getCooledAttackStrength()))));
            drawPartialCircle(x1, y1, this.rounded2.getValue(), 0, 360, this.lwid.getValue(), this.color.getValue().withAlpha((this.color.getValue().getAlpha() > 210) ? this.color.getValue().getAlpha() : (this.color.getValue().getAlpha() + 40)).getColorObject(), this.smt.getValue());
            drawPartialCircle(x1, y1, this.rounded2.getValue() - this.car.getValue(), 0, 360, this.lwid.getValue(), this.color.getValue().withAlpha((this.color.getValue().getAlpha() > 210) ? this.color.getValue().getAlpha() : (this.color.getValue().getAlpha() + 40)).getColorObject(), this.smt.getValue());
            drawPartialCircle(x1, y1, this.rounded2.getValue() + this.car.getValue(), 0, 360, this.lwid.getValue(), this.color.getValue().withAlpha((this.color.getValue().getAlpha() > 210) ? this.color.getValue().getAlpha() : (this.color.getValue().getAlpha() + 40)).getColorObject(), this.smt.getValue());
            drawPartialCircle(x1, y1, this.rounded2.getValue(), 0, this.status, this.lwid.getValue(), PaletteHelper.astolfo(false, 1), this.smt.getValue());
            drawPartialCircle(x1, y1, this.rounded2.getValue() - this.car.getValue(), 0, this.status, this.lwid.getValue(), PaletteHelper.astolfo(false, 1), this.smt.getValue());
            drawPartialCircle(x1, y1, this.rounded2.getValue() + this.car.getValue(), 0, this.status, this.lwid.getValue(), PaletteHelper.astolfo(false, 1), this.smt.getValue());
        }
        else {
            if (this.animation < 20.0f) {
                ++this.animation;
            }
            RoundedShader.drawRound(x1 - this.animation, y1 - 3.0f, this.animation * 2.0f, 6.0f, 4.0f, new Color(657930));
            RenderUtil.glScissor(x1 - this.animation, y1 - 3.0f, x1 + this.animation * 2.0f, x1 + 6.0f, event.scaledResolution);
            GL11.glEnable(3089);
            if (EZbowPOP.delayTimer.getPassedTimeMs() > Thunderhack.moduleManager.getModuleByClass(EZbowPOP.class).delay.getValue() * 666.0f) {
                FontRender.drawCentString5("charging.  ", x1, y1 - 0.5f, -1);
            }
            else if (EZbowPOP.delayTimer.getPassedTimeMs() > Thunderhack.moduleManager.getModuleByClass(EZbowPOP.class).delay.getValue() * 333.0f) {
                FontRender.drawCentString5("charging.. ", x1, y1 - 0.5f, -1);
            }
            else {
                FontRender.drawCentString5("charging...", x1, y1 - 0.5f, -1);
            }
            GL11.glDisable(3089);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
    }
    
    private float getCooledAttackStrength() {
        return MathHelper.clamp(((IEntityLivingBase)CoolCrosshair.mc.player).getTicksSinceLastSwing() / this.getCooldownPeriod(), 0.0f, 1.0f);
    }
    
    public float getCooldownPeriod() {
        return (float)(1.0 / CoolCrosshair.mc.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * (Thunderhack.moduleManager.getModuleByClass(Timer.class).isOn() ? (20.0f * Thunderhack.moduleManager.getModuleByClass(Timer.class).speed.getValue()) : 20.0));
    }
}
