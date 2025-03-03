package ca.cal.tp2.repository;

import ca.cal.tp2.modele.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public class EmprunteurRepositoryJPA implements EmprunteurRepository {
    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("orders.pu");

    @Override
    public void emprunter(Emprunteur emprunteur, Document doc) {
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            em.getTransaction().begin();

            TypedQuery<Emprunteur> query = em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email AND TYPE(u) = Emprunteur ", Emprunteur.class);
            query.setParameter("email", emprunteur.getEmail());
            List<Emprunteur> emprunteurs = query.getResultList();

            if(!emprunteurs.isEmpty())
                emprunteur = emprunteurs.get(0);
            else{
                em.persist(emprunteur);
                em.flush();
            }

            if(doc.getNbExemplaires() <= 0) {
                throw new RuntimeException("Il n'y a plus d'exemplaires disponibles");
            }

            Emprunt emprunt = new Emprunt(emprunteur);
            em.persist(emprunt);

            EmpruntDetail empruntDetail = new EmpruntDetail(emprunt, doc);
            em.persist(empruntDetail);

            doc.setNbExemplaires(doc.getNbExemplaires() - 1);
            em.merge(doc);

            em.getTransaction().commit();

            System.out.println("Emprunt effectué avec succès emprunté par " + emprunteur.getNom());
        }

        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void retourneDocument(Emprunteur emprunteur, Document doc) {
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            em.getTransaction().begin();

            TypedQuery<EmpruntDetail> query = em.createQuery(
                    "SELECT ed FROM EmpruntDetail ed WHERE ed.emprunt.emprunteur = :emprunteur" +
                            " AND ed.document = :doc AND ed.status = 'Emprunte'", EmpruntDetail.class);
            query.setParameter("emprunteur", emprunteur);
            query.setParameter("doc", doc);
            EmpruntDetail empruntDetail = query.getSingleResult();

            empruntDetail.setDateRetourActuelle(LocalDate.now());
            empruntDetail.updateStatus();
            em.merge(empruntDetail);

            doc.setNbExemplaires(doc.getNbExemplaires() + 1);
            em.merge(doc);

            if(empruntDetail.isEnRetard()){
                double montant = empruntDetail.calculAmende();
                Amende amende = new Amende(emprunteur, montant);
                em.persist(amende);
                System.out.println("Amende de " + montant + " a été ajoutée à l'emprunteur " + emprunteur.getNom());
            }

            em.getTransaction().commit();
            System.out.println("Retour effectué avec succès par " + emprunteur.getNom());
        }

        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
