//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import java.util.*;

public abstract class PositionHistoryChecker
{
    protected boolean checkOldLook;
    protected int ticksToCheck;
    
    public PositionHistoryChecker() {
        this.checkOldLook = true;
        this.ticksToCheck = 10;
    }
    
    protected abstract boolean check(final double p0, final double p1, final double p2, final float p3, final float p4, final int p5, final int p6, final int p7);
    
    public boolean checkFlyingQueue(final double x, final double y, final double z, final float oldYaw, final float oldPitch, final int blockX, final int blockY, final int blockZ, final PositionHistoryHelper history) {
        if (this.checkOldLook && this.check(x, y, z, oldYaw, oldPitch, blockX, blockY, blockZ)) {
            return true;
        }
        final Deque<PositionHistoryHelper.RotationHistory> queue = history.getPackets();
        if (queue.size() == 0) {
            return false;
        }
        int checked = 0;
        for (final PositionHistoryHelper.RotationHistory data : queue) {
            if (data == null) {
                continue;
            }
            if (++checked > 10) {
                break;
            }
            if (!data.hasLook) {
                continue;
            }
            final float yaw = data.yaw;
            final float pitch = data.pitch;
            if (yaw == oldYaw && pitch == oldPitch) {
                continue;
            }
            if (this.check(x, y, z, yaw, pitch, blockX, blockY, blockZ)) {
                return true;
            }
        }
        return false;
    }
}
