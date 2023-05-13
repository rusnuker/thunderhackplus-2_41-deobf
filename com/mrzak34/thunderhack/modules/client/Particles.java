//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.client;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.particles.*;
import java.util.concurrent.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import com.mrzak34.thunderhack.util.render.*;

public class Particles extends Module
{
    private static final float SPEED = 0.1f;
    private static Particles INSTANCE;
    public Setting<Integer> delta;
    public Setting<Integer> amount;
    public Setting<Float> scale1;
    public Setting<Float> linet;
    public Setting<Integer> dist;
    private final List<Particle> particleList;
    
    public Particles() {
        super("Particles", "\u0440\u0438\u0441\u0443\u0435\u0442 \u043f\u0430\u0440\u0442\u0438\u043a\u043b\u044b \u0432 \u0433\u0443\u0438", Category.CLIENT);
        this.delta = (Setting<Integer>)this.register(new Setting("Speed", (T)1, (T)0, (T)60));
        this.amount = (Setting<Integer>)this.register(new Setting("Amount ", (T)150, (T)0, (T)666));
        this.scale1 = (Setting<Float>)this.register(new Setting("Scale", (T)5.0f, (T)0.1f, (T)30.0f));
        this.linet = (Setting<Float>)this.register(new Setting("lineT", (T)1.0f, (T)0.1f, (T)10.0f));
        this.dist = (Setting<Integer>)this.register(new Setting("Dist ", (T)50, (T)1, (T)500));
        this.particleList = new CopyOnWriteArrayList<Particle>();
        this.setInstance();
    }
    
    public static Particles getInstance() {
        if (Particles.INSTANCE == null) {
            Particles.INSTANCE = new Particles();
        }
        return Particles.INSTANCE;
    }
    
    public static void drawGradientLine(final float x1, final float y1, final float x2, final float y2, final float lineWidth, final Color color1, final Color color2) {
        GL11.glLineWidth(lineWidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        GL11.glDisable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.shadeModel(7425);
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x1, (double)y1, 0.0).color(color1.getRed() / 255.0f, color1.getGreen() / 255.0f, color1.getBlue() / 255.0f, color1.getAlpha() / 255.0f).endVertex();
        bufferbuilder.pos((double)x2, (double)y2, 0.0).color(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, color2.getAlpha() / 255.0f).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
    }
    
    public static double distance(final float x, final float y, final float x1, final float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }
    
    private void setInstance() {
        Particles.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        for (final Particle particle : this.particleList) {
            particle.tick((int)this.delta.getValue(), 0.1f);
        }
    }
    
    @SubscribeEvent
    public void onGuiOpened(final GuiOpenEvent event) {
        if (event.getGui() != null) {
            this.addParticles(this.amount.getValue());
        }
        else {
            this.particleList.clear();
        }
    }
    
    public void addParticles(final int amount) {
        for (int i = 0; i < amount; ++i) {
            this.particleList.add(Particle.generateParticle((float)this.scale1.getValue()));
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        try {
            this.render();
        }
        catch (Exception ex) {}
    }
    
    public void render() {
        for (final Particle particle : this.particleList) {
            float nearestDistance = 0.0f;
            Particle nearestParticle = null;
            for (final Particle particle2 : this.particleList) {
                final float distance = particle.getDistanceTo(particle2);
                if (distance <= this.dist.getValue() && (nearestDistance <= 0.0f || distance <= nearestDistance)) {
                    nearestDistance = distance;
                    nearestParticle = particle2;
                }
            }
            if (nearestParticle != null) {
                drawGradientLine((float)RadarRewrite.interp((double)particle.getX(), (double)particle.get_prevX()), (float)RadarRewrite.interp((double)particle.getY(), (double)particle.get_prevY()), (float)RadarRewrite.interp((double)nearestParticle.getX(), (double)nearestParticle.get_prevX()), (float)RadarRewrite.interp((double)nearestParticle.getY(), (double)nearestParticle.get_prevY()), this.linet.getValue(), particle.getColor(), nearestParticle.getColor());
            }
            RenderHelper.drawCircle((float)RadarRewrite.interp((double)particle.getX(), (double)particle.get_prevX()), (float)RadarRewrite.interp((double)particle.getY(), (double)particle.get_prevY()), particle.getSize(), true, particle.getColor());
        }
    }
    
    static {
        Particles.INSTANCE = new Particles();
    }
}
