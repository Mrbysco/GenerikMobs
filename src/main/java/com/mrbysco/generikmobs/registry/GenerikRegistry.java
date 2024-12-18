package com.mrbysco.generikmobs.registry;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.block.PuddleBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GenerikRegistry {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(GenerikMod.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GenerikMod.MOD_ID);

	public static final DeferredBlock<PuddleBlock> SLIME_PUDDLE = BLOCKS.register("slime_puddle", () -> new PuddleBlock(Block.Properties.ofFullCopy(Blocks.GREEN_CARPET)
			.mapColor(MapColor.GRASS).forceSolidOn().noCollission().sound(SoundType.SLIME_BLOCK).noOcclusion()));

	public static final DeferredItem<BlockItem> SLIME_PUDDLE_ITEM = ITEMS.registerSimpleBlockItem(SLIME_PUDDLE);
}
