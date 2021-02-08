package com.kotakotik.chaoscraft.chaos_events;

import com.kotakotik.chaoscraft.chaos_handlers.ChaosEvent;
import com.kotakotik.chaoscraft.chaos_handlers.ChaosEventRegister;
import com.kotakotik.chaoscraft.chaos_handlers.Credit;
import com.kotakotik.chaoscraft.chaos_handlers.Credits;
import com.kotakotik.chaoscraft.config.ExtraEventConfig;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@ChaosEventRegister
public class EventManhunt extends ChaosEvent {
    @Override
    public String getEnglish() {
        return "Manhunt";
    }

    @Override
    public String getEnglishDescription() {
        return "Spawns 4 zombies in iron armor with speed 1 around the players";
    }

    @Override
    public String getId() {
        return "manhunt";
    }

    public static Field refl = ObfuscationReflectionHelper.findField(ItemStack.class, "delegate");

    @Override
    public void start(MinecraftServer server) {
        int toSpawn = ZOMBIES_TO_SPAWN.getIntConfigValue().get();
        int area = AREA.getIntConfigValue().get();
        Random random = new Random();
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            for(int i = 0; i < toSpawn; i++) {
                // yep i was too lazy to add area config to tnt rain but not to this...
                // i am very consistent :)
                int x = (int) (player.getPosX() + (random.nextInt(area * 2) - area));
                int z = (int) (player.getPosZ() + (random.nextInt(area * 2) - area));
//                ZombieEntity zombie = new ZombieEntity(player.world, x, player.getPosY(), z, player);
                ZombieEntity zombieEntity = new ZombieEntity(player.world);
                zombieEntity.setPosition(x, player.getPosY(), z);
                // slot > 35 & slot < 40
//                for(int i2 = 0; i2 < 4; i2++) {
//                    zombieEntity.setItemStackToSlot();
//                }
                for(EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
                    if (equipmentslottype.getSlotType() == EquipmentSlotType.Group.ARMOR) {
//                        ItemStack itemstack = zombieEntity.getItemStackFromSlot(equipmentslottype);
                        zombieEntity.setItemStackToSlot(equipmentslottype, new ItemStack(getArmor(equipmentslottype)));
                    }
                }

//                zombieEntity.getArmorInventoryList().forEach(stack -> {
//                    try {
//                        for(Item part : armor) {
//                            refl.set(stack, part.delegate);
//                            System.out.println();
//                        }
//                    } catch (IllegalAccessException ignored) {
//
//                    }
//                });
                System.out.println(zombieEntity.getArmorInventoryList());
                player.world.addEntity(zombieEntity);
            }
        }
    }

    public ExtraEventConfig ZOMBIES_TO_SPAWN = new ExtraEventConfig(this, "zombiesToSpawn", "How many zombies to spawn", 4);
    public ExtraEventConfig AREA = new ExtraEventConfig(this, "area", "The radius in which the zombies spawn", 10);

    @Override
    public List<ExtraEventConfig> getExtraConfig() {
        return Arrays.asList(ZOMBIES_TO_SPAWN, AREA);
    }

    @Override
    public List<Credit> getCredits() {
        return Collections.singletonList(Credits.HUSKER.credit);
    }

    public static Item getArmor(EquipmentSlotType slotIn) {
        switch(slotIn) {
            case HEAD:
                return Items.IRON_HELMET;
            case CHEST:
                return Items.IRON_CHESTPLATE;
            case LEGS:
                return Items.IRON_LEGGINGS;
            case FEET:
                return Items.IRON_BOOTS;
            default:
                return null;
        }
    }
}
