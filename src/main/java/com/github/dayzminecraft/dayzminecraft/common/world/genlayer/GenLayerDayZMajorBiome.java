package com.github.dayzminecraft.dayzminecraft.common.world.genlayer;

import com.github.dayzminecraft.dayzminecraft.common.world.WorldTypes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDayZMajorBiome extends GenLayer {
    private final BiomeGenBase[] allowedBiomes;

    public GenLayerDayZMajorBiome(long l, GenLayer genlayer, WorldTypes worldtype) {
        super(l);
        parent = genlayer;
        allowedBiomes = worldtype.setMajorBiomes();
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] ai1 = IntCache.getIntCache(par3 * par4);

        for (int i = 0; i < par4; i++) {
            for (int j = 0; j < par3; j++) {
                initChunkSeed(j + par1, i + par2);
                ai1[j + i * par3] = allowedBiomes[nextInt(allowedBiomes.length)].biomeID;
            }
        }

        return ai1;
    }
}
