//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public interface IBreakHelper<T extends CrystalData>
{
    BreakData<T> newData(final Collection<T> p0);
    
    BreakData<T> getData(final Collection<T> p0, final List<Entity> p1, final List<EntityPlayer> p2, final List<EntityPlayer> p3);
}
