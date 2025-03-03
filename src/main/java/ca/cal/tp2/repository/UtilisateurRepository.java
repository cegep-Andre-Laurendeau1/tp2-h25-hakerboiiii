package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Utilisateur;


public interface UtilisateurRepository {
    void save(Utilisateur utilisateur) throws DatabaseException;
    Utilisateur findUtilisateur(String nom, String email) throws DatabaseException;
}
