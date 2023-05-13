//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ SPacketSpawnObject.class })
public abstract class MixinSPacketSpawnObject implements ISPacketSpawnObject
{
    @Unique
    private boolean attacked;
    
    public boolean isAttacked() {
        return this.attacked;
    }
    
    public void setAttacked(final boolean attacked) {
        this.attacked = attacked;
    }
}
