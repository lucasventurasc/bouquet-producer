package org.ventura.bouquetproducer.infrastructure.console;

import java.util.Scanner;

public class ScannerWrapper {

    private final Scanner scanner;

    public ScannerWrapper() {
        scanner = new Scanner(System.in);
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }
}