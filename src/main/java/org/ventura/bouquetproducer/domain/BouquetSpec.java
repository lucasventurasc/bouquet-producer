package org.ventura.bouquetproducer.domain;

import java.util.HashMap;
import java.util.Map;

public class BouquetSpec {

    private String name;
    private Size size;
    private Integer totalFlowersQuantity;
    private Map<FlowerSpecie, Integer> quantityOfFlowersBySpecies;

    public BouquetSpec() {
        this.quantityOfFlowersBySpecies = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Size getBouquetSize() {
        return size;
    }

    public void setBouquetSize(Size size) {
        this.size = size;
    }

    public Integer getTotalFlowersQuantity() {
        return totalFlowersQuantity;
    }

    public void setTotalFlowersQuantity(Integer totalFlowersQuantity) {
        this.totalFlowersQuantity = totalFlowersQuantity;
    }

    public Map<FlowerSpecie, Integer> getQuantityOfFlowersBySpecies() {
        return quantityOfFlowersBySpecies;
    }

    public void addFlowerSpecieQuantity(int quantityOfFlowers, FlowerSpecie flowerSpecie) {
        quantityOfFlowersBySpecies.put(flowerSpecie, quantityOfFlowers);
    }

    public Integer getAllowedSpaceForExtraFlowers() {
        return totalFlowersQuantity - quantityOfFlowersBySpecies.values().stream().mapToInt(Integer::valueOf).sum();
    }
}
