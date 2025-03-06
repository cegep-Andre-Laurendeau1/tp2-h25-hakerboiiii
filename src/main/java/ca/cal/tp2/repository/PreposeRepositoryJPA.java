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
import java.util.stream.Collectors;


public class PreposeRepositoryJPA implements PreposeRepository {
    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("libraryH2");

    @Override
    public void saveDocument(Document doc) throws DatabaseException {
        try(EntityManager em = entityManagerFactory.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(doc);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }


    @Override
    public List<DocumentDTO> findDocument(String titre, String auteur, Integer annee, String artiste) throws DatabaseException {
//        try(EntityManager em = entityManagerFactory.createEntityManager()){
//            TypedQuery<Document> query = em.createQuery("SELECT d FROM Document d" +
//                    " WHERE d.titre = :titre", Document.class);
//            query.setParameter("titre", titre);
//            return query.getSingleResult();
//        }
//
//        catch(Exception e) {
//            throw new DatabaseException(e);
//        }


        try(EntityManager em = entityManagerFactory.createEntityManager()){
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
            List<Document> resultats = query.getResultList();

            return resultats.stream()
                    .map(doc -> switch (doc) {
                        case Livre livre ->
                                new DocumentDTO(doc.getTitre(), livre.getAuteur(), null, doc.getAnnee());
                        case Cd cd ->
                                new DocumentDTO(doc.getTitre(), null, cd.getArtiste(), doc.getAnnee());
                        case Dvd dvd ->
                                new DocumentDTO(doc.getTitre(), null, dvd.getDirector(), doc.getAnnee());
                        case null, default -> null;
                    })
                    .collect(Collectors.toList());

        }
        catch(Exception e) {
            throw new DatabaseException(e);
        }
    }

}
