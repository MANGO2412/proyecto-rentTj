package src.clases;
import  src.clases.DataBase;
import java.util.ArrayList;
import java.sql.ResultSet;


 class Equipment{
    //attributes
    int id,inventario;
    String nombre,descr;
    double preciBase;
     
     Equipment(int id,int inventario,String nombre,String descr,double preciBase){
       this.id = id;
       this.inventario = inventario;
       this.nombre = nombre;
       this.descr = descr;
       this.preciBase = preciBase;
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

 public void getEquipments(){
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
      for (int i = 0; i < count; i++) {
           System.out.println(equipments.get(i).getDescr());
      }
   }


}


 



  
