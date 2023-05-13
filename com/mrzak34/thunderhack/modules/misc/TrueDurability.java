//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.misc;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.ffp.*;
import com.mrzak34.thunderhack.*;
import net.minecraftforge.event.entity.player.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.common.eventhandler.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.network.play.server.*;
import java.util.*;

public class TrueDurability extends Module implements PacketListener
{
    public TrueDurability() {
        super("TrueDurability", "\u0440\u0435\u0430\u043b\u044c\u043d\u0430\u044f \u043f\u0440\u043e\u0447\u043d\u043e\u0441\u0442\u044c-\u043d\u0435\u043b\u0435\u0433\u0430\u043b\u044c\u043d\u044b\u0445 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432", Category.PLAYER);
    }
    
    @Override
    public void onUpdate() {
        if (TrueDurability.mc.world != null && TrueDurability.mc.player != null) {
            Thunderhack.networkHandler.registerListener(EnumPacketDirection.CLIENTBOUND, this, 20, 22, 63);
        }
    }
    
    @Override
    public void onEnable() {
        Thunderhack.networkHandler.registerListener(EnumPacketDirection.CLIENTBOUND, this, 20, 22, 63);
    }
    
    @Override
    public void onDisable() {
        Thunderhack.networkHandler.unregisterListener(EnumPacketDirection.CLIENTBOUND, this, 20, 22, 63);
    }
    
    @SubscribeEvent
    public void itemToolTip(final ItemTooltipEvent event) {
        final ItemStack stack = event.getItemStack();
        final int max = stack.getMaxDamage();
        if (stack.isEmpty() || max <= 0) {
            return;
        }
        if (stack.hasTagCompound()) {
            assert stack.getTagCompound() != null;
            if (stack.getTagCompound().getBoolean("Unbreakable")) {
                return;
            }
        }
        final List<String> toolTip = (List<String>)event.getToolTip();
        final NBTTagCompound tag = stack.getTagCompound();
        int damage;
        if (tag instanceof SpecialTagCompound) {
            damage = ((SpecialTagCompound)tag).getTrueDamage();
        }
        else {
            damage = stack.getItemDamage();
        }
        final long count = max - (long)damage;
        TextFormatting color;
        if (damage < 0) {
            color = TextFormatting.DARK_PURPLE;
        }
        else if (damage > max) {
            color = TextFormatting.DARK_RED;
        }
        else {
            color = TextFormatting.BLUE;
        }
        toolTip.add("");
        toolTip.add(color + "Durability: " + count + " [Max: " + Long.toString(max) + "]" + TextFormatting.RESET);
    }
    
    @Override
    public Packet<?> packetReceived(final EnumPacketDirection direction, final int id, final Packet<?> packet, final ByteBuf in) {
        switch (id) {
            case 20: {
                final SPacketWindowItems packet_window = (SPacketWindowItems)packet;
                final PacketBuffer buf = new PacketBuffer(in);
                buf.readerIndex(buf.readerIndex() + 4);
                for (final ItemStack i : packet_window.getItemStacks()) {
                    if (buf.readShort() >= 0) {
                        buf.readerIndex(buf.readerIndex() + 1);
                        final short true_damage = buf.readShort();
                        try {
                            if (true_damage < 0) {
                                i.setTagCompound((NBTTagCompound)new SpecialTagCompound(buf.readCompoundTag(), true_damage));
                            }
                            else {
                                buf.readCompoundTag();
                            }
                        }
                        catch (IOException e) {
                            break;
                        }
                    }
                }
                break;
            }
            case 22: {
                final SPacketSetSlot packet_slot = (SPacketSetSlot)packet;
                final PacketBuffer buf = new PacketBuffer(in);
                buf.readerIndex(buf.readerIndex() + 4);
                if (buf.readShort() >= 0) {
                    buf.readerIndex(buf.readerIndex() + 1);
                    final short real_damage = buf.readShort();
                    if (real_damage < 0) {
                        final ItemStack stack = packet_slot.getStack();
                        stack.setTagCompound((NBTTagCompound)new SpecialTagCompound(stack.getTagCompound(), real_damage));
                    }
                }
                break;
            }
            case 63: {
                final SPacketEntityEquipment equipment = (SPacketEntityEquipment)packet;
                final PacketBuffer buf = new PacketBuffer(in);
                buf.readerIndex(buf.readerIndex() + 3 + (int)Math.floor(Math.log(equipment.getEntityID()) / Math.log(128.0)));
                if (buf.readShort() < 0) {
                    break;
                }
                buf.readerIndex(buf.readerIndex() + 1);
                final short real_damage = buf.readShort();
                if (real_damage < 0) {
                    final ItemStack stack = equipment.getItemStack();
                    stack.setTagCompound((NBTTagCompound)new SpecialTagCompound(stack.getTagCompound(), real_damage));
                    break;
                }
                break;
            }
        }
        return packet;
    }
}
