//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.render.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;

public class TunnelESP extends Module
{
    private final Setting<Integer> boxAlpha;
    private final Setting<Float> lineWidth;
    private final Setting<ColorSetting> Color1;
    private final Setting<ColorSetting> Color2;
    public Setting<Boolean> box;
    public Setting<Boolean> outline;
    List<BlockPos> tunnelbp;
    int delay;
    
    public TunnelESP() {
        super("TunnelESP", "\u041f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u0442 \u0442\u0443\u043d\u043d\u0435\u043b\u0438", Module.Category.RENDER);
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)125, (T)0, (T)255));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.Color1 = (Setting<ColorSetting>)this.register(new Setting("Color1", (T)new ColorSetting(-2013200640)));
        this.Color2 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-2013200640)));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.tunnelbp = new ArrayList<BlockPos>();
    }
    
    public void onRender3D(final Render3DEvent event) {
        try {
            for (final BlockPos bp : this.tunnelbp) {
                RenderUtil.drawBoxESP(bp, this.Color1.getValue().getColorObject(), this.outline.getValue(), this.Color2.getValue().getColorObject(), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true, 0);
            }
        }
        catch (Exception e) {
            System.out.println("Concurrent exception");
        }
    }
    
    public void onUpdate() {
        if (this.delay++ > 100) {
            int x;
            int z;
            int y;
            new Thread(() -> {
                for (x = (int)(TunnelESP.mc.player.posX - 124.0); x < TunnelESP.mc.player.posX + 124.0; ++x) {
                    for (z = (int)(TunnelESP.mc.player.posZ - 124.0); z < TunnelESP.mc.player.posZ + 124.0; ++z) {
                        for (y = 1; y < 120; ++y) {
                            if (this.one_one(new BlockPos(x, y, z))) {
                                this.tunnelbp.add(new BlockPos(x, y, z));
                            }
                            else if (this.one_two(new BlockPos(x, y, z))) {
                                this.tunnelbp.add(new BlockPos(x, y, z));
                                this.tunnelbp.add(new BlockPos(x, y + 1, z));
                            }
                        }
                    }
                }
                return;
            }).start();
            this.delay = 0;
        }
    }
    
    private boolean one_two(final BlockPos pos) {
        if (this.tunnelbp.contains(pos)) {
            return false;
        }
        if (!BlockUtils.isAir(pos) || !BlockUtils.isAir(pos.up())) {
            return false;
        }
        if (BlockUtils.isAir(pos.down()) || BlockUtils.isAir(pos.up().up())) {
            return false;
        }
        if (BlockUtils.isAir(pos.north()) && BlockUtils.isAir(pos.south()) && BlockUtils.isAir(pos.up().north()) && BlockUtils.isAir(pos.up().south())) {
            return !BlockUtils.isAir(pos.east()) && !BlockUtils.isAir(pos.west()) && !BlockUtils.isAir(pos.up().east()) && !BlockUtils.isAir(pos.up().west());
        }
        return BlockUtils.isAir(pos.east()) && BlockUtils.isAir(pos.west()) && BlockUtils.isAir(pos.up().east()) && BlockUtils.isAir(pos.up().west()) && !BlockUtils.isAir(pos.north()) && !BlockUtils.isAir(pos.south()) && !BlockUtils.isAir(pos.up().north()) && !BlockUtils.isAir(pos.up().south());
    }
    
    private boolean one_one(final BlockPos pos) {
        if (this.tunnelbp.contains(pos)) {
            return false;
        }
        if (!BlockUtils.isAir(pos)) {
            return false;
        }
        if (BlockUtils.isAir(pos.down()) || BlockUtils.isAir(pos.up())) {
            return false;
        }
        if (BlockUtils.isAir(pos.north()) && BlockUtils.isAir(pos.south()) && BlockUtils.isAir(pos.up().north()) && BlockUtils.isAir(pos.up().south())) {
            return !BlockUtils.isAir(pos.east()) && !BlockUtils.isAir(pos.west()) && !BlockUtils.isAir(pos.up().east()) && !BlockUtils.isAir(pos.up().west());
        }
        return BlockUtils.isAir(pos.east()) && BlockUtils.isAir(pos.west()) && BlockUtils.isAir(pos.up().east()) && BlockUtils.isAir(pos.up().west()) && !BlockUtils.isAir(pos.north()) && !BlockUtils.isAir(pos.south()) && !BlockUtils.isAir(pos.up().north()) && !BlockUtils.isAir(pos.up().south());
    }
}
