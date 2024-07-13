package cool.bot.dewdropfarmland.block;

import cool.bot.botslib.util.RNG;
import cool.bot.dewdropfarmland.Config;
import cool.bot.botslib.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;


// Override vanilla Farmland
public class CustomFarmland extends FarmBlock {
    public CustomFarmland(Properties properties) {
        super(properties);

    }

@Override
public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!Config.noRandomTick) {
            super.randomTick(state, level, pos, random);
        }

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
        if (dayTime >= Config.dailyTimeMin && dayTime <= Config.dailyTimeMin + 10) {
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
