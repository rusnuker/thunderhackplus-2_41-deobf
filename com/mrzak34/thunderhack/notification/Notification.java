//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.notification;

import com.mrzak34.thunderhack.util.shaders.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;

public class Notification
{
    private final String message;
    private final Timer timer;
    private final Type type;
    private final float height = 25.0f;
    private final long stayTime;
    public BetterAnimation animation;
    private float posY;
    private final float width;
    private float animationX;
    private boolean direction;
    private final Timer animationTimer;
    
    public Notification(final String message, final Type type, final int time) {
        this.animation = new BetterAnimation();
        this.direction = false;
        this.animationTimer = new Timer();
        this.stayTime = time;
        this.message = message;
        this.type = type;
        (this.timer = new Timer()).reset();
        final ScaledResolution sr = new ScaledResolution(Util.mc);
        this.width = (float)(FontRender.getStringWidth5(message) + 34);
        this.animationX = this.width;
        this.posY = sr.getScaledHeight() - 25.0f;
    }
    
    public void render(final float getY) {
        final Color scolor = new Color(-15263977);
        final Color icolor = new Color(scolor.getRed(), scolor.getGreen(), scolor.getBlue(), (int)MathUtil.clamp(255.0 * (1.0 - this.animation.getAnimationd()), 0.0, 255.0));
        final Color icolor2 = new Color(255, 255, 255, (int)MathUtil.clamp(1.0 - this.animation.getAnimationd(), 0.0, 255.0));
        final ScaledResolution resolution = new ScaledResolution(Util.mc);
        this.direction = this.isFinished();
        this.animationX = (float)(this.width * this.animation.getAnimationd());
        this.posY = this.animate(this.posY, getY);
        final int x1 = (int)(resolution.getScaledWidth() - 6 - this.width + this.animationX);
        final int y1 = (int)this.posY;
        RenderUtil.drawBlurredShadow((float)x1, (float)y1, this.width, 25.0f, 20, icolor);
        RoundedShader.drawRound((float)x1, (float)y1, this.width, 25.0f, 6.0f, icolor);
        FontRender.drawString5(this.type.getName(), (float)(x1 + 6), (float)(y1 + 4), -1);
        FontRender.drawString5(this.message, (float)(x1 + 6), (float)(int)(y1 + 4 + (25.0f - FontRender.getFontHeight5()) / 2.0f), icolor2.getRGB());
        if (this.animationTimer.passedMs(50L)) {
            this.animation.update(this.direction);
            this.animationTimer.reset();
        }
    }
    
    private boolean isFinished() {
        return this.timer.passedMs(this.stayTime);
    }
    
    public double getHeight() {
        return 25.0;
    }
    
    public boolean shouldDelete() {
        return this.isFinished() && this.animationX >= this.width - 5.0f;
    }
    
    public float animate(final float value, final float target) {
        return value + (target - value) / 8.0f;
    }
    
    public enum Type
    {
        SUCCESS("Success"), 
        INFO("Information"), 
        WARNING("Warning"), 
        ERROR("Error"), 
        ENABLED("Module enabled"), 
        DISABLED("Module disabled");
        
        final String name;
        
        private Type(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
