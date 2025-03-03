package ca.cal.tp2.service.dto;

import ca.cal.tp2.modele.Utilisateur;

public record UtilisateurDTO(long id, String nom, String email,
                             String numeroTelephone) {

    public static UtilisateurDTO toDTO(Utilisateur user){
        return new UtilisateurDTO(user.getUser_id(), user.getNom(), user.getEmail(), user.getPhoneNumber());
    }


}
