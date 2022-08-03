package src.clases;
import  src.clases.DataBase;
import java.util.ArrayList;
import java.sql.ResultSet;



 class Equipment{
   
    //attributes
    DataBase rentaEquipos = new DataBase();
    int id,inventario,dis,cant_ren,cant_pedida;
    String nombre,descr;
    double preciBase;
     
     Equipment(int id,int inventario,String nombre,String descr,double preciBase){
      //atributos del equipo
       this.id = id;
       this.nombre = nombre;
       this.descr = descr;
       this.preciBase = preciBase;
       
       //atributos del  inventario del equipo
       this.inventario = inventario;
       dis = validateDisp();
       cant_ren =validateCantRent();
       cant_pedida=0;
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

    
    int getDisp(){
      return dis;
    }

    int getCant_ren(){
      return cant_ren;
    }  

    int getCant_pedida(){
      return cant_pedida;
    }


    //methods set
    public void setCantPedida(int cant_pedida){
      this.cant_pedida=cant_pedida;
    }
    public void setName(String nombre){
       this.nombre = nombre;
      
       rentaEquipos.setData("update equipos set nombre='"+this.nombre+"' where id_equipo="+getId());
    }

    public void setPrecioBase(double preciBase){
        this.preciBase = preciBase;
        rentaEquipos.setData("update equipos set precio_base='"+this.preciBase+"' where id_equipo="+getId());
    }
    
    public void setDescr(String descr){
      this.descr = descr;
      rentaEquipos.setData("update inventarios set descripcion='"+this.descr+"' where id_equipo="+getId());
    }

    public void setDis(int disp){
     this.dis = this.dis- disp;
     rentaEquipos.setData("update inventarios set cant_disp="+this.dis+", last_update=now() where id_inv="+getInventario());
    }


    public void setCantRen(int cant_rent){
      this.cant_ren = this.cant_ren + cant_rent;
      rentaEquipos.setData("update inventarios set cant_rent="+this.cant_ren+",last_update=now() where id_inv="+getInventario());
    }

    public void setCant_pedida(int amount){
      cant_pedida = amount;
    }

    //method validate
    int validateDisp(){
      int disp = 0;
      try {
          ResultSet rs = rentaEquipos.getData("select cant_disp from inventarios where id_inv="+getInventario());  
            while (rs.next()) {
               disp = rs.getInt("cant_disp");
            }
               
      } catch (Exception e) {System.out.println(e);}
      return disp;
    }

    int validateCantRent(){
      int cant_rent =0;

      try {
         ResultSet rs = rentaEquipos.getData("select * from inventarios");
         while (rs.next()) {
          if(rs.getInt("id_inv")== this.inventario){
            cant_rent = rs.getInt("cant_rent");
            break;
          }
               
         }
      } catch (Exception e) {System.out.println(e); }
      return cant_rent;
    };

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
   
   //method gets
   public ArrayList<Equipment> getEq(){
      return equipments;
   }

   public int getCount(){
      return count;
   }

   public void storeEquipments(){
    int codeEquip = 0,numInvent = 0;
    String nameEquip = null,  descrEquip = null;
    double precioBase = 0.0D;

    try {
        ResultSet rs = rentaEquipos.getData("select *from equipos ORDER by id_equipo;");
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

   
   public void printEquipments(int index){
         System.out.printf("+-------------------------+%n");
         System.out.printf("|%-25s|%n", equipments.get(index).getNombre());
         System.out.printf("+-------------------------+%n%n");
         System.out.println(">id: "+equipments.get(index).getId());
         System.out.println(">Precio por dia: "+equipments.get(index).getPreciBas()+"\n");
         System.out.println(">Descripcion: "+ equipments.get(index).getDescr());
         System.out.print(equipments.get(index).getInventario());
         System.out.println("\n\n");
      }



   public Equipment getEquipment(int idV,String nameE,int index){ 
     Equipment user = null;
     for (int i = 0; i < count; i++) {
       if(equipments.get(i).getId() == idV || nameE.equals(equipments.get(i).getNombre())|| index >= 0){
          user =  equipments.get(i);
          break;
       }
     }
     
     return user;
   }

   public int createIdPed(){
    int idRentEquip = 2100;

    try {
       ResultSet rs = rentaEquipos.getData("select id_rent_equip from rent_equip");
       while (rs.next()) {
        if(rs.getInt("id_rent_equip")>idRentEquip){
          idRentEquip = rs.getInt("id_rent_equip");
        }
        idRentEquip = idRentEquip +1;
       }
    } catch (Exception e) {System.out.println(e); }
    return idRentEquip;
   }

   //  public static void main(String[] args) {
   //     Catalogo catalogo = new Catalogo();
   //     catalogo.printEquipments();
   // }

}


 



  
