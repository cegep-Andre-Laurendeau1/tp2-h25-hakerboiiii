package ca.cal.tp2.service.dto;

import lombok.Getter;

@Getter
public class DocumentDTO{
    private String titre, auteur, artiste;
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
