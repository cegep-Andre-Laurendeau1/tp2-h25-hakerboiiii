package ca.cal.tp2.modele;


import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("EMPRUNTEUR")
@NoArgsConstructor
public class Emprunteur extends Utilisateur{

    @OneToMany(mappedBy = "emprunteur", cascade = CascadeType.ALL)
    private List<Amende> amendes;

    public Emprunteur(String nom, String courriel,String telephone) {
        super(nom,courriel, telephone);
    }


    @Override
    public String getSql(){
        return "INSERT INTO Emprunteur VALUES (?,?)";
    }

    public List<Amende> getAmendes() {
        return amendes;
    }


}
