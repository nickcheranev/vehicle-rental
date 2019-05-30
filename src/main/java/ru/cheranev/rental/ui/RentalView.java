package ru.cheranev.rental.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.cheranev.rental.domain.ReportAverageTime;
import ru.cheranev.rental.domain.VehicleRentHistory;
import ru.cheranev.rental.domain.VehicleRented;
import ru.cheranev.rental.jpa.ReportAverageTimeRepository;
import ru.cheranev.rental.jpa.VehicleRentedRepository;

/**
 * @author Cheranev N.
 * created on 20.05.2019.
 */
@Route(value = "rental", layout = MainLayout.class)
@PageTitle("Rental Busines")
public class RentalView extends VerticalLayout {

    public static final String VIEW_TITLE = "Выдача ТС в аренду";
    public static final String VIEW_NAME = "rental";
    private final VehicleRentedRepository repository;
    final Grid<VehicleRented> grid;
   // private final RentalPushEditor editor;
   // private final Button addNewBtn;

    public RentalView(VehicleRentedRepository repository /*, RentalPushEditor editor*/) {
        this.repository = repository;
    //    this.editor = editor;
        setSizeFull();
    //    this.addNewBtn = new Button("Выдать", VaadinIcon.PLUS.create());
        grid = new Grid<>(VehicleRented.class);
       // grid.removeColumnByKey("id");
        grid.setColumns("endRentalPoint.name", "vehicle.vehicleModel.name", "vehicle.regNumber");
        grid.getColumnByKey("endRentalPoint.name").setHeader("Пункт проката");
        grid.getColumnByKey("vehicle.vehicleModel.name").setHeader("Модель");
        grid.getColumnByKey("vehicle.regNumber").setHeader("Гос. номер");
        add(/*addNewBtn,*/ grid/*, editor*/);

        // Блок подключения редактора. В разработке
        // Connect selected VehicleRented to editor or hide if none is selected
//        grid.asSingleSelect().addValueChangeListener(e -> {
//            editor.editVehicleRented(e.getValue());
//        });

        // Instantiate and edit new VehicleRented the new button is clicked
     //   addNewBtn.addClickListener(e -> editor.editVehicleRented(new VehicleRented()));

        // Listen changes made by the editor, refresh data from backend
//        editor.setChangeHandler(() -> {
//            editor.setVisible(false);
//            listRecords();
//        });
        listRecords();
    }

    private void listRecords() {
        this.grid.setItems(repository.findAllByParkedIsTrue());
    }
}
