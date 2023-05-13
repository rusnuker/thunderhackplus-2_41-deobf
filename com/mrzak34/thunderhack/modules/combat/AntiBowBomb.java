//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.math.*;

public class AntiBowBomb extends Module
{
    public final Setting<Boolean> stopa;
    public Setting<Integer> range;
    public Setting<Integer> maxUse;
    EntityPlayer target;
    int old;
    boolean b;
    
    public AntiBowBomb() {
        super("AntiBowBomb", "\u0421\u0442\u0430\u0432\u0438\u0442 \u0449\u0438\u0442 \u0435\u0441\u043b\u0438-\u0432 \u0442\u0435\u0431\u044f \u0446\u0435\u043b\u0438\u0442\u0441\u044f-\u0438\u0433\u0440\u043e\u043a", Category.COMBAT);
        this.stopa = (Setting<Boolean>)this.register(new Setting("StopAura", (T)true));
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)40, (T)0, (T)60));
        this.maxUse = (Setting<Integer>)this.register(new Setting("MaxUse", (T)0, (T)0, (T)20));
    }
    
    @Override
    public void onDisable() {
        this.b = false;
        this.old = -1;
        this.target = null;
    }
    
    public EntityPlayer getTarget(final float range) {
        EntityPlayer currentTarget = null;
        for (int size = AntiBowBomb.mc.world.playerEntities.size(), i = 0; i < size; ++i) {
            final EntityPlayer player = AntiBowBomb.mc.world.playerEntities.get(i);
            if (!this.isntValid((Entity)player, range)) {
                if (currentTarget == null) {
                    currentTarget = player;
                }
                else if (AntiBowBomb.mc.player.getDistanceSq((Entity)player) < AntiBowBomb.mc.player.getDistanceSq((Entity)currentTarget)) {
                    currentTarget = player;
                }
            }
        }
        return currentTarget;
    }
    
    @SubscribeEvent
    public void pnPostSync(final EventPostSync e) {
        this.target = this.getTarget(this.range.getValue());
        if (this.target == null) {
            if (this.b) {
                ((IKeyBinding)AntiBowBomb.mc.gameSettings.keyBindUseItem).setPressed(false);
                if (this.old != -1) {
                    InventoryUtil.swapToHotbarSlot(this.old, false);
                }
                this.target = null;
                this.b = false;
            }
        }
        else {
            this.old = AntiBowBomb.mc.player.inventory.currentItem;
            final int shield = InventoryUtil.findItem(ItemShield.class);
            if (shield == -1) {
                this.target = null;
                return;
            }
            if (Thunderhack.friendManager.isFriend(this.target.getName())) {
                return;
            }
            if (this.target.getItemInUseMaxCount() <= this.maxUse.getValue()) {
                return;
            }
            if (!(this.target.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow) || this.target.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemBow) {
                return;
            }
            if (this.stopa.getValue() && ((TargetStrafe)Thunderhack.moduleManager.getModuleByClass((Class)TargetStrafe.class)).isEnabled()) {
                ((TargetStrafe)Thunderhack.moduleManager.getModuleByClass((Class)TargetStrafe.class)).toggle();
            }
            InventoryUtil.switchToHotbarSlot(shield, false);
            if (AntiBowBomb.mc.player.getHeldItemMainhand().getItem() instanceof ItemShield) {
                ((IKeyBinding)AntiBowBomb.mc.gameSettings.keyBindUseItem).setPressed(true);
                InventoryUtil.swapToHotbarSlot(shield, false);
                SilentRotationUtil.lookAtEntity((Entity)this.target);
                this.b = true;
            }
        }
    }
    
    public boolean isntValid(final Entity entity, final double range) {
        return entity == null || EntityUtil.isDead(entity) || entity.equals((Object)AntiBowBomb.mc.player) || (entity instanceof EntityPlayer && Thunderhack.friendManager.isFriend(entity.getName())) || AntiBowBomb.mc.player.getDistanceSq(entity) > MathUtil.square(range);
    }
}
