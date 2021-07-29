package dev.blizzardlibrary.builder;
import dev.blizzardlibrary.string.chat.FormatUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class ItemBuilder {

    private static ItemBuilder instance = null;

    private Material material;

    private int amount;

    private byte id;

    private String name;

    private List<String> lore;

    private Map<Enchantment, Integer> enchantmentMap;

    public ItemBuilder itemType(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder itemAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder itemID(byte id) {
        this.id = id;
        return this;
    }

    public ItemBuilder itemName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder itemLore(List<String> lores) {
        lore = lores.stream().map(FormatUtils::color).collect(Collectors.toList());
        return this;
    }

    public ItemBuilder itemEnchant(Map<Enchantment, Integer> enchants) {
        this.enchantmentMap = enchants;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(FormatUtils.color(name));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack buildWithID() {
        ItemStack item = new ItemStack(material, amount, id);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(FormatUtils.color(name));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack buildWithEnchants() {
        ItemStack item = new ItemStack(material, amount);
        item.addUnsafeEnchantments(enchantmentMap);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(FormatUtils.color(name));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack buildWithIDAndEnchants() {
        ItemStack item = new ItemStack(material, amount, id);
        item.addUnsafeEnchantments(enchantmentMap);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(FormatUtils.color(name));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
