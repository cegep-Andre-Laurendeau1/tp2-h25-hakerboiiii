package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.exception.DocumentDoesNotExist;
import ca.cal.tp2.exception.DocumentNotAvailable;
import ca.cal.tp2.exception.EmprunteurDoesNotExists;
import ca.cal.tp2.modele.*;
import ca.cal.tp2.repository.EmprunteurRepository;
import ca.cal.tp2.repository.PreposeRepository;
import ca.cal.tp2.service.dto.CdDTO;
import ca.cal.tp2.service.dto.DocumentDTO;
import ca.cal.tp2.service.dto.DvdDTO;
import ca.cal.tp2.service.dto.LivreDTO;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmprunteurService {
    private final EmprunteurRepository emprunteurRepository;
    private final PreposeRepository preposeRepository;

    public EmprunteurService(EmprunteurRepository emprunteurRepository, PreposeRepository preposeRepository) {
        this.emprunteurRepository = emprunteurRepository;
        this.preposeRepository = preposeRepository;
    }


    public DocumentDTO rechercherDocumentParTitre(String titre) throws DocumentDoesNotExist {
        Document doc = preposeRepository.findDocumentByTitre(titre);

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


    @Transactional
    public void emprunter(Long emprunteurId, List<Long> docsId)
            throws EmprunteurDoesNotExists,
            DocumentDoesNotExist,
            DocumentNotAvailable, DatabaseException {

        Emprunteur emp = emprunteurRepository.findById(emprunteurId)
                .orElseThrow(() -> new EmprunteurDoesNotExists("L'emprunteur du id: " + emprunteurId + " n'existe pas"));

        Map<Long, Long> docIdCounts = docsId.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        for(Map.Entry<Long, Long> entres: docIdCounts.entrySet()){
            Long docId = entres.getKey();
            Long repetition = entres.getValue();

            Document document = preposeRepository.findDocumentById(docId)
                    .orElseThrow(() -> new DocumentDoesNotExist("Le document du id: " + docId + " n'existe pas"));

            if(document.getNbExemplaires() < repetition){
                throw new DocumentNotAvailable("Le document du id: " + docId + " n'est plus disponible");
            }
        }

        for(Map.Entry<Long, Long> entres: docIdCounts.entrySet()){
            Long docId = entres.getKey();
            Long repetition = entres.getValue();

            Document document = preposeRepository.findDocumentById(docId)
                    .orElseThrow(() -> new DocumentDoesNotExist("Le document du id: " + docId + " n'existe pas"));
            document.setNbExemplaires((int) (document.getNbExemplaires() -  repetition));
            preposeRepository.save(document);
        }

        Emprunt emprunt = new Emprunt(emp);

        for(Long docId: docsId){
            Document doc = preposeRepository.findDocumentById(docId)
                    .orElseThrow(() -> new DocumentDoesNotExist("Le document du id: " + docId + " n'existe pas"));

            EmpruntDetail empruntDetail = new EmpruntDetail(emprunt, doc);
            emprunt.getEmpruntDetails().add(empruntDetail);
        }

        emprunteurRepository.emprunter(emprunt);


//        List<Document> listeDocs = new ArrayList<>();
//
//        for(Long docId: docsId){
//            Document doc = preposeRepository.findDocumentById(docId)
//                    .orElseThrow(() -> new DocumentDoesNotExist("Le document du id: " + docId + " n'existe pas"));
//
//            if(doc.getNbExemplaires() <= 0){
//                throw new DocumentNotAvailable("Le document du id: " + docId + " n'est pas disponible");
//            }
//
//            listeDocs.add(doc);
//        }
//
//        for (Document doc: listeDocs){
//            doc.setNbExemplaires(doc.getNbExemplaires() - 1);
//            preposeRepository.save(doc);
//        }
//
//        Emprunt emprunt = new Emprunt(emp);
//
//        for(Document doc: listeDocs){
//            EmpruntDetail empruntDetail = new EmpruntDetail(emprunt, doc);
//            emprunt.getEmpruntDetails().add(empruntDetail);
//        }
//        emprunteurRepository.emprunter(emprunt);
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
