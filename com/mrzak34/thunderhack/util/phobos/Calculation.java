//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

public class Calculation extends AbstractCalculation<CrystalData>
{
    public Calculation(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players, final BlockPos... blackList) {
        super(module, (List)entities, (List)players, blackList);
    }
    
    public Calculation(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players, final boolean breakOnly, final boolean noBreak, final BlockPos... blackList) {
        super(module, (List)entities, (List)players, breakOnly, noBreak, blackList);
    }
    
    protected IBreakHelper<CrystalData> getBreakHelper() {
        return (IBreakHelper<CrystalData>)this.module.breakHelper;
    }
}
