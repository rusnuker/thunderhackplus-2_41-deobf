//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class CivBreaker extends Module
{
    private final Setting<type> targetType;
    private final Setting<mode> breakMode;
    private final Setting<Integer> startDelay;
    private final Setting<Integer> breakDelay;
    private final Setting<Integer> crystalDelay;
    private final Setting<Integer> hitDelay;
    private final Setting<Integer> nosleep;
    private boolean placedCrystal;
    private boolean breaking;
    private boolean broke;
    private EntityPlayer target;
    private BlockPos breakPos;
    private BlockPos placePos;
    private int timer;
    private int attempts;
    
    public CivBreaker() {
        super("CivBreaker", "CivBreaker", Category.COMBAT);
        this.targetType = (Setting<type>)this.register(new Setting("Target", (T)type.NEAREST));
        this.breakMode = (Setting<mode>)this.register(new Setting("Break Mode", (T)mode.Vanilla));
        this.startDelay = (Setting<Integer>)this.register(new Setting("Start Delay", (T)1, (T)0, (T)10));
        this.breakDelay = (Setting<Integer>)this.register(new Setting("Break Delay", (T)1, (T)0, (T)10));
        this.crystalDelay = (Setting<Integer>)this.register(new Setting("Crystal Delay", (T)1, (T)0, (T)10));
        this.hitDelay = (Setting<Integer>)this.register(new Setting("Hit Delay", (T)3, (T)0, (T)10));
        this.nosleep = (Setting<Integer>)this.register(new Setting("Block Delay", (T)3, (T)0, (T)10));
        this.placedCrystal = false;
        this.breaking = false;
        this.broke = false;
        this.target = null;
        this.breakPos = null;
        this.placePos = null;
        this.timer = 0;
        this.attempts = 0;
    }
    
    @Override
    public void onEnable() {
        final int pix = this.findItem(Items.DIAMOND_PICKAXE);
        if (pix != -1) {
            CivBreaker.mc.player.inventory.currentItem = pix;
        }
        this.target = null;
        this.placedCrystal = false;
        this.breaking = false;
        this.broke = false;
        this.timer = 0;
        this.attempts = 0;
    }
    
    @SubscribeEvent
    public void onEntitySync(final EventSync ev) {
        final int pix = this.findItem(Items.DIAMOND_PICKAXE);
        final int crystal = this.findItem(Items.END_CRYSTAL);
        final int obby = this.findMaterials();
        if (pix == -1 || crystal == -1 || obby == -1) {
            Command.sendMessage("No materials!");
            this.disable();
            return;
        }
        if (this.target == null) {
            if (this.targetType.getValue() == type.NEAREST) {
                this.target = (EntityPlayer)Util.mc.world.playerEntities.stream().filter(p -> p.getEntityId() != Util.mc.player.getEntityId()).min(Comparator.comparing(p -> p.getDistance((Entity)Util.mc.player))).orElse(null);
            }
            if (this.target == null) {
                this.disable();
                return;
            }
        }
        this.searchSpace();
        if (!this.placedCrystal) {
            if (this.timer < this.startDelay.getValue()) {
                ++this.timer;
                return;
            }
            this.timer = 0;
            this.doPlace(obby, crystal);
        }
        else if (!this.breaking) {
            if (this.timer < this.breakDelay.getValue()) {
                ++this.timer;
                return;
            }
            this.timer = 0;
            if (this.breakMode.getValue() == mode.Vanilla) {
                Util.mc.player.inventory.currentItem = pix;
                Util.mc.playerController.updateController();
                Util.mc.player.swingArm(EnumHand.MAIN_HAND);
                Util.mc.playerController.onPlayerDamageBlock(this.breakPos, EnumFacing.DOWN);
            }
            else {
                Util.mc.player.swingArm(EnumHand.MAIN_HAND);
                Util.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breakPos, EnumFacing.DOWN));
                Util.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, EnumFacing.DOWN));
            }
            this.breaking = true;
        }
        else if (!this.broke) {
            if (this.getBlock(this.breakPos) == Blocks.AIR) {
                this.broke = true;
            }
        }
        else {
            if (this.timer < this.crystalDelay.getValue()) {
                ++this.timer;
                return;
            }
            this.timer = 0;
            final Entity bcrystal = (Entity)Util.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).min(Comparator.comparing(c -> c.getDistance((Entity)this.target))).orElse(null);
            if (bcrystal == null) {
                if (this.attempts < this.hitDelay.getValue()) {
                    ++this.attempts;
                    return;
                }
                if (this.attempts < this.nosleep.getValue()) {
                    ++this.attempts;
                    return;
                }
                this.placedCrystal = false;
                this.breaking = false;
                this.broke = false;
                this.attempts = 0;
            }
            else {
                final float[] angle = MathUtil.calcAngle(AutoCrystal.mc.player.getPositionEyes(CivBreaker.mc.getRenderPartialTicks()), bcrystal.getPositionVector());
                CivBreaker.mc.player.rotationYaw = angle[0];
                CivBreaker.mc.player.rotationPitch = angle[1];
                Util.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(bcrystal));
                this.placedCrystal = false;
                this.breaking = false;
                this.broke = false;
                this.attempts = 0;
            }
        }
        if (this.breaking && this.breakPos != null) {
            final float[] angle2 = RotationUtil.getRotations(this.breakPos, EnumFacing.DOWN);
            CivBreaker.mc.player.rotationYaw = angle2[0];
            CivBreaker.mc.player.rotationPitch = angle2[1];
        }
    }
    
    private void doPlace(final int obby, final int crystal) {
        if (this.placePos == null) {
            return;
        }
        if (this.getBlock(this.placePos) == Blocks.AIR) {
            final int oldslot = Util.mc.player.inventory.currentItem;
            Util.mc.player.inventory.currentItem = obby;
            Util.mc.playerController.updateController();
            InteractionUtil.placeBlock(this.placePos, true);
            Util.mc.player.inventory.currentItem = oldslot;
        }
        else if (!this.placedCrystal) {
            final int oldslot = Util.mc.player.inventory.currentItem;
            if (crystal != 999) {
                Util.mc.player.inventory.currentItem = crystal;
            }
            Util.mc.playerController.updateController();
            Util.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placePos, EnumFacing.UP, (Util.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            Util.mc.player.inventory.currentItem = oldslot;
            this.placedCrystal = true;
        }
    }
    
    private void searchSpace() {
        final BlockPos tpos = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
        final BlockPos[] offset = { new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1) };
        if (this.getBlock(tpos) != Blocks.AIR || this.getBlock(tpos.add(0, 1, 0)) != Blocks.AIR) {
            return;
        }
        final List<BlockPos> posList = new ArrayList<BlockPos>();
        for (final BlockPos blockPos : offset) {
            final BlockPos offsetPos = tpos.add((Vec3i)blockPos);
            final Block block = this.getBlock(offsetPos);
            final Block block2 = this.getBlock(offsetPos.add(0, 1, 0));
            final Block block3 = this.getBlock(offsetPos.add(0, 2, 0));
            if (block != Blocks.AIR && !(block instanceof BlockLiquid) && block2 != Blocks.BEDROCK && block3 == Blocks.AIR) {
                posList.add(offsetPos);
            }
        }
        final BlockPos base = posList.stream().max(Comparator.comparing(b -> CivBreaker.mc.player.getDistance((double)b.getX(), (double)b.getY(), (double)b.getZ()))).orElse(null);
        if (base == null) {
            return;
        }
        this.placePos = base.add(0, 1, 0);
        this.breakPos = base.add(0, 1, 0);
    }
    
    private int findMaterials() {
        for (int i = 0; i < 9; ++i) {
            if (Util.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && ((ItemBlock)Util.mc.player.inventory.getStackInSlot(i).getItem()).getBlock() == Blocks.OBSIDIAN) {
                return i;
            }
        }
        return -1;
    }
    
    private int findItem(final Item item) {
        if (item == Items.END_CRYSTAL && Util.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            return 999;
        }
        for (int i = 0; i < 9; ++i) {
            if (Util.mc.player.inventory.getStackInSlot(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }
    
    private Block getBlock(final BlockPos b) {
        return Util.mc.world.getBlockState(b).getBlock();
    }
    
    public enum type
    {
        NEAREST, 
        LOOKING;
    }
    
    public enum mode
    {
        Vanilla, 
        Packet;
    }
}
