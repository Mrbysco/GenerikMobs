package com.mrbysco.generikmobs.registry;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.block.PuddleBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GenerikRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GenerikMod.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GenerikMod.MOD_ID);

	public static final RegistryObject<PuddleBlock> SLIME_PUDDLE = BLOCKS.register("slime_puddle", () -> new PuddleBlock(Block.Properties.copy(Blocks.GREEN_CARPET)
			.mapColor(MapColor.GRASS).forceSolidOn().noCollission().sound(SoundType.SLIME_BLOCK).noOcclusion()));

	public static final RegistryObject<Item> SLIME_PUDDLE_ITEM = ITEMS.register("slime_puddle", () -> new BlockItem(SLIME_PUDDLE.get(), new Item.Properties()));
}
