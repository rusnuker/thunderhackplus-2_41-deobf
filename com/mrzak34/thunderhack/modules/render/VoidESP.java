//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;

public class VoidESP extends Module
{
    public Setting<Float> range;
    public Setting<Boolean> down;
    private List<BlockPos> holes;
    
    public VoidESP() {
        super("VoidESP", "VoidESP", Module.Category.PLAYER);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)6.0f, (T)3.0f, (T)16.0f));
        this.down = (Setting<Boolean>)this.register(new Setting("Up", (T)false));
        this.holes = new ArrayList<BlockPos>();
    }
    
    public void onUpdate() {
        this.holes = this.calcHoles();
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        for (final BlockPos pos : this.holes) {
            RenderUtil.renderCrosses(((boolean)this.down.getValue()) ? pos.up() : pos, new Color(255, 255, 255), 2.0f);
        }
    }
    
    public List<BlockPos> calcHoles() {
        final ArrayList<BlockPos> voidHoles = new ArrayList<BlockPos>();
        final List<BlockPos> positions = BlockUtils.getSphere(this.range.getValue(), false);
        for (final BlockPos pos : positions) {
            if (pos.getY() == 0) {
                if (VoidESP.mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK) {
                    continue;
                }
                voidHoles.add(pos);
            }
        }
        return voidHoles;
    }
}
