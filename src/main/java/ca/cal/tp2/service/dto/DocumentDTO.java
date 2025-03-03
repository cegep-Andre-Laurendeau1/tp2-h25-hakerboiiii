package ca.cal.tp2.service.dto;

import ca.cal.tp2.modele.Document;

public record DocumentDTO(long id, String titre, int nbExemplaires) {
    public static DocumentDTO toDTO(Document doc){
        return new DocumentDTO(doc.getId_document(), doc.getTitre(), doc.getNbExemplaires());
    }
}
