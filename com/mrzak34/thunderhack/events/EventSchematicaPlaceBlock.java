//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;

public class EventSchematicaPlaceBlock extends Event
{
    public BlockPos Pos;
    
    public EventSchematicaPlaceBlock(final BlockPos p_Pos) {
        this.Pos = p_Pos;
    }
}
