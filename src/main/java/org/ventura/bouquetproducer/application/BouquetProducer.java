package org.ventura.bouquetproducer.application;

import org.ventura.bouquetproducer.domain.Bouquet;
import org.ventura.bouquetproducer.domain.BouquetSpec;

import java.util.ArrayList;
import java.util.List;

public class BouquetProducer {

    private BouquetReadyListener bouquetReadyListener;
    private List<BouquetSpec> bouquetSpecs;
    private List<Bouquet> bouquetsInDevelopment;

    public BouquetProducer(BouquetReadyListener bouquetReadyListener, List<BouquetSpec> bouquetSpecs) {
        this.bouquetReadyListener = bouquetReadyListener;
        this.bouquetSpecs = bouquetSpecs;
        this.bouquetsInDevelopment = new ArrayList<>();
    }

    public void addFlower(Flower flower) {
        initialiseBouquetSpecs();

        for (Bouquet bouquet : bouquetsInDevelopment) {
            if (bouquet.needsFlower(flower)) {
                bouquet.addFlower(flower);
            }

            if (bouquet.isReady()) {
                finishBouquet(bouquet);
            }
        }
    }

    private void initialiseBouquetSpecs() {
        if (bouquetsInDevelopment.isEmpty()) {
            bouquetSpecs.forEach(bouquetSpec -> bouquetsInDevelopment.add(new Bouquet(bouquetSpec)));
        }
    }

    private void finishBouquet(Bouquet bouquet) {
        bouquetsInDevelopment.set(bouquetsInDevelopment.indexOf(bouquet), new Bouquet(bouquet.getBouquetSpec()));
        bouquetReadyListener.notify(bouquet.getBouquetSpec());
    }
}