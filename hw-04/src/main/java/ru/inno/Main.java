package ru.inno;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.inno.domain.User;
import ru.inno.service.UserService;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        UserService service = context.getBean(UserService.class);

        service.init();

        User user1 = service.add(new User("Ivan"));
        User user2 = service.add(new User("Peter"));
        User user3 = service.add(new User("John"));

        System.out.println(service.getAll());
        System.out.println(service.getById(user1.getId()));
        System.out.println();

        user3.setUsername("Sergey");
        service.update(user3);
        System.out.println(service.getAll());
        System.out.println();

        service.deleteById(user2.getId());
        System.out.println(service.getAll());
    }
}
