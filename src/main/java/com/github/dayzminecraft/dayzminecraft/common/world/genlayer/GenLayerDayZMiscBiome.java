package com.github.dayzminecraft.dayzminecraft.common.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDayZMiscBiome extends GenLayer {
    public GenLayerDayZMiscBiome(long l, GenLayer genlayer) {
        super(l);
        parent = genlayer;
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int i = par1 - 1;
        int j = par2 - 1;
        int k = par3 + 2;
        int l = par4 + 2;
        int[] ai = parent.getInts(i, j, k, l);
        int[] ai1 = IntCache.getIntCache(par3 * par4);

        for (int i1 = 0; i1 < par4; i1++) {
            for (int j1 = 0; j1 < par3; j1++) {
                int k2 = ai[j1 + 1 + (i1 + 1) * k];
                initChunkSeed(j1 + par1, i1 + par2);
                ai1[j1 + i1 * par3] = k2;
            }
        }
        return ai1;
    }
}
