package net.bikerboys.insanity;

import com.mojang.logging.LogUtils;
import net.bikerboys.insanity.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file

@SuppressWarnings("ALL")

@Mod(value = ExampleMod.MODID)
public class ExampleMod
{

    public static MutableComponent currentcrazyword;
    public static Boolean iscrazyword;
    public static boolean iscrazyitem;
    List<String> crazywords = new ArrayList<String>();
    List<String> crazysentences = new ArrayList<String>();
    // Define mod id in a common place for everything to reference
    public static final String MODID = "assets/insanity";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    public ExampleMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        ModSounds.register(modEventBus);




    }




    public static List<String> getOnlinePlayers() {
        Minecraft mc = Minecraft.getInstance();

        // Ensure we're on the client and connected to a server
        if (mc.getConnection() != null) {
            ClientPacketListener connection = mc.getConnection();

            // Get player list and extract names
            return connection.getOnlinePlayers().stream()
                    .map(PlayerInfo::getProfile)
                    .map(profile -> profile.getName())
                    .collect(Collectors.toList());
        }

        return List.of(); // Return empty list if not connected
    }


    private static BlockPos pickFakeStepPos(LocalPlayer player) {

        if (player != null) {
            Vec3 backVec = player.position().add(player.getLookAngle().multiply(-2f, -2f, -2f));
            return new BlockPos((int) backVec.x, (int) backVec.y, (int) backVec.z);
        }
        return new BlockPos(1, 1, 1);
    }

    public void crazyness() {
        Random random = new Random();

        System.out.println("just did crazy");



        if(random.nextInt(10) < 5) {
            iscrazyitem = true;
        } else {iscrazyitem = false;}


        LOGGER.info("just did crazyness() in examplemod.java");


        crazywords.add("NO ONE WILL BELIEVE YOU");
        crazywords.add("THIS ISN'T REAL");
        crazywords.add("YOU DID THIS");
        crazywords.add("WAKE UP WAKE UP WAKE UP WAKE UP");
        crazywords.add("DON'T TRUST THEM");
        crazywords.add("TRUST NO ONE");
        crazywords.add("DON'T TRUST THE TREES");
        crazywords.add("DON'T TRUST HIM");
        crazywords.add("RUN");
        crazywords.add("is someone reading this?");
        crazywords.add("HELP ME");
        crazywords.add("Are you sure that you're alone?");
        crazywords.add("It can hear you");
        crazywords.add("ITS WATCHING");
        crazywords.add("THATS NOT HIM");
        crazywords.add("HE GOT REPLACED");

        crazysentences.add("hey guys can yall return back to base?");
        crazysentences.add("RTB ASAP");



        int randomnumberz = random.nextInt(100);
        if (randomnumberz == 3 || true) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {

                List<String> players = getOnlinePlayers();

                players.remove(mc.player.getName().getString());




                System.out.println("Online Players: " + players);

                players.add("fallback");

                Component fakeName = Component.literal(players.get(random.nextInt(players.size()))).withStyle(ChatFormatting.WHITE);

                Component messageText = Component.literal(crazysentences.get(random.nextInt(crazysentences.size())));

                Component fullMessage = Component.translatable("chat.type.text", fakeName, messageText);




                mc.gui.getChat().addMessage(fullMessage);




            }
        }



        if (random.nextInt(2) == 1) {

            Minecraft mc = Minecraft.getInstance();
            BlockPos blockPos = pickFakeStepPos(mc.player);
            LOGGER.info("just did sound thang");
            if (mc.player != null){
                mc.player.level().playLocalSound(blockPos, SoundEvents.ALLAY_DEATH, SoundSource.MASTER, 1.0F, 1.0F, true);

            }
        }



        if(random.nextInt(10) < 1) {
          String s = crazywords.get(random.nextInt(crazywords.size()));

          currentcrazyword = Component.literal(s);
          iscrazyword = true;



        } else {
            iscrazyword = false;
            currentcrazyword = null;
        }

        //LOGGER.info("this.currentcrazyword is null cause the random chance failed");



        executor.schedule(this::crazyness, 8, TimeUnit.SECONDS);


    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            crazyness();
        }



        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");


    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", net.minecraft.client.Minecraft.getInstance().getUser().getName());
        }
    }
}
