package mod.schnappdragon.habitat.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class HabitatConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<String> suspiciousStewEffects;

        public final ForgeConfigSpec.ConfigValue<Integer> pookaAidCooldown;
        public final ForgeConfigSpec.ConfigValue<String> pookaPositiveEffects;
        public final ForgeConfigSpec.ConfigValue<String> pookaNegativeEffects;

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

            builder.comment("\n__MISC__\n");
            builder.comment("\nFairy Ring Mushroom Stew Effects\nEnter them in the format effectid:duration (duration in seconds) and separate them with a single ,");
            suspiciousStewEffects = builder.define("suspicious_stew_effects", "1:12,5:8,8:16,10:10,11:8,2:8,15:12,17:16,18:8,19:10");

            builder.comment("\n__MOBS__\n");
            builder.comment("\n_POOKA_\nCooldown duration after Pooka gives the player it is following an effect. (duration in seconds)");
            pookaAidCooldown = builder.define("pooka_aid_cooldown", 20);
            builder.comment("Effects\nEnter them in the format effectid:duration (duration in seconds) and separate them with a single ,");
            pookaPositiveEffects = builder.define("pooka_positive_effects", "1:6,5:4,8:8,10:5,11:4");
            pookaNegativeEffects = builder.define("pooka_negative_effects", "2:4,15:6,17:8,18:4,19:5");

            builder.comment("\n__WORLD GEN__\n");
            builder.comment("\nFeatures have 1 in X Chance of Generating per Chunk where X is the Config Value");
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