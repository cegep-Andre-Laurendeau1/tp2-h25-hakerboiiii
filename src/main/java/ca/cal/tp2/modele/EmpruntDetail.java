package ca.cal.tp2.modele;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class EmpruntDetail {
@Setter
    private Long lineItemId;
    private Long emprunt_id;
    private Long document_id;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourActuelle;
    private String status;

    public EmpruntDetail(Long emprunt_id, Long document_id, Long nb_semaines) {
        this.emprunt_id = emprunt_id;
        this.document_id = document_id;
        this.dateRetourPrevue = LocalDate.now().plusWeeks(nb_semaines);
        this.status = "Emprunte";
    }
}
