//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.potion.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class Jesus extends Module
{
    private final Setting<Mode> mode;
    private final Setting<Boolean> glide;
    private final Setting<Boolean> strict;
    private final Setting<Boolean> boost;
    private boolean jumping;
    private int glideCounter;
    private float lastOffset;
    
    public Jesus() {
        super("Jesus", "Jesus", Module.Category.MOVEMENT);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.SOLID));
        this.glide = (Setting<Boolean>)this.register(new Setting("Glide", (T)false));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.boost = (Setting<Boolean>)this.register(new Setting("Boost", (T)false));
        this.glideCounter = 0;
    }
    
    public static IBlockState checkIfBlockInBB(final Class<? extends Block> blockClass, final int minY) {
        for (int iX = MathHelper.floor(Jesus.mc.player.getEntityBoundingBox().minX); iX < MathHelper.ceil(Jesus.mc.player.getEntityBoundingBox().maxX); ++iX) {
            for (int iZ = MathHelper.floor(Jesus.mc.player.getEntityBoundingBox().minZ); iZ < MathHelper.ceil(Jesus.mc.player.getEntityBoundingBox().maxZ); ++iZ) {
                final IBlockState state = Jesus.mc.world.getBlockState(new BlockPos(iX, minY, iZ));
                if (blockClass.isInstance(state.getBlock())) {
                    return state;
                }
            }
        }
        return null;
    }
    
    public static boolean isInLiquid() {
        if (Jesus.mc.player.fallDistance >= 3.0f) {
            return false;
        }
        boolean inLiquid = false;
        final AxisAlignedBB bb = (Jesus.mc.player.getRidingEntity() != null) ? Jesus.mc.player.getRidingEntity().getEntityBoundingBox() : Jesus.mc.player.getEntityBoundingBox();
        final int y = (int)bb.minY;
        for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; ++x) {
            for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; ++z) {
                final Block block = Jesus.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (!(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    public static boolean isOnLiquid() {
        if (Jesus.mc.player.fallDistance >= 3.0f) {
            return false;
        }
        final AxisAlignedBB bb = (Jesus.mc.player.getRidingEntity() != null) ? Jesus.mc.player.getRidingEntity().getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(0.0, -0.05000000074505806, 0.0) : Jesus.mc.player.getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(0.0, -0.05000000074505806, 0.0);
        boolean onLiquid = false;
        final int y = (int)bb.minY;
        for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX + 1.0); ++x) {
            for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ + 1.0); ++z) {
                final Block block = Jesus.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != Blocks.AIR) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
    
    public void onUpdate() {
        if (this.mode.getValue() != Mode.SOLID) {
            return;
        }
        if (!Jesus.mc.player.movementInput.sneak && !Jesus.mc.player.movementInput.jump && isInLiquid()) {
            Jesus.mc.player.motionY = 0.1;
        }
        if (isOnLiquid() && Jesus.mc.player.fallDistance < 3.0f && !Jesus.mc.player.movementInput.jump && !isInLiquid() && !Jesus.mc.player.isSneaking() && this.glide.getValue()) {
            switch (this.glideCounter) {
                case 0: {
                    final EntityPlayerSP player = Jesus.mc.player;
                    player.motionX *= 1.1;
                    final EntityPlayerSP player2 = Jesus.mc.player;
                    player2.motionZ *= 1.1;
                    break;
                }
                case 1: {
                    final EntityPlayerSP player3 = Jesus.mc.player;
                    player3.motionX *= 1.27;
                    final EntityPlayerSP player4 = Jesus.mc.player;
                    player4.motionZ *= 1.27;
                    break;
                }
                case 2: {
                    final EntityPlayerSP player5 = Jesus.mc.player;
                    player5.motionX *= 1.51;
                    final EntityPlayerSP player6 = Jesus.mc.player;
                    player6.motionZ *= 1.51;
                    break;
                }
                case 3: {
                    final EntityPlayerSP player7 = Jesus.mc.player;
                    player7.motionX *= 1.15;
                    final EntityPlayerSP player8 = Jesus.mc.player;
                    player8.motionZ *= 1.15;
                    break;
                }
                case 4: {
                    final EntityPlayerSP player9 = Jesus.mc.player;
                    player9.motionX *= 1.23;
                    final EntityPlayerSP player10 = Jesus.mc.player;
                    player10.motionZ *= 1.23;
                    break;
                }
            }
            ++this.glideCounter;
            if (this.glideCounter > 4) {
                this.glideCounter = 0;
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            this.glideCounter = 0;
        }
    }
    
    @SubscribeEvent
    public void onLiquidJump(final HandleLiquidJumpEvent event) {
        if (this.mode.getValue() == Mode.NexusCrit || this.mode.getValue() == Mode.NexusFast) {
            return;
        }
        if ((Jesus.mc.player.isInWater() || Jesus.mc.player.isInLava()) && (Jesus.mc.player.motionY == 0.1 || Jesus.mc.player.motionY == 0.5)) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onWalkingPlayerUpdatePre(final EventSync event) {
        if (Jesus.mc.world.getBlockState(new BlockPos(Jesus.mc.player.posX, Jesus.mc.player.posY - 0.25, Jesus.mc.player.posZ)).getBlock() instanceof BlockLiquid && this.mode.getValue() == Mode.NexusCrit) {
            if (Jesus.mc.player.isInWater()) {
                Jesus.mc.player.jump();
                final EntityPlayerSP player = Jesus.mc.player;
                player.motionY /= 1.559999942779541;
                final EntityPlayerSP player2 = Jesus.mc.player;
                player2.motionX /= 2.880000114440918;
                final EntityPlayerSP player3 = Jesus.mc.player;
                player3.motionZ /= 2.880000114440918;
            }
            else if (Jesus.mc.player.fallDistance > 0.24f) {
                Jesus.mc.player.motionY = -0.20000000298023224;
                final EntityPlayerSP player4 = Jesus.mc.player;
                player4.motionY /= 1.559999942779541;
                final EntityPlayerSP player5 = Jesus.mc.player;
                player5.motionX /= 0.8899999856948853;
                final EntityPlayerSP player6 = Jesus.mc.player;
                player6.motionZ /= 0.8899999856948853;
            }
        }
        if (this.mode.getValue() == Mode.NexusFast && (Jesus.mc.world.getBlockState(new BlockPos(Jesus.mc.player.posX, Jesus.mc.player.posY, Jesus.mc.player.posZ)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(Jesus.mc.player.posX, Jesus.mc.player.posY - 1.0, Jesus.mc.player.posZ)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(Jesus.mc.player.posX, Jesus.mc.player.posY - 2.0, Jesus.mc.player.posZ)).getBlock() == Blocks.WATER)) {
            if (Jesus.mc.player.isInWater()) {
                Jesus.mc.player.jump();
                final EntityPlayerSP player7 = Jesus.mc.player;
                player7.motionY /= 1.600000023841858;
                final EntityPlayerSP player8 = Jesus.mc.player;
                player8.motionX /= 4.230000019073486;
                final EntityPlayerSP player9 = Jesus.mc.player;
                player9.motionZ /= 4.230000019073486;
            }
            else if (Jesus.mc.player.fallDistance > 0.0467f) {
                Jesus.mc.player.motionY = -0.18440000712871552;
                final EntityPlayerSP player10 = Jesus.mc.player;
                player10.motionY /= 0.46000000834465027;
                final EntityPlayerSP player11 = Jesus.mc.player;
                player11.motionX /= 0.23000000417232513;
                final EntityPlayerSP player12 = Jesus.mc.player;
                player12.motionZ /= 0.23000000417232513;
            }
            else {
                final EntityPlayerSP player13 = Jesus.mc.player;
                player13.motionX /= 1.5;
                final EntityPlayerSP player14 = Jesus.mc.player;
                player14.motionZ /= 1.5;
            }
        }
        if (this.mode.getValue() == Mode.NCP) {
            final double x = Jesus.mc.player.posX;
            final double y = Jesus.mc.player.posY;
            final double z = Jesus.mc.player.posZ;
            Thunderhack.TICK_TIMER = 1.0f;
            if (Jesus.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(x + 0.3, y, z)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(x - 0.3, y, z)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(x, y, z + 0.3)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(x, y, z - 0.3)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(x + 0.3, y, z + 0.3)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(x - 0.3, y, z - 0.3)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(x - 0.3, y, z + 0.3)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(x + 0.3, y, z - 0.3)).getBlock() == Blocks.WATER || Jesus.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.LAVA || Jesus.mc.world.getBlockState(new BlockPos(x + 0.3, y, z)).getBlock() == Blocks.LAVA || Jesus.mc.world.getBlockState(new BlockPos(x - 0.3, y, z)).getBlock() == Blocks.LAVA || Jesus.mc.world.getBlockState(new BlockPos(x, y, z + 0.3)).getBlock() == Blocks.LAVA || Jesus.mc.world.getBlockState(new BlockPos(x, y, z - 0.3)).getBlock() == Blocks.LAVA || Jesus.mc.world.getBlockState(new BlockPos(x + 0.3, y, z + 0.3)).getBlock() == Blocks.LAVA || Jesus.mc.world.getBlockState(new BlockPos(x - 0.3, y, z - 0.3)).getBlock() == Blocks.LAVA || Jesus.mc.world.getBlockState(new BlockPos(x - 0.3, y, z + 0.3)).getBlock() == Blocks.LAVA || Jesus.mc.world.getBlockState(new BlockPos(x + 0.3, y, z - 0.3)).getBlock() == Blocks.LAVA) {
                if (Jesus.mc.player.movementInput.jump || Jesus.mc.player.collidedHorizontally) {
                    if (Jesus.mc.player.collidedHorizontally) {
                        Jesus.mc.player.setPosition(x, y + 0.2, z);
                    }
                    Jesus.mc.player.onGround = true;
                }
                Jesus.mc.player.motionX = 0.0;
                Jesus.mc.player.motionY = 0.04;
                Jesus.mc.player.motionZ = 0.0;
                if (Jesus.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.LAVA && Jesus.mc.player.fallDistance != 0.0f && Jesus.mc.player.motionX == 0.0 && Jesus.mc.player.motionZ == 0.0) {
                    Jesus.mc.player.setPosition(x, y - 0.0400005, z);
                    if (Jesus.mc.player.fallDistance < 0.08) {
                        Jesus.mc.player.setPosition(x, y + 0.2, z);
                    }
                }
                if (Jesus.mc.player.isPotionActive(Potion.getPotionById(1))) {
                    Jesus.mc.player.jumpMovementFactor = 0.4005f;
                }
                else {
                    Jesus.mc.player.jumpMovementFactor = 0.2865f;
                }
            }
            LegitStrafe.setSpeed((float)MovementUtil.getSpeed());
            if (!Jesus.mc.gameSettings.keyBindJump.isKeyDown() && (Jesus.mc.player.isInWater() || Jesus.mc.player.isInLava())) {
                Jesus.mc.player.motionY = 0.12;
                Thunderhack.TICK_TIMER = 1.5f;
                if (Jesus.mc.player.isInWater() && Jesus.mc.world.getBlockState(new BlockPos(x, y + 0.9, z)).getBlock() == Blocks.WATER && Jesus.mc.world.getBlockState(new BlockPos(x, y + 1.0, z)).getBlock() == Blocks.AIR && Jesus.mc.world.getBlockState(new BlockPos(x, y - 1.0, z)).getBlock() != Blocks.WATER) {
                    final EntityPlayerSP player15 = Jesus.mc.player;
                    player15.posY += 0.1;
                    Jesus.mc.player.motionY = 0.42;
                }
            }
        }
        if (this.mode.getValue() == Mode.TRAMPOLINE) {
            final int minY = MathHelper.floor(Jesus.mc.player.getEntityBoundingBox().minY - 0.2);
            final boolean inLiquid = checkIfBlockInBB((Class<? extends Block>)BlockLiquid.class, minY) != null;
            if (inLiquid && !Jesus.mc.player.isSneaking()) {
                Jesus.mc.player.onGround = false;
            }
            final Block block = Jesus.mc.world.getBlockState(new BlockPos((int)Math.floor(Jesus.mc.player.posX), (int)Math.floor(Jesus.mc.player.posY), (int)Math.floor(Jesus.mc.player.posZ))).getBlock();
            if (this.jumping && !Jesus.mc.player.capabilities.isFlying && !Jesus.mc.player.isInWater()) {
                if (Jesus.mc.player.motionY < -0.3 || Jesus.mc.player.onGround || Jesus.mc.player.isOnLadder()) {
                    this.jumping = false;
                    return;
                }
                Jesus.mc.player.motionY = Jesus.mc.player.motionY / 0.9800000190734863 + 0.08;
                final EntityPlayerSP player16 = Jesus.mc.player;
                player16.motionY -= 0.03120000000005;
            }
            if (Jesus.mc.player.isInWater() || Jesus.mc.player.isInLava()) {
                Jesus.mc.player.motionY = 0.1;
            }
            if (!Jesus.mc.player.isInLava() && (!Jesus.mc.player.isInWater() || this.boost.getValue()) && block instanceof BlockLiquid && Jesus.mc.player.motionY < 0.2) {
                Jesus.mc.player.motionY = 0.5;
                this.jumping = true;
            }
        }
    }
    
    @SubscribeEvent
    public void onLiquidCollision(final JesusEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (this.mode.getValue() == Mode.SOLID && Jesus.mc.world != null && Jesus.mc.player != null && this.checkCollide() && Jesus.mc.player.motionY < 0.10000000149011612 && event.getPos().getY() < Jesus.mc.player.posY - 0.05000000074505806) {
            if (Jesus.mc.player.getRidingEntity() != null) {
                event.setBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.949999988079071, 1.0));
            }
            else {
                event.setBoundingBox(Block.FULL_BLOCK_AABB);
            }
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void sendPacket(final PacketEvent.Send event) {
        if (Jesus.mc.world == null || Jesus.mc.player == null) {
            return;
        }
        if (this.mode.getValue() == Mode.SOLID && event.getPacket() instanceof CPacketPlayer && Jesus.mc.player.ticksExisted > 20 && this.mode.getValue().equals(Mode.SOLID) && Jesus.mc.player.getRidingEntity() == null && !Jesus.mc.gameSettings.keyBindJump.isKeyDown() && Jesus.mc.player.fallDistance < 3.0f) {
            final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            if (isOnLiquid() && !isInLiquid()) {
                ((ICPacketPlayer)packet).setOnGround(false);
                if (this.strict.getValue()) {
                    this.lastOffset += 0.12f;
                    if (this.lastOffset > 0.4f) {
                        this.lastOffset = 0.2f;
                    }
                    ((ICPacketPlayer)packet).setY(packet.getY(Jesus.mc.player.posY) - this.lastOffset);
                }
                else {
                    ((ICPacketPlayer)packet).setY((Jesus.mc.player.ticksExisted % 2 == 0) ? (packet.getY(Jesus.mc.player.posY) - 0.05) : packet.getY(Jesus.mc.player.posY));
                }
            }
        }
    }
    
    private boolean checkCollide() {
        return !Jesus.mc.player.isSneaking() && (Jesus.mc.player.getRidingEntity() == null || Jesus.mc.player.getRidingEntity().fallDistance < 3.0f) && Jesus.mc.player.fallDistance <= 3.0f;
    }
    
    private enum Mode
    {
        SOLID, 
        TRAMPOLINE, 
        NexusCrit, 
        NexusFast, 
        NCP;
    }
}
