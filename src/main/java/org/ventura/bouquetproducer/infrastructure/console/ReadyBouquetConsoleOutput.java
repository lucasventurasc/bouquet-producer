package org.ventura.bouquetproducer.infrastructure.console;

import org.ventura.bouquetproducer.application.BouquetReadyListener;
import org.ventura.bouquetproducer.domain.BouquetSpec;

import java.io.PrintStream;

public class ReadyBouquetConsoleOutput implements BouquetReadyListener {

    private PrintStream out;

    public ReadyBouquetConsoleOutput(PrintStream out) {
        this.out = out;
    }

    @Override
    public void notify(BouquetSpec bouquetSpec) {
        out.println("Bouquet ready to build: " + BouquetStringMapper.toBouquetString(bouquetSpec));
    }
}
