//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class Search extends Module
{
    public static CopyOnWriteArrayList<BlockVec> blocks;
    public static ArrayList<Block> defaultBlocks;
    public Setting<Boolean> softReload;
    private final Setting<Float> range;
    private final Setting<ColorSetting> color;
    private final Setting<Boolean> illegals;
    private final Setting<Boolean> tracers;
    private final Setting<Boolean> fill;
    private final Setting<Boolean> outline;
    
    public Search() {
        super("Search", "\u043f\u043e\u0434\u0441\u0432\u0435\u0442\u043a\u0430 \u0431\u043b\u043e\u043a\u043e\u0432", Module.Category.RENDER);
        this.softReload = (Setting<Boolean>)this.register(new Setting("SoftReload", (T)true));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)100.0f, (T)1.0f, (T)500.0f));
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-16711681)));
        this.illegals = (Setting<Boolean>)this.register(new Setting("Illegals", (T)true));
        this.tracers = (Setting<Boolean>)this.register(new Setting("Tracers", (T)false));
        this.fill = (Setting<Boolean>)this.register(new Setting("Fill", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
    }
    
    public static void doSoftReload() {
        if (Search.mc.world != null && Search.mc.player != null) {
            final int posX = (int)Search.mc.player.posX;
            final int posY = (int)Search.mc.player.posY;
            final int posZ = (int)Search.mc.player.posZ;
            final int range = Search.mc.gameSettings.renderDistanceChunks * 16;
            Search.mc.renderGlobal.markBlockRangeForRenderUpdate(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range);
        }
    }
    
    public static void renderTracer(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int color) {
        GL11.glPushAttrib(1048575);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        ((IEntityRenderer)Search.mc.entityRenderer).orientCam(Search.mc.getRenderPartialTicks());
        GL11.glEnable(2848);
        GL11.glBegin(1);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glEnd();
        GL11.glPopAttrib();
    }
    
    public void onEnable() {
        if (this.softReload.getValue()) {
            doSoftReload();
        }
    }
    
    @SubscribeEvent
    public void onBlockRender(final BlockRenderEvent event) {
        if (Search.mc.world == null || Search.mc.player == null) {
            return;
        }
        if (Search.blocks.size() > 100000) {
            Search.blocks.clear();
        }
        if (this.shouldAdd(event.getBlock(), event.getPos())) {
            final BlockVec vec = new BlockVec(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
            if (!Search.blocks.contains(vec)) {
                Search.blocks.add(vec);
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (Search.mc.world == null || Search.mc.player == null || Search.blocks.isEmpty()) {
            return;
        }
        if (this.fill.getValue() || this.outline.getValue()) {
            for (final BlockVec vec : Search.blocks) {
                if (vec.getDistance(new BlockVec(Search.mc.player.posX, Search.mc.player.posY, Search.mc.player.posZ)) > this.range.getValue() || !this.shouldRender(vec)) {
                    Search.blocks.remove(vec);
                }
                else {
                    final BlockPos pos = new BlockPos(vec.x, vec.y, vec.z);
                    final AxisAlignedBB axisAlignedBB = Search.mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)Search.mc.world, pos).offset(pos);
                    if (this.fill.getValue()) {
                        TessellatorUtil.prepare();
                        TessellatorUtil.drawBox(axisAlignedBB, this.color.getValue().getColorObject());
                        TessellatorUtil.release();
                    }
                    if (!this.outline.getValue()) {
                        continue;
                    }
                    TessellatorUtil.prepare();
                    TessellatorUtil.drawBoundingBox(axisAlignedBB, 1.5, this.color.getValue().getColorObject());
                    TessellatorUtil.release();
                }
            }
        }
        if (this.tracers.getValue()) {
            for (final BlockVec vec : Search.blocks) {
                if (vec.getDistance(new BlockVec(Search.mc.player.posX, Search.mc.player.posY, Search.mc.player.posZ)) > this.range.getValue() || !this.shouldRender(vec)) {
                    Search.blocks.remove(vec);
                }
                else {
                    final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Search.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Search.mc.player.rotationYaw));
                    renderTracer(eyes.x, eyes.y + Search.mc.player.getEyeHeight(), eyes.z, vec.x - ((IRenderManager)Search.mc.getRenderManager()).getRenderPosX() + 0.5, vec.y - ((IRenderManager)Search.mc.getRenderManager()).getRenderPosY() + 0.5, vec.z - ((IRenderManager)Search.mc.getRenderManager()).getRenderPosZ() + 0.5, this.color.getValue().getColor());
                }
            }
        }
    }
    
    private boolean shouldAdd(final Block block, final BlockPos pos) {
        return Search.defaultBlocks.contains(block) || (this.illegals.getValue() && this.isIllegal(block, pos));
    }
    
    private boolean shouldRender(final BlockVec vec) {
        return Search.defaultBlocks.contains(Search.mc.world.getBlockState(new BlockPos(vec.x, vec.y, vec.z)).getBlock()) || (this.illegals.getValue() && this.isIllegal(Search.mc.world.getBlockState(new BlockPos(vec.x, vec.y, vec.z)).getBlock(), new BlockPos(vec.x, vec.y, vec.z)));
    }
    
    private boolean isIllegal(final Block block, final BlockPos pos) {
        if (block instanceof BlockCommandBlock || block instanceof BlockBarrier) {
            return true;
        }
        if (block != Blocks.BEDROCK) {
            return false;
        }
        if (Search.mc.player.dimension == 0) {
            return pos.getY() > 4;
        }
        return Search.mc.player.dimension != -1 || pos.getY() > 127 || (pos.getY() < 123 && pos.getY() > 4);
    }
    
    static {
        Search.blocks = new CopyOnWriteArrayList<BlockVec>();
        Search.defaultBlocks = new ArrayList<Block>();
    }
    
    private static class BlockVec
    {
        public final double x;
        public final double y;
        public final double z;
        
        public BlockVec(final double x, final double y, final double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        @Override
        public boolean equals(final Object object) {
            if (object instanceof BlockVec) {
                final BlockVec v = (BlockVec)object;
                return Double.compare(this.x, v.x) == 0 && Double.compare(this.y, v.y) == 0 && Double.compare(this.z, v.z) == 0;
            }
            return super.equals(object);
        }
        
        public double getDistance(final BlockVec v) {
            final double dx = this.x - v.x;
            final double dy = this.y - v.y;
            final double dz = this.z - v.z;
            return Math.sqrt(dx * dx + dy * dy + dz * dz);
        }
    }
}
