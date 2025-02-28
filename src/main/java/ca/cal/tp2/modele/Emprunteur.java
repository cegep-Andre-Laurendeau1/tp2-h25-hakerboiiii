package ca.cal.tp1.modele;

public class Emprunteur extends Utilisateur{

    public Emprunteur(String nom, String courriel,String telephone) {
        super(nom,courriel, telephone);
    }


    @Override
    public String getSql(){
        return "INSERT INTO Emprunteur VALUES (?,?)";
    }
}
