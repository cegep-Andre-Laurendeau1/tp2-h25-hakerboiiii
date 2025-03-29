package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.exception.DocumentDoesNotExist;
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
import java.util.Optional;
import java.util.stream.Collectors;


public class PreposeRepositoryJPA implements PreposeRepository {
    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("libraryH2");

    @Override
    public void save(Document doc) throws DatabaseException {
        try(EntityManager em = entityManagerFactory.createEntityManager()) {
            em.getTransaction().begin();

            if(doc.getId_document() == null){
                em.persist(doc);
            }
            else{
                em.merge(doc);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Document findDocumentByTitre(String titre) throws DocumentDoesNotExist {
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            TypedQuery<Document> query = em.createQuery(
                    "SELECT d FROM Document d WHERE d.titre = :titre", Document.class);
            query.setParameter("titre", titre);

            return query.getResultStream().findFirst().orElse(null);
        }

        catch (Exception e){
            throw new DocumentDoesNotExist("Le document du titre : " + titre + " n'existe pas");
        }
    }

    @Override
    public Optional<Document> findDocumentById(Long id) throws DatabaseException {
        if(id == null){
            return Optional.empty();
        }

        try(EntityManager em = entityManagerFactory.createEntityManager()){
            Document doc = em.find(Document.class, id);
            return Optional.ofNullable(doc);
        }
        catch(Exception e){
            throw new DatabaseException(e);
        }
    }

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
}
