//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.mixin.ducks.*;

public class HelperEntityBlocksPlace
{
    private final AutoCrystal module;
    
    public HelperEntityBlocksPlace(final AutoCrystal module) {
        this.module = module;
    }
    
    public boolean blocksBlock(final AxisAlignedBB bb, final Entity entity) {
        if (entity instanceof IEntityPlayer && (int)this.module.blockExtrapol.getValue() != 0) {
            final MotionTracker tracker = ((IEntityPlayer)entity).getBlockMotionTrackerT();
            if (tracker != null && tracker.active) {
                switch ((AutoCrystal.BlockExtrapolationMode)this.module.blockExtraMode.getValue()) {
                    case Extrapolated: {
                        return tracker.getEntityBoundingBox().intersects(bb);
                    }
                    case Pessimistic: {
                        return tracker.getEntityBoundingBox().intersects(bb) || entity.getEntityBoundingBox().intersects(bb);
                    }
                    default: {
                        return tracker.getEntityBoundingBox().intersects(bb) && entity.getEntityBoundingBox().intersects(bb);
                    }
                }
            }
        }
        return entity.getEntityBoundingBox().intersects(bb);
    }
}
