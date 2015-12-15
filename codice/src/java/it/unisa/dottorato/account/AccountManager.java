package it.unisa.dottorato.account;

import it.unisa.dottorato.autenticazione.EmailException;
import it.unisa.dottorato.autenticazione.PasswordException;
import it.unisa.dottorato.utility.Utility;
import it.unisa.integrazione.database.DBConnection;
import it.unisa.integrazione.database.exception.ConnectionException;
import it.unisa.integrazione.database.exception.MissingDataException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**Classe per la gestione degli account
 *
 * @author ariemmov
 */
public class AccountManager {
    
    private static final String TABLE_PROFESSOR_STUDENT = "professor_student";
    private static final String TABLE_STUDENT = "PhdStudent";
    //istanza della classe
    private static AccountManager instance;

    /**
     * Metodo della classe incaricato della produzione degli oggetti, tale
     * metodo deve essere chiamato per restituire l'istanza del Singleton.
     * L'oggetto Singleton sara' istanziato solo alla prima invocazione del
     * metodo. Nelle successive invocazioni, invece, sara' restituito un
     * riferimento allo stesso oggetto.
     *
     * @return L'istanza della classe
     */
    public static AccountManager getInstance() {

        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;

    }
    private Object account;
    
    /** Metodo della classe incaricato di ricercare tutti gli account presenti
     * nella piattaforma
     * 
     * @return restituisce un array list di account di tutti gli account presenti
     * nella piattaforma
     */
    public ArrayList<Account> getAccountList() {
    Connection connect = null;
    
    try {
         ArrayList<Account> accounts = new ArrayList<>();
         //Connessione al database
         connect = DBConnection.getConnection();
          /*
             * stringa SQL per selezionare piu record 
             * nella tabella account
             */
         String sql = "SELECT * FROM account ";
                
         //esecuzione della query
         ResultSet result = Utility.queryOperation(connect, sql);
         while(result.next()){
             Account temp = new Account();
             temp.setName(result.getString("name"));
             temp.setSurname(result.getString("surname"));
             temp.setEmail(result.getString("email"));
             temp.setSecondaryEmail(result.getString("secondaryEmail"));
             temp.setTypeAccount(result.getString("typeAccount"));
             temp.setPassword(result.getString("password"));
             temp.setAdmin(result.getBoolean("isAdministrator"));
             accounts.add(temp);
         }
          return accounts;

         
    }   catch (SQLException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        DBConnection.releaseConnection(connect);
    }
    return null;
  }
    
    public ArrayList<String> viewProfile(Account pAccount) throws SQLException {
        Connection connect = null;
        
        String sql = "SELECT * from " + pAccount.getTypeAccount()
                + "WHERE fkAccount  = '" + pAccount.getSecondaryEmail() + "'";
        
        try {
        ArrayList<String> profile = new ArrayList<>();
        connect = DBConnection.getConnection();
        ResultSet rs = Utility.queryOperation(connect, sql);
        
        while(rs.next()) {
            if(pAccount.getTypeAccount().equals("phdstudent")) {
                profile.add(rs.getString("telephone"));
                profile.add(rs.getString("link"));
                profile.add(rs.getString("department"));
                profile.add(rs.getString("researchInterest"));
                profile.add(rs.getString("fkCurriculum"));
                profile.add(rs.getString("fkCycle"));
                profile.add(rs.getString("fkProfessor"));
                profile.add(rs.getString("fkAccount"));
            }
            
            if(pAccount.getTypeAccount().equals("professor")) {
                profile.add(rs.getString("link"));
                profile.add(rs.getString("department"));
                profile.add(rs.getString("fkAccount"));
            }
        }
        
        return profile;
        
    } catch (SQLException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        DBConnection.releaseConnection(connect);
    }
        
    return null;
 }
    
    public ArrayList<Account> getPhdStudents() {
            Connection connect = null;
           
            try {
         ArrayList<Account> students = new ArrayList<>();
         //Connessione al database
         connect = DBConnection.getConnection();
          /*
             * stringa SQL per selezionare piu record 
             * nella tabella account
             */
         String sql = "SELECT * FROM account "
                 +    "WHERE typeAccount = 'phdstudent'";
         //esecuzione della query
         ResultSet result = Utility.queryOperation(connect, sql);
         while(result.next()){
             PhdStudent temp = new PhdStudent();
             temp.setName(result.getString("name"));
             temp.setSurname(result.getString("surname"));
             temp.setEmail(result.getString("email"));
             temp.setSecondaryEmail(result.getString("secondaryEmail"));
             temp.setTypeAccount(result.getString("typeAccount"));
             temp.setPassword(result.getString("password"));
             temp.setAdmin(result.getBoolean("isAdministrator"));
             students.add(temp);
         }
         return students; 
    }   catch (SQLException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        DBConnection.releaseConnection(connect);
    }
    return null;
    }
    
    
        public ArrayList<Account> getProfessors() {
            Connection connect = null;
           
            try {
         ArrayList<Account> professors = new ArrayList<>();
         //Connessione al database
         connect = DBConnection.getConnection();
          /*
             * stringa SQL per selezionare piu record 
             * nella tabella account
             */
         String sql = "SELECT * FROM account "
                 +    "WHERE typeAccount = 'professor'";
         //esecuzione della query
         ResultSet result = Utility.queryOperation(connect, sql);
         while(result.next()){
             Professor temp = new Professor();
             temp.setName(result.getString("name"));
             temp.setSurname(result.getString("surname"));
             temp.setEmail(result.getString("email"));
             temp.setSecondaryEmail(result.getString("secondaryEmail"));
             temp.setTypeAccount(result.getString("typeAccount"));
             temp.setDepartment(result.getString("department"));
             temp.setLink(result.getString("link"));
             temp.setPassword(result.getString("password"));
             temp.setAdmin(result.getBoolean("isAdministrator"));
             professors.add(temp);
         }
         return professors; 
    }   catch (SQLException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        DBConnection.releaseConnection(connect);
    }
    return null;
    }
    
    
  /** Metodo della classe incaricato alla ricerca di un utente dato il suo nome
   * e il suo tipo di account
   * 
   * @param search il nome dell'utente da ricercare
   * @param type il tipo di account da ricercare
   * @return restituisce un array list di account di tutti gli utenti trovati, lancia un'eccezione altrimenti
   * @throws SQLException 
   */
  public ArrayList<Account> searchUser(String search, String type) throws SQLException {
      Connection connect = null;
      ArrayList<Account> accounts;
       /*
             * stringhe SQL per selezionare piu record 
             * nella tabella account
             */
      String sql = "SELECT * from account WHERE "
              + "name LIKE '%" + search + "%'" +
               "AND typeAccount = '" + type + "'";
      try {
          //connesione al database
          connect = DBConnection.getConnection();
          accounts = new ArrayList<>();
          ResultSet result =  Utility.queryOperation(connect, sql); 
          while(result.next()) {
             Account temp = new Account();   
             temp.setName(result.getString("name"));
             temp.setSurname(result.getString("surname"));
             temp.setEmail(result.getString("email"));
             temp.setSecondaryEmail(result.getString("secondaryEmail"));
             temp.setTypeAccount(result.getString("typeAccount"));
             temp.setPassword(result.getString("password"));
             temp.setAdmin(result.getBoolean("isAdministrator"));
             accounts.add(temp);

          }          
      } finally {
          DBConnection.releaseConnection(connect);
      }
      return accounts;
  }
      
      
      

    /**Metodo della classe incaricato dell'aggiornamento di un progilo
     * 
     * @param key email dell'account da modificare
     * @param pAccount le modifiche da apportare
     * @throws SQLException
     * @throws ConnectionException
     * @throws MissingDataException
     * @throws NullAccountException
     * @throws ProfileException
     * @throws PasswordException
     * @throws EmailException 
     */
    public void updateProfile(String key, Account pAccount) throws SQLException, ConnectionException,
            MissingDataException, NullAccountException, ProfileException, PasswordException, EmailException {
        try (Connection connect = DBConnection.getConnection()) {
            pAccount = testAccount(pAccount);

             /*
             * stringa SQL per aggiornare un record 
             * nella tabella account
             */
            String sql = "UPDATE account"
                + "set name = '"
                + Utility.Replace(testProfileData(pAccount.getName()))
                + "', surname = '"
                + Utility.Replace(testProfileData(pAccount.getSurname()))
                + "', password = '"
                + testPassword(pAccount.getPassword())
                + "', secondaryEmail = '"
                + testEmail(pAccount.getSecondaryEmail())
                + "WHERE email = '"
                + key + "'";
        
        String sql2 = "UPDATE "
                + pAccount.getTypeAccount();
        
        if(pAccount instanceof PhdStudent) {
            sql2 += " set telephone = '"
                    + testProfileData(((PhdStudent) pAccount).getTelephone())
                    + "', link =  '"
                    + testProfileData(((PhdStudent) pAccount).getLink())
                    + "', deparment = '"
                    + testProfileData(((PhdStudent) pAccount).getDepartment())
                    + "', researchInterest = '"
                    + testProfileData(((PhdStudent) pAccount).getResearchInterest())
                    + "' WHERE fkAccount = '"
                    + testProfileData(((PhdStudent) pAccount).getSecondaryEmail());
        }
        
        if(pAccount instanceof Professor) {
            sql2 += " set link = '"
                    + ((Professor) pAccount).getLink()
                    +"', set department = '"
                    + ((Professor) pAccount).getDepartment()
                    +"' WHERE fkAccount = '"
                    + ((Professor) pAccount).getSecondaryEmail()
                    +"'";
        }
                
       if(pAccount.getTypeAccount().equals("basic")) //aggiorna solo info base
                //esecuzione della query
               Utility.executeOperation(connect, sql);
       else {
           //esecuzione delle query
           Utility.executeOperation(connect, sql);
           Utility.executeOperation(connect, sql2);
        }
        
        connect.commit();

    }
  }
    
    
    /**Metodo della classe incaricato dell'aggiornamento di un tipo di un account
     * 
     * @param pAccount account da aggiornare
     * @param newType il nuovo tipo da inserire
     * @throws SQLException
     * @throws ConnectionException
     * @throws NullAccountException
     * @throws EmailException 
     */
    public void changeType(Account pAccount, String newType)
            throws SQLException, ConnectionException, NullAccountException, EmailException {
         /*
             * stringghe SQL per inserire/rimuovere piu record 
             * nella tabella account
             */
        String demotionSql = "DELETE FROM " // cancella vecchie info
                 + pAccount.getTypeAccount()
                 + " WHERE fkAccount = '"
                 + testEmail(pAccount.getSecondaryEmail()) + "'";
        
        String toProfessorSql = "INSERT INTO professor " //se nuovo professor
                +"(fkAccount,link,department)"
                + "VALUES ('"
                + testEmail(pAccount.getSecondaryEmail()) + "',"
                +"'',"
                +"''";
        
        String toPhdSql = "INSERT INTO phdstudent "
                + "(fkAccount,telephone,link,deparment,researchInterest,fkCycle"
                + "fkCurriculum, fkProfessor )" //nuovo dottorando
                + "VALUES ('"
                + testEmail(pAccount.getSecondaryEmail()) + "',"
                + "'',"
                + "'',"
                + "'',"
                + "'',"
                + "'',"
                + "'',"
                + "''";
        
        String changeTypeSql = "UPDATE account" //aggiorna il tipo
                +"set typeAccount = '" + newType
                + "' WHERE email = '" + pAccount.getEmail();
        
        Connection connect = null;
        try {
            //connessione al database
            connect = DBConnection.getConnection();
            pAccount = testAccount(pAccount);
            
            if(newType.equals("phdstudent") && pAccount.getTypeAccount().equals("basic")) {
                //esecuzione delle query
                Utility.executeOperation(connect, toPhdSql); //diventa un dottorando
                Utility.executeOperation(connect, changeTypeSql); //cambia tipo in account
            }
            else if(newType.equals("phdstudent") && pAccount.getTypeAccount().equals("professor")) {
                //esecuzione delle query
                Utility.executeOperation(connect, demotionSql); //perde info phd
                Utility.executeOperation(connect, toPhdSql); //nuove info prof
                Utility.executeOperation(connect, changeTypeSql);
            }
            else if(newType.equals("professor") && pAccount.getTypeAccount().equals("basic")) {
                //esecuzione delle query
                Utility.executeOperation(connect, toProfessorSql);
                Utility.executeOperation(connect, changeTypeSql);
            }
            else if(newType.equals("professor") && pAccount.getTypeAccount().equals("phdstudent")) {
                //esecuzione delle query
                Utility.executeOperation(connect, demotionSql);
                Utility.executeOperation(connect, toProfessorSql);
                Utility.executeOperation(connect, changeTypeSql);
            }
            else if(newType.equals("basic")) {
                //esecuzione delle query
                Utility.executeOperation(connect, demotionSql);
                Utility.executeOperation(connect, changeTypeSql);
            }
            
            
            
        } finally {
            DBConnection.releaseConnection(connect);
        }
        
        }
    
    /** Metodo della classe incaricato dell'invito degli utenti via email
     * 
     * @param email email a cui inviare l'invito
     * @throws SQLException
     * @throws EmailException 
     */
    //Dovrebbe inviare una mail, not tested
    public void inviteUser(String email) throws SQLException, EmailException {
        String to;
        to = testEmail(email);
        String from = "phdplatform@unisa.it";
        
        String host = "localhost"; //testing
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host",host);
        
        Session mailSession = Session.getDefaultInstance(properties);
        
        try {
            
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject("Sei stato invitato ad iscriverti a"
                    + " Phd-platform.");
            message.setText("localhost:8080/phd-platform/codice/register.jsp"); //test
            Transport.send(message);
        } catch(MessagingException ex) {
            ex.printStackTrace();
      }
    }
    
    
     /**
     * Metodo della classe incaricato dell'inserimento di una nuova entita'
     * nella tabella professor_student del database.
     *
     * @param tutor
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public synchronized void insertStudentTutor(Professor_student tutor) throws ClassNotFoundException, SQLException, IOException {
        Connection connect = null;
        try {
            // Otteniamo una Connessione al DataBase
            connect = DBConnection.getConnection();

            /*
             * Prepariamo la stringa SQL per inserire un nuovo record 
             * nella tabella professor_student
             */
            String tSql = "INSERT INTO "
                    + AccountManager.TABLE_PROFESSOR_STUDENT
                    + " (FK_Professor, FK_Student)"
                    + " VALUES ('"
                    + tutor.getFK_Professor()
                    + "','"
                    + tutor.getFK_Student()
                    + "')";

            //Inviamo la Query al DataBase
            Utility.executeOperation(connect, tSql);

            connect.commit();
        } finally {
            DBConnection.releaseConnection(connect);
        }
    }
    
     /**
     * Metodo della classe incaricato della ricerca delle informazioni del tutor
     * relativo a uno studente.
     *
     * @param idStudent
     * @return
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public synchronized Account getTutor(String idStudent) throws ClassNotFoundException, SQLException, IOException {
        Connection connect = null;
        try {
            Account professor = null;
            // Otteniamo una Connessione al DataBase
            connect = DBConnection.getConnection();

            /*
             * Prepariamo la stringa SQL per la ricerca di un record 
             * nella tabella professor_Student
             */
            String tSql = "SELECT * FROM "
                    + AccountManager.TABLE_STUDENT
                    + "  JOIN "
                    + AccountManager.TABLE_PROFESSOR_STUDENT
                    + " ON "
                    + AccountManager.TABLE_STUDENT
                    + "fkAccount = "
                    + AccountManager.TABLE_PROFESSOR_STUDENT
                    + ".FK_Professor "
                    + " WHERE FK_Student = '"
                    + idStudent + "'";

            //Inviamo la Query al DataBase
            ResultSet result = Utility.queryOperation(connect, tSql);

            while (result.next()) {
                professor = new Account();
                professor.setEmail(result.getString("ssn"));
                professor.setName(result.getString("name"));
                professor.setSurname(result.getString("surname"));
            }

            return professor;

        } finally {
            DBConnection.releaseConnection(connect);
        }

    }
    
    /**
     * Metodo della classe incaricato della modifica di un'entita' nella tabella
     * professor_student del database.
     *
     * @param tutor
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public synchronized void updateStudentTutor(Professor_student tutor) throws ClassNotFoundException, SQLException, IOException {
        try (Connection connect = DBConnection.getConnection()) {

            /*
             * Prepariamo la stringa SQL per modificare un record 
             * nella tabella professor_student
             */
            String tSql = "UPDATE "
                    + AccountManager.TABLE_PROFESSOR_STUDENT
                    + " set FK_Professor = '"
                    + tutor.getFK_Professor()
                    + "' WHERE FK_Student = '"
                    + tutor.getFK_Student() + "'";

            //Inviamo la Query al DataBase
            Utility.executeOperation(connect, tSql);

            connect.commit();
        }
    }
    
     /**
     * Metodo della classe incaricato della cancellazopme di un'entita' nella
     * tabella professor_student del database.
     *
     * @param idStudent
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public synchronized void deleteStudentTutor(String idStudent) throws ClassNotFoundException, SQLException, IOException {
        Connection connect = null;
        try {
            // Otteniamo una Connessione al DataBase
            connect = DBConnection.getConnection();

            /*
             * Prepariamo la stringa SQL per la cancellazione di un record 
             * nella tabella professor_student
             */
            String tSql = "DELETE FROM "
                    + AccountManager.TABLE_PROFESSOR_STUDENT
                    + " WHERE FK_Student = '"
                    + idStudent + "'";

            //Inviamo la Query al DataBase
            Utility.executeOperation(connect, tSql);

            connect.commit();
        } finally {
            DBConnection.releaseConnection(connect);
        }
    }
    /** Metodo della classe per il testing dell'account; verifica che l'account non sia 
     * <code>null</code>
     * 
     * @param account account da testare
     * @return restituisce l'account se non è nullo, lancia un'eccezione altrimenti
     * @throws NullAccountException 
     */
    public Account testAccount(Account account) throws NullAccountException {
        if(account == null)
            throw new NullAccountException();
        return account;
    }
    
    /** Metodo della classe per il testing dell'email; verifica che non sia una
     * stringa vuota o piu' lunga di 49 caratteri
     * 
     * @param email stringa da testare
     * @return restituisce la stringa se valida, lancia un'eccezione altrimenti
     * @throws EmailException 
     */
    public String testEmail(String email) throws EmailException {
        if(email.isEmpty() || email.length() > 50) 
            throw new EmailException();
        return email;
    }
    
     /** Metodo della classe per il testing della password; verifica che non sia una
     * stringa vuota o piu' lunga di 19 caratteri
     * 
     * @param pass stringa da testare
     * @return restituisce la stringa se valida, lancia un'eccezione altrimenti
     * @throws PasswordException 
     */
    public String testPassword(String pass) throws PasswordException {
        if(pass.isEmpty() || pass.length() > 20)
            throw new PasswordException();
        return pass;
    }
    
    /** Metodo della classe per il testing di una stringa; verifica se è vuota
     * 
     * @param data stringa da testare
     * @return restituisce la stringa se valida, lancia un'eccezione altrimenti
     * @throws ProfileException 
     */
    public String testProfileData(String data) throws ProfileException {
        if(data.isEmpty())
            throw new ProfileException();
        return data;
    }


  }


 

   
    
   
