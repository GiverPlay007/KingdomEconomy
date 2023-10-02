package me.giverplay.kingdomeconomy.block.entity;

import me.giverplay.kingdomeconomy.registry.KingdomEconomyBlockEntities;
import me.giverplay.kingdomeconomy.screen.ATMMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ATMBlockEntity extends BlockEntity implements MenuProvider {

  private final ContainerData data;
  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

  private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
    @Override
    protected void onContentsChanged(int slot) {
      setChanged();
    }
  };

  public ATMBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(KingdomEconomyBlockEntities.ATM.get(), blockPos, blockState);

    this.data = new SimpleContainerData(1);
    this.data.set(0, 0);
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
    return new ATMMenu(id, inventory, this, this.data);
  }

  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    if (cap == ForgeCapabilities.ITEM_HANDLER) {
      return lazyItemHandler.cast();
    }

    return super.getCapability(cap, side);
  }

  @Override
  public void onLoad() {
    super.onLoad();
    lazyItemHandler = LazyOptional.of(() -> itemHandler);
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    lazyItemHandler.invalidate();
  }

  @Override
  protected void saveAdditional(@NotNull CompoundTag nbt) {
    super.saveAdditional(nbt);
    nbt.put("inventory", itemHandler.serializeNBT());
  }

  @Override
  public void load(@NotNull CompoundTag nbt) {
    super.load(nbt);
    itemHandler.deserializeNBT(nbt.getCompound("inventory"));
  }

  @NotNull
  @Override
  public Component getDisplayName() {
    return Component.translatable("gui.kingdomeconomy.atm");
  }

  public void drops() {
    SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

    for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
      inventory.setItem(slot, itemHandler.getStackInSlot(slot));
    }

    Containers.dropContents(this.level, this.worldPosition, inventory);
  }

  public static void tick(Level level, BlockPos blockPos, BlockState blockState, ATMBlockEntity atmBlockEntity) {
    if(level.isClientSide) return;
  }
}
