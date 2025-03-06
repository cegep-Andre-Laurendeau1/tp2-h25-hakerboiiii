package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class Emprunt {
@Setter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_emprunt;

    @ManyToOne
    @JoinColumn(name = "id_emprunteur", nullable = false)
    private Emprunteur emprunteur;


    private LocalDate date_emprunt;
    private String statuts;

    @OneToMany(mappedBy = "emprunt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpruntDetail> empruntDetails = new ArrayList<>();


    public Emprunt(Emprunteur emprunteur){
        this.emprunteur = emprunteur;
        this.date_emprunt = LocalDate.now();
        this.statuts = "Active";

    }


}
