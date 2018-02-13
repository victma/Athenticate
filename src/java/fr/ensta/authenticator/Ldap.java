/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensta.authenticator;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.SimpleBindRequest;
/**
 *
 * @author eleve
 */
public class Ldap {

    public static LDAPConnection authenticate(String username, String password){
        
        
        LDAPConnection ldap = null;
        try {
            ldap = new LDAPConnection("localhost.localdomain", 1389);
        
            SearchResult sr = ldap.search("ou=People,dc=ensta,dc=fr", SearchScope.SUB, "(uid=" + username + ")");
       
            if (sr.getEntryCount() == 0) {
                System.out.println("KO");
            } else {
                String dn = sr.getSearchEntries().get(0).getDN();
                ldap = new LDAPConnection("localhost.localdomain", 1389, dn, password);
                System.out.print("connected \n");
            }
        } catch (LDAPException ex) { System.out.print(ex.getMessage() + "\n");}
        
        return ldap;
    }
    
    public static int update(LDAPConnection ldap, String attribute, String value){
        Modification mod = new Modification(ModificationType.REPLACE, attribute, value); 
        
        SimpleBindRequest request = (SimpleBindRequest)ldap.getLastBindRequest();
        if (request == null){ return -1;}
        String dn = request.getBindDN();
        
        try {
            
            
            LDAPResult result=ldap.modify(dn,mod);
        } catch (LDAPException ex) {
            System.out.print(ex.getMessage());
            return 1;
        }
      
      return 0;
    }
}
