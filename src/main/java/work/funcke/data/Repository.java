package work.funcke.data;

import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class Repository<T> {
    private javax.persistence.EntityManagerFactory entityManagerFactory;
    private Class<T> entityType;
    // entityManager is required to manipulate model objects in the database
    // it is the central connection between the Java Persistence API, the actual database
    // and the ORM in our case Hibernate Framework.
    //
    // Use the entityManagerFactory whenever you want to create, update or delete an ORM object
    // in the SQLite database.
    public javax.persistence.EntityManager entityManager;

    // Initialize entityManagerFactory using the Java persistence XML file under main/resources/persistence.xml
    public Repository(Class<T> entityType) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory( "local_dev" );
        this.entityManager = this.entityManagerFactory.createEntityManager();
        this.entityType = entityType;
        // TODO: prepare dev database
    }

    public List<T> all() {
        return entityManager.createQuery("from " + entityType.getCanonicalName() + " t").getResultList();
    }

    public T findById(String id) {
        return (T)entityManager.createQuery("from " + entityType.getCanonicalName() + "t where id = " + id).getSingleResult();
    }

    public boolean create(T newInstance) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(newInstance);
        this.entityManager.getTransaction().commit();
        return this.entityManager.contains(newInstance);
    }

    public List<T> findBy(String property, String value) {
        return this.entityManager.createQuery("from " + entityType.getCanonicalName() + " where " + property + " = " + value).getResultList();
    }
}
