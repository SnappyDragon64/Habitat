package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID)
public class BGTrades {
    @SubscribeEvent
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(BGItems.RAFFLESIA_SEED.get(), 1), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(BGItems.KABLOOM_FRUIT.get(), 1), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(1, new ItemStack(BGItems.SLIME_FERN.get(), 1), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(1, new ItemStack(BGItems.PINK_BALL_CACTUS_FLOWER.get(), 1), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(1, new ItemStack(BGItems.RED_BALL_CACTUS_FLOWER.get(), 1), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(1, new ItemStack(BGItems.ORANGE_BALL_CACTUS_FLOWER.get(), 1), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(1, new ItemStack(BGItems.YELLOW_BALL_CACTUS_FLOWER.get(), 1), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(2, new ItemStack(BGItems.PINK_BALL_CACTUS.get(), 1), 5, 10, 1));
        event.getGenericTrades().add(new BasicTrade(2, new ItemStack(BGItems.RED_BALL_CACTUS.get(), 1), 5, 10, 1));
        event.getGenericTrades().add(new BasicTrade(2, new ItemStack(BGItems.ORANGE_BALL_CACTUS.get(), 1), 5, 10, 1));
        event.getGenericTrades().add(new BasicTrade(2, new ItemStack(BGItems.YELLOW_BALL_CACTUS.get(), 1), 5, 10, 1));
    }
}