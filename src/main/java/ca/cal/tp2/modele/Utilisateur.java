package ca.cal.tp2.modele;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_utilisateur", discriminatorType = DiscriminatorType.STRING)
@Data


public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false)
    private String nom;
    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    public Utilisateur(String nom, String email, String phoneNumber) {
        this.nom = nom;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public abstract String getSql();
}
