//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class KeyBinds extends HudElement
{
    public final Setting<ColorSetting> shadowColor;
    public final Setting<ColorSetting> color2;
    public final Setting<ColorSetting> color3;
    public final Setting<ColorSetting> textColor;
    public final Setting<ColorSetting> oncolor;
    public final Setting<ColorSetting> offcolor;
    
    public KeyBinds() {
        super("KeyBinds", "KeyBinds", 100, 100);
        this.shadowColor = (Setting<ColorSetting>)this.register(new Setting("ShadowColor", (T)new ColorSetting(-15724528)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-15724528)));
        this.color3 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-979657829)));
        this.textColor = (Setting<ColorSetting>)this.register(new Setting("TextColor", (T)new ColorSetting(12500670)));
        this.oncolor = (Setting<ColorSetting>)this.register(new Setting("OnColor", (T)new ColorSetting(12500670)));
        this.offcolor = (Setting<ColorSetting>)this.register(new Setting("OffColor", (T)new ColorSetting(6579300)));
    }
    
    public static void size(final double width, final double height, final double animation) {
        GL11.glTranslated(width, height, 0.0);
        GL11.glScaled(animation, animation, 1.0);
        GL11.glTranslated(-width, -height, 0.0);
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        int y_offset1 = 0;
        for (final Module feature : Thunderhack.moduleManager.modules) {
            if (!Objects.equals(feature.getBind().toString(), "None") && !feature.getName().equalsIgnoreCase("clickgui") && !feature.getName().equalsIgnoreCase("thundergui")) {
                y_offset1 += 10;
            }
        }
        GlStateManager.pushMatrix();
        RenderUtil.drawBlurredShadow(this.getPosX(), this.getPosY(), 100.0f, (float)(20 + y_offset1), 20, this.shadowColor.getValue().getColorObject());
        RoundedShader.drawRound(this.getPosX(), this.getPosY(), 100.0f, (float)(20 + y_offset1), 7.0f, this.color2.getValue().getColorObject());
        FontRender.drawCentString6("KeyBinds", this.getPosX() + 50.0f, this.getPosY() + 5.0f, this.textColor.getValue().getColor());
        RoundedShader.drawRound(this.getPosX() + 2.0f, this.getPosY() + 13.0f, 96.0f, 1.0f, 0.5f, this.color3.getValue().getColorObject());
        int y_offset2 = 0;
        for (final Module feature2 : Thunderhack.moduleManager.modules) {
            if (!Objects.equals(feature2.getBind().toString(), "None") && !feature2.getName().equalsIgnoreCase("clickgui") && !feature2.getName().equalsIgnoreCase("thundergui")) {
                GlStateManager.pushMatrix();
                GlStateManager.resetColor();
                FontRender.drawString6("[" + feature2.getBind().toString() + "]  " + feature2.getName(), this.getPosX() + 5.0f, this.getPosY() + 18.0f + y_offset2, feature2.isOn() ? this.oncolor.getValue().getColor() : this.offcolor.getValue().getColor(), false);
                GlStateManager.resetColor();
                GlStateManager.popMatrix();
                y_offset2 += 10;
            }
        }
        GlStateManager.popMatrix();
    }
}
