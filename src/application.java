package src;
import java.util.*;
import src.clases.*;
import javax.swing.JOptionPane;


public class application{
/*metodos de consola */
//--------------------------------------------------------------------------------------------------    
//limpia la consola
  static void  clear(){
    System.out.print("\033[H\033[2J");  
    System.out.flush(); 
  }
  
  //detiene el programa en consola
  static void stop(){
    System.exit(0);
  }

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
       clear();

       if(option == 1){
         System.out.println("Escribe tu usuario:"); username = in.next();
         System.out.println("Escribe tu cuntraseña:"); contrasena = in.next();
                
         user = new User(username,contrasena);
         if(user.log()== true)
           break;
        
        clear();
       }else if(option == 3){
         clear();
         System.out.println("Gracias por visitar nuestro programa usuario :)");
         stop(); 
       }else{
        JOptionPane.showMessageDialog(null, "La opcion que escribiste es incorrecta :("); 
        clear();
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
   static void menuRental(String nameCliente,String apellC, String nameUser ){
      System.out.println("_________BIENVENIDO  "+nameCliente+" "+apellC+"____");
      System.out.println("|1)mostra  equipos de renta.         |");
      System.out.println("|2)mostrar las rentas generadas      |");
      System.out.println("|3)crear una renta                   |");
      System.out.println("|4)Mostrar informacion del cuenta    |");
      System.out.println("|5)salir                             |");
      System.out.println("|____________________________________|");
      inputM("Escribe la opcion", nameUser+"/>");   
   }

   //programa para el cliente
   static  void appRenal(Cliente usuario){
     Catalogo catalogo = new Catalogo();
     Cliente cliente = usuario;
     Scanner in = new Scanner(System.in);
     int option;
     
     do {
      menuRental(cliente.getNombre(),cliente.getApell(),cliente.getuserName());
      option = validOption();
       switch (option) {
         case 1:clear(); catalogo.getEquipments();  catalogo.printEquipments();;break;
         case 2:clear(); System.out.println("no tienes ninguna renta generada");break;
         case 3:clear(); System.out.println("no puedes crear rentas"); break;
         case 4:
           clear();
           cliente.printInformation(); 
            break;
         case 5:  
            clear();
            System.out.println("------->Hasta pronto "+cliente.getNombre()+" "+cliente.getApell()+"!>|<!");
            stop();    
          break;
         default:JOptionPane.showMessageDialog(null, "la opcion que escribiste es incorrecta :("); clear(); break;
       }

     } while (true);    
   }

//----------------------------------------------------------------------------------------------------------------------
    
    
    
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
      } catch (Exception e) { System.out.println(e);}
       
    }
}

