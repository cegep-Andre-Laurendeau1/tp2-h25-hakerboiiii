package ca.cal.tp2.repository;

import ca.cal.tp2.modele.Utilisateur;


public interface UtilisateurRepository {
    void save(Utilisateur utilisateur);
    long saveUtilisateur(Utilisateur utilisateur);
}
