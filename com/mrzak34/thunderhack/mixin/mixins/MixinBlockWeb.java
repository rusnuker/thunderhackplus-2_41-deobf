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
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockWeb.class })
public class MixinBlockWeb extends Block
{
    protected MixinBlockWeb() {
        super(Material.WEB);
    }
    
    @Inject(method = { "getCollisionBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBoundingBox(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> cir) {
        if (((SolidWeb)Thunderhack.moduleManager.getModuleByClass((Class)SolidWeb.class)).isEnabled()) {
            cir.setReturnValue((Object)MixinBlockWeb.FULL_BLOCK_AABB);
        }
    }
}
