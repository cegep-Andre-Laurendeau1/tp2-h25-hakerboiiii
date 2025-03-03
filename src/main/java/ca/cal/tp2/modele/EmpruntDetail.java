package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@NoArgsConstructor
public class EmpruntDetail {
@Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineItemId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "emprunt_id", nullable = false)
    private Emprunt emprunt;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(nullable = false)
    private LocalDate dateRetourPrevue;

    private LocalDate dateRetourActuelle;


    @Column(nullable = false)
    private String status;

    public EmpruntDetail(Emprunt emprunt, Document document) {
        this.emprunt = emprunt;
        this.document = document;
        document.nbSemaines();
        this.dateRetourPrevue = LocalDate.now().plusWeeks(document.nbSemaines());
        this.status = "Emprunte";
    }

    public void setDateRetourActuelle (LocalDate dateRetourActuelle) {
        this.dateRetourActuelle = dateRetourActuelle;
        updateStatus();
    }

    public boolean isEnRetard(){
        if (dateRetourActuelle == null){
            return LocalDate.now().isAfter(dateRetourPrevue);
        }

        return dateRetourActuelle.isAfter(dateRetourPrevue);
    }

    public void updateStatus(){
        if(dateRetourActuelle == null){
            if(isEnRetard()){
                this.status = "retard";
            }
            else {
                this.status = "emprunte";
            }
        }
        else {
            this.status = "retourne";
        }
    }

    public double calculAmende(){
        if(!isEnRetard()) return 0;
        long joursEnRetard = ChronoUnit.DAYS.between(dateRetourPrevue, dateRetourActuelle);
        return joursEnRetard * 0.25;
    }
}
