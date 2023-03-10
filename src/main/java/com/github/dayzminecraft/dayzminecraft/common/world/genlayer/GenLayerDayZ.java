package com.github.dayzminecraft.dayzminecraft.common.world.genlayer;

import com.github.dayzminecraft.dayzminecraft.common.world.WorldTypes;
import net.minecraft.world.gen.layer.*;

public abstract class GenLayerDayZ extends GenLayer {
    public GenLayerDayZ(long seed) {
        super(seed);
    }

    public static GenLayer[] getGenLayers(long l, WorldTypes worldtype) {
        GenLayer genlayer = new GenLayerIsland(1L);
        genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
        genlayer = new GenLayerDayZOcean1(1L, genlayer);
        genlayer = new GenLayerZoom(2001L, genlayer);
        genlayer = new GenLayerDayZOcean1(2L, genlayer);
        genlayer = new GenLayerZoom(2002L, genlayer);
        genlayer = new GenLayerDayZOcean1(3L, genlayer);
        genlayer = new GenLayerZoom(2003L, genlayer);
        genlayer = new GenLayerDayZOcean1(4L, genlayer);
        byte byte0 = 4;
        GenLayer genlayer1 = genlayer;
        genlayer1 = GenLayerZoom.magnify(1000L, genlayer1, 0);
        genlayer1 = new GenLayerDayZOcean2(100L, genlayer1);
        genlayer1 = GenLayerZoom.magnify(1000L, genlayer1, byte0 + 2);
        genlayer1 = new GenLayerDayZRiver(1L, genlayer1, worldtype);
        genlayer1 = new GenLayerSmooth(1000L, genlayer1);
        GenLayer genlayer2 = genlayer;
        genlayer2 = GenLayerZoom.magnify(1000L, genlayer2, 0);
        genlayer2 = new GenLayerDayZMajorBiome(200L, genlayer2, worldtype);
        genlayer2 = GenLayerZoom.magnify(1000L, genlayer2, 2);
        genlayer2 = new GenLayerDayZMinorBiome(1000L, genlayer2, worldtype);
        genlayer2 = new GenLayerDayZMiscBiome(5L, genlayer2);
        for (int i = 0; i < byte0; i++) {
            genlayer2 = new GenLayerZoom(1000 + i, genlayer2);
            if (i == 0) genlayer2 = new GenLayerDayZOcean1(3L, genlayer2);
            if (i == 1) genlayer2 = new GenLayerDayZBorderBiome(1000L, genlayer2);
        }
        genlayer2 = new GenLayerSmooth(1000L, genlayer2);
        genlayer2 = new GenLayerDayZWater(100L, genlayer2, genlayer1, worldtype);
        GenLayerDayZWater genlayerdimensionwater = ((GenLayerDayZWater) (genlayer2));
        GenLayerVoronoiZoom genlayervoronoizoom = new GenLayerVoronoiZoom(10L, genlayer2);
        genlayer2.initWorldGenSeed(l);
        genlayervoronoizoom.initWorldGenSeed(l);
        GenLayer genlayer3 = genlayer2;
        return (new GenLayer[]{genlayer3, genlayervoronoizoom, genlayerdimensionwater});
    }
}
