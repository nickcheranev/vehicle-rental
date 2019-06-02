package ru.cheranev.rental.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.domain.VehicleRented;

import java.time.LocalDateTime;

/**
 * Форма выдачи в аренду
 *
 * @author Cheranev N.
 * created on 30.05.2019.
 */
public class RentalPullForm extends VerticalLayout {

    private final ComboBox<RentalPoint> rentalPointComboBox;
    private Binder<VehicleRented> binder = new Binder<>(VehicleRented.class);
    private RentalPullView view;

    RentalPullForm(RentalPullView view) {
        this.view = view;

        rentalPointComboBox = new ComboBox<>("Пункт проката приема");
        rentalPointComboBox.setItems(view.getService().findAllRentalPoints());
        rentalPointComboBox.setItemLabelGenerator(RentalPoint::getName);

        Button save = new Button("Принять", VaadinIcon.CHECK.create());
        Button cancel = new Button("Отмена");
        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        add(rentalPointComboBox, actions);

        save.addClickListener(e -> save());
        cancel.addClickListener(e -> setVehicleRented(null));
    }

    public void setVehicleRented(VehicleRented vr) {
        binder.setBean(vr);
        rentalPointComboBox.clear();

        if (vr == null) {
            setVisible(false);
        } else {
            setVisible(true);
            rentalPointComboBox.focus();
        }
    }

    private void save() {
        Long rentalPointId = rentalPointComboBox.getValue().getId();
        Long vehicleId = binder.getBean().getVehicle().getId();
        view.getService().pullFromRent(vehicleId, rentalPointId, LocalDateTime.now());
        setVisible(false);
        view.refreshGrid();
    }
}
