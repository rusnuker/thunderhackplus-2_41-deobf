//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import java.util.*;
import net.minecraft.util.*;

public final class FaceMasks
{
    public static final HashMap<EnumFacing, Integer> FACEMAP;
    
    static {
        (FACEMAP = new HashMap<EnumFacing, Integer>()).put(EnumFacing.DOWN, 1);
        FaceMasks.FACEMAP.put(EnumFacing.WEST, 16);
        FaceMasks.FACEMAP.put(EnumFacing.NORTH, 4);
        FaceMasks.FACEMAP.put(EnumFacing.SOUTH, 8);
        FaceMasks.FACEMAP.put(EnumFacing.EAST, 32);
        FaceMasks.FACEMAP.put(EnumFacing.UP, 2);
    }
    
    public static final class Quad
    {
        public static final int DOWN = 1;
        public static final int UP = 2;
        public static final int NORTH = 4;
        public static final int SOUTH = 8;
        public static final int WEST = 16;
        public static final int EAST = 32;
        public static final int ALL = 63;
    }
}
