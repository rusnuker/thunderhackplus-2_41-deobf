//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.block.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.modules.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;

@Mixin({ Block.class })
public abstract class MixinBlock
{
    @Shadow
    @Deprecated
    public abstract float getBlockHardness(final IBlockState p0, final World p1, final BlockPos p2);
    
    @Inject(method = { "isFullCube" }, at = { @At("HEAD") }, cancellable = true)
    public void isFullCubeHook(final IBlockState blockState, final CallbackInfoReturnable<Boolean> info) {
        try {
            if (((XRay)Thunderhack.moduleManager.getModuleByClass((Class)XRay.class)).isOn() && ((XRay)Thunderhack.moduleManager.getModuleByClass((Class)XRay.class)).wh.getValue()) {
                info.setReturnValue((Object)((XRay)Thunderhack.moduleManager.getModuleByClass((Class)XRay.class)).shouldRender(Block.class.cast(this)));
            }
        }
        catch (Exception ex) {}
    }
    
    @Shadow
    public abstract BlockStateContainer getBlockState();
    
    @Inject(method = { "addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V" }, at = { @At("HEAD") }, cancellable = true)
    public void addCollisionBoxToListHook(final IBlockState state, final World worldIn, final BlockPos pos, final AxisAlignedBB entityBox, final List<AxisAlignedBB> collidingBoxes, final Entity entityIn, final boolean isActualState, final CallbackInfo info) {
        if (entityIn != null && Util.mc.player != null && entityIn.equals((Object)Util.mc.player) && ((NoClip)Thunderhack.moduleManager.getModuleByClass((Class)NoClip.class)).isOn() && ((NoClip)Thunderhack.moduleManager.getModuleByClass((Class)NoClip.class)).canNoClip() && entityIn.equals((Object)Util.mc.player) && ((NoClip)Thunderhack.moduleManager.getModuleByClass((Class)NoClip.class)).mode.getValue() != NoClip.Mode.CC && (Util.mc.gameSettings.keyBindSneak.isKeyDown() || (!Objects.equals(pos, new BlockPos((Entity)Util.mc.player).add(0, -1, 0)) && !Objects.equals(pos, new BlockPos((Entity)Util.mc.player).add(0, -2, 0))))) {
            info.cancel();
        }
    }
    
    @Inject(method = { "getPlayerRelativeBlockHardness" }, at = { @At("HEAD") }, cancellable = true)
    public void getPlayerRelativeBlockHardness(final IBlockState state, final EntityPlayer player, final World worldIn, final BlockPos pos, final CallbackInfoReturnable<Float> ci) {
        final AutoTool autoTool = (AutoTool)Thunderhack.moduleManager.getModuleByClass((Class)AutoTool.class);
        if (autoTool.isEnabled() && autoTool.silent.getValue()) {
            final float f = state.getBlockHardness(worldIn, pos);
            if (f < 0.0f) {
                ci.setReturnValue((Object)0.0f);
            }
            else {
                ci.setReturnValue((Object)(this.canHarvestBlock(state, player.inventory.getStackInSlot(autoTool.itemIndex)) ? (this.getDigSpeed(state, player.inventory.getStackInSlot(autoTool.itemIndex)) / f / 30.0f) : (this.getDigSpeed(state, player.inventory.getStackInSlot(autoTool.itemIndex)) / f / 100.0f)));
            }
        }
        final NoClip nclip = (NoClip)Thunderhack.moduleManager.getModuleByClass((Class)NoClip.class);
        if (nclip.isEnabled()) {
            final float f2 = state.getBlockHardness(worldIn, pos);
            if (f2 < 0.0f) {
                ci.setReturnValue((Object)0.0f);
            }
            else {
                ci.setReturnValue((Object)(this.canHarvestBlock(state, player.inventory.getStackInSlot(nclip.itemIndex)) ? (this.getDigSpeed(state, player.inventory.getStackInSlot(nclip.itemIndex)) / f2 / 30.0f) : (this.getDigSpeed(state, player.inventory.getStackInSlot(nclip.itemIndex)) / f2 / 100.0f)));
            }
        }
    }
    
    public float getDigSpeed(final IBlockState state, final ItemStack stack) {
        final double str = stack.getDestroySpeed(state);
        final int effect = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        return (float)Math.max(str + ((str > 1.0) ? (effect * effect + 1.0) : 0.0), 0.0);
    }
    
    private boolean canHarvestBlock(final IBlockState state, final ItemStack stack) {
        if (state.getMaterial().isToolNotRequired()) {
            return true;
        }
        final String tool = state.getBlock().getHarvestTool(state);
        if (stack.isEmpty() || tool == null) {
            return Util.mc.player.canHarvestBlock(state);
        }
        final int toolLevel = stack.getItem().getHarvestLevel(stack, tool, (EntityPlayer)Util.mc.player, state);
        if (toolLevel < 0) {
            return Util.mc.player.canHarvestBlock(state);
        }
        return toolLevel >= state.getBlock().getHarvestLevel(state);
    }
}
