package net.bikerboys.insanity.sound;

import net.bikerboys.insanity.ExampleMod;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ExampleMod.MODID);

    public static final RegistryObject<SoundEvent> BREATH = registerSoundEvents("Breath");
    public static final RegistryObject<SoundEvent> BREATH2 = registerSoundEvents("Breath2");
    public static final RegistryObject<SoundEvent> Giggle = registerSoundEvents("Giggle");
    public static final RegistryObject<SoundEvent> Giggle2 = registerSoundEvents("Giggle2");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ExampleMod.MODID, name)));
    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
