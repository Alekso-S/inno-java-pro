package ru.inno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.inno.service.ProductService;
import ru.inno.service.UserService;

@SpringBootApplication
public class ProductApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProductApp.class);
        UserService userService = context.getBean(UserService.class);
        ProductService productService = context.getBean(ProductService.class);

        System.out.println(userService.findAll());
        System.out.println(productService.findAll());
    }
}
