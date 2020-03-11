package com.qidydl.ccwebserver;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

public class BlockWebModem extends Block implements ITileEntityProvider, IPeripheralProvider {

	private static String blockName = "webmodem";
	
	public BlockWebModem() {
		super(Material.IRON);
		setRegistryName(blockName);
		setUnlocalizedName(blockName);
		setCreativeTab(CreativeTabs.MISC);
		ComputerCraftAPI.registerPeripheralProvider(this);
		GameRegistry.registerTileEntity(TileEntityWebModem.class, new ResourceLocation(ModConstants.MODID, blockName));
	}
	
	/**
	 * Get a new Tile Entity associated with this block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWebModem();
	}


	@Override
	public IPeripheral getPeripheral(World world, BlockPos pos, EnumFacing side) {
		return (TileEntityWebModem)world.getTileEntity(pos);
	}

	///////////////////////////////////////////
	// RENDERING
	///////////////////////////////////////////

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§6ComputerCraft Peripheral");
	}
	
}
