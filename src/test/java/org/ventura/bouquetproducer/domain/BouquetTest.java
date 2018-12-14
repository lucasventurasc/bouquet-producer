package org.ventura.bouquetproducer.domain;

import org.junit.jupiter.api.Test;
import org.ventura.bouquetproducer.application.Flower;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.ventura.bouquetproducer.domain.Size.LARGE;
import static org.ventura.bouquetproducer.domain.Size.SMALL;

class BouquetTest {

    @Test
    void addFlowerShouldThrowExceptionWhenReceivedFlowerHasASizeDifferentFromBouquet() {
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName("a");
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("o"));
        bouquetSpec.setBouquetSize(Size.LARGE);
        bouquetSpec.setTotalFlowersQuantity(3);

        Bouquet bouquet = new Bouquet(bouquetSpec);

        assertThrows(FlowerSizeDifferentFromBouquetSizeException.class, () -> {
            bouquet.addFlower(flower("o", SMALL));
        });
    }

    @Test
    void aFlowerShouldBeNeededWhenWasSpecifiedInBouquetSpeciesAndHasNoSlotToExtraFlowers() {
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName("a");
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("o"));
        bouquetSpec.setBouquetSize(Size.LARGE);
        bouquetSpec.setTotalFlowersQuantity(2);

        Bouquet bouquet = new Bouquet(bouquetSpec);

        assertThat(bouquet.needsFlower(flower("o", LARGE))).isTrue();
        assertThat(bouquet.needsFlower(flower("a", SMALL))).isFalse();
        assertThat(bouquet.needsFlower(flower("a", LARGE))).isFalse();
    }

    @Test
    void whenBouquetExtraSlotsAreAvailableAnyFlowerWithSameSizeAsBouquetIsNeeded() {
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName("a");
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("o"));
        bouquetSpec.setBouquetSize(Size.LARGE);
        bouquetSpec.setTotalFlowersQuantity(5);

        Bouquet bouquet = new Bouquet(bouquetSpec);

        assertThat(bouquet.needsFlower(flower("b", LARGE))).isTrue();
        assertThat(bouquet.needsFlower(flower("t", LARGE))).isTrue();
        assertThat(bouquet.needsFlower(flower("o", LARGE))).isTrue();
        assertThat(bouquet.needsFlower(flower("o", SMALL))).isFalse();
        assertThat(bouquet.needsFlower(flower("a", SMALL))).isFalse();
    }

    @Test
    void shouldReturnFalseForNeedsFlowerNotSpecifiedWhenAllExtraSlotsAreAlreadyFilled() {
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName("a");
        bouquetSpec.addFlowerSpecieQuantity(1, new FlowerSpecie("o"));
        bouquetSpec.setBouquetSize(Size.LARGE);
        bouquetSpec.setTotalFlowersQuantity(2);

        Bouquet bouquet = new Bouquet(bouquetSpec);

        assertThat(bouquet.needsFlower(flower("b", LARGE))).isTrue();
        bouquet.addFlower(flower("b", LARGE));

        assertThat(bouquet.needsFlower(flower("b", LARGE))).isFalse();


        assertThat(bouquet.needsFlower(flower("o", LARGE))).isTrue();
        bouquet.addFlower(flower("o", LARGE));
        assertThat(bouquet.needsFlower(flower("o", LARGE))).isFalse();
    }

    @Test
    void aBouquetIsReadyWhenAllHaveAllSpecifiedFlowersAndFlowersCountIsEqualToTheTotalExpected() {
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName("a");
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("o"));
        bouquetSpec.setBouquetSize(Size.LARGE);
        bouquetSpec.setTotalFlowersQuantity(3);

        Bouquet bouquet = new Bouquet(bouquetSpec);
        bouquet.addFlower(flower("o", Size.LARGE));
        bouquet.addFlower(flower("o", Size.LARGE));
        bouquet.addFlower(flower("a", Size.LARGE));

        assertThat(bouquet.isReady()).isTrue();
    }

    private Flower flower(String flowerSpecie, Size size) {
        return new Flower(new FlowerSpecie(flowerSpecie), size);
    }
}