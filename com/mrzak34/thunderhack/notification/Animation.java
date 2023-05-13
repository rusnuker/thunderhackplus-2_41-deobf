//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.notification;

import com.mrzak34.thunderhack.util.*;

public abstract class Animation
{
    public Timer timerUtil;
    protected int duration;
    protected double endPoint;
    protected Direction direction;
    
    public Animation(final int ms, final double endPoint) {
        this.timerUtil = new Timer();
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = Direction.FORWARDS;
    }
    
    public Animation(final int ms, final double endPoint, final Direction direction) {
        this.timerUtil = new Timer();
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = direction;
    }
    
    public boolean finished(final Direction direction) {
        return this.isDone() && this.direction.equals(direction);
    }
    
    public void reset() {
        this.timerUtil.reset();
    }
    
    public boolean isDone() {
        return this.timerUtil.passedMs(this.duration);
    }
    
    public Direction getDirection() {
        return this.direction;
    }
    
    public void setDirection(final Direction direction) {
        if (this.direction != direction) {
            this.direction = direction;
            this.timerUtil.setMs(System.currentTimeMillis() - (this.duration - Math.min(this.duration, this.timerUtil.getPassedTimeMs())));
        }
    }
    
    protected boolean correctOutput() {
        return false;
    }
    
    public double getOutput() {
        if (this.direction == Direction.FORWARDS) {
            if (this.isDone()) {
                return this.endPoint;
            }
            return this.getEquation((double)this.timerUtil.getPassedTimeMs()) * this.endPoint;
        }
        else {
            if (this.isDone()) {
                return 0.0;
            }
            if (this.correctOutput()) {
                final double revTime = (double)Math.min(this.duration, Math.max(0L, this.duration - this.timerUtil.getPassedTimeMs()));
                return this.getEquation(revTime) * this.endPoint;
            }
            return (1.0 - this.getEquation((double)this.timerUtil.getPassedTimeMs())) * this.endPoint;
        }
    }
    
    protected abstract double getEquation(final double p0);
}
