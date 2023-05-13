//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;

@Cancelable
public class EventSchematicaPlaceBlockFull extends EventSchematicaPlaceBlock
{
    public boolean Result;
    public ItemStack ItemStack;
    
    public EventSchematicaPlaceBlockFull(final BlockPos p_Pos, final ItemStack itemStack) {
        super(p_Pos);
        this.Result = true;
        this.ItemStack = itemStack;
    }
    
    public boolean GetResult() {
        return this.Result;
    }
}
