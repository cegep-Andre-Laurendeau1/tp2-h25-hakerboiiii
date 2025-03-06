package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.repository.PreposeRepository;
import ca.cal.tp2.service.dto.DocumentDTO;

import java.util.List;
import java.util.stream.Collectors;


public class PreposeService {

    private final PreposeRepository preposeRepository;

    public PreposeService(PreposeRepository preposerepository) {
        this.preposeRepository = preposerepository;
    }
    public void entreNouveauDocument(Document document) throws DatabaseException {
        preposeRepository.saveDocument(document);
    }


    public List<DocumentDTO> rechercherDocument(String titre, String auteur, Integer annee, String artiste) throws DatabaseException{
        return preposeRepository.findDocument(titre, auteur, annee, artiste);
    }
}
