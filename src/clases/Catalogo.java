package src.clases;
import  src.clases.DataBase;
import java.util.ArrayList;
import java.sql.ResultSet;


 class Equipment{
    //attributes
    DataBase rentaEquipos;
    int id,inventario;
    String nombre,descr;
    double preciBase;
     
     Equipment(int id,int inventario,String nombre,String descr,double preciBase){
       this.id = id;
       this.inventario = inventario;
       this.nombre = nombre;
       this.descr = descr;
       this.preciBase = preciBase;
       rentaEquipos = new DataBase();
    }

    //methods get
     int  getId(){
        return id;
    }

     int getInventario(){
        return inventario;
    }

     String getNombre(){
        return nombre;
    }


     String getDescr(){  
        return descr;
    }

   double getPreciBas(){
    return preciBase;
    }


    //methods set
    public void setName(String nombre){
       this.nombre = nombre;
      
       rentaEquipos.insertData("update equipos set nombre='"+this.nombre+"' where id_equipo="+getId());
    }

    public void setPrecioBase(double preciBase){
        this.preciBase = preciBase;
        rentaEquipos.insertData("update equipos set precio_base='"+this.preciBase+"' where id_equipo="+getId());
    }
    
    public void setDescr(String descr){
      this.descr = descr;
      rentaEquipos.insertData("update equipos set descripcion='"+this.descr+"' where id_equipo="+getId());
    }

}

public class Catalogo{
    DataBase rentaEquipos;
    ArrayList<Equipment> equipments;
    int count;

   public Catalogo(){
      rentaEquipos = new DataBase();
      equipments = new ArrayList<Equipment>();
      count = 0;
   }

   void storeEquipments(){
    int codeEquip = 0,numInvent = 0;
    String nameEquip = null,  descrEquip = null;
    double precioBase = 0.0D;

    try {
        ResultSet rs = rentaEquipos.getTableResults("select *from equipos ORDER by id_equipo;");
        while (rs.next()) {
           codeEquip = rs.getInt("id_equipo");
           numInvent = rs.getInt("inventario");
           nameEquip = rs.getString("nombre").trim();
           descrEquip = rs.getString("descripcion").trim();
           precioBase = rs.getDouble("precio_base");

          equipments.add(new Equipment(codeEquip, numInvent, nameEquip, descrEquip, precioBase));
          count++;
         }
      
        rs.close();
      } catch (Exception e) {
        System.out.println("en el archivo Catalogo en la linea 62  "+e);
      }  
   }

   
   public void printEquipments(){
      storeEquipments();
      for (int i = 0; i < count; i++) {

         System.out.printf("+-------------------------+%n");
         System.out.printf("|%-25s|%n", equipments.get(i).getNombre());
         System.out.printf("+-------------------------+%n%n");
         System.out.println(">id: "+equipments.get(i).getId());
         System.out.println(">Precio por dia: "+equipments.get(i).getPreciBas()+"\n");
         System.out.println(">Descripcion: "+ equipments.get(i).getDescr());
         System.out.println("\n\n");
      }
   }

   public Equipment getEquipment(int idV,String name){        
      Equipment user = null;
     for (int i = 0; i < count; i++) {
       if(equipments.get(i).getId() == idV || equipments.get(i).getNombre() == name){
          user =  equipments.get(i);
          break;
       }
     }

     return user;
   }

}


 



  
