package com.github.dayzminecraft.dayzminecraft.common.world.generation;

import com.github.dayzminecraft.dayzminecraft.common.world.generation.structures.IStructure;
import com.github.dayzminecraft.dayzminecraft.common.world.generation.structures.StructureFort;
import com.github.dayzminecraft.dayzminecraft.common.world.generation.structures.StructureHouse;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StructureHandler {
    private static final List<IStructure> structures = new ArrayList<>();

    public static void generateStructure(World world, Random rand, int xCoord, int yCoord, int zCoord) {
        structures.get(rand.nextInt(structures.size())).generate(world, rand, xCoord, yCoord, zCoord);
    }

    public static void addStructure(IStructure structure) {
        structures.add(structure);
    }

    public static void addDefaultStructures() {
        addStructure(new StructureFort());
        addStructure(new StructureHouse());
    }
}
