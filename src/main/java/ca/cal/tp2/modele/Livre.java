package ca.cal.tp2.modele;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("LIVRE")
@NoArgsConstructor
@Getter
public class Livre extends Document{
    private String isbn;
    private String auteur, editeur;
    private int nbPages;

    public Livre(String titre, int nbExemplaires, LocalDate annee, String isbn, String auteur, String editeur, int nbPages) {
        super(titre, nbExemplaires, annee);
        this.isbn = isbn;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nbPages = nbPages;
    }

    @Override
    public Long nbSemaines() {
        return 3L;
    }


}
