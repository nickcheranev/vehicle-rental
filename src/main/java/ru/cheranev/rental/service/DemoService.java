package ru.cheranev.rental.service;

import com.vividsolutions.jts.io.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cheranev.rental.common.Const;
import ru.cheranev.rental.domain.Status;
import ru.cheranev.rental.domain.*;
import ru.cheranev.rental.jpa.*;
import ru.cheranev.rental.util.GisUtil;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Service
@Slf4j
public class DemoService {

    private static final int DEMO_VEHICLE_NUMBER = 50;
    private static final int DEMO_RENT_COUNT = 5;

    @Autowired
    private VehicleModelRepository vehicleModelRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TrackerRepository trackerRepository;
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;
    @Autowired
    private VehicleRentedRepository vehicleRentedRepository;
    @Autowired
    private VehicleRentHistoryRepository historyRepository;
    @Autowired
    private RentalPointRepository rentalPointRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RentalServiceImpl rentalService;

    private List<VehicleModel> allModels;
    private List<RentalPoint> allRentalPoints;
    private List<Customer> allCustomers;

    public void populateDemoExample() {
        clearAll();
        populateVehicleType();
        populateVehicleModel();
        populateRentalPoint();
        populateCustomer();
        populateVehicle();

        try {
            populateVehicleRented();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public void clearAll() {
        historyRepository.deleteAll();
        vehicleRentedRepository.deleteAll();
        vehicleRepository.deleteAll();
        trackerRepository.deleteAll();
        vehicleModelRepository.deleteAll();
        rentalPointRepository.deleteAll();
        customerRepository.deleteAll();
        vehicleTypeRepository.deleteAll();
    }

    public void populateVehicleType() {
        String[] vehicleTypes = {"Автомобиль", "Мотоцикл", "Скутер"};
        for(String type : vehicleTypes) {
            VehicleType vehicleType = new VehicleType();
            vehicleType.setName(type);
            vehicleTypeRepository.save(vehicleType);
        }
    }

    public void populateCustomer() {
        String[] customers = {
                "Иванов Иван Петрович",
                "Петров Иван Иванович",
                "Михайлов Стас Петрович",
                "Соколов Федор Михайлович",
                "Пушкин Илья Сергеевич",
                "Мошкин Вячеслав Александрович",
                "Никитин Петр Сергеевич",
                "Зайцев Андрей Петрович",
                "Волков Сидор Иванович",
                "Захаров Андрей Сергеевич" };
        for(String fio : customers) {
            Customer customer = new Customer();
            customer.setName(fio);
            customerRepository.save(customer);
        }
    }

    public void populateRentalPoint() {
        Map<String,String> cities = Stream.of(new String[][] {
                {"Пермь","56.234200 58.010300"},
                {"Березники","57.578300 59.163700"},
                {"Губаха","57.554500 58.837100"},
                {"Красновишерск","57.053700 60.390200"},
                {"Кунгур","56.943800 57.428400"},
                {"Лысьва","57.808600 58.099700"},
                {"Оса","55.460300 57.279700"},
                {"Очер","54.709600 57.871700"},
                {"Соликамск","56.771000 59.648300"},
                {"Чайковский","54.147800 56.778100"}
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        for (String city : cities.keySet()) {
            RentalPoint rentalPoint = new RentalPoint();
            rentalPoint.setName(city);
            rentalPoint.setLocation("POINT( " + cities.get(city) + ")");
            rentalPointRepository.save(rentalPoint);
        }
    }

    public void populateVehicleRented() throws ParseException {

        // поставить на учет в пункт проката
        List<Vehicle> vehicles = vehicleRepository.findAll();
        for(Vehicle vehicle : vehicles) {
            VehicleRented vehicleRented = new VehicleRented();
            vehicleRented.setEndRentTime(LocalDateTime.now());
            vehicleRented.setEndRentalPoint(getRandomRentalPoint());
            vehicleRented.setVehicle(vehicle);
            vehicleRented.setStatus(Status.PARKED);
            vehicleRentedRepository.save(vehicleRented);
        }

        for (int i = 0; i<DEMO_RENT_COUNT; i++) {
            // выдать в аренду ТС
            for(VehicleRented vr : vehicleRentedRepository.findAllByStatus(Status.PARKED)) {
                rentalService.pushToRent(vr.getVehicle().getId(), getRandomCustomer().getId(), vr.getEndRentTime().plusMinutes(ThreadLocalRandom.current().nextLong(1,4*60)));
            }

            // принять ТС из аренды
            for (VehicleRented vr : vehicleRentedRepository.findAllByStatus(Status.RENTED).subList(0, DEMO_VEHICLE_NUMBER-3)) {
                VehicleRented pulled = rentalService.pullFromRent(vr.getVehicle().getId(), getRandomRentalPoint().getId(), vr.getBeginRentTime().plusMinutes(ThreadLocalRandom.current().nextLong(1, 6*60)));
                populateHistory(pulled);
            }
        }
    }

    private void populateHistory(VehicleRented vehicleRented) throws ParseException {
        LocalDateTime currentTime = vehicleRented.getBeginRentTime();
        LocalDateTime endRentTime = vehicleRented.getEndRentTime();
        String currentLocation = vehicleRented.getBeginRentalPoint().getLocation();
        while(currentTime.isBefore(endRentTime)) {
            VehicleRentHistory history = new VehicleRentHistory();
            history.setVehicleRented(vehicleRented);
            history.setEventTime(currentTime);
            history.setLocation(currentLocation);
            // Трекер GPS отправляет координаты каждые 10 минут
            currentTime = currentTime.plus(Const.GPS_TRACKER_SIGNAL_PERIOD, ChronoUnit.MINUTES);
            currentLocation = GisUtil.randomNextPoint(currentLocation);
            historyRepository.save(history);
        }
    }

    public void populateVehicleModel() {
        Map<String,String> items = Stream.of(new String[][] {
                {"Lada Largus","Автомобиль"},
                {"Lada Granta","Автомобиль"},
                {"Volkswagen Polo","Автомобиль"},
                {"Toyota Corola","Автомобиль"},
                {"Hyundai Solaris","Автомобиль"},
                {"Kia Rio","Автомобиль"},
                {"Toyota Rav4","Автомобиль"},
                {"Renault Logan","Автомобиль"},
                {"Yamaha XJR 1200", "Мотоцикл"},
                {"Honda Giorno 1200", "Скутер"}
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        vehicleModelRepository.deleteAll();
        for(String key : items.keySet()) {
            VehicleModel vehicleModel = new VehicleModel();
            vehicleModel.setName(key);
            vehicleModel.setVehicleType(getVehicleType(items.get(key)));
            vehicleModelRepository.save(vehicleModel);
        }
    }

    private VehicleType getVehicleType(String name) {
        return vehicleTypeRepository.findByName(name).get();
    }

    @Transactional
    public void populateVehicle() {
        for(int i=0; i<DEMO_VEHICLE_NUMBER; i++) {
            Vehicle vehicle = new Vehicle();
            vehicle.setTracker(getNextTracker());
            vehicle.setRegNumber(getNextRegNumber());
            vehicle.setVehicleModel(getRandomVehicleModel());
            vehicleRepository.save(vehicle);
        }
    }

    public VehicleModel getRandomVehicleModel() {
        if(allModels == null)
            allModels = vehicleModelRepository.findAll();
        return allModels.get(ThreadLocalRandom.current().nextInt(0, allModels.size()));
    }

    private RentalPoint getRandomRentalPoint() {
        if(allRentalPoints == null)
            allRentalPoints = rentalPointRepository.findAll();
        return allRentalPoints.get(ThreadLocalRandom.current().nextInt(0, allRentalPoints.size()));
    }

    private Customer getRandomCustomer() {
        if(allCustomers == null)
            allCustomers = customerRepository.findAll();
        return allCustomers.get(ThreadLocalRandom.current().nextInt(0, allCustomers.size()));
    }

    private String getNextRegNumber() {
        return RegNumberSequence.getNextRegNumber();
    }

    private Tracker getNextTracker() {
        trackerRepository.findFirstByOrderByIdDesc();
        Tracker tracker = new Tracker();
        tracker.setIdentifier("TRACKER-" + UUID.randomUUID().toString());
        trackerRepository.save(tracker);
        return tracker;
    }
}
