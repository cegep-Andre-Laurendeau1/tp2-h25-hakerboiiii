package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.exception.DocumentDoesNotExist;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Emprunt;
import ca.cal.tp2.modele.EmpruntDetail;
import ca.cal.tp2.modele.Emprunteur;

import java.util.List;
import java.util.Optional;

public interface EmprunteurRepository {
    void emprunter(Emprunt emprunt) throws DatabaseException;
    void retourneDocument(Emprunteur emp, Document doc) throws DatabaseException;
    List<EmpruntDetail> chercherListeEmprunts(Long id) throws DatabaseException;
    void payerAmende(Emprunteur emp, double montant) throws DatabaseException;
    Optional<Emprunteur> findById(Long id) throws DatabaseException;

}
