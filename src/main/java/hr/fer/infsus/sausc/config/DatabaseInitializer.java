package hr.fer.infsus.sausc.config;

import hr.fer.infsus.sausc.model.db.*;
import hr.fer.infsus.sausc.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Configuration
public class DatabaseInitializer {
    @Bean
    CommandLineRunner initDatabase(AdministratorRepository administratorRepository,
                                   SportsCenterMemberRepository memberRepository,
                                   ActivityRepository activityRepository,
                                   EquipmentRepository equipmentRepository,
                                   ActivityEquipmentRepository activityEquipmentRepository,
                                   StatusRepository statusRepository) {
        return args -> {
            // Provjeri postoje li podaci u svakoj tablici

            long adminCount = administratorRepository.count();
            long memberCount = memberRepository.count();
            long activityCount = activityRepository.count();
            long equipmentCount = equipmentRepository.count();
            long activityEquipmentCount = activityEquipmentRepository.count();
            long statusCount = statusRepository.count();

            // Ako su sve tablice već inicijalizirane, ne radi ništa
            if (adminCount > 0 && memberCount > 0 && activityCount > 0 && equipmentCount > 0 && activityEquipmentCount > 0 && statusCount > 0) {
                return;
            }

            // Kreiraj administratore ako nisu inicijalizirani
            if (adminCount == 0) {
                Administrator admin1 = new Administrator();
                admin1.setFirstName("John");
                admin1.setLastName("Doe");
                admin1.setEmail("john.doe@example.com");
                admin1.setPassword("password");
                admin1.setEmploymentDate(new Date());
                administratorRepository.save(admin1);

                Administrator admin2 = new Administrator();
                admin2.setFirstName("Jane");
                admin2.setLastName("Smith");
                admin2.setEmail("jane.smith@example.com");
                admin2.setPassword("password");
                admin2.setEmploymentDate(new Date());
                administratorRepository.save(admin2);
            }

            // Kreiraj članove sportskog centra ako nisu inicijalizirani
            if (memberCount == 0) {
                SportsCenterMember member1 = new SportsCenterMember();
                member1.setFirstName("Alice");
                member1.setLastName("Johnson");
                member1.setEmail("alice.johnson@example.com");
                member1.setPassword("password");
                member1.setMembershipStatus("ACTIVE");
                member1.setMembershipStartDate(LocalDate.now());
                member1.setMembershipEndDate(LocalDate.now().plusYears(1));
                member1.setNumberOfAbsences(0);
                memberRepository.save(member1);

                SportsCenterMember member2 = new SportsCenterMember();
                member2.setFirstName("Bob");
                member2.setLastName("Williams");
                member2.setEmail("bob.williams@example.com");
                member2.setPassword("password");
                member2.setMembershipStatus("ACTIVE");
                member2.setMembershipStartDate(LocalDate.now());
                member2.setMembershipEndDate(LocalDate.now().plusYears(1));
                member2.setNumberOfAbsences(0);
                memberRepository.save(member2);
            }

            // Kreiraj opremu ako nije inicijalizirana
            if (equipmentCount == 0) {
                Equipment equipment1 = new Equipment();
                equipment1.setName("Traka za trčanje");
                equipment1.setDescription("Visokokvalitetna traka za trčanje");
                equipmentRepository.save(equipment1);

                Equipment equipment2 = new Equipment();
                equipment2.setName("Bučice");
                equipment2.setDescription("Set bučica");
                equipmentRepository.save(equipment2);
            }

            // Kreiraj aktivnosti ako nisu inicijalizirane
            if (activityCount == 0) {
                Activity activity1 = new Activity();
                activity1.setName("Pilates");
                activity1.setDescription("Jutarnji pilates");
                activity1.setPricePerHour(20.0);
                activity1.setAdministrator(administratorRepository.findById(1L).orElse(null)); // ili koristi stvarni ID administratora
                activity1.setActivityEquipments(new HashSet<>());
                activityRepository.save(activity1);

                Activity activity2 = new Activity();
                activity2.setName("Kardio");
                activity2.setDescription("Kardio vježbanje");
                activity2.setPricePerHour(25.0);
                activity2.setAdministrator(administratorRepository.findById(2L).orElse(null)); // ili koristi stvarni ID administratora
                activity2.setActivityEquipments(new HashSet<>());
                activityRepository.save(activity2);
            }

            // Kreiraj ActivityEquipment ako nije inicijaliziran
            if (activityEquipmentCount == 0) {
                ActivityEquipment activityEquipment1 = new ActivityEquipment();
                activityEquipment1.setActivity(activityRepository.findById(1L).orElse(null)); // ili koristi stvarni ID aktivnosti
                activityEquipment1.setEquipment(equipmentRepository.findById(1L).orElse(null)); // ili koristi stvarni ID opreme
                activityEquipment1.setQuantity(5);
                activityEquipmentRepository.save(activityEquipment1);

                ActivityEquipment activityEquipment2 = new ActivityEquipment();
                activityEquipment2.setActivity(activityRepository.findById(2L).orElse(null)); // ili koristi stvarni ID aktivnosti
                activityEquipment2.setEquipment(equipmentRepository.findById(2L).orElse(null)); // ili koristi stvarni ID opreme
                activityEquipment2.setQuantity(10);
                activityEquipmentRepository.save(activityEquipment2);
            }

            // Kreiraj statuse ako nisu inicijalizirani
            if (statusCount == 0) {
                Status status1 = new Status();
                status1.setName("Potvrđena");
                status1.setAbbreviation("POT");

                Status status2 = new Status();
                status2.setName("Završena");
                status2.setAbbreviation("ZAV");

                Status status3 = new Status();
                status3.setName("Otkazana");
                status3.setAbbreviation("OTK");

                Status status4 = new Status();
                status4.setName("Nedolazak");
                status4.setAbbreviation("NED");

                List<Status> statuses = List.of(status1,status2,status3,status4);
                statusRepository.saveAll(statuses);
            }
        };
    }
}
