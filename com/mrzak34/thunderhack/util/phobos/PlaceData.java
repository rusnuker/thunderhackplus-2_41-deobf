//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import java.util.function.*;
import java.util.*;

public class PlaceData
{
    private final Map<EntityPlayer, ForceData> force;
    private final Map<EntityPlayer, List<PositionData>> corr;
    private final Map<BlockPos, PositionData> obby;
    private final Map<BlockPos, PositionData> liquidObby;
    private final List<PositionData> liquid;
    private final Set<PositionData> data;
    private final Set<AntiTotemData> antiTotem;
    private final Set<PositionData> shieldData;
    private final Set<PositionData> raytraceData;
    private final float minDamage;
    private EntityPlayer shieldPlayer;
    private float highestSelfDamage;
    private EntityPlayer target;
    
    public PlaceData(final float minDamage) {
        this.force = new HashMap<EntityPlayer, ForceData>();
        this.corr = new HashMap<EntityPlayer, List<PositionData>>();
        this.obby = new HashMap<BlockPos, PositionData>();
        this.liquidObby = new HashMap<BlockPos, PositionData>();
        this.liquid = new ArrayList<PositionData>();
        this.data = new TreeSet<PositionData>();
        this.antiTotem = new TreeSet<AntiTotemData>();
        this.shieldData = new TreeSet<PositionData>();
        this.raytraceData = new TreeSet<PositionData>();
        this.minDamage = minDamage;
    }
    
    public EntityPlayer getShieldPlayer() {
        if (this.shieldPlayer == null) {
            this.shieldPlayer = new ShieldPlayer((World)Minecraft.getMinecraft().world);
        }
        return this.shieldPlayer;
    }
    
    public void addAntiTotem(final AntiTotemData data) {
        this.antiTotem.add(data);
    }
    
    public void addCorrespondingData(final EntityPlayer player, final PositionData data) {
        final List<PositionData> list = this.corr.computeIfAbsent(player, v -> new ArrayList());
        list.add(data);
    }
    
    public void confirmHighDamageForce(final EntityPlayer player) {
        final ForceData data = this.force.computeIfAbsent(player, v -> new ForceData());
        data.setPossibleHighDamage(true);
    }
    
    public void confirmPossibleAntiTotem(final EntityPlayer player) {
        final ForceData data = this.force.computeIfAbsent(player, v -> new ForceData());
        data.setPossibleAntiTotem(true);
    }
    
    public void addForceData(final EntityPlayer player, final ForcePosition forceIn) {
        final ForceData data = this.force.computeIfAbsent(player, v -> new ForceData());
        data.getForceData().add(forceIn);
    }
    
    public void addAllCorrespondingData() {
        for (final AntiTotemData antiTotemData : this.antiTotem) {
            for (final EntityPlayer player : antiTotemData.getAntiTotems()) {
                final List<PositionData> corresponding = this.corr.get(player);
                if (corresponding != null) {
                    corresponding.forEach(antiTotemData::addCorrespondingData);
                }
            }
        }
    }
    
    public float getMinDamage() {
        return this.minDamage;
    }
    
    public EntityPlayer getTarget() {
        return this.target;
    }
    
    public void setTarget(final EntityPlayer target) {
        this.target = target;
    }
    
    public Set<AntiTotemData> getAntiTotem() {
        return this.antiTotem;
    }
    
    public Set<PositionData> getData() {
        return this.data;
    }
    
    public Map<BlockPos, PositionData> getAllObbyData() {
        return this.obby;
    }
    
    public Map<EntityPlayer, ForceData> getForceData() {
        return this.force;
    }
    
    public List<PositionData> getLiquid() {
        return this.liquid;
    }
    
    public Map<BlockPos, PositionData> getLiquidObby() {
        return this.liquidObby;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("PlaceData:\n");
        for (final PositionData data : this.data) {
            builder.append("Position: ").append(data.getPos()).append("\n");
        }
        return builder.toString();
    }
    
    public float getHighestSelfDamage() {
        return this.highestSelfDamage;
    }
    
    public void setHighestSelfDamage(final float highestSelfDamage) {
        this.highestSelfDamage = highestSelfDamage;
    }
    
    public Set<PositionData> getShieldData() {
        return this.shieldData;
    }
    
    public Set<PositionData> getRaytraceData() {
        return this.raytraceData;
    }
}
