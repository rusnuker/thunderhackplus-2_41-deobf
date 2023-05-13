//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.funnygame;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;
import com.mrzak34.thunderhack.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;

public class FunnyClicker extends Module
{
    public Setting<Integer> chanceval;
    BlockPos bp;
    Timer timer;
    
    public FunnyClicker() {
        super("FunnyClicker", "FunnyClicker", Category.FUNNYGAME);
        this.chanceval = (Setting<Integer>)this.register(new Setting("Chance", (T)100, (T)1, (T)1000));
        this.bp = null;
        this.timer = new Timer();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketOpenWindow) {
            e.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(final EventSync event) {
        for (final TileEntity tileEntity : FunnyClicker.mc.world.loadedTileEntityList) {
            if (FunnyClicker.mc.player.getDistanceSq(new BlockPos((Vec3i)tileEntity.getPos())) > 4.0) {
                continue;
            }
            if (!(tileEntity instanceof TileEntityChest)) {
                continue;
            }
            if (FunnyClicker.mc.player.getDistance((double)tileEntity.getPos().getX(), (double)tileEntity.getPos().getY(), (double)tileEntity.getPos().getZ()) > 8.0) {
                continue;
            }
            if (!this.timer.passedMs(this.chanceval.getValue())) {
                continue;
            }
            this.bp = tileEntity.getPos();
            SilentRotationUtil.lookAtBlock(tileEntity.getPos());
            FunnyClicker.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(tileEntity.getPos(), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            this.timer.reset();
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent e) {
        if (this.bp != null) {
            try {
                RenderUtil.drawBlockOutline(this.bp, new Color(718982), 3.0f, true, 0);
            }
            catch (Exception ex) {}
        }
    }
}
