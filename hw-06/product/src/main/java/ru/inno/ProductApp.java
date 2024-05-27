package ru.inno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.inno.domain.User;
import ru.inno.service.ProductService;
import ru.inno.service.UserService;

import java.math.BigDecimal;

import static ru.inno.enums.ProductType.ACCOUNT;
import static ru.inno.enums.ProductType.CARD;

@SpringBootApplication
public class ProductApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProductApp.class);
        UserService userService = context.getBean(UserService.class);
        ProductService productService = context.getBean(ProductService.class);

        User user1 = userService.add(new User("Ivan"));
        User user2 = userService.add(new User("Peter"));
        User user3 = userService.add(new User("John"));
        System.out.println(userService.findAll());

        ru.inno.domain.Product product11 = productService.add(new ru.inno.domain.Product(user1.getId(), "IVAC", new BigDecimal("123.123"), CARD));
        ru.inno.domain.Product product12 = productService.add(new ru.inno.domain.Product(user1.getId(), "IVAA", new BigDecimal("123.123"), ACCOUNT));
        ru.inno.domain.Product product21 = productService.add(new ru.inno.domain.Product(user2.getId(), "PETC", new BigDecimal("123.123"), CARD));
        ru.inno.domain.Product product22 = productService.add(new ru.inno.domain.Product(user2.getId(), "PETA", new BigDecimal("123.123"), ACCOUNT));
        ru.inno.domain.Product product31 = productService.add(new ru.inno.domain.Product(user3.getId(), "JONC", new BigDecimal("123.123"), CARD));
        ru.inno.domain.Product product32 = productService.add(new ru.inno.domain.Product(user3.getId(), "JONA", new BigDecimal("123.123"), ACCOUNT));
        System.out.println(productService.findAll());
    }
}
