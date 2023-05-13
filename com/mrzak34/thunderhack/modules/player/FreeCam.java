//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.events.*;

public class FreeCam extends Module
{
    private static FreeCam INSTANCE;
    public Setting<SubBind> movePlayer;
    private final Setting<Float> hSpeed;
    private final Setting<Float> vSpeed;
    private final Setting<Boolean> follow;
    private final Setting<Boolean> copyInventory;
    private Entity cachedActiveEntity;
    private int lastActiveTick;
    private Entity oldRenderEntity;
    private FreecamCamera camera;
    private final MovementInput cameraMovement;
    private final MovementInput playerMovement;
    
    public FreeCam() {
        super("FreeCam", "\u0441\u0432\u043e\u0431\u043e\u043d\u0430\u044f \u043a\u0430\u043c\u0435\u0440\u0430", Module.Category.PLAYER);
        this.movePlayer = (Setting<SubBind>)this.register(new Setting("Control", (T)new SubBind(56)));
        this.hSpeed = (Setting<Float>)this.register(new Setting("HSpeed", (T)1.0f, (T)0.2f, (T)2.0f));
        this.vSpeed = (Setting<Float>)this.register(new Setting("VSpeed", (T)1.0f, (T)0.2f, (T)2.0f));
        this.follow = (Setting<Boolean>)this.register(new Setting("Follow", (T)false));
        this.copyInventory = (Setting<Boolean>)this.register(new Setting("CopyInv", (T)false));
        this.cachedActiveEntity = null;
        this.lastActiveTick = -1;
        this.oldRenderEntity = null;
        this.camera = null;
        this.cameraMovement = (MovementInput)new MovementInputFromOptions(FreeCam.mc.gameSettings) {
            public void updatePlayerMoveState() {
                if (!PlayerUtils.isKeyDown(FreeCam.this.movePlayer.getValue().getKey())) {
                    super.updatePlayerMoveState();
                }
                else {
                    this.moveStrafe = 0.0f;
                    this.moveForward = 0.0f;
                    this.forwardKeyDown = false;
                    this.backKeyDown = false;
                    this.leftKeyDown = false;
                    this.rightKeyDown = false;
                    this.jump = false;
                    this.sneak = false;
                }
            }
        };
        this.playerMovement = (MovementInput)new MovementInputFromOptions(FreeCam.mc.gameSettings) {
            public void updatePlayerMoveState() {
                if (PlayerUtils.isKeyDown(FreeCam.this.movePlayer.getValue().getKey())) {
                    super.updatePlayerMoveState();
                }
                else {
                    this.moveStrafe = 0.0f;
                    this.moveForward = 0.0f;
                    this.forwardKeyDown = false;
                    this.backKeyDown = false;
                    this.leftKeyDown = false;
                    this.rightKeyDown = false;
                    this.jump = false;
                    this.sneak = false;
                }
            }
        };
        this.setInstance();
    }
    
    public static FreeCam getInstance() {
        if (FreeCam.INSTANCE == null) {
            FreeCam.INSTANCE = new FreeCam();
        }
        return FreeCam.INSTANCE;
    }
    
    private void setInstance() {
        FreeCam.INSTANCE = this;
    }
    
    public Entity getActiveEntity() {
        if (this.cachedActiveEntity == null) {
            this.cachedActiveEntity = (Entity)FreeCam.mc.player;
        }
        final int currentTick = FreeCam.mc.player.ticksExisted;
        if (this.lastActiveTick != currentTick) {
            this.lastActiveTick = currentTick;
            if (this.isEnabled()) {
                if (PlayerUtils.isKeyDown(this.movePlayer.getValue().getKey())) {
                    this.cachedActiveEntity = (Entity)FreeCam.mc.player;
                }
                else {
                    this.cachedActiveEntity = (Entity)((FreeCam.mc.getRenderViewEntity() == null) ? FreeCam.mc.player : FreeCam.mc.getRenderViewEntity());
                }
            }
            else {
                this.cachedActiveEntity = (Entity)FreeCam.mc.player;
            }
        }
        return this.cachedActiveEntity;
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Unload event) {
        FreeCam.mc.setRenderViewEntity((Entity)FreeCam.mc.player);
        this.toggle();
    }
    
    @SubscribeEvent
    public void onFreecam(final FreecamEvent event) {
        event.setCanceled(true);
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent e) {
        final ScaledResolution sr = new ScaledResolution(FreeCam.mc);
        final String yCoord = "" + -Math.round(FreeCam.mc.player.posY - this.getActiveEntity().posY);
        final String str = ".vclip " + yCoord;
        FontRender.drawString6(str, (float)((sr.getScaledWidth() - FontRender.getStringWidth6(str)) / 1.98), (float)(sr.getScaledHeight() / 1.8 - 20.0), -1, false);
    }
    
    public void onUpdate() {
        if (FreeCam.mc.player == null || FreeCam.mc.world == null) {
            return;
        }
        this.camera.setCopyInventory(this.copyInventory.getValue());
        this.camera.setFollow(this.follow.getValue());
        this.camera.sethSpeed(this.hSpeed.getValue());
        this.camera.setvSpeed(this.vSpeed.getValue());
    }
    
    public void onEnable() {
        if (FreeCam.mc.player == null) {
            return;
        }
        this.camera = new FreecamCamera(this.copyInventory.getValue(), this.follow.getValue(), this.hSpeed.getValue(), this.vSpeed.getValue());
        this.camera.movementInput = this.cameraMovement;
        FreeCam.mc.player.movementInput = this.playerMovement;
        FreeCam.mc.world.addEntityToWorld(-921, (Entity)this.camera);
        this.oldRenderEntity = FreeCam.mc.getRenderViewEntity();
        FreeCam.mc.setRenderViewEntity((Entity)this.camera);
        FreeCam.mc.renderChunksMany = false;
    }
    
    public void onDisable() {
        if (FreeCam.mc.player == null) {
            return;
        }
        if (this.camera != null) {
            FreeCam.mc.world.removeEntity((Entity)this.camera);
        }
        this.camera = null;
        FreeCam.mc.player.movementInput = (MovementInput)new MovementInputFromOptions(FreeCam.mc.gameSettings);
        FreeCam.mc.setRenderViewEntity(this.oldRenderEntity);
        FreeCam.mc.renderChunksMany = true;
    }
    
    @SubscribeEvent
    public void onRenderOverlay(final RenderItemOverlayEvent event) {
        event.setCanceled(true);
    }
    
    static {
        FreeCam.INSTANCE = new FreeCam();
    }
}
