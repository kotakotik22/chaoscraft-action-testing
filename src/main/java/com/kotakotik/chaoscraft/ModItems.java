package com.kotakotik.chaoscraft;

import com.kotakotik.chaoscraft.items.TotallyRealDiamond;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> TOTALLY_REAL_DIAMOND = Registration.ITEMS.register("totally_real_diamond", TotallyRealDiamond::new);

    static void register() {
    }
}
