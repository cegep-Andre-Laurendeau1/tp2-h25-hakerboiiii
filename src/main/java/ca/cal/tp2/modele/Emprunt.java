package ca.cal.tp2.modele;

import lombok.Data;
import lombok.Setter;

import java.util.Date;


@Data
public class Emprunt {
@Setter
    private int id_emprunt;
    private int id_emprunteur;
    private Date date_emprunt;
    private String statuts;


    public Emprunt(int id_emprunteur){
        this.id_emprunteur = id_emprunteur;
        this.date_emprunt = new Date();
        this.statuts = "Active";
    }
}
