//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2.components;

import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.util.*;

public class BooleanComponent extends SettingElement
{
    float animation;
    
    public BooleanComponent(final Setting setting) {
        super(setting);
        this.animation = 0.0f;
    }
    
    public static double deltaTime() {
        return (Minecraft.getDebugFPS() > 0) ? (1.0 / Minecraft.getDebugFPS()) : 1.0;
    }
    
    public static float fast(final float end, final float start, final float multiple) {
        return (1.0f - MathUtil.clamp((float)(deltaTime() * multiple), 0.0f, 1.0f)) * end + MathUtil.clamp((float)(deltaTime() * multiple), 0.0f, 1.0f) * start;
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        FontRender.drawString6(this.getSetting().getName(), (float)this.getX(), (float)this.getY() + 5.0f, this.isHovered() ? -1 : new Color(-1325400065, true).getRGB(), false);
        this.animation = fast(this.animation, ((boolean)this.setting.getValue()) ? 1.0f : 0.0f, 15.0f);
        final double paddingX = 7.0f * this.animation;
        final Color color = ClickGui.getInstance().getColor(0);
        RoundedShader.drawRound((float)(this.x + this.width - 18.0), (float)(this.y + this.height / 2.0 - 4.0), 15.0f, 8.0f, 4.0f, (paddingX > 4.0) ? color : new Color(-5066319));
        RoundedShader.drawRound((float)(this.x + this.width - 17.0 + paddingX), (float)(this.y + this.height / 2.0 - 3.0), 6.0f, 6.0f, 3.0f, true, new Color(-1));
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.getY() > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.getY() < ThunderGui2.getInstance().main_posY) {
            return;
        }
        if (this.isHovered()) {
            this.setting.setValue(!this.setting.getValue());
        }
    }
}
