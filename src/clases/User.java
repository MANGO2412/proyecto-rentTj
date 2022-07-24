package src.clases;
import java.sql.*;
import src.clases.*;


public class User {
   DataBase rentaEquipos = new DataBase();

   //attributes
    int id;
    String userName;
    String password;
    String tipo;
    int no_empleado;
    int no_cliente;
   
  public  User(String userName,String password){
        this.userName = userName;
        this.password = password;
   }

  public  String getuserName(){
    return userName;
  }
 
  public  String getTipo(){
   return tipo;
  }


  //method to login
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

  public Cliente getCustomer(){
     Cliente customer = null;

     //customer's attributes
      int idCustomer =0;
      String nameCustomer=null,apellCustomer=null,numTelCUstomer=null;

      try {
        String com = "SELECT *from clientes,users where users.id_user="+id +" and clientes.ID_cliente ="+no_cliente +";";
        ResultSet resultSet = rentaEquipos.getTableResults(com);
        while (resultSet.next()) {
          idCustomer = resultSet.getInt("ID_cliente");
          nameCustomer = resultSet.getString("nombre");
          apellCustomer = resultSet.getString("apell_paterno");
          numTelCUstomer = resultSet.getString("num_tel");
        }        

       
      } catch (SQLException e) {
        //TODO: handle exception
        e.printStackTrace();;
      }
    
    customer = new Cliente(idCustomer,nameCustomer,apellCustomer,numTelCUstomer,userName,password);
     return customer;
  }


  //method  to get empleyer
    

}
