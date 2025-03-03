package ca.cal.tp2.service;

import ca.cal.tp2.modele.Amende;
import ca.cal.tp2.modele.Emprunt;
import ca.cal.tp2.modele.Emprunteur;
import ca.cal.tp2.repository.AmendeRepositoryJDBC;
import ca.cal.tp2.repository.EmpruntRepositoryJDBC;

public class EmprunteurService {
    private final AmendeRepositoryJDBC amendeRepositoryJDBC;
    private final EmpruntRepositoryJDBC empruntRepositoryJDBC;

    public EmprunteurService(AmendeRepositoryJDBC amendeRepositoryJDBC, EmpruntRepositoryJDBC empruntRepositoryJDBC) {
        this.amendeRepositoryJDBC = amendeRepositoryJDBC;
        this.empruntRepositoryJDBC = empruntRepositoryJDBC;
    }

    public void saveAmende(Emprunteur emp, double montant){
        Amende montantAmende = new Amende(emp, montant);
        amendeRepositoryJDBC.save(montantAmende);
    }

    public void saveEmprunt(Emprunteur emprunteur){
        Emprunt emprunt = new Emprunt(emprunteur);
        empruntRepositoryJDBC.saveEmprunt(emprunt);
    }
}
