package ca.cal.tp2.repository;

import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Emprunteur;

public interface EmprunteurRepository {
    void emprunter(Emprunteur emp, Document doc);
    void retourneDocument(Emprunteur emp, Document doc);
}
