//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Coords extends HudElement
{
    public final Setting<ColorSetting> color;
    
    public Coords() {
        super("Coords", "coords", 100, 10);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        final boolean inHell = Coords.mc.world.getBiome(Coords.mc.player.getPosition()).getBiomeName().equals("Hell");
        final int posX = (int)Coords.mc.player.posX;
        final int posY = (int)Coords.mc.player.posY;
        final int posZ = (int)Coords.mc.player.posZ;
        final float nether = inHell ? 8.0f : 0.125f;
        final int hposX = (int)(Coords.mc.player.posX * nether);
        final int hposZ = (int)(Coords.mc.player.posZ * nether);
        final String coordinates = ChatFormatting.WHITE + "XYZ " + ChatFormatting.RESET + (inHell ? (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]" + ChatFormatting.RESET) : (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]"));
        FontRender.drawString6(coordinates, this.getPosX(), this.getPosY(), this.color.getValue().getRawColor(), false);
    }
}
