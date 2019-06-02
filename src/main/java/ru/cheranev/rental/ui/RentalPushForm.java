package ru.cheranev.rental.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import ru.cheranev.rental.domain.Customer;
import ru.cheranev.rental.domain.VehicleRented;

import java.time.LocalDateTime;

/**
 * Форма выдачи в аренду
 *
 * @author Cheranev N.
 * created on 30.05.2019.
 */
public class RentalPushForm extends VerticalLayout {

    private final ComboBox<Customer> customerComboBox;
    private Button save = new Button("Выдать", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Отмена");
    private HorizontalLayout actions = new HorizontalLayout(save, cancel);
    private Binder<VehicleRented> binder = new Binder<>(VehicleRented.class);
    private RentalPushView view;

    public RentalPushForm(RentalPushView view) {
        this.view = view;

        customerComboBox = new ComboBox<>("Арендатор");
        customerComboBox.setItems(view.getService().findAllCustomers());
        customerComboBox.setItemLabelGenerator(Customer::getName);

        add(customerComboBox, actions);

        save.addClickListener(e -> save());
        cancel.addClickListener(e -> setVehicleRented(null));
    }

    public void setVehicleRented(VehicleRented vr) {
        binder.setBean(vr);
        customerComboBox.clear();

        if (vr == null) {
            setVisible(false);
        } else {
            setVisible(true);
            customerComboBox.focus();
        }
    }

    private void save() {
        Long selectedCustomerId = customerComboBox.getValue().getId();
        Long selectedVehicleId = binder.getBean().getVehicle().getId();
        view.getService().pushToRent(selectedVehicleId, selectedCustomerId, LocalDateTime.now());
        setVisible(false);
        view.refreshGrid();
    }
}
