//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import java.util.*;
import com.mojang.authlib.*;

public class ShieldPlayer extends EntityPlayer
{
    public ShieldPlayer(final World worldIn) {
        super(worldIn, new GameProfile(UUID.randomUUID(), "Shield"));
    }
    
    public boolean isSpectator() {
        return false;
    }
    
    public boolean isCreative() {
        return false;
    }
}
