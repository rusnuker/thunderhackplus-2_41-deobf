//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

@Cancelable
public class BlockRenderEvent extends Event
{
    private final Block block;
    private final BlockPos pos;
    
    public BlockRenderEvent(final Block block, final BlockPos pos) {
        this.block = block;
        this.pos = pos;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
}
