package ca.cal.tp1.service;

import ca.cal.tp1.modele.Document;
import ca.cal.tp1.repository.DocumentRepositoryJDBC;


public class PreposeService {
    private final DocumentRepositoryJDBC documentRepositoryJDBC;

    public PreposeService(DocumentRepositoryJDBC documentRepositoryJDBC) {
        this.documentRepositoryJDBC = documentRepositoryJDBC;
    }
    public void entreNouveauDocument(Document document){
        documentRepositoryJDBC.save(document);
    }
}
