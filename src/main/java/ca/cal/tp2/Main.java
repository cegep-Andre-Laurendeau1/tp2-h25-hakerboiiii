package ca.cal.tp2;
import ca.cal.tp2.modele.*;
import ca.cal.tp2.repository.*;
import ca.cal.tp2.service.*;
import java.sql.SQLException;


//todo: Fixer: Pas mettre new Livre dans entreNouveaudocument() La même chose pour les utilisateurs. Utiliser DTO pour passer les parametres;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Votre script qui utilise votre API ici
        TcpServeur.startTcpServer();
        PreposeService service_doc = new PreposeService(new DocumentRepositoryJDBC());
        UtilisateurService service_user = new UtilisateurService(new UtilisateurRepositoryJDBC());
        EmprunteurService service_emprunteur = new EmprunteurService(
                new AmendeRepositoryJDBC(),
                new EmpruntRepositoryJDBC());


        service_doc.entreNouveauDocument(new Livre("La bete humaine", 8, "isbn", "Émile Zola", "LePage", 500));
        service_doc.entreNouveauDocument(new Livre ("1984", 12, "isbn", "George Orwell", "LePage", 300));

        service_doc.entreNouveauDocument(new Cd("Thriller", 5, "Michael Jackson", 60, "Pop"));
        service_doc.entreNouveauDocument(new Dvd("Le seigneur des anneaux", 3, "Peter Jackson", 180, "PG-13"));


        service_user.save(new Emprunteur("Mohamed", "shahed@gmail.com", "514-706-3301"));
        service_user.save(new Emprunteur("Jean", "jean@gmail.com", "514-199-1000"));

        service_user.save(new Prepose("Thomas", "hotmail@gmail.com", "514-123-4567"));
        service_user.save(new Prepose("Karim", "claurendeau.qc.ca", "514-322-1902"));

        service_emprunteur.saveAmende(1L, 134.43);
        service_emprunteur.saveEmprunt(2L);
    }
}
