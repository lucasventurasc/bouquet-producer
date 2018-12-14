package org.ventura.bouquetproducer.application;

import org.ventura.bouquetproducer.domain.FlowerSpecie;
import org.ventura.bouquetproducer.domain.Size;

import java.util.Objects;

public class Flower {

    private FlowerSpecie specie;
    private Size flowerSize;

    public Flower(FlowerSpecie specie, Size flowerSize) {
        this.specie = specie;
        this.flowerSize = flowerSize;
    }

    public FlowerSpecie getSpecie() {
        return specie;
    }

    public Size getSize() {
        return flowerSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return Objects.equals(specie, flower.specie) &&
                flowerSize == flower.flowerSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(specie, flowerSize);
    }
}
