package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.modele.Utilisateur;
import ca.cal.tp2.service.dto.UtilisateurDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class UtilisateurRepositoryJPA implements UtilisateurRepository {
    private EntityManagerFactory entiteManagerFactory =
            Persistence.createEntityManagerFactory("libraryH2");

    @Override
    public void save(Utilisateur user) throws DatabaseException {
        try (EntityManager em = entiteManagerFactory.createEntityManager()){
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }

        catch(Exception e){
            throw new DatabaseException(e);
        }
    }

    @Override
    public Utilisateur findUtilisateur(String nom, String email) throws DatabaseException {
        try (EntityManager em = entiteManagerFactory.createEntityManager()){
            TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u" +
                    " WHERE u.email = :email" +
                    " AND u.nom = :nom", Utilisateur.class);
            query.setParameter("email", email);
            query.setParameter("nom", nom);
            return query.getSingleResult();
        } catch(Exception e){
            throw new DatabaseException(e);
        }
    }
}
