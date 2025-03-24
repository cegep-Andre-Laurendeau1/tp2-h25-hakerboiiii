package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;

import ca.cal.tp2.modele.Document;
import ca.cal.tp2.service.dto.DocumentDTO;

import java.util.List;
import java.util.Map;

public interface PreposeRepository {
    void saveDocument(Document doc) throws DatabaseException;
//    List<DocumentDTO> findDocument(String titre, String auteur, Integer annee, String artiste) throws DatabaseException;
    List<Document> findDocument(String jpql, Map<String, Object> params) throws DatabaseException;
    Document findDocumentByTitre(String titre) throws DatabaseException;
}
