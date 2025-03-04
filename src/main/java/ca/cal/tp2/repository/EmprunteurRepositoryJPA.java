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
                emprunteur = emprunteurs.getFirst();
            else{
                em.persist(emprunteur);
                em.flush();
            }

            //Doit implémenter cela, car sinon la base de donnée persiste le même enregistrement
            // à chaque fois qu'on emprunte un document.
            TypedQuery<Document> documentQuery = em.createQuery(
                    "SELECT d FROM Document d WHERE d.titre = :titre", Document.class);
            documentQuery.setParameter("titre", doc.getTitre());
            List<Document> documents = documentQuery.getResultList();

            if(!documents.isEmpty()){
                doc = documents.getFirst();
            }
            else{
                em.persist(doc);
                em.flush();
            }

            if(doc.getNbExemplaires() <= 0) {
                throw new RuntimeException("Il n'y a plus d'exemplaires disponibles pour le document :  " +
                        doc.getTitre());
            }

            Emprunt emprunt = new Emprunt(emprunteur);
            em.persist(emprunt);

            EmpruntDetail empruntDetail = new EmpruntDetail(emprunt, doc);
            em.persist(empruntDetail);

            doc.setNbExemplaires(doc.getNbExemplaires() - 1);

            em.createQuery(
                    " UPDATE Document d SET d.nbExemplaires = :newValue WHERE d.id_document = :id")
                            .setParameter("newValue", doc.getNbExemplaires())
                            .setParameter("id", doc.getId_document())
                            .executeUpdate();


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

            //Assure que l'emprunteur existe dans la base de données. Sinon, je risque une exception.+-
            TypedQuery<Emprunteur> queryEmp = em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email " +
                            " AND TYPE(u) = Emprunteur ", Emprunteur.class);
            queryEmp.setParameter("email", emprunteur.getEmail());
            List<Emprunteur> emprunteurs = queryEmp.getResultList();

            if(!emprunteurs.isEmpty()){
                emprunteur = emprunteurs.getFirst();
            }
            else{
                throw new RuntimeException("L'emprunteur n'existe pas dans la base de données");
            }

            TypedQuery<Document> queryDoc = em.createQuery(
                    "SELECT d FROM Document d WHERE d.titre = :titre", Document.class);
            queryDoc.setParameter("titre", doc.getTitre());
            List<Document> documents = queryDoc.getResultList();
            if(!documents.isEmpty()){
                doc = documents.getFirst();
            }
            else{
                throw new RuntimeException("Le document n'existe pas dans la base de données");
            }


            TypedQuery<EmpruntDetail> query = em.createQuery(
                    "SELECT ed FROM EmpruntDetail ed WHERE ed.emprunt.emprunteur = :emprunteur" +
                            " AND ed.document = :doc AND ed.status = 'Emprunte'", EmpruntDetail.class);
            query.setParameter("emprunteur", emprunteur);
            query.setParameter("doc", doc);
            List<EmpruntDetail> empruntDetails = query.getResultList();

            if(empruntDetails.isEmpty()){
                throw new RuntimeException("L'emprunteur n'a pas emprunté ce document");
            }
            EmpruntDetail empruntDetail = empruntDetails.getFirst();
            empruntDetail.setDateRetourActuelle(LocalDate.now());
            empruntDetail.updateStatus();
            em.merge(empruntDetail);

            doc.setNbExemplaires(doc.getNbExemplaires() + 1);
            em.createQuery(
                    "UPDATE Document d SET d.nbExemplaires = :newvalue WHERE d.id_document = :id")
                    .setParameter("newvalue", doc.getNbExemplaires())
                    .setParameter("id", doc.getId_document())
                    .executeUpdate();


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
