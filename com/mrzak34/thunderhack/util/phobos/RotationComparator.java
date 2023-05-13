//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;
import com.mrzak34.thunderhack.*;

public class RotationComparator implements Comparator<CrystalData>
{
    private final double ex;
    private final double diff;
    
    public RotationComparator(final double exponent, final double minRotationDiff) {
        this.ex = exponent;
        this.diff = minRotationDiff;
    }
    
    public static <T extends CrystalData> TreeSet<T> asSet(final double exponent, final double diff) {
        return new TreeSet<T>(new RotationComparator(exponent, diff));
    }
    
    @Override
    public int compare(final CrystalData o1, final CrystalData o2) {
        float[] rotations = null;
        double angle1;
        if (o1.hasCachedRotations()) {
            angle1 = o1.getAngle();
        }
        else {
            rotations = new float[] { Thunderhack.rotationManager.getServerYaw(), Thunderhack.rotationManager.getServerPitch() };
            final float[] rotations2 = RotationUtil.getRotations(o1.getCrystal());
            angle1 = RotationUtil.angle(rotations, rotations2);
            o1.cacheRotations(rotations2, angle1);
        }
        double angle2;
        if (o2.hasCachedRotations()) {
            angle2 = o2.getAngle();
        }
        else {
            if (rotations == null) {
                rotations = new float[] { Thunderhack.rotationManager.getServerYaw(), Thunderhack.rotationManager.getServerPitch() };
            }
            final float[] rotations3 = RotationUtil.getRotations(o2.getCrystal());
            angle2 = RotationUtil.angle(rotations, rotations3);
            o2.cacheRotations(rotations3, angle2);
        }
        if (Math.abs(angle1 - angle2) < this.diff) {
            return o1.compareTo(o2);
        }
        angle1 /= 180.0;
        angle2 /= 180.0;
        final float damage1 = o1.getDamage();
        final float damage2 = o2.getDamage();
        final float self1 = o1.getSelfDmg();
        final float self2 = o2.getSelfDmg();
        o1.setDamage((float)(damage1 * Math.pow(1.0 / angle1, this.ex)));
        o2.setDamage((float)(damage2 * Math.pow(1.0 / angle2, this.ex)));
        o1.setSelfDmg((float)(self1 * Math.pow(angle1, this.ex)));
        o2.setSelfDmg((float)(self2 * Math.pow(angle2, this.ex)));
        final int result = o1.compareTo(o2);
        o1.setSelfDmg(self1);
        o2.setSelfDmg(self2);
        o1.setDamage(damage1);
        o2.setDamage(damage2);
        return result;
    }
}
