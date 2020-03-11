package com.qidydl.ccwebserver;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = ModConstants.MODID, version=ModConstants.VERSION, dependencies=ModConstants.DEPENDENCIES)
public class CCWebServer {
	
	@Mod.Instance(ModConstants.MODID)
	public static CCWebServer instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="com.qidydl.ccwebserver.client.ClientProxy", serverSide="com.qidydl.ccwebserver.CommonProxy")
	public static CommonProxy proxy;

	// The port to listen on for incoming requests.
	public static int LISTEN_PORT = 60000;

	// Standard Java instance variables
	public static Block blockWebModem;
	private CommsThread commsThread;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try {
			cfg.load();
			LISTEN_PORT = cfg.get(Configuration.CATEGORY_GENERAL, "listenPort", LISTEN_PORT).getInt();
		}
		catch (Exception e) {
			FMLLog.log.warn(e.toString(), "ComputerCraft WebServer has a problem loading its configuration");
		}
		finally	{
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}

		blockWebModem = new BlockWebModem();
	}
	
	@Mod.EventBusSubscriber(modid = ModConstants.MODID)
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			IForgeRegistry<Block> registry = event.getRegistry();
			registry.register(blockWebModem);
		}

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			IForgeRegistry<Item> registry = event.getRegistry();
			registry.register( new ItemBlock(blockWebModem).setRegistryName(blockWebModem.getRegistryName()) );
		}

		@SubscribeEvent
		public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
			//ItemStack stoneStack = new ItemStack(Blocks.STONE);
			//ItemStack enderPearlStack = new ItemStack(Items.ENDER_PEARL);
			//ItemStack diamondStack = new ItemStack(Items.DIAMOND);
			//ItemStack webModemStack = new ItemStack(blockWebModem);

			//GameRegistry.addRecipe(webModemStack, "xxx", "xyx", "xzx", 'x', stoneStack, 'y', enderPearlStack, 'z', diamondStack);
		}
		
	}
	

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		commsThread = new CommsThread(LISTEN_PORT);
		CommsThread.setInstance(commsThread);
		commsThread.start();
	}

	@Mod.EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		commsThread.shutdown();
		try {
			commsThread.join();
		}
		catch (InterruptedException e) {
			// We don't care, we're shutting down anyway.
		}
	}
	
}
