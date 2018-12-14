package org.ventura.bouquetproducer.infrastructure.console;

import org.ventura.bouquetproducer.domain.BouquetSpec;
import org.ventura.bouquetproducer.domain.FlowerSpecie;
import org.ventura.bouquetproducer.domain.Size;

import java.util.Map;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

class BouquetStringMapper {

    static BouquetSpec toBouquetSpec(String bouquet) {
        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName(valueOf(bouquet.charAt(0)));
        bouquetSpec.setBouquetSize(Size.valueOfString(valueOf(bouquet.charAt(1))));

        String flowerQuantity = "";
        for (int i = 2; i < bouquet.length(); i++) {
            char character = bouquet.charAt(i);

            if(Character.isDigit(character)) {
                flowerQuantity = flowerQuantity + character;
            } else {
                bouquetSpec.addFlowerSpecieQuantity(parseInt(flowerQuantity), new FlowerSpecie(valueOf(character)));
                flowerQuantity = "";
            }
        }

        bouquetSpec.setTotalFlowersQuantity(parseInt(flowerQuantity));
        return bouquetSpec;
    }

    static String toBouquetString(BouquetSpec bouquetSpec) {
        Map<FlowerSpecie, Integer> mapSortedByString = new TreeMap<>(bouquetSpec.getQuantityOfFlowersBySpecies());

        StringBuilder string = new StringBuilder();
        string.append(bouquetSpec.getName());
        string.append(bouquetSpec.getBouquetSize().getSize());
        mapSortedByString.forEach((key, value) -> string.append(value).append(key.getName()));
        string.append(bouquetSpec.getTotalFlowersQuantity());
        return string.toString();
    }
}
