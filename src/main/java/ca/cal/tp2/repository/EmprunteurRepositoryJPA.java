package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
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
            Persistence.createEntityManagerFactory("libraryH2"); //Nom de la base de données

    @Override
    public void emprunter(Emprunteur emprunteur, Document doc) {
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            em.getTransaction().begin();
            emprunteur = getEmprunteur(em, emprunteur.getEmail());
            doc = getDocument(em, doc.getTitre());

            if(doc.getNbExemplaires() <= 0) {
                throw new RuntimeException("Il n'y a plus d'exemplaires disponibles pour le document :  " +
                        doc.getTitre());
            }

            TypedQuery<Emprunt> empruntQuery = em.createQuery(
                    "SELECT e FROM Emprunt e WHERE e.emprunteur = :emprunteur" +
                            " AND e.statuts =  'Emprunte'", Emprunt.class);
            empruntQuery.setParameter("emprunteur", emprunteur);
            List<Emprunt> empruntsExistants = empruntQuery.getResultList();
            Emprunt emprunt = empruntsExistants.isEmpty() ? new Emprunt(emprunteur) : empruntsExistants.getFirst();
            if(empruntsExistants.isEmpty()){
                em.persist(emprunt);
            }

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
            emprunteur = getEmprunteur(em, emprunteur.getEmail());
            doc = getDocument(em, doc.getTitre());

            EmpruntDetail empruntDetail = getEmpruntDetails(em, emprunteur, doc);
            if(empruntDetail == null){
                throw new RuntimeException("L'emprunteur n'a pas emprunté ce document");
            }
            empruntDetail.setDateRetourActuelle(LocalDate.now());
            empruntDetail.updateStatus();
            em.merge(empruntDetail);

            doc.setNbExemplaires(doc.getNbExemplaires() + 1);
            em.merge(doc);


            if(empruntDetail.isEnRetard()){
                double montant = empruntDetail.calculAmende();
                Amende amende = new Amende(emprunteur, montant);
                em.persist(amende);
                System.out.println("Amende de " + montant + "$ a été ajoutée à l'emprunteur " + emprunteur.getNom());
            }

            em.getTransaction().commit();
            System.out.println("Retour effectué avec succès par " + emprunteur.getNom());
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EmpruntDetail> chercherListeEmprunts(Emprunteur emp){
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            TypedQuery query = em.createQuery(
                    "SELECT ed FROM EmpruntDetail ed" +
                            " JOIN ed.emprunt e  " +
                            " JOIN e.emprunteur u " +
                            " WHERE type(u) = Emprunteur AND u.email  = :email" +
                            " ORDER BY ed.dateRetourPrevue ASC", EmpruntDetail.class);
            query.setParameter("email", emp.getEmail());
            return query.getResultList();
        }
    }

    private Emprunteur getEmprunteur(EntityManager em, String courriel){
        TypedQuery<Emprunteur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.email = :email AND TYPE(u) = Emprunteur ", Emprunteur.class);
        query.setParameter("email", courriel);
        List<Emprunteur> emprunteurs = query.getResultList();

        if(emprunteurs.isEmpty())
            throw new RuntimeException("L'emprunteur n'existe pas dans la base de données");
        else
            return emprunteurs.getFirst();

    }

    private Document getDocument(EntityManager em, String titre){
        TypedQuery<Document> query = em.createQuery(
                "SELECT d FROM Document d WHERE d.titre = :titre", Document.class);
        query.setParameter("titre", titre);
        List<Document> documents = query.getResultList();

        if(documents.isEmpty())
            throw new RuntimeException("Le document n'existe pas dans la base de données");
        else
            return documents.getFirst();
    }
    private EmpruntDetail getEmpruntDetails(EntityManager em, Emprunteur emp, Document doc){
        TypedQuery<EmpruntDetail> query = em.createQuery(
                "SELECT ed FROM EmpruntDetail ed WHERE ed.emprunt.emprunteur = :emprunteur" +
                        " AND ed.document = :doc AND ed.status = 'Emprunte'", EmpruntDetail.class);
        query.setParameter("emprunteur", emp);
        query.setParameter("doc", doc);
        List<EmpruntDetail> empruntDetails = query.getResultList();
        return empruntDetails.isEmpty() ? null : empruntDetails.getFirst();
    }

    //Méthode qui hardcode et modifie la date de retour prévue d'un emprunt pour tester
    // les cas de retours qui sont en retard.
    public void testRetounerEnRetard(){
        String sql = "UPDATE  EMPRUNTDETAIL SET DATERETOURPREVUE = '2025-02-01' WHERE EMPRUNT_ID = 4";
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            em.getTransaction().begin();
            em.createNativeQuery(sql).executeUpdate();
            em.getTransaction().commit();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
