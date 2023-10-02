package me.giverplay.kingdomeconomy.block;

import me.giverplay.kingdomeconomy.block.entity.ATMBlockEntity;
import me.giverplay.kingdomeconomy.registry.KingdomEconomyBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ATMBlock extends BaseEntityBlock {
  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

  public ATMBlock(Properties properties) {
    super(properties);
  }

  @Override
  public BlockState getStateForPlacement(@NotNull BlockPlaceContext placeContext) {
    return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
  }

  @NotNull
  @Override
  public BlockState rotate(@NotNull BlockState blockState, @NotNull Rotation rotation) {
    return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
  }

  @NotNull
  @Override
  public BlockState mirror(BlockState blockState, Mirror mirror) {
    return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
    stateBuilder.add(FACING);
  }

  @Override
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return new ATMBlockEntity(blockPos, blockState);
  }

  @Override
  public void onRemove(@NotNull BlockState state,
                       @NotNull Level level,
                       @NotNull BlockPos blockPos,
                       @NotNull BlockState newState,
                       boolean isMoving) {

    if (state.getBlock() != newState.getBlock()) {
      BlockEntity blockEntity = level.getBlockEntity(blockPos);

      if (blockEntity instanceof ATMBlockEntity atm) {
        atm.drops();
      }
    }

    super.onRemove(state, level, blockPos, newState, isMoving);
  }

  @NotNull
  @Override
  public InteractionResult use(@NotNull BlockState blockState,
                               @NotNull Level level,
                               @NotNull BlockPos blockPos,
                               @NotNull Player player,
                               @NotNull InteractionHand interactionHand,
                               @NotNull BlockHitResult hitResult) {

    if (!level.isClientSide) {
      BlockEntity entity = level.getBlockEntity(blockPos);

      if (entity instanceof ATMBlockEntity atm) {
        NetworkHooks.openScreen((ServerPlayer) player, atm, blockPos);
      } else {
        throw new IllegalStateException("Missing container provider");
      }
    }

    return InteractionResult.SUCCESS;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level,
                                                                @NotNull BlockState blockState,
                                                                @NotNull BlockEntityType<T> entityType) {

    return createTickerHelper(entityType, KingdomEconomyBlockEntities.ATM.get(), ATMBlockEntity::tick);
  }
}
