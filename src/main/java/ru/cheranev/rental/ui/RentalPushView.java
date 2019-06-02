package ru.cheranev.rental.ui;

import com.vaadin.flow.component.Html;
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
 * Страница выдачи ТС в аренду
 *
 * @author Cheranev N.
 * created on 20.05.2019.
 */
@Route(value = "push", layout = MainLayout.class)
@PageTitle("Выдача ТС в аренду")
public class RentalPushView extends VerticalLayout {

    public static final String VIEW_TITLE = "Выдача ТС в аренду";
    public static final String VIEW_NAME = "push";
    private final Grid<VehicleRented> grid;
    private final RentalServiceImpl service;
    private final RentalPushForm form;

    public RentalPushView(RentalServiceImpl service) {

        this.service = service;
        this.form = new RentalPushForm(this);

        grid = new Grid<>(VehicleRented.class);
        grid.removeColumnByKey("id");
        grid.setColumns("endRentalPoint.name", "vehicle.vehicleModel.vehicleType.name", "vehicle.vehicleModel.name", "vehicle.regNumber");
        grid.getColumnByKey("vehicle.vehicleModel.vehicleType.name").setHeader("Тип ТС");
        grid.getColumnByKey("endRentalPoint.name").setHeader("Пункт проката");
        grid.getColumnByKey("vehicle.vehicleModel.name").setHeader("Модель");
        grid.getColumnByKey("vehicle.regNumber").setHeader("Гос. номер");

        grid.asSingleSelect().addValueChangeListener(event ->
                form.setVehicleRented(event.getValue()));

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();
        setSizeFull();

        form.setVehicleRented(null);

        Label label = new Label();
        label.getElement().setProperty("innerHTML", "Для выдачи ТС в аренду выберите ТС в таблице, задайте <b>Арендатора</b> и нажмите <b>Выдать</b>");

        add(label, mainContent);
        refreshGrid();
    }

    public RentalServiceImpl getService() {
        return service;
    }

    private List<VehicleRented> getItems() {
        return service.findAllVehicleRentedByStatus(Status.PARKED);
    }

    public void refreshGrid() {
        this.grid.setItems(getItems());
    }
}
