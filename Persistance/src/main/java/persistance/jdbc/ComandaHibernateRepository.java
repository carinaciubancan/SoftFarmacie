package persistance.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistance.ComandaRepository;
import persistance.MedicamentRepository;
import spital.Comanda;
import spital.Medicament;

import java.util.List;

public class ComandaHibernateRepository implements ComandaRepository {
    static SessionFactory sessionFactory;

    public ComandaHibernateRepository(){
        sessionFactory = HibernateUtility.getSessionFactory();
        System.out.println("MedicamentHibernateRepo" + sessionFactory);
    }

    @Override
    public List<Comanda> getComenzi() {
        List<Comanda> result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Comanda as c order by c.nivel desc ", Comanda.class).list();

                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if(transaction !=null)
                    transaction.rollback();
            }
        }
        return result;
    }


    public void delete(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Comanda comandaSTERS = session.createQuery("from Comanda where id = ?", Comanda.class)
                        .setParameter(0, id)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Se va sterge " + comandaSTERS);
                session.delete(comandaSTERS);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    public void add(Comanda comanda){
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.save(comanda);
                System.out.println("ComandaHibernateRepo save" + comanda);
                tx.commit();
            }catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Comanda findOne(Integer id) {
        Comanda result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Comanda where id = ? ", Comanda.class)
                        .setParameter(0,id).uniqueResult();

                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if(transaction !=null)
                    transaction.rollback();
            }
        }
        return result;
    }

    @Override
    public void update(Comanda elem) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Comanda comandaUpdated = session.createQuery("from Comanda where id = ?", Comanda.class)
                        .setParameter(0, elem.getId())
                        .setMaxResults(1)
                        .uniqueResult();

                System.out.println("Inainte de modificare " + comandaUpdated);

                comandaUpdated.setStatus(elem.getStatus());
                comandaUpdated.setDataExpedierii(elem.getDataExpedierii());
                comandaUpdated.setIdMedicament(elem.getIdMedicament());
                comandaUpdated.setNivel(elem.getNivel());
                System.out.println("Dupa modificare " + comandaUpdated);

                session.update(comandaUpdated);

                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }
}
