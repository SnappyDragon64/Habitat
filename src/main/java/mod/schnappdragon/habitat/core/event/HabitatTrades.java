package mod.schnappdragon.habitat.core.event;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MODID)
public class HabitatTrades {
    @SubscribeEvent
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        event.getGenericTrades().add(new BasicItemListing(3, new ItemStack(HabitatItems.RAFFLESIA.get()), 12, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(3, new ItemStack(HabitatItems.KABLOOM_FRUIT.get()), 12, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(3, new ItemStack(HabitatItems.KABLOOM_PULP.get()), 12, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(1, new ItemStack(HabitatItems.SLIME_FERN.get()), 12, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(2, new ItemStack(HabitatItems.ORANGE_BALL_CACTUS_FLOWER.get()), 8, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(2, new ItemStack(HabitatItems.PINK_BALL_CACTUS_FLOWER.get()), 8, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(2, new ItemStack(HabitatItems.RED_BALL_CACTUS_FLOWER.get()), 8, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(2, new ItemStack(HabitatItems.YELLOW_BALL_CACTUS_FLOWER.get()), 8, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(3, new ItemStack(HabitatItems.ORANGE_BALL_CACTUS.get()), 5, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(3, new ItemStack(HabitatItems.PINK_BALL_CACTUS.get()), 5, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(3, new ItemStack(HabitatItems.RED_BALL_CACTUS.get()), 5, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(3, new ItemStack(HabitatItems.YELLOW_BALL_CACTUS.get()), 5, 10, 1));
        event.getGenericTrades().add(new BasicItemListing(5, new ItemStack(HabitatItems.FAIRY_RING_MUSHROOM.get()), 5, 10, 1));
    }
}