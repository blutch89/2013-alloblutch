package ch.blutch.alloblutch.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {	
	private static SessionFactory sessionFactory = buildSessionFactory();
	private static Session session;
	
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("ch/blutch/alloblutch/cfg/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            Log4JUtils.getLogger().fatal("Impossible de créer le SessionFactory");
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static void openSession() {
    	session = sessionFactory.openSession();
    }
    
    public static Session getCurrentSession() {
    	return session;
    }
    
    public static void closeSession() {
    	session.close();
    }
    
}
