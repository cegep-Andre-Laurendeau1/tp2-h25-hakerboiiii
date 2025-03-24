package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;

import ca.cal.tp2.modele.Document;
import ca.cal.tp2.service.dto.DocumentDTO;

import java.util.List;

public interface PreposeRepository {
    void saveDocument(Document doc) throws DatabaseException;
    List<DocumentDTO> findDocument(String titre, String auteur, Integer annee, String artiste) throws DatabaseException;
    Document findDocumentByTitre(String titre) throws DatabaseException;
}
