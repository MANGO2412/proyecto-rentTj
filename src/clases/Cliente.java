package src.clases;
import java.util.Scanner;
import java.sql.*;
//import com.mysql.cj.callback.UsernameCallback;
import src.clases.*;

public class Cliente extends User{
    //conecction wuth database
    Scanner in = new Scanner(System.in);
    DataBase rentaEquipos = new DataBase();
    
    int id;
    String nombre;
    String apell_paterno;
    String numTel;
    //attributes
    public Cliente(int id,String nombre,String apell_paterno,String numTel,String username,String contrasena){
      super(username,contrasena);
      this.id = id;
      this.nombre = nombre;
      this.apell_paterno = apell_paterno;
      this.numTel = numTel;
    }
  
    //method gets
    public String getNombre(){
      return nombre;
    }

    public String getApell(){
      return apell_paterno;
    }
     
   public String getNumTel(){
    return numTel;
   }

   //other methods
    public  void printState(){
        System.out.println(nombre);
    }  

    void createRenta(){

    }

      void showRental(){

      }

      void deleteRental(){

      }


      //later
      public void printInformation(){
        System.out.println("+---------informacion de la cuenta----------+");
        System.out.println("|>nombre de usuario:"+super.getuserName().trim()+". ");
        System.out.println("|>Nombre:"+getNombre().trim()+",                    ");
        System.out.println("|>apellido:"+getApell().trim()+",                   ");
        System.out.println("|>Numero de telefono:"+ getNumTel().trim()+".");
        System.out.println("+--------------------------------------------+");       
      }

      void changeInformation(){

      }
   
}
