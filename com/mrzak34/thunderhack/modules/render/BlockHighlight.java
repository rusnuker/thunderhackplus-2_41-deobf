//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class BlockHighlight extends Module
{
    public final Setting<ColorSetting> color;
    private final Setting<Float> lineWidth;
    
    public BlockHighlight() {
        super("BlockHighlight", "\u043f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u0442 \u0431\u043b\u043e\u043a \u043d\u0430-\u043a\u043e\u0442\u043e\u0440\u044b\u0439 \u0442\u044b \u0441\u043c\u043e\u0442\u0440\u0438\u0448\u044c", Module.Category.RENDER);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(575714484)));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        final RayTraceResult ray = BlockHighlight.mc.objectMouseOver;
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos blockpos = ray.getBlockPos();
            RenderUtil.drawBlockOutline(blockpos, this.color.getValue().getColorObject(), this.lineWidth.getValue(), false, 0);
        }
    }
}
