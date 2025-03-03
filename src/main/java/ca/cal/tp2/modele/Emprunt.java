package ca.cal.tp2.modele;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
public class Emprunt {
@Setter
    private Long id_emprunt;
    private Long id_emprunteur;
    private LocalDate date_emprunt;
    private String statuts;
    private List<EmpruntDetail> empruntDetails;


    public Emprunt(Long id_emprunteur){
        this.id_emprunteur = id_emprunteur;
        this.date_emprunt = LocalDate.now();
        this.statuts = "Active";
        this.empruntDetails = new ArrayList<>();
    }
}
