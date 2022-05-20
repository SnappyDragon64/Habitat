package mod.schnappdragon.habitat.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class HabitatConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<String> suspiciousStewEffects;

        public final ForgeConfigSpec.ConfigValue<Integer> pookaAidCooldown;
        public final ForgeConfigSpec.ConfigValue<String> pookaPositiveEffects;
        public final ForgeConfigSpec.ConfigValue<String> pookaNegativeEffects;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common Configurations for Habitat").push("common");

            builder.comment("\n== MISC ==\n\nFairy Ring Mushroom Stew Effects\nEnter them in the format effectid:duration (duration in seconds) and separate them with a single ,");
            suspiciousStewEffects = builder.define("suspicious_stew_effects", "1:12,5:8,8:16,10:10,11:8,2:8,15:12,17:16,18:8,19:10");

            builder.comment("\n== MOBS ==\n\n= POOKA =\nMinimum cooldown duration after Pooka gives the player it is following an effect. (duration in seconds)\nActual cooldown duration can be upto 20% more than the specified value.");
            pookaAidCooldown = builder.define("pooka_aid_cooldown", 30);
            builder.comment("Pooka Effects\nEnter them in the format effectid:duration (duration in seconds) and separate them with a single ,");
            pookaPositiveEffects = builder.define("pooka_positive_effects", "1:6,5:4,8:8,10:5,11:4");
            pookaNegativeEffects = builder.define("pooka_negative_effects", "2:4,15:6,17:8,18:4,19:5");

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