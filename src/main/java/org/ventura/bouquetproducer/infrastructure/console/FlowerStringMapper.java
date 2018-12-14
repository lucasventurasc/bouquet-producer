package org.ventura.bouquetproducer.infrastructure.console;

import org.ventura.bouquetproducer.application.Flower;
import org.ventura.bouquetproducer.domain.FlowerSpecie;
import org.ventura.bouquetproducer.domain.Size;

class FlowerStringMapper {

    static Flower toFlower(String line) {
        String flowerSpecie = String.valueOf(line.charAt(0));
        String size = String.valueOf(line.charAt(1));
        return new Flower(new FlowerSpecie(flowerSpecie), Size.valueOfString(size));
    }
}
