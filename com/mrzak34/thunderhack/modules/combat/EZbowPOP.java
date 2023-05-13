//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;

public class EZbowPOP extends Module
{
    public static Timer delayTimer;
    public final Setting<Parent> selection;
    public final Setting<Boolean> bow;
    public final Setting<Boolean> pearls;
    public final Setting<Boolean> xp;
    public final Setting<Boolean> eggs;
    public final Setting<Boolean> potions;
    public final Setting<Boolean> snowballs;
    public Setting<Boolean> rotation;
    public Setting<ModeEn> Mode;
    public Setting<Float> factor;
    public Setting<exploitEn> exploit;
    public Setting<Float> scale;
    public Setting<Boolean> minimize;
    public Setting<Float> delay;
    private final Random rnd;
    
    public EZbowPOP() {
        super("EZbowPOP", "\u0428\u043e\u0442\u0430\u0435\u0442 \u0441 \u043b\u0443\u043a\u0430", Category.COMBAT);
        this.selection = (Setting<Parent>)this.register(new Setting("Selection", (T)new Parent(false)));
        this.bow = this.register(new Setting("Bows", (T)true)).withParent(this.selection);
        this.pearls = this.register(new Setting("EPearls", (T)true)).withParent(this.selection);
        this.xp = this.register(new Setting("XP", (T)true)).withParent(this.selection);
        this.eggs = this.register(new Setting("Eggs", (T)true)).withParent(this.selection);
        this.potions = this.register(new Setting("SplashPotions", (T)true)).withParent(this.selection);
        this.snowballs = this.register(new Setting("Snowballs", (T)true)).withParent(this.selection);
        this.rotation = (Setting<Boolean>)this.register(new Setting("Rotation", (T)false));
        this.Mode = (Setting<ModeEn>)this.register(new Setting("Mode", (T)ModeEn.Maximum));
        this.factor = (Setting<Float>)this.register(new Setting("Factor", (T)1.0f, (T)1.0f, (T)20.0f));
        this.exploit = (Setting<exploitEn>)this.register(new Setting("Exploit", (T)exploitEn.Strong));
        this.scale = (Setting<Float>)this.register(new Setting("Scale", (T)0.01f, (T)0.01f, (T)0.4f));
        this.minimize = (Setting<Boolean>)this.register(new Setting("Minimize", (T)false));
        this.delay = (Setting<Float>)this.register(new Setting("Delay", (T)5.0f, (T)0.0f, (T)10.0f));
        this.rnd = new Random();
    }
    
    @SubscribeEvent
    protected void onPacketSend(final PacketEvent.Send event) {
        if (Module.fullNullCheck() || !EZbowPOP.delayTimer.passedMs((long)(this.delay.getValue() * 1000.0f))) {
            return;
        }
        if ((event.getPacket() instanceof CPacketPlayerDigging && ((CPacketPlayerDigging)event.getPacket()).getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM && EZbowPOP.mc.player.getActiveItemStack().getItem() == Items.BOW && this.bow.getValue()) || (event.getPacket() instanceof CPacketPlayerTryUseItem && ((CPacketPlayerTryUseItem)event.getPacket()).getHand() == EnumHand.MAIN_HAND && ((EZbowPOP.mc.player.getHeldItemMainhand().getItem() == Items.ENDER_PEARL && this.pearls.getValue()) || (EZbowPOP.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE && this.xp.getValue()) || (EZbowPOP.mc.player.getHeldItemMainhand().getItem() == Items.EGG && this.eggs.getValue()) || (EZbowPOP.mc.player.getHeldItemMainhand().getItem() == Items.SPLASH_POTION && this.potions.getValue()) || (EZbowPOP.mc.player.getHeldItemMainhand().getItem() == Items.SNOWBALL && this.snowballs.getValue())))) {
            EZbowPOP.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)EZbowPOP.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            final double[] strict_direction = { 100.0 * -Math.sin(Math.toRadians(EZbowPOP.mc.player.rotationYaw)), 100.0 * Math.cos(Math.toRadians(EZbowPOP.mc.player.rotationYaw)) };
            if (this.exploit.getValue() == exploitEn.Fast) {
                for (int i = 0; i < this.getRuns(); ++i) {
                    this.spoof(EZbowPOP.mc.player.posX, ((boolean)this.minimize.getValue()) ? EZbowPOP.mc.player.posY : (EZbowPOP.mc.player.posY - 1.0E-10), EZbowPOP.mc.player.posZ, true);
                    this.spoof(EZbowPOP.mc.player.posX, EZbowPOP.mc.player.posY + 1.0E-10, EZbowPOP.mc.player.posZ, false);
                }
            }
            if (this.exploit.getValue() == exploitEn.Strong) {
                for (int i = 0; i < this.getRuns(); ++i) {
                    this.spoof(EZbowPOP.mc.player.posX, EZbowPOP.mc.player.posY + 1.0E-10, EZbowPOP.mc.player.posZ, false);
                    this.spoof(EZbowPOP.mc.player.posX, ((boolean)this.minimize.getValue()) ? EZbowPOP.mc.player.posY : (EZbowPOP.mc.player.posY - 1.0E-10), EZbowPOP.mc.player.posZ, true);
                }
            }
            if (this.exploit.getValue() == exploitEn.Phobos) {
                for (int i = 0; i < this.getRuns(); ++i) {
                    this.spoof(EZbowPOP.mc.player.posX, EZbowPOP.mc.player.posY + 1.3E-13, EZbowPOP.mc.player.posZ, true);
                    this.spoof(EZbowPOP.mc.player.posX, EZbowPOP.mc.player.posY + 2.7E-13, EZbowPOP.mc.player.posZ, false);
                }
            }
            if (this.exploit.getValue() == exploitEn.Strict) {
                for (int i = 0; i < this.getRuns(); ++i) {
                    if (this.rnd.nextBoolean()) {
                        this.spoof(EZbowPOP.mc.player.posX - strict_direction[0], EZbowPOP.mc.player.posY, EZbowPOP.mc.player.posZ - strict_direction[1], false);
                    }
                    else {
                        this.spoof(EZbowPOP.mc.player.posX + strict_direction[0], EZbowPOP.mc.player.posY, EZbowPOP.mc.player.posZ + strict_direction[1], true);
                    }
                }
            }
            EZbowPOP.delayTimer.reset();
        }
    }
    
    private void spoof(final double x, final double y, final double z, final boolean ground) {
        if (this.rotation.getValue()) {
            EZbowPOP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(x, y, z, EZbowPOP.mc.player.rotationYaw, EZbowPOP.mc.player.rotationPitch, ground));
        }
        else {
            EZbowPOP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, ground));
        }
    }
    
    private int getRuns() {
        if (this.Mode.getValue() == ModeEn.Factorised) {
            return 10 + (int)(this.factor.getValue() - 1.0f);
        }
        if (this.Mode.getValue() == ModeEn.Normal) {
            return (int)Math.floor(this.factor.getValue());
        }
        if (this.Mode.getValue() == ModeEn.Maximum) {
            return (int)(30.0f * this.factor.getValue());
        }
        return 1;
    }
    
    static {
        EZbowPOP.delayTimer = new Timer();
    }
    
    private enum exploitEn
    {
        Strong, 
        Fast, 
        Strict, 
        Phobos;
    }
    
    private enum ModeEn
    {
        Normal, 
        Maximum, 
        Factorised;
    }
}
