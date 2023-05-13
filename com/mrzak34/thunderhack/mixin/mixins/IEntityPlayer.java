//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.datasync.*;
import org.spongepowered.asm.mixin.gen.*;
import com.mojang.authlib.*;

@Mixin({ EntityPlayer.class })
public interface IEntityPlayer
{
    @Accessor("ABSORPTION")
    default DataParameter<Float> getAbsorption() {
        throw new IllegalStateException("ABSORPTION accessor wasn't shadowed.");
    }
    
    @Accessor("gameProfile")
    void setGameProfile(final GameProfile p0);
}
