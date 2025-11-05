
import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        //cargar el controlador jdbc
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

        //Enrutar la base de datos
        String DB_URL ="jdbc:mysql://localhost:3306/empresa_ejemplo";

         //configurar conexiones
         String USER = "root";
         String PASSWORD ="123456789*";

         // gestión de conexion con la variable conn
         Connection conn = null;
         // gestión de sp con variable cstmt
         CallableStatement cstmt = null;

         try{


             //1. REGISTRAR EL DRIVER O CONTROLADOR JDBC
             Class.forName(JDBC_DRIVER);
             System.out.println("cargado y conectado al controlador gestor MySQL");

             //2. CONECTAR AL SERVIDOR MySQL A LA BASE DE DATOS
            //EMPRESA PRUEBA CON SU USUARIO Y CONTRASEÑA

            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("conectado exitosamente");

            //Uso para gesstión de transferencias 
            conn.setAutoCommit(false);

            //---------Bloque para ingresar usando SP

            String sqlInsertSP = "{call sp_insertar_empleado(?,?,?,?,?)}";
            cstmt = conn.prepareCall(sqlInsertSP);

            //ingreso de un registro
            //PRIMER DATO ID DEL EMPLEADO
            cstmt.setInt(1,500);

            //SEGUNDO DATO EL NOMBRE DE EL EMPLEADO
            cstmt.setString(2, "Pedro");

            //TECER DATO APELLIDO EMPLEADO
            cstmt.setString(3, "Garcia");

            //CUARTO DATO SALARIO
            cstmt.setFloat(4, 100.00F);

            //QUINTO DATO ID_DEPARTAMENTO DEL EMPLEADO
            cstmt.setInt(5, 20);

            //Ejecutar el SP

            cstmt.executeUpdate();

            //Confirmar la transaccion de datos con el SP 

            conn.commit();

            //Ingreso de varios registros (RETO!!!)

            System.out.println("Registros ingresados con exito");

            //---------Bloque para consultar usando SP

            String sqlSelectSP = "{sp_consultar_empleado}";
            cstmt = conn.prepareCall(sqlSelectSP);

            cstmt.setInt(1, 500);

            //Verificar si hubo resultados o hay registros
            //O no hay registros
            Boolean verificarResultado = cstmt.execute();

            if(verificarResultado){
                

                //Extraiga esos registros que encontro
                ResultSet rs = cstmt.getResultSet();

                String nombreEmpleado, apellidoEmpleado;

                while (rs.next()){

                    nombreEmpleado =rs.getString("nombre");
                    apellidoEmpleado =rs.getString("nombre");

                    System.out.println("nombre del empleado" + nombreEmpleado + "apellido del empleado" + apellidoEmpleado);
                    

                }

                rs.close();

            }else{

                System.out.println("no existe el empleado");


            }


         }catch(Exception ex){

            System.out.println("Error:" + ex.getMessage());

         }finally{

            conn.close();
         }

    }
}
