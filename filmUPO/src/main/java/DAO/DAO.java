package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import mapping.Actor;
import mapping.Director;
import mapping.Pelicula;

public class DAO {

    private Connection conn;

    public DAO() {
        this.conn = null;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void abrirConexion() throws InstantiationException, IllegalAccessException {

        //Conexion con BBDD utilizando JDBC
        String login = "root";
        String password = "";
        String url = "jdbc:mysql://localhost/filmupo";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.setConn(DriverManager.getConnection(url, login, password));
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    //Metodo para cerrar conexion BBDD
    public void cerrarConexion() throws SQLException {
        this.getConn().close();
    }

    //Metodo para la consulta de todas las peliculas de la BBDD
    public List<Pelicula> consultarPeliculas() throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula ORDER BY titulo");
        while (res.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }

    //Metodo para la consulta de todos los directores de la BBDD
    public List<Director> consultarDirectores() throws SQLException {
        final List<Director> listaDirectores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM director ORDER BY nombre");
        while (res.next()) {
            Director d = new Director(Integer.parseInt(res.getString("idDirector")), res.getString("nombre"), res.getString("apellidos"));
            listaDirectores.add(d);
        }
        res.close();
        stmt.close();
        return listaDirectores;
    }

    //Metodo para la consula de todos los actores de la BBDD
    public List<Actor> consultarActores() throws SQLException {
        List<Actor> listaActores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM actor ORDER BY nombre");
        while (res.next()) {
            Actor a = new Actor(Integer.parseInt(res.getString("idActor")), res.getString("nombre"), res.getString("apellidos"));
            listaActores.add(a);
        }
        res.close();
        stmt.close();
        return listaActores;
    }

    //Metodo para obtener un Director exacto segun su ID
    public Director devolverDirector(Integer idDirector) throws SQLException {
        Director d = null;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM director WHERE idDirector='" + idDirector + "'");
        while (res.next()) {
            d = new Director(Integer.parseInt(res.getString("idDirector")),
                    res.getString("nombre"),
                    res.getString("apellidos"));
        }
        res.close();
        stmt.close();
        return d;
    }

    //Metodo para devolver los actores dado el identificador de una pelicula
    public List<Actor> devolverActores(int idPelicula) throws SQLException {
        List<Actor> listaActores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM filmUPO.actorpelicula P join filmUPO.actor A on P.idActor = A.idActor WHERE P.idPelicula='" + idPelicula + "'");
        while (res.next()) {
            Actor a = new Actor(Integer.parseInt(res.getString("idActor")),
                    res.getString("nombre"),
                    res.getString("apellidos"));
            listaActores.add(a);
        }
        res.close();
        stmt.close();
        return listaActores;
    }

    //Metodo para la busqueda de peliculas segun un String pasado por el usuario
    public List<Pelicula> busqueda(String patron) throws SQLException {

        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula WHERE titulo LIKE '%" + patron + "%' or genero LIKE '%" + patron + "%' or pais LIKE '%" + patron + "%'");
        while (res.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }

    //Metodo para devolver una pelicula exacta dado su identificador
    public Pelicula devolverPelicula(Integer idPelicula) throws SQLException {
        Pelicula p = null;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula WHERE idPelicula='" + idPelicula + "'");
        while (res.next()) {
            p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"));
        }
        res.close();
        stmt.close();
        return p;
    }

    //Metodo para actualizar pelicula dado su identificador y sus demas atributos
    public void actualizarPelicula(int idPelicula, String titulo, int anio, String pais, String genero, String sinopsis, int duracion, String imagen) throws SQLException {
        String updateTableSQL = "UPDATE pelicula SET titulo='" + titulo + "', anio='" + anio + "', pais ='" + pais + "', genero='" + genero + "', sinopsis='" + sinopsis + "', duracion='" + duracion + "', imagen='" + imagen + "' WHERE idPelicula='" + idPelicula + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(updateTableSQL);
        preparedStatement.executeUpdate();
    }

    //Metodo para actualizar un actor dado su identificador, actualizamos su nombre y apellidos
    public void actualizarActor(int idActor, String nombre, String apellidos) throws SQLException {
        String updateTableSQL = "UPDATE actor SET nombre='" + nombre + "', apellidos='" + apellidos + "' WHERE idActor='" + idActor + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(updateTableSQL);
        preparedStatement.executeUpdate();
    }

    //Metodo para actualizar un director dado su identificador, nombre y apellidos
    public void actualizarDirector(int idDirector, String nombre, String apellidos) throws SQLException {
        String updateTableSQL = "UPDATE director SET nombre='" + nombre + "', apellidos='" + apellidos + "' WHERE idDirector = '" + idDirector + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(updateTableSQL);
        int retorno = preparedStatement.executeUpdate();
    }

    //Metodo para eliminar una pelicula dado su identificador
    public void eliminarPelicula(int idPelicula) throws SQLException {
        String deleteSQL = "DELETE FROM actorpelicula WHERE idPelicula='" + idPelicula + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(deleteSQL);
        int retorno = preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM pelicula WHERE idPelicula='" + idPelicula + "'";
        preparedStatement = this.getConn().prepareStatement(deleteSQL);
        retorno = preparedStatement.executeUpdate();
    }

    //Metodo para eliminar un actor dado su identificador
    public void eliminarActor(int idActor) throws SQLException {
        String deleteSQL = "DELETE FROM actorpelicula WHERE idActor='" + idActor + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(deleteSQL);
        int retorno = preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM actor WHERE idActor='" + idActor + "'";
        preparedStatement = this.getConn().prepareStatement(deleteSQL);
        retorno = preparedStatement.executeUpdate();
    }

    //Metodo para eliminar un director dado suidentificador
    public void eliminarDirector(int idDirector) throws SQLException {
        String SQL = "UPDATE pelicula SET idDirector='0' WHERE idDirector='" + idDirector + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(SQL);
        int retorno = preparedStatement.executeUpdate();

        SQL = "DELETE FROM director WHERE idDirector='" + idDirector + "'";
        preparedStatement = this.getConn().prepareStatement(SQL);
        retorno = preparedStatement.executeUpdate();
    }

    //Metodo para añadir una pelicula
    public int insertarPelicula(Object idDirector, String titulo, int anio, String pais, String genero, String sinopsis, int duracion, String imagen) throws SQLException {
        String SQL = "INSERT INTO pelicula VALUES (0, '" + idDirector + "', '" + titulo + "', "
                + "'" + anio + "', '" + pais + "', '" + genero + "', '" + sinopsis + "', '" + duracion + "', "
                + "'" + imagen + "')";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        int retorno = preparedStatement.executeUpdate();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        int res = 0;
        if (rs.next()) {
            res = rs.getInt(1);
        }
        return res;
    }

    //Metodo para añadir un actor a una pelicula
    public void insertarActorPelicula(Collection idsActores, int idPelicula) throws SQLException {
        for (Iterator it = idsActores.iterator(); it.hasNext();) {
            int idActor = (int) it.next();
            String insertTableSQL = "INSERT INTO actorpelicula VALUES (0, '" + idActor + "', '" + idPelicula + "')";
            PreparedStatement preparedStatement = this.getConn().prepareStatement(insertTableSQL);
            int retorno = preparedStatement.executeUpdate();
        }
    }

    //Metodo para añadir un actor
    public void insertarActor(String nombre, String apellidos) throws SQLException {
        String SQL = "INSERT INTO actor VALUES (0, '" + nombre + "', '" + apellidos + "')";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        int retorno = preparedStatement.executeUpdate();
    }

    //Metodo para añadir un director
    public void insertarDirector(String nombre, String apellidos) throws SQLException {
        String SQL = "INSERT INTO director VALUES (0, '" + nombre + "', '" + apellidos + "')";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        int retorno = preparedStatement.executeUpdate();
    }

    //Metodo para obtener el numero de peliculas que ha echo un determinado actor, esto nos servira para las graficas
    public int numPeliculasA(Integer idActor) throws SQLException {
        int num = 0;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT count(*) FROM actorpelicula WHERE idActor='" + idActor + "'");
        if (res.next()) {
            num = Integer.parseInt(res.getString("count(*)"));
        }
        res.close();
        stmt.close();
        return num;
    }

    //Metodo para obtener los tipos de generos de cada pelicula para utilizarlo luego en las graficas
    public int numGeneros(String genero) throws SQLException {
        int num = 0;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT count(*) FROM pelicula WHERE genero='" + genero + "'");
        if (res.next()) {
            num = Integer.parseInt(res.getString("count(*)"));
        }
        res.close();
        stmt.close();
        return num;
    }

    //Metodo para obtener el numero de peliculas realizadas por un director, metodo utilizado para las graficas de estadisiticas
    public int numPeliculasD(Integer idDirector) throws SQLException {
        int num = 0;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT count(*) FROM pelicula WHERE idDirector='" + idDirector + "'");
        if (res.next()) {
            num = Integer.parseInt(res.getString("count(*)"));
        }
        res.close();
        stmt.close();
        return num;
    }
}
