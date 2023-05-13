//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Criticals extends Module
{
    public Setting<Mode> mode;
    Timer timer;
    boolean cancelSomePackets;
    
    public Criticals() {
        super("Criticals", "\u041a\u0430\u0436\u0434\u044b\u0439 \u0443\u0434\u0430\u0440 \u0441\u0442\u0430\u043d\u0435\u0442-\u043a\u0440\u0438\u0442\u043e\u043c", Category.COMBAT);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.FunnyGame));
        this.timer = new Timer();
    }
    
    @SubscribeEvent
    public void onAttack(final AttackEvent e) {
        if (e.getStage() == 1) {
            return;
        }
        final boolean reasonToReturn = Criticals.mc.player.fallDistance > 0.08f || Criticals.mc.player.isInLava() || ((IEntity)Criticals.mc.player).isInWeb() || Criticals.mc.player.isRiding() || Criticals.mc.player.isOnLadder() || e.getEntity() instanceof EntityEnderCrystal;
        if (reasonToReturn) {
            return;
        }
        if (this.mode.getValue() == Mode.Deadcode) {
            if (!(e.getEntity() instanceof EntityPlayer)) {
                return;
            }
            if (((EntityPlayer)e.getEntity()).hurtTime >= 7) {
                return;
            }
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0625, Criticals.mc.player.posZ, true));
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
            Criticals.mc.player.onCriticalHit(e.getEntity());
        }
        if (this.mode.getValue() == Mode.Nurik && Criticals.mc.player.onGround) {
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0625 + Aura.interpolateRandom(-0.09f, 0.09f), Criticals.mc.player.posZ, false));
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + (0.001 - Math.random() / 10000.0), Criticals.mc.player.posZ, false));
        }
        if (this.mode.getValue() == Mode.FunnyGame && Criticals.mc.player.collidedVertically && this.timer.passedMs(400L)) {
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0627, Criticals.mc.player.posZ, false));
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
            Criticals.mc.player.onCriticalHit(e.getEntity());
            this.timer.reset();
            this.cancelSomePackets = true;
        }
        if (this.mode.getValue() == Mode.Strict) {
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.11, Criticals.mc.player.posZ, false));
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.1100013579, Criticals.mc.player.posZ, false));
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 1.3579E-6, Criticals.mc.player.posZ, false));
        }
        if (this.mode.getValue() == Mode.OldNCP) {
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.05000000074505806, Criticals.mc.player.posZ, false));
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.012511000037193298, Criticals.mc.player.posZ, false));
            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketPlayer && this.cancelSomePackets) {
            this.cancelSomePackets = false;
            e.setCanceled(true);
        }
    }
    
    private enum Mode
    {
        OldNCP, 
        Strict, 
        Nurik, 
        FunnyGame, 
        Deadcode;
    }
}
