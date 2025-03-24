package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.*;
import ca.cal.tp2.repository.PreposeRepository;
import ca.cal.tp2.service.dto.DocumentDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class PreposeService {

    private final PreposeRepository preposeRepository;

    public PreposeService(PreposeRepository preposerepository) {
        this.preposeRepository = preposerepository;
    }

    public void creeLivre(String titre, LocalDate datePublication,String  isbn, String auteur, String editeur,
                            int nbPages, int nbExemplaires) throws DatabaseException{
        documentCree(titre, nbExemplaires, () ->
                new Livre(titre, nbExemplaires, datePublication, isbn, auteur, editeur, nbPages));
    }

    public void creeDvd(String titre, LocalDate datePublication, int nbExamplaires, String director, int duration, String rating) throws DatabaseException{
        documentCree(titre, nbExamplaires, () ->
                new Dvd(titre, nbExamplaires, datePublication, director, duration, rating));
    }

    public void creeCd(String titre, LocalDate datePublication, int nbExamplaires, String artiste, int duree, String genre) throws DatabaseException{
        documentCree(titre, nbExamplaires, () ->
                new Cd(titre, nbExamplaires, datePublication, artiste, duree, genre));
    }

    public List<DocumentDTO> rechercherDocument(String titre, String auteur, Integer annee, String artiste) throws DatabaseException{
        return preposeRepository.findDocument(titre, auteur, annee, artiste);
    }

    private void documentCree(String titre, int nbExemplaires, Supplier<Document> documentSupplier)
            throws DatabaseException{
        Document docExistant = preposeRepository.findDocumentByTitre(titre);
        if(docExistant != null){
            docExistant.setNbExemplaires(docExistant.getNbExemplaires() + nbExemplaires);
            preposeRepository.saveDocument(docExistant);
        }
        else{
            Document document = documentSupplier.get();
            preposeRepository.saveDocument(document);
        }
    }

}
