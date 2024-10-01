package net.redmelon.fishandshiz.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.item.ModItems;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {
    public ModLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.FISHMEAL_BLOCK);
        addDropWithSilkTouch(ModBlocks.MULM);

        addDrop(ModBlocks.FANWORT, ModItems.FANWORT); {
            this.addDrop(ModBlocks.FANWORT, this.drops(ModItems.FANWORT));
        }
        addDrop(ModBlocks.FANWORT_PLANT, ModItems.FANWORT); {
            this.addDrop(ModBlocks.FANWORT_PLANT, this.drops(ModItems.FANWORT));
        }

        addDrop(ModBlocks.VALLISNERIA, ModItems.VALLISNERIA); {
            this.addDrop(ModBlocks.VALLISNERIA, this.drops(ModItems.VALLISNERIA));
        }
        addDrop(ModBlocks.TALL_VALLISNERIA, ModItems.VALLISNERIA); {
            this.addDrop(ModBlocks.VALLISNERIA, this.drops(ModItems.VALLISNERIA));
        }

        addDrop(ModBlocks.POTHOS_PLANT, ModItems.POTHOS); {
            this.addDrop(ModBlocks.POTHOS_PLANT, this.drops(ModItems.POTHOS));
        }
        addDrop(ModBlocks.NITROGEN_DETECTOR, ModItems.NITROGEN_DETECTOR); {
            this.addDrop(ModBlocks.NITROGEN_DETECTOR, this.drops(ModItems.NITROGEN_DETECTOR));
        }

        addDropWithSilkTouch(ModBlocks.SEA_ANEMONE);

        

    }
}
