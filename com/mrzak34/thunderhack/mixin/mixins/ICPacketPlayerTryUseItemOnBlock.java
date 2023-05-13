//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ CPacketPlayerTryUseItemOnBlock.class })
public interface ICPacketPlayerTryUseItemOnBlock
{
    @Accessor("hand")
    void setHand(final EnumHand p0);
}
