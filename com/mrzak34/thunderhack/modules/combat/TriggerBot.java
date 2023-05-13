//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.util.phobos.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;

public class TriggerBot extends Module
{
    public final Setting<Boolean> criticals;
    public final Setting<Boolean> smartCrit;
    public final Setting<TimingMode> timingMode;
    public final Setting<Integer> minCPS;
    public final Setting<Integer> maxCPS;
    public final Setting<Boolean> randomDelay;
    public final Setting<Float> critdist;
    private final Timer oldTimer;
    
    public TriggerBot() {
        super("TriggerBot", "\u0430\u0442\u0442\u0430\u043a\u0443\u0435\u0442 \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0435\u0439-\u043f\u043e\u0434 \u043f\u0440\u0438\u0446\u0435\u043b\u043e\u043c", Category.COMBAT);
        this.criticals = (Setting<Boolean>)this.register(new Setting("Criticals", (T)true));
        this.smartCrit = (Setting<Boolean>)this.register(new Setting("OnlySpace", (T)true, v -> this.criticals.getValue()));
        this.timingMode = (Setting<TimingMode>)this.register(new Setting("Timing", (T)TimingMode.Default));
        this.minCPS = (Setting<Integer>)this.register(new Setting("MinCPS", (T)10, (T)1, (T)20, v -> this.timingMode.getValue() == TimingMode.Old));
        this.maxCPS = (Setting<Integer>)this.register(new Setting("MaxCPS", (T)12, (T)1, (T)20, v -> this.timingMode.getValue() == TimingMode.Old));
        this.randomDelay = (Setting<Boolean>)this.register(new Setting("RandomDelay", (T)true, v -> this.timingMode.getValue() == TimingMode.Default));
        this.critdist = (Setting<Float>)this.register(new Setting("FallDistance", (T)0.15f, (T)0.0f, (T)1.0f, v -> this.criticals.getValue()));
        this.oldTimer = new Timer();
    }
    
    @SubscribeEvent
    public void onPreMotion(final EventSync e) {
        final Entity entity = TriggerBot.mc.objectMouseOver.entityHit;
        if (this.canAttack(entity)) {
            TriggerBot.mc.playerController.attackEntity((EntityPlayer)TriggerBot.mc.player, entity);
            TriggerBot.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    private boolean canAttack(final Entity entity) {
        if (entity == null) {
            return false;
        }
        if (entity instanceof EntityEnderCrystal) {
            return false;
        }
        final boolean reasonForCancelCritical = TriggerBot.mc.player.isPotionActive(MobEffects.SLOWNESS) || TriggerBot.mc.player.isOnLadder() || Aura.isInLiquid() || ((IEntity)TriggerBot.mc.player).isInWeb() || (this.smartCrit.getValue() && !TriggerBot.mc.gameSettings.keyBindJump.isKeyDown());
        if (this.timingMode.getValue() == TimingMode.Default) {
            if (!this.randomDelay.getValue()) {
                if (this.getCooledAttackStrength() <= 0.93) {
                    return false;
                }
            }
            else {
                final float delay = MathUtil.random(0.85f, 0.1f);
                if (this.getCooledAttackStrength() <= delay) {
                    return false;
                }
            }
        }
        else {
            final int CPS = (int)MathUtil.random(this.minCPS.getValue(), this.maxCPS.getValue());
            if (!this.oldTimer.passedMs((long)((1000.0f + (MathUtil.random(1.0f, 50.0f) - MathUtil.random(1.0f, 60.0f) + MathUtil.random(1.0f, 70.0f))) / CPS))) {
                return false;
            }
        }
        if (this.criticals.getValue() && TriggerBot.mc.world.getBlockState(new BlockPos(TriggerBot.mc.player.posX, TriggerBot.mc.player.posY, TriggerBot.mc.player.posZ)).getBlock() instanceof BlockLiquid && TriggerBot.mc.world.getBlockState(new BlockPos(TriggerBot.mc.player.posX, TriggerBot.mc.player.posY + 1.0, TriggerBot.mc.player.posZ)).getBlock() instanceof BlockAir && TriggerBot.mc.player.fallDistance >= 0.08f) {
            return true;
        }
        if (this.criticals.getValue() && !reasonForCancelCritical) {
            final boolean onFall = Aura.isBlockAboveHead() ? (TriggerBot.mc.player.fallDistance > 0.0f) : (TriggerBot.mc.player.fallDistance >= this.critdist.getValue());
            return onFall && !TriggerBot.mc.player.onGround;
        }
        this.oldTimer.reset();
        return true;
    }
    
    private float getCooledAttackStrength() {
        return MathHelper.clamp((((IEntityLivingBase)TriggerBot.mc.player).getTicksSinceLastSwing() + 1.5f) / this.getCooldownPeriod(), 0.0f, 1.0f);
    }
    
    public float getCooldownPeriod() {
        return (float)(1.0 / TriggerBot.mc.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * (((com.mrzak34.thunderhack.modules.misc.Timer)Thunderhack.moduleManager.getModuleByClass((Class)com.mrzak34.thunderhack.modules.misc.Timer.class)).isOn() ? (20.0f * ((com.mrzak34.thunderhack.modules.misc.Timer)Thunderhack.moduleManager.getModuleByClass((Class)com.mrzak34.thunderhack.modules.misc.Timer.class)).speed.getValue()) : 20.0));
    }
    
    public enum TimingMode
    {
        Default, 
        Old;
    }
}
