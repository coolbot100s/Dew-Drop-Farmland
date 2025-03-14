package cool.bot.dewdropfarmland.block;

import cool.bot.botslib.util.RNG;
import cool.bot.dewdropfarmland.Config;
import cool.bot.botslib.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.Nullable;


// Override vanilla Farmland
public class CustomFarmland extends FarmBlock implements IForgeBlock {
    public CustomFarmland(Properties properties) {
        super(properties);

    }

@Override
public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!Config.noRandomTick) {
            super.randomTick(state, level, pos, random);
        }

}

@Nullable
@Override
public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
    ItemStack itemStack = context.getItemInHand();

    if (!itemStack.canPerformAction(toolAction)) {return null;}

    if (ToolActions.SHOVEL_FLATTEN == toolAction && Config.shovelReverting) {
        return Blocks.DIRT.defaultBlockState();
    }

    return super.getToolModifiedState(state, context, toolAction, simulate);
}


@Override
public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (Config.rainWatering || Config.dailyReset) {

        level.scheduleTick(pos, this, 10);

        if (level.isRainingAt(pos.above()) && Config.rainWatering) {
            Util.setMoist(level, pos);
        }

        if (!Config.dailyReset || !level.getGameRules().getRule(GameRules.RULE_DAYLIGHT).get()) {
            return;
        }
        long dayTime = level.getDayTime() % 24000;
        // check before rain
        if (dayTime >= Config.dailyTimeMin && dayTime < Config.dailyTimeMin + 10) {
            if (!Util.isMoistWaterable(level, pos)) {
                if (RNG.mc_ihundo(random, Config.dailyDecayChance)) {
                    level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
                }
            } else {
                if (RNG.mc_ihundo(random, Config.dailyDryChance)) {
                    Util.setDry(level, pos);
                }

            }
        }

    }

    if (!Config.sturdyFarmland) {
        super.tick(state, level, pos, random);
    }

}

    @Override
    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        if (!level.isClientSide()) {
            if (Config.rainWatering || Config.dailyReset) {
                ((ServerLevel) level).scheduleTick(pos, this, 10); // prevent rescheduling
            }
        }
        super.onBlockStateChange(level, pos, oldState, newState);
    }
}
