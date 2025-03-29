package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.exception.DocumentDoesNotExist;
import ca.cal.tp2.modele.*;
import ca.cal.tp2.service.dto.DocumentDTO;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmprunteurRepositoryJPA implements EmprunteurRepository {
    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("libraryH2"); //Nom de la base de données

    @Override
    public void emprunter(Emprunt emprunt) throws DatabaseException {

        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();

            if(emprunt.getEmprunteur() != null && em.contains(emprunt.getEmprunteur())){
                emprunt.setEmprunteur(em.merge(emprunt.getEmprunteur()));
            }

            for(EmpruntDetail ed: emprunt.getEmpruntDetails()){
                if(!em.contains(ed.getDocument()))
                    ed.setDocument(em.merge(ed.getDocument()));
            }

            if(emprunt.getId_emprunt() == 0){
                em.persist(emprunt);
            }
            else{
                em.merge(emprunt);
            }

            tx.commit();
        }

        catch(Exception e){
            if(tx != null && tx.isActive())
                tx.rollback();
            throw new DatabaseException(e);
        }

        finally{
            em.close();
        }
//        try(EntityManager em = entityManagerFactory.createEntityManager()){
//            em.getTransaction().begin();
//
//            if(emprunt.getId_emprunt() == 0){
//                em.persist(emprunt);
//            }
//            else{
//                em.merge(emprunt);
//            }
//
//            em.getTransaction().commit();
//
//        }
//        catch(Exception e){
//            throw new DatabaseException(e);
//        }
    }

    @Override
    public Optional<Emprunteur> findById(Long id) throws DatabaseException{
        try(EntityManager em = entityManagerFactory.createEntityManager()){

            Emprunteur emp = em.find(Emprunteur.class, id);
            return Optional.ofNullable(emp);
        }


        catch(Exception e){
            throw new DatabaseException(e);
        }

    }

    @Override
    public void retourneDocument(Emprunteur emprunteur, Document doc) throws DatabaseException {
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            em.getTransaction().begin();
            emprunteur = getEmprunteur(em, emprunteur.getEmail());
            doc = getDocument(em, doc.getTitre());

            EmpruntDetail empruntDetail = getEmpruntDetails(em, emprunteur, doc);
            if(empruntDetail == null){
                throw new RuntimeException("L'emprunteur n'a pas emprunté ce document");
            }
            empruntDetail.setDateRetourActuelle(LocalDate.now());
            em.merge(empruntDetail);

            doc.setNbExemplaires(doc.getNbExemplaires() + 1);
            em.merge(doc);


            if(empruntDetail.isEnRetard()){
                isAmende(em, empruntDetail, emprunteur);
            }
            em.getTransaction().commit();
            System.out.println("Retour effectué avec succès par " + emprunteur.getNom());
        }
        catch(Exception e){
            throw new DatabaseException(e);
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

    @Override
    public void payerAmende(Emprunteur emp, double montant) throws DatabaseException {
        try(EntityManager em = entityManagerFactory.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<Amende> query = em.createQuery(
                    "SELECT a FROM Amende a WHERE a.emprunteur = :emprunteur " +
                            "AND a.statut = false", Amende.class);
            query.setParameter("emprunteur", emp);
            List<Amende> amendeList = query.getResultList();

            if(amendeList.isEmpty()){
                throw new RuntimeException("L'emprunteur n'a pas d'amende à payer");
            }

            Amende amende = amendeList.getFirst();
            if(montant < amende.getMontant()){
                throw new RuntimeException("Montant insuffisant. Le montant de l'amende est " +
                        amende.getMontant() + "$. Il vous manque " + (amende.getMontant() - montant) + "$");
            }
            amende.setMontant(amende.getMontant() - montant);
            amende.setStatut(true);
            em.merge(amende);
            em.getTransaction().commit();
            System.out.println("Le paiement a été fait succesivement par " + emp.getNom());
        }

        catch (Exception e){
            throw new DatabaseException(e);
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

//    private Emprunt getEmprunt(EntityManager em, Emprunteur emprunteur){
//        Emprunt emp;
//        TypedQuery<Emprunt> empruntQuery = em.createQuery(
//                "SELECT e FROM Emprunt e WHERE e.emprunteur = :emprunteur" +
//                        " AND e.statuts =  'Emprunte'", Emprunt.class);
//        empruntQuery.setParameter("emprunteur", emprunteur);
//        List<Emprunt> empruntsExistants = empruntQuery.getResultList();
//
//        if(empruntsExistants.isEmpty()){
//            emp = new Emprunt(emprunteur);
//            em.persist(emp);
//        }
//        else{
//            emp = empruntsExistants.getFirst();
//        }
//        return emp;
//    }

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

    private void isAmende(EntityManager em, EmpruntDetail empruntDetail, Emprunteur emprunteur){
        double montant = empruntDetail.calculAmende();
        Amende amende = new Amende(emprunteur, montant);
        em.persist(amende);
        System.out.println("Amende de " + montant + "$ a été ajoutée à l'emprunteur " + emprunteur.getNom());
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
