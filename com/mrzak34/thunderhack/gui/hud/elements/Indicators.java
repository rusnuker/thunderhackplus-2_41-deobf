//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.movement.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import java.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.util.math.*;

public class Indicators extends HudElement
{
    public static AstolfoAnimation astolfo;
    private static final List<Indicator> indicators;
    private final Setting<ColorSetting> cc;
    private final Setting<ColorSetting> cs;
    public Setting<Boolean> dmgflyy;
    public Setting<Boolean> Memoryy;
    public Setting<Boolean> Timerr;
    public Setting<Boolean> TPS;
    public Setting<Boolean> dmgspeed;
    public Setting<Boolean> blur;
    public Setting<Float> grange;
    public Setting<Float> gmult;
    public Setting<Float> range;
    boolean once;
    private final Setting<mode2> colorType;
    
    public Indicators() {
        super("WexIndicators", "\u0418\u043d\u0434\u0438\u043a\u0430\u0442\u043e\u0440\u044b \u043a\u0430\u043a \u0432 \u0432\u0435\u043a\u0441\u0430\u0439\u0434\u0435-(\u0438\u0437 \u0432\u0435\u043a\u0441\u0430\u0439\u0434\u0430)", 150, 50);
        this.cc = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.cs = (Setting<ColorSetting>)this.register(new Setting("RectColor", (T)new ColorSetting(-2013200640)));
        this.dmgflyy = (Setting<Boolean>)this.register(new Setting("DMGFly", (T)true));
        this.Memoryy = (Setting<Boolean>)this.register(new Setting("Memory", (T)true));
        this.Timerr = (Setting<Boolean>)this.register(new Setting("Timer", (T)true));
        this.TPS = (Setting<Boolean>)this.register(new Setting("TPS", (T)true));
        this.dmgspeed = (Setting<Boolean>)this.register(new Setting("DMGSpeed", (T)true));
        this.blur = (Setting<Boolean>)this.register(new Setting("Blur", (T)true));
        this.grange = (Setting<Float>)this.register(new Setting("GlowRange", (T)3.6f, (T)0.0f, (T)10.0f));
        this.gmult = (Setting<Float>)this.register(new Setting("GlowMultiplier", (T)3.6f, (T)0.0f, (T)10.0f));
        this.range = (Setting<Float>)this.register(new Setting("RangeBetween", (T)46.0f, (T)46.0f, (T)100.0f));
        this.once = false;
        this.colorType = (Setting<mode2>)this.register(new Setting("Mode", (T)mode2.Astolfo));
    }
    
    public static float[] getRG(final double input) {
        return new float[] { 255.0f - 255.0f * (float)input, 255.0f * (float)input, 100.0f * (float)input };
    }
    
    protected void once() {
        Indicators.indicators.add(new Indicator() {
            @Override
            boolean enabled() {
                return Indicators.this.Timerr.getValue();
            }
            
            @Override
            String getName() {
                return "Timer";
            }
            
            @Override
            double getProgress() {
                return (10.0 - Timer.value) / (Math.abs(Thunderhack.moduleManager.getModuleByClass(Timer.class).getMin()) + 10);
            }
        });
        Indicators.indicators.add(new Indicator() {
            @Override
            boolean enabled() {
                return Indicators.this.Memoryy.getValue();
            }
            
            @Override
            String getName() {
                return "Memory";
            }
            
            @Override
            double getProgress() {
                final long total = Runtime.getRuntime().totalMemory();
                final long free = Runtime.getRuntime().freeMemory();
                final long delta = total - free;
                return delta / (double)Runtime.getRuntime().maxMemory();
            }
        });
        Indicators.indicators.add(new Indicator() {
            @Override
            boolean enabled() {
                return Indicators.this.dmgflyy.getValue();
            }
            
            @Override
            String getName() {
                return "DMG Fly";
            }
            
            @Override
            double getProgress() {
                return DMGFly.getProgress();
            }
        });
        Indicators.indicators.add(new Indicator() {
            @Override
            boolean enabled() {
                return Indicators.this.dmgspeed.getValue();
            }
            
            @Override
            String getName() {
                return "DMG Speed";
            }
            
            @Override
            double getProgress() {
                return MSTSpeed.getProgress();
            }
        });
        Indicators.indicators.add(new Indicator() {
            @Override
            boolean enabled() {
                return Indicators.this.TPS.getValue();
            }
            
            @Override
            String getName() {
                return "TPS";
            }
            
            @Override
            double getProgress() {
                return Thunderhack.serverManager.getTPS() / 20.0f;
            }
        });
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        this.draw(e.scaledResolution);
    }
    
    @Override
    public void onUpdate() {
        if (!this.once) {
            this.once();
            this.once = true;
            return;
        }
        Indicators.astolfo.update();
        Indicators.indicators.forEach(indicator -> indicator.update());
    }
    
    public void draw(final ScaledResolution sr) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)(this.getX() * sr.getScaledWidth()), (double)(this.getY() * sr.getScaledHeight()), 0.0);
        final List<Indicator> enabledIndicators = new ArrayList<Indicator>();
        for (final Indicator indicator : Indicators.indicators) {
            if (indicator.enabled()) {
                enabledIndicators.add(indicator);
            }
        }
        final int enabledCount = enabledIndicators.size();
        if (enabledCount > 0) {
            for (int i = 0; i < enabledCount; ++i) {
                GL11.glPushMatrix();
                GL11.glTranslated((double)(this.range.getValue() * i), 0.0, 0.0);
                final Indicator ind = enabledIndicators.get(i);
                if (!this.blur.getValue()) {
                    RenderUtil.drawSmoothRect(0.0f, 0.0f, 44.0f, 44.0f, new Color(25, 25, 25, 180).getRGB());
                }
                else {
                    DrawHelper.drawRectWithGlow(0.0, 0.0, 44.0, 44.0, this.grange.getValue(), this.gmult.getValue(), this.cs.getValue().getColorObject());
                }
                GL11.glTranslated(22.0, 26.0, 0.0);
                this.drawCircle(ind.getName(), ind.progress());
                GL11.glPopMatrix();
            }
        }
        GL11.glPopMatrix();
    }
    
    public void drawCircle(final String name, final double offset) {
        GL11.glDisable(3553);
        final boolean oldState = GL11.glIsEnabled(3042);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glLineWidth(5.5f);
        GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.5f);
        GL11.glBegin(3);
        for (int i = 0; i < 360; ++i) {
            final double x = Math.cos(Math.toRadians(i)) * 11.0;
            final double z = Math.sin(Math.toRadians(i)) * 11.0;
            GL11.glVertex2d(x, z);
        }
        GL11.glEnd();
        GL11.glBegin(3);
        for (int i = -90; i < -90.0 + 360.0 * offset; ++i) {
            float red = (float)this.cc.getValue().getRed();
            float green = (float)this.cc.getValue().getGreen();
            float blue = (float)this.cc.getValue().getBlue();
            if (this.colorType.getValue() == mode2.StateBased) {
                final float[] buffer = getRG(offset);
                red = buffer[0];
                green = buffer[1];
                blue = buffer[2];
            }
            else if (this.colorType.getValue() == mode2.Astolfo) {
                final double stage = (i + 90) / 360.0;
                final int clr = Indicators.astolfo.getColor(stage);
                red = (float)(clr >> 16 & 0xFF);
                green = (float)(clr >> 8 & 0xFF);
                blue = (float)(clr & 0xFF);
            }
            GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, 1.0f);
            final double x2 = Math.cos(Math.toRadians(i)) * 11.0;
            final double z2 = Math.sin(Math.toRadians(i)) * 11.0;
            GL11.glVertex2d(x2, z2);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        if (!oldState) {
            GL11.glDisable(3042);
        }
        GL11.glEnable(3553);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (!Objects.equals(name, "TPS")) {
            FontRender.drawCentString6((int)(offset * 100.0) + "%", 0.3f, -0.8f, new Color(200, 200, 200, 255).getRGB());
            FontRender.drawCentString6(name, 0.0f, -20.0f, new Color(200, 200, 200, 255).getRGB());
        }
        else {
            FontRender.drawCentString6(String.valueOf((int)(offset * 20.0)), 0.0f, -0.8f, new Color(200, 200, 200, 255).getRGB());
            FontRender.drawCentString6(name, 0.0f, -20.0f, new Color(200, 200, 200, 255).getRGB());
        }
    }
    
    static {
        Indicators.astolfo = new AstolfoAnimation();
        indicators = new ArrayList<Indicator>();
    }
    
    public enum mode2
    {
        Static, 
        StateBased, 
        Astolfo;
    }
    
    public abstract static class Indicator
    {
        DynamicAnimation animation;
        
        public Indicator() {
            this.animation = new DynamicAnimation();
        }
        
        void update() {
            this.animation.setValue(Math.max(this.getProgress(), 0.0));
            this.animation.update();
        }
        
        double progress() {
            return this.animation.getValue();
        }
        
        abstract boolean enabled();
        
        abstract String getName();
        
        abstract double getProgress();
    }
}
