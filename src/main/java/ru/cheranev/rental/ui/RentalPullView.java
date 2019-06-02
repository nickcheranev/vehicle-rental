package ru.cheranev.rental.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.cheranev.rental.domain.Status;
import ru.cheranev.rental.domain.VehicleRented;
import ru.cheranev.rental.service.RentalServiceImpl;

import java.util.List;

/**
 * Страница приема ТС из аренды
 *
 * @author Cheranev N.
 * created on 20.05.2019.
 */
@Route(value = "pull", layout = MainLayout.class)
@PageTitle("Прием ТС из аренды")
public class RentalPullView extends VerticalLayout {

    public static final String VIEW_TITLE = "Прием ТС из аренды";
    public static final String VIEW_NAME = "pull";
    private final Grid<VehicleRented> grid;
    private final RentalServiceImpl service;
    private final RentalPullForm form;

    public RentalPullView(RentalServiceImpl service) {

        this.service = service;
        this.form = new RentalPullForm(this);

        grid = new Grid<>(VehicleRented.class);

        grid.setColumns("beginRentalPoint.name", "vehicle.vehicleModel.vehicleType.name", "vehicle.vehicleModel.name", "vehicle.regNumber");
        grid.getColumnByKey("vehicle.vehicleModel.vehicleType.name").setHeader("Тип ТС");
        grid.getColumnByKey("vehicle.vehicleModel.name").setHeader("Модель");
        grid.getColumnByKey("vehicle.regNumber").setHeader("Гос. номер");
        grid.getColumnByKey("beginRentalPoint.name").setHeader("Пункт проката выдачи");

        grid.asSingleSelect().addValueChangeListener(event ->
                form.setVehicleRented(event.getValue()));

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();
        setSizeFull();

        form.setVehicleRented(null);

        Label label = new Label();
        label.getElement().setProperty("innerHTML", "Для приема ТС из аренды выберите ТС в таблице, задайте <b>Пункт проката приема</b> и нажмите <b>Выдать</b>");

        add(label, mainContent);
        refreshGrid();
    }

    public RentalServiceImpl getService() {
        return service;
    }

    private List<VehicleRented> getItems() {
        return service.findAllVehicleRentedByStatus(Status.RENTED);
    }

    public void refreshGrid() {
        this.grid.setItems(getItems());
    }
}
