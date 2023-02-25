package com.github.dayzminecraft.dayzminecraft.common.thirst;

/*
public class PlayerData implements IExtendedEntityProperties {

  public final static String EXT_PROP_NAME = "DayZPlayerData";

  private final EntityPlayer player;

  private int thirst;

  public PlayerData(EntityPlayer player) {
    this.player = player;
    this.thirst = 0;
  }

  public static void register(EntityPlayer player) {
    player.registerExtendedProperties(PlayerData.EXT_PROP_NAME, new PlayerData(player));
  }

  public static PlayerData get(EntityPlayer player) {
    return (PlayerData)player.getExtendedProperties(EXT_PROP_NAME);
  }

  @Override
  public void saveNBTData(NBTTagCompound compound) {

    NBTTagCompound properties = new NBTTagCompound();
    properties.setInteger("thirstLevel", thirst);
    compound.setTag(EXT_PROP_NAME, properties);
  }

  @Override
  public void loadNBTData(NBTTagCompound compound) {
    NBTTagCompound properties = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
    thirst = properties.getInteger("thirstLevel");
  }

  @Override
  public void init(Entity entity, World world) {}

  public void drink(int amount) {
    thirst = thirst - amount;
    if (thirst < 0) thirst = 0;
  }

  public void handleThirst() {
    if (!Config.thirstEnabled) return;
    if (player == null || player.isDead || player.capabilities.isCreativeMode)
      return;

    if (thirst == 20000)
      ChatHandler.chatWarning(player, StatCollector.translateToLocal("thirst.thirdwarning"));
    else if (thirst == 18000)
      ChatHandler.chatWarning(player, StatCollector.translateToLocal("thirst.secondwarning"));
    else if (thirst == 16000)
      ChatHandler.chatWarning(player, StatCollector.translateToLocal("thirst.firstwarning"));
    else if (thirst >= 24000 && FMLCommonHandler.instance().getMinecraftServerInstance().getTickCounter() % 40 == 0)
      player.attackEntityFrom(DamageType.thirstDeath, 1);
    else if (player.isSprinting() || player.isAirBorne)
      thirst = thirst + 2;

    thirst++;

    ChatHandler.logDebug(player.getDisplayName() + " - " + String.valueOf(thirst));
  }

  public int getThirst() {
    return thirst;
  }
}
 */
