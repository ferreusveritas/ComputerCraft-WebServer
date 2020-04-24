package com.qidydl.ccwebserver;

import java.util.List;

import com.ferreusveritas.mcf.ModTabs;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
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
		//setCreativeTab(CreativeTabs.MISC);
		setCreativeTab(ModTabs.mcfTab);
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
		
		TileEntity tileEntity = world.getTileEntity(pos);
		
		if(tileEntity instanceof TileEntityWebModem) {
			return (IPeripheral) tileEntity;
		}
		
		return null;
	}

	///////////////////////////////////////////
	// RENDERING
	///////////////////////////////////////////

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§6ComputerCraft Peripheral");
	}
	
}
