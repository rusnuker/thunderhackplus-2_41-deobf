//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.manager.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.movement.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.events.*;
import java.util.*;

@Mixin(value = { EntityPlayerSP.class }, priority = 9998)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer implements IEntityPlayerSP
{
    @Shadow
    public final NetHandlerPlayClient connection;
    public Runnable auraCallBack;
    boolean pre_sprint_state;
    private boolean updateLock;
    
    public MixinEntityPlayerSP(final Minecraft p_i47378_1_, final World p_i47378_2_, final NetHandlerPlayClient p_i47378_3_, final StatisticsManager p_i47378_4_, final RecipeBook p_i47378_5_, final NetHandlerPlayClient connection) {
        super(p_i47378_2_, p_i47378_3_.getGameProfile());
        this.pre_sprint_state = false;
        this.updateLock = false;
        this.connection = p_i47378_3_;
    }
    
    @Shadow
    public abstract boolean isSneaking();
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
    private boolean redirectIsCurrentViewEntity(final EntityPlayerSP entityPlayerSP) {
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return entityPlayerSP == Util.mc.player;
        }
        return Util.mc.getRenderViewEntity() == entityPlayerSP;
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("HEAD") })
    private void updateHook(final CallbackInfo info) {
        final PlayerUpdateEvent playerUpdateEvent = new PlayerUpdateEvent();
        MinecraftForge.EVENT_BUS.post((Event)playerUpdateEvent);
    }
    
    @Shadow
    protected abstract void onUpdateWalkingPlayer();
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.onUpdateWalkingPlayer()V", ordinal = 0, shift = At.Shift.AFTER) }, cancellable = true)
    private void PostUpdateHook(final CallbackInfo info) {
        if (this.updateLock) {
            return;
        }
        final PostPlayerUpdateEvent playerUpdateEvent = new PostPlayerUpdateEvent();
        MinecraftForge.EVENT_BUS.post((Event)playerUpdateEvent);
        if (playerUpdateEvent.isCanceled()) {
            info.cancel();
            if (playerUpdateEvent.getIterations() > 0) {
                for (int i = 0; i < playerUpdateEvent.getIterations(); ++i) {
                    this.updateLock = true;
                    this.onUpdate();
                    this.updateLock = false;
                    this.onUpdateWalkingPlayer();
                }
            }
        }
    }
    
    @Redirect(method = { "updateEntityActionState" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
    private boolean redirectIsCurrentViewEntity2(final EntityPlayerSP entityPlayerSP) {
        final Minecraft mc = Minecraft.getMinecraft();
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return entityPlayerSP == mc.player;
        }
        return mc.getRenderViewEntity() == entityPlayerSP;
    }
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    private void pushOutOfBlocksHook(final double x, final double y, final double z, final CallbackInfoReturnable<Boolean> info) {
        final PushEvent event = new PushEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") }, cancellable = true)
    private void preMotion(final CallbackInfo info) {
        final EventSync event = new EventSync(this.rotationYaw, this.rotationPitch, this.onGround);
        MinecraftForge.EVENT_BUS.post((Event)event);
        final EventSprint e = new EventSprint(this.isSprinting());
        MinecraftForge.EVENT_BUS.post((Event)e);
        if (e.getSprintState() != ((com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP)Util.mc.player).getServerSprintState()) {
            if (e.getSprintState()) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            ((com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP)Util.mc.player).setServerSprintState(e.getSprintState());
        }
        this.pre_sprint_state = ((com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP)Util.mc.player).getServerSprintState();
        EventManager.lock_sprint = true;
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "move" }, at = { @At("HEAD") }, cancellable = true)
    private void movePre(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        final EventMove event = new EventMove(type, x, y, z);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            super.move(type, event.get_x(), event.get_y(), event.get_z());
            info.cancel();
        }
        if (((Speed)Thunderhack.moduleManager.getModuleByClass((Class)Speed.class)).isEnabled() || ((Strafe)Thunderhack.moduleManager.getModuleByClass((Class)Strafe.class)).isEnabled()) {
            final AxisAlignedBB before = this.getEntityBoundingBox();
            final boolean predictGround = !Util.mc.world.getCollisionBoxes((Entity)Util.mc.player, Util.mc.player.getEntityBoundingBox().offset(0.0, -0.228, 0.0)).isEmpty() && this.fallDistance > 0.1f && !Util.mc.player.onGround;
            final MatrixMove move = new MatrixMove(Util.mc.player.posX, Util.mc.player.posY, Util.mc.player.posZ, x, y, z, predictGround, before);
            MinecraftForge.EVENT_BUS.post((Event)move);
            if (move.isCanceled()) {
                super.move(type, move.getMotionX(), move.getMotionY(), move.getMotionZ());
                info.cancel();
            }
        }
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") })
    private void postMotion(final CallbackInfo info) {
        ((com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP)Util.mc.player).setServerSprintState(this.pre_sprint_state);
        EventManager.lock_sprint = false;
        final EventPostSync event = new EventPostSync();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.getPostEvents().isEmpty()) {
            for (final Runnable runnable : event.getPostEvents()) {
                Minecraft.getMinecraft().addScheduledTask(runnable);
            }
        }
    }
    
    public void setAuraCallback(final Runnable auraCallBack) {
        this.auraCallBack = auraCallBack;
    }
}
