package com.github.dayzminecraft.dayzminecraft.common.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDayZOcean1 extends GenLayer {
    public GenLayerDayZOcean1(long l, GenLayer genlayer) {
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
                int k1 = ai[j1 + (i1) * k];
                int l1 = ai[j1 + 2 + (i1) * k];
                int i2 = ai[j1 + (i1 + 2) * k];
                int j2 = ai[j1 + 2 + (i1 + 2) * k];
                int k2 = ai[j1 + 1 + (i1 + 1) * k];
                initChunkSeed(j1 + par1, i1 + par2);

                if (k2 == 0 && (k1 != 0 || l1 != 0 || i2 != 0 || j2 != 0)) {
                    int l2 = 1;
                    int i3 = 1;

                    if (k1 != 0 && nextInt(l2++) == 0) i3 = k1;
                    if (l1 != 0 && nextInt(l2++) == 0) i3 = l1;
                    if (i2 != 0 && nextInt(l2++) == 0) i3 = i2;
                    if (j2 != 0 && nextInt(l2++) == 0) i3 = j2;

                    if (nextInt(3) == 0) {
                        ai1[j1 + i1 * par3] = i3;
                        continue;
                    }
                    continue;
                }

                ai1[j1 + i1 * par3] = k2;
            }
        }

        return ai1;
    }
}
