//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import java.util.function.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

public class BlockStateHelper implements IBlockStateHelper
{
    private final Map<BlockPos, IBlockState> states;
    private final Supplier<IBlockAccess> world;
    
    public BlockStateHelper() {
        this(new HashMap<BlockPos, IBlockState>());
    }
    
    public BlockStateHelper(final Supplier<IBlockAccess> world) {
        this(new HashMap<BlockPos, IBlockState>(), world);
    }
    
    public BlockStateHelper(final Map<BlockPos, IBlockState> stateMap) {
        this(stateMap, () -> Util.mc.world);
    }
    
    public BlockStateHelper(final Map<BlockPos, IBlockState> stateMap, final Supplier<IBlockAccess> world) {
        this.states = stateMap;
        this.world = world;
    }
    
    public IBlockState getBlockState(final BlockPos pos) {
        final IBlockState state = this.states.get(pos);
        if (state == null) {
            return this.world.get().getBlockState(pos);
        }
        return state;
    }
    
    @Override
    public void addBlockState(final BlockPos pos, final IBlockState state) {
        this.states.putIfAbsent(pos.toImmutable(), state);
    }
    
    public TileEntity getTileEntity(final BlockPos pos) {
        return this.world.get().getTileEntity(pos);
    }
    
    public int getCombinedLight(final BlockPos pos, final int lightValue) {
        return this.world.get().getCombinedLight(pos, lightValue);
    }
    
    public boolean isAirBlock(final BlockPos pos) {
        return this.getBlockState(pos).getBlock().isAir(this.getBlockState(pos), (IBlockAccess)this, pos);
    }
    
    public Biome getBiome(final BlockPos pos) {
        return this.world.get().getBiome(pos);
    }
    
    public int getStrongPower(final BlockPos pos, final EnumFacing direction) {
        return this.getBlockState(pos).getStrongPower((IBlockAccess)this, pos, direction);
    }
    
    public WorldType getWorldType() {
        return this.world.get().getWorldType();
    }
    
    public boolean isSideSolid(final BlockPos pos, final EnumFacing side, final boolean _default) {
        if (!Util.mc.world.isValid(pos)) {
            return _default;
        }
        final Chunk chunk = Util.mc.world.getChunk(pos);
        if (chunk == null || chunk.isEmpty()) {
            return _default;
        }
        return this.getBlockState(pos).isSideSolid((IBlockAccess)this, pos, side);
    }
}
