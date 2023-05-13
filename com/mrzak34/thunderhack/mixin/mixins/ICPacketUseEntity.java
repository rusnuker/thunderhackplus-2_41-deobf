//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ CPacketUseEntity.class })
public interface ICPacketUseEntity
{
    @Accessor("entityId")
    void setEntityId(final int p0);
    
    @Accessor("entityId")
    int getEntityId();
    
    @Accessor("action")
    void setAction(final CPacketUseEntity.Action p0);
}
