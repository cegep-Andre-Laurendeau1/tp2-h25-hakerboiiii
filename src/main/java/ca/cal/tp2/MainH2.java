package ca.cal.tp2;
import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.*;
import ca.cal.tp2.repository.EmprunteurRepositoryJPA;
import ca.cal.tp2.repository.PreposeRepositoryJPA;
import ca.cal.tp2.repository.UtilisateurRepositoryJPA;
import ca.cal.tp2.service.EmprunteurService;
import ca.cal.tp2.service.PreposeService;
import ca.cal.tp2.service.UtilisateurService;
import ca.cal.tp2.service.dto.DocumentDTO;

import java.sql.SQLException;
import java.util.List;

public class MainH2 {
    public static void main(String[] args) throws SQLException, DatabaseException {
        TcpServeur.startTcpServer();
        Livre livre = new Livre("Harry Potter", 12, 2002, "sadasdas",
                "J.K. Rowling", "LePage", 1997);
        Livre bete_humaine = new Livre("La bete humaine", 1, 1888, "isbn",
                "Émile Zola", "France", 500);
        Livre germinal = new Livre("Germinal", 1, 1884, "isbn",
                "Émile Zola", "France", 500);
        Livre germinal_duplique = new Livre("Germinal", 4, 1884, "isbn",
                "Émile Zola", "France", 500);

        Livre potter = new Livre("Harry Potter: Kingdom", 12, 2002, "sadasdas",
                "J.K. Rowling", "LePage", 2001);


        Cd cd = new Cd("Thriller", 5, 1995, "Michael Jackson",
                60, "Pop");
        Dvd dvd = new Dvd("Le seigneur des anneaux", 3, 2001,
                "Peter Jackson", 180, "PG-13");
        Cd cd2 = new Cd("Zombies", 2, 1995, "Michael Jackson",
                10, "Pop");

        Emprunteur thomas = new Emprunteur("toto", "toto@gmail.com", "514-123-4567");
        Emprunteur alice = new Emprunteur("Alice", "alice@exaple.com", "514-123-4567");

        Prepose bob = new Prepose("Bob", "bob@gmail.com", "514-123-4567");
        Prepose bobby = new Prepose("Bobby", "zmkda22@hotmail", "514-230-1222");

        UtilisateurService utilisateurService = new UtilisateurService(new UtilisateurRepositoryJPA());
        PreposeService preposeService = new PreposeService(new PreposeRepositoryJPA());
        EmprunteurService emprunteurService = new EmprunteurService(new EmprunteurRepositoryJPA());
        EmprunteurRepositoryJPA tester = new EmprunteurRepositoryJPA(); //tester pour les emprunts retardataires.


        try{
            utilisateurService.save(thomas);
            utilisateurService.save(alice);
            utilisateurService.save(bob);
            utilisateurService.save(bobby);

            preposeService.entreNouveauDocument(livre);
            preposeService.entreNouveauDocument(bete_humaine);
            preposeService.entreNouveauDocument(germinal);
            preposeService.entreNouveauDocument(germinal_duplique); //est mise a jour. Donc, 5 exemplaires.
            preposeService.entreNouveauDocument(potter);
            preposeService.entreNouveauDocument(cd);
            preposeService.entreNouveauDocument(cd2);
            preposeService.entreNouveauDocument(dvd);

            List<DocumentDTO> documents =
                    preposeService.rechercherDocument(null, null, null, "Jackson");
            documents.forEach(System.out::println);

            emprunteurService.emprunter(alice, livre);
            emprunteurService.emprunter(alice, bete_humaine);
            emprunteurService.emprunter(alice, germinal);
            emprunteurService.emprunter(thomas, cd);


            List<EmpruntDetail> emprunts = emprunteurService.retournerListeEmprunts(alice);

            System.out.println("Liste des emprunts de : " + alice.getNom() + "(" + alice.getEmail() + ")");

            if(emprunts.isEmpty()){
                System.out.println("Aucun empprunt");
            }
            else {
                System.out.println("-----------------------------------------------------");
                System.out.printf("%-30s | %-12s | %-12s\n", "Titre du document", "Date Emprunt",
                        "Jour de retour");
                System.out.println("-----------------------------------------------------");
                for (EmpruntDetail empruntDetail : emprunts) {
                    System.out.printf("%-30s | %-12s | %-12s\n",
                            empruntDetail.getDocument().getTitre(),
                            empruntDetail.getEmprunt().getDate_emprunt(),
                            empruntDetail.getDateRetourPrevue());
                }
                System.out.println("-----------------------------------------------------");
            }
            //emprunteurService.emprunter(thomas, bete_humaine); //Erreur: Aucun d'exemplaires disponibles.

            emprunteurService.retourneDocument(alice, livre);
            emprunteurService.retourneDocument(alice, bete_humaine);
            //emprunteurService.retourneDocument(thomas, dvd); //Erreur: document non emprunte.




            //Tester pour les emprunts retardataires.
            //Il faut décommenter les lignes suivantes pour tester les emprunts retardataires
//            tester.testRetounerEnRetard();
//            emprunteurService.retourneDocument(thomas, cd);
//            emprunteurService.payerAmende(thomas, 8.0); //Attention: L'amende peut varier selon le temps.



        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
}
