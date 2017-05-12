package com.mycompany.filmupo;

import DAO.DAO;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapping.Actor;
import mapping.Director;
import mapping.Pelicula;

@Theme("mytheme")
@Title("FilmUPO")
@Widgetset("com.mycompany.filmupo.MyAppWidgetset")
public class Main extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        //Layout para el campo busqueda
        HorizontalLayout h1v1 = new HorizontalLayout();
        h1v1.setMargin(true);
        //Layout para el campo con los links
        HorizontalLayout h1v2 = new HorizontalLayout();
        h1v2.setMargin(true);

        //Panel para almacenar los dos anteriores layout
        final HorizontalSplitPanel h1 = new HorizontalSplitPanel();
        h1.addComponent(h1v1);
        h1.addComponent(h1v2);
        h1.setSplitPosition(60, Sizeable.Unit.PERCENTAGE);

        h1.setLocked(true);

        //Layout que contendra la tabla
        final HorizontalLayout h2 = new HorizontalLayout();
        h2.setMargin(true);

        VerticalLayout v1 = new VerticalLayout();
        v1.setMargin(true);

        VerticalSplitPanel v2 = new VerticalSplitPanel();
        v2.addComponent(h1);
        v2.addComponent(h2);
        v2.setSplitPosition(22, Sizeable.Unit.PERCENTAGE);

        HorizontalSplitPanel layout = new HorizontalSplitPanel();
        layout.addComponent(v1);
        layout.addComponent(v2);
        layout.setSplitPosition(0, Sizeable.Unit.PERCENTAGE);

        setContent(layout);

        //Lista para recoger mas tarde las peliculas
        List<Pelicula> listaPeliculas = new ArrayList();

        final DAO dao = new DAO();

        try {
            //Abrimos conexion de BBDD
            dao.abrirConexion();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //Recogemos las peliculas de BBDD
            listaPeliculas = dao.consultarPeliculas();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dao.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Creamos una tabla con las columnas: Portada, Titulo, Año, Pais, Duracion
        Table table = new Table();

        table.addContainerProperty("Portada", Image.class, null);
        table.addContainerProperty("Titulo", String.class, null);
        table.addContainerProperty("Año", Integer.class, null);
        table.addContainerProperty("Pais", String.class, null);
        table.addContainerProperty("Duracion", Integer.class, null);

        table.setPageLength(table.size());
        table.setSizeFull();
        table.setSelectable(true);

        for (Pelicula p : listaPeliculas) {
            Image portada = new Image();
            final ExternalResource externalResource = new ExternalResource(p.getImagen());
            portada.setSource(externalResource);
            portada.setWidth("230");
            table.addItem(new Object[]{portada, p.getTitulo(), p.getAnio(), p.getPais(), p.getDuracion()}, p.getIdPelicula());
        }

        //Metodo para que cuando pinchemos en cualquier fila de la tabla se muestre
        //una ventana con la informacion detallada de esa pelicula
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                try {
                    dao.abrirConexion();
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                Pelicula p = null;
                Director d = null;
                List<Actor> a = null;
                try {
                    p = dao.devolverPelicula((Integer) event.getProperty().getValue());
                    d = dao.devolverDirector(p.getIdDirector());
                    a = dao.devolverActores((Integer) event.getProperty().getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                final Window window = new Window("Información detallada:");
                window.setWidth(700.0f, Sizeable.Unit.PIXELS);
                final FormLayout content = new FormLayout();
                Label datos = new Label(
                        "Pelicula del " + p.getAnio() + "," + p.getDuracion() + "min.," + p.getPais() + "<br>"
                        + "<b>Genero:</b> " + p.getGenero() + "<br>"
                        + "<b>Director:</b> " + d.getNombreCompleto() + "<br>"
                        + "<b>Titulo orginal:</b> " + p.getTitulo() + "<br>"
                        + "<b>Sinopsis:</b> " + p.getSinopsis() + "<br>"
                        + "<b>Protagonistas:</b><br> ");
                datos.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                content.addComponent(datos);
                for (Actor ac : a) {
                    Label datos2 = new Label("- " + ac.getNombre() + " " + ac.getApellidos() + "</br>");
                    datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                    datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                    content.addComponent(datos2);
                }
                content.setMargin(true);
                window.setContent(content);
                window.center();
                window.setModal(true);
                window.setResizable(false);
                window.setClosable(true);
                UI.getCurrent().addWindow(window);
            }
        });

        //Declaramos el boton buscar y lo añadimos al layout declarado al principio del codigo
        final TextField buscar = new TextField();
        h1v1.addComponent(buscar);
        Button button1 = new Button("Buscar");
        button1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                h2.removeAllComponents();
                //Creamos una nueva tabla para mostrar las peliculas que coincidan con la busqueda
                Table table2 = new Table();

                table2.addContainerProperty("Portada", Image.class, null);
                table2.addContainerProperty("Titulo", String.class, null);
                table2.addContainerProperty("Año", Integer.class, null);
                table2.addContainerProperty("Pais", String.class, null);
                table2.addContainerProperty("Duracion", Integer.class, null);

                List<Pelicula> pel = new ArrayList<>();

                try {
                    dao.abrirConexion();
                    pel = dao.busqueda(buscar.getValue());
                } catch (SQLException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        dao.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for (Pelicula p : pel) {
                    Image portada = new Image();
                    final ExternalResource externalResource = new ExternalResource(p.getImagen());
                    portada.setSource(externalResource);
                    portada.setWidth("230");
                    table2.addItem(new Object[]{portada, p.getTitulo(), p.getAnio(), p.getPais(), p.getDuracion()}, p.getIdPelicula());
                }

                table2.setPageLength(table2.size());
                table2.setSelectable(true);
                table2.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent event) {
                        try {
                            dao.abrirConexion();
                        } catch (InstantiationException | IllegalAccessException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Pelicula p = null;
                        Director d = null;
                        List<Actor> a = null;
                        try {
                            p = dao.devolverPelicula((Integer) event.getProperty().getValue());
                            d = dao.devolverDirector(p.getIdDirector());
                            a = dao.devolverActores((Integer) event.getProperty().getValue());
                        } catch (SQLException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        final Window window = new Window("Información detallada:");
                        window.setWidth(700.0f, Sizeable.Unit.PIXELS);
                        final FormLayout content = new FormLayout();
                        //Metodo para que cuando pinchemos en cualquier fila de la tabla se muestre
                        //una ventana con la informacion detallada de esa pelicula
                        Label datos = new Label(
                                "Pelicula del " + p.getAnio() + "," + p.getDuracion() + "min.," + p.getPais() + "<br>"
                                + "<b>Genero:</b> " + p.getGenero() + "<br>"
                                + "<b>Director:</b> " + d.getNombreCompleto() + "<br>"
                                + "<b>Titulo orginal:</b> " + p.getTitulo() + "<br>"
                                + "<b>Sinopsis:</b> " + p.getSinopsis() + "<br>"
                                + "<b>Protagonistas:</b><br> ");
                        datos.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                        content.addComponent(datos);
                        for (Actor ac : a) {
                            Label datos2 = new Label("- " + ac.getNombre() + " " + ac.getApellidos() + "</br>");
                            datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                            datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                            content.addComponent(datos2);
                        }
                        content.setMargin(true);
                        window.setContent(content);
                        window.center();
                        window.setModal(true);
                        window.setResizable(false);
                        window.setClosable(true);
                        UI.getCurrent().addWindow(window);
                    }
                }
                );
                h2.addComponent(table2);
            }
        }
        );
        h1v1.addComponent(button1);

        h2.addComponent(table);

        //Creamos los 3 links de navegacion de la aplicacion y loa añadimos al layout declarado al principio del codigo
        Link pri = new Link("Principal", new ExternalResource("/Principal"));
        Link est = new Link("Graficos", new ExternalResource("/Graficos"));
        Link adm = new Link("Administración", new ExternalResource("/Admin"));
        h1v2.addComponent(pri);
        h1v2.addComponent(new Label(" / "));
        h1v2.addComponent(est);
        h1v2.addComponent(new Label(" / "));
        h1v2.addComponent(adm);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Main.class, productionMode = false, widgetset = "com.mycompany.filmupo.MyAppWidgetset")
    public static class MyUIServlet extends VaadinServlet {
    }
}
