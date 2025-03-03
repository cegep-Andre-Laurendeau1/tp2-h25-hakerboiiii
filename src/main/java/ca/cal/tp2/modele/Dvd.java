package ca.cal.tp2.modele;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DVD")
@NoArgsConstructor
@Getter
public class Dvd extends Document {
    private String director, rating;
    private int duration;

    public Dvd(String titre, int nbExemplaires, String director, int duration, String rating) {
        super(titre, nbExemplaires);
        this.director = director;
        this.duration = duration;
        this.rating = rating;
    }


    @Override
    public String getSql() {
        return "INSERT INTO Dvd VALUES (?,?,?,?)";
    }
}
