package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.*;
import ca.cal.tp2.repository.PreposeRepository;
import ca.cal.tp2.service.dto.DocumentDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

        QueryBuilderResult queryBuilderResult = queryBuilder(titre, auteur, annee, artiste);
        List<Document> documents =
                preposeRepository.findDocument(
                        queryBuilderResult.jpql,
                        queryBuilderResult.parameters);

        return convertToDTO(documents);

    }

    private List<DocumentDTO> convertToDTO(List<Document> resultats){
        return resultats.stream()
                .map(doc -> switch (doc) {
                    case Livre livre ->
                            new DocumentDTO(doc.getTitre(), livre.getAuteur(), null, doc.getDatePublication());
                    case Cd cd ->
                            new DocumentDTO(doc.getTitre(), null, cd.getArtiste(), doc.getDatePublication());
                    case Dvd dvd ->
                            new DocumentDTO(doc.getTitre(), null, dvd.getDirector(), doc.getDatePublication());
                    case null, default -> null;
                })
                .collect(Collectors.toList());
    }

    private QueryBuilderResult queryBuilder(String titre, String auteur, Integer annee, String artiste){
        StringBuilder jpql = new StringBuilder("SELECT d FROM Document d WHERE 1=1");
        Map<String, Object> parameters = Map.of();

        if(titre != null && !titre.isEmpty()){
            jpql.append(" AND LOWER(d.titre) LIKE LOWER(:titre)");
            parameters = Map.of("titre", "%" + titre + "%");
        }
        if(auteur != null && !auteur.isEmpty()){
            jpql.append(" AND TYPE (d) = :classType AND LOWER(d.auteur) LIKE LOWER(:auteur)");
            parameters = Map.of("classType", Livre.class, "auteur", "%" + auteur + "%");
        }
        if(annee != null){
            jpql.append(" AND d.annee = :annee");
            parameters = Map.of("annee", annee);
        }
        if(artiste != null && !artiste.isEmpty()) {
            jpql.append(" AND TYPE (d) IN (:cdClass, :dvdClass) AND LOWER(d.artiste) LIKE LOWER(:artiste)");
            parameters = Map.of("cdClass", Cd.class, "dvdClass", Dvd.class, "artiste", "%" + artiste + "%");

        }

        return new QueryBuilderResult(jpql.toString(), parameters);
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
    private record QueryBuilderResult(String jpql, Map<String, Object> parameters) {}
}
