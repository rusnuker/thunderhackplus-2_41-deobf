//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.events.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;

public class XRay extends Module
{
    public static int done;
    public static int all;
    public Setting<Boolean> wh;
    public Setting<Boolean> brutForce;
    public Setting<Integer> checkSpeed;
    public Setting<Integer> rxz;
    public Setting<Integer> ry;
    public Setting<Boolean> diamond;
    public Setting<Boolean> gold;
    public Setting<Boolean> iron;
    public Setting<Boolean> emerald;
    public Setting<Boolean> redstone;
    public Setting<Boolean> lapis;
    public Setting<Boolean> coal;
    public Setting<Boolean> wow;
    public Setting<Boolean> water;
    public Setting<Boolean> lava;
    ArrayList<BlockPos> ores;
    ArrayList<BlockPos> toCheck;
    BlockPos verycute;
    private final Setting<mode> Mode;
    
    public XRay() {
        super("XRay", "\u0418\u0441\u043a\u0430\u0442\u044c \u0430\u043b\u043c\u0430\u0437\u044b \u043d\u0430 ezzzzz", Module.Category.MISC);
        this.wh = (Setting<Boolean>)this.register(new Setting("wallhack", (T)false));
        this.brutForce = (Setting<Boolean>)this.register(new Setting("BrutForce", (T)false));
        this.checkSpeed = (Setting<Integer>)this.register(new Setting("checkSpeed", (T)4, (T)1, (T)5, v -> this.brutForce.getValue()));
        this.rxz = (Setting<Integer>)this.register(new Setting("Radius XZ", (T)20, (T)5, (T)200, v -> this.brutForce.getValue()));
        this.ry = (Setting<Integer>)this.register(new Setting("Radius Y", (T)6, (T)2, (T)50, v -> this.brutForce.getValue()));
        this.diamond = (Setting<Boolean>)this.register(new Setting("diamond ", (T)false));
        this.gold = (Setting<Boolean>)this.register(new Setting("gold", (T)false));
        this.iron = (Setting<Boolean>)this.register(new Setting("iron", (T)false));
        this.emerald = (Setting<Boolean>)this.register(new Setting("emerald", (T)false));
        this.redstone = (Setting<Boolean>)this.register(new Setting("redstone", (T)false));
        this.lapis = (Setting<Boolean>)this.register(new Setting("lapis", (T)false));
        this.coal = (Setting<Boolean>)this.register(new Setting("coal", (T)false));
        this.wow = (Setting<Boolean>)this.register(new Setting("WowEffect", (T)true, v -> this.brutForce.getValue()));
        this.water = (Setting<Boolean>)this.register(new Setting("water", (T)false));
        this.lava = (Setting<Boolean>)this.register(new Setting("lava", (T)false));
        this.ores = new ArrayList<BlockPos>();
        this.toCheck = new ArrayList<BlockPos>();
        this.Mode = (Setting<mode>)this.register(new Setting("Render Mode", (T)mode.FullBox));
    }
    
    public void onEnable() {
        this.ores.clear();
        this.toCheck.clear();
        final int radXZ = this.rxz.getValue();
        final int radY = this.ry.getValue();
        final ArrayList<BlockPos> blockPositions = this.getBlocks(radXZ, radY, radXZ);
        for (final BlockPos pos : blockPositions) {
            final IBlockState state = BlockUtils.getState(pos);
            if (!this.isCheckableOre(Block.getIdFromBlock(state.getBlock()))) {
                continue;
            }
            this.toCheck.add(pos);
        }
        XRay.all = this.toCheck.size();
        XRay.done = 0;
        if (this.wh.getValue() && this.brutForce.getValue()) {
            this.wh.setValue(false);
        }
        if (!this.brutForce.getValue()) {
            XRay.mc.renderGlobal.loadRenderers();
        }
    }
    
    public void onDisable() {
        XRay.mc.renderGlobal.loadRenderers();
        super.onDisable();
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final EventSync event) {
        for (int i = 0; i < this.checkSpeed.getValue(); ++i) {
            if (this.toCheck.size() < 1) {
                return;
            }
            final BlockPos pos = this.toCheck.remove(0);
            ++XRay.done;
            XRay.mc.getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
            if (this.wow.getValue()) {
                this.verycute = pos;
            }
        }
    }
    
    @SubscribeEvent
    public void onReceivePacket(final PacketEvent e) {
        if (e.getPacket() instanceof SPacketBlockChange) {
            final SPacketBlockChange p = (SPacketBlockChange)e.getPacket();
            if (this.isEnabledOre(Block.getIdFromBlock(p.getBlockState().getBlock()))) {
                this.ores.add(p.getBlockPosition());
            }
        }
        else if (e.getPacket() instanceof SPacketMultiBlockChange) {
            final SPacketMultiBlockChange p2 = (SPacketMultiBlockChange)e.getPacket();
            for (final SPacketMultiBlockChange.BlockUpdateData dat : p2.getChangedBlocks()) {
                if (this.isEnabledOre(Block.getIdFromBlock(dat.getBlockState().getBlock()))) {
                    this.ores.add(dat.getPos());
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent e) {
        try {
            for (final BlockPos pos : this.ores) {
                final IBlockState state = BlockUtils.getState(pos);
                final Block mat = state.getBlock();
                if (this.Mode.getValue() == mode.FullBox) {
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 56 && this.diamond.getValue() && Block.getIdFromBlock(mat) == 56) {
                        RenderUtil.blockEsp(pos, new Color(0, 255, 255, 50), 1.0, 1.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 14 && this.gold.getValue() && Block.getIdFromBlock(mat) == 14) {
                        RenderUtil.blockEsp(pos, new Color(255, 215, 0, 100), 1.0, 1.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 15 && this.iron.getValue() && Block.getIdFromBlock(mat) == 15) {
                        RenderUtil.blockEsp(pos, new Color(213, 213, 213, 100), 1.0, 1.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 129 && this.emerald.getValue() && Block.getIdFromBlock(mat) == 129) {
                        RenderUtil.blockEsp(pos, new Color(0, 255, 77, 100), 1.0, 1.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 73 && this.redstone.getValue() && Block.getIdFromBlock(mat) == 73) {
                        RenderUtil.blockEsp(pos, new Color(255, 0, 0, 100), 1.0, 1.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 16 && this.coal.getValue() && Block.getIdFromBlock(mat) == 16) {
                        RenderUtil.blockEsp(pos, new Color(0, 0, 0, 100), 1.0, 1.0);
                    }
                    if (Block.getIdFromBlock(mat) == 0 || Block.getIdFromBlock(mat) != 21 || !this.lapis.getValue()) {
                        continue;
                    }
                    if (Block.getIdFromBlock(mat) != 21) {
                        continue;
                    }
                    RenderUtil.blockEsp(pos, new Color(38, 97, 156, 100), 1.0, 1.0);
                }
                else {
                    if (this.Mode.getValue() != mode.Frame) {
                        continue;
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 56 && this.diamond.getValue() && Block.getIdFromBlock(mat) == 56) {
                        RenderUtil.blockEspFrame(pos, 0.0, 255.0, 255.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 14 && this.gold.getValue() && Block.getIdFromBlock(mat) == 14) {
                        RenderUtil.blockEspFrame(pos, 255.0, 215.0, 0.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 15 && this.iron.getValue() && Block.getIdFromBlock(mat) == 15) {
                        RenderUtil.blockEspFrame(pos, 213.0, 213.0, 213.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 129 && this.emerald.getValue() && Block.getIdFromBlock(mat) == 129) {
                        RenderUtil.blockEspFrame(pos, 0.0, 255.0, 77.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 73 && this.redstone.getValue() && Block.getIdFromBlock(mat) == 73) {
                        RenderUtil.blockEspFrame(pos, 255.0, 0.0, 0.0);
                    }
                    if (Block.getIdFromBlock(mat) != 0 && Block.getIdFromBlock(mat) == 16 && this.coal.getValue() && Block.getIdFromBlock(mat) == 16) {
                        RenderUtil.blockEspFrame(pos, 0.0, 0.0, 0.0);
                    }
                    if (Block.getIdFromBlock(mat) == 0 || Block.getIdFromBlock(mat) != 21 || !this.lapis.getValue()) {
                        continue;
                    }
                    if (Block.getIdFromBlock(mat) != 21) {
                        continue;
                    }
                    RenderUtil.blockEspFrame(pos, 38.0, 97.0, 156.0);
                }
            }
            if (this.verycute != null && XRay.done != XRay.all && this.wow.getValue()) {
                RenderUtil.drawBlockOutline(this.verycute, new Color(255, 0, 30), 1.0f, false, 0);
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent e) {
        final String f = "" + XRay.all;
        final String g = "" + XRay.done;
        final ScaledResolution sr = new ScaledResolution(XRay.mc);
        final FontRenderer font = XRay.mc.fontRenderer;
        final int size = 125;
        final float xOffset = sr.getScaledWidth() / 2.0f - size / 2.0f;
        final float yOffset = 5.0f;
        final float Y = 0.0f;
        RenderUtil.rectangleBordered(xOffset + 2.0f, yOffset + 1.0f, xOffset + 10.0f + size + font.getStringWidth(g) + 1.0f, yOffset + size / 6.0f + 3.0f + (font.FONT_HEIGHT + 2.2f), 0.5, 90, 0);
        RenderUtil.rectangleBordered(xOffset + 3.0f, yOffset + 2.0f, xOffset + 10.0f + size + font.getStringWidth(g), yOffset + size / 6.0f + 2.0f + (font.FONT_HEIGHT + 2.2f), 0.5, 27, 61);
        font.drawStringWithShadow("" + ChatFormatting.GREEN + "Done: " + ChatFormatting.WHITE + XRay.done + " / " + ChatFormatting.RED + "All: " + ChatFormatting.WHITE + XRay.all, xOffset + 25.0f, yOffset + font.FONT_HEIGHT + 4.0f, -1);
        GlStateManager.disableBlend();
    }
    
    private boolean isCheckableOre(final int id) {
        int check = 0;
        int check2 = 0;
        int check3 = 0;
        int check4 = 0;
        int check5 = 0;
        int check6 = 0;
        int check7 = 0;
        if (this.diamond.getValue() && id != 0) {
            check = 56;
        }
        if (this.gold.getValue() && id != 0) {
            check2 = 14;
        }
        if (this.iron.getValue() && id != 0) {
            check3 = 15;
        }
        if (this.emerald.getValue() && id != 0) {
            check4 = 129;
        }
        if (this.redstone.getValue() && id != 0) {
            check5 = 73;
        }
        if (this.coal.getValue() && id != 0) {
            check6 = 16;
        }
        if (this.lapis.getValue() && id != 0) {
            check7 = 21;
        }
        return id != 0 && (id == check || id == check2 || id == check3 || id == check4 || id == check5 || id == check6 || id == check7);
    }
    
    private boolean isEnabledOre(final int id) {
        int check = 0;
        int check2 = 0;
        int check3 = 0;
        int check4 = 0;
        int check5 = 0;
        int check6 = 0;
        int check7 = 0;
        if (this.diamond.getValue() && id != 0) {
            check = 56;
        }
        if (this.gold.getValue() && id != 0) {
            check2 = 14;
        }
        if (this.iron.getValue() && id != 0) {
            check3 = 15;
        }
        if (this.emerald.getValue() && id != 0) {
            check4 = 129;
        }
        if (this.redstone.getValue() && id != 0) {
            check5 = 73;
        }
        if (this.coal.getValue() && id != 0) {
            check6 = 16;
        }
        if (this.lapis.getValue() && id != 0) {
            check7 = 21;
        }
        return id != 0 && (id == check || id == check2 || id == check3 || id == check4 || id == check5 || id == check6 || id == check7);
    }
    
    private ArrayList<BlockPos> getBlocks(final int x, final int y, final int z) {
        final BlockPos min = new BlockPos(Util.mc.player.posX - x, Util.mc.player.posY - y, Util.mc.player.posZ - z);
        final BlockPos max = new BlockPos(Util.mc.player.posX + x, Util.mc.player.posY + y, Util.mc.player.posZ + z);
        return BlockUtils.getAllInBox(min, max);
    }
    
    public Boolean shouldRender(final Block cast) {
        if (cast == Blocks.DIAMOND_ORE && this.diamond.getValue()) {
            return true;
        }
        if (cast == Blocks.GOLD_ORE && this.gold.getValue()) {
            return true;
        }
        if (cast == Blocks.WATER && this.water.getValue()) {
            return true;
        }
        if (cast == Blocks.LAVA && this.lava.getValue()) {
            return true;
        }
        if (cast == Blocks.IRON_ORE && this.iron.getValue()) {
            return true;
        }
        if (cast == Blocks.EMERALD_ORE && this.emerald.getValue()) {
            return true;
        }
        if (cast == Blocks.REDSTONE_ORE && this.redstone.getValue()) {
            return true;
        }
        if (cast == Blocks.LAPIS_ORE && this.lapis.getValue()) {
            return true;
        }
        if (cast == Blocks.COAL_ORE && this.coal.getValue()) {
            return true;
        }
        return !this.wh.getValue();
    }
    
    public enum mode
    {
        FullBox, 
        Frame;
    }
}
