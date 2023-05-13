//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.world.*;
import java.util.*;

public class ExtrapolationHelper
{
    public ExtrapolationHelper(final AutoCrystal module) {
    }
    
    public static void onUpdateEntity(final UpdateEntitiesEvent e) {
        for (final EntityPlayer player : Util.mc.world.playerEntities) {
            MotionTracker tracker = ((IEntityPlayer)player).getMotionTrackerT();
            MotionTracker breakTracker = ((IEntityPlayer)player).getBreakMotionTrackerT();
            MotionTracker blockTracker = ((IEntityPlayer)player).getBlockMotionTrackerT();
            if (player.getHealth() <= 0.0f || Util.mc.player.getDistanceSq((Entity)player) > 400.0 || (!(boolean)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).selfExtrapolation.getValue() && player.equals((Object)Util.mc.player))) {
                if (tracker != null) {
                    tracker.active = false;
                }
                if (breakTracker != null) {
                    breakTracker.active = false;
                }
                if (blockTracker == null) {
                    continue;
                }
                blockTracker.active = false;
            }
            else {
                if (tracker == null && (int)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).extrapol.getValue() != 0) {
                    tracker = new MotionTracker((World)Util.mc.world, player);
                    ((IEntityPlayer)player).setMotionTrackerT(tracker);
                }
                if (breakTracker == null && (int)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).bExtrapol.getValue() != 0) {
                    breakTracker = new MotionTracker((World)Util.mc.world, player);
                    ((IEntityPlayer)player).setBreakMotionTrackerT(breakTracker);
                }
                if (blockTracker == null && (int)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).blockExtrapol.getValue() != 0) {
                    blockTracker = new MotionTracker((World)Util.mc.world, player);
                    ((IEntityPlayer)player).setBlockMotionTrackerT(blockTracker);
                }
                updateTracker(tracker, (int)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).extrapol.getValue());
                updateTracker(breakTracker, (int)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).bExtrapol.getValue());
                updateTracker(blockTracker, (int)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).blockExtrapol.getValue());
            }
        }
    }
    
    private static void updateTracker(final MotionTracker tracker, final int ticks) {
        if (tracker == null) {
            return;
        }
        tracker.active = false;
        tracker.copyLocationAndAnglesFrom((Entity)tracker.tracked);
        tracker.gravity = (boolean)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).gravityExtrapolation.getValue();
        tracker.gravityFactor = (double)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).gravityFactor.getValue();
        tracker.yPlusFactor = (double)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).yPlusFactor.getValue();
        tracker.yMinusFactor = (double)((AutoCrystal)Thunderhack.moduleManager.getModuleByClass((Class)AutoCrystal.class)).yMinusFactor.getValue();
        tracker.ticks = 0;
        while (tracker.ticks < ticks) {
            tracker.updateFromTrackedEntity();
            ++tracker.ticks;
        }
        tracker.active = true;
    }
    
    public MotionTracker getTrackerFromEntity(final Entity player) {
        return ((IEntityPlayer)player).getMotionTrackerT();
    }
    
    public MotionTracker getBreakTrackerFromEntity(final Entity player) {
        return ((IEntityPlayer)player).getBreakMotionTrackerT();
    }
    
    public MotionTracker getBlockTracker(final Entity player) {
        return ((IEntityPlayer)player).getBlockMotionTrackerT();
    }
}
