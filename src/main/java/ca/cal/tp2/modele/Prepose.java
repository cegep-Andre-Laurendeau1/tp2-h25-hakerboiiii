package ca.cal.tp2.modele;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PREPOSE")
@NoArgsConstructor
public class Prepose extends Utilisateur {
    public Prepose(String nom, String courriel, String telephone) {
        super(nom, courriel, telephone);
    }


    @Override
    public String getSql(){
        return "INSERT INTO Prepose VALUES (?,?)";
    }
}
