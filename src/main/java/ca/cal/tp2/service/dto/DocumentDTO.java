package ca.cal.tp2.service.dto;

import ca.cal.tp2.modele.Document;
import lombok.Getter;

//public record DocumentDTO(long id, String titre, int nbExemplaires) {
//    public static DocumentDTO toDTO(Document doc){
//        return new DocumentDTO(doc.getId_document(), doc.getTitre(), doc.getNbExemplaires());
//    }
//}

public class DocumentDTO{
    @Getter
    private String titre, auteur, artiste;
    @Getter
    private int annee;

    public DocumentDTO(String titre, String auteur, String artiste, int annee){
        this.titre = titre;
        this.auteur = auteur;
        this.artiste = artiste;
        this.annee = annee;
    }

    @Override
    public String toString(){
        return "DocumentDTO{" +
                "titre = '" + titre + '\'' +
                ", auteur = '" + (auteur != null ? auteur: "N/A") + '\'' +
                ", artiste = '" + (artiste != null ? artiste: "N/A") + '\'' +
                ", annee = " + annee +
                '}';
    }

}
