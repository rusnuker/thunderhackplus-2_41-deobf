//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.surround;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;

public final class ModeUtil
{
    private final Vec3d[] NormalVecArray;
    public final SurroundMode MormalMode;
    private final Vec3d[] StrictModeArray;
    public final SurroundMode StrictMode;
    private final Vec3d[] SemiSafeModeArray;
    public final SurroundMode SemiSafeMode;
    private final Vec3d[] SafeArray;
    public final SurroundMode SafeMode;
    private final Vec3d[] CubicModeArray;
    public final SurroundMode CubicMode;
    private final Vec3d[] HighModeArray;
    public final SurroundMode HighMode;
    public final SurroundMode AntiFacePlaceMode;
    public final SurroundMode DynamicMode;
    
    public ModeUtil() {
        this.NormalVecArray = new Vec3d[] { new Vec3d(1.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 0.0, -1.0) };
        this.MormalMode = new SurroundMode(Surround.Mode.Normal, this.NormalVecArray);
        this.StrictModeArray = new Vec3d[] { new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 0.0, -1.0) };
        this.StrictMode = new SurroundMode(Surround.Mode.Strict, this.StrictModeArray);
        this.SemiSafeModeArray = new Vec3d[] { new Vec3d(1.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(2.0, 0.0, 0.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 2.0), new Vec3d(0.0, 0.0, -2.0) };
        this.SemiSafeMode = new SurroundMode(Surround.Mode.SemiSafe, this.SemiSafeModeArray);
        this.SafeArray = new Vec3d[] { new Vec3d(1.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(1.0, -1.0, 1.0), new Vec3d(1.0, -1.0, -1.0), new Vec3d(-1.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, -1.0), new Vec3d(2.0, 0.0, 0.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 2.0), new Vec3d(0.0, 0.0, -2.0) };
        this.SafeMode = new SurroundMode(Surround.Mode.Safe, this.SafeArray);
        this.CubicModeArray = new Vec3d[] { new Vec3d(1.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(1.0, -1.0, 1.0), new Vec3d(1.0, -1.0, -1.0), new Vec3d(-1.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, -1.0) };
        this.CubicMode = new SurroundMode(Surround.Mode.Cubic, this.CubicModeArray);
        this.HighModeArray = new Vec3d[] { new Vec3d(1.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(0.0, 1.0, -1.0) };
        this.HighMode = new SurroundMode(Surround.Mode.High, this.HighModeArray);
        this.AntiFacePlaceMode = new SurroundMode(Surround.Mode.AntiFacePlace, null);
        this.DynamicMode = new SurroundMode(Surround.Mode.Dynamic, null);
    }
    
    public List<BlockPos> getBlockPositions(final Surround.Mode mode) {
        final Surround surround = (Surround)Thunderhack.moduleManager.getModuleByClass((Class)Surround.class);
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>(64);
        if (mode == this.DynamicMode.name) {
            return (List<BlockPos>)surround.getDynamicPositions();
        }
        if (mode == this.AntiFacePlaceMode.name) {
            return (List<BlockPos>)surround.getAntiFacePlacePositions();
        }
        if (surround.feetBlocks.getValue()) {
            arrayList.addAll(surround.checkHitBoxes((Entity)Util.mc.player, Util.mc.player.posY, -1));
        }
        if (surround.down.getValue()) {
            arrayList.addAll(surround.checkHitBoxes((Entity)Util.mc.player, Util.mc.player.posY, -2));
        }
        final Vec3d vec3d = Util.mc.player.getPositionVector();
        for (final Vec3d vec3d2 : this.getMode(mode).vecArray) {
            final BlockPos blockPos = new BlockPos(vec3d2.add(vec3d));
            if (!(boolean)surround.smartHelping.getValue() || vec3d2.y >= 0.0 || getFacing(blockPos).isEmpty()) {
                arrayList.add(blockPos);
            }
        }
        return arrayList;
    }
    
    public static List<EnumFacing> getFacing(final BlockPos blockPos) {
        final ArrayList<EnumFacing> arrayList = new ArrayList<EnumFacing>();
        if (Util.mc.world == null) {
            return arrayList;
        }
        if (blockPos == null) {
            return arrayList;
        }
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            final BlockPos blockPos2 = blockPos.offset(enumFacing);
            final IBlockState iBlockState = Util.mc.world.getBlockState(blockPos2);
            if (iBlockState != null && iBlockState.getBlock().canCollideCheck(iBlockState, false) && !iBlockState.getMaterial().isReplaceable()) {
                arrayList.add(enumFacing);
            }
        }
        return arrayList;
    }
    
    private SurroundMode getMode(final Surround.Mode mode) {
        final List<SurroundMode> list = new ArrayList<SurroundMode>();
        list.add(this.MormalMode);
        list.add(this.StrictMode);
        list.add(this.SemiSafeMode);
        list.add(this.SafeMode);
        list.add(this.CubicMode);
        list.add(this.HighMode);
        list.add(this.AntiFacePlaceMode);
        list.add(this.DynamicMode);
        for (final SurroundMode mode2 : list) {
            if (mode2.name == mode) {
                return mode2;
            }
        }
        return this.MormalMode;
    }
    
    public static class SurroundMode
    {
        Surround.Mode name;
        Vec3d[] vecArray;
        
        public SurroundMode(final Surround.Mode name, final Vec3d[] vec3dArray) {
            this.name = name;
            this.vecArray = vec3dArray;
        }
    }
}
