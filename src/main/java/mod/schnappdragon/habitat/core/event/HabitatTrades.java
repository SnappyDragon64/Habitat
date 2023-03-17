package mod.schnappdragon.habitat.core.event;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Habitat.MODID)
public class HabitatTrades {
    @SubscribeEvent
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        TradeHelper helper = new TradeHelper(event);

        helper.addTrade(2, 5, HabitatItems.RAFFLESIA);
        helper.addTrade(1, 12, HabitatItems.KABLOOM_PULP);
        helper.addTrade(1, 12, HabitatItems.SLIME_FERN);
        helper.addTrade(1, 8, HabitatItems.ORANGE_BALL_CACTUS_FLOWER);
        helper.addTrade(1, 8, HabitatItems.PINK_BALL_CACTUS_FLOWER);
        helper.addTrade(1, 8, HabitatItems.RED_BALL_CACTUS_FLOWER);
        helper.addTrade(1, 8, HabitatItems.YELLOW_BALL_CACTUS_FLOWER);
        helper.addTrade(2, 5, HabitatItems.ORANGE_BALL_CACTUS);
        helper.addTrade(2, 5, HabitatItems.PINK_BALL_CACTUS);
        helper.addTrade(2, 5, HabitatItems.RED_BALL_CACTUS);
        helper.addTrade(2, 5, HabitatItems.YELLOW_BALL_CACTUS);
        helper.addTrade(5, 8, HabitatItems.FAIRY_RING_MUSHROOM);
        helper.addTrade(1, 8, HabitatItems.EDELWEISS);
        helper.addTrade(3, 8, HabitatItems.ORANGE_BALL_CACTUS_BLOCK);
        helper.addTrade(3, 8, HabitatItems.PINK_BALL_CACTUS_BLOCK);
        helper.addTrade(3, 8, HabitatItems.RED_BALL_CACTUS_BLOCK);
        helper.addTrade(3, 8, HabitatItems.YELLOW_BALL_CACTUS_BLOCK);
        helper.addTrade(3, 8, HabitatItems.FLOWERING_ORANGE_BALL_CACTUS_BLOCK);
        helper.addTrade(3, 8, HabitatItems.FLOWERING_PINK_BALL_CACTUS_BLOCK);
        helper.addTrade(3, 8, HabitatItems.FLOWERING_RED_BALL_CACTUS_BLOCK);
        helper.addTrade(3, 8, HabitatItems.FLOWERING_YELLOW_BALL_CACTUS_BLOCK);
    }

    static class TradeHelper {
        WandererTradesEvent event;

        TradeHelper(WandererTradesEvent event) {
            this.event = event;
        }
        
        public void addTrade(int cost, int maxTrades, Supplier<Item> item) {
            event.getGenericTrades().add(new BasicItemListing(cost, new ItemStack(item.get()), maxTrades, 10, 1));
        }
    }
}