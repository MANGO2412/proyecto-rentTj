package src.clases;
import java.sql.*;
import src.clases.DataBase;


public class User {
   DataBase rentaEquipos = new DataBase();

   //attributes
   public int id;
   public String userName;
   public  String password;
   public String tipo;
   public int no_empleado;
   public int no_cliente;
   
  public  User(String userName,String password){
        this.userName = userName;
        this.password = password;
   }


  public  void log(){
      try {
        String com = "select * from users where username='"+userName+"'"+"AND"+" password='"+password+"';";
        ResultSet resultSet = rentaEquipos.getTableResults(com);
        while (resultSet.next()) {

          id = resultSet.getInt("id_user");
          tipo =resultSet.getString("tipo").trim();
          no_cliente =resultSet.getInt("cliente");
          no_empleado = resultSet.getInt("cliente");     
        }
      

        // resultSet.close();
      } catch (Exception e) {
         e.printStackTrace();
      }      
   }

   
  public  String getTipo(){
   return tipo;
  }

  public int getNoEmpleado(){
    return no_empleado;
  }

  public int getNoCliente(){
    return no_cliente;
  }

}
