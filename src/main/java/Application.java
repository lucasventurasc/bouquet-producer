import org.ventura.bouquetproducer.application.BouquetProducer;
import org.ventura.bouquetproducer.domain.BouquetSpec;
import org.ventura.bouquetproducer.infrastructure.console.BouquetStreamInputReader;
import org.ventura.bouquetproducer.infrastructure.console.ReadyBouquetConsoleOutput;
import org.ventura.bouquetproducer.infrastructure.console.ScannerWrapper;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        PrintStream out = new PrintStream(System.out);
        out.print("Please, paste your stream below:\n");

        ReadyBouquetConsoleOutput bouquetReadyListener = new ReadyBouquetConsoleOutput(out);

        List<BouquetSpec> bouquetSpecs = new ArrayList<>();
        BouquetProducer bouquetProducer = new BouquetProducer(bouquetReadyListener, bouquetSpecs);

        BouquetStreamInputReader bouquetStreamInputReader = new BouquetStreamInputReader(new ScannerWrapper());
        bouquetStreamInputReader.read(bouquetSpec -> {
            bouquetSpecs.add(bouquetSpec);
            return null;
        }, flower -> {
            bouquetProducer.addFlower(flower);
            return null;
        });
    }
}
