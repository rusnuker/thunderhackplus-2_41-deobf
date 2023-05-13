//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.input.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import net.minecraft.util.math.*;

public class ClickTP extends Module
{
    private final Setting<Float> ass;
    private final Setting<Float> adss;
    private final Setting<Boolean> ground;
    private final Setting<Boolean> spoofs;
    
    public ClickTP() {
        super("ClickTP", "ClickTP", Module.Category.MOVEMENT);
        this.ass = (Setting<Float>)this.register(new Setting("BlockYCorrect", (T)1.0f, (T)(-1.0f), (T)1.0f));
        this.adss = (Setting<Float>)this.register(new Setting("PlayerYCorrect", (T)0.0f, (T)(-1.0f), (T)1.0f));
        this.ground = (Setting<Boolean>)this.register(new Setting("ground", (T)false));
        this.spoofs = (Setting<Boolean>)this.register(new Setting("spoofs", (T)false));
    }
    
    @SubscribeEvent
    public void onMotion(final EventSync e) {
        if (Mouse.isButtonDown(1)) {
            final RayTraceResult ray = ClickTP.mc.player.rayTrace(256.0, ClickTP.mc.getRenderPartialTicks());
            BlockPos pos = null;
            if (ray != null) {
                pos = ray.getBlockPos();
            }
            final EntityPlayer rayTracedEntity = this.getEntityUnderMouse(256);
            if (rayTracedEntity == null && ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
                if (this.spoofs.getValue()) {
                    for (int i = 0; i < 10; ++i) {
                        ClickTP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position((double)pos.getX(), (double)(this.ass.getValue() + pos.getY()), (double)pos.getZ(), (boolean)this.ground.getValue()));
                    }
                }
                ClickTP.mc.player.setPosition((double)pos.getX(), (double)(pos.getY() + this.ass.getValue()), (double)pos.getZ());
            }
            else if (rayTracedEntity != null) {
                final BlockPos bp = new BlockPos((Entity)rayTracedEntity);
                if (this.spoofs.getValue()) {
                    for (int i = 0; i < 10; ++i) {
                        ClickTP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position((double)bp.getX(), (double)(this.adss.getValue() + bp.getY()), (double)bp.getZ(), (boolean)this.ground.getValue()));
                    }
                }
                ClickTP.mc.player.setPosition((double)bp.getX(), (double)(bp.getY() + this.adss.getValue()), (double)bp.getZ());
            }
        }
    }
    
    public void onRender3D(final Render3DEvent event) {
        final EntityPlayer rayTracedEntity = this.getEntityUnderMouse(256);
        final RayTraceResult ray = ClickTP.mc.player.rayTrace(256.0, ClickTP.mc.getRenderPartialTicks());
        if (rayTracedEntity == null) {
            if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
                final BlockPos blockpos = ray.getBlockPos();
                RenderUtil.drawBlockOutline(blockpos, new Color(11008512), 1.0f, false, 0);
            }
        }
        else {
            TessellatorUtil.prepare();
            TessellatorUtil.drawBoundingBox(rayTracedEntity.getEntityBoundingBox(), 3.0, new Color(2817792));
            TessellatorUtil.release();
        }
    }
    
    public EntityPlayer getEntityUnderMouse(final int range) {
        final Entity entity = ClickTP.mc.getRenderViewEntity();
        if (entity != null) {
            Vec3d pos = ClickTP.mc.player.getPositionEyes(1.0f);
            for (float i = 0.0f; i < range; i += 0.5f) {
                pos = pos.add(ClickTP.mc.player.getLookVec().scale(0.5));
                for (final EntityPlayer player : ClickTP.mc.world.playerEntities) {
                    if (player == ClickTP.mc.player) {
                        continue;
                    }
                    AxisAlignedBB bb = player.getEntityBoundingBox();
                    if (bb == null) {
                        continue;
                    }
                    if (player.getDistance((Entity)ClickTP.mc.player) > 6.0f) {
                        bb = bb.grow(0.5);
                    }
                    if (bb.contains(pos)) {
                        return player;
                    }
                }
            }
        }
        return null;
    }
}
