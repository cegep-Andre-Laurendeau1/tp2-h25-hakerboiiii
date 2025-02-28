package ca.cal.tp2.modele;

import lombok.Getter;

@Getter
public class Cd extends Document {
    private String artiste,genre;
    private int duree;

    public Cd(String titre, int nbExemplaires, String artiste, int duree, String genre) {
        super(titre, nbExemplaires);
        this.artiste = artiste;
        this.duree = duree;
        this.genre = genre;
    }

    @Override
    public String getSql(){
        return "INSERT INTO Cd VALUES (?,?,?,?)";
    }
}
