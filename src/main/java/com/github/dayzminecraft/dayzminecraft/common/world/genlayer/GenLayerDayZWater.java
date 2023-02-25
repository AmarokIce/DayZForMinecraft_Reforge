package com.github.dayzminecraft.dayzminecraft.common.world.genlayer;

import com.github.dayzminecraft.dayzminecraft.common.world.WorldTypes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDayZWater extends GenLayer {

    private final GenLayer field_35512_b;
    private final GenLayer field_35513_c;
    private final WorldTypes worldType;

    public GenLayerDayZWater(long l, GenLayer genlayer, GenLayer genlayer1, WorldTypes worldtype) {
        super(l);
        field_35512_b = genlayer;
        field_35513_c = genlayer1;
        worldType = worldtype;
    }

    @Override
    public void initWorldGenSeed(long par1) {
        field_35512_b.initWorldGenSeed(par1);
        field_35513_c.initWorldGenSeed(par1);
        super.initWorldGenSeed(par1);
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] ai = field_35512_b.getInts(par1, par2, par3, par4);
        int[] ai1 = field_35513_c.getInts(par1, par2, par3, par4);
        int[] ai2 = IntCache.getIntCache(par3 * par4);

        for (int i = 0; i < par3 * par4; i++) {
            if (ai1[i] >= 0) {
                BiomeGenBase river = worldType.setRiverBiomes();
                if (river != null) ai2[i] = river.biomeID;
                else ai2[i] = ai1[i];
            } else ai2[i] = ai[i];
        }

        return ai2;
    }
}
