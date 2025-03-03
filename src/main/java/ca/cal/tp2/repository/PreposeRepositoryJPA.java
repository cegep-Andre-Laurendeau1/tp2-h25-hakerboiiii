package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Document;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;


public class PreposeRepositoryJPA implements PreposeRepository {
    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("orders.pu");

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
    public Document findDocument(String titre) throws DatabaseException {
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            TypedQuery<Document> query = em.createQuery("SELECT d FROM Document d" +
                    " WHERE d.titre = :titre", Document.class);
            query.setParameter("titre", titre);
            return query.getSingleResult();
        }

        catch(Exception e) {
            throw new DatabaseException(e);
        }
    }
}
