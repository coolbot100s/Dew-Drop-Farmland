package cool.bot.dewdropfarmland.block;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> VANILLA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    public static final RegistryObject<Block> FARMLAND = registerBlock("farmland",
            () -> new CustomFarmland(BlockBehaviour.Properties.copy(Blocks.FARMLAND)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = VANILLA_BLOCKS.register(name, block);
        return toReturn;
    }


    public static void register(IEventBus eventBus) {
        VANILLA_BLOCKS.register(eventBus);
    }

}
