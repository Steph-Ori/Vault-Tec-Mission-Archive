package CPTEST;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.logging.Logger;
import java.util.List;

public class TodoDao {

    private static final Logger logger = Logger.getLogger(TodoDao.class.getName());

    public void add(String title, String category, String priority) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            TodoItem item = new TodoItem(title, category, priority, "Assigned");

            session.persist(item);
            logger.info("{\"event\":\"MISSION_CREATED\", \"title\":\"" + title + "\", \"category\":\"" + category + "\", \"priority\":\"" + priority + "\"}");
            tx.commit();
        }
    }

    public List<TodoItem> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from TodoItem", TodoItem.class).list();
        }
    }

    public boolean removeById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            TodoItem item = session.get(TodoItem.class, id);
            if (item == null) {
                tx.commit();
                return false;
            }

            session.remove(item);
            logger.warning("{\"event\":\"MISSION_PURGED\", \"id\":" + id + ", \"title\":\"" + item.getTitle() + "\"}");
            tx.commit();
            return true;
        }
    }

    public List<TodoItem> findByTitle(String title) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // SECURE: parameterised query, treated as data only
            String hql = "FROM TodoItem WHERE title = :title";
            Query<TodoItem> query = session.createQuery(hql, TodoItem.class);
            query.setParameter("title", title);
            List<TodoItem> results = query.getResultList();

            logger.info("{\"event\":\"ARCHIVE_SEARCH\", \"searchTerm\":\"" + title + "\", \"resultsFound\":" + results.size() + "}");

            return results;

        } finally {
            session.close();
        }
    }

    public boolean updateStatus(long id, String status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            TodoItem item = session.get(TodoItem.class, id);
            if (item == null) {
                tx.commit();
                return false;
            }

            item.setStatus(status);
            logger.info("{\"event\":\"MISSION_STATUS_UPDATED\", \"id\":" + id + ", \"newStatus\":\"" + status + "\"}");
            tx.commit();
            return true;
        }
    }
    public long countAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT COUNT(t) FROM TodoItem t", Long.class)
                    .getSingleResult();
        }
    }

    public long countByPriority(String priority) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT COUNT(t) FROM TodoItem t WHERE t.priority = :priority",
                            Long.class
                    ).setParameter("priority", priority)
                    .getSingleResult();
        }
    }

    public long countByStatus(String status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT COUNT(t) FROM TodoItem t WHERE t.status = :status",
                            Long.class
                    ).setParameter("status", status)
                    .getSingleResult();
        }
    }
} // End