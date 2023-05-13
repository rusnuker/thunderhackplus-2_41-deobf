//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;

@Cancelable
public class EventBlockCollisionBoundingBox extends Event
{
    private final BlockPos _pos;
    private AxisAlignedBB _boundingBox;
    
    public EventBlockCollisionBoundingBox(final BlockPos pos) {
        this._pos = pos;
    }
    
    public BlockPos getPos() {
        return this._pos;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this._boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this._boundingBox = boundingBox;
    }
}
