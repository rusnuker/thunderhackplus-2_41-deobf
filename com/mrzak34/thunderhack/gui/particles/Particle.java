//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.particles;

import java.awt.*;
import javax.vecmath.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.gui.*;
import java.util.*;
import com.mrzak34.thunderhack.modules.client.*;

public class Particle
{
    public static Color[] colors;
    private final Color color;
    private final Vector2f pos;
    private final Vector2f prev_pos;
    private Vector2f velocity;
    private float size;
    private float alpha;
    
    public Particle(final Vector2f velocity, final float x, final float y, final float size, final Color color) {
        this.velocity = velocity;
        this.color = color;
        this.pos = new Vector2f(x, y);
        this.prev_pos = new Vector2f(x, y);
        this.size = size;
    }
    
    public static Particle generateParticle(final float sc) {
        final ScaledResolution sr = new ScaledResolution(Util.mc);
        final Vector2f velocity = new Vector2f((float)(Math.random() * 2.0 - 1.0), (float)(Math.random() * 2.0 - 1.0));
        final float x = (float)getRandomNumberUsingNextInt(100, sr.getScaledWidth() - 30);
        final float y = (float)getRandomNumberUsingNextInt(100, sr.getScaledHeight() - 30);
        final float size = sc + getRandomNumberUsingNextInt(0, (int)(sc / 3.0f));
        final int n = (int)Math.floor(Math.random() * Particle.colors.length);
        final Color color = Particle.colors[n];
        return new Particle(velocity, x, y, size, color);
    }
    
    public static int getRandomNumberUsingNextInt(final int min, final int max) {
        final Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    
    public static double distance(final float x, final float y, final float x1, final float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }
    
    public float getAlpha() {
        return this.alpha;
    }
    
    public float getX() {
        return this.pos.getX();
    }
    
    public float getY() {
        return this.pos.getY();
    }
    
    public float get_prevX() {
        return this.prev_pos.getX();
    }
    
    public float get_prevY() {
        return this.prev_pos.getY();
    }
    
    public void setX(final float x) {
        this.pos.setX(x);
    }
    
    public void setY(final float y) {
        this.pos.setY(y);
    }
    
    public float getSize() {
        return this.size;
    }
    
    public void setSize(final float size) {
        this.size = size;
    }
    
    public void tick(final int delta, final float speed) {
        final ScaledResolution sr = new ScaledResolution(Util.mc);
        this.prev_pos.x = this.pos.x;
        this.prev_pos.y = this.pos.y;
        final Vector2f pos = this.pos;
        pos.x += this.velocity.getX() * delta * speed;
        final Vector2f pos2 = this.pos;
        pos2.y += this.velocity.getY() * delta * speed;
        if (this.alpha < 255.0f) {
            this.alpha += 0.05f * delta;
        }
        if (this.pos.getX() + Particles.getInstance().scale1.getValue() > sr.getScaledWidth()) {
            this.velocity = new Vector2f(-this.velocity.x, this.velocity.y);
        }
        if (this.pos.getX() - Particles.getInstance().scale1.getValue() < 0.0f) {
            this.velocity = new Vector2f(-this.velocity.x, this.velocity.y);
        }
        if (this.pos.getY() + Particles.getInstance().scale1.getValue() > sr.getScaledWidth()) {
            this.velocity = new Vector2f(this.velocity.x, -this.velocity.y);
        }
        if (this.pos.getY() - Particles.getInstance().scale1.getValue() < 0.0f) {
            this.velocity = new Vector2f(this.velocity.x, -this.velocity.y);
        }
    }
    
    public float getDistanceTo(final Particle particle1) {
        return this.getDistanceTo(particle1.getX(), particle1.getY());
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public float getDistanceTo(final float x, final float y) {
        return (float)distance(this.getX(), this.getY(), x, y);
    }
    
    static {
        Particle.colors = new Color[] { new Color(0, 233, 255), new Color(209, 2, 209), new Color(237, 0, 87), new Color(195, 0, 195), new Color(255, 1, 255), new Color(1, 95, 245), new Color(206, 2, 30), new Color(90, 14, 214) };
    }
}
