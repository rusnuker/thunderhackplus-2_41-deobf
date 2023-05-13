//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.movement.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.function.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;
import java.util.stream.*;

public class AutoTrap extends Module
{
    public static ConcurrentHashMap<BlockPos, Long> shiftedBlocks;
    public final Setting<ColorSetting> Color;
    private final Setting<Float> placeRange;
    private final Setting<Boolean> top;
    private final Setting<Boolean> piston;
    private final Setting<SubBind> self;
    Timer renderTimer;
    private final Setting<Integer> actionShift;
    private final Setting<Integer> actionInterval;
    private final Setting<Boolean> strict;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> toggelable;
    private int itemSlot;
    private BlockPos renderPos;
    private int tickCounter;
    private BlockPos playerPos;
    private InteractionUtil.Placement placement;
    private InteractionUtil.Placement lastPlacement;
    private final Timer lastPlacementTimer;
    
    public AutoTrap() {
        super("AutoTrap", "\u0422\u0440\u0430\u043f\u0438\u0442 \u043d\u044c\u044e\u0444\u0430\u0433\u043e\u0432", Category.COMBAT);
        this.Color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.placeRange = (Setting<Float>)this.register(new Setting("TargetRange", (T)4.5f, (T)1.0f, (T)16.0f));
        this.top = (Setting<Boolean>)this.register(new Setting("Top", (T)true));
        this.piston = (Setting<Boolean>)this.register(new Setting("Piston", (T)false));
        this.self = (Setting<SubBind>)this.register(new Setting("Self", (T)new SubBind(0)));
        this.renderTimer = new Timer();
        this.actionShift = (Setting<Integer>)this.register(new Setting("ActionShift", (T)3, (T)1, (T)8));
        this.actionInterval = (Setting<Integer>)this.register(new Setting("ActionInterval", (T)0, (T)0, (T)10));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.toggelable = (Setting<Boolean>)this.register(new Setting("DisableWhenDone", (T)false));
        this.tickCounter = 0;
        this.playerPos = null;
        this.lastPlacementTimer = new Timer();
    }
    
    @Override
    public void onEnable() {
        if (AutoTrap.mc.player == null || AutoTrap.mc.world == null) {
            this.toggle();
            return;
        }
        this.renderPos = null;
        this.playerPos = null;
        this.placement = null;
        this.lastPlacement = null;
        this.tickCounter = this.actionInterval.getValue();
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final EventSync event) {
        if (this.placement != null) {
            this.lastPlacement = this.placement;
            this.lastPlacementTimer.reset();
        }
        this.placement = null;
        this.playerPos = null;
        final int ping = CrystalUtils.ping();
        AutoTrap.shiftedBlocks.forEach((pos, time) -> {
            if (System.currentTimeMillis() - time > ping + 100) {
                AutoTrap.shiftedBlocks.remove(pos);
            }
            return;
        });
        if (event.isCanceled() || !InteractionUtil.canPlaceNormally(this.rotate.getValue())) {
            return;
        }
        if (this.strict.getValue() && (!AutoTrap.mc.player.onGround || !AutoTrap.mc.player.collidedVertically)) {
            return;
        }
        if (((PacketFly)Thunderhack.moduleManager.getModuleByClass((Class)PacketFly.class)).isEnabled()) {
            return;
        }
        if (this.tickCounter < this.actionInterval.getValue()) {
            ++this.tickCounter;
        }
        final int slot = this.getBlockSlot();
        if (slot == -1) {
            Command.sendMessage("No Obby Found!");
            this.toggle();
            return;
        }
        this.itemSlot = slot;
        final EntityPlayer nearestPlayer = this.getNearestTarget();
        if (nearestPlayer == null) {
            return;
        }
        if (this.tickCounter < this.actionInterval.getValue()) {
            if (this.lastPlacement != null && !this.lastPlacementTimer.passedMs(650L)) {
                AutoTrap.mc.player.rotationPitch = this.lastPlacement.getPitch();
                AutoTrap.mc.player.rotationYaw = this.lastPlacement.getYaw();
            }
            return;
        }
        this.playerPos = new BlockPos(nearestPlayer.posX, nearestPlayer.posY, nearestPlayer.posZ);
        final BlockPos firstPos = this.getNextPos(this.playerPos);
        if (firstPos != null) {
            this.placement = InteractionUtil.preparePlacement(firstPos, this.rotate.getValue(), event);
            if (this.placement != null) {
                AutoTrap.shiftedBlocks.put(firstPos, System.currentTimeMillis());
                this.tickCounter = 0;
                this.renderPos = firstPos;
                this.renderTimer.reset();
            }
        }
        else if (this.toggelable.getValue()) {
            this.toggle();
        }
    }
    
    @SubscribeEvent
    public void onPlayerPost(final EventPostSync event) {
        if (this.placement != null && this.playerPos != null && this.itemSlot != -1) {
            final boolean changeItem = AutoTrap.mc.player.inventory.currentItem != this.itemSlot;
            final int startingItem = AutoTrap.mc.player.inventory.currentItem;
            if (changeItem) {
                AutoTrap.mc.player.inventory.currentItem = this.itemSlot;
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.itemSlot));
            }
            final boolean isSprinting = AutoTrap.mc.player.isSprinting();
            final boolean shouldSneak = BlockUtils.shouldSneakWhileRightClicking(this.placement.getNeighbour());
            if (isSprinting) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (shouldSneak) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            InteractionUtil.placeBlock(this.placement, EnumHand.MAIN_HAND, true);
            int extraBlocks = 0;
            while (extraBlocks < this.actionShift.getValue() - 1) {
                final BlockPos nextPos = this.getNextPos(this.playerPos);
                if (nextPos != null) {
                    final InteractionUtil.Placement nextPlacement = InteractionUtil.preparePlacement(nextPos, this.rotate.getValue(), true);
                    if (nextPlacement == null) {
                        break;
                    }
                    this.placement = nextPlacement;
                    AutoTrap.shiftedBlocks.put(nextPos, System.currentTimeMillis());
                    InteractionUtil.placeBlock(this.placement, EnumHand.MAIN_HAND, true);
                    this.renderPos = nextPos;
                    this.renderTimer.reset();
                    ++extraBlocks;
                }
                else {
                    if (this.toggelable.getValue()) {
                        this.toggle();
                        if (changeItem) {
                            AutoTrap.mc.player.inventory.currentItem = startingItem;
                            AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(startingItem));
                        }
                        return;
                    }
                    break;
                }
            }
            if (shouldSneak) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (isSprinting) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            if (changeItem) {
                AutoTrap.mc.player.inventory.currentItem = startingItem;
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(startingItem));
            }
        }
    }
    
    private boolean canPlaceBlock(final BlockPos pos, final boolean strictDirection) {
        return InteractionUtil.canPlaceBlock(pos, strictDirection) && !AutoTrap.shiftedBlocks.containsKey(pos);
    }
    
    private boolean pistonCheck(final BlockPos facePos, final EnumFacing facing) {
        final PistonAura pistonAura = (PistonAura)Thunderhack.moduleManager.getModuleByClass((Class)PistonAura.class);
        if (pistonAura.facePos != null) {
            return !pistonAura.faceOffset.equals((Object)facing);
        }
        pistonAura.evaluateTarget(facePos);
        if (pistonAura.facePos != null) {
            if (pistonAura.faceOffset.equals((Object)facing)) {
                this.resetPA(pistonAura);
                return false;
            }
            this.resetPA(pistonAura);
        }
        return true;
    }
    
    private void resetPA(final PistonAura pistonAura) {
        pistonAura.facePos = null;
        pistonAura.faceOffset = null;
        pistonAura.pistonOffset = null;
        pistonAura.pistonNeighbour = null;
        pistonAura.crystalPos = null;
    }
    
    private BlockPos getNextPos(final BlockPos playerPos) {
        for (final EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            BlockPos furthestBlock = null;
            double furthestDistance = 0.0;
            if (this.canPlaceBlock(playerPos.offset(enumFacing).down(), true)) {
                final BlockPos tempBlock = playerPos.offset(enumFacing).down();
                final double tempDistance = AutoTrap.mc.player.getDistance(tempBlock.getX() + 0.5, tempBlock.getY() + 0.5, tempBlock.getZ() + 0.5);
                if (tempDistance >= furthestDistance) {
                    furthestBlock = tempBlock;
                    furthestDistance = tempDistance;
                }
            }
            if (furthestBlock != null) {
                return furthestBlock;
            }
        }
        for (final EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            BlockPos furthestBlock = null;
            double furthestDistance = 0.0;
            if (this.canPlaceBlock(playerPos.offset(enumFacing), false)) {
                final BlockPos tempBlock = playerPos.offset(enumFacing);
                final double tempDistance = AutoTrap.mc.player.getDistance(tempBlock.getX() + 0.5, tempBlock.getY() + 0.5, tempBlock.getZ() + 0.5);
                if (tempDistance >= furthestDistance) {
                    furthestBlock = tempBlock;
                    furthestDistance = tempDistance;
                }
            }
            if (furthestBlock != null) {
                return furthestBlock;
            }
        }
        for (final EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            BlockPos furthestBlock = null;
            double furthestDistance = 0.0;
            if (this.canPlaceBlock(playerPos.up().offset(enumFacing), false) && (!this.piston.getValue() || this.pistonCheck(playerPos.up(), enumFacing))) {
                final BlockPos tempBlock = playerPos.up().offset(enumFacing);
                final double tempDistance = AutoTrap.mc.player.getDistance(tempBlock.getX() + 0.5, tempBlock.getY() + 0.5, tempBlock.getZ() + 0.5);
                if (tempDistance >= furthestDistance) {
                    furthestBlock = tempBlock;
                    furthestDistance = tempDistance;
                }
            }
            if (furthestBlock != null) {
                return furthestBlock;
            }
        }
        if (this.top.getValue()) {
            final Block baseBlock = AutoTrap.mc.world.getBlockState(playerPos.up().up()).getBlock();
            if (baseBlock instanceof BlockAir || baseBlock instanceof BlockLiquid) {
                if (this.canPlaceBlock(playerPos.up().up(), false)) {
                    return playerPos.up().up();
                }
                final BlockPos offsetPos = playerPos.up().up().offset(EnumFacing.byHorizontalIndex(MathHelper.floor(AutoTrap.mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3));
                if (this.canPlaceBlock(offsetPos, false)) {
                    return offsetPos;
                }
            }
        }
        return null;
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (AutoTrap.mc.world == null || AutoTrap.mc.player == null) {
            return;
        }
        if (this.renderPos != null && !this.renderTimer.passedMs(500L)) {
            RenderUtil.drawBlockOutline(this.renderPos, this.Color.getValue().getColorObject(), 0.3f, true, 0);
        }
    }
    
    private int getBlockSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoTrap.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockObsidian) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    private EntityPlayer getNearestTarget() {
        final Stream<EntityPlayer> stream = (Stream<EntityPlayer>)AutoTrap.mc.world.playerEntities.stream();
        return stream.filter(e -> e != AutoTrap.mc.player && e != AutoTrap.mc.getRenderViewEntity()).filter(e -> !Thunderhack.friendManager.isFriend(e.getName())).filter(e -> AutoTrap.mc.player.getDistance(e) < Math.max(this.placeRange.getValue() - 1.0f, 1.0f)).filter(this::isValidBase).min(Comparator.comparing(e -> AutoTrap.mc.player.getDistance(e))).orElse((EntityPlayer)(PlayerUtils.isKeyDown(this.self.getValue().getKey()) ? AutoTrap.mc.player : null));
    }
    
    private boolean isValidBase(final EntityPlayer player) {
        final BlockPos basePos = new BlockPos(player.posX, player.posY, player.posZ).down();
        final Block baseBlock = AutoTrap.mc.world.getBlockState(basePos).getBlock();
        return !(baseBlock instanceof BlockAir) && !(baseBlock instanceof BlockLiquid);
    }
    
    static {
        AutoTrap.shiftedBlocks = new ConcurrentHashMap<BlockPos, Long>();
    }
}
