//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class RenderItemEvent extends Event
{
    float mainX;
    float mainY;
    float mainZ;
    float offX;
    float offY;
    float offZ;
    float mainRotX;
    float mainRotY;
    float mainRotZ;
    float offRotX;
    float offRotY;
    float offRotZ;
    float mainHandScaleX;
    float mainHandScaleY;
    float mainHandScaleZ;
    float offHandScaleX;
    float offHandScaleY;
    float offHandScaleZ;
    
    public RenderItemEvent(final float mainX, final float mainY, final float mainZ, final float offX, final float offY, final float offZ, final float mainRotX, final float mainRotY, final float mainRotZ, final float offRotX, final float offRotY, final float offRotZ, final float mainHandScaleX, final float mainHandScaleY, final float mainHandScaleZ, final float offHandScaleX, final float offHandScaleY, final float offHandScaleZ) {
        this.mainX = mainX;
        this.mainY = mainY;
        this.mainZ = mainZ;
        this.offX = offX;
        this.offY = offY;
        this.offZ = offZ;
        this.mainRotX = mainRotX;
        this.mainRotY = mainRotY;
        this.mainRotZ = mainRotZ;
        this.offRotX = offRotX;
        this.offRotY = offRotY;
        this.offRotZ = offRotZ;
        this.mainHandScaleX = mainHandScaleX;
        this.mainHandScaleY = mainHandScaleY;
        this.mainHandScaleZ = mainHandScaleZ;
        this.offHandScaleX = offHandScaleX;
        this.offHandScaleY = offHandScaleY;
        this.offHandScaleZ = offHandScaleZ;
    }
    
    public float getMainX() {
        return this.mainX;
    }
    
    public void setMainX(final float v) {
        this.mainX = v;
    }
    
    public float getMainY() {
        return this.mainY;
    }
    
    public void setMainY(final float v) {
        this.mainY = v;
    }
    
    public float getMainZ() {
        return this.mainZ;
    }
    
    public void setMainZ(final float v) {
        this.mainZ = v;
    }
    
    public float getOffX() {
        return this.offX;
    }
    
    public void setOffX(final float v) {
        this.offX = v;
    }
    
    public float getOffY() {
        return this.offY;
    }
    
    public void setOffY(final float v) {
        this.offY = v;
    }
    
    public float getOffZ() {
        return this.offZ;
    }
    
    public void setOffZ(final float v) {
        this.offZ = v;
    }
    
    public float getMainRotX() {
        return this.mainRotX;
    }
    
    public void setMainRotX(final float v) {
        this.mainRotX = v;
    }
    
    public float getMainRotY() {
        return this.mainRotY;
    }
    
    public void setMainRotY(final float v) {
        this.mainRotY = v;
    }
    
    public float getMainRotZ() {
        return this.mainRotZ;
    }
    
    public void setMainRotZ(final float v) {
        this.mainRotZ = v;
    }
    
    public float getOffRotX() {
        return this.offRotX;
    }
    
    public void setOffRotX(final float v) {
        this.offRotX = v;
    }
    
    public float getOffRotY() {
        return this.offRotY;
    }
    
    public void setOffRotY(final float v) {
        this.offRotY = v;
    }
    
    public float getOffRotZ() {
        return this.offRotZ;
    }
    
    public void setOffRotZ(final float v) {
        this.offRotZ = v;
    }
    
    public float getMainHandScaleX() {
        return this.mainHandScaleX;
    }
    
    public void setMainHandScaleX(final float v) {
        this.mainHandScaleX = v;
    }
    
    public float getMainHandScaleY() {
        return this.mainHandScaleY;
    }
    
    public void setMainHandScaleY(final float v) {
        this.mainHandScaleY = v;
    }
    
    public float getMainHandScaleZ() {
        return this.mainHandScaleZ;
    }
    
    public void setMainHandScaleZ(final float v) {
        this.mainHandScaleZ = v;
    }
    
    public float getOffHandScaleX() {
        return this.offHandScaleX;
    }
    
    public void setOffHandScaleX(final float v) {
        this.offHandScaleX = v;
    }
    
    public float getOffHandScaleY() {
        return this.offHandScaleY;
    }
    
    public void setOffHandScaleY(final float v) {
        this.offHandScaleY = v;
    }
    
    public float getOffHandScaleZ() {
        return this.offHandScaleZ;
    }
    
    public void setOffHandScaleZ(final float v) {
        this.offHandScaleZ = v;
    }
}
