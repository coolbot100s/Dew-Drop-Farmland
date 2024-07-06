package cool.bot.dewdropfarmland.block;

import cool.bot.dewdropfarmland.Config;
import cool.bot.dewdropfarmland.tag.ModBlockTags;
import cool.bot.dewdropfarmland.util.Util;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FarmlandEventHandler {

    @SubscribeEvent
    public static void onFarmlandTrampleEvent(BlockEvent.FarmlandTrampleEvent event) {
        if (Config.noTrample) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onSplashWaterBottleHitEvent(ProjectileImpactEvent event) {
        if (!Config.splashPotionWatering) {
            return;
        }

        if (event.getProjectile() instanceof ThrownPotion) {
            ThrownPotion potion = ((ThrownPotion) event.getProjectile());
            Potion type = PotionUtils.getPotion(potion.getItem());
            if (type == Potions.WATER) {
                if (event.getRayTraceResult().getType() == HitResult.Type.BLOCK) {
                    Level level = (event.getProjectile().getOwner().level());
                    if (level.isClientSide()) {
                        return;
                    }
                    BlockPos pos = BlockPos.containing(event.getRayTraceResult().getLocation());
                    if (Config.splashWaterArea == 0) {
                        if (level.getBlockState(pos).is(ModBlockTags.WATERABLE)) {
                            Util.setMoist(((ServerLevel) level),pos);
                        }
                    } else {
                        BlockPos.withinManhattanStream(pos, Config.splashWaterArea,0,Config.splashWaterArea).forEach(blockPos -> {
                            BlockState state = level.getBlockState(blockPos);
                            if (state.is(ModBlockTags.WATERABLE)) {
                                Util.setMoist(((ServerLevel) level), blockPos);
                            }
                        });
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void interactBlockEvent(PlayerInteractEvent event) {
        if (event.getLevel().isClientSide) {
            return;
        }

        if (!Config.bucketWatering &&! Config.bottleWatering) {
            return;
        }

        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(ModBlockTags.WATERABLE)) {
            return;
        }
        Player player = event.getEntity();
        ItemStack stack = player.getItemInHand(event.getHand());


        if (Config.bucketWatering && stack.is(Items.WATER_BUCKET)) {
            if (!player.isCreative()) {
                player.setItemInHand(event.getHand(), new ItemStack(Items.BUCKET));
            }
        } else if (Config.bottleWatering && stack.is(Items.POTION) && PotionUtils.getPotion(stack) == Potions.WATER) {
            if (!player.isCreative()) {
                player.setItemInHand(event.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }
        } else {
            return;
        }
        Util.setMoist(level, pos);

    }

}
