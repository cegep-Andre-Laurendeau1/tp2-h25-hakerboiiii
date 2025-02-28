package ca.cal.tp2.modele;

public class Prepose extends Utilisateur {
    public Prepose(String nom, String courriel, String telephone) {
        super(nom, courriel, telephone);
    }


    @Override
    public String getSql(){
        return "INSERT INTO Prepose VALUES (?,?)";
    }
}
