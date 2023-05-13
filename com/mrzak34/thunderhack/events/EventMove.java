//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class EventMove extends Event
{
    public double x;
    public double y;
    public double z;
    private MoverType move_type;
    
    public EventMove(final MoverType type, final double x, final double y, final double z) {
        this.move_type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public MoverType get_move_type() {
        return this.move_type;
    }
    
    public void set_move_type(final MoverType type) {
        this.move_type = type;
    }
    
    public double get_x() {
        return this.x;
    }
    
    public void set_x(final double x) {
        this.x = x;
    }
    
    public double get_y() {
        return this.y;
    }
    
    public void set_y(final double y) {
        this.y = y;
    }
    
    public double get_z() {
        return this.z;
    }
    
    public void set_z(final double z) {
        this.z = z;
    }
}
