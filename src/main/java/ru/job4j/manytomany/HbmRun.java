package ru.job4j.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Author pushin = Author.of("Pushkin");
            Author ritchi = Author.of("Ritchi");
            Author kernigan = Author.of("Kernigan");

            Book langc = Book.of("Language С");
            langc.setAuthors(List.of(ritchi, kernigan));

            Book onegin = Book.of("Evgeniy Onegin");
            onegin.setAuthors(List.of(pushin));

            session.persist(langc);
            session.persist(onegin);

            Book second = session.get(Book.class, 2);
            session.remove(second);
            
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
