//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import com.mrzak34.thunderhack.modules.combat.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;

public class HelperLiquids
{
    private final AutoCrystal module;
    
    public HelperLiquids(final AutoCrystal module) {
        this.module = module;
    }
    
    public static MineSlots getSlots(final boolean onGroundCheck) {
        int bestBlock = -1;
        int bestTool = -1;
        float maxSpeed = 0.0f;
        for (int i = 8; i > -1; --i) {
            final ItemStack stack = Util.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                final int tool = getTool(block);
                final float digSpeed = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, Util.mc.player.inventory.getStackInSlot(tool));
                final float destroySpeed = Util.mc.player.inventory.getStackInSlot(tool).getDestroySpeed(block.getDefaultState());
                final float damage = digSpeed + destroySpeed;
                if (damage > maxSpeed) {
                    bestBlock = i;
                    bestTool = tool;
                    maxSpeed = damage;
                }
            }
        }
        return new MineSlots(bestBlock, bestTool, maxSpeed);
    }
    
    public PlaceData calculate(final HelperPlace placeHelper, final PlaceData placeData, final List<EntityPlayer> friends, final List<EntityPlayer> players, final float minDamage) {
        final PlaceData newData = new PlaceData(minDamage);
        newData.setTarget(placeData.getTarget());
        for (final PositionData data : placeData.getLiquid()) {
            if (placeHelper.validate(placeData, data, friends) != null) {
                placeHelper.calcPositionData(newData, data, players);
            }
        }
        return newData;
    }
    
    private static int getTool(final Block pos) {
        int index = -1;
        float CurrentFastest = 1.0f;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Util.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                final float digSpeed = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
                final float destroySpeed = stack.getDestroySpeed(pos.getDefaultState());
                if (pos instanceof BlockAir) {
                    return 0;
                }
                if (digSpeed + destroySpeed > CurrentFastest) {
                    CurrentFastest = digSpeed + destroySpeed;
                    index = i;
                }
            }
        }
        return index;
    }
    
    public EnumFacing getAbsorbFacing(final BlockPos pos, final List<Entity> entities, final IBlockAccess access, final double placeRange) {
        for (final EnumFacing facing : EnumFacing.values()) {
            if (facing != EnumFacing.DOWN) {
                final BlockPos offset = pos.offset(facing);
                if (BlockUtils.getDistanceSq(offset) < MathUtil.square(placeRange)) {
                    if (access.getBlockState(offset).getMaterial().isReplaceable()) {
                        boolean found = false;
                        final AxisAlignedBB bb = new AxisAlignedBB(offset);
                        for (final Entity entity : entities) {
                            if (entity != null && !EntityUtil.isDead(entity)) {
                                if (!entity.preventEntitySpawning) {
                                    continue;
                                }
                                if (this.module.bbBlockingHelper.blocksBlock(bb, entity)) {
                                    found = true;
                                    break;
                                }
                                continue;
                            }
                        }
                        if (!found) {
                            return facing;
                        }
                    }
                }
            }
        }
        return null;
    }
}
