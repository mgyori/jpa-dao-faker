package book;

import book.model.Book;
import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import java.util.Locale;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new PersistenceModule("test"));
        BookDao bookDao = injector.getInstance(BookDao.class);
        BookGenerator gen = new BookGenerator(new Locale("hu"));
        for (int i = 0; i < 100; i++) {
            bookDao.persist(gen.generate());
        }
        bookDao.findAll()
                .stream()
                .forEach(System.out::println);

        // Test findByIsbn13 function:
        Optional<Book> b = bookDao.find(Long.valueOf(1));
        System.out.println(bookDao.findByIsbn13(b.get().getIsbn13()));
    }
}
