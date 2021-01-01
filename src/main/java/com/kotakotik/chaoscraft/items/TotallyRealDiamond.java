package com.kotakotik.chaoscraft.items;

import com.kotakotik.chaoscraft.TranslationKeys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class TotallyRealDiamond extends Item {
    public TotallyRealDiamond() {
        super(new Item.Properties()
            .group(ItemGroup.MISC)
        );
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(isSelected) {
            entityIn.attackEntityFrom(new DamageSource("chaoscraft.totally_real_diamond_death"), Integer.MAX_VALUE);
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
