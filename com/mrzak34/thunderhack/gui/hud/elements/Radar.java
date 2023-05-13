//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.concurrent.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Radar extends HudElement
{
    private final Setting<Integer> size;
    public final Setting<ColorSetting> shadowColor;
    public final Setting<ColorSetting> color2;
    public final Setting<ColorSetting> color3;
    public Setting<Boolean> jew;
    public Setting<Boolean> ljew;
    private CopyOnWriteArrayList<EntityPlayer> players;
    
    public Radar() {
        super("Radar", "\u043a\u043b\u0430\u0441\u0441\u0438\u0447\u0435\u0441\u043a\u0438\u0439 2\u0434-\u0440\u0430\u0434\u0430\u0440", "classic 2d-radar", 100, 100);
        this.size = (Setting<Integer>)this.register(new Setting("Size", (T)80, (T)20, (T)300));
        this.shadowColor = (Setting<ColorSetting>)this.register(new Setting("ShadowColor", (T)new ColorSetting(-15724528)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-15724528)));
        this.color3 = (Setting<ColorSetting>)this.register(new Setting("PlayerColor", (T)new ColorSetting(-979657829)));
        this.jew = (Setting<Boolean>)this.register(new Setting("Jew", (T)false));
        this.ljew = (Setting<Boolean>)this.register(new Setting("LaaaargeJew", (T)false));
        this.players = new CopyOnWriteArrayList<EntityPlayer>();
    }
    
    @Override
    public void onUpdate() {
        this.players.clear();
        this.players.addAll(Radar.mc.world.playerEntities);
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        final double psx = this.getPosX();
        final double psy = this.getPosY();
        final int sizeRect = this.size.getValue();
        final float xOffset = (float)psx;
        final float yOffset = (float)psy;
        final double playerPosX = Radar.mc.player.posX;
        final double playerPosZ = Radar.mc.player.posZ;
        RenderUtil.drawBlurredShadow(xOffset, this.getPosY(), (float)sizeRect, (float)sizeRect, 20, this.shadowColor.getValue().getColorObject());
        RoundedShader.drawRound(xOffset, this.getPosY(), (float)sizeRect, (float)sizeRect, 7.0f, this.color2.getValue().getColorObject());
        RenderUtil.drawRect(xOffset + (sizeRect / 2.0f - 0.5), yOffset + 3.5, xOffset + (sizeRect / 2.0f + 0.2), yOffset + sizeRect - 3.5, PaletteHelper.getColor(155, 100));
        RenderUtil.drawRect(xOffset + 3.5, yOffset + (sizeRect / 2.0f - 0.2), xOffset + sizeRect - 3.5, yOffset + (sizeRect / 2.0f + 0.5), PaletteHelper.getColor(155, 100));
        for (final EntityPlayer entityPlayer : this.players) {
            if (entityPlayer == Radar.mc.player) {
                continue;
            }
            final float partialTicks = Radar.mc.getRenderPartialTicks();
            final float posX = (float)(entityPlayer.posX + (entityPlayer.posX - entityPlayer.lastTickPosX) * partialTicks - playerPosX) * 2.0f;
            final float posZ = (float)(entityPlayer.posZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * partialTicks - playerPosZ) * 2.0f;
            final float cos = (float)Math.cos(Radar.mc.player.rotationYaw * 0.017453292);
            final float sin = (float)Math.sin(Radar.mc.player.rotationYaw * 0.017453292);
            float rotY = -(posZ * cos - posX * sin);
            float rotX = -(posX * cos + posZ * sin);
            if (rotY > sizeRect / 2.0f - 6.0f) {
                rotY = sizeRect / 2.0f - 6.0f;
            }
            else if (rotY < -(sizeRect / 2.0f - 8.0f)) {
                rotY = -(sizeRect / 2.0f - 8.0f);
            }
            if (rotX > sizeRect / 2.0f - 5.0f) {
                rotX = sizeRect / 2.0f - 5.0f;
            }
            else if (rotX < -(sizeRect / 2.0f - 5.0f)) {
                rotX = -(sizeRect / 2.0f - 5.0f);
            }
            if (this.jew.getValue()) {
                if (!this.ljew.getValue()) {
                    FontRender.drawIconF("y", xOffset + sizeRect / 2.0f + rotX - 2.0f, yOffset + sizeRect / 2.0f + rotY - 2.0f, this.color3.getValue().getColor());
                }
                else {
                    FontRender.drawMidIcon("y", xOffset + sizeRect / 2.0f + rotX - 4.0f, yOffset + sizeRect / 2.0f + rotY - 4.0f, this.color3.getValue().getColor());
                }
            }
            else {
                RoundedShader.drawRound(xOffset + sizeRect / 2.0f + rotX - 2.0f, yOffset + sizeRect / 2.0f + rotY - 2.0f, 4.0f, 4.0f, 4.0f, this.color3.getValue().getColorObject());
            }
        }
    }
}
