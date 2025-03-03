package ca.cal.tp2.service;

import ca.cal.tp2.modele.Amende;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Emprunt;
import ca.cal.tp2.modele.Emprunteur;
import ca.cal.tp2.repository.AmendeRepositoryJDBC;
import ca.cal.tp2.repository.EmpruntRepositoryJDBC;

public class EmprunteurService {
    private final EmprunteurRepository emprunteurRepository;

    public EmprunteurService(EmprunteurRepository emprunteurRepository) {
        this.emprunteurRepository = emprunteurRepository;

    }

    public void emprunter(Document doc){

    }

    public void retourneDocument(Document doc){

    }
}
