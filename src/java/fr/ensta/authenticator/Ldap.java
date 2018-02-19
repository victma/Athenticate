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
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.SimpleBindRequest;
/**
 *
 * @author eleve
 */
public class Ldap {
    private String userid;
    private LDAPConnection ldap;
    
    public String getUid() {
        return this.userid;
    }
    
    public void authenticate(String username, String password) throws LDAPException {
        LDAPConnection ldap = null;
        ldap = new LDAPConnection("localhost.localdomain", 1389);

        SearchResult sr = ldap.search("ou=People,dc=ensta,dc=fr", SearchScope.SUB, "(uid=" + username + ")");

        if (sr.getEntryCount() == 0) {
            System.out.println("KO");
            throw new LDAPException(ResultCode.OPERATIONS_ERROR);
        } else {
            String dn = sr.getSearchEntries().get(0).getDN();
            ldap = new LDAPConnection("localhost.localdomain", 1389, dn, password);
            System.out.print("connected \n");
            userid = username;
            this.ldap = ldap;
        }
    }
    
    public int update(String attribute, String value){
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
    public String getAttribute(String attribute) throws LDAPException
    {
        SearchRequest sr = new SearchRequest("ou=People,dc=ensta,dc=fr", SearchScope.SUB, "(uid="+userid+")", attribute);
      
        SearchResult searchResult;
      
        searchResult = ldap.search(sr);
        SearchResultEntry entry = searchResult.getSearchEntries().get(0);
        String retAttribute = entry.getAttributeValue(attribute);
        return retAttribute;
    }
    
    public static void main(String[ ] args) throws LDAPException
    {
        Ldap ldap = new Ldap();
        ldap.authenticate("vmalet", "malet");
        String mail = ldap.getAttribute("securityquestion");
        System.out.print(mail);
        ldap.update("securityquestion", "Nom de la mère de mouss ?");
    }
}
