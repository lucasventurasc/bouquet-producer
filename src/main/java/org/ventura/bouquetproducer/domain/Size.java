package org.ventura.bouquetproducer.domain;

public enum Size {

    SMALL("S"), LARGE("L");

    private String size;

    Size(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public static Size valueOfString(String size) {
        if(size.equals("S")) {
            return SMALL;
        } else if(size.equals("L")) {
            return LARGE;
        }
        return null;
    }
}
