package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;

import ca.cal.tp2.modele.Document;

public interface PreposeRepository {
    void saveDocument(Document doc) throws DatabaseException;
    Document findDocument(String titre) throws DatabaseException;
}
