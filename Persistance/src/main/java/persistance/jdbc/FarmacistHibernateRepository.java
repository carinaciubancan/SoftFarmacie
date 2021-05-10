package persistance.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistance.FarmacistRepository;
import persistance.MedicRepository;
import spital.Farmacist;
import spital.Medic;

import java.util.List;

public class FarmacistHibernateRepository implements FarmacistRepository {
    static SessionFactory sessionFactory;

    public FarmacistHibernateRepository(){
        sessionFactory = HibernateUtility.getSessionFactory();
        System.out.println("MedicHibernateRepo" + sessionFactory);
    }
    @Override
    public Farmacist findFarmacistByUsernamePassword(String username, String password) {
        Farmacist result=null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Farmacist  where username= ? and password= ?", Farmacist.class)
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
    public void add(Farmacist elem) throws Exception {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Farmacist elem) {

    }

    @Override
    public Farmacist findById(Integer integer) {
        return null;
    }

    @Override
    public List<Farmacist> findAll() {
        return null;
    }
}
