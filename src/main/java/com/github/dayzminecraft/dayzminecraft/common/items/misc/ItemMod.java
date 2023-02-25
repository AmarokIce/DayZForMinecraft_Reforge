package com.github.dayzminecraft.dayzminecraft.common.items.misc;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemMod extends Item {
    public List<String> subNames;

    public ItemMod() {
        super();
        maxStackSize = 1;
        setCreativeTab(DayZ.creativeTab);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(DayZ.meta.modId + ":" + getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return itemIcon;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List informationList, boolean advancedItemTooltips) {
        if (subNames == null) return;
        for (String string : subNames) informationList.add(string);
    }

    public Item subNames(String... strings) {
        if (subNames == null) subNames = new ArrayList<>();

        Collections.addAll(subNames, strings);
        return this;
    }
}
