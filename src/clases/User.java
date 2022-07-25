package src.clases;
import java.sql.*;
import src.clases.*;
import javax.swing.JOptionPane;


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
        this.tipo = validateUser(userName,password);
   }




  public  String getuserName(){
    return userName;
  }
 
  public  String getTipo(){
   return tipo;
  }


  //method to login
   String validateUser(String usernameV,String passwordV){
     tipo = null;
    try{
      String command = "select *from users where username='"+usernameV+"' and"+" password='"+passwordV+"'";
      ResultSet resultSet= rentaEquipos.getTableResults(command);

      while (resultSet.next()) {
        if(this.userName.equals(resultSet.getString("username"))){
          tipo=resultSet.getString("tipo"); 
          id = resultSet.getInt("id_user");
          no_cliente = resultSet.getInt("cliente");
          no_empleado = resultSet.getInt("empleado");
        }
      }
    }catch(Exception e){
        e.printStackTrace();
    }
    return tipo;
   }


  public boolean log(){
      boolean found = false;
      if(getTipo()!= null){
         found = true;
       }else{
        JOptionPane.showMessageDialog(null, "La contraseña o nombre de  usario es incorrecto vulve intentarlo.\n registrate si no cuentas con una cuenta. Si estas iniciando sesion como un empleado, \n notifica al departamento de informatica el porblema.");
       }      
       return found;
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
          nameCustomer = resultSet.getString("nombre").trim();
          apellCustomer = resultSet.getString("apell_paterno").trim();
          numTelCUstomer = resultSet.getString("num_tel").trim();
        }        

       
      } catch (SQLException e) {
        e.printStackTrace();;
      }
    
    customer = new Cliente(idCustomer,nameCustomer,apellCustomer,numTelCUstomer,userName,password);
     return customer;
  }


  //method  to get empleyer
    

}
