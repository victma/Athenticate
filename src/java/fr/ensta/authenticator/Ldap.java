/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensta.authenticator;
import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.unboundid.ldap.sdk.ModifyDNRequest;
import com.unboundid.ldap.sdk.ModifyRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author eleve
 */
public class Ldap {

    public static LDAPConnection authenticate(String username, String password){
        
        
        LDAPConnection ldap = null;
        try {
            ldap = new LDAPConnection("localhost.localdomain", 1389);
        
            SearchResult sr = ldap.search("ou=people,dc=ensta,dc=fr", SearchScope.SUB, "(uid=" + username + ")");
       
            if (sr.getEntryCount() == 0) {
                System.out.println("KO");
            } else {
                String dn = sr.getSearchEntries().get(0).getDN();
                ldap = new LDAPConnection("localhost.localdomain", 1389, dn, password);
            }
        } catch (LDAPException ex) { System.out.print(ex.getMessage() + "\n");}
        
        return ldap;
    }
    
    public static int updatemail(LDAPConnection ldap, String email){
      Modification mod = new Modification(ModificationType.REPLACE,"email",email); 
      //ModifyRequest modifyRequest = new ModifyRequest("sAMAccountName=jhh,ou=SysAdmin,dc=jhh,dc=com", mod);  
        try {
            LDAPResult result=ldap.modify("uid=ochabrol,ou=people,dc=ensta,dc=fr",mod);
        } catch (LDAPException ex) {
            System.out.print(ex.getMessage());
            return 1;
        }
      
      return 0;
    }
    
    
    /**l
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LDAPConnection ldap = authenticate("ochabrol", "chabrol");
        try { 
            ldap = new LDAPConnection("localhost.localdomain", 1389, "cn=Directory Manager,dc=ensta,dc=fr", "password");
            updatemail(ldap,"chabrol@ensta.fr");
        } catch( LDAPException e) {System.out.print("connexion as root failed:" + e.getMessage() +"\n");}
        
        
    }
    
}
