package me.giverplay.kingdomeconomy.screen;

import me.giverplay.kingdomeconomy.block.entity.ATMBlockEntity;
import me.giverplay.kingdomeconomy.registry.KingdomEconomyBlocks;
import me.giverplay.kingdomeconomy.registry.KingdomEconomyMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ATMMenu extends AbstractContainerMenu {

  // CREDIT: diesieben07 | https://github.com/diesieben07/SevenCommons
  private static final int HOTBAR_SLOT_COUNT = 9;
  private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
  private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
  private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
  private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
  private static final int VANILLA_FIRST_SLOT_INDEX = 0;
  private static final int INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
  private static final int INVENTORY_SLOT_COUNT = 4;

  private final ATMBlockEntity blockEntity;
  private final Level level;
  private final ContainerData data;

  public ATMMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
    this(id,
      inventory,
      inventory.player.level.getBlockEntity(extraData.readBlockPos()),
      new SimpleContainerData(INVENTORY_SLOT_COUNT));
  }

  public ATMMenu(int id, Inventory inventory, BlockEntity entity, ContainerData data) {
    super(KingdomEconomyMenuTypes.ATM_MENU.get(), id);
    checkContainerSize(inventory, INVENTORY_SLOT_COUNT);

    this.blockEntity = (ATMBlockEntity) entity;
    this.level = inventory.player.level;
    this.data = data;

    this.addPlayerInventory(inventory);
    this.addPlayerHotbar(inventory);

    this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
      this.addSlot(new SlotItemHandler(handler, 0, 12, 35));
      this.addSlot(new SlotItemHandler(handler, 1, 40, 35));
      this.addSlot(new SlotItemHandler(handler, 2, 78, 35));
      this.addSlot(new SlotItemHandler(handler, 3, 100, 35));
    });

    addDataSlots(data);
  }

  // CREDIT: diesieben07 | https://github.com/diesieben07/SevenCommons
  @Override
  public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
    Slot sourceSlot = slots.get(index);

    if (!sourceSlot.hasItem()) return ItemStack.EMPTY;

    ItemStack sourceStack = sourceSlot.getItem();
    ItemStack copyOfSourceStack = sourceStack.copy();

    if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
      if (!moveItemStackTo(sourceStack, INVENTORY_FIRST_SLOT_INDEX, INVENTORY_FIRST_SLOT_INDEX
        + INVENTORY_SLOT_COUNT, false)) {
        return ItemStack.EMPTY;
      }
    } else if (index < INVENTORY_FIRST_SLOT_INDEX + INVENTORY_SLOT_COUNT) {
      if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
        return ItemStack.EMPTY;
      }
    } else {
      return ItemStack.EMPTY;
    }

    if (sourceStack.getCount() == 0) {
      sourceSlot.set(ItemStack.EMPTY);
    } else {
      sourceSlot.setChanged();
    }

    sourceSlot.onTake(playerIn, sourceStack);
    return copyOfSourceStack;
  }

  @Override
  public boolean stillValid(@NotNull Player player) {
    return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, KingdomEconomyBlocks.ATM_BLOCK.get());
  }

  private void addPlayerInventory(Inventory playerInventory) {
    for (int row = 0; row < PLAYER_INVENTORY_ROW_COUNT; ++row) {
      for (int slot = 0; slot < PLAYER_INVENTORY_COLUMN_COUNT; ++slot) {
        this.addSlot(new Slot(playerInventory, slot + row * 9 + 9, 8 + slot * 18, 86 + row * 18));
      }
    }
  }

  private void addPlayerHotbar(Inventory playerInventory) {
    for (int slot = 0; slot < PLAYER_INVENTORY_COLUMN_COUNT; ++slot) {
      this.addSlot(new Slot(playerInventory, slot, 8 + slot * 18, 144));
    }
  }
}
