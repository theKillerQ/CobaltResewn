package com.example.screen.util;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.*;

public class ImGuiItemUtil {

    private static final ImInt currentItemIndex = new ImInt(0);

    public static void ItemSelectModal(IItemCallback callback) {
        if (ImGui.button("Select Item")) ImGui.openPopup("Select Item");

        if (!ImGui.beginPopupModal("Select Item", ImGuiWindowFlags.AlwaysAutoResize)) return;

        ImGui.combo("Base Item", currentItemIndex, getItems());

        if (ImGui.button("Confirm")){
            ImGui.closeCurrentPopup();
            String itemName = getItems()[currentItemIndex.intValue()];
            Identifier itemId = getItemIds().get(itemName);
            Item item = Registries.ITEM.get(itemId);
            callback.callback(item);
        }

        ImGui.endPopup();
    }

    private static String[] getItems() {
        List<String> items = new ArrayList<>();

        for (Item item : Registries.ITEM) {
            items.add(item.getName().getString());
        }

        return items.toArray(new String[0]);
    }

    private static Map<String, Identifier> getItemIds(){
        Map<String, Identifier> ids = new HashMap<>();
        for (Item item : Registries.ITEM) {
            ids.put(item.getName().getString(), Registries.ITEM.getId(item));
        }
        return ids;
    }

}
