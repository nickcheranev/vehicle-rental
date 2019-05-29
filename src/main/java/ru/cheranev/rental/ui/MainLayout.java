package ru.cheranev.rental.ui;

import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * @author Cheranev N.
 * created on 21.05.2019.
 */
@HtmlImport("css/shared-styles.html")
@Theme(value = Lumo.class)
public class MainLayout extends AbstractAppRouterLayout {

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu menu) {
        setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.CLOCK.create(), ReportAvgTimeView.VIEW_TITLE, ReportAvgTimeView.VIEW_NAME));
        setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.CHART.create(), ReportRentalHistoryView.VIEW_TITLE, ReportRentalHistoryView.VIEW_NAME));
        setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.INFO.create(), AboutView.VIEW_TITLE, AboutView.VIEW_NAME));
    }

    private void setMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }
}
