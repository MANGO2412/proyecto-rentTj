package src.clases;
import java.sql.*;
import java.util.Scanner;

import src.clases.*;
import javax.swing.JOptionPane;


public class User {
   DataBase rentaEquipos = new DataBase();
   Scanner in = new Scanner(System.in);
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

   public  User(String userName,String password,String tipo){
    this.userName = userName;
    this.password = password;
    this.tipo = tipo;
  }

  public User(String tipo){
    this.tipo = tipo;
  } 



 //methods get 
  public  String getuserName(){
    return userName;
  }
 
  public  String getTipo(){
   return tipo;
  }



//--------------------------------------------------------------------------------

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


//------------------------------------------------------------------------------------------

  //metodo que revisa  si el usuario existe
  public boolean log(){
      boolean found = false;
      if(getTipo()!= null){
         found = true;
       }else{
        JOptionPane.showMessageDialog(null, "La contraseña o nombre de  usario es incorrecto vulve intentarlo.\n registrate si no cuentas con una cuenta. Si estas iniciando sesion como un empleado, \n notifica al departamento de informatica el porblema.");
       }      
       return found;
  }
  
//--------------------------------------------------------------------------------

/*methods to get employer and customer */

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

  /*methos to register new users*/
   //validates
   String  validateUserName(){
    String answer;    
    do {
      answer = in.next();
      try {
        ResultSet resultSet= rentaEquipos.getTableResults("select username from users");
        
        while (resultSet.next()) {
           if(answer.equals(resultSet.getString("username"))){
              System.out.println("el nombre de uusario que Escribiste ya existe :(, vuelve escribir otro nuevo");
              answer = "";
              break;  
           }
         
        }
   
       } catch (Exception e) {
         System.out.println(e);
       }      
    } while (answer.equals(""));
     
     return answer;
  }

  String validatePassword(){
    String answer; 
     do {
      answer = in.next();
       if(answer.length() ==12)
          break;
        else{
          System.out.println("tu contraseña no tiene 12 caracteres por tiene: "+answer.length());
          System.out.println("vuelve a escribir otra o gregale mas caracters");
          answer = "";
        }
     } while (answer.equals(""));
    return answer;
  }

  String validateNumTel(){
    long num = 0;
    String answer ="";
    boolean skip = false;
    do {
      do {
        try {
          num = in.nextLong();

          String tamano = String.valueOf(num); 
          if(tamano.length() == 10){
            skip = true;
          }else{
            System.out.println("tu numero de telefono debe de ser de 12 digitos");
          }
          
        } catch (Exception e) {
          System.out.println("inserte el numero de telefono en numero por favor >:(");
          in.nextLine();
        }
      } while (skip == false);

      answer+= num;
      try {
        ResultSet resultSet= rentaEquipos.getTableResults("select num_tel from clientes");
        
        while (resultSet.next()) {
           if(answer.equals(resultSet.getString("num_tel"))){
              System.out.println("Este numero ya existe, Escribe otro diferente a este por favor :)");
              answer = "";
              break;  
           }
         
        }
   
       } catch (Exception e) {
         System.out.println(e);
       }      

    } while (answer.equals(""));
   
    

    return answer;
  }

  

    int getLastIdUser(){
      int id_User = 0;
      try {
       String  com = "SELECT id_user from users";
       ResultSet resultSet = rentaEquipos.getTableResults(com);
       
       while (resultSet.next()) {
          if(resultSet.getInt("id_user") > id_User){
            id_User = resultSet.getInt("id_user");
          }     
        }
      } catch (Exception e) {
        // TODO: handle exception
        System.out.println("erroro en la linea 170 en la clase cliente"+e);
      }
     
      id_User = id_User+1;
      return id_User;
    }

   void register(String cmmd){
         try {
           rentaEquipos.insertData(cmmd);
         } catch (Exception e) {
            System.out.println("error en la linea 181 en la clase user"+e);
         }
   };




  //method  to get empleyer
   public Empleado getEmployer(){
     Empleado empleado = null;
      

     //customer's attributes
     int idEmployer =0;
     String nameEmployer=null,apellEmployer=null,numTelEmployer=null;

     try {
       String com = "SELECT *from empleados,users where users.id_user="+id +" and empleados.id_empleado ="+no_empleado +";";
       ResultSet resultSet = rentaEquipos.getTableResults(com);
       while (resultSet.next()) {
         idEmployer = resultSet.getInt("id_empleado");
         nameEmployer = resultSet.getString("nombre").trim();
         apellEmployer = resultSet.getString("apellid_pat").trim();
         numTelEmployer = resultSet.getString("numeroTelefono").trim();
       }        

      
     } catch (SQLException e) {
       e.printStackTrace();
     }

     
     empleado = new Empleado(idEmployer, nameEmployer, apellEmployer, numTelEmployer,getuserName(),password,getTipo());
     return empleado;
   }
    

}
