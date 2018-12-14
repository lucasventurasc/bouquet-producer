package org.ventura.bouquetproducer.application;

import org.ventura.bouquetproducer.domain.BouquetSpec;

public interface BouquetReadyListener {

    void notify(BouquetSpec bouquetSpec);
}
