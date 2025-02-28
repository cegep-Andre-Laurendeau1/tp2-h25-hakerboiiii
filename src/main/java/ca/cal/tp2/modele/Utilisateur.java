package ca.cal.tp1.modele;
import lombok.Data;
import lombok.Setter;

@Data // Lombok annotation
public abstract class Utilisateur {
    @Setter
    private Long user_id;
    private String nom, email, phoneNumber;

    public Utilisateur(String nom, String email, String phoneNumber) {
        this.nom = nom;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public abstract String getSql();
}
