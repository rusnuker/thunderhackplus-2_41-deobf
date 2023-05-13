//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.math.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.util.math.*;

public class Speedometer extends HudElement
{
    public final Setting<ColorSetting> color;
    public double speedometerCurrentSpeed;
    private final Setting<Boolean> bps;
    
    public Speedometer() {
        super("Speedometer", "Speedometer", 50, 10);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.speedometerCurrentSpeed = 0.0;
        this.bps = (Setting<Boolean>)this.register(new Setting("BPS", (T)false));
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        String str = "";
        if (!this.bps.getValue()) {
            str = "Speed " + ChatFormatting.WHITE + this.round(this.getSpeedKpH() * Thunderhack.TICK_TIMER) + " km/h";
        }
        else {
            str = String.format("Speed " + ChatFormatting.WHITE + this.round(this.getSpeedMpS() * Thunderhack.TICK_TIMER) + " b/s", new Object[0]);
        }
        FontRender.drawString6(str, this.getPosX(), this.getPosY(), this.color.getValue().getRawColor(), true);
    }
    
    private float round(final double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
    
    @SubscribeEvent
    public void updateValues(final EventSync e) {
        final double distTraveledLastTickX = Speedometer.mc.player.posX - Speedometer.mc.player.prevPosX;
        final double distTraveledLastTickZ = Speedometer.mc.player.posZ - Speedometer.mc.player.prevPosZ;
        this.speedometerCurrentSpeed = distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ;
    }
    
    public double turnIntoKpH(final double input) {
        return MathHelper.sqrt(input) * 71.2729367892;
    }
    
    public double getSpeedKpH() {
        double speedometerkphdouble = this.turnIntoKpH(this.speedometerCurrentSpeed);
        speedometerkphdouble = Math.round(10.0 * speedometerkphdouble) / 10.0;
        return speedometerkphdouble;
    }
    
    public double getSpeedMpS() {
        double speedometerMpsdouble = this.turnIntoKpH(this.speedometerCurrentSpeed) / 3.6;
        speedometerMpsdouble = Math.round(10.0 * speedometerMpsdouble) / 10.0;
        return speedometerMpsdouble;
    }
}
