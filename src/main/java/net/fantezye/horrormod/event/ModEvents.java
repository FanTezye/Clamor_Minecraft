package net.fantezye.horrormod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.fantezye.horrormod.HorrorMod;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;

import net.minecraft.tags.BlockTags;

import javax.annotation.processing.SupportedSourceVersion;

@Mod.EventBusSubscriber(modid = HorrorMod.MOD_ID)
public class ModEvents {

    static int PlayerCraftedBedCounter_int = 0;
    static public int PlayerCraftedBedCounter(){

        return PlayerCraftedBedCounter_int;
    }

    @SubscribeEvent
    public static void PlayerCraftsBed(PlayerEvent.ItemCraftedEvent event){
            Player player = event.getEntity();
            ItemStack craftedStack = event.getCrafting();
            if (craftedStack.is(ItemTags.BEDS) && PlayerCraftedBedCounter()<1) {
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
                    PlayerCraftedBedCounter_int +=1;
                }
            }
    }
    @SubscribeEvent
    public static void PlayerBreakSpecifiedBlock(BlockEvent.BreakEvent event) {
        LevelAccessor world = event.getLevel();
        if (!world.isClientSide() && world instanceof ServerLevel serverLevel) {
            BlockPos pos = event.getPos();
            Zombie zombie = EntityType.ZOMBIE.create(serverLevel);
            if (zombie != null) {
                zombie.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
                serverLevel.addFreshEntity(zombie);
            }
        }
    }
}
