package org.ventura.bouquetproducer.infrastructure.console;

import org.ventura.bouquetproducer.application.Flower;
import org.ventura.bouquetproducer.domain.BouquetSpec;

import java.util.function.Function;

public class BouquetStreamInputReader {

    private ScannerWrapper scannerWrapper;

    public BouquetStreamInputReader(ScannerWrapper scannerWrapper) {
        this.scannerWrapper = scannerWrapper;
    }

    public void read(Function<BouquetSpec, Void> addedBouquetsCallback, Function<Flower, Void> addedFlowersCallback) {
        boolean readingSpecs = true;

        while(scannerWrapper.hasNextLine()) {
            String line = scannerWrapper.nextLine();
            if (line.equals("")) {
                readingSpecs = false;
                continue;
            }

            if(readingSpecs) {
                addedBouquetsCallback.apply(BouquetStringMapper.toBouquetSpec(line));
            } else {
                addedFlowersCallback.apply(FlowerStringMapper.toFlower(line));
            }
        }
    }
}
