package com.github.dayzminecraft.dayzminecraft.common.blocks;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class BlockFence extends BlockPane {
    public BlockFence(String texture, Material material) {
        super(DayZ.meta.modId + ":" + texture.substring(texture.indexOf(".") + 1), DayZ.meta.modId + ":" + texture.substring(texture.indexOf(".") + 1), material, false);
        setCreativeTab(DayZ.creativeTab);
    }
}