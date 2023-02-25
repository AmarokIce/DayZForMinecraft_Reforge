package com.github.dayzminecraft.dayzminecraft.common.items.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class ItemHeal extends ItemMod {
    private final int healAmount;
    private final Potion[] potionsToStop;

    public ItemHeal(int amountToHeal, Potion... potionsToStop) {
        super();
        healAmount = amountToHeal;
        this.potionsToStop = potionsToStop;
    }

    public int getHealAmount() {
        return healAmount;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        itemStack.stackSize--;
        entityPlayer.heal(healAmount);
        entityPlayer.clearActivePotions();
        for (Potion potion : potionsToStop) entityPlayer.removePotionEffect(potion.id);
        return itemStack;
    }
}
