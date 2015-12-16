package com.hp.it.util;

import java.net.URL;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jason
 *
 */
public class LdapConnection {
	
	public LdapContext context = null;
	
	private static Logger log = LoggerFactory.getLogger(LdapConnection.class);
	
	
	private static void loadCertificate() {
		URL u = Thread.currentThread().getContextClassLoader().getResource("java6_cacerts");
		String path;
		if(u == null){
			path = Configure.get("cerpath");
		}else{
			path = u.getPath();
		}
			log.info(path);
			System.setProperty("javax.net.ssl.trustStore", path);
	}
	
	public LdapConnection(String contextFactory, String providerUrl, String securityPrincipal, String securityCredentials) throws NamingException{
		log.info("check the certificate file");
		loadCertificate();
    	Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		env.put(Context.PROVIDER_URL, providerUrl);
		//env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
		//env.put(Context.SECURITY_CREDENTIALS, securityCredentials);
		log.info("get the ldap connection!");
		context = new InitialLdapContext(env,null);
		log.info("LdapConnection: ldap connection successful!");
	}
    
	public NamingEnumeration search(String baseDn, String filter){
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		try {
			NamingEnumeration results;
			results = context.search(baseDn, filter, searchControls);
			return results;
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException("LdapConnection: ldap search exception!", e);
		}
	}
	
	
	public void closeConnection(){
			try {
				if(context != null){
					context.close();
					log.info("LdapConnection: ldap connection have closed!");
				}
			} catch (NamingException e) {
				e.printStackTrace();
				throw new RuntimeException("LdapConnection: context.close() exception!", e);
			}
	}
    public static void main(String[] args) throws NamingException{    	
    	//LdapConnection con = new LdapConnection(Configure.get("initContextFactory"), Configure.get("providerUrl"), "uid=xi-yaoh@hp.com,ou=People,o=hp.com", "hxy@1988");
    	
    	
    	LdapContext context = null;
    	NamingEnumeration results = null;
		
		loadCertificate();
    	Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, Configure.get("initContextFactory"));
		env.put(Context.PROVIDER_URL, Configure.get("providerUrl"));
		//env.put(Context.SECURITY_PRINCIPAL, "uid=xi-yaoh@hp.com,ou=People,o=hp.com");
		//env.put(Context.SECURITY_CREDENTIALS, "hxy@1988");
		
		context = new InitialLdapContext(env,null);
//		results = context.search("ou=People,o=hp.com", "(uid=xi-yaoh@*)", new SearchControls());
//			while (results.hasMoreElements()) {
//	            SearchResult searchResults = (SearchResult) (results.next());
//	            if(searchResults.getAttributes().get("uid") != null && searchResults.getAttributes().get("uid").toString().lastIndexOf("@hp")>0){
//	            	String uid = searchResults.getAttributes().get("uid").toString();
//	            	 System.out.println(searchResults.getAttributes().get("uid"));
//	 	            System.out.println("============00000000000000=============");
//	            }
//	            System.out.println("33333");
//	            System.out.println(searchResults.getAttributes().get("baseDn"));
//	        }
			System.out.println(results.hasMoreElements());
			
		
    }
}
