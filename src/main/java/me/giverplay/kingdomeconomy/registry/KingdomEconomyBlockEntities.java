package me.giverplay.kingdomeconomy.registry;

import me.giverplay.kingdomeconomy.KingdomEconomy;
import me.giverplay.kingdomeconomy.block.entity.ATMBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KingdomEconomyBlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES
    = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, KingdomEconomy.MOD_ID);

  public static final RegistryObject<BlockEntityType<ATMBlockEntity>> ATM = BLOCK_ENTITIES.register("atm",
    () -> BlockEntityType.Builder.of(ATMBlockEntity::new, KingdomEconomyBlocks.ATM_BLOCK.get()).build(null));

  public static void register(IEventBus eventBus) {
    BLOCK_ENTITIES.register(eventBus);
  }
}
