//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.modules.funnygame.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockCarpet.class })
public abstract class MixinBlockCarpet extends Block
{
    public MixinBlockCarpet(final Material blockMaterialIn, final MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }
    
    @Inject(method = { "getBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> cir) {
        if (((GroundBoost)Thunderhack.moduleManager.getModuleByClass((Class)GroundBoost.class)).isEnabled()) {
            final AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0, 1.0);
            cir.setReturnValue((Object)CARPET_AABB);
        }
    }
}
