package net.fantezye.horrormod.event;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.fantezye.horrormod.HorrorMod;
import java.util.concurrent.TimeUnit;

import javax.annotation.processing.SupportedSourceVersion;

@Mod.EventBusSubscriber(modid = HorrorMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void PlayerCraftsBed(PlayerEvent.ItemCraftedEvent event){
            Player player = event.getEntity();
            ItemStack craftedStack = event.getCrafting();
            if (craftedStack.is(Items.WHITE_BED)) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100));
                    for(int i = 0;i<3;++i){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        player.sendSystemMessage(net.minecraft.network.chat.Component.empty());
                    }
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal("Sleeping will not make it go away."));
                    for(int i = 0;i<3;++i){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        player.sendSystemMessage(net.minecraft.network.chat.Component.empty());
                    }
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMBIENT_CAVE.value(), SoundSource.AMBIENT, 2f, 1f);
                }
            }
    }

}
