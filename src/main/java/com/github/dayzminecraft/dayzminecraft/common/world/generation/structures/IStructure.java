package com.github.dayzminecraft.dayzminecraft.common.world.generation.structures;

import net.minecraft.world.World;

import java.util.Random;

public interface IStructure {
    public boolean generate(World world, Random rand, int xCoord, int yCoord, int zCoord);
}
