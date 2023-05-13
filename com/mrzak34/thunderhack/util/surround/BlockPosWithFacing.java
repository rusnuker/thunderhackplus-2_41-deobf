//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.surround;

import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class BlockPosWithFacing
{
    private final BlockPos bp;
    private final EnumFacing facing;
    
    public BlockPosWithFacing(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.bp = blockPos;
        this.facing = enumFacing;
    }
    
    public BlockPos getPosition() {
        return this.bp;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
}
