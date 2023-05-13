//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.util.render.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;

public class PistonAura extends Module
{
    private static PistonAura INSTANCE;
    public final Setting<ColorSetting> arrowColor;
    public final Setting<ColorSetting> outlineColorCurrent;
    public final Setting<ColorSetting> colorFull;
    public final Setting<ColorSetting> outlineColorFull;
    public final Setting<ColorSetting> colorCurrent;
    public Setting<Boolean> render;
    public BlockPos facePos;
    public EnumFacing faceOffset;
    public BlockPos crystalPos;
    public BlockPos pistonNeighbour;
    public EnumFacing pistonOffset;
    private final Setting<Mode> mode;
    private final Setting<Boolean> smart;
    private final Setting<Boolean> triggerable;
    private final Setting<Boolean> disableWhenNone;
    private final Setting<Integer> targetRange;
    private final Setting<Integer> breakDelay;
    private final Setting<Integer> actionShift;
    private final Setting<Integer> actionInterval;
    private final Setting<Boolean> strict;
    private final Setting<Boolean> raytrace;
    private final Setting<Boolean> antiSuicide;
    private final Setting<Boolean> mine;
    private final Setting<Boolean> protocol;
    private final Setting<Boolean> ccrys;
    private final Setting<Boolean> renderCurrent;
    private final Setting<Boolean> renderFull;
    private final Setting<Boolean> arrow;
    private final Setting<Boolean> bottomArrow;
    private final Setting<Boolean> topArrow;
    private Stage stage;
    private BlockPos torchPos;
    private final Timer torchTimer;
    private boolean skipPiston;
    private final Timer delayTimer;
    private int delayTime;
    private final Timer renderTimer;
    private Runnable postAction;
    private int tickCounter;
    private BlockPos placedPiston;
    private final Timer placedPistonTimer;
    private final Timer actionTimer;
    
    public PistonAura() {
        super("PistonAura", "\u041f\u043e\u0440\u0448\u043d\u0438 \u0432\u0442\u0430\u043b\u043a\u0438\u0432\u0430\u044e\u0442-\u043a\u0440\u0438\u0441\u0442\u0430\u043b \u0432 \u0447\u0435\u043b\u0430-(\u041e\u0445\u0443\u0435\u043d\u043d\u0430\u044f \u0445\u0443\u0439\u043d\u044f)", Category.COMBAT);
        this.arrowColor = (Setting<ColorSetting>)this.register(new Setting("Arrow Color", (T)new ColorSetting(-2009289807)));
        this.outlineColorCurrent = (Setting<ColorSetting>)this.register(new Setting("Outline Color", (T)new ColorSetting(-1323314462)));
        this.colorFull = (Setting<ColorSetting>)this.register(new Setting("Full Color", (T)new ColorSetting(-2011093215)));
        this.outlineColorFull = (Setting<ColorSetting>)this.register(new Setting("OutlineColorF", (T)new ColorSetting(-2009289807)));
        this.colorCurrent = (Setting<ColorSetting>)this.register(new Setting("ColorCurrent", (T)new ColorSetting(-1323314462)));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.DAMAGE));
        this.smart = (Setting<Boolean>)this.register(new Setting("Smart", (T)true, v -> this.mode.getValue() == Mode.PUSH));
        this.triggerable = (Setting<Boolean>)this.register(new Setting("DisablePush", (T)true, v -> this.mode.getValue() == Mode.PUSH));
        this.disableWhenNone = (Setting<Boolean>)this.register(new Setting("DisableNone", (T)false, v -> this.mode.getValue() == Mode.DAMAGE));
        this.targetRange = (Setting<Integer>)this.register(new Setting("TargetRange", (T)3, (T)1, (T)6));
        this.breakDelay = (Setting<Integer>)this.register(new Setting("Delay", (T)25, (T)0, (T)100));
        this.actionShift = (Setting<Integer>)this.register(new Setting("ActionShift", (T)3, (T)1, (T)8));
        this.actionInterval = (Setting<Integer>)this.register(new Setting("ActionInterval", (T)0, (T)0, (T)10));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.raytrace = (Setting<Boolean>)this.register(new Setting("RayTrace", (T)false));
        this.antiSuicide = (Setting<Boolean>)this.register(new Setting("AntiSuicide", (T)false));
        this.mine = (Setting<Boolean>)this.register(new Setting("Mine", (T)false));
        this.protocol = (Setting<Boolean>)this.register(new Setting("Protocol", (T)false));
        this.ccrys = (Setting<Boolean>)this.register(new Setting("WaitCrystal", (T)true));
        this.renderCurrent = (Setting<Boolean>)this.register(new Setting("Current", (T)true, v -> this.render.getValue()));
        this.renderFull = (Setting<Boolean>)this.register(new Setting("Full", (T)true, v -> this.render.getValue()));
        this.arrow = (Setting<Boolean>)this.register(new Setting("Arrow", (T)true, v -> this.render.getValue()));
        this.bottomArrow = (Setting<Boolean>)this.register(new Setting("Bottom", (T)true, v -> this.render.getValue()));
        this.topArrow = (Setting<Boolean>)this.register(new Setting("Top", (T)true, v -> this.render.getValue()));
        this.stage = Stage.SEARCHING;
        this.torchTimer = new Timer();
        this.delayTimer = new Timer();
        this.renderTimer = new Timer();
        this.postAction = null;
        this.tickCounter = 0;
        this.placedPiston = null;
        this.placedPistonTimer = new Timer();
        this.actionTimer = new Timer();
        this.setInstance();
    }
    
    public static PistonAura getInstance() {
        if (PistonAura.INSTANCE == null) {
            PistonAura.INSTANCE = new PistonAura();
        }
        return PistonAura.INSTANCE;
    }
    
    public static int getPistonSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockPistonBase) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    public static void invokeSync(final float yaw, final float pitch) {
        final boolean flag = PistonAura.mc.player.isSprinting();
        if (flag != ((IEntityPlayerSP)PistonAura.mc.player).getServerSprintState()) {
            if (flag) {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PistonAura.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PistonAura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            ((IEntityPlayerSP)PistonAura.mc.player).setServerSprintState(flag);
        }
        final boolean flag2 = PistonAura.mc.player.isSneaking();
        if (flag2 != ((IEntityPlayerSP)PistonAura.mc.player).getServerSneakState()) {
            if (flag2) {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PistonAura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PistonAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            ((IEntityPlayerSP)PistonAura.mc.player).setServerSneakState(flag2);
        }
        if (PistonAura.mc.player == PistonAura.mc.getRenderViewEntity()) {
            final AxisAlignedBB axisalignedbb = PistonAura.mc.player.getEntityBoundingBox();
            final double dX = PistonAura.mc.player.posX - ((IEntityPlayerSP)PistonAura.mc.player).getLastReportedPosX();
            final double dY = axisalignedbb.minY - ((IEntityPlayerSP)PistonAura.mc.player).getLastReportedPosY();
            final double dZ = PistonAura.mc.player.posZ - ((IEntityPlayerSP)PistonAura.mc.player).getLastReportedPosZ();
            final double dYaw = yaw - ((IEntityPlayerSP)PistonAura.mc.player).getLastReportedYaw();
            final double dPitch = pitch - ((IEntityPlayerSP)PistonAura.mc.player).getLastReportedPitch();
            ((IEntityPlayerSP)PistonAura.mc.player).setPositionUpdateTicks(((IEntityPlayerSP)PistonAura.mc.player).getPositionUpdateTicks() + 1);
            boolean positionChanged = dX * dX + dY * dY + dZ * dZ > 9.0E-4 || ((IEntityPlayerSP)PistonAura.mc.player).getPositionUpdateTicks() >= 20;
            final boolean rotationChanged = dYaw != 0.0 || dPitch != 0.0;
            if (PistonAura.mc.player.isRiding()) {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PistonAura.mc.player.motionX, -999.0, PistonAura.mc.player.motionZ, yaw, pitch, PistonAura.mc.player.onGround));
                positionChanged = false;
            }
            else if (positionChanged && rotationChanged) {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PistonAura.mc.player.posX, axisalignedbb.minY, PistonAura.mc.player.posZ, yaw, pitch, PistonAura.mc.player.onGround));
            }
            else if (positionChanged) {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(PistonAura.mc.player.posX, axisalignedbb.minY, PistonAura.mc.player.posZ, PistonAura.mc.player.onGround));
            }
            else if (rotationChanged) {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, PistonAura.mc.player.onGround));
            }
            else if (((IEntityPlayerSP)PistonAura.mc.player).getPrevOnGround() != PistonAura.mc.player.onGround) {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer(PistonAura.mc.player.onGround));
            }
            if (positionChanged) {
                ((IEntityPlayerSP)PistonAura.mc.player).setLastReportedPosX(PistonAura.mc.player.posX);
                ((IEntityPlayerSP)PistonAura.mc.player).setLastReportedPosY(axisalignedbb.minY);
                ((IEntityPlayerSP)PistonAura.mc.player).setLastReportedPosZ(PistonAura.mc.player.posZ);
                ((IEntityPlayerSP)PistonAura.mc.player).setPositionUpdateTicks(0);
            }
            if (rotationChanged) {
                ((IEntityPlayerSP)PistonAura.mc.player).setLastReportedYaw(yaw);
                ((IEntityPlayerSP)PistonAura.mc.player).setLastReportedPitch(pitch);
            }
            ((IEntityPlayerSP)PistonAura.mc.player).setPrevOnGround(PistonAura.mc.player.onGround);
            ((IEntityPlayerSP)PistonAura.mc.player).setAutoJumpEnabled(PistonAura.mc.gameSettings.autoJump);
        }
    }
    
    private void setInstance() {
        PistonAura.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        if (PistonAura.mc.player == null || PistonAura.mc.world == null) {
            return;
        }
        this.stage = Stage.SEARCHING;
        this.facePos = null;
        this.faceOffset = null;
        this.crystalPos = null;
        this.pistonNeighbour = null;
        this.pistonOffset = null;
        this.delayTime = 0;
        this.tickCounter = 0;
        this.postAction = null;
        this.torchPos = null;
        this.skipPiston = false;
        this.placedPiston = null;
    }
    
    @SubscribeEvent
    public void onReceivePacket(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketBlockChange && this.torchPos != null && ((SPacketBlockChange)event.getPacket()).getBlockPosition().equals((Object)this.torchPos) && ((SPacketBlockChange)event.getPacket()).getBlockState().getBlock() instanceof BlockAir) {
            this.torchPos = null;
        }
    }
    
    private void handleAction(final boolean extra) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.actionTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //     4: ldc2_w          1000
        //     7: invokevirtual   com/mrzak34/thunderhack/util/Timer.passedMs:(J)Z
        //    10: ifeq            33
        //    13: aload_0         /* this */
        //    14: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.disableWhenNone:Lcom/mrzak34/thunderhack/setting/Setting;
        //    17: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //    20: checkcast       Ljava/lang/Boolean;
        //    23: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    26: ifeq            33
        //    29: aload_0         /* this */
        //    30: invokevirtual   com/mrzak34/thunderhack/modules/combat/PistonAura.toggle:()V
        //    33: aload_0         /* this */
        //    34: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.delayTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //    37: aload_0         /* this */
        //    38: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.delayTime:I
        //    41: i2l            
        //    42: invokevirtual   com/mrzak34/thunderhack/util/Timer.passedMs:(J)Z
        //    45: ifne            49
        //    48: return         
        //    49: aload_0         /* this */
        //    50: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.strict:Lcom/mrzak34/thunderhack/setting/Setting;
        //    53: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //    56: checkcast       Ljava/lang/Boolean;
        //    59: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    62: ifeq            115
        //    65: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //    68: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    71: getfield        net/minecraft/client/entity/EntityPlayerSP.motionX:D
        //    74: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //    77: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    80: getfield        net/minecraft/client/entity/EntityPlayerSP.motionX:D
        //    83: dmul           
        //    84: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //    87: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    90: getfield        net/minecraft/client/entity/EntityPlayerSP.motionZ:D
        //    93: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //    96: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    99: getfield        net/minecraft/client/entity/EntityPlayerSP.motionZ:D
        //   102: dmul           
        //   103: dadd           
        //   104: invokestatic    java/lang/Math.sqrt:(D)D
        //   107: ldc2_w          9.0E-4
        //   110: dcmpl          
        //   111: ifle            115
        //   114: return         
        //   115: aload_0         /* this */
        //   116: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.mode:Lcom/mrzak34/thunderhack/setting/Setting;
        //   119: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //   122: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura$Mode.DAMAGE:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Mode;
        //   125: if_acmpne       1679
        //   128: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura$1.$SwitchMap$com$mrzak34$thunderhack$modules$combat$PistonAura$Stage:[I
        //   131: aload_0         /* this */
        //   132: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //   135: invokevirtual   com/mrzak34/thunderhack/modules/combat/PistonAura$Stage.ordinal:()I
        //   138: iaload         
        //   139: tableswitch {
        //                2: 168
        //                3: 415
        //                4: 908
        //                5: 1360
        //          default: 1676
        //        }
        //   168: aload_0         /* this */
        //   169: invokespecial   com/mrzak34/thunderhack/modules/combat/PistonAura.getTargets:()Ljava/util/List;
        //   172: astore_2        /* candidates */
        //   173: aload_2         /* candidates */
        //   174: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   179: astore_3       
        //   180: aload_3        
        //   181: invokeinterface java/util/Iterator.hasNext:()Z
        //   186: ifeq            412
        //   189: aload_3        
        //   190: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   195: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   198: astore          candidate
        //   200: aload_0         /* this */
        //   201: aload           candidate
        //   203: invokespecial   com/mrzak34/thunderhack/modules/combat/PistonAura.evaluateTarget:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //   206: ifeq            409
        //   209: invokestatic    com/mrzak34/thunderhack/modules/combat/PistonAura.getPistonSlot:()I
        //   212: istore          itemSlot
        //   214: iload           itemSlot
        //   216: iconst_m1      
        //   217: if_icmpne       231
        //   220: ldc_w           "No pistons found!"
        //   223: invokestatic    com/mrzak34/thunderhack/command/Command.sendMessage:(Ljava/lang/String;)V
        //   226: aload_0         /* this */
        //   227: invokevirtual   com/mrzak34/thunderhack/modules/combat/PistonAura.toggle:()V
        //   230: return         
        //   231: aload_0         /* this */
        //   232: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.skipPiston:Z
        //   235: ifeq            251
        //   238: aload_0         /* this */
        //   239: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura$Stage.CRYSTAL:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //   242: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //   245: aload_0         /* this */
        //   246: iconst_0       
        //   247: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.skipPiston:Z
        //   250: return         
        //   251: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   254: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   257: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   260: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   263: iload           itemSlot
        //   265: if_icmpeq       272
        //   268: iconst_1       
        //   269: goto            273
        //   272: iconst_0       
        //   273: istore          changeItem
        //   275: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   278: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   281: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isSprinting:()Z
        //   284: istore          isSprinting
        //   286: aload_0         /* this */
        //   287: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.pistonNeighbour:Lnet/minecraft/util/math/BlockPos;
        //   290: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.shouldSneakWhileRightClicking:(Lnet/minecraft/util/math/BlockPos;)Z
        //   293: istore          shouldSneak
        //   295: new             Lnet/minecraft/util/math/Vec3d;
        //   298: dup            
        //   299: aload_0         /* this */
        //   300: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.pistonNeighbour:Lnet/minecraft/util/math/BlockPos;
        //   303: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //   306: ldc2_w          0.5
        //   309: ldc2_w          0.5
        //   312: ldc2_w          0.5
        //   315: invokevirtual   net/minecraft/util/math/Vec3d.add:(DDD)Lnet/minecraft/util/math/Vec3d;
        //   318: new             Lnet/minecraft/util/math/Vec3d;
        //   321: dup            
        //   322: aload_0         /* this */
        //   323: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.pistonOffset:Lnet/minecraft/util/EnumFacing;
        //   326: invokevirtual   net/minecraft/util/EnumFacing.getDirectionVec:()Lnet/minecraft/util/math/Vec3i;
        //   329: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //   332: ldc2_w          0.5
        //   335: invokevirtual   net/minecraft/util/math/Vec3d.scale:(D)Lnet/minecraft/util/math/Vec3d;
        //   338: invokevirtual   net/minecraft/util/math/Vec3d.add:(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
        //   341: astore          vec
        //   343: iload_1         /* extra */
        //   344: ifeq            383
        //   347: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   350: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   353: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   356: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //   359: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //   362: aload           vec
        //   364: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //   367: astore          angle
        //   369: aload           angle
        //   371: iconst_0       
        //   372: faload         
        //   373: aload           angle
        //   375: iconst_1       
        //   376: faload         
        //   377: invokestatic    com/mrzak34/thunderhack/modules/combat/PistonAura.invokeSync:(FF)V
        //   380: goto            388
        //   383: aload           vec
        //   385: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.lookAtVec3d:(Lnet/minecraft/util/math/Vec3d;)V
        //   388: aload_0         /* this */
        //   389: aload_0         /* this */
        //   390: iload           changeItem
        //   392: iload           itemSlot
        //   394: iload           isSprinting
        //   396: iload           shouldSneak
        //   398: aload           vec
        //   400: invokedynamic   BootstrapMethod #8, run:(Lcom/mrzak34/thunderhack/modules/combat/PistonAura;ZIZZLnet/minecraft/util/math/Vec3d;)Ljava/lang/Runnable;
        //   405: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //   408: return         
        //   409: goto            180
        //   412: goto            1676
        //   415: aload_0         /* this */
        //   416: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   419: ifnull          451
        //   422: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   425: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //   428: aload_0         /* this */
        //   429: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   432: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   435: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   440: getstatic       net/minecraft/init/Blocks.AIR:Lnet/minecraft/block/Block;
        //   443: if_acmpne       451
        //   446: aload_0         /* this */
        //   447: aconst_null    
        //   448: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   451: aload_0         /* this */
        //   452: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   455: ifnull          693
        //   458: aload_0         /* this */
        //   459: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.torchTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //   462: ldc2_w          1000
        //   465: invokevirtual   com/mrzak34/thunderhack/util/Timer.passedMs:(J)Z
        //   468: ifeq            692
        //   471: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   474: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //   477: new             Lnet/minecraft/util/math/Vec3d;
        //   480: dup            
        //   481: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   484: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   487: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   490: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   493: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   496: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //   499: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   502: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   505: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEyeHeight:()F
        //   508: f2d            
        //   509: dadd           
        //   510: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   513: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   516: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   519: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   522: new             Lnet/minecraft/util/math/Vec3d;
        //   525: dup            
        //   526: aload_0         /* this */
        //   527: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   530: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //   533: i2d            
        //   534: ldc2_w          0.5
        //   537: dadd           
        //   538: aload_0         /* this */
        //   539: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   542: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //   545: i2d            
        //   546: ldc2_w          0.5
        //   549: dadd           
        //   550: aload_0         /* this */
        //   551: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   554: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //   557: i2d            
        //   558: ldc2_w          0.5
        //   561: dadd           
        //   562: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   565: invokevirtual   net/minecraft/client/multiplayer/WorldClient.rayTraceBlocks:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;
        //   568: astore_2        /* result */
        //   569: aload_2         /* result */
        //   570: ifnull          580
        //   573: aload_2         /* result */
        //   574: getfield        net/minecraft/util/math/RayTraceResult.sideHit:Lnet/minecraft/util/EnumFacing;
        //   577: ifnonnull       586
        //   580: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //   583: goto            590
        //   586: aload_2         /* result */
        //   587: getfield        net/minecraft/util/math/RayTraceResult.sideHit:Lnet/minecraft/util/EnumFacing;
        //   590: astore_3        /* f */
        //   591: new             Lnet/minecraft/util/math/Vec3d;
        //   594: dup            
        //   595: aload_0         /* this */
        //   596: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   599: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //   602: ldc2_w          0.5
        //   605: ldc2_w          0.5
        //   608: ldc2_w          0.5
        //   611: invokevirtual   net/minecraft/util/math/Vec3d.add:(DDD)Lnet/minecraft/util/math/Vec3d;
        //   614: new             Lnet/minecraft/util/math/Vec3d;
        //   617: dup            
        //   618: aload_3         /* f */
        //   619: invokevirtual   net/minecraft/util/EnumFacing.getDirectionVec:()Lnet/minecraft/util/math/Vec3i;
        //   622: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //   625: ldc2_w          0.5
        //   628: invokevirtual   net/minecraft/util/math/Vec3d.scale:(D)Lnet/minecraft/util/math/Vec3d;
        //   631: invokevirtual   net/minecraft/util/math/Vec3d.add:(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
        //   634: astore          vec
        //   636: iload_1         /* extra */
        //   637: ifeq            676
        //   640: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   643: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   646: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   649: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //   652: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //   655: aload           vec
        //   657: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //   660: astore          angle
        //   662: aload           angle
        //   664: iconst_0       
        //   665: faload         
        //   666: aload           angle
        //   668: iconst_1       
        //   669: faload         
        //   670: invokestatic    com/mrzak34/thunderhack/modules/combat/PistonAura.invokeSync:(FF)V
        //   673: goto            681
        //   676: aload           vec
        //   678: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.lookAtVec3d:(Lnet/minecraft/util/math/Vec3d;)V
        //   681: aload_0         /* this */
        //   682: aload_0         /* this */
        //   683: aload_3         /* f */
        //   684: invokedynamic   BootstrapMethod #9, run:(Lcom/mrzak34/thunderhack/modules/combat/PistonAura;Lnet/minecraft/util/EnumFacing;)Ljava/lang/Runnable;
        //   689: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //   692: return         
        //   693: aload_0         /* this */
        //   694: invokevirtual   com/mrzak34/thunderhack/modules/combat/PistonAura.isOffhand:()Z
        //   697: ifne            758
        //   700: invokestatic    com/mrzak34/thunderhack/util/CrystalUtils.getCrystalSlot:()I
        //   703: istore_2        /* crystalSlot */
        //   704: iload_2         /* crystalSlot */
        //   705: iconst_m1      
        //   706: if_icmpne       720
        //   709: ldc_w           "No crystals found!"
        //   712: invokestatic    com/mrzak34/thunderhack/command/Command.sendMessage:(Ljava/lang/String;)V
        //   715: aload_0         /* this */
        //   716: invokevirtual   com/mrzak34/thunderhack/modules/combat/PistonAura.toggle:()V
        //   719: return         
        //   720: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   723: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   726: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   729: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   732: iload_2         /* crystalSlot */
        //   733: if_icmpeq       758
        //   736: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   739: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   742: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   745: iload_2         /* crystalSlot */
        //   746: putfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   749: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   752: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //   755: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.updateController:()V
        //   758: aload_0         /* this */
        //   759: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //   762: ifnonnull       773
        //   765: aload_0         /* this */
        //   766: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura$Stage.SEARCHING:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //   769: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //   772: return         
        //   773: iload_1         /* extra */
        //   774: ifeq            851
        //   777: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   780: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   783: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   786: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //   789: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //   792: new             Lnet/minecraft/util/math/Vec3d;
        //   795: dup            
        //   796: aload_0         /* this */
        //   797: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //   800: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //   803: i2d            
        //   804: ldc2_w          0.5
        //   807: dadd           
        //   808: aload_0         /* this */
        //   809: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //   812: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //   815: i2d            
        //   816: ldc2_w          0.5
        //   819: dadd           
        //   820: aload_0         /* this */
        //   821: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //   824: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //   827: i2d            
        //   828: ldc2_w          0.5
        //   831: dadd           
        //   832: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   835: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //   838: astore_2        /* angle */
        //   839: aload_2         /* angle */
        //   840: iconst_0       
        //   841: faload         
        //   842: aload_2         /* angle */
        //   843: iconst_1       
        //   844: faload         
        //   845: invokestatic    com/mrzak34/thunderhack/modules/combat/PistonAura.invokeSync:(FF)V
        //   848: goto            897
        //   851: new             Lnet/minecraft/util/math/Vec3d;
        //   854: dup            
        //   855: aload_0         /* this */
        //   856: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //   859: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //   862: i2d            
        //   863: ldc2_w          0.5
        //   866: dadd           
        //   867: aload_0         /* this */
        //   868: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //   871: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //   874: i2d            
        //   875: ldc2_w          0.5
        //   878: dadd           
        //   879: aload_0         /* this */
        //   880: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //   883: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //   886: i2d            
        //   887: ldc2_w          0.5
        //   890: dadd           
        //   891: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   894: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.lookAtVec3d:(Lnet/minecraft/util/math/Vec3d;)V
        //   897: aload_0         /* this */
        //   898: aload_0         /* this */
        //   899: invokedynamic   BootstrapMethod #10, run:(Lcom/mrzak34/thunderhack/modules/combat/PistonAura;)Ljava/lang/Runnable;
        //   904: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //   907: return         
        //   908: aload_0         /* this */
        //   909: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //   912: ifnonnull       923
        //   915: aload_0         /* this */
        //   916: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura$Stage.SEARCHING:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //   919: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //   922: return         
        //   923: aload_0         /* this */
        //   924: invokespecial   com/mrzak34/thunderhack/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //   927: istore_2        /* itemSlot */
        //   928: iload_2         /* itemSlot */
        //   929: iconst_m1      
        //   930: if_icmpne       944
        //   933: ldc_w           "No redstone found!"
        //   936: invokestatic    com/mrzak34/thunderhack/command/Command.sendMessage:(Ljava/lang/String;)V
        //   939: aload_0         /* this */
        //   940: invokevirtual   com/mrzak34/thunderhack/modules/combat/PistonAura.toggle:()V
        //   943: return         
        //   944: aload_0         /* this */
        //   945: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //   948: aload_0         /* this */
        //   949: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.faceOffset:Lnet/minecraft/util/EnumFacing;
        //   952: iconst_3       
        //   953: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //   956: iconst_0       
        //   957: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   960: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   963: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   966: aload_0         /* this */
        //   967: invokespecial   com/mrzak34/thunderhack/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //   970: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //   973: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   976: checkcast       Lnet/minecraft/item/ItemBlock;
        //   979: invokevirtual   net/minecraft/item/ItemBlock.getBlock:()Lnet/minecraft/block/Block;
        //   982: getstatic       net/minecraft/init/Blocks.REDSTONE_TORCH:Lnet/minecraft/block/Block;
        //   985: if_acmpne       992
        //   988: iconst_1       
        //   989: goto            993
        //   992: iconst_0       
        //   993: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.generateClickLocation:(Lnet/minecraft/util/math/BlockPos;ZZ)Ljava/util/Optional;
        //   996: astore_3        /* posCL */
        //   997: aload_3         /* posCL */
        //   998: invokevirtual   java/util/Optional.isPresent:()Z
        //  1001: ifne            1166
        //  1004: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1007: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1010: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1013: aload_0         /* this */
        //  1014: invokespecial   com/mrzak34/thunderhack/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //  1017: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //  1020: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //  1023: checkcast       Lnet/minecraft/item/ItemBlock;
        //  1026: invokevirtual   net/minecraft/item/ItemBlock.getBlock:()Lnet/minecraft/block/Block;
        //  1029: getstatic       net/minecraft/init/Blocks.REDSTONE_TORCH:Lnet/minecraft/block/Block;
        //  1032: if_acmpne       1166
        //  1035: getstatic       net/minecraft/util/EnumFacing.HORIZONTALS:[Lnet/minecraft/util/EnumFacing;
        //  1038: astore          4
        //  1040: aload           4
        //  1042: arraylength    
        //  1043: istore          5
        //  1045: iconst_0       
        //  1046: istore          6
        //  1048: iload           6
        //  1050: iload           5
        //  1052: if_icmpge       1166
        //  1055: aload           4
        //  1057: iload           6
        //  1059: aaload         
        //  1060: astore          torchFacing
        //  1062: aload           torchFacing
        //  1064: aload_0         /* this */
        //  1065: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.faceOffset:Lnet/minecraft/util/EnumFacing;
        //  1068: invokevirtual   net/minecraft/util/EnumFacing.equals:(Ljava/lang/Object;)Z
        //  1071: ifne            1160
        //  1074: aload           torchFacing
        //  1076: aload_0         /* this */
        //  1077: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.faceOffset:Lnet/minecraft/util/EnumFacing;
        //  1080: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //  1083: invokevirtual   net/minecraft/util/EnumFacing.equals:(Ljava/lang/Object;)Z
        //  1086: ifeq            1092
        //  1089: goto            1160
        //  1092: aload_0         /* this */
        //  1093: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1096: aload_0         /* this */
        //  1097: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.faceOffset:Lnet/minecraft/util/EnumFacing;
        //  1100: iconst_2       
        //  1101: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  1104: aload           torchFacing
        //  1106: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  1109: iconst_0       
        //  1110: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1113: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1116: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1119: aload_0         /* this */
        //  1120: invokespecial   com/mrzak34/thunderhack/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //  1123: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //  1126: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //  1129: checkcast       Lnet/minecraft/item/ItemBlock;
        //  1132: invokevirtual   net/minecraft/item/ItemBlock.getBlock:()Lnet/minecraft/block/Block;
        //  1135: getstatic       net/minecraft/init/Blocks.REDSTONE_TORCH:Lnet/minecraft/block/Block;
        //  1138: if_acmpne       1145
        //  1141: iconst_1       
        //  1142: goto            1146
        //  1145: iconst_0       
        //  1146: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.generateClickLocation:(Lnet/minecraft/util/math/BlockPos;ZZ)Ljava/util/Optional;
        //  1149: astore_3        /* posCL */
        //  1150: aload_3         /* posCL */
        //  1151: invokevirtual   java/util/Optional.isPresent:()Z
        //  1154: ifeq            1160
        //  1157: goto            1166
        //  1160: iinc            6, 1
        //  1163: goto            1048
        //  1166: aload_3         /* posCL */
        //  1167: invokevirtual   java/util/Optional.isPresent:()Z
        //  1170: ifeq            1352
        //  1173: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1176: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1179: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1182: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //  1185: iload_2         /* itemSlot */
        //  1186: if_icmpeq       1193
        //  1189: iconst_1       
        //  1190: goto            1194
        //  1193: iconst_0       
        //  1194: istore          changeItem
        //  1196: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1199: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1202: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isSprinting:()Z
        //  1205: istore          isSprinting
        //  1207: aload_3         /* posCL */
        //  1208: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
        //  1211: checkcast       Lcom/mrzak34/thunderhack/util/BlockUtils$ClickLocation;
        //  1214: getfield        com/mrzak34/thunderhack/util/BlockUtils$ClickLocation.neighbour:Lnet/minecraft/util/math/BlockPos;
        //  1217: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.shouldSneakWhileRightClicking:(Lnet/minecraft/util/math/BlockPos;)Z
        //  1220: istore          shouldSneak
        //  1222: new             Lnet/minecraft/util/math/Vec3d;
        //  1225: dup            
        //  1226: aload_3         /* posCL */
        //  1227: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
        //  1230: checkcast       Lcom/mrzak34/thunderhack/util/BlockUtils$ClickLocation;
        //  1233: getfield        com/mrzak34/thunderhack/util/BlockUtils$ClickLocation.neighbour:Lnet/minecraft/util/math/BlockPos;
        //  1236: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //  1239: ldc2_w          0.5
        //  1242: ldc2_w          0.5
        //  1245: ldc2_w          0.5
        //  1248: invokevirtual   net/minecraft/util/math/Vec3d.add:(DDD)Lnet/minecraft/util/math/Vec3d;
        //  1251: new             Lnet/minecraft/util/math/Vec3d;
        //  1254: dup            
        //  1255: aload_3         /* posCL */
        //  1256: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
        //  1259: checkcast       Lcom/mrzak34/thunderhack/util/BlockUtils$ClickLocation;
        //  1262: getfield        com/mrzak34/thunderhack/util/BlockUtils$ClickLocation.opposite:Lnet/minecraft/util/EnumFacing;
        //  1265: invokevirtual   net/minecraft/util/EnumFacing.getDirectionVec:()Lnet/minecraft/util/math/Vec3i;
        //  1268: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //  1271: ldc2_w          0.5
        //  1274: invokevirtual   net/minecraft/util/math/Vec3d.scale:(D)Lnet/minecraft/util/math/Vec3d;
        //  1277: invokevirtual   net/minecraft/util/math/Vec3d.add:(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
        //  1280: astore          vec
        //  1282: iload_1         /* extra */
        //  1283: ifeq            1322
        //  1286: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1289: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1292: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1295: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //  1298: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  1301: aload           vec
        //  1303: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  1306: astore          angle
        //  1308: aload           angle
        //  1310: iconst_0       
        //  1311: faload         
        //  1312: aload           angle
        //  1314: iconst_1       
        //  1315: faload         
        //  1316: invokestatic    com/mrzak34/thunderhack/modules/combat/PistonAura.invokeSync:(FF)V
        //  1319: goto            1327
        //  1322: aload           vec
        //  1324: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.lookAtVec3d:(Lnet/minecraft/util/math/Vec3d;)V
        //  1327: aload_3         /* posCL */
        //  1328: astore          finalCL
        //  1330: aload_0         /* this */
        //  1331: aload_0         /* this */
        //  1332: iload           changeItem
        //  1334: iload_2         /* itemSlot */
        //  1335: iload           isSprinting
        //  1337: iload           shouldSneak
        //  1339: aload           finalCL
        //  1341: aload           vec
        //  1343: invokedynamic   BootstrapMethod #11, run:(Lcom/mrzak34/thunderhack/modules/combat/PistonAura;ZIZZLjava/util/Optional;Lnet/minecraft/util/math/Vec3d;)Ljava/lang/Runnable;
        //  1348: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  1351: return         
        //  1352: aload_0         /* this */
        //  1353: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura$Stage.BREAKING:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //  1356: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //  1359: return         
        //  1360: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1363: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1366: getfield        net/minecraft/client/multiplayer/WorldClient.loadedEntityList:Ljava/util/List;
        //  1369: invokeinterface java/util/List.stream:()Ljava/util/stream/Stream;
        //  1374: invokedynamic   BootstrapMethod #12, test:()Ljava/util/function/Predicate;
        //  1379: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //  1384: aload_0         /* this */
        //  1385: invokedynamic   BootstrapMethod #13, test:(Lcom/mrzak34/thunderhack/modules/combat/PistonAura;)Ljava/util/function/Predicate;
        //  1390: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //  1395: invokedynamic   BootstrapMethod #14, apply:()Ljava/util/function/Function;
        //  1400: invokestatic    java/util/Comparator.comparing:(Ljava/util/function/Function;)Ljava/util/Comparator;
        //  1403: invokeinterface java/util/stream/Stream.min:(Ljava/util/Comparator;)Ljava/util/Optional;
        //  1408: aconst_null    
        //  1409: invokevirtual   java/util/Optional.orElse:(Ljava/lang/Object;)Ljava/lang/Object;
        //  1412: checkcast       Lnet/minecraft/entity/Entity;
        //  1415: astore_2        /* nearestCrystal */
        //  1416: aload_2         /* nearestCrystal */
        //  1417: ifnull          1567
        //  1420: aload_0         /* this */
        //  1421: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.antiSuicide:Lcom/mrzak34/thunderhack/setting/Setting;
        //  1424: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //  1427: checkcast       Ljava/lang/Boolean;
        //  1430: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  1433: ifeq            1473
        //  1436: aload_2         /* nearestCrystal */
        //  1437: checkcast       Lnet/minecraft/entity/item/EntityEnderCrystal;
        //  1440: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1443: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1446: invokestatic    com/mrzak34/thunderhack/util/CrystalUtils.calculateDamage:(Lnet/minecraft/entity/item/EntityEnderCrystal;Lnet/minecraft/entity/Entity;)F
        //  1449: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1452: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1455: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getHealth:()F
        //  1458: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1461: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1464: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getAbsorptionAmount:()F
        //  1467: fadd           
        //  1468: fcmpl          
        //  1469: iflt            1473
        //  1472: return         
        //  1473: aload_0         /* this */
        //  1474: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.delayTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //  1477: invokevirtual   com/mrzak34/thunderhack/util/Timer.reset:()V
        //  1480: aload_0         /* this */
        //  1481: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.renderTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //  1484: invokevirtual   com/mrzak34/thunderhack/util/Timer.reset:()V
        //  1487: aload_0         /* this */
        //  1488: aload_0         /* this */
        //  1489: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.breakDelay:Lcom/mrzak34/thunderhack/setting/Setting;
        //  1492: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //  1495: checkcast       Ljava/lang/Integer;
        //  1498: invokevirtual   java/lang/Integer.intValue:()I
        //  1501: bipush          10
        //  1503: imul           
        //  1504: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.delayTime:I
        //  1507: iload_1         /* extra */
        //  1508: ifeq            1546
        //  1511: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1514: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1517: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1520: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //  1523: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  1526: aload_2         /* nearestCrystal */
        //  1527: invokevirtual   net/minecraft/entity/Entity.getPositionVector:()Lnet/minecraft/util/math/Vec3d;
        //  1530: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  1533: astore_3        /* angle */
        //  1534: aload_3         /* angle */
        //  1535: iconst_0       
        //  1536: faload         
        //  1537: aload_3         /* angle */
        //  1538: iconst_1       
        //  1539: faload         
        //  1540: invokestatic    com/mrzak34/thunderhack/modules/combat/PistonAura.invokeSync:(FF)V
        //  1543: goto            1553
        //  1546: aload_2         /* nearestCrystal */
        //  1547: invokevirtual   net/minecraft/entity/Entity.getPositionVector:()Lnet/minecraft/util/math/Vec3d;
        //  1550: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.lookAtVec3d:(Lnet/minecraft/util/math/Vec3d;)V
        //  1553: aload_0         /* this */
        //  1554: aload_0         /* this */
        //  1555: aload_2         /* nearestCrystal */
        //  1556: invokedynamic   BootstrapMethod #15, run:(Lcom/mrzak34/thunderhack/modules/combat/PistonAura;Lnet/minecraft/entity/Entity;)Ljava/lang/Runnable;
        //  1561: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  1564: goto            1676
        //  1567: iload_1         /* extra */
        //  1568: ifeq            1641
        //  1571: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1574: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1577: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1580: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //  1583: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  1586: new             Lnet/minecraft/util/math/Vec3d;
        //  1589: dup            
        //  1590: aload_0         /* this */
        //  1591: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1594: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //  1597: i2d            
        //  1598: ldc2_w          0.5
        //  1601: dadd           
        //  1602: aload_0         /* this */
        //  1603: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1606: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //  1609: i2d            
        //  1610: aload_0         /* this */
        //  1611: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1614: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //  1617: i2d            
        //  1618: ldc2_w          0.5
        //  1621: dadd           
        //  1622: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //  1625: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  1628: astore_3        /* angle */
        //  1629: aload_3         /* angle */
        //  1630: iconst_0       
        //  1631: faload         
        //  1632: aload_3         /* angle */
        //  1633: iconst_1       
        //  1634: faload         
        //  1635: invokestatic    com/mrzak34/thunderhack/modules/combat/PistonAura.invokeSync:(FF)V
        //  1638: goto            1676
        //  1641: aload_0         /* this */
        //  1642: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1645: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //  1648: i2d            
        //  1649: ldc2_w          0.5
        //  1652: dadd           
        //  1653: aload_0         /* this */
        //  1654: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1657: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //  1660: i2d            
        //  1661: aload_0         /* this */
        //  1662: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1665: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //  1668: i2d            
        //  1669: ldc2_w          0.5
        //  1672: dadd           
        //  1673: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.lookAtXYZ:(DDD)V
        //  1676: goto            2460
        //  1679: aload_0         /* this */
        //  1680: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura$Stage.SEARCHING:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //  1683: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/modules/combat/PistonAura$Stage;
        //  1686: invokestatic    com/mrzak34/thunderhack/modules/combat/PistonAura.getPistonSlot:()I
        //  1689: istore_2        /* pistonSlot */
        //  1690: iload_2         /* pistonSlot */
        //  1691: iconst_m1      
        //  1692: if_icmpne       1706
        //  1695: ldc_w           "No pistons found!"
        //  1698: invokestatic    com/mrzak34/thunderhack/command/Command.sendMessage:(Ljava/lang/String;)V
        //  1701: aload_0         /* this */
        //  1702: invokevirtual   com/mrzak34/thunderhack/modules/combat/PistonAura.toggle:()V
        //  1705: return         
        //  1706: aload_0         /* this */
        //  1707: invokespecial   com/mrzak34/thunderhack/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //  1710: istore_3        /* redstoneBlockSlot */
        //  1711: iload_3         /* redstoneBlockSlot */
        //  1712: iconst_m1      
        //  1713: if_icmpne       1727
        //  1716: ldc_w           "No redstone found!"
        //  1719: invokestatic    com/mrzak34/thunderhack/command/Command.sendMessage:(Ljava/lang/String;)V
        //  1722: aload_0         /* this */
        //  1723: invokevirtual   com/mrzak34/thunderhack/modules/combat/PistonAura.toggle:()V
        //  1726: return         
        //  1727: aload_0         /* this */
        //  1728: invokespecial   com/mrzak34/thunderhack/modules/combat/PistonAura.getTargets:()Ljava/util/List;
        //  1731: astore          candidates
        //  1733: aload           candidates
        //  1735: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1740: astore          5
        //  1742: aload           5
        //  1744: invokeinterface java/util/Iterator.hasNext:()Z
        //  1749: ifeq            2460
        //  1752: aload           5
        //  1754: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1759: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //  1762: astore          candidate
        //  1764: aload_0         /* this */
        //  1765: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.smart:Lcom/mrzak34/thunderhack/setting/Setting;
        //  1768: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //  1771: checkcast       Ljava/lang/Boolean;
        //  1774: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  1777: ifeq            1827
        //  1780: new             Lnet/minecraft/util/math/BlockPos;
        //  1783: dup            
        //  1784: aload           candidate
        //  1786: invokespecial   net/minecraft/util/math/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  1789: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.isHole:(Lnet/minecraft/util/math/BlockPos;)Z
        //  1792: ifne            1827
        //  1795: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1798: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1801: new             Lnet/minecraft/util/math/BlockPos;
        //  1804: dup            
        //  1805: aload           candidate
        //  1807: invokespecial   net/minecraft/util/math/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  1810: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  1813: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  1818: getstatic       net/minecraft/init/Blocks.AIR:Lnet/minecraft/block/Block;
        //  1821: if_acmpne       1827
        //  1824: goto            1742
        //  1827: new             Lnet/minecraft/util/math/BlockPos;
        //  1830: dup            
        //  1831: aload           candidate
        //  1833: invokespecial   net/minecraft/util/math/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  1836: invokevirtual   net/minecraft/util/math/BlockPos.up:()Lnet/minecraft/util/math/BlockPos;
        //  1839: astore          candidatePos
        //  1841: aload_0         /* this */
        //  1842: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.antiSuicide:Lcom/mrzak34/thunderhack/setting/Setting;
        //  1845: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //  1848: checkcast       Ljava/lang/Boolean;
        //  1851: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  1854: ifeq            1881
        //  1857: aload           candidatePos
        //  1859: new             Lnet/minecraft/util/math/BlockPos;
        //  1862: dup            
        //  1863: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1866: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1869: invokespecial   net/minecraft/util/math/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  1872: invokevirtual   net/minecraft/util/math/BlockPos.equals:(Ljava/lang/Object;)Z
        //  1875: ifeq            1881
        //  1878: goto            1742
        //  1881: getstatic       net/minecraft/util/EnumFacing.HORIZONTALS:[Lnet/minecraft/util/EnumFacing;
        //  1884: astore          8
        //  1886: aload           8
        //  1888: arraylength    
        //  1889: istore          9
        //  1891: iconst_0       
        //  1892: istore          10
        //  1894: iload           10
        //  1896: iload           9
        //  1898: if_icmpge       2184
        //  1901: aload           8
        //  1903: iload           10
        //  1905: aaload         
        //  1906: astore          faceTryOffset
        //  1908: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1911: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1914: aload           candidatePos
        //  1916: aload           faceTryOffset
        //  1918: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  1921: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  1924: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  1929: instanceof      Lnet/minecraft/block/BlockPistonBase;
        //  1932: ifne            1970
        //  1935: aload_0         /* this */
        //  1936: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.placedPistonTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //  1939: invokestatic    com/mrzak34/thunderhack/util/CrystalUtils.ping:()I
        //  1942: sipush          150
        //  1945: iadd           
        //  1946: i2l            
        //  1947: invokevirtual   com/mrzak34/thunderhack/util/Timer.passed:(J)Z
        //  1950: ifne            2178
        //  1953: aload           candidatePos
        //  1955: aload           faceTryOffset
        //  1957: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  1960: aload_0         /* this */
        //  1961: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.placedPiston:Lnet/minecraft/util/math/BlockPos;
        //  1964: invokevirtual   net/minecraft/util/math/BlockPos.equals:(Ljava/lang/Object;)Z
        //  1967: ifeq            2178
        //  1970: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1973: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1976: aload           candidatePos
        //  1978: aload           faceTryOffset
        //  1980: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  1983: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  1986: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  1991: instanceof      Lnet/minecraft/block/BlockPistonBase;
        //  1994: ifeq            2042
        //  1997: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2000: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2003: aload           candidatePos
        //  2005: aload           faceTryOffset
        //  2007: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2010: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2013: getstatic       net/minecraft/block/BlockDirectional.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //  2016: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //  2021: checkcast       Lnet/minecraft/util/EnumFacing;
        //  2024: astore          enumfacing
        //  2026: aload           enumfacing
        //  2028: aload           faceTryOffset
        //  2030: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //  2033: invokevirtual   net/minecraft/util/EnumFacing.equals:(Ljava/lang/Object;)Z
        //  2036: ifne            2042
        //  2039: goto            2178
        //  2042: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2045: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2048: aload           candidatePos
        //  2050: aload           faceTryOffset
        //  2052: iconst_2       
        //  2053: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2056: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2059: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2064: getstatic       net/minecraft/init/Blocks.REDSTONE_BLOCK:Lnet/minecraft/block/Block;
        //  2067: if_acmpeq       2460
        //  2070: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2073: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2076: aload           candidatePos
        //  2078: aload           faceTryOffset
        //  2080: iconst_2       
        //  2081: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2084: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2087: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2092: getstatic       net/minecraft/init/Blocks.REDSTONE_TORCH:Lnet/minecraft/block/Block;
        //  2095: if_acmpne       2101
        //  2098: goto            2460
        //  2101: aload           candidatePos
        //  2103: aload           faceTryOffset
        //  2105: iconst_2       
        //  2106: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2109: aload_0         /* this */
        //  2110: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/setting/Setting;
        //  2113: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //  2116: checkcast       Ljava/lang/Boolean;
        //  2119: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2122: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.canPlaceBlock:(Lnet/minecraft/util/math/BlockPos;Z)Z
        //  2125: ifeq            2460
        //  2128: aload           candidatePos
        //  2130: aload           faceTryOffset
        //  2132: iconst_2       
        //  2133: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2136: iconst_1       
        //  2137: iload_1         /* extra */
        //  2138: aload_0         /* this */
        //  2139: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/setting/Setting;
        //  2142: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //  2145: checkcast       Ljava/lang/Boolean;
        //  2148: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2151: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.preparePlacement:(Lnet/minecraft/util/math/BlockPos;ZZZ)Lcom/mrzak34/thunderhack/util/InteractionUtil$Placement;
        //  2154: astore          placement
        //  2156: aload           placement
        //  2158: ifnull          2175
        //  2161: aload_0         /* this */
        //  2162: aload_0         /* this */
        //  2163: iload_3         /* redstoneBlockSlot */
        //  2164: aload           placement
        //  2166: invokedynamic   BootstrapMethod #16, run:(Lcom/mrzak34/thunderhack/modules/combat/PistonAura;ILcom/mrzak34/thunderhack/util/InteractionUtil$Placement;)Ljava/lang/Runnable;
        //  2171: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  2174: return         
        //  2175: goto            2460
        //  2178: iinc            10, 1
        //  2181: goto            1894
        //  2184: getstatic       net/minecraft/util/EnumFacing.HORIZONTALS:[Lnet/minecraft/util/EnumFacing;
        //  2187: astore          8
        //  2189: aload           8
        //  2191: arraylength    
        //  2192: istore          9
        //  2194: iconst_0       
        //  2195: istore          10
        //  2197: iload           10
        //  2199: iload           9
        //  2201: if_icmpge       2457
        //  2204: aload           8
        //  2206: iload           10
        //  2208: aaload         
        //  2209: astore          faceTryOffset
        //  2211: aload           candidatePos
        //  2213: aload           faceTryOffset
        //  2215: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2218: aload_0         /* this */
        //  2219: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/setting/Setting;
        //  2222: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //  2225: checkcast       Ljava/lang/Boolean;
        //  2228: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2231: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.canPlaceBlock:(Lnet/minecraft/util/math/BlockPos;Z)Z
        //  2234: ifeq            2451
        //  2237: aload_0         /* this */
        //  2238: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/setting/Setting;
        //  2241: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //  2244: checkcast       Ljava/lang/Boolean;
        //  2247: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2250: ifeq            2271
        //  2253: aload           candidatePos
        //  2255: aload           faceTryOffset
        //  2257: iconst_2       
        //  2258: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2261: iconst_1       
        //  2262: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.canPlaceBlock:(Lnet/minecraft/util/math/BlockPos;Z)Z
        //  2265: ifeq            2451
        //  2268: goto            2299
        //  2271: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2274: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2277: aload           candidatePos
        //  2279: aload           faceTryOffset
        //  2281: iconst_2       
        //  2282: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2285: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2288: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2293: getstatic       net/minecraft/init/Blocks.AIR:Lnet/minecraft/block/Block;
        //  2296: if_acmpne       2451
        //  2299: getstatic       com/mrzak34/thunderhack/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2302: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  2305: fconst_1       
        //  2306: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  2309: new             Lnet/minecraft/util/math/Vec3d;
        //  2312: dup            
        //  2313: aload           candidatePos
        //  2315: aload           faceTryOffset
        //  2317: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2320: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //  2323: i2d            
        //  2324: ldc2_w          0.5
        //  2327: dadd           
        //  2328: aload           candidatePos
        //  2330: aload           faceTryOffset
        //  2332: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2335: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //  2338: i2d            
        //  2339: dconst_1       
        //  2340: dadd           
        //  2341: aload           candidatePos
        //  2343: aload           faceTryOffset
        //  2345: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2348: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //  2351: i2d            
        //  2352: ldc2_w          0.5
        //  2355: dadd           
        //  2356: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //  2359: invokestatic    com/mrzak34/thunderhack/util/SilentRotationUtil.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  2362: astore          rots
        //  2364: aload           rots
        //  2366: iconst_0       
        //  2367: faload         
        //  2368: f2d            
        //  2369: invokestatic    net/minecraft/util/EnumFacing.fromAngle:(D)Lnet/minecraft/util/EnumFacing;
        //  2372: astore          facing
        //  2374: aload           rots
        //  2376: iconst_1       
        //  2377: faload         
        //  2378: invokestatic    java/lang/Math.abs:(F)F
        //  2381: ldc_w           55.0
        //  2384: fcmpl          
        //  2385: ifle            2391
        //  2388: goto            2451
        //  2391: aload           facing
        //  2393: aload           faceTryOffset
        //  2395: if_acmpeq       2401
        //  2398: goto            2451
        //  2401: aload           candidatePos
        //  2403: aload           faceTryOffset
        //  2405: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2408: iconst_1       
        //  2409: iload_1         /* extra */
        //  2410: aload_0         /* this */
        //  2411: getfield        com/mrzak34/thunderhack/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/setting/Setting;
        //  2414: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //  2417: checkcast       Ljava/lang/Boolean;
        //  2420: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2423: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.preparePlacement:(Lnet/minecraft/util/math/BlockPos;ZZZ)Lcom/mrzak34/thunderhack/util/InteractionUtil$Placement;
        //  2426: astore          placement
        //  2428: aload           placement
        //  2430: ifnull          2451
        //  2433: aload_0         /* this */
        //  2434: aload_0         /* this */
        //  2435: iload_2         /* pistonSlot */
        //  2436: aload           placement
        //  2438: aload           candidatePos
        //  2440: aload           faceTryOffset
        //  2442: invokedynamic   BootstrapMethod #17, run:(Lcom/mrzak34/thunderhack/modules/combat/PistonAura;ILcom/mrzak34/thunderhack/util/InteractionUtil$Placement;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Ljava/lang/Runnable;
        //  2447: putfield        com/mrzak34/thunderhack/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  2450: return         
        //  2451: iinc            10, 1
        //  2454: goto            2197
        //  2457: goto            1742
        //  2460: return         
        //    StackMapTable: 00 47 21 0F FB 00 41 34 FD 00 0B 07 02 93 07 02 99 FD 00 32 07 02 A1 01 13 14 40 01 FF 00 6D 00 0A 07 00 02 01 07 02 93 07 02 99 07 02 A1 01 01 01 01 07 02 BB 00 00 04 FF 00 14 00 04 07 00 02 01 07 02 93 07 02 99 00 00 FA 00 02 FA 00 02 23 FC 00 80 07 00 33 05 43 07 02 C6 FD 00 55 07 02 C6 07 02 BB 04 F8 00 0A 00 FC 00 1A 01 FA 00 25 0E FB 00 4D 2D 0A 0E FC 00 14 01 FF 00 2F 00 03 07 00 02 01 01 00 02 07 02 5F 01 FF 00 00 00 03 07 00 02 01 01 00 03 07 02 5F 01 01 FF 00 36 00 07 07 00 02 01 01 07 03 4A 07 03 52 01 01 00 00 FC 00 2B 07 02 C6 FF 00 34 00 08 07 00 02 01 01 07 03 4A 07 03 52 01 01 07 02 C6 00 02 07 02 5F 01 FF 00 00 00 08 07 00 02 01 01 07 03 4A 07 03 52 01 01 07 02 C6 00 03 07 02 5F 01 01 FA 00 0D F8 00 05 1A 40 01 FF 00 7F 00 08 07 00 02 01 01 07 03 4A 01 01 01 07 02 BB 00 00 04 FF 00 18 00 04 07 00 02 01 01 07 03 4A 00 00 F9 00 07 FC 00 70 07 03 A6 FB 00 48 06 0D FB 00 49 FA 00 22 02 FC 00 1A 01 FC 00 14 01 FD 00 0E 07 02 93 07 02 99 FC 00 54 07 02 A1 FC 00 35 07 02 5F FE 00 0C 07 03 52 01 01 FC 00 4B 07 02 C6 FB 00 47 3A FB 00 49 FA 00 02 F8 00 05 FE 00 0C 07 03 52 01 01 FC 00 49 07 02 C6 1B FD 00 5B 07 03 FD 07 02 C6 09 F8 00 31 FF 00 05 00 06 07 00 02 01 01 01 07 02 93 07 02 99 00 00 FF 00 02 00 02 07 00 02 01 00 00
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @SubscribeEvent
    public void onEntitySync(final EventSync event) {
        if (this.tickCounter < this.actionInterval.getValue()) {
            ++this.tickCounter;
        }
        if (event.isCanceled() || !InteractionUtil.canPlaceNormally()) {
            return;
        }
        if (this.stage == Stage.BREAKING) {
            final float[] angle = SilentRotationUtil.calcAngle(PistonAura.mc.player.getPositionEyes(PistonAura.mc.getRenderPartialTicks()), new Vec3d(this.facePos.getX() + 0.5, (double)this.facePos.getY(), this.facePos.getZ() + 0.5));
            PistonAura.mc.player.rotationYaw = angle[0];
            PistonAura.mc.player.rotationPitch = angle[1];
        }
        if (this.tickCounter < this.actionInterval.getValue()) {
            return;
        }
        this.handleAction(false);
    }
    
    @SubscribeEvent
    public void postEntitySync(final EventPostSync event) {
        if (this.postAction != null) {
            this.actionTimer.reset();
            this.tickCounter = 0;
            this.postAction.run();
            this.postAction = null;
            for (int extraBlocks = 0; extraBlocks < this.actionShift.getValue() - 1; ++extraBlocks) {
                this.handleAction(true);
                if (this.postAction == null) {
                    return;
                }
                this.postAction.run();
                this.postAction = null;
            }
        }
        this.postAction = null;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (this.crystalPos == null) {
                return;
            }
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE && this.crystalPos.up().getDistance((int)packet.getX(), (int)packet.getY(), (int)packet.getZ()) <= 2.0) {
                this.stage = Stage.SEARCHING;
                this.delayTime = 0;
            }
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.facePos == null || this.faceOffset == null) {
            return;
        }
        if (this.renderTimer.passedMs(1000L)) {
            return;
        }
        if (this.renderCurrent.getValue()) {
            BlockPos renderBlock = null;
            switch (this.stage) {
                case SEARCHING: {
                    renderBlock = this.facePos.down().offset(this.faceOffset, 2);
                    break;
                }
                case CRYSTAL:
                case BREAKING: {
                    renderBlock = this.facePos.down().offset(this.faceOffset, 1);
                    break;
                }
                case REDSTONE: {
                    renderBlock = this.facePos.down().offset(this.faceOffset, 3);
                    break;
                }
            }
            if (renderBlock != null) {
                AxisAlignedBB axisAlignedBB = PistonAura.mc.world.getBlockState(renderBlock).getBoundingBox((IBlockAccess)PistonAura.mc.world, renderBlock).offset(renderBlock);
                axisAlignedBB = axisAlignedBB.offset(-((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosZ());
                BlockRenderUtil.prepareGL();
                BlockRenderUtil.drawFill(axisAlignedBB, this.colorCurrent.getValue().getColor());
                BlockRenderUtil.releaseGL();
                BlockRenderUtil.prepareGL();
                BlockRenderUtil.drawOutline(axisAlignedBB, this.outlineColorCurrent.getValue().getColor(), 1.5f);
                BlockRenderUtil.releaseGL();
            }
        }
        if (this.renderFull.getValue()) {
            AxisAlignedBB axisAlignedBB2 = null;
            switch (this.faceOffset) {
                case NORTH: {
                    axisAlignedBB2 = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, -3.0).offset(this.facePos.down());
                    break;
                }
                case SOUTH: {
                    axisAlignedBB2 = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 3.0).offset(this.facePos.down());
                    break;
                }
                case EAST: {
                    axisAlignedBB2 = new AxisAlignedBB(0.0, 0.0, 0.0, 3.0, 1.0, 1.0).offset(this.facePos.down());
                    break;
                }
                case WEST: {
                    axisAlignedBB2 = new AxisAlignedBB(0.0, 0.0, 0.0, -3.0, 1.0, 1.0).offset(this.facePos.down());
                    break;
                }
            }
            if (axisAlignedBB2 != null) {
                axisAlignedBB2 = axisAlignedBB2.offset(-((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosZ());
                BlockRenderUtil.prepareGL();
                BlockRenderUtil.drawFill(axisAlignedBB2, this.colorFull.getValue().getColor());
                BlockRenderUtil.releaseGL();
                BlockRenderUtil.prepareGL();
                BlockRenderUtil.drawOutline(axisAlignedBB2, this.outlineColorFull.getValue().getColor(), 1.5f);
                BlockRenderUtil.releaseGL();
            }
        }
        if (this.arrow.getValue()) {
            Vec3d firstVec = null;
            Vec3d secondVec = null;
            Vec3d thirdVec = null;
            final BlockPos offsetPos = this.facePos.offset(this.faceOffset, 2);
            final Vec3d properPos = new Vec3d(offsetPos.getX() + 0.5 - ((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosX(), offsetPos.getY() + 1 - ((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosY(), offsetPos.getZ() + 0.5 - ((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosZ());
            switch (this.faceOffset) {
                case NORTH: {
                    firstVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z - 0.5);
                    secondVec = new Vec3d(properPos.x, properPos.y, properPos.z + 0.5);
                    thirdVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z - 0.5);
                    break;
                }
                case SOUTH: {
                    firstVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z + 0.5);
                    secondVec = new Vec3d(properPos.x, properPos.y, properPos.z - 0.5);
                    thirdVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z + 0.5);
                    break;
                }
                case EAST: {
                    firstVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z - 0.5);
                    secondVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z);
                    thirdVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z + 0.5);
                    break;
                }
                case WEST: {
                    firstVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z - 0.5);
                    secondVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z);
                    thirdVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z + 0.5);
                    break;
                }
            }
            if (firstVec != null) {
                BlockRenderUtil.prepareGL();
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(2896);
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glLineWidth(5.0f);
                GL11.glColor4f((this.arrowColor.getValue().getColor() >> 16 & 0xFF) / 255.0f, (this.arrowColor.getValue().getColor() >> 8 & 0xFF) / 255.0f, (this.arrowColor.getValue().getColor() & 0xFF) / 255.0f, (this.arrowColor.getValue().getColor() >> 24 & 0xFF) / 255.0f);
                if (this.topArrow.getValue()) {
                    GL11.glBegin(1);
                    GL11.glVertex3d(firstVec.x, firstVec.y, firstVec.z);
                    GL11.glVertex3d(secondVec.x, secondVec.y, secondVec.z);
                    GL11.glEnd();
                    GL11.glBegin(1);
                    GL11.glVertex3d(thirdVec.x, thirdVec.y, thirdVec.z);
                    GL11.glVertex3d(secondVec.x, secondVec.y, secondVec.z);
                    GL11.glEnd();
                }
                if (this.bottomArrow.getValue()) {
                    GL11.glBegin(1);
                    GL11.glVertex3d(firstVec.x, firstVec.y - 1.0, firstVec.z);
                    GL11.glVertex3d(secondVec.x, secondVec.y - 1.0, secondVec.z);
                    GL11.glEnd();
                    GL11.glBegin(1);
                    GL11.glVertex3d(thirdVec.x, thirdVec.y - 1.0, thirdVec.z);
                    GL11.glVertex3d(secondVec.x, secondVec.y - 1.0, secondVec.z);
                    GL11.glEnd();
                }
                GL11.glLineWidth(1.0f);
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
                BlockRenderUtil.releaseGL();
            }
        }
    }
    
    public boolean isOffhand() {
        return PistonAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
    }
    
    private boolean evaluateTarget(final EntityPlayer candidate) {
        if (this.getRedstoneBlockSlot() == -1) {
            Command.sendMessage("No redstone found!");
            this.toggle();
            return false;
        }
        BlockPos tempFacePos = new BlockPos((Entity)candidate).up();
        if (this.evaluateTarget(tempFacePos)) {
            return true;
        }
        tempFacePos = new BlockPos((Entity)candidate).up().up();
        return this.evaluateTarget(tempFacePos);
    }
    
    public boolean canPlaceCrystal(final BlockPos blockPos) {
        if (PistonAura.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && PistonAura.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        final BlockPos boost = blockPos.add(0, 1, 0);
        if (PistonAura.mc.world.getBlockState(boost).getBlock() != Blocks.AIR && PistonAura.mc.world.getBlockState(boost).getBlock() != Blocks.PISTON_HEAD) {
            return false;
        }
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        return (this.protocol.getValue() || PistonAura.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR) && PistonAura.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost, boost2.add(1, 1, 1))).isEmpty();
    }
    
    public boolean evaluateTarget(final BlockPos tempFacePos) {
        if (!this.isAir(tempFacePos) && !this.mine.getValue()) {
            return false;
        }
        for (final EnumFacing faceTryOffset : EnumFacing.HORIZONTALS) {
            this.torchPos = null;
            this.skipPiston = false;
            Label_0801: {
                if (this.canPlaceCrystal(tempFacePos.offset(faceTryOffset).down())) {
                    if (this.getRedstoneBlockSlot() == -1) {
                        return false;
                    }
                    final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(this.getRedstoneBlockSlot());
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block == Blocks.REDSTONE_BLOCK) {
                        if (!this.isAir(tempFacePos.offset(faceTryOffset, 3))) {
                            if (!this.mine.getValue() || (PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 3)).getBlock() != Blocks.REDSTONE_TORCH && PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 3)).getBlock() != Blocks.REDSTONE_BLOCK)) {
                                break Label_0801;
                            }
                            this.torchPos = tempFacePos.offset(faceTryOffset, 3);
                        }
                    }
                    else {
                        Optional<BlockUtils.ClickLocation> posCL = BlockUtils.generateClickLocation(tempFacePos.offset(faceTryOffset, 3), false, true);
                        if (!posCL.isPresent() && this.mine.getValue() && (PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 3)).getBlock() == Blocks.REDSTONE_TORCH || PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 3)).getBlock() == Blocks.REDSTONE_BLOCK)) {
                            this.torchPos = tempFacePos.offset(faceTryOffset, 3);
                        }
                        if (!posCL.isPresent() && this.torchPos == null && ((ItemBlock)PistonAura.mc.player.inventory.getStackInSlot(this.getRedstoneBlockSlot()).getItem()).getBlock() == Blocks.REDSTONE_TORCH) {
                            for (final EnumFacing torchFacing : EnumFacing.HORIZONTALS) {
                                if (!torchFacing.equals((Object)faceTryOffset)) {
                                    if (!torchFacing.equals((Object)faceTryOffset.getOpposite())) {
                                        posCL = BlockUtils.generateClickLocation(tempFacePos.offset(faceTryOffset, 2).offset(torchFacing), false, true);
                                        if (posCL.isPresent()) {
                                            break;
                                        }
                                        if (this.mine.getValue() && PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 2).offset(torchFacing)).getBlock() == Blocks.REDSTONE_TORCH) {
                                            this.torchPos = tempFacePos.offset(faceTryOffset, 2).offset(torchFacing);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (!posCL.isPresent() && this.torchPos == null) {
                            break Label_0801;
                        }
                    }
                    Optional<BlockUtils.ClickLocation> posCL = BlockUtils.generateClickLocation(tempFacePos.offset(faceTryOffset, 2));
                    this.skipPiston = (this.mine.getValue() && PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 2)).getBlock() instanceof BlockPistonBase);
                    if (posCL.isPresent() || this.skipPiston) {
                        if (!this.skipPiston) {
                            final BlockPos currentPos = posCL.get().neighbour;
                            final EnumFacing currentFace = posCL.get().opposite;
                            final double[] yawPitch = BlockUtils.calculateLookAt(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFace, (EntityPlayer)PistonAura.mc.player);
                            final EnumFacing facing = EnumFacing.fromAngle(yawPitch[0]);
                            if (Math.abs(yawPitch[1]) > 55.0) {
                                break Label_0801;
                            }
                            if (facing != faceTryOffset) {
                                break Label_0801;
                            }
                            if (this.raytrace.getValue() && !this.rayTrace(posCL.get().neighbour)) {
                                break Label_0801;
                            }
                            this.pistonNeighbour = currentPos;
                            this.pistonOffset = currentFace;
                        }
                        this.facePos = tempFacePos;
                        this.faceOffset = faceTryOffset;
                        this.crystalPos = tempFacePos.offset(faceTryOffset).down();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean rayTrace(final BlockPos pos) {
        for (double xS = 0.1; xS < 0.9; xS += 0.1) {
            for (double yS = 0.1; yS < 0.9; yS += 0.1) {
                for (double zS = 0.1; zS < 0.9; zS += 0.1) {
                    final Vec3d eyesPos = new Vec3d(PistonAura.mc.player.posX, PistonAura.mc.player.getEntityBoundingBox().minY + PistonAura.mc.player.getEyeHeight(), PistonAura.mc.player.posZ);
                    final Vec3d posVec = new Vec3d((Vec3i)pos).add(xS, yS, zS);
                    final double distToPosVec = eyesPos.distanceTo(posVec);
                    final double diffX = posVec.x - eyesPos.x;
                    final double diffY = posVec.y - eyesPos.y;
                    final double diffZ = posVec.z - eyesPos.z;
                    final double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
                    final double[] tempPlaceRotation = { MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))) };
                    final float yawCos = MathHelper.cos((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                    final float yawSin = MathHelper.sin((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                    final float pitchCos = -MathHelper.cos((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                    final float pitchSin = MathHelper.sin((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                    final Vec3d rotationVec = new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
                    final Vec3d eyesRotationVec = eyesPos.add(rotationVec.x * distToPosVec, rotationVec.y * distToPosVec, rotationVec.z * distToPosVec);
                    final RayTraceResult rayTraceResult = PistonAura.mc.world.rayTraceBlocks(eyesPos, eyesRotationVec, false, false, false);
                    if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceResult.getBlockPos().equals((Object)pos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean isAir(final BlockPos pos) {
        return PistonAura.mc.world.getBlockState(pos).getBlock() instanceof BlockAir;
    }
    
    private List<EntityPlayer> getTargets() {
        return (List<EntityPlayer>)PistonAura.mc.world.playerEntities.stream().filter(entityPlayer -> !Thunderhack.friendManager.isFriend(entityPlayer.getName())).filter(entityPlayer -> entityPlayer != PistonAura.mc.player).filter(e -> PistonAura.mc.player.getDistance(e) < this.targetRange.getValue()).sorted(Comparator.comparing(e -> PistonAura.mc.player.getDistance(e))).collect(Collectors.toList());
    }
    
    private int getRedstoneBlockSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block == Blocks.REDSTONE_BLOCK || block == Blocks.REDSTONE_TORCH) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    static {
        PistonAura.INSTANCE = new PistonAura();
    }
    
    private enum Mode
    {
        DAMAGE, 
        PUSH;
    }
    
    private enum Stage
    {
        SEARCHING, 
        CRYSTAL, 
        REDSTONE, 
        BREAKING, 
        EXPLOSION;
    }
}
