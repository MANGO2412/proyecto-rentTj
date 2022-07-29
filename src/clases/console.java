package src.clases;

public class console {
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
    
}
