//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class AntiTotemData extends PositionData
{
    private final Set<PositionData> corresponding;
    
    public AntiTotemData(final PositionData data, final AutoCrystal module) {
        super(data.getPos(), data.getMaxLength(), module, data.getAntiTotems());
        this.corresponding = new TreeSet<PositionData>();
    }
    
    public void addCorrespondingData(final PositionData data) {
        this.corresponding.add(data);
    }
    
    public Set<PositionData> getCorresponding() {
        return this.corresponding;
    }
    
    @Override
    public int compareTo(final PositionData o) {
        if (Math.abs(o.getSelfDamage() - this.getSelfDamage()) >= 1.0f || !(o instanceof AntiTotemData)) {
            return super.compareTo(o);
        }
        final EntityPlayer player = this.getFirstTarget();
        final EntityPlayer other = ((AntiTotemData)o).getFirstTarget();
        if (other == null) {
            return (player == null) ? super.compareTo(o) : -1;
        }
        return (player == null) ? 1 : Double.compare(player.getDistanceSq(this.getPos()), other.getDistanceSq(o.getPos()));
    }
    
    public EntityPlayer getFirstTarget() {
        return this.getAntiTotems().stream().findFirst().orElse(null);
    }
}
