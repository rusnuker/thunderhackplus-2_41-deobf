//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.vertex.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class HoleESP extends Module
{
    private final Setting<Integer> rangeXZ;
    private final Setting<Integer> rangeY;
    private final Setting<Float> width;
    private final Setting<Float> height;
    private final Setting<Mode> mode;
    private final Setting<Integer> fadeAlpha;
    private final Setting<Boolean> depth;
    private final Setting<Boolean> noLineDepth;
    private final Setting<Lines> lines;
    private final Setting<Boolean> sides;
    private final Setting<Boolean> notSelf;
    private final Setting<Boolean> twoBlock;
    private final Setting<Boolean> bedrock;
    private final Setting<Boolean> obsidian;
    private final Setting<Boolean> vunerable;
    private final Setting<Boolean> selfVunerable;
    private final Setting<ColorSetting> bRockHoleColor;
    private final Setting<ColorSetting> bRockLineColor;
    private final Setting<ColorSetting> obiHoleColor;
    private final Setting<ColorSetting> obiLineHoleColor;
    private final Setting<ColorSetting> vunerableColor;
    private final Setting<ColorSetting> vunerableLineColor;
    private final List<BlockPos> obiHoles;
    private final List<BlockPos> bedrockHoles;
    private final List<TwoBlockHole> obiHolesTwoBlock;
    private final List<TwoBlockHole> bedrockHolesTwoBlock;
    
    public HoleESP() {
        super("HoleESP", "\u0440\u0435\u043d\u0434\u0435\u0440\u0438\u0442\u044c \u0431\u0435\u0437\u043e\u043f\u0430\u0441\u043d\u044b\u0435-\u0445\u043e\u043b\u043a\u0438", Module.Category.RENDER);
        this.rangeXZ = (Setting<Integer>)this.register(new Setting("RangeXZ", (T)8, (T)1, (T)25));
        this.rangeY = (Setting<Integer>)this.register(new Setting("RangeY", (T)5, (T)1, (T)25));
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)1.5f, (T)0.0f, (T)10.0f));
        this.height = (Setting<Float>)this.register(new Setting("Height", (T)1.0f, (T)(-2.0f), (T)8.0f));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.FULL));
        this.fadeAlpha = (Setting<Integer>)this.register(new Setting("FadeAlpha", (T)0, (T)0, (T)255, v -> this.mode.getValue() == Mode.FADE));
        this.depth = (Setting<Boolean>)this.register(new Setting("Depth", (T)true, v -> this.mode.getValue() == Mode.FADE));
        this.noLineDepth = (Setting<Boolean>)this.register(new Setting("NotLines", (T)true, v -> this.mode.getValue() == Mode.FADE && this.depth.getValue()));
        this.lines = (Setting<Lines>)this.register(new Setting("Lines", (T)Lines.BOTTOM, v -> this.mode.getValue() == Mode.FADE));
        this.sides = (Setting<Boolean>)this.register(new Setting("Sides", (T)false, v -> this.mode.getValue() == Mode.FULL || this.mode.getValue() == Mode.FADE));
        this.notSelf = (Setting<Boolean>)this.register(new Setting("NotSelf", (T)true, v -> this.mode.getValue() == Mode.FADE));
        this.twoBlock = (Setting<Boolean>)this.register(new Setting("TwoBlock", (T)false));
        this.bedrock = (Setting<Boolean>)this.register(new Setting("Bedrock", (T)true));
        this.obsidian = (Setting<Boolean>)this.register(new Setting("Obsidian", (T)true));
        this.vunerable = (Setting<Boolean>)this.register(new Setting("Vulnerable", (T)false));
        this.selfVunerable = (Setting<Boolean>)this.register(new Setting("Self", (T)false));
        this.bRockHoleColor = (Setting<ColorSetting>)this.register(new Setting("bRockHoleColor", (T)new ColorSetting(-2013200640)));
        this.bRockLineColor = (Setting<ColorSetting>)this.register(new Setting("bRockLineColor", (T)new ColorSetting(-1996554240)));
        this.obiHoleColor = (Setting<ColorSetting>)this.register(new Setting("obiHoleColor", (T)new ColorSetting(-2013200640)));
        this.obiLineHoleColor = (Setting<ColorSetting>)this.register(new Setting("obiLineHoleColor", (T)new ColorSetting(-65536)));
        this.vunerableColor = (Setting<ColorSetting>)this.register(new Setting("vunerableColor", (T)new ColorSetting(1727987967)));
        this.vunerableLineColor = (Setting<ColorSetting>)this.register(new Setting("vunerableLineColor", (T)new ColorSetting(-65281)));
        this.obiHoles = new ArrayList<BlockPos>();
        this.bedrockHoles = new ArrayList<BlockPos>();
        this.obiHolesTwoBlock = new ArrayList<TwoBlockHole>();
        this.bedrockHolesTwoBlock = new ArrayList<TwoBlockHole>();
    }
    
    public static void prepareGL2() {
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }
    
    public static void releaseGL2() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static void drawWireframe2(final AxisAlignedBB axisAlignedBB, final int color, final float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f);
        if (axisAlignedBB == null) {
            return;
        }
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static ArrayList<BlockPos> getVulnerablePositions(final BlockPos root) {
        final ArrayList<BlockPos> vP = new ArrayList<BlockPos>();
        if (!(HoleESP.mc.world.getBlockState(root).getBlock() instanceof BlockAir)) {
            return vP;
        }
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (HoleESP.mc.world.getBlockState(root.offset(facing)).getBlock() instanceof BlockAir) {
                return new ArrayList<BlockPos>();
            }
            if (HoleESP.mc.world.getBlockState(root.offset(facing)).getBlock() instanceof BlockObsidian) {
                if (CrystalUtils.canPlaceCrystal(root.offset(facing, 2).down()) && HoleESP.mc.world.getBlockState(root.offset(facing)).getBlock() != Blocks.AIR) {
                    vP.add(root.offset(facing));
                }
                else if (CrystalUtils.canPlaceCrystal(root.offset(facing)) && HoleESP.mc.world.getBlockState(root.offset(facing)).getBlock() != Blocks.AIR && (HoleESP.mc.world.getBlockState(root.offset(facing).down()).getBlock() == Blocks.BEDROCK || HoleESP.mc.world.getBlockState(root.offset(facing).down()).getBlock() == Blocks.OBSIDIAN)) {
                    vP.add(root.offset(facing));
                }
            }
        }
        return vP;
    }
    
    public static void drawBoxOutline(final AxisAlignedBB box, final float red, final float green, final float blue, final float alpha) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, 0.0f).endVertex();
        tessellator.draw();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final Color color) {
        final AxisAlignedBB boundingBox = bb.offset(-HoleESP.mc.getRenderManager().viewerPosX, -HoleESP.mc.getRenderManager().viewerPosY, -HoleESP.mc.getRenderManager().viewerPosZ);
        drawBoxOutline(boundingBox.grow(0.0020000000949949026), (float)(color.getRed() * 255), (float)(color.getGreen() * 255), (float)(color.getBlue() * 255), (float)(color.getAlpha() * 255));
    }
    
    public static void beginRender() {
        GL11.glBlendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void endRender() {
        GlStateManager.resetColor();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }
    
    public void onUpdate() {
        if (HoleESP.mc.world == null || HoleESP.mc.player == null) {
            return;
        }
        this.obiHoles.clear();
        this.bedrockHoles.clear();
        this.obiHolesTwoBlock.clear();
        this.bedrockHolesTwoBlock.clear();
        final Iterable<BlockPos> blocks = (Iterable<BlockPos>)BlockPos.getAllInBox(HoleESP.mc.player.getPosition().add(-this.rangeXZ.getValue(), -this.rangeY.getValue(), -this.rangeXZ.getValue()), HoleESP.mc.player.getPosition().add((int)this.rangeXZ.getValue(), (int)this.rangeY.getValue(), (int)this.rangeXZ.getValue()));
        for (final BlockPos pos : blocks) {
            if (!HoleESP.mc.world.getBlockState(pos).getMaterial().blocksMovement() || !HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement() || !HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial().blocksMovement()) {
                if (BlockUtils.validObi(pos) && this.obsidian.getValue()) {
                    this.obiHoles.add(pos);
                }
                else {
                    final BlockPos validTwoBlock = BlockUtils.validTwoBlockObiXZ(pos);
                    if (validTwoBlock != null && this.obsidian.getValue() && this.twoBlock.getValue()) {
                        this.obiHolesTwoBlock.add(new TwoBlockHole(pos, pos.add(validTwoBlock.getX(), validTwoBlock.getY(), validTwoBlock.getZ())));
                    }
                }
                if (BlockUtils.validBedrock(pos) && this.bedrock.getValue()) {
                    this.bedrockHoles.add(pos);
                }
                else {
                    final BlockPos validTwoBlock = BlockUtils.validTwoBlockBedrockXZ(pos);
                    if (validTwoBlock == null || !this.bedrock.getValue() || !this.twoBlock.getValue()) {
                        continue;
                    }
                    this.bedrockHolesTwoBlock.add(new TwoBlockHole(pos, pos.add(validTwoBlock.getX(), validTwoBlock.getY(), validTwoBlock.getZ())));
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (HoleESP.mc.world == null || HoleESP.mc.player == null) {
            return;
        }
        GL11.glPushAttrib(1048575);
        if (this.mode.getValue() == Mode.BOTTOM) {
            GlStateManager.pushMatrix();
            beginRender();
            GlStateManager.enableBlend();
            GlStateManager.glLineWidth(5.0f);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            for (final BlockPos pos : this.bedrockHoles) {
                final AxisAlignedBB box = new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)pos.getY(), (double)(pos.getZ() + 1));
                drawBoundingBox(box, this.bRockHoleColor.getValue().getColorObject());
            }
            for (final BlockPos pos : this.obiHoles) {
                final AxisAlignedBB box = new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)pos.getY(), (double)(pos.getZ() + 1));
                drawBoundingBox(box, this.obiHoleColor.getValue().getColorObject());
            }
            for (final TwoBlockHole pos2 : this.bedrockHolesTwoBlock) {
                final AxisAlignedBB box = new AxisAlignedBB((double)pos2.getOne().getX(), (double)pos2.getOne().getY(), (double)pos2.getOne().getZ(), (double)(pos2.getExtra().getX() + 1), (double)pos2.getExtra().getY(), (double)(pos2.getExtra().getZ() + 1));
                drawBoundingBox(box, this.bRockHoleColor.getValue().getColorObject());
            }
            for (final TwoBlockHole pos2 : this.obiHolesTwoBlock) {
                final AxisAlignedBB box = new AxisAlignedBB((double)pos2.getOne().getX(), (double)pos2.getOne().getY(), (double)pos2.getOne().getZ(), (double)(pos2.getExtra().getX() + 1), (double)pos2.getExtra().getY(), (double)(pos2.getExtra().getZ() + 1));
                drawBoundingBox(box, this.obiHoleColor.getValue().getColorObject());
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            endRender();
            GlStateManager.popMatrix();
        }
        else {
            for (final BlockPos pos : this.bedrockHoles) {
                this.drawHole(pos, this.bRockHoleColor.getValue(), this.bRockLineColor.getValue());
            }
            for (final BlockPos pos : this.obiHoles) {
                this.drawHole(pos, this.obiHoleColor.getValue(), this.obiLineHoleColor.getValue());
            }
            for (final TwoBlockHole pos2 : this.bedrockHolesTwoBlock) {
                this.drawHoleTwoBlock(pos2.getOne(), pos2.getExtra(), this.bRockHoleColor.getValue(), this.bRockLineColor.getValue());
            }
            for (final TwoBlockHole pos2 : this.obiHolesTwoBlock) {
                this.drawHoleTwoBlock(pos2.getOne(), pos2.getExtra(), this.obiHoleColor.getValue(), this.obiLineHoleColor.getValue());
            }
        }
        if (this.vunerable.getValue()) {
            final List<Entity> targetsInRange = (List<Entity>)HoleESP.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer).filter(e -> e.getDistance((Entity)HoleESP.mc.player) < this.rangeXZ.getValue()).filter(e -> e != HoleESP.mc.player || this.selfVunerable.getValue()).filter(e -> !Thunderhack.friendManager.isFriend(e.getName())).sorted(Comparator.comparing(e -> HoleESP.mc.player.getDistance(e))).collect(Collectors.toList());
            for (final Entity target : targetsInRange) {
                final ArrayList<BlockPos> vuns = getVulnerablePositions(new BlockPos(target));
                for (final BlockPos pos3 : vuns) {
                    final AxisAlignedBB axisAlignedBB = HoleESP.mc.world.getBlockState(pos3).getBoundingBox((IBlockAccess)HoleESP.mc.world, pos3).offset(pos3);
                    TessellatorUtil.prepare();
                    TessellatorUtil.drawBox(axisAlignedBB, true, 1.0, this.vunerableColor.getValue().getColorObject(), this.vunerableColor.getValue().getAlpha(), 63);
                    TessellatorUtil.drawBoundingBox(axisAlignedBB, this.width.getValue(), this.vunerableLineColor.getValue().getColorObject());
                    TessellatorUtil.release();
                }
            }
        }
        GL11.glPopAttrib();
    }
    
    public void drawHole(final BlockPos pos, final ColorSetting color, final ColorSetting lineColor) {
        AxisAlignedBB axisAlignedBB = HoleESP.mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)HoleESP.mc.world, pos).offset(pos);
        axisAlignedBB = axisAlignedBB.setMaxY(axisAlignedBB.minY + this.height.getValue());
        if (this.mode.getValue() == Mode.FULL) {
            TessellatorUtil.prepare();
            TessellatorUtil.drawBox(axisAlignedBB, true, 1.0, color.getColorObject(), color.getAlpha(), ((boolean)this.sides.getValue()) ? 60 : 63);
            TessellatorUtil.release();
        }
        if (this.mode.getValue() == Mode.FULL || this.mode.getValue() == Mode.OUTLINE) {
            TessellatorUtil.prepare();
            TessellatorUtil.drawBoundingBox(axisAlignedBB, this.width.getValue(), lineColor.getColorObject());
            TessellatorUtil.release();
        }
        if (this.mode.getValue() == Mode.WIREFRAME) {
            prepareGL2();
            drawWireframe2(axisAlignedBB.offset(-((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosZ()), lineColor.getColor(), this.width.getValue());
            releaseGL2();
        }
        if (this.mode.getValue() == Mode.FADE) {
            AxisAlignedBB tBB = HoleESP.mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)HoleESP.mc.world, pos).offset(pos);
            tBB = tBB.setMaxY(tBB.minY + this.height.getValue());
            if (HoleESP.mc.player.getEntityBoundingBox() != null && tBB.intersects(HoleESP.mc.player.getEntityBoundingBox()) && this.notSelf.getValue()) {
                tBB = tBB.setMaxY(Math.min(tBB.maxY, HoleESP.mc.player.posY + 1.0));
            }
            TessellatorUtil.prepare();
            if (this.depth.getValue()) {
                GlStateManager.enableDepth();
                tBB = tBB.shrink(0.01);
            }
            TessellatorUtil.drawBox(tBB, true, this.height.getValue(), color.getColorObject(), this.fadeAlpha.getValue(), ((boolean)this.sides.getValue()) ? 60 : 63);
            if (this.width.getValue() >= 0.1f) {
                if (this.lines.getValue() == Lines.BOTTOM) {
                    tBB = new AxisAlignedBB(tBB.minX, tBB.minY, tBB.minZ, tBB.maxX, tBB.minY, tBB.maxZ);
                }
                else if (this.lines.getValue() == Lines.TOP) {
                    tBB = new AxisAlignedBB(tBB.minX, tBB.maxY, tBB.minZ, tBB.maxX, tBB.maxY, tBB.maxZ);
                }
                if (this.noLineDepth.getValue()) {
                    GlStateManager.disableDepth();
                }
                TessellatorUtil.drawBoundingBox(tBB, this.width.getValue(), lineColor.getColorObject(), this.fadeAlpha.getValue());
            }
            TessellatorUtil.release();
        }
    }
    
    public void drawHoleTwoBlock(final BlockPos pos, final BlockPos two, final ColorSetting color, final ColorSetting lineColor) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(two.getX() + 1), (double)(two.getY() + this.height.getValue()), (double)(two.getZ() + 1));
        if (this.mode.getValue() == Mode.FULL) {
            TessellatorUtil.prepare();
            TessellatorUtil.drawBox(axisAlignedBB, true, 1.0, color.getColorObject(), color.getAlpha(), ((boolean)this.sides.getValue()) ? 60 : 63);
            TessellatorUtil.release();
        }
        if (this.mode.getValue() == Mode.FULL || this.mode.getValue() == Mode.OUTLINE) {
            TessellatorUtil.prepare();
            TessellatorUtil.drawBoundingBox(axisAlignedBB, this.width.getValue(), lineColor.getColorObject());
            TessellatorUtil.release();
        }
        if (this.mode.getValue() == Mode.WIREFRAME) {
            prepareGL2();
            drawWireframe2(axisAlignedBB.offset(-((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosZ()), lineColor.getColor(), this.width.getValue());
            releaseGL2();
        }
        if (this.mode.getValue() == Mode.FADE) {
            AxisAlignedBB tBB = new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(two.getX() + 1), (double)(two.getY() + this.height.getValue()), (double)(two.getZ() + 1));
            if (tBB.intersects(HoleESP.mc.player.getEntityBoundingBox()) && this.notSelf.getValue()) {
                tBB = tBB.setMaxY(Math.min(tBB.maxY, HoleESP.mc.player.posY + 1.0));
            }
            TessellatorUtil.prepare();
            if (this.depth.getValue()) {
                GlStateManager.enableDepth();
                tBB = tBB.shrink(0.01);
            }
            TessellatorUtil.drawBox(tBB, true, this.height.getValue(), color.getColorObject(), this.fadeAlpha.getValue(), ((boolean)this.sides.getValue()) ? 60 : 63);
            if (this.width.getValue() >= 0.1f) {
                if (this.lines.getValue() == Lines.BOTTOM) {
                    tBB = new AxisAlignedBB(tBB.minX, tBB.minY, tBB.minZ, tBB.maxX, tBB.minY, tBB.maxZ);
                }
                else if (this.lines.getValue() == Lines.TOP) {
                    tBB = new AxisAlignedBB(tBB.minX, tBB.maxY, tBB.minZ, tBB.maxX, tBB.maxY, tBB.maxZ);
                }
                if (this.noLineDepth.getValue()) {
                    GlStateManager.disableDepth();
                }
                TessellatorUtil.drawBoundingBox(tBB, this.width.getValue(), lineColor.getColorObject(), this.fadeAlpha.getValue());
            }
            TessellatorUtil.release();
        }
    }
    
    private enum Lines
    {
        FULL, 
        BOTTOM, 
        TOP;
    }
    
    private enum Mode
    {
        BOTTOM, 
        OUTLINE, 
        FULL, 
        WIREFRAME, 
        FADE;
    }
    
    private static class TwoBlockHole
    {
        private final BlockPos one;
        private final BlockPos extra;
        
        public TwoBlockHole(final BlockPos one, final BlockPos extra) {
            this.one = one;
            this.extra = extra;
        }
        
        public BlockPos getOne() {
            return this.one;
        }
        
        public BlockPos getExtra() {
            return this.extra;
        }
    }
}
