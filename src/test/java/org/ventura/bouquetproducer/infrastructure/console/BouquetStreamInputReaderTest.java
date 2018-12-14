package org.ventura.bouquetproducer.infrastructure.console;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.ventura.bouquetproducer.application.Flower;
import org.ventura.bouquetproducer.domain.BouquetSpec;
import org.ventura.bouquetproducer.domain.FlowerSpecie;
import org.ventura.bouquetproducer.domain.Size;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.ventura.bouquetproducer.domain.Size.LARGE;
import static org.ventura.bouquetproducer.domain.Size.SMALL;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BouquetStreamInputReaderTest {

    @Mock
    private ScannerWrapper scannerWrapper;

    @Test
    void shouldReadAStreamOfBouquetsSpecsAndFlowers() {
        BouquetStreamInputReader bouquetStreamInputReader = new BouquetStreamInputReader(scannerWrapper);

        when(scannerWrapper.nextLine()).thenReturn("AS3a4b6k20", "AL8d10r5t30", "", "aS", "aS", "bL", "rL", "tS");

        List<BouquetSpec> bouquetSpecs = new ArrayList<>();
        List<Flower> flowers = new ArrayList<>();
        bouquetStreamInputReader.read(bouquetSpec -> {

            bouquetSpecs.add(bouquetSpec);
            if (bouquetSpecs.size() == 2) assertBouquetSpecs(bouquetSpecs);
            return null;

        }, flower -> {

            flowers.add(flower);
            if (flowers.size() == 5) assertFlowerSpecies(flowers);
            return null;

        });
    }

    private void assertFlowerSpecies(List<Flower> flowers) {
        assertThat(flowers).containsExactly(
                new Flower(new FlowerSpecie("a"), SMALL),
                new Flower(new FlowerSpecie("a"), SMALL),
                new Flower(new FlowerSpecie("b"), LARGE),
                new Flower(new FlowerSpecie("r"), LARGE),
                new Flower(new FlowerSpecie("t"), SMALL)
        );
    }

    private void assertBouquetSpecs(List<BouquetSpec> bouquetSpecs) {
        assertThat(bouquetSpecs.get(0).getName()).isEqualTo("A");
        assertThat(bouquetSpecs.get(0).getBouquetSize()).isEqualTo(SMALL);
        assertThat(bouquetSpecs.get(0).getTotalFlowersQuantity()).isEqualTo(20);
        assertThat(bouquetSpecs.get(0).getQuantityOfFlowersBySpecies()).contains(
                new SimpleEntry<>(new FlowerSpecie("a"), 3),
                new SimpleEntry<>(new FlowerSpecie("b"), 4),
                new SimpleEntry<>(new FlowerSpecie("k"), 6)
        );

        assertThat(bouquetSpecs.get(1).getName()).isEqualTo("A");
        assertThat(bouquetSpecs.get(1).getBouquetSize()).isEqualTo(Size.LARGE);
        assertThat(bouquetSpecs.get(1).getTotalFlowersQuantity()).isEqualTo(30);
        assertThat(bouquetSpecs.get(1).getQuantityOfFlowersBySpecies()).contains(
                new SimpleEntry<>(new FlowerSpecie("d"), 8),
                new SimpleEntry<>(new FlowerSpecie("r"), 10),
                new SimpleEntry<>(new FlowerSpecie("t"), 5)
        );
    }
}