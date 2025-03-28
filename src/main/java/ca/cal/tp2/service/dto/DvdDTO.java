package ca.cal.tp2.service.dto;

import ca.cal.tp2.modele.Dvd;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class DvdDTO extends DocumentDTO  {

    private String director, rating;
    private int duration;

    public DvdDTO(String titre, int nbExemplaires, LocalDate annee, String director, int duration, String rating) {
        super(titre, nbExemplaires, annee);
        this.director = director;
        this.duration = duration;
        this.rating = rating;
    }

    public static DvdDTO toDTO(Dvd dvd) {
        return new DvdDTO(dvd.getTitre(), dvd.getNbExemplaires(),
                dvd.getDatePublication(), dvd.getDirector(), dvd.getDuration(), dvd.getRating());
    }

}
