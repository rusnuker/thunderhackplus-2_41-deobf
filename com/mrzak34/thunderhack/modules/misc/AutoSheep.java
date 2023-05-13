//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.world.*;

public class AutoSheep extends Module
{
    public Setting<Boolean> Rotate;
    
    public AutoSheep() {
        super("AutoSheep", "\u0441\u0442\u0440\u0435\u0433\u0435\u0442 \u043e\u0432\u0435\u0446", Category.MISC);
        this.Rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final EventSync p_Event) {
        if (!(AutoSheep.mc.player.getHeldItemMainhand().getItem() instanceof ItemShears)) {
            return;
        }
        final EntitySheep l_Sheep = (EntitySheep)AutoSheep.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidSheep(p_Entity)).map(p_Entity -> p_Entity).min(Comparator.comparing(p_Entity -> AutoSheep.mc.player.getDistance(p_Entity))).orElse(null);
        if (l_Sheep != null) {
            if (this.Rotate.getValue()) {
                final float[] angle = calcAngle(AutoSheep.mc.player.getPositionEyes(AutoSheep.mc.getRenderPartialTicks()), l_Sheep.getPositionEyes(AutoSheep.mc.getRenderPartialTicks()));
                AutoSheep.mc.player.rotationYaw = angle[0];
                AutoSheep.mc.player.rotationPitch = angle[1];
            }
            AutoSheep.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)l_Sheep, EnumHand.MAIN_HAND));
        }
    }
    
    private boolean IsValidSheep(final Entity p_Entity) {
        if (!(p_Entity instanceof EntitySheep)) {
            return false;
        }
        if (p_Entity.getDistance((Entity)AutoSheep.mc.player) > 4.0f) {
            return false;
        }
        final EntitySheep l_Sheep = (EntitySheep)p_Entity;
        return l_Sheep.isShearable(AutoSheep.mc.player.getHeldItemMainhand(), (IBlockAccess)AutoSheep.mc.world, p_Entity.getPosition());
    }
}
