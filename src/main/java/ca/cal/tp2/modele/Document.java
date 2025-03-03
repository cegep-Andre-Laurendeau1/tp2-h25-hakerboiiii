package ca.cal.tp2.modele;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_document", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@Table
public abstract class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_document;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private int nbExemplaires;



    public Document(String titre, int nbExemplaires) {
        this.titre = titre;
        this.nbExemplaires = nbExemplaires;
    }
    public abstract String getSql();
}
