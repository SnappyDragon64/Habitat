package mod.schnappdragon.habitat.core.event;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MOD_ID)
public class HabitatTrades {
    @SubscribeEvent
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(HabitatItems.RAFFLESIA.get()), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(HabitatItems.KABLOOM_FRUIT.get()), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(HabitatItems.KABLOOM_SEEDS.get()), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(1, new ItemStack(HabitatItems.SLIME_FERN.get()), 12, 10, 1));
        event.getGenericTrades().add(new BasicTrade(2, new ItemStack(HabitatItems.ORANGE_BALL_CACTUS_FLOWER.get()), 8, 10, 1));
        event.getGenericTrades().add(new BasicTrade(2, new ItemStack(HabitatItems.PINK_BALL_CACTUS_FLOWER.get()), 8, 10, 1));
        event.getGenericTrades().add(new BasicTrade(2, new ItemStack(HabitatItems.RED_BALL_CACTUS_FLOWER.get()), 8, 10, 1));
        event.getGenericTrades().add(new BasicTrade(2, new ItemStack(HabitatItems.YELLOW_BALL_CACTUS_FLOWER.get()), 8, 10, 1));
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(HabitatItems.ORANGE_BALL_CACTUS.get()), 5, 10, 1));
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(HabitatItems.PINK_BALL_CACTUS.get()), 5, 10, 1));
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(HabitatItems.RED_BALL_CACTUS.get()), 5, 10, 1));
        event.getGenericTrades().add(new BasicTrade(3, new ItemStack(HabitatItems.YELLOW_BALL_CACTUS.get()), 5, 10, 1));
        event.getGenericTrades().add(new BasicTrade(5, new ItemStack(HabitatItems.FAIRY_RING_MUSHROOM.get()), 5, 10, 1));
    }
}