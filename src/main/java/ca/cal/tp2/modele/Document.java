package ca.cal.tp2.modele;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_document", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@Table
public abstract class Document {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_document;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private int nbExemplaires;

    @Column()
    private LocalDate datePublication;



    public Document(String titre, int nbExemplaires, LocalDate datePublication) {
        this.titre = titre;
        this.nbExemplaires = nbExemplaires;
        this.datePublication = datePublication;
    }

    public abstract Long nbSemaines();
}
