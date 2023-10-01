package me.giverplay.kingdomeconomy.registry;

import me.giverplay.kingdomeconomy.KingdomEconomy;
import me.giverplay.kingdomeconomy.block.ATMBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class KingdomEconomyBlocks {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KingdomEconomy.MOD_ID);

  public static final RegistryObject<Block> ATM_BLOCK = registerBlock("atm",
    () -> new ATMBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()));

  private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> blockRegistry = BLOCKS.register(name, block);
    registerBlockItem(name, blockRegistry, KingdomEconomyItems.TAB);
    return blockRegistry;
  }

  private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
    return KingdomEconomyItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
  }

  public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }
}
