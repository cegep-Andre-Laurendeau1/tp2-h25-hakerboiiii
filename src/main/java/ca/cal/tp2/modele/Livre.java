package ca.cal.tp2.modele;

import lombok.Getter;

@Getter
public class Livre extends Document{
    private String isbn;
    private String auteur, editeur;
    private int nbPages;

    public Livre(String titre, int nbExemplaires, String isbn, String auteur, String editeur, int nbPages) {
        super(titre, nbExemplaires);
        this.isbn = isbn;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nbPages = nbPages;
    }

    @Override
    public String getSql() {
        return "INSERT INTO Livre (id_livre, isbn, auteur, editeur, " +
                "nbPages) VALUES (?, ?, ?, ?, ?)";
    }
}
