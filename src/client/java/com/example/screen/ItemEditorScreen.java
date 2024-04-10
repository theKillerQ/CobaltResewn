package com.example.screen;

import com.example.screen.util.ImGuiItemUtil;
import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import imgui.type.ImString;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.client.itemgroup.CreativeGuiExtensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemEditorScreen {

    private final ImString itemInternalName = new ImString(255);
    private final ImString itemDisplayName = new ImString(255);

    private Item selectedItem = Items.DIRT;

    public void open() {
        MinecraftClient.getInstance().setScreen(new ImguiScreen(io -> {

            ImGui.dockSpaceOverViewport(ImGui.getMainViewport(), ImGuiDockNodeFlags.PassthruCentralNode);

            if (ImGui.begin("Demo")) {
                ImGui.text("Item Editor v0.1.0");

                // ImGui.inputText("Internal Name", itemInternalName);
                // ImGui.inputText("Display Name", itemDisplayName);

                // Item type
                /*
                ImGui.inputText("##", itemFilter);
                ImGui.sameLine();
                if (ImGui.beginCombo("Items", currentValue)) {
                    for (Item item : Registries.ITEM) {
                        boolean isSelected = (currentValue.equalsIgnoreCase(item.getName().getString()));
                        if (ImGui.selectable(item.getName().getString(), isSelected))
                            currentValue = item.getName().getString();
                        if (isSelected)
                            ImGui.setItemDefaultFocus();
                    }
                    ImGui.endCombo();
                }
                 */

                // Item dropdown
                // ImGui.combo("Base Item", currentItemIndex, getItems());

                // Base Item Selection
                ImGui.text("Base Item: " + selectedItem.getName().getString());
                ImGui.sameLine();
                ImGuiItemUtil.ItemSelectModal(this::selectItem);

                // Generic item settings
                ImGui.separator();
                ImGui.inputText("Display Name", itemDisplayName);

                if (ImGui.button("Give")) {
                    ItemStack stack = new ItemStack(selectedItem);
                    if (itemDisplayName.isNotEmpty()) stack.setCustomName(Text.empty().append(itemDisplayName.toString()));

                    MinecraftClient.getInstance().player.giveItemStack(stack);
                }

                // TODO: REMOVE
                // Test item images
                ItemStack iconItem = new ItemStack(Items.DIRT);
                MinecraftClient client = MinecraftClient.getInstance();
                BakedModel model = client.getItemRenderer().getModel(iconItem, client.world, client.player, 0);
                ImDrawList drawList = ImGui.getWindowDrawList();
                ImVec2 windowSize = ImGui.getWindowSize();
                drawList.addCircleFilled(windowSize.x / 2, windowSize.y / 2, windowSize.y / 5, ImColor.rgb(Color.red));

            }
            ImGui.end();
        }));
    }

    private void selectItem(Item item) {
        selectedItem = item;
    }

    private static String[] getItems() {
        List<String> items = new ArrayList<>();

        for (Item item : Registries.ITEM) {
            items.add(item.getName().getString());
        }

        return items.toArray(new String[0]);
    }



}
