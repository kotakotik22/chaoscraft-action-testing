package com.kotakotik.chaoscraft.items;

import com.kotakotik.chaoscraft.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class BonkBat extends SwordItem {
    public BonkBat() {
        super(ItemTier.WOOD, 10, -3.0F, (new Item.Properties()).group(ItemGroup.COMBAT));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }


    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    public static ItemStack create() {
        ItemStack stack = new ItemStack(ModItems.BONK_BAT.get());
        stack.addEnchantment(Enchantments.KNOCKBACK, 4);
        return stack;
    }
}
