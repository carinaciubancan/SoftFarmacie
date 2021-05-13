package persistance.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistance.MedicRepository;
import spital.Medic;

import java.util.List;

public class MedicHibernateRepository implements MedicRepository {
    static SessionFactory sessionFactory;

    public MedicHibernateRepository(){
        sessionFactory = HibernateUtility.getSessionFactory();
        System.out.println("MedicHibernateRepo" + sessionFactory);
    }
    @Override
    public Medic findMedicByUsernamePassword(String username, String password) {
        Medic result=null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Medic  where username= ? and password= ?", Medic.class)
                        .setParameter(0,username).setParameter(1,password).uniqueResult();

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
    public void add(Medic elem) throws Exception {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Medic elem) {

    }

    @Override
    public Medic findById(Integer integer) {
        return null;
    }

    @Override
    public List<Medic> findAll() {
        return null;
    }
}
