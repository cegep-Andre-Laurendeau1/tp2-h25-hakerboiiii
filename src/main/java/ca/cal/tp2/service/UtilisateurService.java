package ca.cal.tp1.service;

import ca.cal.tp1.modele.Amende;
import ca.cal.tp1.modele.Utilisateur;
import ca.cal.tp1.repository.AmendeRepositoryJDBC;
import ca.cal.tp1.repository.UtilisateurRepositoryJDBC;

public class UtilisateurService {
    public final UtilisateurRepositoryJDBC utilisateurRepositoryJDBC;

    public UtilisateurService(UtilisateurRepositoryJDBC utilisateurRepositoryJDBC) {
        this.utilisateurRepositoryJDBC = utilisateurRepositoryJDBC;
    }

    public void save(Utilisateur user){
        utilisateurRepositoryJDBC.save(user);
    }
}
