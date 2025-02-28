package ca.cal.tp2.repository;

import ca.cal.tp2.modele.*;

public interface DocumentRepository {
    void save(Document document);
    long saveDocument(Document document);


}
