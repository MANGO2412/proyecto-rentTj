package src;
import java.util.*;
import src.clases.*;
import javax.swing.JOptionPane;






public class application{
/*metodos de consola */
//--------------------------------------------------------------------------------------------------    
//limpia la consola
//  public  static void  clear(){
//     System.out.print("\033[H\033[2J");  
//     System.out.flush(); 
//   }
  
  // public static void clear(){
  //   try {
  //     String operatingSystem = System.getProperty("os.name");
      
  //     if(operatingSystem.contains("Windows")){        
  //       ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
  //       Process startProcess = pb.inheritIO().start();
  //       startProcess.waitFor();
  //     } else {
  //       ProcessBuilder pb = new ProcessBuilder("clear");
  //       Process startProcess = pb.inheritIO().start();

  //       startProcess.waitFor();
  //     } 
  //   } catch (Exception e) {
  //     //TODO: handle exception
  //      System.out.println(e);

  //   }
  // }
  // //detiene el programa en consola
  // public static void stop(){
  //   System.exit(0);
  // }

  //validar numeros
  static  int  validOption(){
    Scanner in = new Scanner(System.in);
    int num = 0;
    boolean skip = false;
    do {
      try {
        num = in.nextInt();
        skip = true;
      } catch (Exception e) {
        System.out.println("inserta la opcion en numero por favor >:(");
        in.nextLine();

      }
    } while (skip == false);

    return num;
  }
 //-------------------------------------------------------------------------------------------------

  /*metodo para imprimir el menu de login*/

    static User  logMessage(){
      //objetos  para el metodo
      console con = new console();
      User user = null;
      Scanner in = new Scanner(System.in);

      //datos de inicio para el metodo
      String username;
      String  contrasena;
      int option;
      //loop of menu
     do {
       System.out.println("*-------------BIENVENIDO A RENT-TJ----------------*");
       System.out.println("|1) Iniciar sesion                                |");
       System.out.println("|2) Registrarse como cliente, si no tiene cuenta. |");
       System.out.println("|3) Salir del programa                            |");
       System.out.println("*-------------------------------------------------*");
       System.out.println("Escribe una opcion del menu");
       System.out.print("/>");option = validOption();
       con.clear();

       if(option == 1){
         System.out.println("Escribe tu usuario:"); username = in.next();
         System.out.println("Escribe tu cuntraseña:"); contrasena = in.next();
                
         user = new User(username,contrasena);
         if(user.log()== true)
           break;
        
        con.clear();
       }else if(option == 2){
         Cliente cliente = new Cliente("cliente");
         cliente.register();
         con.clear();

       }else if(option == 3){
         con.clear();
         System.out.println("Gracias por visitar nuestro programa usuario :)");
         con.stop(); 
       }else{
        JOptionPane.showMessageDialog(null, "La opcion que escribiste es incorrecta :("); 
        con.clear();
       }
      
    } while (true);
    
    
     return user;
  }
//--------------------------------------------------------------------------------------------------


   /*application  of the rental*/
//---------------------------------------------------------------------------------------------------   
   //mensage  para el input
   static void inputM(String message,String inpuot){
      System.out.println(message);
      System.out.print(inpuot);
   }

   //menu principal del programa
   static void menuRental(String nameUser ){
      System.out.println("_________Menu del programa____________");
      System.out.println("|[1] Ver catalago y generar renta.   |");
      System.out.println("|[2] Mostrar tus rentas generadas.   |");
      System.out.println("|[3] Mostrar informacion del cuenta. |");
      System.out.println("|[4] salir.                          |");
      System.out.println("|____________________________________|");
      inputM("Escribe la opcion", nameUser+"/>");   
   }

   //programa para el cliente
   static  void appRenal(Cliente usuario){
    //variablles to the program work
     console con = new console();
     Catalogo catalogo = new Catalogo();
     Cliente cliente = usuario;
     Scanner in = new Scanner(System.in);
     int option;
     

     //message welcome
     System.out.println("Bienvenido "+cliente.getNombre()+" "+cliente.getApell());
     do {
      menuRental(cliente.getuserName());
      option = validOption();
       switch (option) {
         case 1:con.clear(); cliente.createRenta();con.clear();break;
         case 2:con.clear(); System.out.println("no tienes ninguna renta generada");break;
         case 3:con.clear();cliente.printInformation();break;
         case 4:
          con.clear();
          System.out.println("------->Hasta pronto "+cliente.getNombre()+" "+cliente.getApell()+"!>|<!");
          con.stop(); 
          break;
      
         default:JOptionPane.showMessageDialog(null, "la opcion que escribiste es incorrecta :("); con.clear(); break;
       }

     } while (true);    
   }

//----------------------------------------------------------------------------------------------------------------------
  static void menuAdmin(String nameUser){
    System.out.println("_________Menu de empleado_________________________");
    System.out.println("|1) Agregar  equipos.                             |");
    System.out.println("|2) Eliminar equipos.                             |");
    System.out.println("|3) Actualizar y ver inventario.                  |");
    System.out.println("|4) Actualizar y ver catalogo de equipos.         |");
    System.out.println("|5) Mostrar historial de cambios de catalogo.     |");
    System.out.println("|6) Salir                                         |");
    System.out.println("|_________________________________________________|");
    inputM("Escribe la opcion", nameUser+"/>");   
  }

  static void appAdmin(Empleado empleado){
    //Empleado employer = empleado;
    int option;
     
    System.out.println("Hola empleado "+empleado.getId());
    do {
     menuAdmin(empleado.getuserName());
     option = validOption();

     switch (option) {
      case 1: empleado.addInventory(); break;
      case 2: System.out.println("eliminar equipo");break;
      case 3: System.out.println("actualizar inventario"); break;
      case 4: empleado.changeCatolgy(); break;
     
      default:
         System.out.println("esa opcion no existe  en el menu");
        break;
     }
    } while (true);
  }



    
    
    public static void  main(String[] args){
      console con = new console();
      //ejecucion de los metodos estaticos
      con.clear();

      try {
       //creacion del objeto tipoUser
        User  tipoUser = logMessage();
        if(tipoUser.getTipo().equals("cliente")){
             con.clear();
           //ejecucio del programa del cliente
             appRenal(tipoUser.getCustomer());
        }else{
          con.clear();
          appAdmin(tipoUser.getEmployer());
        } 
      } catch (Exception e) {System.out.println(e);}
       
    }
}

    // String  getEntrDate(String date){
  
    //   return date;
    // }