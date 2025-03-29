package ca.cal.tp2.service.dto;

import ca.cal.tp2.modele.Livre;
import lombok.Getter;

import java.time.LocalDate;


@Getter

public class LivreDTO extends DocumentDTO {
    private String isbn;
    private String auteur, editeur;
    private int nbPages;

    public LivreDTO(Long id, String titre, int nbExemplaires, LocalDate annee, String isbn, String auteur, String editeur, int nbPages) {
        super(id, titre, nbExemplaires, annee);
        this.isbn = isbn;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nbPages = nbPages;
    }

    public static LivreDTO toDTO(Livre livre) {
        return new LivreDTO(livre.getId_document(), livre.getTitre(), livre.getNbExemplaires(), livre.getDatePublication(),
                livre.getIsbn(), livre.getAuteur(), livre.getEditeur(), livre.getNbPages());
    }
}
