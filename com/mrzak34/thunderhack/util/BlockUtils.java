//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.util.math.*;
import java.util.*;

public class BlockUtils
{
    public static final List<Block> blackList;
    public static final List<Block> shulkerList;
    private static final Minecraft mc;
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.x - pos.getX());
            final float f2 = (float)(vec.y - pos.getY());
            final float f3 = (float)(vec.z - pos.getZ());
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
        }
        else {
            BlockUtils.mc.playerController.processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, pos, direction, vec, hand);
            BlockUtils.mc.player.swingArm(hand);
        }
        ((IMinecraft)BlockUtils.mc).setRightClickDelayTimer(4);
    }
    
    public static Optional<ClickLocation> generateClickLocation(final BlockPos pos) {
        return generateClickLocation(pos, false, false, false);
    }
    
    public static Optional<ClickLocation> generateClickLocation(final BlockPos pos, final boolean ignoreEntities, final boolean noPistons) {
        return generateClickLocation(pos, ignoreEntities, noPistons, false);
    }
    
    public static double[] calculateLookAt(final double x, final double y, final double z, final EnumFacing facing, final EntityPlayer me) {
        return PlayerUtils.calculateLookAt(x + 0.5 + facing.getDirectionVec().getX() * 0.5, y + 0.5 + facing.getDirectionVec().getY() * 0.5, z + 0.5 + facing.getDirectionVec().getZ() * 0.5, me);
    }
    
    public static Optional<ClickLocation> generateClickLocation(final BlockPos pos, final boolean ignoreEntities, final boolean noPistons, final boolean onlyCrystals) {
        final Block block = BlockUtils.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return Optional.empty();
        }
        if (!ignoreEntities) {
            for (final Entity entity : BlockUtils.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
                if (onlyCrystals && entity instanceof EntityEnderCrystal) {
                    continue;
                }
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityArrow)) {
                    return Optional.empty();
                }
            }
        }
        EnumFacing side = null;
        for (final EnumFacing blockSide : EnumFacing.values()) {
            final BlockPos sidePos = pos.offset(blockSide);
            if (!noPistons || BlockUtils.mc.world.getBlockState(sidePos).getBlock() != Blocks.PISTON) {
                if (BlockUtils.mc.world.getBlockState(sidePos).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(sidePos), false)) {
                    final IBlockState blockState = BlockUtils.mc.world.getBlockState(sidePos);
                    if (!blockState.getMaterial().isReplaceable()) {
                        side = blockSide;
                        break;
                    }
                }
            }
        }
        if (side == null) {
            return Optional.empty();
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtils.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(neighbour), false)) {
            return Optional.empty();
        }
        return Optional.of(new ClickLocation(neighbour, opposite));
    }
    
    public static boolean shouldSneakWhileRightClicking(final BlockPos blockPos) {
        final Block block = BlockUtils.mc.world.getBlockState(blockPos).getBlock();
        TileEntity tileEntity = null;
        for (final TileEntity tE : BlockUtils.mc.world.loadedTileEntityList) {
            if (!tE.getPos().equals((Object)blockPos)) {
                continue;
            }
            tileEntity = tE;
            break;
        }
        return tileEntity != null || block instanceof BlockBed || block instanceof BlockContainer || block instanceof BlockDoor || block instanceof BlockTrapDoor || block instanceof BlockFenceGate || block instanceof BlockButton || block instanceof BlockAnvil || block instanceof BlockWorkbench || block instanceof BlockCake || block instanceof BlockRedstoneDiode;
    }
    
    public static boolean validObi(final BlockPos pos) {
        return !validBedrock(pos) && (BlockUtils.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR;
    }
    
    public static boolean validBedrock(final BlockPos pos) {
        return BlockUtils.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR;
    }
    
    public static BlockPos validTwoBlockObiXZ(final BlockPos pos) {
        if ((BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up(2)).getMaterial() == Material.AIR && (BlockUtils.mc.world.getBlockState(pos.east().down()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east().down()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.east(2)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east(2)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.east().south()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east().south()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.east().north()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east().north()).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos.east()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().up(2)).getMaterial() == Material.AIR) {
            return (validTwoBlockBedrockXZ(pos) == null) ? new BlockPos(1, 0, 0) : null;
        }
        if ((BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up(2)).getMaterial() == Material.AIR && (BlockUtils.mc.world.getBlockState(pos.south().down()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south().down()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.south(2)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south(2)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.south().east()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south().east()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.south().west()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south().west()).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos.south()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().up(2)).getMaterial() == Material.AIR) {
            return (validTwoBlockBedrockXZ(pos) == null) ? new BlockPos(0, 0, 1) : null;
        }
        return null;
    }
    
    public static BlockPos validTwoBlockBedrockXZ(final BlockPos pos) {
        if (BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up(2)).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().down()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east(2)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east().south()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east().north()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().up(2)).getMaterial() == Material.AIR) {
            return new BlockPos(1, 0, 0);
        }
        if (BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up(2)).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().down()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south(2)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south().east()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south().west()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().up(2)).getMaterial() == Material.AIR) {
            return new BlockPos(0, 0, 1);
        }
        return null;
    }
    
    public static boolean isHole(final BlockPos pos) {
        return validObi(pos) || validBedrock(pos);
    }
    
    public static EntityPlayer getRotationPlayer() {
        final EntityPlayer rotationEntity = (EntityPlayer)Util.mc.player;
        return (EntityPlayer)((rotationEntity == null) ? Util.mc.player : rotationEntity);
    }
    
    public static double getDistanceSq(final BlockPos pos) {
        return getDistanceSq((Entity)getRotationPlayer(), pos);
    }
    
    public static double getDistanceSq(final Entity from, final BlockPos to) {
        return from.getDistanceSqToCenter(to);
    }
    
    public static BlockPos getPosition(final Entity entity) {
        return getPosition(entity, 0.0);
    }
    
    public static BlockPos getPosition(final Entity entity, final double yOffset) {
        double y = entity.posY + yOffset;
        if (entity.posY - Math.floor(entity.posY) > 0.5) {
            y = Math.ceil(entity.posY);
        }
        return new BlockPos(entity.posX, y, entity.posZ);
    }
    
    public static double getEyeHeight() {
        return getEyeHeight((Entity)Util.mc.player);
    }
    
    public static double getEyeHeight(final Entity entity) {
        return entity.posY + entity.getEyeHeight();
    }
    
    public static boolean isAir(final BlockPos pos) {
        return BlockUtils.mc.world.getBlockState(pos).getBlock() == Blocks.AIR;
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final ArrayList<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BlockUtils.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState;
                if (!(blockState = BlockUtils.mc.world.getBlockState(neighbour)).getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static Block getBlock(final double x, final double y, final double z) {
        return BlockUtils.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    public static ArrayList<BlockPos> getAllInBox(final BlockPos from, final BlockPos to) {
        final ArrayList<BlockPos> blocks = new ArrayList<BlockPos>();
        final BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        for (int x = min.getX(); x <= max.getX(); ++x) {
            for (int y = min.getY(); y <= max.getY(); ++y) {
                for (int z = min.getZ(); z <= max.getZ(); ++z) {
                    blocks.add(new BlockPos(x, y, z));
                }
            }
        }
        return blocks;
    }
    
    public static EnumFacing getFacing(final BlockPos pos) {
        for (final EnumFacing facing : EnumFacing.values()) {
            final RayTraceResult rayTraceResult = BlockUtils.mc.world.rayTraceBlocks(new Vec3d(BlockUtils.mc.player.posX, BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight(), BlockUtils.mc.player.posZ), new Vec3d(pos.getX() + 0.5 + facing.getDirectionVec().getX() / 2.0, pos.getY() + 0.5 + facing.getDirectionVec().getY() / 2.0, pos.getZ() + 0.5 + facing.getDirectionVec().getZ() / 2.0), false, true, false);
            if (rayTraceResult == null || (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceResult.getBlockPos().equals((Object)pos))) {
                return facing;
            }
        }
        if (pos.getY() > BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight()) {
            return EnumFacing.DOWN;
        }
        return EnumFacing.UP;
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static void rightClickBlock2(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.x - pos.getX());
            final float f2 = (float)(vec.y - pos.getY());
            final float f3 = (float)(vec.z - pos.getZ());
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            BlockUtils.mc.playerController.processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, pos, direction, vec, hand);
        }
        BlockUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
        ((IMinecraft)BlockUtils.mc).setRightClickDelayTimer(4);
    }
    
    public static boolean placeBlockSmartRotate(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking, final EventSync ev) {
        boolean sneaking = false;
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return isSneaking;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = BlockUtils.mc.world.getBlockState(neighbour).getBlock();
        if (!BlockUtils.mc.player.isSneaking() && (BlockUtils.blackList.contains(neighbourBlock) || BlockUtils.shulkerList.contains(neighbourBlock))) {
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            sneaking = true;
        }
        if (rotate) {
            final float[] angle = MathUtil.calcAngle(BlockUtils.mc.player.getPositionEyes(BlockUtils.mc.getRenderPartialTicks()), new Vec3d(hitVec.x, hitVec.y, hitVec.z));
            BlockUtils.mc.player.rotationPitch = angle[1];
            BlockUtils.mc.player.rotationYaw = angle[0];
        }
        rightClickBlock2(neighbour, hitVec, hand, opposite, packet);
        BlockUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
        ((IMinecraft)BlockUtils.mc).setRightClickDelayTimer(4);
        return sneaking || isSneaking;
    }
    
    public static List<BlockPos> getSphere(final float radius, final boolean ignoreAir) {
        final ArrayList<BlockPos> sphere = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(Util.mc.player.getPositionVector());
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        final int radiuss = (int)radius;
        for (int x = posX - radiuss; x <= posX + radius; ++x) {
            for (int z = posZ - radiuss; z <= posZ + radius; ++z) {
                for (int y = posY - radiuss; y < posY + radius; ++y) {
                    final double dist = (posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y);
                    final BlockPos position;
                    if (dist < radius * radius && (Util.mc.world.getBlockState(position = new BlockPos(x, y, z)).getBlock() != Blocks.AIR || !ignoreAir)) {
                        sphere.add(position);
                    }
                }
            }
        }
        return sphere;
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static Block getBlockgs(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return BlockUtils.mc.world.getBlockState(pos);
    }
    
    public static Boolean isPosInFov(final BlockPos pos) {
        final int dirnumber = RotationUtil.getDirection4D();
        if (dirnumber == 0 && pos.getZ() - BlockUtils.mc.player.getPositionVector().z < 0.0) {
            return false;
        }
        if (dirnumber == 1 && pos.getX() - BlockUtils.mc.player.getPositionVector().x > 0.0) {
            return false;
        }
        if (dirnumber == 2 && pos.getZ() - BlockUtils.mc.player.getPositionVector().z > 0.0) {
            return false;
        }
        return dirnumber != 3 || pos.getX() - BlockUtils.mc.player.getPositionVector().x >= 0.0;
    }
    
    static {
        blackList = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE);
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        mc = Minecraft.getMinecraft();
    }
    
    public static class ClickLocation
    {
        public final BlockPos neighbour;
        public final EnumFacing opposite;
        
        public ClickLocation(final BlockPos neighbour, final EnumFacing opposite) {
            this.neighbour = neighbour;
            this.opposite = opposite;
        }
    }
}
