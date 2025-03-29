package ca.cal.tp2.service.dto;

import ca.cal.tp2.modele.Cd;
import lombok.Getter;

import java.time.LocalDate;

@Getter

public class CdDTO extends DocumentDTO {
    private String artiste, genre;
    private int duree;


    public CdDTO(Long id, String titre, int nbExemplaires, LocalDate annee, String artiste, String genre, int duree) {
        super(id, titre, nbExemplaires, annee);
        this.artiste = artiste;
        this.genre = genre;
        this.duree = duree;
    }


    public static CdDTO toDTO(Cd cd) {
        return new CdDTO(
                        cd.getId_document(),
                        cd.getTitre(),
                        cd.getNbExemplaires(),
                        cd.getDatePublication(),
                        cd.getArtiste(),
                        cd.getGenre(),
                        cd.getDuree()
                );

    }
}
