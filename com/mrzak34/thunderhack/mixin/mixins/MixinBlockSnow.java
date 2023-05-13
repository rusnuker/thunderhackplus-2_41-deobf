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

@Mixin({ BlockSnow.class })
public abstract class MixinBlockSnow extends Block
{
    protected MixinBlockSnow(final Material materialIn) {
        super(materialIn);
    }
    
    @Inject(method = { "getCollisionBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> cir) {
        if (((GroundBoost)Thunderhack.moduleManager.getModuleByClass((Class)GroundBoost.class)).isEnabled()) {
            cir.setReturnValue((Object)null);
        }
    }
}
