package src;
import java.sql.*;
import java.util.*;

import com.mysql.cj.protocol.Message;
import src.clases.*;



public class application{
  /*metodos de consola */
  
  //limpia la consola
  static void  clear(){
    System.out.print("\033[H\033[2J");  
    System.out.flush(); 
  }
  
  //detiene el programa en consola
  static void stop(){
    System.exit(0);
  }
  
   //metodo para imprimir el menu de login
    static User  logMessage(){
      //objetos  para el metodo
      User user = null;
      Scanner in = new Scanner(System.in);

      //datos de inicio para el metodo
      String username;
      String  contrasena;
      

      int option;


    do {
      System.out.println("*-------------BIENVENIDO A RENT-TJ----------------*");
      System.out.println("|1) Iniciar sesion                                |");
      System.out.println("|2) Registrarse como cliente, si no tiene cuenta. |");
      System.out.println("|3) Salir del programa                            |");
      System.out.println("*-------------------------------------------------*");
      System.out.println("Escribe una opcion del menu");
      System.out.print("/>");option = in.nextInt();
      clear();   
      
      
      switch (option) {
        case 1:
          System.out.println("Escribe tu usuario"); username = in.next();
          System.out.println("Escribe tu cuntraseña"); contrasena = in.next();
          
          user = new User(username,contrasena);
          user.log();
          break;
        
      
        default:
          break;
      }

      if( user.getTipo() != null){
        break;
      }else if(option != 3 || user.getTipo() == null){
        System.out.println("*--------------------------------ERROR AL INICIAR SESION :(------------------------------------------------");
        System.out.println("|>La contraseña o nombre de  usario es incorrecto vulve intentarlo, registrate si no cuentas con una cuenta.");
        System.out.println("|>Si esta iniciando sesion como un empleado, \n notifica al departamento de informatica para resaolver el problema.");
        System.out.println("*-----------------------------------------------------------------------------------------------------------*\n");
      }
           
    } while (option!=3);

    if(option == 3){
      clear();
      System.out.println("gracias por visitar nuestro programa usuario :)");
      stop();
    }

    return user;
    }

   /*application  of the rental*/
   //mensage  para el input
   static void inputM(String message,String inpuot){
      System.out.println(message);
      System.out.print(inpuot);
   }

   //menu principal del programa
   static void menuRental(String nameCliente,String apellC, String nameUser ){
      System.out.println("_________BIENVENIDO  "+nameCliente+" "+apellC+"____");
      System.out.println("|1)mostra  equipos de renta.         |");
      System.out.println("|2)mostrar las rentas generadas      |");
      System.out.println("|3)crear una renta                   |");
      System.out.println("|4)configuraraciones  de tu cuenta   |");
      System.out.println("|5)salir                             |");
      System.out.println("|____________________________________|");
      inputM("Escribe la opcion", nameUser+"/>");   
   }

   //programa para el cliente
   static  void appRenal(Cliente usuario){
     Cliente cliente = usuario;
     Scanner in = new Scanner(System.in);
     int option;
     
     do {
      menuRental(cliente.getNombre(),cliente.getApell(),cliente.getuserName());
      option = in.nextInt();
     } while (option != 5);

    if(option == 5){
      clear();
      System.out.println("hasta pronto "+cliente.getNombre()+" "+cliente.getApell());
      stop();
    }
   }
    
    
    
    public static void  main(String[] args){
      //ejecucion de los metodos estaticos
      try {
       //creacion del objeto tipoUser
        User  tipoUser = logMessage();
        if(tipoUser.getTipo().equals("cliente")){
           //ejecucio del programa del cliente
             clear();
             appRenal(tipoUser.getCustomer());
        }else{
           System.out.println("se ejecuto cuenta de empleado");
        } 
      } catch (Exception e) { System.out.println("gracias por visitar nuestro programa usuario :)");}
       
    }
}

