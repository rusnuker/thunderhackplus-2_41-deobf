//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class WaterMark extends HudElement
{
    public final Setting<ColorSetting> color1;
    public final Setting<ColorSetting> color2;
    public final Setting<ColorSetting> shadowColor;
    public Timer timer;
    int i;
    
    public WaterMark() {
        super("WaterMark", "WaterMark", 200, 20);
        this.color1 = (Setting<ColorSetting>)this.register(new Setting("TextColor", (T)new ColorSetting(-1)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-15724528)));
        this.shadowColor = (Setting<ColorSetting>)this.register(new Setting("ShadowColor", (T)new ColorSetting(-15724528)));
        this.timer = new Timer();
        this.i = 0;
    }
    
    public static void setColor(final int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        RenderUtil.drawBlurredShadow(4.0f, 4.0f, (float)(FontRender.getStringWidth6("ThunderHack  |  " + WaterMark.mc.player.getName() + "  |  " + Thunderhack.serverManager.getPing() + " ms  |  " + ((WaterMark.mc.getCurrentServerData() == null) ? "SinglePlayer" : WaterMark.mc.getCurrentServerData().serverIP)) + 29), 12.0f, 10, this.shadowColor.getValue().getColorObject());
        RoundedShader.drawRound(4.0f, 4.0f, (float)(FontRender.getStringWidth6("ThunderHack  |  " + WaterMark.mc.player.getName() + "  |  " + Thunderhack.serverManager.getPing() + " ms  |  " + ((WaterMark.mc.getCurrentServerData() == null) ? "SinglePlayer" : WaterMark.mc.getCurrentServerData().serverIP)) + 30), 13.0f, 2.0f, this.color2.getValue().getColorObject());
        if (this.timer.passedMs(350L)) {
            ++this.i;
            this.timer.reset();
        }
        if (this.i == 24) {
            this.i = 0;
        }
        final String w1 = "_";
        final String w2 = "T_";
        final String w3 = "Th_";
        final String w4 = "Thu_";
        final String w5 = "Thun_";
        final String w6 = "Thund_";
        final String w7 = "Thunde_";
        final String w8 = "Thunder_";
        final String w9 = "ThunderH_";
        final String w10 = "ThunderHa_";
        final String w11 = "ThunderHac_";
        final String w12 = "ThunderHack";
        final String w13 = "ThunderHack";
        final String w14 = "ThunderHack";
        final String w15 = "ThunderHac_";
        final String w16 = "ThunderHa_";
        final String w17 = "ThunderH_";
        final String w18 = "Thunder_";
        final String w19 = "Thunde_";
        final String w20 = "Thund_";
        final String w21 = "Thun_";
        final String w22 = "Thu_";
        final String w23 = "Th_";
        final String w24 = "T_";
        final String w25 = "_";
        String text = "";
        if (this.i == 0) {
            text = w1;
        }
        if (this.i == 1) {
            text = w2;
        }
        if (this.i == 2) {
            text = w3;
        }
        if (this.i == 3) {
            text = w4;
        }
        if (this.i == 4) {
            text = w5;
        }
        if (this.i == 5) {
            text = w6;
        }
        if (this.i == 6) {
            text = w7;
        }
        if (this.i == 7) {
            text = w8;
        }
        if (this.i == 8) {
            text = w9;
        }
        if (this.i == 9) {
            text = w10;
        }
        if (this.i == 10) {
            text = w11;
        }
        if (this.i == 11) {
            text = w12;
        }
        if (this.i == 12) {
            text = w13;
        }
        if (this.i == 13) {
            text = w14;
        }
        if (this.i == 14) {
            text = w15;
        }
        if (this.i == 15) {
            text = w16;
        }
        if (this.i == 16) {
            text = w17;
        }
        if (this.i == 17) {
            text = w18;
        }
        if (this.i == 18) {
            text = w19;
        }
        if (this.i == 19) {
            text = w20;
        }
        if (this.i == 20) {
            text = w21;
        }
        if (this.i == 21) {
            text = w22;
        }
        if (this.i == 22) {
            text = w23;
        }
        if (this.i == 23) {
            text = w24;
        }
        if (this.i == 23) {
            text = w25;
        }
        FontRender.drawString6(text, 7.0f, 9.0f, -1, false);
        FontRender.drawString6("  |  " + WaterMark.mc.player.getName() + "  |  " + Thunderhack.serverManager.getPing() + " ms  |  " + ((WaterMark.mc.getCurrentServerData() == null) ? "SinglePlayer" : WaterMark.mc.getCurrentServerData().serverIP), (float)(FontRender.getStringWidth6("ThunderHack") + 10), 9.0f, this.color1.getValue().getColor(), false);
    }
}
