package ca.cal.tp2.service.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DocumentDTO{
    private String titre, auteur, artiste;
    private LocalDate annee;

    public DocumentDTO(String titre, String auteur, String artiste, LocalDate annee){
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
