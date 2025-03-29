package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


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


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(nullable = false)
    private LocalDate dateRetourPrevue;

    @Setter
    private LocalDate dateRetourActuelle;



    public EmpruntDetail(Emprunt emprunt, Document document) {
        this.emprunt = emprunt;
        this.document = document;
        document.nbSemaines();
        this.dateRetourPrevue = LocalDate.now().plusWeeks(document.nbSemaines());
    }

    public boolean isEnRetard(){
        return Objects.requireNonNullElseGet(
                dateRetourActuelle, LocalDate::now).isAfter(dateRetourPrevue);

    }
    public double calculAmende(){
        if(!isEnRetard()) return 0;
        long joursEnRetard = ChronoUnit.DAYS.between(dateRetourPrevue, dateRetourActuelle);
        return joursEnRetard * 0.25;
    }
}
