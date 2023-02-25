package com.github.dayzminecraft.dayzminecraft.common.items.misc;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemBloodbag extends Item {
    public ItemBloodbag() {
        this.setUnlocalizedName("healBloodbag");
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(DayZ.meta.modId + ":" + getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean advancedItemTooltips) {
        list.add(StatCollector.translateToLocal("item.healBloodbag.description"));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        entityPlayer.heal(20F);
        itemStack.stackSize--;
        return itemStack;
    }
}
