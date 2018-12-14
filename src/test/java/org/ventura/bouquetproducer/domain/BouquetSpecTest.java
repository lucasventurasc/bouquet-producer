package org.ventura.bouquetproducer.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BouquetSpecTest {

    @Test
    void getAllowedSpaceForExtraFlowersShouldReturnTheDifferenceBetweenSpecifiedSpeciesAndTotalOfFlowersOfBouquet() {
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("t"));
        bouquetSpec.addFlowerSpecieQuantity(4, new FlowerSpecie("x"));
        bouquetSpec.setTotalFlowersQuantity(10);

        assertThat(bouquetSpec.getAllowedSpaceForExtraFlowers()).isEqualTo(4);
    }
}