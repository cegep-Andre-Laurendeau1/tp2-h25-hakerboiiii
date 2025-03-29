package ca.cal.tp2;
import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.exception.DocumentDoesNotExist;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.repository.EmprunteurRepositoryJPA;
import ca.cal.tp2.repository.PreposeRepositoryJPA;
import ca.cal.tp2.repository.UtilisateurRepositoryJPA;
import ca.cal.tp2.service.EmprunteurService;
import ca.cal.tp2.service.PreposeService;
import ca.cal.tp2.service.UtilisateurService;
import ca.cal.tp2.service.dto.CdDTO;
import ca.cal.tp2.service.dto.DocumentDTO;
import ca.cal.tp2.service.dto.LivreDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

//todo: Pas importer couche modèle dans Main. Utiliser les services.
//todo: Modifier le méthode emprunteur pour qu'un client ajoute plusieurs documents à la fois.
// Donc, changer le paramètre de Document à List<Document>.
//todo: enlever le variable status.
//todo: enlever UtilisateurService. Laisser le prépose ajouter les utilisateurs.

public class MainH2 {
    public static void main(String[] args) throws SQLException, DatabaseException {
        TcpServeur.startTcpServer();
        UtilisateurService utilisateurService = new UtilisateurService(new UtilisateurRepositoryJPA());
        PreposeService preposeService = new PreposeService(new PreposeRepositoryJPA());
        EmprunteurService emprunteurService = new EmprunteurService(
                new EmprunteurRepositoryJPA(),
                new PreposeRepositoryJPA());


        try{
            utilisateurService.creeEmprunteur("thomas", "toto@gmail.com", "514-123-4567");
            utilisateurService.creeEmprunteur("alice", "alice@example.com", "514-123-4567");
            utilisateurService.creePrepose("bob", "bob@gmail.com", "514-222-2990");
            utilisateurService.creePrepose("Bobby", "zmkda22@hotmail", "514-230-1222");


            preposeService.creeLivre("Happy Potter", LocalDate.of(2002, 1, 1), "sadasdas",
                    "J.K. Rowling", "LePage", 1997, 12);
            preposeService.creeLivre("La bete humaine", LocalDate.of(1888, 1, 1), "isbn",
                    "Émile Zola", "France", 500, 4);
            preposeService.creeLivre("Germinal", LocalDate.of(1884, 1, 1), "isbn",
                    "Émile Zola", "France", 500, 1);


            preposeService.creeLivre("Germinal", LocalDate.of(1884, 1, 1), "isbn",
                    "Émile Zola", "France", 500, 1); //est mise a jour. Donc, 2 exemplaires.


            preposeService.creeCd("Thriller", LocalDate.of(1995, 1, 1), 5, "Michael Jackson",
                    60, "Pop");

            preposeService.creeCd("Zombies", LocalDate.of(1995, 1, 1), 5, "Michael Jackson",
                    30, "Pop");
            preposeService.creeDvd("Le seigneur des anneaux", LocalDate.of(2001, 1, 1), 3,
                    "Peter Jackson", 180, "PG-13");



            List<DocumentDTO> documents =
                    preposeService.rechercherDocument(null, "Émile Zola", null, null);
            documents.forEach(System.out::println);

            LivreDTO livre = (LivreDTO) emprunteurService.rechercherDocumentParTitre("Germinal");
            CdDTO cd = (CdDTO) emprunteurService.rechercherDocumentParTitre("Thriller");

            System.out.println("Livre trouvé : " + livre.getTitre());
            System.out.println("Cd trouvé : " + cd.getTitre());



            emprunteurService.emprunter(1L, Arrays.asList(livre.getId(),  cd.getId(), livre.getId()));
            emprunteurService.emprunter(2L, Arrays.asList(livre.getId())); //Alice emprunte Germinal, mais il ne reste plus en stock.

//
//
//            List<EmpruntDetail> emprunts = emprunteurService.retournerListeEmprunts(alice);
//
//            System.out.println("Liste des emprunts de : " + alice.getNom() + "(" + alice.getEmail() + ")");
//
//            if(emprunts.isEmpty()){
//                System.out.println("Aucun empprunt");
//            }
//            else {
//                System.out.println("-----------------------------------------------------");
//                System.out.printf("%-30s | %-12s | %-12s\n", "Titre du document", "Date Emprunt",
//                        "Jour de retour");
//                System.out.println("-----------------------------------------------------");
//                for (EmpruntDetail empruntDetail : emprunts) {
//                    System.out.printf("%-30s | %-12s | %-12s\n",
//                            empruntDetail.getDocument().getTitre(),
//                            empruntDetail.getEmprunt().getDate_emprunt(),
//                            empruntDetail.getDateRetourPrevue());
//                }
//                System.out.println("-----------------------------------------------------");
//            }
//
//            emprunteurService.retourneDocument(alice, livre);
//            emprunteurService.retourneDocument(alice, bete_humaine);
//
//
//

        } catch (DatabaseException | DocumentDoesNotExist e) {
            throw new RuntimeException(e);
        }
    }
}
