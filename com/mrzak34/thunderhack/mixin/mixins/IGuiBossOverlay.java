//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import java.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ GuiBossOverlay.class })
public interface IGuiBossOverlay
{
    @Accessor("mapBossInfos")
    Map<UUID, BossInfoClient> getMapBossInfos();
    
    @Invoker("render")
    void invokeRender(final int p0, final int p1, final BossInfo p2);
}
