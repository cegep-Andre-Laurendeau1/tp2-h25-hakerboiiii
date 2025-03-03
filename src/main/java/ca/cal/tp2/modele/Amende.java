package ca.cal.tp2.modele;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;


@Data
public class Amende {
    @Setter
    private Long fineId;
    private Long id_emprunteur;
    private double montant;
    private LocalDate dateCreation;
    private boolean statut;

    public Amende(Long id_emprunteur, double montant){
        this.id_emprunteur = id_emprunteur;
        this.montant = montant;
        dateCreation = LocalDate.now();
        statut = false;
    }

    public void calculMontant(int nb_jours_retard){
        this.montant = nb_jours_retard * 0.25;
    }


    public void updateStatus(){
        this.statut = true;
    }





}
