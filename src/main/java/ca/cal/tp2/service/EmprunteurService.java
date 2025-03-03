package ca.cal.tp2.service;

import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Emprunteur;
import ca.cal.tp2.repository.EmprunteurRepository;

public class EmprunteurService {
    private final EmprunteurRepository emprunteurRepository;

    public EmprunteurService(EmprunteurRepository emprunteurRepository) {
        this.emprunteurRepository = emprunteurRepository;
    }

    public void emprunter(Document doc){
        Emprunteur emp = findEmprunteur();
        emprunteurRepository.emprunter(emp, doc);
    }

    public void retourneDocument(Document doc){
        Emprunteur emp = findEmprunteur();
        emprunteurRepository.retourneDocument(emp, doc);
    }

    private Emprunteur findEmprunteur(){
        return new Emprunteur("Alice", "alice@example.com", "514-123-4567");
    }
}
