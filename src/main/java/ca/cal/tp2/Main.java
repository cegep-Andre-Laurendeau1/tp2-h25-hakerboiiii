package ca.cal.tp2;
import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.*;
import ca.cal.tp2.repository.*;
import ca.cal.tp2.service.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jdk.jshell.execution.Util;

import javax.xml.crypto.Data;
import java.sql.SQLException;


//todo: Fixer: Pas mettre new Livre dans entreNouveaudocument() La même chose pour les utilisateurs. Utiliser DTO pour passer les parametres;

public class Main {
    public static void main(String[] args) throws InterruptedException, DatabaseException {
        // Votre script qui utilise votre API ici
        UtilisateurService utilisateurService = new UtilisateurService(new UtilisateurRepositoryJPA());
        PreposeService preposeService = new PreposeService(new PreposeRepositoryJPA());
        EmprunteurService emprunteurService = new EmprunteurService(new EmprunteurRepositoryJPA());

//        try{
//            Emprunteur emp = new Emprunteur("toto", "toto@gmail.com", "514-123-4567");
//            Prepose prepose = new Prepose("Bobby", "zmkda22@hotmail", "514-230-1222");
//            utilisateurService.save(emp);
//            utilisateurService.save(prepose);
//        }
//         catch(DatabaseException e){
//            System.out.println("Erreur bd: " + e.getMessage());
//         }

//        try{
//            preposeService.entreNouveauDocument(new Livre("Harry Potter", 12, "sadasdas",
//                    "J.K. Rowling", "LePage", 1997));
//            preposeService.entreNouveauDocument(new Cd("Thriller", 5, "Michael Jackson",
//                    60, "Pop"));
//            preposeService.entreNouveauDocument(new Dvd("Le seigneur des anneaux", 3,
//                    "Peter Jackson", 180, "PG-13"));
//
//
//        }
//        catch(DatabaseException e){
//            System.out.println("Erreur bd: " + e.getMessage());
//        }


        emprunteurService.emprunter(new Livre("Harry Potter", 12, "sadasdas",
                "J.K. Rowling", "LePage", 1997));


        try {
            System.out.println(preposeService.rechercherDocument("Harry Potter"));
        } catch (DatabaseException e) {
            System.out.println("Erreur bd: " + e.getMessage());
        }


        try{
            System.out.println(utilisateurService.findUtilisateur("toto", "toto@gmail.com"));
        }

        catch(DatabaseException e){
            System.out.println("Erreur bd: " + e.getMessage());
        }

        Thread.currentThread().join(); // Keep the program running

    }
}
