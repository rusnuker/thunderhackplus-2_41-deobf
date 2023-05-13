//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.util.*;
import com.mojang.authlib.*;

public class EntityPlayerNoInterp extends EntityOtherPlayerMP implements IEntityNoInterp
{
    public EntityPlayerNoInterp(final World worldIn) {
        this(worldIn, Util.mc.player.getGameProfile());
    }
    
    public EntityPlayerNoInterp(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    
    public void setNoInterpX(final double x) {
    }
    
    public void setNoInterpY(final double y) {
    }
    
    public void setNoInterpZ(final double z) {
    }
}
