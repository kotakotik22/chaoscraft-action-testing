package com.kotakotik.chaoscraft;

import com.kotakotik.chaoscraft.items.BonkBat;
import com.kotakotik.chaoscraft.items.TotallyRealDiamond;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> TOTALLY_REAL_DIAMOND = Registration.ITEMS.register("totally_real_diamond", TotallyRealDiamond::new);
    public static final RegistryObject<Item> BONK_BAT = Registration.ITEMS.register("bonk_bat", BonkBat::new);

    static void register() {
    }
}
