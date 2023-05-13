//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ CPacketCustomPayload.class })
public interface ICPacketCustomPayload
{
    @Accessor("data")
    void setData(final PacketBuffer p0);
}
