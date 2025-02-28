package ca.cal.tp1.repository;

import ca.cal.tp1.modele.Utilisateur;


public interface UtilisateurRepository {
    void save(Utilisateur utilisateur);
    long saveUtilisateur(Utilisateur utilisateur);
}
