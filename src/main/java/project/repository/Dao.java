package project.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import project.model.EntityTest;
import project.util.HibernateSessionFactoryUtil;

import java.util.List;

public class Dao {

    public EntityTest findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(EntityTest.class, id);
    }

    public void save(EntityTest test) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(test);
        transaction.commit();
        session.close();
    }

    public void update(EntityTest test) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(test);
        transaction.commit();
        session.close();
    }

    public void delete(EntityTest test) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(test);
        transaction.commit();
        session.close();
    }

    public List<EntityTest> findAll() {
        List<EntityTest> test = (List<EntityTest>) HibernateSessionFactoryUtil
                .getSessionFactory().openSession().createQuery("From EntityTest").list();
        return test;
    }

    public int countRows() {
        return findAll().size();
    }


    public void deleteAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM EntityTest tabletest").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
