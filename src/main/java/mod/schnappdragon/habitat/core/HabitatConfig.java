package mod.schnappdragon.habitat.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class HabitatConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> rafflesiaChance;
        public final ForgeConfigSpec.ConfigValue<Integer> kabloomBushChance;
        public final ForgeConfigSpec.ConfigValue<Integer> slimeFernChance;
        public final ForgeConfigSpec.ConfigValue<Integer> ballCactusChance;

        public final ForgeConfigSpec.ConfigValue<Integer> fairyRingAverage;
        public final ForgeConfigSpec.ConfigValue<Integer> fairyRingMinimum;

        public final ForgeConfigSpec.ConfigValue<String> rafflesiaWhitelist;
        public final ForgeConfigSpec.ConfigValue<String> kabloomBushWhitelist;
        public final ForgeConfigSpec.ConfigValue<String> slimeFernWhitelist;
        public final ForgeConfigSpec.ConfigValue<String> ballCactusWhitelist;
        public final ForgeConfigSpec.ConfigValue<String> fairyRingWhitelist;

        public final ForgeConfigSpec.ConfigValue<String> rafflesiaBlacklist;
        public final ForgeConfigSpec.ConfigValue<String> kabloomBushBlacklist;
        public final ForgeConfigSpec.ConfigValue<String> slimeFernBlacklist;
        public final ForgeConfigSpec.ConfigValue<String> ballCactusBlacklist;
        public final ForgeConfigSpec.ConfigValue<String> fairyRingBlacklist;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common Configurations for Habitat").push("common");

            builder.comment("Features have 1 in X Chance of Generating per Chunk where X is the Config Value");
            rafflesiaChance = builder.define("rafflesia_chance", 3);
            kabloomBushChance = builder.define("kabloom_bush_chance", 144);
            slimeFernChance = builder.define("slime_fern_chance", 2);
            ballCactusChance = builder.define("ball_cactus_chance", 14);

            builder.comment("Average and Minimum Chunk Spacing for Structures (Minimum must be lower than Average)");
            fairyRingAverage = builder.define("fairy_ring_average", 24);
            fairyRingMinimum = builder.define("fairy_ring_minimum", 4);

            builder.comment("Whitelist and Blacklist for Biomes in which Features and Structures can generate\nUse the Biome Name with Namespace (namespace:biome) and separate Biomes if more than one with a single ,\nBiome Whitelist");
            rafflesiaWhitelist = builder.define("rafflesia_whitelist", "");
            kabloomBushWhitelist = builder.define("kabloom_bush_whitelist", "");
            slimeFernWhitelist = builder.define("slime_fern_whitelist", "");
            ballCactusWhitelist = builder.define("ball_cactus_whitelist", "");
            fairyRingWhitelist = builder.define("fairy_ring_whitelist", "");

            builder.comment("Biome Blacklist");
            rafflesiaBlacklist = builder.define("rafflesia_blacklist", "");
            kabloomBushBlacklist = builder.define("kabloom_bush_blacklist", "");
            slimeFernBlacklist = builder.define("slime_fern_blacklist", "");
            ballCactusBlacklist = builder.define("ball_cactus_blacklist", "");
            fairyRingBlacklist = builder.define("fairy_ring_blacklist", "");

            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}