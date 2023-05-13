//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.audio.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SoundHandler.class })
public interface ISoundHandler
{
    @Accessor("sndManager")
    SoundManager getSoundManager();
}
