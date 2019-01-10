package net.chlab.sandbox.springmvcasciidoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringMvcAsciiDocApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcAsciiDocApplication.class, args);
    }

    @Configuration
    public static class WebAppConfig implements WebMvcConfigurer {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addRedirectViewController("/", "docs/api-guide.html");
        }
    }
}
