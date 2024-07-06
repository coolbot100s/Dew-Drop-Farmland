package cool.bot.dewdropfarmland.util;


import cool.bot.dewdropfarmland.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.HitResult;

public final class Util {

    public static BlockPos blockHighlightedOrNull(Player player) {
        HitResult hitResult = player.pick(player.getBlockReach(),1.0F,true);
        if (!(hitResult.getType() == HitResult.Type.BLOCK)) {
            return null;
        }
        return BlockPos.containing(hitResult.getLocation().add(hitResult.getLocation().subtract(player.getEyePosition()).normalize().multiply(0.001, 0.001, 0.001)));
    }

    public static boolean isMoistWaterable(ServerLevel level, BlockPos pos) {
       return level.getBlockState(pos).is(ModBlockTags.WATERABLE) && level.getBlockState(pos).getValue(BlockStateProperties.MOISTURE) >= 1;
    }

    // Calling these methods on blocks that do not have a moisture property like farmland (0-7) will break things.
    public static void setMoist(ServerLevel level, BlockPos pos) {
        level.setBlock(pos, level.getBlockState(pos).setValue(BlockStateProperties.MOISTURE, 7), 3);
    }
    public static void setDry(ServerLevel level, BlockPos pos) {
        level.setBlock(pos, level.getBlockState(pos).setValue(BlockStateProperties.MOISTURE, 0), 3);
    }

}