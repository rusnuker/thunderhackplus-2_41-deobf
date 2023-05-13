//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import com.mojang.authlib.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.util.math.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;

public class FakePlayer extends Module
{
    protected final List<PositionforFP> positions;
    private final ItemStack[] armors;
    public Setting<Integer> vulnerabilityTick;
    public Setting<Integer> resetHealth;
    public Setting<Integer> tickRegenVal;
    public Setting<Integer> startHealth;
    public Setting<String> nameFakePlayer;
    int incr;
    EntityOtherPlayerMP clonedPlayer;
    boolean beforePressed;
    ArrayList<playerInfo> listPlayers;
    int index;
    private final Setting<Boolean> copyInventory;
    private final Setting<Boolean> playerStacked;
    private final Setting<Boolean> onShift;
    private final Setting<Boolean> simulateDamage;
    private final Setting<Boolean> resistance;
    private final Setting<Boolean> pop;
    private final Setting<Boolean> record2;
    private final Setting<Boolean> play;
    private int ticks;
    
    public FakePlayer() {
        super("FakePlayer", "\u0444\u0435\u0439\u043a\u043f\u043b\u0435\u0435\u0440 \u0434\u043b\u044f \u0442\u0435\u0441\u0442\u043e\u0432", Module.Category.PLAYER);
        this.positions = new ArrayList<PositionforFP>();
        this.armors = new ItemStack[] { new ItemStack((Item)Items.DIAMOND_BOOTS), new ItemStack((Item)Items.DIAMOND_LEGGINGS), new ItemStack((Item)Items.DIAMOND_CHESTPLATE), new ItemStack((Item)Items.DIAMOND_HELMET) };
        this.vulnerabilityTick = (Setting<Integer>)this.register(new Setting("Vulnerability Tick", (T)4, (T)0, (T)10));
        this.resetHealth = (Setting<Integer>)this.register(new Setting("Reset Health", (T)10, (T)0, (T)36));
        this.tickRegenVal = (Setting<Integer>)this.register(new Setting("Tick Regen", (T)4, (T)0, (T)30));
        this.startHealth = (Setting<Integer>)this.register(new Setting("Start Health", (T)20, (T)0, (T)30));
        this.nameFakePlayer = (Setting<String>)this.register(new Setting("Name FakePlayer", (T)"Ebatte_Sratte"));
        this.clonedPlayer = null;
        this.listPlayers = new ArrayList<playerInfo>();
        this.index = 0;
        this.copyInventory = (Setting<Boolean>)this.register(new Setting("Copy Inventory", (T)false));
        this.playerStacked = (Setting<Boolean>)this.register(new Setting("Player Stacked", (T)true, v -> !this.copyInventory.getValue()));
        this.onShift = (Setting<Boolean>)this.register(new Setting("On Shift", (T)false));
        this.simulateDamage = (Setting<Boolean>)this.register(new Setting("Simulate Damage", (T)false));
        this.resistance = (Setting<Boolean>)this.register(new Setting("Resistance", (T)true));
        this.pop = (Setting<Boolean>)this.register(new Setting("Pop", (T)true));
        this.record2 = (Setting<Boolean>)this.register(new Setting("Record", (T)true));
        this.play = (Setting<Boolean>)this.register(new Setting("Play", (T)true));
    }
    
    public void onLogout() {
        if (this.isOn()) {
            this.disable();
        }
    }
    
    public void onEnable() {
        this.incr = 0;
        this.beforePressed = false;
        if (FakePlayer.mc.player == null || FakePlayer.mc.player.isDead) {
            this.disable();
            return;
        }
        if (!this.onShift.getValue()) {
            this.spawnPlayer();
        }
    }
    
    void spawnPlayer() {
        (this.clonedPlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), (String)this.nameFakePlayer.getValue()))).copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        this.clonedPlayer.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
        this.clonedPlayer.rotationYaw = FakePlayer.mc.player.rotationYaw;
        this.clonedPlayer.rotationPitch = FakePlayer.mc.player.rotationPitch;
        this.clonedPlayer.setGameType(GameType.SURVIVAL);
        this.clonedPlayer.setHealth((float)this.startHealth.getValue());
        FakePlayer.mc.world.addEntityToWorld(-1234 + this.incr, (Entity)this.clonedPlayer);
        ++this.incr;
        if (this.copyInventory.getValue()) {
            this.clonedPlayer.inventory.copyInventory(FakePlayer.mc.player.inventory);
        }
        else if (this.playerStacked.getValue()) {
            for (int i = 0; i < 4; ++i) {
                final ItemStack item = this.armors[i];
                item.addEnchantment((i == 3) ? Enchantments.BLAST_PROTECTION : Enchantments.PROTECTION, 4);
                this.clonedPlayer.inventory.armorInventory.set(i, (Object)item);
            }
        }
        if (this.resistance.getValue()) {
            this.clonedPlayer.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 123456789, 0));
        }
        this.clonedPlayer.onEntityUpdate();
        this.listPlayers.add(new playerInfo(this.clonedPlayer.getName()));
    }
    
    public void onUpdate() {
        if (this.onShift.getValue() && FakePlayer.mc.gameSettings.keyBindSneak.isPressed() && !this.beforePressed) {
            this.beforePressed = true;
            this.spawnPlayer();
        }
        else {
            this.beforePressed = false;
        }
        for (int i = 0; i < this.listPlayers.size(); ++i) {
            if (this.listPlayers.get(i).update()) {
                final int finalI = i;
                final Optional<EntityPlayer> temp = (Optional<EntityPlayer>)FakePlayer.mc.world.playerEntities.stream().filter(e -> e.getName().equals(this.listPlayers.get(finalI).name)).findAny();
                if (temp.isPresent() && temp.get().getHealth() < 20.0f) {
                    temp.get().setHealth(temp.get().getHealth() + 1.0f);
                }
            }
        }
    }
    
    public void onDisable() {
        if (FakePlayer.mc.world != null) {
            for (int i = 0; i < this.incr; ++i) {
                FakePlayer.mc.world.removeEntityFromWorld(-1234 + i);
            }
        }
        this.listPlayers.clear();
        this.positions.clear();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (this.simulateDamage.getValue() && event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packetSoundEffect = (SPacketSoundEffect)event.getPacket();
            if (packetSoundEffect.getCategory() == SoundCategory.BLOCKS && packetSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity entity : new ArrayList<Entity>(FakePlayer.mc.world.loadedEntityList)) {
                    if (entity instanceof EntityEnderCrystal && entity.getDistanceSq(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ()) <= 36.0) {
                        for (final EntityPlayer entityPlayer : FakePlayer.mc.world.playerEntities) {
                            if (entityPlayer.getName().equals(this.nameFakePlayer.getValue())) {
                                final Optional<playerInfo> temp = this.listPlayers.stream().filter(e -> e.name.equals(entityPlayer.getName())).findAny();
                                if (!temp.isPresent()) {
                                    continue;
                                }
                                if (!temp.get().canPop()) {
                                    continue;
                                }
                                final float damage = DamageUtil.calculateDamage(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ(), (Entity)entityPlayer, false);
                                if (damage > entityPlayer.getHealth()) {
                                    entityPlayer.setHealth((float)this.resetHealth.getValue());
                                    if (this.pop.getValue()) {
                                        FakePlayer.mc.effectRenderer.emitParticleAtEntity((Entity)entityPlayer, EnumParticleTypes.TOTEM, 30);
                                        FakePlayer.mc.world.playSound(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.0f, false);
                                    }
                                    MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(entityPlayer));
                                }
                                else {
                                    entityPlayer.setHealth(entityPlayer.getHealth() - damage);
                                }
                                temp.get().tickPop = 0;
                            }
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onMotionUpdateEvent(final EventSync event) {
        if (!this.record2.getValue()) {
            if (this.play.getValue()) {
                if (this.positions.isEmpty()) {
                    return;
                }
                if (this.index >= this.positions.size()) {
                    this.index = 0;
                }
                if (this.ticks++ % 2 == 0) {
                    final PositionforFP p = this.positions.get(this.index++);
                    this.clonedPlayer.rotationYaw = p.getYaw();
                    this.clonedPlayer.rotationPitch = p.getPitch();
                    this.clonedPlayer.rotationYawHead = p.getHead();
                    this.clonedPlayer.setPositionAndRotationDirect(p.getX(), p.getY(), p.getZ(), p.getYaw(), p.getPitch(), 3, false);
                }
            }
            else {
                this.index = 0;
            }
        }
    }
    
    @SubscribeEvent
    public void onMotionUpdateEventPost(final EventPostSync event) {
        if (this.record2.getValue() && this.ticks++ % 2 == 0) {
            this.positions.add(new PositionforFP((EntityPlayer)FakePlayer.mc.player));
        }
    }
    
    public enum movingmode
    {
        None, 
        Line, 
        Circle, 
        Random;
    }
    
    class playerInfo
    {
        final String name;
        int tickPop;
        int tickRegen;
        
        public playerInfo(final String name) {
            this.tickPop = -1;
            this.tickRegen = 0;
            this.name = name;
        }
        
        boolean update() {
            if (this.tickPop != -1 && ++this.tickPop >= FakePlayer.this.vulnerabilityTick.getValue()) {
                this.tickPop = -1;
            }
            if (++this.tickRegen >= FakePlayer.this.tickRegenVal.getValue()) {
                this.tickRegen = 0;
                return true;
            }
            return false;
        }
        
        boolean canPop() {
            return this.tickPop == -1;
        }
    }
}
