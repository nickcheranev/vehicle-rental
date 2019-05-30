package ru.cheranev.rental.ui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import ru.cheranev.rental.domain.VehicleRented;
import ru.cheranev.rental.jpa.VehicleRentedRepository;

/**
 * Форма выдачи в аренду. В разработке
 *
 * @author Cheranev N.
 * created on 30.05.2019.
 */
//@SpringComponent
//@UIScope
public class RentalPushEditor extends VerticalLayout implements KeyNotifier {

    private final VehicleRentedRepository repository;
    TextField vehicleVehicleModelName = new TextField("Модель");
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    HorizontalLayout actions = new HorizontalLayout(save, cancel);
    Binder<VehicleRented> binder = new Binder<>(VehicleRented.class);
    private VehicleRented vehicleRented;
    private ChangeHandler changeHandler;

    @Autowired
    public RentalPushEditor(VehicleRentedRepository repository) {
        this.repository = repository;

        add(vehicleVehicleModelName, actions);

        binder.bindInstanceFields(this);

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> editVehicleRented(vehicleRented));
        setVisible(false);
    }

    void save() {
        repository.save(vehicleRented);
        changeHandler.onChange();
    }

    public final void editVehicleRented(VehicleRented c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            vehicleRented = repository.findById(c.getId()).get();
        } else {
            vehicleRented = c;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(vehicleRented);

        setVisible(true);

    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }

}
