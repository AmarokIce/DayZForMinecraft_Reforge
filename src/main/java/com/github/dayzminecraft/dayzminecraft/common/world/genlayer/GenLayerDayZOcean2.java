package com.github.dayzminecraft.dayzminecraft.common.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDayZOcean2 extends GenLayer {
    public GenLayerDayZOcean2(long l, GenLayer genlayer) {
        super(l);
        parent = genlayer;
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] ai1 = IntCache.getIntCache(par3 * par4);
        for (int i = 0; i < par4; i++) {
            for (int j = 0; j < par3; j++) {
                initChunkSeed(j + par1, i + par2);
                ai1[j + i * par3] = nextInt(2) + 2;
            }
        }
        return ai1;
    }
}
