package org.ventura.bouquetproducer.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ventura.bouquetproducer.domain.BouquetSpec;
import org.ventura.bouquetproducer.domain.FlowerSpecie;
import org.ventura.bouquetproducer.domain.Size;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.verify;
import static org.ventura.bouquetproducer.domain.Size.LARGE;
import static org.ventura.bouquetproducer.domain.Size.SMALL;

@ExtendWith(MockitoExtension.class)
class BouquetSpecProducerTest {

    @Mock
    private BouquetReadyListener bouquetReadyListener;

    @Test
    void shouldNotifyOneBouquetWhenAllSpecifiedFlowersAreReceived() {
        //AS3a4b2k20
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName("A");
        bouquetSpec.setBouquetSize(Size.SMALL);
        bouquetSpec.addFlowerSpecieQuantity(3, new FlowerSpecie("a"));
        bouquetSpec.addFlowerSpecieQuantity(4, new FlowerSpecie("b"));
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("k"));
        bouquetSpec.setTotalFlowersQuantity(9);

        BouquetProducer bouquetProducer = new BouquetProducer(bouquetReadyListener, singletonList(bouquetSpec));

        bouquetProducer.addFlower(flower("a", SMALL));
        bouquetProducer.addFlower(flower("a", SMALL));
        bouquetProducer.addFlower(flower("a", SMALL));
        bouquetProducer.addFlower(flower("b", SMALL));
        bouquetProducer.addFlower(flower("b", SMALL));
        bouquetProducer.addFlower(flower("b", SMALL));
        bouquetProducer.addFlower(flower("b", SMALL));
        bouquetProducer.addFlower(flower("k", SMALL));
        bouquetProducer.addFlower(flower("k", SMALL));

        verify(bouquetReadyListener).notify(bouquetSpec);
    }

    @Test
    void shouldProduceABouquetWithExtraFlowersReceivingFlowersInAnyOrder() {
        //AS3a4b2k20
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName("A");
        bouquetSpec.setBouquetSize(Size.SMALL);
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("x"));
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("y"));
        bouquetSpec.setTotalFlowersQuantity(6);

        BouquetProducer bouquetProducer = new BouquetProducer(bouquetReadyListener, singletonList(bouquetSpec));

        bouquetProducer.addFlower(flower("x", SMALL));
        bouquetProducer.addFlower(flower("y", SMALL));
        bouquetProducer.addFlower(flower("t", SMALL));
        bouquetProducer.addFlower(flower("y", SMALL));
        bouquetProducer.addFlower(flower("x", SMALL));
        bouquetProducer.addFlower(flower("t", SMALL));

        verify(bouquetReadyListener).notify(bouquetSpec);
    }

    @Test
    void shouldProduceABouquetWithExtraFlowersWithSameTypeOfSpecifiedFlowers() {
        //AS3a4b2k20
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName("A");
        bouquetSpec.setBouquetSize(Size.SMALL);
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("x"));
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("y"));
        bouquetSpec.setTotalFlowersQuantity(6);

        BouquetProducer bouquetProducer = new BouquetProducer(bouquetReadyListener, singletonList(bouquetSpec));

        bouquetProducer.addFlower(flower("x", SMALL));
        bouquetProducer.addFlower(flower("y", SMALL));
        bouquetProducer.addFlower(flower("y", SMALL));
        bouquetProducer.addFlower(flower("y", SMALL));
        bouquetProducer.addFlower(flower("x", SMALL));
        bouquetProducer.addFlower(flower("x", SMALL));

        verify(bouquetReadyListener).notify(bouquetSpec);
    }

    @Test
    void shouldProduceMultipleDifferentBouquetSpecs() {
        BouquetSpec bouquetSpecH = new BouquetSpec();
        bouquetSpecH.setName("H");
        bouquetSpecH.setBouquetSize(Size.SMALL);
        bouquetSpecH.addFlowerSpecieQuantity(1, new FlowerSpecie("h"));
        bouquetSpecH.setTotalFlowersQuantity(1);

        BouquetSpec bouquetSpecNl = new BouquetSpec();
        bouquetSpecNl.setName("N");
        bouquetSpecNl.setBouquetSize(Size.LARGE);
        bouquetSpecNl.addFlowerSpecieQuantity(1, new FlowerSpecie("n"));
        bouquetSpecNl.setTotalFlowersQuantity(2);

        BouquetSpec bouquetSpecNs = new BouquetSpec();
        bouquetSpecNs.setName("N");
        bouquetSpecNs.setBouquetSize(Size.SMALL);
        bouquetSpecNs.addFlowerSpecieQuantity(1, new FlowerSpecie("n"));
        bouquetSpecNs.setTotalFlowersQuantity(1);

        List<BouquetSpec> specs = asList(bouquetSpecH, bouquetSpecNl, bouquetSpecNs);
        BouquetProducer bouquetProducer = new BouquetProducer(bouquetReadyListener, specs);

        bouquetProducer.addFlower(flower("n", LARGE));

        bouquetProducer.addFlower(flower("n", SMALL));
        verify(bouquetReadyListener).notify(bouquetSpecNs);

        bouquetProducer.addFlower(flower("n", LARGE));
        verify(bouquetReadyListener).notify(bouquetSpecNl);

        bouquetProducer.addFlower(flower("h", SMALL));
        verify(bouquetReadyListener).notify(bouquetSpecH);
    }

    private Flower flower(String flowerSpecie, Size size) {
        return new Flower(new FlowerSpecie(flowerSpecie), size);
    }
}