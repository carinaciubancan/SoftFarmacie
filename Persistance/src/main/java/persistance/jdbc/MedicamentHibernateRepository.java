package persistance.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistance.MedicamentRepository;
import spital.Medicament;

import java.util.List;

public class MedicamentHibernateRepository implements MedicamentRepository {
    static SessionFactory sessionFactory;

    public MedicamentHibernateRepository(){
        sessionFactory = HibernateUtility.getSessionFactory();
        System.out.println("MedicamentHibernateRepo" + sessionFactory);
    }

    @Override
    public List<Medicament> getMedicamenteStoc() {
        List<Medicament> result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Medicament", Medicament.class).list();

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
    public List<Medicament> findMedicamentDupaNume(String nume) {
        List<Medicament> result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Medicament where nume = ?  ", Medicament.class)
                        .setParameter(0,nume).list();

                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if(transaction !=null)
                    transaction.rollback();
            }
        }
        return result;
    }


}
