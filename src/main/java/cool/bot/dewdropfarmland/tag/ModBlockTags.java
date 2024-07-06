package cool.bot.dewdropfarmland.tag;
// TODO: Move to util mod, shared by farmland and watering can

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> WATERABLE = tag("waterable");

    public static TagKey<Block> tag(String name) {
        return BlockTags.create(new ResourceLocation("dewdrop", name));
    }
}
