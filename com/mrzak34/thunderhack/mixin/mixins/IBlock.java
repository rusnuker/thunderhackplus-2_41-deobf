//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ Block.class })
public interface IBlock
{
    @Accessor("blockResistance")
    float getBlockResistance();
}
