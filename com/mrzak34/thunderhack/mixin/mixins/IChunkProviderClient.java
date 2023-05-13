//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import it.unimi.dsi.fastutil.longs.*;
import net.minecraft.world.chunk.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ ChunkProviderClient.class })
public interface IChunkProviderClient
{
    @Accessor("loadedChunks")
    Long2ObjectMap<Chunk> getLoadedChunks();
}
