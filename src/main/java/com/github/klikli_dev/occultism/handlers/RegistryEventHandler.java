/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.handlers;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

@Mod.EventBusSubscriber(modid = Occultism.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEventHandler {

    //region Static Methods
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        // Register BlockItems for blocks without custom items
        OccultismBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> OccultismBlocks.BLOCK_DATA_GEN_SETTINGS
                                         .get(block.getRegistryName()).generateDefaultBlockItem).forEach(block -> {
            BlockItem blockItem = new BlockItem(block, new Item.Properties().group(Occultism.ITEM_GROUP));
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
        Occultism.LOGGER.info("Registered BlockItems");

        registerSpawnEgg(registry, OccultismEntities.FOLIOT_TYPE.get(), modLoc("foliot"));
        Occultism.LOGGER.info("Registered SpawnEggItems");
    }

    public static void registerSpawnEgg(IForgeRegistry<Item> registry, EntityType<?> entityType, ResourceLocation registryName){
        SpawnEggItem spawnEggItem = new SpawnEggItem(entityType, 0xaa728d, 0x37222c,
                new Item.Properties().group(Occultism.ITEM_GROUP));
        spawnEggItem.setRegistryName(registryName);
        registry.register(spawnEggItem);
    }
    //endregion Static Methods
}
