package org.ventura.bouquetproducer.domain;

import java.util.Objects;

public class FlowerSpecie implements Comparable<FlowerSpecie> {

    private String name;

    public FlowerSpecie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowerSpecie that = (FlowerSpecie) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(FlowerSpecie o) {
        return getName().compareTo(o.getName());
    }
}


