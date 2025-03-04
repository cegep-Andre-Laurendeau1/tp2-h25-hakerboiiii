package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Emprunteur;
import ca.cal.tp2.repository.EmprunteurRepository;

public class EmprunteurService {
    private final EmprunteurRepository emprunteurRepository;

    public EmprunteurService(EmprunteurRepository emprunteurRepository) {
        this.emprunteurRepository = emprunteurRepository;
    }

    public void emprunter(Emprunteur emp, Document doc) throws DatabaseException {
        emprunteurRepository.emprunter(emp, doc);
    }

    public void retourneDocument(Emprunteur emp, Document doc){

        emprunteurRepository.retourneDocument(emp, doc);
    }
}
