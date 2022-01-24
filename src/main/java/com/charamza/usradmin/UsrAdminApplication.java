package com.charamza.usradmin;

import com.charamza.usradmin.models.User;
import com.charamza.usradmin.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsrAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsrAdminApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService service) {
        return args -> {
            service.saveUser(new User("Theodore", "Roosevelt", "teddy@rose.com", "111 525 111", true));
            service.saveUser(new User("Leto", "Atreides", "duke@houseatreides.du", "456 123 456", false));
            service.saveUser(new User("Julius", "Caesar", "roman@empire.it", "456 000 321", true));
            service.saveUser(new User("Johanka", "Z Arku", "panna@orleanska.fr", "112 567 321", true));
            service.saveUser(new User("Robert", "Langdon", "davinci@code.uk", "437 543 222", false));
            service.saveUser(new User("Jacques", "Saunière", "prioryof@sione.it", "090 940 999", true));
            service.saveUser(new User("Frank", "Abagnale", "c@tchme.if", "352 567 368", false));
            service.saveUser(new User("Blade", "Brooks", "eric@cross.us", "112 112 112", false));
            service.saveUser(new User("Ragnar", "Lothbrok", "skol@skol.skol", "093 343 222", true));
            service.saveUser(new User("Arabela", "Vdavkuchtiva", "prsten@tocim.ju", "760 455 334", true));
            service.saveUser(new User("Joker", "Smile", "heaheheh@hehahhe.he", "000 000 000", false));
            service.saveUser(new User("Yup", "Yop", "yup@yop.ya", "523 652 662", false));
        };
    }

}
