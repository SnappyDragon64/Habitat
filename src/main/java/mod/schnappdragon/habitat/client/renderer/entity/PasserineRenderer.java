package mod.schnappdragon.habitat.client.renderer.entity;

import mod.schnappdragon.habitat.client.model.PasserineModel;
import mod.schnappdragon.habitat.client.renderer.HabitatModelLayers;
import mod.schnappdragon.habitat.client.renderer.entity.layers.PasserineEyesLayer;
import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PasserineRenderer extends MobRenderer<Passerine, PasserineModel<Passerine>> {
    public static final ResourceLocation[] PASSERINE_LOCATIONS = new ResourceLocation[]{
            new ResourceLocation(Habitat.MODID, "textures/entity/passerine/american_goldfinch.png"),
            new ResourceLocation(Habitat.MODID, "textures/entity/passerine/bali_myna.png"),
            new ResourceLocation(Habitat.MODID, "textures/entity/passerine/common_sparrow.png"),
            new ResourceLocation(Habitat.MODID, "textures/entity/passerine/eastern_bluebird.png"),
            new ResourceLocation(Habitat.MODID, "textures/entity/passerine/eurasian_bullfinch.png"),
            new ResourceLocation(Habitat.MODID, "textures/entity/passerine/red_cardinal.png"),
    };
    public static final ResourceLocation PASSERINE_BERDLY_LOCATION = new ResourceLocation(Habitat.MODID, "textures/entity/passerine/berdly.png");

    public PasserineRenderer(EntityRendererProvider.Context context) {
        super(context, new PasserineModel<>(context.bakeLayer(HabitatModelLayers.PASSERINE)), 0.25F);
        this.addLayer(new PasserineEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Passerine passerine) {
        return passerine.isBerdly() ? PASSERINE_BERDLY_LOCATION : PASSERINE_LOCATIONS[passerine.getVariant()];
    }

    @Override
    public float getBob(Passerine passerine, float partialTicks) {
        float f = Mth.lerp(partialTicks, passerine.initialFlap, passerine.flap);
        float f1 = Mth.lerp(partialTicks, passerine.initialFlapSpeed, passerine.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }
}