package ru.cheranev.rental.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import ru.cheranev.rental.domain.ReportRentalHistory;
import ru.cheranev.rental.jpa.ReportRentalHistoryRepository;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;

/**
 * @author Cheranev N.
 * created on 20.05.2019.
 */
@Route(value = "reportRentalHistory", layout = MainLayout.class)
@PageTitle("Report Rental History")
public class ReportRentalHistoryView extends VerticalLayout {

    public static final String VIEW_TITLE = "Отчет \"История эксплуатации\"";
    public static final String VIEW_NAME = "reportRentalHistory";
    private static final String FILTER_PLACEHOLDER = "Фильтр";

    public ReportRentalHistoryView(ReportRentalHistoryRepository repository) {
        Grid<ReportRentalHistory> grid = new Grid<>();
        ListDataProvider<ReportRentalHistory> dataProvider = new ListDataProvider<>(repository.findAll());
        grid.setDataProvider(dataProvider);

        Grid.Column<ReportRentalHistory> typeNameColumn = grid
                .addColumn(ReportRentalHistory::getTypeName)
                .setComparator(Comparator.comparing(ReportRentalHistory::getTypeName))
                .setHeader("Вид ТС");
        Grid.Column<ReportRentalHistory> modelNameColumn = grid
                .addColumn(ReportRentalHistory::getModelName)
                .setComparator(Comparator.comparing(ReportRentalHistory::getModelName))
                .setHeader("Модель");
        Grid.Column<ReportRentalHistory> regNumberColumn = grid
                .addColumn(ReportRentalHistory::getRegNumber)
                .setComparator(Comparator.comparing(ReportRentalHistory::getRegNumber))
                .setHeader("Гос. Номер");
        Grid.Column<ReportRentalHistory> customerNameColumn = grid
                .addColumn(ReportRentalHistory::getCustomerName)
                .setComparator(Comparator.comparing(ReportRentalHistory::getCustomerName))
                .setHeader("Арендатор");
        Grid.Column<ReportRentalHistory> beginRentTimeColumn = grid
                .addColumn(new LocalDateTimeRenderer<>(ReportRentalHistory::getBeginRentTime,
                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)))
                .setComparator(Comparator.comparing(ReportRentalHistory::getBeginRentTime))
                .setHeader("Начало аренды");
        Grid.Column<ReportRentalHistory> endRentTimeColumn = grid
                .addColumn(new LocalDateTimeRenderer<>(ReportRentalHistory::getEndRentTime,
                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)))
                .setComparator(Comparator.comparing(ReportRentalHistory::getEndRentTime))
                .setHeader("Окончание аренды");

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField typeNameField = new TextField();
        typeNameField.addValueChangeListener(event -> dataProvider.addFilter(
                report -> StringUtils.containsIgnoreCase(report.getTypeName(),
                        typeNameField.getValue())));
        typeNameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(typeNameColumn).setComponent(typeNameField);
        typeNameField.setSizeFull();
        typeNameField.setPlaceholder(FILTER_PLACEHOLDER);

        TextField modelNameField = new TextField();
        modelNameField.addValueChangeListener(event -> dataProvider.addFilter(
                report -> StringUtils.containsIgnoreCase(report.getModelName(),
                        modelNameField.getValue())));
        modelNameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(modelNameColumn).setComponent(modelNameField);
        modelNameField.setSizeFull();
        modelNameField.setPlaceholder(FILTER_PLACEHOLDER);

        TextField regNumberField = new TextField();
        regNumberField.addValueChangeListener(event -> dataProvider.addFilter(
                report -> StringUtils.containsIgnoreCase(report.getRegNumber(),
                        regNumberField.getValue())));
        regNumberField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(regNumberColumn).setComponent(regNumberField);
        regNumberField.setSizeFull();
        regNumberField.setPlaceholder(FILTER_PLACEHOLDER);

        TextField customerNameField = new TextField();
        customerNameField.addValueChangeListener(event -> dataProvider.addFilter(
                report -> StringUtils.containsIgnoreCase(report.getCustomerName(),
                        customerNameField.getValue())));
        customerNameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(customerNameColumn).setComponent(customerNameField);
        customerNameField.setSizeFull();
        customerNameField.setPlaceholder(FILTER_PLACEHOLDER);

        TextField beginRentTimeField = new TextField();
        beginRentTimeField.addValueChangeListener(event -> dataProvider.addFilter(
                report -> StringUtils.containsIgnoreCase(report.getBeginRentTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                        beginRentTimeField.getValue())));
        beginRentTimeField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(beginRentTimeColumn).setComponent(beginRentTimeField);
        beginRentTimeField.setSizeFull();
        beginRentTimeField.setPlaceholder(FILTER_PLACEHOLDER);

        TextField endRentTimeField = new TextField();
        endRentTimeField.addValueChangeListener(event -> dataProvider.addFilter(
                report -> StringUtils.containsIgnoreCase(report.getEndRentTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                        endRentTimeField.getValue())));
        endRentTimeField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(endRentTimeColumn).setComponent(endRentTimeField);
        endRentTimeField.setSizeFull();
        endRentTimeField.setPlaceholder(FILTER_PLACEHOLDER);

        add(grid);
        setSizeFull();
    }
}
