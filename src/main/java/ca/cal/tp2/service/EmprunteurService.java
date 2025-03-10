package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.EmpruntDetail;
import ca.cal.tp2.modele.Emprunteur;
import ca.cal.tp2.repository.EmprunteurRepository;

import java.util.List;

public class EmprunteurService {
    private final EmprunteurRepository emprunteurRepository;

    public EmprunteurService(EmprunteurRepository emprunteurRepository) {
        this.emprunteurRepository = emprunteurRepository;
    }

    public void emprunter(Emprunteur emp, Document doc) throws DatabaseException {
        emprunteurRepository.emprunter(emp, doc);
    }
    public void retourneDocument(Emprunteur emp, Document doc) throws DatabaseException {

        emprunteurRepository.retourneDocument(emp, doc);
    }

    public List<EmpruntDetail> retournerListeEmprunts(Emprunteur emp){
        return emprunteurRepository.chercherListeEmprunts(emp);
    }

    public void payerAmende(Emprunteur emp, Double montant) throws DatabaseException {
        emprunteurRepository.payerAmende(emp, montant);
    }



}
