package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Entity
@NoArgsConstructor

public class Amende {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;

    @ManyToOne
    @JoinColumn(name = "id_emprunteur", nullable = false)
    private Emprunteur emprunteur;

    @Column(nullable = false)
    private double montant;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @Column(nullable = false)
    private boolean statut;

    public Amende(Emprunteur emp, double montant){
        this.emprunteur = emp;
        this.montant = montant;
        dateCreation = LocalDate.now();
        statut = false;
    }


}
