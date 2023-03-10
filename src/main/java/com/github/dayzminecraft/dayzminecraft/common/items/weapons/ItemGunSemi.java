package com.github.dayzminecraft.dayzminecraft.common.items.weapons;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityBullet;
import com.github.dayzminecraft.dayzminecraft.common.items.misc.ItemMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunSemi extends ItemMod {
    private IGun gun;

    public ItemGunSemi(IGun iGun) {
        super();
        gun = iGun;
        maxStackSize = 1;
        setMaxDamage(gun.getRounds());
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
        if (itemstack.getItemDamage() < gun.getRounds()) {
            EntityBullet entitybullet = new EntityBullet(world, entityplayer, gun.getDamage());
            world.playSoundAtEntity(entityplayer, DayZ.meta.modId + ":" + "gun." + gun.getShootSound(), 1.0F, 1.0F);
            itemstack.damageItem(1, entityplayer);

            if (!world.isRemote) {
                world.spawnEntityInWorld(entitybullet);
            }
        } else if (itemstack.getItemDamage() >= gun.getRounds() && entityplayer.inventory.hasItem(gun.getAmmo())) {
            int k = getMaxItemUseDuration(itemstack) - i;
            float f1 = k / 20F;
            f1 = (f1 * f1 + f1 * 2.0F) / 3F;

            if (f1 >= 1.0F) {
                itemstack.setItemDamage(0);
                world.playSoundAtEntity(entityplayer, DayZ.meta.modId + ":" + "gun." + gun.getReloadSound(), 1.0F, 1.0F);
                entityplayer.inventory.consumeInventoryItem(gun.getAmmo());
            }
        } else {
            world.playSoundAtEntity(entityplayer, "random.click", 1.0F, 1.0F);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 0x11940;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        if (itemstack.getItemDamage() < gun.getRounds()) {
            return null;
        } else {
            return EnumAction.block;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        return itemstack;
    }
}