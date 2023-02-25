package com.github.dayzminecraft.dayzminecraft.common;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import com.github.dayzminecraft.dayzminecraft.common.blocks.ModBlocks;
import com.github.dayzminecraft.dayzminecraft.common.effects.Effect;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityBullet;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityCrawler;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityZombieDayZ;
import com.github.dayzminecraft.dayzminecraft.common.items.ModItems;
import com.github.dayzminecraft.dayzminecraft.common.misc.Config;
import com.github.dayzminecraft.dayzminecraft.common.misc.DamageType;
import com.github.dayzminecraft.dayzminecraft.common.misc.LootManager;
import com.github.dayzminecraft.dayzminecraft.common.thirst.PlayerContainer;
import com.github.dayzminecraft.dayzminecraft.common.world.WorldTypes;
import com.github.dayzminecraft.dayzminecraft.common.world.biomes.Biomes;
import com.github.dayzminecraft.dayzminecraft.common.world.generation.StructureHandler;
import com.github.dayzminecraft.dayzminecraft.common.world.genlayer.GenLayerDayZ;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class CommonProxy {
    public void preload(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(DayZ.proxy);
        MinecraftForge.TERRAIN_GEN_BUS.register(DayZ.proxy);
        Config.init(event);

        ModBlocks.loadBlocks();
        ModItems.loadItems();
        WorldTypes.loadWorldTypes();
        StructureHandler.addDefaultStructures();
        Effect.register();
        EntityRegistry.registerGlobalEntityID(EntityZombieDayZ.class, "Walker", EntityRegistry.findGlobalUniqueEntityId(), 1, 2);
        EntityRegistry.registerGlobalEntityID(EntityCrawler.class, "Crawler", EntityRegistry.findGlobalUniqueEntityId(), 1, 2);

        EntityRegistry.registerModEntity(EntityBullet.class, "Bullet", 1, DayZ.INSTANCE, 250, 5, true);

        if (event.getSide().isServer()) {
            EntityRegistry.addSpawn(EntityZombieDayZ.class, 200, 1, 4, EnumCreatureType.creature, Biomes.biomeForest, Biomes.biomePlains, Biomes.biomeRiver, Biomes.biomeSnowMountains, Biomes.biomeSnowPlains);
            EntityRegistry.addSpawn(EntityCrawler.class, 100, 1, 4, EnumCreatureType.creature, Biomes.biomeForest, Biomes.biomePlains, Biomes.biomeRiver, Biomes.biomeSnowMountains, Biomes.biomeSnowPlains);
        }
    }

    public void load(FMLInitializationEvent event) {
    }

    public void postload(FMLPostInitializationEvent event) {
        LootManager.init();
    }

    @SubscribeEvent
    public void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (Config.thirstEnabled) PlayerContainer.onPlayerLog(event.player);
    }

    @SubscribeEvent
    public void playerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        if (Config.thirstEnabled) PlayerContainer.onPlayerLevel(event.player);
    }

    @SubscribeEvent
    public void playerTickEvent(TickEvent.PlayerTickEvent event) {
        if (Config.thirstEnabled && !event.player.capabilities.isCreativeMode) PlayerContainer.onTick(event.player);
    }

    @SubscribeEvent
    public void worldLoad(WorldEvent.Load event) {
        for (Object obj : event.world.loadedTileEntityList) {
            if (obj instanceof TileEntityChest) {
                TileEntityChest chest = (TileEntityChest) obj;
                if (event.world.getBlock(chest.xCoord, chest.yCoord, chest.zCoord) == ModBlocks.chestLoot) {
                    boolean continueChecking = true;
                    int slotNumber = 0;
                    while (continueChecking) {
                        if (chest.getStackInSlot(slotNumber) == null && slotNumber < 27) {
                            if (slotNumber == 26) {
                                WeightedRandomChestContent.generateChestContents(event.world.rand, LootManager.loot, chest, event.world.rand.nextInt(5) + 1);
                                continueChecking = false;
                            } else {
                                slotNumber++;
                            }
                        } else {
                            continueChecking = false;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
    /*
    if (event.entityLiving instanceof EntityPlayer && DayZ.isServer()) {
      PlayerData.get((EntityPlayer)event.entityLiving).handleThirst();
    }
     */

        if (event.entityLiving.isPotionActive(Effect.bleeding)) {
            if (event.entityLiving.getActivePotionEffect(Effect.bleeding).getDuration() == 0) {
                event.entityLiving.removePotionEffect(Effect.bleeding.id);
                return;
            }
            if (event.entityLiving.worldObj.rand.nextInt(100) == 0)
                event.entityLiving.attackEntityFrom(DamageType.bleedOut, 2);
        }
        if (event.entityLiving.isPotionActive(Effect.zombification)) {
            if (event.entityLiving.getActivePotionEffect(Effect.zombification).getDuration() == 0) {
                event.entityLiving.removePotionEffect(Effect.zombification.id);
                return;
            }
            if (event.entityLiving.worldObj.rand.nextInt(100) == 0) {
                if (event.entityLiving.getHealth() > 1.0F)
                    event.entityLiving.attackEntityFrom(DamageType.zombieInfection, 1.0F);
                else {
                    EntityZombieDayZ var2 = new EntityZombieDayZ(event.entityLiving.worldObj);
                    var2.setLocationAndAngles(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, event.entityLiving.rotationYaw, event.entityLiving.rotationPitch);
                    event.entityLiving.worldObj.spawnEntityInWorld(var2);
                    event.entityLiving.attackEntityFrom(DamageType.zombieInfection, 1.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public void initBiomeGens(WorldTypeEvent.InitBiomeGens event) {
        if (event.worldType instanceof WorldTypes) {
            event.newBiomeGens = GenLayerDayZ.getGenLayers(event.seed, (WorldTypes) event.worldType);
        }
    }

    @SubscribeEvent
    public void populateChunk(PopulateChunkEvent.Populate event) {
        if (event.world.getWorldInfo().getTerrainType() instanceof WorldTypes) {
            if (event.type == PopulateChunkEvent.Populate.EventType.LAKE)
                event.setResult(Event.Result.DENY);
            if (event.type == PopulateChunkEvent.Populate.EventType.LAVA)
                event.setResult(Event.Result.DENY);
            if (event.type == PopulateChunkEvent.Populate.EventType.DUNGEON)
                event.setResult(Event.Result.DENY);
        }

        if (event.world.getWorldInfo().getTerrainType() instanceof WorldTypes && event.world.rand.nextInt(Config.structureGenerationChance) == 0) {
            for (int i = 0; i < 12; ++i) {
                int x = event.chunkX * 16 + event.rand.nextInt(16) + 8;
                int y = event.rand.nextInt(128);
                int z = event.chunkZ * 16 + event.rand.nextInt(16) + 8;

                StructureHandler.generateStructure(event.world, event.rand, x, y, z);
            }
        }
    }
}