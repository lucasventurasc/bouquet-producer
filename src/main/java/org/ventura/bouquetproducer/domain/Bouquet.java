package org.ventura.bouquetproducer.domain;

import org.ventura.bouquetproducer.application.Flower;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Bouquet {

    private BouquetSpec bouquetSpec;
    private Map<FlowerSpecie, Integer> flowersInBouquet;

    public Bouquet(BouquetSpec bouquetSpec) {
        this.bouquetSpec = bouquetSpec;
        this.flowersInBouquet = new HashMap<>();
    }

    public void addFlower(Flower flower) {
        if (!flower.getSize().equals(bouquetSpec.getBouquetSize())) {
            throw new FlowerSizeDifferentFromBouquetSizeException();
        }

        flowersInBouquet.merge(flower.getSpecie(), 1, Integer::sum);
    }

    private boolean isAllExpectedFlowersAlreadyPresent() {
        Map<FlowerSpecie, Integer> quantityOfFlowersBySpecies = bouquetSpec.getQuantityOfFlowersBySpecies();
        for (Entry<FlowerSpecie, Integer> entry : quantityOfFlowersBySpecies.entrySet()) {
            if (!hasFlowersEnough(entry, flowersInBouquet)) {
                return false;
            }
        }
        return true;
    }

    /*
     * There is a bug in this method that I couldn't find, I didn't had time enough to look for it.
     * I think that the problem is on hasAvailableSpaceForAnyKindOfFlowers because the amount of bouquets
     * that are created are very small.
     */
    public boolean needsFlower(Flower flower) {
        Map<FlowerSpecie, Integer> quantityOfFlowersBySpecies = bouquetSpec.getQuantityOfFlowersBySpecies();

        if (!isFlowerSizeSameAsBouquetSize(flower) || bouquetSpec.getTotalFlowersQuantity() == countFlowersInBouquet()) {
            return false;
        }

        if (hasAvailableSpaceForAnyKindOfFlowers()) {
            return true;
        }

        return isSpecieNeeded(flower, quantityOfFlowersBySpecies);
    }

    private boolean hasAvailableSpaceForAnyKindOfFlowers() {
        return countAllExtraFlowersAlreadyAdded() < bouquetSpec.getAllowedSpaceForExtraFlowers();
    }

    private int countAllExtraFlowersAlreadyAdded() {
        int countNotSpecifiedFlowers = 0;
        int countExceededSpecifiedFlowers = 0;

        for (Entry<FlowerSpecie, Integer> entry : flowersInBouquet.entrySet()) {

            if (!bouquetSpec.getQuantityOfFlowersBySpecies().containsKey(entry.getKey())) {
                countNotSpecifiedFlowers = countNotSpecifiedFlowers + entry.getValue();
            } else {
                int countOfFlowersThatExceededTheSpecified = countHowManyFlowersExceededTheSpecified(entry);
                countExceededSpecifiedFlowers = countExceededSpecifiedFlowers + countOfFlowersThatExceededTheSpecified;
            }
        }

        return countNotSpecifiedFlowers + countExceededSpecifiedFlowers;
    }

    private int countHowManyFlowersExceededTheSpecified(Entry<FlowerSpecie, Integer> entry) {
        int countFlowersExceeding = 0;
        int specifiedQuantity = bouquetSpec.getQuantityOfFlowersBySpecies().get(entry.getKey());
        int addedQuantity = entry.getValue();

        if(addedQuantity - specifiedQuantity > 0) {
            countFlowersExceeding = countFlowersExceeding + addedQuantity - specifiedQuantity;
        }
        return countFlowersExceeding;
    }

    private boolean isSpecieNeeded(Flower flower, Map<FlowerSpecie, Integer> quantityOfFlowersBySpecies) {
        return quantityOfFlowersBySpecies.containsKey(flower.getSpecie());
    }

    private boolean isFlowerSizeSameAsBouquetSize(Flower flower) {
        return flower.getSize().equals(bouquetSpec.getBouquetSize());
    }

    public boolean isReady() {
        int flowersCount = countFlowersInBouquet();
        return isAllExpectedFlowersAlreadyPresent() && flowersCount == bouquetSpec.getTotalFlowersQuantity();
    }

    private int countFlowersInBouquet() {
        return flowersInBouquet.values().stream().mapToInt(Integer::valueOf).sum();
    }

    private boolean hasFlowersEnough(Entry<FlowerSpecie, Integer> entry, Map<FlowerSpecie, Integer> flowersInBouquet) {
        return flowersInBouquet.getOrDefault(entry.getKey(), 0) >= entry.getValue();
    }

    public BouquetSpec getBouquetSpec() {
        return bouquetSpec;
    }
}
