package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;

import ca.cal.tp2.exception.DocumentDoesNotExist;
import ca.cal.tp2.modele.Document;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PreposeRepository {
    void save(Document doc) throws DatabaseException;
//    List<DocumentDTO> findDocument(String titre, String auteur, Integer annee, String artiste) throws DatabaseException;
    List<Document> findDocument(String jpql, Map<String, Object> params) throws DatabaseException;
    Document findDocumentByTitre(String titre) throws DocumentDoesNotExist;
    Optional<Document> findDocumentById(Long id) throws DatabaseException;

}
