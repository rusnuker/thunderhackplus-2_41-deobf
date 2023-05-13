//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;

public class PacketStatistics extends HudElement
{
    public final Setting<ColorSetting> shadowColor;
    public final Setting<ColorSetting> color2;
    public final Setting<ColorSetting> textColor;
    public final Setting<ColorSetting> color3;
    int counter_in;
    int counter_out;
    int packets_in;
    int packets_out;
    
    public PacketStatistics() {
        super("PacketStatistics", "PacketStatistics", 100, 50);
        this.shadowColor = (Setting<ColorSetting>)this.register(new Setting("ShadowColor", (T)new ColorSetting(-15724528)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-15724528)));
        this.textColor = (Setting<ColorSetting>)this.register(new Setting("TextColor", (T)new ColorSetting(12500670)));
        this.color3 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-979657829)));
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        GlStateManager.pushMatrix();
        RenderUtil.drawBlurredShadow(this.getPosX(), this.getPosY(), 80.0f, 40.0f, 20, this.shadowColor.getValue().getColorObject());
        RoundedShader.drawRound(this.getPosX(), this.getPosY(), 80.0f, 40.0f, 7.0f, this.color2.getValue().getColorObject());
        RoundedShader.drawRound(this.getPosX() + 2.0f, this.getPosY() + 13.0f, 76.0f, 1.0f, 0.5f, this.color3.getValue().getColorObject());
        FontRender.drawCentString6("PacketStatistics", this.getPosX() + 40.0f, this.getPosY() + 5.0f, this.textColor.getValue().getColor());
        FontRender.drawString5("In: " + this.packets_in * 4 + " p/s", this.getPosX() + 3.0f, this.getPosY() + 20.0f, this.textColor.getValue().getColor());
        FontRender.drawString5("Out: " + this.packets_out * 4 + " p/s", this.getPosX() + 3.0f, this.getPosY() + 30.0f, this.textColor.getValue().getColor());
        GlStateManager.popMatrix();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        ++this.counter_in;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        ++this.counter_out;
    }
    
    @Override
    public void onUpdate() {
        if (PacketStatistics.mc.player.ticksExisted % 5 == 0) {
            this.packets_in = this.counter_in;
            this.packets_out = this.counter_out;
            this.counter_out = 0;
            this.counter_in = 0;
        }
    }
}
