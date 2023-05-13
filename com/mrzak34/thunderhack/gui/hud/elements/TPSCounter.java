//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TPSCounter extends HudElement
{
    public final Setting<ColorSetting> color;
    
    public TPSCounter() {
        super("TPS", "trps", 50, 10);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        final String str = "TPS " + ChatFormatting.WHITE + Thunderhack.serverManager.getTPS();
        FontRender.drawString6(str, this.getPosX(), this.getPosY(), this.color.getValue().getRawColor(), false);
    }
}
