package me.giverplay.kingdomeconomy.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.giverplay.kingdomeconomy.KingdomEconomy;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ATMScreen extends AbstractContainerScreen<ATMMenu> {
  private static final ResourceLocation BACKGROUND
    = new ResourceLocation(KingdomEconomy.MOD_ID, "textures/gui/atm_gui.png");

  public ATMScreen(ATMMenu menu, Inventory inventory, Component component) {
    super(menu, inventory, component);
  }

  @Override
  protected void renderBg(@NotNull PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    RenderSystem.setShaderTexture(0, BACKGROUND);

    int x = (width - imageWidth) / 2;
    int y = (height - imageHeight) / 2;

    this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
  }

  @Override
  public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
    renderBackground(poseStack);
    super.render(poseStack, mouseX, mouseY, delta);
    renderTooltip(poseStack, mouseX, mouseY);
  }
}
