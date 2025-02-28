package ca.cal.tp1.modele;

import lombok.Data;
import lombok.Setter;

import java.util.Date;


@Data
public class Amende {
    @Setter
    private Long fineId;
    private int id_emprunteur;
    private double montant;
    private Date dateCreation;
    private boolean statut;

    public Amende(int id_emprunteur, double montant){
        this.id_emprunteur = id_emprunteur;
        this.montant = montant;
        dateCreation = new Date();
        statut = false;
    }

    public void calculMontant(int nb_jours_retard){
        this.montant = nb_jours_retard * 0.25;
    }


    public void updateStatus(){
        this.statut = true;
    }





}
