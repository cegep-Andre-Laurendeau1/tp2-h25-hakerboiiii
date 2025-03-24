package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.*;
import ca.cal.tp2.repository.UtilisateurRepository;
import ca.cal.tp2.service.dto.UtilisateurDTO;

public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public void creePrepose(String nom, String courriel, String telephone) throws DatabaseException {
        Prepose prepose = new Prepose(nom, courriel, telephone);
        utilisateurRepository.save(prepose);
    }
    public void creeEmprunteur(String nom, String courriel, String telephone) throws DatabaseException {
        Emprunteur emprunteur = new Emprunteur(nom, courriel, telephone);
        utilisateurRepository.save(emprunteur);
    }

    public UtilisateurDTO findUtilisateur(String nom, String email) throws DatabaseException {
        return UtilisateurDTO.toDTO(utilisateurRepository.findUtilisateur(nom, email));
    }
}