package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class EmpruntDetail {
@Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineItemId;

    @ManyToOne
    @JoinColumn(name = "emprunt_id", nullable = false)
    private Emprunt emprunt;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

//    private LocalDate dateRetourPrevue;
//    private LocalDate dateRetourActuelle;
    @Column(nullable = false)
    private String status;

    public EmpruntDetail(Emprunt emprunt, Document document, Long nb_semaines) {
        this.emprunt = emprunt;
        this.document = document;
        //this.dateRetourPrevue = LocalDate.now().plusWeeks(nb_semaines);
        this.status = "Emprunte";
    }
}
