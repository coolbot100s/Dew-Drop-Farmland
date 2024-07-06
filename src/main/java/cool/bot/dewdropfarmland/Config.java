package cool.bot.dewdropfarmland;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DewDropFarmland.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue NO_TRAMPLE = BUILDER
            .comment("Whether to stop crop trampling")
            .define("noTrample", true);
    private static final ForgeConfigSpec.BooleanValue NO_RANDOM_TICK = BUILDER
            .comment("Whether to stop random ticks (Water from source blocks and moisture decay)")
            .define("noRandomTick", true);
    private static final ForgeConfigSpec.BooleanValue STURDY_FARMLAND = BUILDER
            .comment("When enabled, farmland will not check for blocks above it, and can be built on top of without turning to dirt")
            .define("sturdyFarmland", true);
    private static final ForgeConfigSpec.BooleanValue SPLASH_POTION_WATERING = BUILDER
            .comment("If splash water potions can be used to moisten farmland")
            .define("splashPotionWatering", true);
    private static final ForgeConfigSpec.IntValue SPLASH_WATER_AREA = BUILDER
            .comment("The range of splash water bottles for watering, 0 to water only the block hit")
            .defineInRange("splashWaterArea", 2, 0, 8);
    private static final ForgeConfigSpec.BooleanValue BUCKET_WATERING = BUILDER
            .comment("If buckets can be used to moisten farmland")
            .define("bucketWatering", true);
    private static final ForgeConfigSpec.BooleanValue BOTTLE_WATERING = BUILDER
            .comment("If bottles can be used to moisten farmland")
            .define("bottleWatering", true);
    private static final ForgeConfigSpec.BooleanValue RAIN_WATERING = BUILDER
            .comment("If rain will moisten farmland")
            .define("rainWatering", true);
    private static final ForgeConfigSpec.BooleanValue DAILY_RESET = BUILDER
            .comment("If the farmland will decay daily")
            .define("dailyReset", true);
    private static final ForgeConfigSpec.IntValue DAILY_TIME_MIN = BUILDER
            .comment("The time of day daily reset logic will occur (within 10 ticks)")
            .defineInRange("dailyTimeMin", 0, 0, 24000);
    private static final ForgeConfigSpec.IntValue DAILY_DRY_CHANCE = BUILDER
            .comment("The chance that farmland will become dry")
            .defineInRange("dailyDryChance", 100, 0, 100);
    private static final ForgeConfigSpec.IntValue DAILY_DECAY_CHANCE = BUILDER
            .comment("The chance that dry farmland will decay into dirt")
            .defineInRange("dailyDecayChance", 50, 0, 100);



    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean noTrample;
    public static boolean splashPotionWatering;
    public static int splashWaterArea;
    public static boolean bucketWatering;
    public static boolean bottleWatering;
    public static boolean rainWatering;
    public static boolean dailyReset;
    public static int dailyTimeMin;
    public static int dailyDryChance;
    public static int dailyDecayChance;
    public static boolean sturdyFarmland;
    public static boolean noRandomTick;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        noTrample = NO_TRAMPLE.get();
        splashPotionWatering = SPLASH_POTION_WATERING.get();
        splashWaterArea = SPLASH_WATER_AREA.get();
        bucketWatering = BUCKET_WATERING.get();
        bottleWatering = BOTTLE_WATERING.get();
        rainWatering = RAIN_WATERING.get();
        dailyReset = DAILY_RESET.get();
        dailyTimeMin = DAILY_TIME_MIN.get();
        dailyDryChance = DAILY_DRY_CHANCE.get();
        dailyDecayChance = DAILY_DECAY_CHANCE.get();
        sturdyFarmland = STURDY_FARMLAND.get();
        noRandomTick = NO_RANDOM_TICK.get();
    }
}
