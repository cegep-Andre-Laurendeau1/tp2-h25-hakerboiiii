package ca.cal.tp2.service;

import ca.cal.tp2.modele.Utilisateur;
import ca.cal.tp2.repository.UtilisateurRepositoryJDBC;

public class UtilisateurService {
    public final UtilisateurRepositoryJDBC utilisateurRepositoryJDBC;

    public UtilisateurService(UtilisateurRepositoryJDBC utilisateurRepositoryJDBC) {
        this.utilisateurRepositoryJDBC = utilisateurRepositoryJDBC;
    }

    public void save(Utilisateur user){
        utilisateurRepositoryJDBC.save(user);
    }
}
