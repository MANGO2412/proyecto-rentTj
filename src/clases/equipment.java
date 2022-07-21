package src.clases;


public class equipment{
    //attributes
    int id;
    String name;
    String description;
    int amount;
    double price;


   public  equipment(int idValue, String nameValue, int amountValue,String des){
     id = idValue;
     name = nameValue;
     amount = amountValue;
     description = des;
     price = 23.4;
    }




    public void PrintStates(){
        System.out.println("Nombre del equipo: " + name);
        System.out.println("Descripcion del equipo: " + description);
        System.out.println("Cantidad de equipos: " + amount);
        System.out.println("precio por unidad: " + price);
    }

    //void get
    //void set 

}


 



  
