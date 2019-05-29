package ru.cheranev.rental.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import ru.cheranev.rental.domain.ReportAverageTime;
import ru.cheranev.rental.jpa.ReportAverageTimeRepository;

/**
 * @author Cheranev N.
 * created on 20.05.2019.
 */
@Route(value = "reportAvgTime", layout = MainLayout.class)
@PageTitle("Report Average Time")
public class ReportAvgTimeView extends VerticalLayout {

    public static final String VIEW_TITLE = "Отчет \"Средняя продолжительность аренды\"";
    public static final String VIEW_NAME = "reportAvgTime";
    private final ReportAverageTimeRepository repository;
    final Grid<ReportAverageTime> grid;

    public ReportAvgTimeView(ReportAverageTimeRepository repository) {
        this.repository = repository;
        setSizeFull();
        grid = new Grid<>(ReportAverageTime.class);
       // grid.removeColumnByKey("id");
        grid.setColumns("rpName", "vtName", "vmName", "avgTime");
        grid.getColumnByKey("rpName").setHeader("Точка проката");
        grid.getColumnByKey("vtName").setHeader("Вид ТС");
        grid.getColumnByKey("vmName").setHeader("Модель");
        grid.getColumnByKey("avgTime").setHeader("Средняя продолжительность аренды, час");
        add(grid);
        listRecords();
    }

    private void listRecords() {
        this.grid.setItems(repository.findAll());
    }
}
