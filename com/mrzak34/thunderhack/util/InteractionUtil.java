//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import java.util.*;

public class InteractionUtil
{
    private static final List<Block> shiftBlocks;
    private static final Minecraft mc;
    
    public static boolean canPlaceNormally() {
        return true;
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final EventSync e) {
        return preparePlacement(pos, rotate, false, e);
    }
    
    public static boolean canPlaceNormally(final boolean rotate) {
        return rotate || true;
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final boolean instant, final EventSync e) {
        return preparePlacement(pos, rotate, instant, false, e);
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final boolean instant, final boolean strictDirection, final EventSync e) {
        return preparePlacement(pos, rotate, instant, strictDirection, false, e);
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final boolean instant, final boolean strictDirection, final boolean rayTrace, final EventSync e) {
        EnumFacing side = null;
        Vec3d hitVec = null;
        final double dist = 69420.0;
        for (final EnumFacing facing : getPlacableFacings(pos, strictDirection, rayTrace)) {
            final BlockPos tempNeighbour = pos.offset(facing);
            final Vec3d tempVec = new Vec3d((Vec3i)tempNeighbour).add(0.5, 0.5, 0.5).add(new Vec3d(facing.getDirectionVec()).scale(0.5));
            if (InteractionUtil.mc.player.getPositionVector().add(0.0, (double)InteractionUtil.mc.player.getEyeHeight(), 0.0).distanceTo(tempVec) < dist) {
                side = facing;
                hitVec = tempVec;
            }
        }
        if (side == null) {
            return null;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final float[] angle = SilentRotationUtil.calculateAngle(InteractionUtil.mc.player.getPositionEyes(InteractionUtil.mc.getRenderPartialTicks()), hitVec);
        if (rotate) {
            if (instant) {
                InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], InteractionUtil.mc.player.onGround));
                ((IEntityPlayerSP)InteractionUtil.mc.player).setLastReportedYaw(angle[0]);
                ((IEntityPlayerSP)InteractionUtil.mc.player).setLastReportedPitch(angle[1]);
            }
            else {
                InteractionUtil.mc.player.rotationPitch = angle[1];
                InteractionUtil.mc.player.rotationYaw = angle[0];
            }
        }
        return new Placement(neighbour, opposite, hitVec, angle[0], angle[1]);
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final boolean instant) {
        return preparePlacement(pos, rotate, instant, false);
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final boolean instant, final boolean strictDirection) {
        return preparePlacement(pos, rotate, instant, strictDirection, false);
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final boolean instant, final boolean strictDirection, final boolean rayTrace) {
        EnumFacing side = null;
        Vec3d hitVec = null;
        final double dist = 69420.0;
        for (final EnumFacing facing : getPlacableFacings(pos, strictDirection, rayTrace)) {
            final BlockPos tempNeighbour = pos.offset(facing);
            final Vec3d tempVec = new Vec3d((Vec3i)tempNeighbour).add(0.5, 0.5, 0.5).add(new Vec3d(facing.getDirectionVec()).scale(0.5));
            if (InteractionUtil.mc.player.getPositionVector().add(0.0, (double)InteractionUtil.mc.player.getEyeHeight(), 0.0).distanceTo(tempVec) < dist) {
                side = facing;
                hitVec = tempVec;
            }
        }
        if (side == null) {
            return null;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final float[] angle = SilentRotationUtil.calculateAngle(InteractionUtil.mc.player.getPositionEyes(InteractionUtil.mc.getRenderPartialTicks()), hitVec);
        if (rotate) {
            if (instant) {
                InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], InteractionUtil.mc.player.onGround));
                ((IEntityPlayerSP)InteractionUtil.mc.player).setLastReportedYaw(angle[0]);
                ((IEntityPlayerSP)InteractionUtil.mc.player).setLastReportedPitch(angle[1]);
            }
            else {
                InteractionUtil.mc.player.rotationYaw = angle[0];
                InteractionUtil.mc.player.rotationPitch = angle[1];
            }
        }
        return new Placement(neighbour, opposite, hitVec, angle[0], angle[1]);
    }
    
    public static void placeBlockSafely(final Placement placement, final EnumHand hand, final boolean packet) {
        final boolean isSprinting = InteractionUtil.mc.player.isSprinting();
        final boolean shouldSneak = BlockUtils.shouldSneakWhileRightClicking(placement.getNeighbour());
        if (isSprinting) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
        if (shouldSneak) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        placeBlock(placement, hand, packet);
        if (shouldSneak) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (isSprinting) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }
    
    public static void placeBlock(final Placement placement, final EnumHand hand, final boolean packet) {
        rightClickBlock(placement.getNeighbour(), placement.getHitVec(), hand, placement.getOpposite(), packet, true);
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet, final boolean swing) {
        if (packet) {
            final float dX = (float)(vec.x - pos.getX());
            final float dY = (float)(vec.y - pos.getY());
            final float dZ = (float)(vec.z - pos.getZ());
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, dX, dY, dZ));
        }
        else {
            InteractionUtil.mc.playerController.processRightClickBlock(InteractionUtil.mc.player, InteractionUtil.mc.world, pos, direction, vec, hand);
        }
        if (swing) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
        }
    }
    
    public static boolean canPlaceBlock(final BlockPos pos, final boolean strictDirection) {
        return canPlaceBlock(pos, strictDirection, true);
    }
    
    public static boolean canPlaceBlock(final BlockPos pos, final boolean strictDirection, final boolean checkEntities) {
        return canPlaceBlock(pos, strictDirection, false, checkEntities);
    }
    
    public static boolean canPlaceBlock(final BlockPos pos, final boolean strictDirection, final boolean rayTrace, final boolean checkEntities) {
        final Block block = InteractionUtil.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) {
            return false;
        }
        if (checkEntities) {
            for (final Entity entity : InteractionUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
                if (!(entity instanceof EntityItem)) {
                    if (entity instanceof EntityXPOrb) {
                        continue;
                    }
                    return false;
                }
            }
        }
        for (final EnumFacing side : getPlacableFacings(pos, strictDirection, rayTrace)) {
            if (!canClick(pos.offset(side))) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public static boolean canClick(final BlockPos pos) {
        return InteractionUtil.mc.world.getBlockState(pos).getBlock().canCollideCheck(InteractionUtil.mc.world.getBlockState(pos), false);
    }
    
    public static List<EnumFacing> getPlacableFacings(final BlockPos pos, final boolean strictDirection, final boolean rayTrace) {
        final ArrayList<EnumFacing> validFacings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            Label_0420: {
                if (rayTrace) {
                    final Vec3d testVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5).add(new Vec3d(side.getDirectionVec()).scale(0.5));
                    final RayTraceResult result = InteractionUtil.mc.world.rayTraceBlocks(InteractionUtil.mc.player.getPositionEyes(1.0f), testVec);
                    if (result != null && result.typeOfHit != RayTraceResult.Type.MISS) {
                        System.out.println("weary");
                        break Label_0420;
                    }
                }
                final BlockPos neighbour = pos.offset(side);
                if (strictDirection) {
                    final Vec3d eyePos = InteractionUtil.mc.player.getPositionEyes(1.0f);
                    final Vec3d blockCenter = new Vec3d(neighbour.getX() + 0.5, neighbour.getY() + 0.5, neighbour.getZ() + 0.5);
                    final IBlockState blockState = InteractionUtil.mc.world.getBlockState(neighbour);
                    final boolean isFullBox = blockState.getBlock() == Blocks.AIR || blockState.isFullBlock();
                    final ArrayList<EnumFacing> validAxis = new ArrayList<EnumFacing>();
                    validAxis.addAll(checkAxis(eyePos.x - blockCenter.x, EnumFacing.WEST, EnumFacing.EAST, !isFullBox));
                    validAxis.addAll(checkAxis(eyePos.y - blockCenter.y, EnumFacing.DOWN, EnumFacing.UP, true));
                    validAxis.addAll(checkAxis(eyePos.z - blockCenter.z, EnumFacing.NORTH, EnumFacing.SOUTH, !isFullBox));
                    if (!validAxis.contains(side.getOpposite())) {
                        break Label_0420;
                    }
                }
                final IBlockState blockState2 = InteractionUtil.mc.world.getBlockState(neighbour);
                if (blockState2 != null && blockState2.getBlock().canCollideCheck(blockState2, false)) {
                    if (!blockState2.getMaterial().isReplaceable()) {
                        validFacings.add(side);
                    }
                }
            }
        }
        return validFacings;
    }
    
    public static ArrayList<EnumFacing> checkAxis(final double diff, final EnumFacing negativeSide, final EnumFacing positiveSide, final boolean bothIfInRange) {
        final ArrayList<EnumFacing> valid = new ArrayList<EnumFacing>();
        if (diff < -0.5) {
            valid.add(negativeSide);
        }
        if (diff > 0.5) {
            valid.add(positiveSide);
        }
        if (bothIfInRange) {
            if (!valid.contains(negativeSide)) {
                valid.add(negativeSide);
            }
            if (!valid.contains(positiveSide)) {
                valid.add(positiveSide);
            }
        }
        return valid;
    }
    
    public static void placeBlock(final BlockPos position, final boolean strict) {
        for (final EnumFacing direction : EnumFacing.values()) {
            final BlockPos directionOffset = position.offset(direction);
            for (final Entity entity : InteractionUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position))) {
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    return;
                }
            }
            final EnumFacing oppositeFacing = direction.getOpposite();
            if (!strict || getVisibleSides(directionOffset).contains(direction.getOpposite())) {
                if (!InteractionUtil.mc.world.getBlockState(directionOffset).getMaterial().isReplaceable()) {
                    final boolean sneak = InteractionUtil.shiftBlocks.contains(InteractionUtil.mc.world.getBlockState(directionOffset).getBlock()) && !InteractionUtil.mc.player.isSneaking();
                    if (sneak) {
                        InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    final float[] angle = getAnglesToBlock(directionOffset, oppositeFacing);
                    Vec3d interactVector = null;
                    if (strict) {
                        final RayTraceResult result = getTraceResult(InteractionUtil.mc.playerController.getBlockReachDistance(), angle[0], angle[1]);
                        if (result != null && result.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK)) {
                            interactVector = result.hitVec;
                        }
                    }
                    if (interactVector == null) {
                        interactVector = new Vec3d((Vec3i)directionOffset).add(0.5, 0.5, 0.5);
                    }
                    InteractionUtil.mc.playerController.processRightClickBlock(InteractionUtil.mc.player, InteractionUtil.mc.world, directionOffset, direction.getOpposite(), interactVector, EnumHand.MAIN_HAND);
                    if (sneak) {
                        InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                    break;
                }
            }
        }
    }
    
    public static void placeBlock(final BlockPos position, final boolean strict, final boolean rotate) {
        for (final EnumFacing direction : EnumFacing.values()) {
            final BlockPos directionOffset = position.offset(direction);
            for (final Entity entity : InteractionUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position))) {
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    return;
                }
            }
            final EnumFacing oppositeFacing = direction.getOpposite();
            if (!strict || getVisibleSides(directionOffset).contains(direction.getOpposite())) {
                if (!InteractionUtil.mc.world.getBlockState(directionOffset).getMaterial().isReplaceable()) {
                    final boolean sneak = InteractionUtil.shiftBlocks.contains(InteractionUtil.mc.world.getBlockState(directionOffset).getBlock()) && !InteractionUtil.mc.player.isSneaking();
                    if (sneak) {
                        InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    final float[] angle = getAnglesToBlock(directionOffset, oppositeFacing);
                    if (rotate) {
                        InteractionUtil.mc.player.rotationYaw = angle[0];
                        InteractionUtil.mc.player.rotationPitch = angle[1];
                    }
                    Vec3d interactVector = null;
                    if (strict) {
                        final RayTraceResult result = getTraceResult(InteractionUtil.mc.playerController.getBlockReachDistance(), angle[0], angle[1]);
                        if (result != null && result.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK)) {
                            interactVector = result.hitVec;
                        }
                    }
                    if (interactVector == null) {
                        interactVector = new Vec3d((Vec3i)directionOffset).add(0.5, 0.5, 0.5);
                    }
                    InteractionUtil.mc.playerController.processRightClickBlock(InteractionUtil.mc.player, InteractionUtil.mc.world, directionOffset, direction.getOpposite(), interactVector, EnumHand.MAIN_HAND);
                    if (sneak) {
                        InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                    break;
                }
            }
        }
    }
    
    public static float[] getAnglesToBlock(final BlockPos pos, final EnumFacing facing) {
        final Vec3d diff = new Vec3d(pos.getX() + 0.5 - InteractionUtil.mc.player.posX + facing.getXOffset() / 2.0, pos.getY() + 0.5, pos.getZ() + 0.5 - InteractionUtil.mc.player.posZ + facing.getZOffset() / 2.0);
        final double distance = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
        final float yaw = (float)(Math.atan2(diff.z, diff.x) * 180.0 / 3.141592653589793 - 90.0);
        final float pitch = (float)(Math.atan2(InteractionUtil.mc.player.posY + InteractionUtil.mc.player.getEyeHeight() - diff.y, distance) * 180.0 / 3.141592653589793);
        return new float[] { MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch) };
    }
    
    public static RayTraceResult getTraceResult(final double distance, final float yaw, final float pitch) {
        final Vec3d eyes = InteractionUtil.mc.player.getPositionEyes(1.0f);
        final Vec3d rotationVector = getVectorForRotation(yaw, pitch);
        return InteractionUtil.mc.world.rayTraceBlocks(eyes, eyes.add(rotationVector.x * distance, rotationVector.y * distance, rotationVector.z * distance), false, false, true);
    }
    
    public static Vec3d getVectorForRotation(final float yaw, final float pitch) {
        final float yawCos = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float yawSin = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float pitchCos = -MathHelper.cos(-pitch * 0.017453292f);
        final float pitchSin = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
    }
    
    public static List<EnumFacing> getVisibleSides(final BlockPos position) {
        final List<EnumFacing> visibleSides = new ArrayList<EnumFacing>();
        final Vec3d positionVector = new Vec3d((Vec3i)position).add(0.5, 0.5, 0.5);
        final double facingX = InteractionUtil.mc.player.getPositionEyes(1.0f).x - positionVector.x;
        final double facingY = InteractionUtil.mc.player.getPositionEyes(1.0f).y - positionVector.y;
        final double facingZ = InteractionUtil.mc.player.getPositionEyes(1.0f).z - positionVector.z;
        if (facingX < -0.5) {
            visibleSides.add(EnumFacing.WEST);
        }
        else if (facingX > 0.5) {
            visibleSides.add(EnumFacing.EAST);
        }
        else if (!InteractionUtil.mc.world.getBlockState(position).isFullBlock() || !InteractionUtil.mc.world.isAirBlock(position)) {
            visibleSides.add(EnumFacing.WEST);
            visibleSides.add(EnumFacing.EAST);
        }
        if (facingY < -0.5) {
            visibleSides.add(EnumFacing.DOWN);
        }
        else if (facingY > 0.5) {
            visibleSides.add(EnumFacing.UP);
        }
        else {
            visibleSides.add(EnumFacing.DOWN);
            visibleSides.add(EnumFacing.UP);
        }
        if (facingZ < -0.5) {
            visibleSides.add(EnumFacing.NORTH);
        }
        else if (facingZ > 0.5) {
            visibleSides.add(EnumFacing.SOUTH);
        }
        else if (!InteractionUtil.mc.world.getBlockState(position).isFullBlock() || !InteractionUtil.mc.world.isAirBlock(position)) {
            visibleSides.add(EnumFacing.NORTH);
            visibleSides.add(EnumFacing.SOUTH);
        }
        return visibleSides;
    }
    
    static {
        shiftBlocks = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        mc = Minecraft.getMinecraft();
    }
    
    public static class Placement
    {
        private final BlockPos neighbour;
        private final EnumFacing opposite;
        private final Vec3d hitVec;
        private final float yaw;
        private final float pitch;
        
        public Placement(final BlockPos neighbour, final EnumFacing opposite, final Vec3d hitVec, final float yaw, final float pitch) {
            this.neighbour = neighbour;
            this.opposite = opposite;
            this.hitVec = hitVec;
            this.yaw = yaw;
            this.pitch = pitch;
        }
        
        public BlockPos getNeighbour() {
            return this.neighbour;
        }
        
        public EnumFacing getOpposite() {
            return this.opposite;
        }
        
        public Vec3d getHitVec() {
            return this.hitVec;
        }
        
        public float getYaw() {
            return this.yaw;
        }
        
        public float getPitch() {
            return this.pitch;
        }
    }
}
