package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Cd;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Dvd;
import ca.cal.tp2.modele.Livre;
import ca.cal.tp2.service.dto.DocumentDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PreposeRepositoryJPA implements PreposeRepository {
    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("libraryH2");

    @Override
    public void saveDocument(Document doc) throws DatabaseException {
        try(EntityManager em = entityManagerFactory.createEntityManager()) {
            em.getTransaction().begin();

            if(doc.getId_document() == null){
                em.persist(doc);
            }
            else{
                em.merge(doc);
            }

//            TypedQuery<Document> query = em.createQuery(
//                    "SELECT d FROM Document d WHERE d.titre = :titre", Document.class);
//            query.setParameter("titre", doc.getTitre());
//            List<Document> documentsExistant = query.getResultList();
//
//            if(!documentsExistant.isEmpty()){
//                Document docExistant = documentsExistant.getFirst();
//                docExistant.setNbExemplaires(docExistant.getNbExemplaires() + doc.getNbExemplaires());
//                em.merge(docExistant);
//                System.out.println("Document existant. Mise a jour du nombre d'exemplaires: "
//                        + docExistant.getNbExemplaires());
//            }
//
//            else{
//                em.persist(doc);
//            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Document findDocumentByTitre(String titre) throws DatabaseException{
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            TypedQuery<Document> query = em.createQuery(
                    "SELECT d FROM Document d WHERE d.titre = :titre", Document.class);
            query.setParameter("titre", titre);
            return query.getResultStream().findFirst().orElse(null);
        }

        catch(Exception e){
            throw new DatabaseException(e);
        }
    }

//    @Override
//    public List<DocumentDTO> findDocument(String titre, String auteur,
//                                          Integer annee, String artiste) throws DatabaseException {
//        try(EntityManager em = entityManagerFactory.createEntityManager()){
//            TypedQuery<Document> query = buildQuery(em, titre, auteur, annee, artiste);
//            List<Document> resultats = query.getResultList();
//            return convertToDTO(resultats);
//        }
//        catch(Exception e) {
//            throw new DatabaseException(e);
//        }
//    }

    @Override
    public List<Document> findDocument(String jpql, Map<String, Object> params) throws DatabaseException {
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            TypedQuery<Document> query = em.createQuery(jpql, Document.class);
            params.forEach(query::setParameter);
            return query.getResultList();
        }
        catch(Exception e) {
            throw new DatabaseException(e);
        }
    }

    private TypedQuery<Document> buildQuery(EntityManager em, String titre,
                                            String auteur, Integer annee, String artiste){
        StringBuilder queryString = new StringBuilder("SELECT d FROM Document d WHERE 1=1");

        if(titre != null && !titre.isEmpty())
            queryString.append(" AND LOWER(d.titre) LIKE LOWER(:titre)");
        if(auteur != null && !auteur.isEmpty())
            queryString.append(" AND TYPE (d) = :classType AND LOWER(d.auteur) LIKE LOWER   (:auteur)");
        if(annee != null)
            queryString.append(" AND d.annee = :annee");
        if(artiste != null && !artiste.isEmpty())
            queryString.append(" AND TYPE (d) IN (:cdClass, :dvdClass) AND LOWER(d.artiste) LIKE LOWER(:artiste)");

        TypedQuery<Document> query = em.createQuery(queryString.toString(), Document.class);
        if(titre != null && !titre.isEmpty())
            query.setParameter("titre", "%" + titre + "%");
        if(auteur != null && !auteur.isEmpty()){
            query.setParameter("classType", Livre.class);
            query.setParameter("auteur", "%" + auteur + "%");
        }
        if(annee != null)
            query.setParameter("annee", annee);
        if(artiste != null && !artiste.isEmpty()){
            query.setParameter("cdClass", Cd.class);
            query.setParameter("dvdClass", Dvd.class);
            query.setParameter("artiste", "%" + artiste + "%");

        }
        return query;
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
}
