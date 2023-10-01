package me.giverplay.kingdomeconomy.registry;

import me.giverplay.kingdomeconomy.KingdomEconomy;
import me.giverplay.kingdomeconomy.item.MoneyItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KingdomEconomyItems {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KingdomEconomy.MOD_ID);
  public static final CreativeModeTab TAB = new KingdomEconomyTab();

  public static final RegistryObject<Item> ONE_COIN = ITEMS.register("one_coin", () -> new MoneyItem(0.01f));
  public static final RegistryObject<Item> FIVE_COIN = ITEMS.register("five_coin", () -> new MoneyItem(0.05f));
  public static final RegistryObject<Item> TEN_COIN = ITEMS.register("ten_coin", () -> new MoneyItem(0.1f));
  public static final RegistryObject<Item> TWENTY_FIVE_COIN = ITEMS.register("twenty_five_coin", () -> new MoneyItem(0.25f));
  public static final RegistryObject<Item> FIFTY_COIN = ITEMS.register("fifty_coin", () -> new MoneyItem(0.5f));

  public static final RegistryObject<Item> ONE_BILL = ITEMS.register("one_bill", () -> new MoneyItem(1.0f));
  public static final RegistryObject<Item> TWO_BILL = ITEMS.register("two_bill", () -> new MoneyItem(2.0f));
  public static final RegistryObject<Item> FIVE_BILL = ITEMS.register("five_bill", () -> new MoneyItem(5.0f));
  public static final RegistryObject<Item> TEN_BILL = ITEMS.register("ten_bill", () -> new MoneyItem(10.0f));
  public static final RegistryObject<Item> TWENTY_BILL = ITEMS.register("twenty_bill", () -> new MoneyItem(20.0f));
  public static final RegistryObject<Item> FIFTY_BILL = ITEMS.register("fifty_bill", () -> new MoneyItem(50.0f));
  public static final RegistryObject<Item> ONE_HUNDRED_BILL = ITEMS.register("one_hundred_bill", () -> new MoneyItem(100.0f));

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
