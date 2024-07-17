package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
//        List<Node> nodes = new ArrayList<>();
//        Charger charger = new Charger(nodes);

        System.out.println(appConfig.getAreaLowerCorner() +" " + appConfig.getScootersAmount());

//        if (appConfig.isNeedRandomGeneration()) {
//            if (database_helper::get_records_count() > appConfig.getScootersAmount())
//            {
//                both = read_nodes_from_db();
//            }
//	else
//            {
//
//                NodeGenerator generator = new RandomNodeGenerator(appConfig.getAreaLowerCorner().getLat(), appConfig.getAreaUpperCorner().getLat(), appConfig.getAreaLowerCorner().getLon(), appConfig.getAreaUpperCorner().getLon());
//                both = generator.generateScooterList(appConfig.getScootersAmount());
//
//                // TODO: create ENTITY and REPOSITORY database_helper::insert_vector_nodes_into_table(both);
//            }
//        } else {
//            both = read_nodes_from_db();
//        }
//
//        var divider = new AreaDivider(Pair<>(appConfig.getAreaLowerCorner().getLon(), ));
//
//        problem_solver solv
//        (check_period_in_sec, finder.get(), divider.get(), grid_size, battery_limit, both, {lat_min, lon_min}, {lat_max, lon_max})
//        ;
//
//        std::cout << "Start percentage value: [ " << solv.get_avg_charging_percentage_info() << "% ]" << std::endl;
//        charger chrgr ( & solv, logger_filename, critical_charge_val, battery_limit);
//        chrgr.start_route_following();
//        std::cout << "End percentage value: [ " << solv.get_avg_charging_percentage_info() << "% ]" << std::endl;
//
//        for (auto station : both) {
//            delete station;
//        }

        SpringApplication.run(Main.class, args);
    }



}