//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.*;

public class CrystalDataMotion extends CrystalData
{
    private Timing timing;
    private float postSelf;
    
    public CrystalDataMotion(final Entity crystal) {
        super(crystal);
        this.timing = Timing.BOTH;
    }
    
    public float getPostSelf() {
        return this.postSelf;
    }
    
    public void setPostSelf(final float postSelf) {
        this.postSelf = postSelf;
    }
    
    public Timing getTiming() {
        return this.timing;
    }
    
    public void invalidateTiming(final Timing timing) {
        if (timing == Timing.PRE) {
            if (this.timing == Timing.PRE) {
                this.timing = Timing.NONE;
            }
            else if (this.timing == Timing.BOTH) {
                this.timing = Timing.POST;
            }
        }
        else if (this.timing == Timing.POST) {
            this.timing = Timing.NONE;
        }
        else if (this.timing == Timing.BOTH) {
            this.timing = Timing.PRE;
        }
    }
    
    public int compareTo(final CrystalData o) {
        if (o instanceof CrystalDataMotion && Math.abs(o.getDamage() - this.getDamage()) < 1.0f) {
            final CrystalDataMotion motion = (CrystalDataMotion)o;
            boolean breakCase = true;
            float lowestSelf = Float.MAX_VALUE;
            boolean thisBetter = this.getDamage() > o.getDamage();
            switch (motion.getTiming()) {
                case BOTH: {
                    breakCase = false;
                }
                case PRE: {
                    if (motion.getSelfDmg() < lowestSelf) {
                        lowestSelf = motion.getSelfDmg();
                        thisBetter = false;
                    }
                    if (breakCase) {
                        break;
                    }
                }
                case POST: {
                    if (motion.getPostSelf() < lowestSelf) {
                        lowestSelf = motion.getSelfDmg();
                        thisBetter = false;
                        break;
                    }
                    break;
                }
                case NONE: {
                    return -1;
                }
            }
            breakCase = true;
            switch (this.getTiming()) {
                case BOTH: {
                    breakCase = false;
                }
                case PRE: {
                    if (this.getSelfDmg() < lowestSelf || (this.getSelfDmg() == lowestSelf && this.getDamage() > motion.getDamage())) {
                        lowestSelf = this.getSelfDmg();
                        thisBetter = true;
                    }
                    if (breakCase) {
                        break;
                    }
                }
                case POST: {
                    if (this.getSelfDmg() < lowestSelf || (this.getSelfDmg() == lowestSelf && this.getDamage() > motion.getDamage())) {
                        thisBetter = true;
                        break;
                    }
                    break;
                }
                case NONE: {
                    return 1;
                }
            }
            return thisBetter ? -1 : 1;
        }
        return super.compareTo(o);
    }
    
    public enum Timing
    {
        NONE, 
        PRE, 
        POST, 
        BOTH;
    }
}
