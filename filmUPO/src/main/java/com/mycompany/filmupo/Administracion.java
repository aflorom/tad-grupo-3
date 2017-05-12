package com.mycompany.filmupo;

import DAO.DAO;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapping.Actor;
import mapping.Director;
import mapping.Pelicula;

/**
 * @autor: Grupo 3
 */
@Theme("mytheme")
@Title("FilmUPO")
@Widgetset("com.mycompany.filmupo.MyAppWidgetset")
public class Administracion extends UI {

    /**
     * Metodo de inicializacion de la clase
     *
     * @param vaadinRequest VaadinRequest
     */
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        try {
            HorizontalLayout v2h2 = new HorizontalLayout();
            v2h2.setMargin(true);

            final VerticalLayout v2h1 = new VerticalLayout();
            v2h1.setMargin(true);

            final VerticalSplitPanel v2 = new VerticalSplitPanel();
            v2.addComponent(v2h2);
            v2.addComponent(v2h1);
            v2.setSplitPosition(22, Sizeable.Unit.PERCENTAGE);
            v2.setLocked(true);

            VerticalLayout v1 = new VerticalLayout();
            v1.setMargin(true);

            HorizontalSplitPanel layout = new HorizontalSplitPanel();
            layout.addComponent(v1);
            layout.addComponent(v2);
            layout.setSplitPosition(28, Sizeable.Unit.PERCENTAGE);

            setContent(layout);

            //Creamos los 3 links de navegacion de la aplicacion y lo añadimos al layout creado anteriormente
            Link pri = new Link("Principal", new ExternalResource("/Principal"));
            Link est = new Link("Graficos", new ExternalResource("/Graficos"));
            Link adm = new Link("Administración", new ExternalResource("/Admin"));
            v2h2.addComponent(pri);
            v2h2.addComponent(new Label(" / "));
            v2h2.addComponent(est);
            v2h2.addComponent(new Label(" / "));
            v2h2.addComponent(adm);

            final DAO dao = new DAO();
            //Creamos conexion con la BBDD
            dao.abrirConexion();

            final List<Pelicula> listaPeliculas = dao.consultarPeliculas();
            final List<Director> listaDirectores = dao.consultarDirectores();
            final List<Actor> listaActores = dao.consultarActores();

            final BeanItemContainer<Director> bdir = new BeanItemContainer(Director.class, listaDirectores);

            Tree tree1 = new Tree("Administración:");
            //Declaramos el primer objeto de tipo Tree para mostrar en sus hijos todas las peliculas existentes
            String pel = "Peliculas";
            tree1.addItem(pel);
            for (Pelicula p : listaPeliculas) {
                tree1.addItem(p);
                tree1.setParent(p, pel);
                tree1.setChildrenAllowed(p, false);
            }
            Tree tree2 = new Tree("");
            //Declaramos el primer objeto de tipo Tree para mostrar en sus hijos todas los actores existentes
            String act = "Actores";
            tree2.addItem(act);
            for (Actor a : listaActores) {
                tree2.addItem(a);
                tree2.setParent(a, act);
                tree2.setItemCaption(a, a.getNombre() + " " + a.getApellidos());
                tree2.setChildrenAllowed(a, false);
            }
            Tree tree3 = new Tree("");
            //Declaramos el primer objeto de tipo Tree para mostrar en sus hijos todas los directores existentes
            String dic = "Directores";
            tree3.addItem(dic);
            for (Director d : listaDirectores) {
                if (d.getIdDirector() != 9) {
                    tree3.addItem(d);
                    tree3.setParent(d, dic);
                    tree3.setItemCaption(d, d.getNombre() + " " + d.getApellidos());
                    tree3.setChildrenAllowed(d, false);
                }
            }

            tree1.setSelectable(true);
            //Cuando pulsemos en algunas de las peliculas del arbol de las peliculas
            //se mostrara la informacion de la pelicula en textfield para que pueda ser modificada por
            //el usuario o que pueda eliminar la pelicula en cuestion
            tree1.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    try {
                        v2h1.removeAllComponents();
                        if ("Peliculas".equals(event.getProperty().getValue())) {

                            final TextField titulo = new TextField("Titulo");
                            titulo.setColumns(25);
                            v2h1.addComponent(titulo);

                            final TextField pais = new TextField("Pais");
                            v2h1.addComponent(pais);

                            final TextField anio = new TextField("Año");
                            v2h1.addComponent(anio);

                            final TextField genero = new TextField("Genero");
                            v2h1.addComponent(genero);

                            final TextField duracion = new TextField("Duracion");
                            v2h1.addComponent(duracion);

                            final TextArea sinopsis = new TextArea("Sinopsis");
                            sinopsis.setColumns(30);
                            v2h1.addComponent(sinopsis);

                            final TextField portada = new TextField("Portada");
                            portada.setColumns(30);
                            v2h1.addComponent(portada);

                            //Seleccion de director
                            final ComboBox director = new ComboBox("Director", bdir);
                            director.setItemCaptionPropertyId("nombreCompleto");
                            dao.abrirConexion();
                            v2h1.addComponent(director);

                            //Seleccion de actores
                            final TwinColSelect actores = new TwinColSelect();
                            List<Actor> actoresPeli = dao.consultarActores();
                            for (Actor a : listaActores) {
                                actores.addItem(a.getIdActor());
                                actores.setItemCaption(a.getIdActor(), a.getNombre() + " " + a.getApellidos());
                            }
                            actores.setRows(listaActores.size());
                            actores.setNullSelectionAllowed(true);
                            actores.setMultiSelect(true);
                            actores.setImmediate(true);
                            actores.setLeftColumnCaption("Actores disponibles");
                            actores.setRightColumnCaption("Actores seleccionados");
                            v2h1.addComponent(actores);
                            //Boton 'Añadir pelicula'
                            Button btnGuardarPelicula = new Button("Añadir Pelicula");
                            //Al pulsar en el el boton de añadir pelicula llamaremos al metodo insertar pelicula 
                            //y la insertaremos
                            btnGuardarPelicula.addClickListener(new Button.ClickListener() {
                                @Override
                                public void buttonClick(Button.ClickEvent event) {
                                    try {
                                        dao.abrirConexion();
                                        if (director.getValue() == null) {
                                            Notification.show("Error", "Seleccione el director",
                                                    Notification.Type.ERROR_MESSAGE);
                                        } else {
                                            int idPelicula = dao.insertarPelicula(director.getValue(), titulo.getValue(), Integer.parseInt(anio.getValue()), pais.getValue(), genero.getValue(), sinopsis.getValue(), Integer.parseInt(duracion.getValue()), portada.getValue());
                                            dao.insertarActorPelicula((Collection) actores.getValue(), idPelicula);
                                            Notification.show("Hecho", "La pelicula ha sido insertada correctamente",
                                                    Notification.Type.TRAY_NOTIFICATION);
                                        }
                                    } catch (SQLException | InstantiationException | IllegalAccessException ex) {
                                        Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            v2h1.addComponent(btnGuardarPelicula);

                        } else {
                            final Pelicula p = (Pelicula) event.getProperty().getValue();

                            final TextField titulo = new TextField("Titulo", p.getTitulo());
                            titulo.setColumns(25);
                            v2h1.addComponent(titulo);

                            final TextField pais = new TextField("Pais", p.getPais());
                            v2h1.addComponent(pais);

                            final TextField anio = new TextField("Año", Integer.toString(p.getAnio()));
                            v2h1.addComponent(anio);

                            final TextField genero = new TextField("Genero", p.getGenero());
                            v2h1.addComponent(genero);

                            final TextField duracion = new TextField("Duracion", Integer.toString(p.getDuracion()));
                            v2h1.addComponent(duracion);

                            final TextArea sinopsis = new TextArea("Sinopsis", p.getSinopsis());
                            sinopsis.setColumns(30);
                            v2h1.addComponent(sinopsis);

                            final TextField portada = new TextField("Portada", p.getImagen());
                            portada.setColumns(30);
                            v2h1.addComponent(portada);

                            Button button = new Button("Guardar");
                            //Mensaje mostrado como tipo Notification al haber realizado exitosamente un registro
                            button.addClickListener(new Button.ClickListener() {
                                @Override
                                public void buttonClick(Button.ClickEvent event) {
                                    try {
                                        dao.abrirConexion();

                                        dao.actualizarPelicula(p.getIdPelicula(), titulo.getValue(), Integer.parseInt(anio.getValue()), pais.getValue(), genero.getValue(), sinopsis.getValue(), Integer.parseInt(duracion.getValue()), portada.getValue());

                                        Notification.show("Hecho", "La pelicula ha sido actualizada correctamente",
                                                Notification.Type.TRAY_NOTIFICATION);

                                    } catch (SQLException | InstantiationException | IllegalAccessException ex) {
                                        Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            v2h1.addComponent(button);
                            //Boton 'Eliminar'
                            Button btnEliminar = new Button("Eliminar");
                            //Mensaje mostrado como tipo Notification al haber realizado exitosamente un registro
                            btnEliminar.addClickListener(new Button.ClickListener() {
                                @Override
                                public void buttonClick(Button.ClickEvent event) {
                                    try {
                                        dao.abrirConexion();
                                        dao.eliminarPelicula(p.getIdPelicula());
                                        Notification.show("Hecho", "La pelicula ha sido eliminada correctamente", Notification.Type.TRAY_NOTIFICATION);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (InstantiationException ex) {
                                        Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IllegalAccessException ex) {
                                        Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            v2h1.addComponent(btnEliminar);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            dao.cerrarConexion();
                        } catch (SQLException ex) {
                            Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            tree2.setSelectable(true);
            //Cuando pulsemos en algunas de los actores del arbol de los actores
            //se mostrara la informacion del actor en textfield para que pueda ser modificada por
            //el usuario o que pueda eliminar el actor en cuestion
            tree2.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    v2h1.removeAllComponents();
                    if ("Actores".equals(event.getProperty().getValue())) {

                        final TextField nombre = new TextField("Nombre");
                        nombre.setColumns(25);
                        v2h1.addComponent(nombre);

                        final TextField apellidos = new TextField("Apellidos");
                        apellidos.setColumns(25);
                        v2h1.addComponent(apellidos);
                        //Boton 'Añadir' actor
                        Button button = new Button("Añadir Actor");
                        //Mensaje mostrado como tipo Notification al haber realizado exitosamente un registro
                        button.addClickListener(new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                try {
                                    dao.abrirConexion();
                                    dao.insertarActor(nombre.getValue(), apellidos.getValue());
                                    Notification.show("Hecho", "El actor ha sido insertado correctamente", Notification.Type.TRAY_NOTIFICATION);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InstantiationException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        v2h1.addComponent(button);
                    } else {
                        final Actor a = (Actor) event.getProperty().getValue();
                        final TextField nombre = new TextField("Nombre", a.getNombre());
                        nombre.setColumns(25);
                        v2h1.addComponent(nombre);
                        final TextField apellidos = new TextField("Apellidos", a.getApellidos());
                        apellidos.setColumns(25);
                        v2h1.addComponent(apellidos);

                        Button button = new Button("Guardar");
                        //Mensaje mostrado como tipo Notification al haber actualizado exitosamente un registro
                        button.addClickListener(new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                try {
                                    dao.abrirConexion();
                                    dao.actualizarActor(a.getIdActor(), nombre.getValue(), apellidos.getValue());
                                    Notification.show("Hecho", "El actor ha sido actualizado correctamente", Notification.Type.TRAY_NOTIFICATION);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InstantiationException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        v2h1.addComponent(button);

                        Button btnEliminar = new Button("Eliminar");
                        //Mensaje mostrado como tipo Notification al haber realizado exitosamente una baja
                        btnEliminar.addClickListener(new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                try {
                                    dao.abrirConexion();
                                    dao.eliminarActor(a.getIdActor());
                                    Notification.show("Hecho", "El actor ha sido eliminado correctamente", Notification.Type.TRAY_NOTIFICATION);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InstantiationException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        v2h1.addComponent(btnEliminar);
                    }

                }
            });

            tree3.setSelectable(true);
            //Cuando pulsemos en algunas de los direcotres del arbol de los directores
            //se mostrara la informacion del actor en textfield para que pueda ser modificada por
            //el usuario o que pueda eliminar el director en cuestion
            tree3.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    v2h1.removeAllComponents();
                    if ("Directores".equals(event.getProperty().getValue())) {
                        final TextField nombre = new TextField("Nombre");
                        nombre.setColumns(25);
                        v2h1.addComponent(nombre);

                        final TextField apellidos = new TextField("Apellidos");
                        apellidos.setColumns(25);
                        v2h1.addComponent(apellidos);

                        Button button = new Button("Añadir Director");
                        //Mensaje mostrado como tipo Notification al haber realizado exitosamente un registro
                        button.addClickListener(new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                try {
                                    dao.abrirConexion();
                                    dao.insertarDirector(nombre.getValue(), apellidos.getValue());
                                    Notification.show("Hecho", "El director ha sido insertado correctamente", Notification.Type.TRAY_NOTIFICATION);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InstantiationException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        v2h1.addComponent(button);
                    } else {
                        final Director d = (Director) event.getProperty().getValue();
                        final TextField nombre = new TextField("Nombre", d.getNombre());
                        nombre.setColumns(25);
                        v2h1.addComponent(nombre);
                        final TextField apellidos = new TextField("Apellidos", d.getApellidos());
                        apellidos.setColumns(25);
                        v2h1.addComponent(apellidos);

                        Button button = new Button("Guardar");
                        //Mensaje mostrado como tipo Notification al haber sido actualizado exitosamente un registro
                        button.addClickListener(new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                try {
                                    dao.abrirConexion();
                                    dao.actualizarDirector(d.getIdDirector(), nombre.getValue(), apellidos.getValue());
                                    Notification.show("Hecho", "El director ha sido actualizado correctamente", Notification.Type.TRAY_NOTIFICATION);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InstantiationException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        v2h1.addComponent(button);

                        Button btnEliminar = new Button("Eliminar");
                        //Mensaje mostrado como tipo Notification al haber realizado exitosamente una baja
                        btnEliminar.addClickListener(new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                try {
                                    dao.abrirConexion();
                                    dao.eliminarDirector(d.getIdDirector());
                                    Notification.show("Hecho", "El director ha sido eliminado correctamente", Notification.Type.TRAY_NOTIFICATION);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InstantiationException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        v2h1.addComponent(btnEliminar);
                    }
                }
            });

            //Añadimos al layout los tres componentes de tipo Tree
            v1.addComponent(tree1);
            v1.addComponent(tree2);
            v1.addComponent(tree3);
            dao.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @WebServlet(urlPatterns = "/Admin/*", name = "Admin", asyncSupported = true)
    @VaadinServletConfiguration(ui = Administracion.class, productionMode = false, widgetset = "MyAppWidgetset")
    public static class MyUIServlet extends VaadinServlet {
    }
}
