//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.math.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class LowHPScreen extends Module
{
    public final Setting<ColorSetting> color;
    int dynamic_alpha;
    int nuyahz;
    
    public LowHPScreen() {
        super("LowHPScreen", "LowHPScreen", Module.Category.RENDER);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(575714484)));
        this.dynamic_alpha = 0;
        this.nuyahz = 0;
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent e) {
        final Color color2 = new Color(this.color.getValue().getRed(), this.color.getValue().getGreen(), this.color.getValue().getBlue(), MathUtil.clamp(this.dynamic_alpha + 40, 0, 255));
        if (LowHPScreen.mc.player.getHealth() < 10.0f) {
            final ScaledResolution sr = new ScaledResolution(LowHPScreen.mc);
            RenderUtil.draw2DGradientRect(0.0f, 0.0f, (float)sr.getScaledWidth(), (float)sr.getScaledWidth(), color2.getRGB(), new Color(0, 0, 0, 0).getRGB(), color2.getRGB(), new Color(0, 0, 0, 0).getRGB());
            if (LowHPScreen.mc.player.getHealth() > 9.0f) {
                this.nuyahz = 18;
            }
            else if (LowHPScreen.mc.player.getHealth() > 8.0f) {
                this.nuyahz = 36;
            }
            else if (LowHPScreen.mc.player.getHealth() > 7.0f) {
                this.nuyahz = 54;
            }
            else if (LowHPScreen.mc.player.getHealth() > 6.0f) {
                this.nuyahz = 72;
            }
            else if (LowHPScreen.mc.player.getHealth() > 5.0f) {
                this.nuyahz = 90;
            }
            else if (LowHPScreen.mc.player.getHealth() > 4.0f) {
                this.nuyahz = 108;
            }
            else if (LowHPScreen.mc.player.getHealth() > 3.0f) {
                this.nuyahz = 126;
            }
            else if (LowHPScreen.mc.player.getHealth() > 2.0f) {
                this.nuyahz = 144;
            }
            else if (LowHPScreen.mc.player.getHealth() > 1.0f) {
                this.nuyahz = 162;
            }
            else if (LowHPScreen.mc.player.getHealth() > 0.0f) {
                this.nuyahz = 180;
            }
        }
        if (this.nuyahz > this.dynamic_alpha) {
            this.dynamic_alpha += 3;
        }
        if (this.nuyahz < this.dynamic_alpha) {
            this.dynamic_alpha -= 3;
        }
    }
}
