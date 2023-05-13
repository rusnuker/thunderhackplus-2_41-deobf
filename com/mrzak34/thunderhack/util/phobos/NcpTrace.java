//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

public abstract class NcpTrace
{
    protected double x0;
    protected double y0;
    protected double z0;
    protected double dX;
    protected double dY;
    protected double dZ;
    protected int blockX;
    protected int blockY;
    protected int blockZ;
    protected int endBlockX;
    protected int endBlockY;
    protected int endBlockZ;
    protected double oX;
    protected double oY;
    protected double oZ;
    protected double t;
    protected double tol;
    protected boolean forceStepEndPos;
    protected int step;
    protected boolean secondaryStep;
    protected boolean collides;
    private int maxSteps;
    
    public NcpTrace() {
        this.t = Double.MIN_VALUE;
        this.tol = 0.0;
        this.forceStepEndPos = true;
        this.step = 0;
        this.secondaryStep = true;
        this.collides = false;
        this.maxSteps = Integer.MAX_VALUE;
        this.set(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    private static double tDiff(final double dTotal, final double offset, final boolean isEndBlock) {
        if (dTotal > 0.0) {
            if (offset >= 1.0) {
                return isEndBlock ? Double.MAX_VALUE : 0.0;
            }
            return (1.0 - offset) / dTotal;
        }
        else {
            if (dTotal >= 0.0) {
                return Double.MAX_VALUE;
            }
            if (offset <= 0.0) {
                return isEndBlock ? Double.MAX_VALUE : 0.0;
            }
            return offset / -dTotal;
        }
    }
    
    protected abstract boolean step(final int p0, final int p1, final int p2, final double p3, final double p4, final double p5, final double p6, final boolean p7);
    
    public void set(final double x0, final double y0, final double z0, final double x1, final double y1, final double z1) {
        this.x0 = x0;
        this.y0 = y0;
        this.z0 = z0;
        this.dX = x1 - x0;
        this.dY = y1 - y0;
        this.dZ = z1 - z0;
        this.blockX = Visible.floor(x0);
        this.blockY = Visible.floor(y0);
        this.blockZ = Visible.floor(z0);
        this.endBlockX = Visible.floor(x1);
        this.endBlockY = Visible.floor(y1);
        this.endBlockZ = Visible.floor(z1);
        this.oX = x0 - this.blockX;
        this.oY = y0 - this.blockY;
        this.oZ = z0 - this.blockZ;
        this.t = 0.0;
        this.step = 0;
        this.collides = false;
    }
    
    public void loop() {
        while (this.t + this.tol < 1.0) {
            final double tX = tDiff(this.dX, this.oX, this.blockX == this.endBlockX);
            final double tY = tDiff(this.dY, this.oY, this.blockY == this.endBlockY);
            final double tZ = tDiff(this.dZ, this.oZ, this.blockZ == this.endBlockZ);
            double tMin = Math.max(0.0, Math.min(tX, Math.min(tY, tZ)));
            if (tMin == Double.MAX_VALUE) {
                if (this.step >= 1) {
                    break;
                }
                tMin = 0.0;
            }
            if (this.t + tMin > 1.0) {
                tMin = 1.0 - this.t;
            }
            ++this.step;
            if (!this.step(this.blockX, this.blockY, this.blockZ, this.oX, this.oY, this.oZ, tMin, true)) {
                break;
            }
            if (this.t + tMin + this.tol >= 1.0 && this.isEndBlock()) {
                break;
            }
            int transitions = 0;
            boolean transZ;
            boolean transX;
            boolean transY = transX = (transZ = false);
            if (tX == tMin && this.blockX != this.endBlockX && this.dX != 0.0) {
                transX = true;
                ++transitions;
            }
            if (tY == tMin && this.blockY != this.endBlockY && this.dY != 0.0) {
                transY = true;
                ++transitions;
            }
            if (tZ == tMin && this.blockZ != this.endBlockZ && this.dZ != 0.0) {
                transZ = true;
                ++transitions;
            }
            this.oX = Math.min(1.0, Math.max(0.0, this.oX + tMin * this.dX));
            this.oY = Math.min(1.0, Math.max(0.0, this.oY + tMin * this.dY));
            this.oZ = Math.min(1.0, Math.max(0.0, this.oZ + tMin * this.dZ));
            this.t = Math.min(1.0, this.t + tMin);
            if (transitions <= 0) {
                break;
            }
            if (!this.handleTransitions(transitions, transX, transY, transZ)) {
                break;
            }
            if (this.forceStepEndPos && this.t + this.tol >= 1.0) {
                this.step(this.blockX, this.blockY, this.blockZ, this.oX, this.oY, this.oZ, 0.0, true);
                break;
            }
            if (this.step >= this.maxSteps) {
                break;
            }
        }
    }
    
    protected boolean handleTransitions(final int transitions, final boolean transX, final boolean transY, final boolean transZ) {
        if (transitions > 1 && this.secondaryStep && !this.handleSecondaryTransitions(transitions, transX, transY, transZ)) {
            return false;
        }
        double tcMin = 1.0;
        if (transX) {
            if (this.dX > 0.0) {
                ++this.blockX;
                tcMin = Math.min(tcMin, (this.blockX - this.x0) / this.dX);
            }
            else {
                --this.blockX;
                tcMin = Math.min(tcMin, (1.0 + this.blockX - this.x0) / this.dX);
            }
        }
        if (transY) {
            if (this.dY > 0.0) {
                ++this.blockY;
                tcMin = Math.min(tcMin, (this.blockY - this.y0) / this.dY);
            }
            else {
                --this.blockY;
                tcMin = Math.min(tcMin, (1.0 + this.blockY - this.y0) / this.dY);
            }
        }
        if (transZ) {
            if (this.dZ > 0.0) {
                ++this.blockZ;
                tcMin = Math.min(tcMin, (this.blockZ - this.z0) / this.dZ);
            }
            else {
                --this.blockZ;
                tcMin = Math.min(tcMin, (1.0 + this.blockZ - this.z0) / this.dZ);
            }
        }
        this.oX = this.x0 + tcMin * this.dX - this.blockX;
        this.oY = this.y0 + tcMin * this.dY - this.blockY;
        this.oZ = this.z0 + tcMin * this.dZ - this.blockZ;
        this.t = tcMin;
        return true;
    }
    
    protected boolean handleSecondaryTransitions(final int transitions, final boolean transX, final boolean transY, final boolean transZ) {
        return (!transX || this.step(this.blockX + ((this.dX > 0.0) ? 1 : -1), this.blockY, this.blockZ, (this.dX > 0.0) ? 0.0 : 1.0, this.oY, this.oZ, 0.0, false)) && (!transY || this.step(this.blockX, this.blockY + ((this.dY > 0.0) ? 1 : -1), this.blockZ, this.oX, (this.dY > 0.0) ? 0.0 : 1.0, this.oZ, 0.0, false)) && (!transZ || this.step(this.blockX, this.blockY, this.blockZ + ((this.dZ > 0.0) ? 1 : -1), this.oX, this.oY, (this.dZ > 0.0) ? 0.0 : 1.0, 0.0, false)) && (transitions != 3 || this.handleSecondaryDoubleTransitions());
    }
    
    protected boolean handleSecondaryDoubleTransitions() {
        return this.step(this.blockX + ((this.dX > 0.0) ? 1 : -1), this.blockY + ((this.dY > 0.0) ? 1 : -1), this.blockZ, (this.dX > 0.0) ? 0.0 : 1.0, (this.dY > 0.0) ? 0.0 : 1.0, this.oZ, 0.0, false) && this.step(this.blockX + ((this.dX > 0.0) ? 1 : -1), this.blockY, this.blockZ + ((this.dZ > 0.0) ? 1 : -1), (this.dX > 0.0) ? 0.0 : 1.0, this.oY, (this.dZ > 0.0) ? 0.0 : 1.0, 0.0, false) && this.step(this.blockX, this.blockY + ((this.dY > 0.0) ? 1 : -1), this.blockZ + ((this.dZ > 0.0) ? 1 : -1), this.oX, (this.dY > 0.0) ? 0.0 : 1.0, (this.dZ > 0.0) ? 0.0 : 1.0, 0.0, false);
    }
    
    public boolean isEndBlock() {
        return this.blockX == this.endBlockX && this.blockY == this.endBlockY && this.blockZ == this.endBlockZ;
    }
    
    public int getStepsDone() {
        return this.step;
    }
    
    public int getMaxSteps() {
        return this.maxSteps;
    }
    
    public void setMaxSteps(final int maxSteps) {
        this.maxSteps = maxSteps;
    }
}
