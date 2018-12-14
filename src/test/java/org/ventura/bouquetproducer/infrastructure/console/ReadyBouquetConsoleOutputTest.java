package org.ventura.bouquetproducer.infrastructure.console;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ventura.bouquetproducer.domain.BouquetSpec;
import org.ventura.bouquetproducer.domain.FlowerSpecie;
import org.ventura.bouquetproducer.domain.Size;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReadyBouquetConsoleOutputTest {

    /*
    <bouquet name><bouquet size><flower 1 quantity><flower 1 specie><f.2 quantity><f.2 specie>...<f.N quantity><f.N species><total quantity of flowers in the bouquet>
     */

    @Mock
    private PrintStream consoleOutput;

    @Test
    void shouldPrintOnConsoleWhenANewReadyBouquetIsNotified() {
        ReadyBouquetConsoleOutput readyBouquetConsoleOutput = new ReadyBouquetConsoleOutput(consoleOutput);

        BouquetSpec bouquetSpec = new BouquetSpec();
        bouquetSpec.setName("P");
        bouquetSpec.setBouquetSize(Size.SMALL);
        bouquetSpec.addFlowerSpecieQuantity(3, new FlowerSpecie("c"));
        bouquetSpec.addFlowerSpecieQuantity(5, new FlowerSpecie("a"));
        bouquetSpec.addFlowerSpecieQuantity(2, new FlowerSpecie("b"));
        bouquetSpec.setTotalFlowersQuantity(15);

        readyBouquetConsoleOutput.notify(bouquetSpec);

        verify(consoleOutput).println("Bouquet ready to build: PS5a2b3c15");
    }
}