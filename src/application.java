package src;
import java.sql.*;
import java.util.*;
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
    System.out.println("nos vemos :)");
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
      stop();
    }

    return user;
    }
    
    
    
    public static void  main(String[] args){
       User  tipoUser = logMessage();
       
      System.out.println(tipoUser.getTipo());
    }
}

