package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Utilisateur;
import ca.cal.tp2.repository.UtilisateurRepository;
import ca.cal.tp2.service.dto.UtilisateurDTO;

public class UtilisateurService {
    //public final UtilisateurRepositoryJDBC utilisateurRepositoryJDBC;
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public void save(Utilisateur user) throws DatabaseException {
        utilisateurRepository.save(user);
    }

    public UtilisateurDTO findUtilisateur(String nom, String email) throws DatabaseException {
        return UtilisateurDTO.toDTO(utilisateurRepository.findUtilisateur(nom, email));
    }
}