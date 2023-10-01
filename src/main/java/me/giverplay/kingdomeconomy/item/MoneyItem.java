package me.giverplay.kingdomeconomy.item;

import me.giverplay.kingdomeconomy.registry.KingdomEconomyItems;
import net.minecraft.world.item.Item;

public class MoneyItem extends Item {
  private final float value;

  public MoneyItem(Properties properties, float value) {
    super(properties
      .tab(KingdomEconomyItems.TAB));

    this.value = value;
  }

  public MoneyItem(float value) {
    this(new Item.Properties(), value);
  }

  public float getValue() {
    return value;
  }
}
