package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.repository.PreposeRepository;
import ca.cal.tp2.service.dto.DocumentDTO;


public class PreposeService {

    private final PreposeRepository preposeRepository;

    public PreposeService(PreposeRepository preposerepository) {
        this.preposeRepository = preposerepository;
    }
    public void entreNouveauDocument(Document document) throws DatabaseException {
        preposeRepository.saveDocument(document);
    }

    public DocumentDTO rechercherDocument(String titre) throws DatabaseException{
        return DocumentDTO.toDTO(preposeRepository.findDocument(titre));
    }
}
