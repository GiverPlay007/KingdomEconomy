package me.giverplay.kingdomeconomy.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class KingdomEconomyTab extends CreativeModeTab {
  public KingdomEconomyTab() {
    super("kingdomEconomyTab");
  }

  @Override
  public ItemStack makeIcon() {
    return new ItemStack(KingdomEconomyItems.ONE_BILL.get());
  }
}
