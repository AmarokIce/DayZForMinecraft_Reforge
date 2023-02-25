package com.github.dayzminecraft.dayzminecraft.common.thirst;

import com.github.dayzminecraft.dayzminecraft.common.misc.Config;
import com.github.dayzminecraft.dayzminecraft.common.misc.DamageType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Someoneice
 * <p> <p>
 * === Pineapple license ===
 * <p>
 * I will provide all or part of the code, as long as you use it within the limits of the law, you can use these codes as you like.
 * You can get full access to this source code without paying any royalties, which will be permanent and irrevocable if you are granted this license.
 * You can get full right to use this source code without paying any royalties, which will be perpetual and will not be revoked  if you are granted this license, unless you have violated this agreement or have been prevented by me from granting it to you in any way.
 * If you have made a copy of the Program with these code, you should include this license to keep the original code open source.
 * <p> <p>
 * Of course this license doesn't used in court, just like you know that none of us want legal battles for these meaningless things, so I hope you will respect this brief license.
 * I do not want my code to be used for commercial purposes, including but not limited to paid downloads and download with AD(e.g. AdF.ly).
 * If you used my code to do some instructional videos, or if you used my code to the fullest, put it in the pineapple juicer and sold the juicer to make money, it's your credit, you can certainly do it.
 * If you think my mod is good to learn something, if you like, invite me to a cup of pineapple juice.
 * <p> <p>
 * If you do not follow this simple agreement and legal liability arises, this has nothing to do with me, please take responsibility for yourself.
 * If you're selling my source code or compiled code, I'll be very angry and accuse you smelling ugly.
 * <p> <p>
 * I reserve the right to make changes to the license without affecting your exercise of this license.
 * If you are unable to accept a license other than those approved by the Open Source Initiative, you may use these codes using the AFL-3.0 license, instead of complying with this license.
 * If you choose to accept AFL-3.0, you may need to show in your README or somewhere else that is very convenient to see, you are using the AFL-3.0 license.
 * <p>
 * <p>
 * <p>
 * === 凤梨许可证 ===
 * <p>
 * 我会提供全部或者部分的代码，只要您在法律限制之内使用，您可以随意的使用这些代码。
 * 您可以获取这份源代码全部的使用权并且不需要支付任何版权税，如果您被授权了这份许可证，这将会永久生效且不会撤回，除非您已经违反了这份协议或由我以任何形式阻止了这份协议授权于您。
 * 如果您使用这些代码制作了副本，您应当将本许可证包涵其中保持原有代码依然开源。
 * <p> <p>
 * 当然，这个许可证在法庭中站不住脚，您知道的，我们都不希望为了这些没有意义的事情而产生法律纠纷，所以我希望您能尊重这份非常简短的许可证。
 * 我不希望我的代码被用于商业用途，包括但不限于付费下载与广告下载（例如AdF.ly)。
 * 如果您使用我的代码做了一些教学影片，或者您充分的使用了我的代码，把它写入了凤梨榨汁机里然后贩卖榨汁机赚取了钱，这是您的功劳，您当然可以这样做。
 * 如果您觉得您可以在我的代码中收获点什么，若您愿意，那就请我喝一杯凤梨汁吧。
 * <p> <p>
 * 如果您没有按照这份简单的协议执行，出现了法律责任，这与我无关，请您自己承担责任。
 * 如果您正在贩卖我的源代码或者编译后代码,我会非常愤怒的指责你闻起来很臭。
 * <p> <p>
 * 我将保留在不影响您执行这份许可证的情况下对许可证进行改动。
 * 如果您无法接受一份在开源社区协会认可之外的许可证，您可以参照AFL-3.0许可证来使用这些代码，而不是遵照这份许可证。
 * 若您选择接受AFL-3.0，您可能需要在您的README中或者别的能够非常方便的看到的地方著名，您正在使用AFL-3.0许可证的情况下使用这些代码。
 */
public class PlayerContainer {
    private static final Map<EntityPlayer, PlayerContainer> ThirstyContainer = new HashMap<>();
    private final int MaxThirstLevel = 20;
    private int ThirstLevel;
    private Float ThirstCounter;
    private int Timer;

    public PlayerContainer(NBTTagCompound tag) {
        if (tag.hasKey("thirst_data")) this.load(tag);
        else this.init(tag);
    }

    public static void drink(EntityPlayer player, int level, float counter) {
        if (Config.thirstEnabled) {
            PlayerContainer container = ThirstyContainer.get(player);
            if (container.ThirstLevel < 5) player.addChatMessage(new ChatComponentTranslation("thirst.replentish"));
            container.ThirstLevel = Math.min(container.ThirstLevel + level, container.MaxThirstLevel);
            container.ThirstCounter = Math.min(container.ThirstCounter + counter, 2.0F);
            container.writeToNBT(player.getEntityData());
        }
    }

    public static void onPlayerLevel(EntityPlayer player) {
        ThirstyContainer.get(player).writeToNBT(player.getEntityData());
        ThirstyContainer.remove(player);
    }

    public static void onPlayerLog(EntityPlayer player) {
        ThirstyContainer.put(player, new PlayerContainer(player.getEntityData()));
    }

    public static void onTick(EntityPlayer player) {
        if (ThirstyContainer.containsKey(player)) ThirstyContainer.get(player).tick(player);
    }

    public void tick(EntityPlayer player) {
        --Timer;
        if (Timer <= 0) {
            Timer = 200;
            if (ThirstCounter == 0.0F && ThirstLevel > 0) {
                ThirstLevel = Math.max(this.ThirstLevel - 1, 0);
                ThirstCounter = 0.5F;

                if (this.ThirstLevel == 10) player.addChatMessage(new ChatComponentTranslation("thirst.firstwarning"));
                else if (this.ThirstLevel == 5) player.addChatMessage(new ChatComponentTranslation("thirst.secondwarning"));
                else if (this.ThirstLevel == 2) player.addChatMessage(new ChatComponentTranslation("thirst.thirdwarning"));
            } else if (ThirstLevel <= 0) player.attackEntityFrom(DamageType.thirstDeath, 5.0F);
            this.ThirstCounter = Math.max(this.ThirstCounter - 0.1F, 0.0F);
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("thirst_level", ThirstLevel);
        tagCompound.setInteger("thirst_timer", Timer);
        tagCompound.setFloat("thirst_counter", ThirstCounter);
        tag.setTag("thirst_data", tagCompound);
    }

    private void load(NBTTagCompound tag) {
        NBTTagCompound tagCompound = tag.getCompoundTag("thirst_data");
        ThirstLevel = tagCompound.getInteger("thirst_level");
        Timer = tagCompound.getInteger("thirst_timer");
        ThirstCounter = tagCompound.getFloat("thirst_counter");
    }


    private void init(NBTTagCompound tag) {
        this.ThirstLevel = MaxThirstLevel;
        this.Timer = 200;
        this.ThirstCounter = 2.0F;
        writeToNBT(tag);
    }
}
