/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonctionalCore;

import static fonctionalCore.DB_Link.cnx;
import static fonctionalCore.DB_Link.connecterDB;
import static fonctionalCore.DB_Link.rst;
import static fonctionalCore.DB_Link.st;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author camille
 */
public class Prescription {
    private String id_prescription;
    private String prescri;
    private String Ph;
    private String date;

    /**
     * @return the id_prescription
     */
    public String getId_prescription() {
        return id_prescription;
    }

    /**
     * @param id_prescription the id_prescription to set
     */
    public void setId_prescription(String id_prescription) {
        this.id_prescription = id_prescription;
    }

    /**
     * @return the prescri
     */
    public String getPrescri() {
        return prescri;
    }

    /**
     * @param prescri the prescri to set
     */
    public void setPrescri(String prescri) {
        this.prescri = prescri;
    }

    /**
     * @return the id_ph
     */
    public String getPh() {
        return Ph;
    }

    /**
     * @param id_ph the id_ph to set
     */
    public void setPh(String ph) {
        this.Ph = ph;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    
    public Prescription(){
        
    }
    
    public Prescription(String id_prescri,String prescri, String ph, String date){
        this.id_prescription=id_prescri;
        this.prescri=prescri;
        this.Ph=ph;
        this.date=date;
    }
    
/************************************************************
       AFFICHER LISTE DES PRESCRIPTION D'UN PATIENT          
 ***********************************************************/     
    
    public static ArrayList afficherPrescription(String ipp){     
        ArrayList<Prescription> listPrescription = new ArrayList();
        ArrayList<String> ListDmAne =new ArrayList();
        ArrayList<String> ListDmcli =new ArrayList();
        ArrayList<String> ListidPresciption =new ArrayList();
        try{ 
            String query="SELECT id_DMane FROM dmane WHERE IPP='"+ipp+"'";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            while (rst.next()){
                String IdDmAne=rst.getString("id_DMane");
                ListDmAne.add(IdDmAne);                  
            }
            
            query="SELECT id_DMcli FROM dmcli WHERE IPP='"+ipp+"'";
            cnx=connecterDB();
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            while (rst.next()){
                String IdDmcli=rst.getString("id_DMcli");
                ListDmcli.add(IdDmcli);              
            }
            
            for (String idDm : ListDmAne){
                query="SELECT id_prescription FROM prescription_iddmane WHERE id_DMane='"+idDm+"'";
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){
                    String Id_Pres=rst.getString("id_prescription");
                    ListidPresciption.add(Id_Pres);  
                    
                }
            }
            for (String idDm : ListDmcli){
                query="SELECT id_prescription FROM prescription_iddmcli WHERE id_DMcli='"+idDm+"'";                  
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){
                    String Id_Pres=rst.getString("id_prescription");
                    ListidPresciption.add(Id_Pres);                      
                }
            }
            
            for (String Id_pres : ListidPresciption){
                
                query="SELECT * FROM prescription WHERE id_prescription='"+Id_pres+"'";                 
                cnx=connecterDB();
                st=cnx.createStatement();
                rst=st.executeQuery(query);
                while (rst.next()){                    
                    
                    Prescription prescription = new Prescription(Id_pres,rst.getString("prescri"),null,rst.getString("date"));
                    String ph = PH.AfficherNomPH(rst.getString("id_Ph"));
                    prescription.setPh(ph);
                    listPrescription.add(prescription);                 
                }
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
        
        return listPrescription;
    }
           
}
