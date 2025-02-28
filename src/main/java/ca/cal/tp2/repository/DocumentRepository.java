package ca.cal.tp1.repository;

import ca.cal.tp1.modele.*;

public interface DocumentRepository {
    void save(Document document);
    long saveDocument(Document document);


}
