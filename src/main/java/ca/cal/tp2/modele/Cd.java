package ca.cal.tp2.modele;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("CD")
@NoArgsConstructor
@Getter
public class Cd extends Document {
    private String artiste,genre;
    private int duree;

    public Cd(String titre, int nbExemplaires, LocalDate annee, String artiste, int duree, String genre) {
        super(titre, nbExemplaires, annee);
        this.artiste = artiste;
        this.duree = duree;
        this.genre = genre;
    }



    @Override
    public Long nbSemaines() {
        return 2L;
    }
}
