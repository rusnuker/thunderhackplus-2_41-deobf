//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.util.math.*;

@Mixin({ PlayerControllerMP.class })
public interface IPlayerControllerMP
{
    @Accessor("curBlockDamageMP")
    float getCurBlockDamageMP();
    
    @Accessor("curBlockDamageMP")
    void setCurBlockDamageMP(final float p0);
    
    @Accessor("currentBlock")
    BlockPos getCurrentBlock();
}
