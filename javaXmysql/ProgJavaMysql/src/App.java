
import java.sql.*;

import javax.print.DocFlavor.STRING;

public class App {
    public static void main(String[] args) throws Exception {

        //cargar el controlador jdbc
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

        //Enrutar la base de datos
        String DB_URL ="jdbc:mysql://localhost:3306/empresa_prueba";

        //configurar conexiones
        String USER = "root";
        String PASSWORD ="123456789*";

        //Variables para gestion de objetos de conexion y sentencias

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        try{

            //1. REGISTRAR EL DRIVER O CONTROLADOR JDBC
            Class.forName(JDBC_DRIVER);
            System.out.println("cargado y conectado al controlador gestor MySQL");

            //2. CONECTAR AL SERVIDOR MySQL A LA BASE DE DATOS
            //EMPRESA PRUEBA CON SU USUARIO Y CONTRASEÑA

            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("conectado exitosamente");

            //3. CREAR UNA TABLA 
            stmt = conn.createStatement();
            String sqlCreate = "create table Articulos ("+
            "id INT NOT NULL PRIMARY KEY,"+
            "nombre VARCHAR (100) NOT NULL,"+
            "cantidad INT NOT NULL)";

            //se ejecutara la sentencia sql contenida en
            //la variable sqlcreate
            stmt.executeUpdate(sqlCreate);

            //4. INSERTAR DATOS
            String sqlInsert = "INSERT INTO Articulos (id, nombre, cantidad)"+
            "VALUES (?,?,?)";

            pstmt = conn.prepareStatement(sqlInsert);

            // primer dato
            pstmt.setInt(1, 100);

            // segundo dato
            pstmt.setString(2, "Monitor");

            // tercer dato
            pstmt.setInt(3, 50);

            // EJECUTAR LA SNETENCIA COMPLETA CON SUS PARAMETROS 
            pstmt.executeUpdate();
            System.out.println("Registro exitoso");

            //stmt.executeUpdate("INSERT INTO Articulos (id, nombre, cantidad)"+
            //"VALUES ("+ id + ","+ nombre +","+ cantidad +")");

            // 5. CONSULTAR CON SELECT
            String sqlSelect = "select id, nombre, cantidad from Articulos"+
            "where cantidad > ?";

            pstmt = conn.prepareStatement(sqlSelect);

            pstmt.setInt(1, 20);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");

                System.out.println("id: "+id+" Nombre: "+nombre+"cantidad :"+cantidad);
            }


            rs.close();






        }catch(Exception ex){

            System.out.println("Error"+ ex.getMessage());


        }finally{


            conn.close();



        }


    }
}
