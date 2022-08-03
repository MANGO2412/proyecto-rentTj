package src.clases;

import java.io.Console;
import java.util.Scanner;

public class console {
   Console console = System.console();
   Scanner in = new Scanner(System.in);

  public void stop(){
       System.exit(0);
   }

   public void clear(){
    try {
        String operatingSystem = System.getProperty("os.name");
        
        if(operatingSystem.contains("Windows")){        
          ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
          Process startProcess = pb.inheritIO().start();
          startProcess.waitFor();
        } else {
          ProcessBuilder pb = new ProcessBuilder("clear");
          Process startProcess = pb.inheritIO().start();
  
          startProcess.waitFor();
        } 
      } catch (Exception e) {
        //TODO: handle exception
         System.out.println(e);
  
      }
   }

    public int validarOptionNumber(String value, String message){ 
         boolean confirm;
         int num = 0;

         do {
          confirm = true;
          try {
            num = Integer.parseInt(value);
          } catch (Exception e) {
            System.out.println(message); 
            value = console.readLine();
            confirm = false;           
          }
         } while (confirm == false);
      return num;
    }

    public String validateStrinUnNull(String value, String message){
      boolean confirm = true;

      do {

        if(value.trim().equals("")){
          System.out.println(message);
          value = console.readLine();
          confirm = false;
        }else{
          confirm=true;
        }
           
      } while (confirm == false);  
      return value;
    }
    
    public String validatScannerNext(String message, String meessageError){
        boolean anyError;
        String answer = "";

        do {
           anyError=true;
           

           try {
             System.out.println(message);
             answer = in.next();  
           } catch (Exception e) {
              System.out.println(meessageError);
              anyError = false;
           }      
         } while (anyError == false);
      
     return answer;
    }
    
}
