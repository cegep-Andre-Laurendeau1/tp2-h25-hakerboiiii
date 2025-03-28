package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.exception.DocumentDoesNotExist;
import ca.cal.tp2.exception.EmprunteurDoesNotExists;
import ca.cal.tp2.modele.*;
import ca.cal.tp2.repository.EmprunteurRepository;
import ca.cal.tp2.service.dto.CdDTO;
import ca.cal.tp2.service.dto.DocumentDTO;
import ca.cal.tp2.service.dto.DvdDTO;
import ca.cal.tp2.service.dto.LivreDTO;

import java.util.List;

public class EmprunteurService {
    private final EmprunteurRepository emprunteurRepository;

    public EmprunteurService(EmprunteurRepository emprunteurRepository) {
        this.emprunteurRepository = emprunteurRepository;
    }


    public DocumentDTO rechercherDocumentParTitre(String titre) throws DocumentDoesNotExist {
        Document doc = emprunteurRepository.findDocumentByTitre(titre);

        if(doc == null){
            throw new DocumentDoesNotExist("Document does not exist");
        }
        return switch (doc) {
            case Livre livre -> LivreDTO.toDTO(livre);
            case Dvd dvd -> DvdDTO.toDTO(dvd);
            case Cd cd -> CdDTO.toDTO(cd);
            default -> null;
        };
    }

    public void emprunter(Long emprunteurId, List<Long> docsId) throws EmprunteurDoesNotExists {

    }
    public void retourneDocument(Emprunteur emp, Document doc) throws DatabaseException {

        emprunteurRepository.retourneDocument(emp, doc);
    }

    public List<EmpruntDetail> retournerListeEmprunts(Emprunteur emp){
        return emprunteurRepository.chercherListeEmprunts(emp);
    }

    public void payerAmende(Emprunteur emp, Double montant) throws DatabaseException {
        emprunteurRepository.payerAmende(emp, montant);
    }



}
