package it.unisa.dottorato.presence;
import it.unisa.dottorato.account.Account;
import it.unisa.dottorato.exception.IdException;
import it.unisa.dottorato.phdCourse.Course;
import it.unisa.dottorato.phdCourse.Lesson;
import it.unisa.dottorato.utility.Utility;
import it.unisa.integrazione.database.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/** Classe per la gestione delle presenze
 *
 * @author Rembor
 */
public class PresenceManager {
    /**
     * I nomi delle tabelle
     */
  private static final String TABLE_PHDSTUDENT="phdstudent";
  private static final String TABLE_Lesson="lesson";
  private static final String TABLE_Presence="presence";
  private static final String TABLE_Course="course";
  private static PresenceManager instance;
   Presence checkPermission;
  /**
     * Il costruttore della classe e' dichiarato privato, per evitare
     * l'istanziazione di oggetti della classe .
     */
  private PresenceManager() {
        super();
    }
  
   /**
     * Metodo della classe incaricato della produzione degli oggetti, tale
     * metodo deve essere chiamato per restituire l'istanza del Singleton.
     * L'oggetto Singleton sara' istanziato solo alla prima invocazione del
     * metodo. Nelle successive invocazioni, invece, sara' restituito un
     * riferimento allo stesso oggetto.
     *
     * @return L'istanza della classe
     */
   public static synchronized PresenceManager getInstance() {
        if (instance == null) {
            instance = new PresenceManager();
        }
        return instance;
    }
   
   /**
    * Metodo della classe incaricato di inserire la presenza  di un dottorando  una lezione
    * 
     * @param Professor
     * @return ArrayList dei Corsi in cui insegna il Professore preso in considerazione
     * @throws SQLException 
     * @throws it.unisa.dottorato.presence.PhdStudentexception 
    */
   public ArrayList<Course> getCourseByProfessor(String Professor) throws SQLException, PhdStudentexception{
        //connessione al database
        Connection connect = DBConnection.getConnection();
        ArrayList<Course> corsi=null;
     try {
            /*
             *stringa SQL per effettuare l'inserimento nella 
             * tabella news
             */
            corsi=new ArrayList<>();
             String tSql = "SELECT course.* FROM course, lesson, keep, professor "
                    + PresenceManager.TABLE_Presence
                    + " where fkAccount=fkProfessor and fkLesson=IdLesson and fkCourse=IdCourse "
                    + " and fkAccount= '"+testDottorando(Professor)+"'";      
            
            ResultSet result = Utility.queryOperation(connect, tSql);

            while (result.next()) {
                Course corso=new Course();
                corso.setIdCourse(result.getInt("idCourse"));
                corso.setFkCurriculum(result.getString("fkCurriculum"));
                corso.setIdCourse(result.getInt("idCourse"));
                corso.setName(result.getString("name"));
                corso.setDescription(result.getString("description"));
                corso.setStartDate(result.getDate("startDate"));
                corso.setEndDate(result.getDate("endDate"));
                corsi.add(corso);
            }
            return corsi;
        }finally {
            DBConnection.releaseConnection(connect);
        }
    }
   

   /**
    * Metodo della classe incaricato di inserire la presenza  di un dottorando  una lezione
    * 
    * 
     * @param numbercic
     * @param fkCourse
    * @throws SQLException 
     * @throws it.unisa.dottorato.presence.PhdStudentexception 
     * @throws it.unisa.dottorato.exception.IdException 
    */
  public void insertPresence(int numbercic,int fkCourse) throws SQLException, PhdStudentexception, IdException{
        //connessione al database
        
     try (Connection connect = DBConnection.getConnection()) {
            /*
             *stringa SQL per effettuare l'inserimento nella 
             * tabella news
             */
             String tSql = "insert IGNORE presence (fkPhdstudent,fkLesson,isPresent) " +
         " select distinct phdstudent.fkAccount ,lesson.idLesson,false " +
         " from phdstudent,lesson,course,cycle " +
        " where phdstudent.fkCycle = " +numbercic
          +
          " and lesson.fkCourse= "+fkCourse;
             
            Utility.executeOperation(connect, tSql);
            connect.commit();
        }
    }

   
  
      
  
   
   
   
   
 
   /**metodo che dato un idCorso restituisce tutti i dottorandi che seguono quel corso
    * 
    * 
    * @param idCorso
    * @return
    * @throws ClassNotFoundException
    * @throws SQLException
    * @throws IOException
    * @throws IdException 
    */
   
   public synchronized ArrayList<Account> getPresenceDottorandi(int idCorso) throws 
           ClassNotFoundException, SQLException, IOException, IdException {
        Connection connect = null;
        Connection connect2 = null;
       Account corso = null ;
       ArrayList<Account> classList =null;
    
        try {
            connect2 = DBConnection.getConnection();
            classList = new ArrayList <>();
            String t="select * from lesson where lesson.fkCourse="+idCorso;
            ResultSet result2 = Utility.queryOperation(connect2, t);
            if(!result2.next())
                throw new IdException();
            
        // Otteniamo una Connessione al DataBase
            connect = DBConnection.getConnection();

            /*
             * Prepariamo la stringa SQL per la ricerca dei record 
             * nella tabella presence
           */
            String tSql = "SELECT distinct  account.name, account.surname, account.secondaryEmail" +
        " FROM presence, account, lesson " +
        " where presence.fkPhdstudent = account.secondaryEmail " +
         " and presence.fkLesson = lesson.idLesson " +
          " and lesson.fkCourse="+testid(idCorso);
            //Inviamo la Query al DataBase
            ResultSet result = Utility.queryOperation(connect, tSql);

       while (result.next()) {
                
              corso = new Account();
              
                   corso.setName(result.getString("name"));
                  corso.setSurname(result.getString("surname"));
                 corso.setSecondaryEmail(result.getString("secondaryEmail"));

                classList.add(corso);
            }

           
        }  finally {
            DBConnection.releaseConnection(connect);
            DBConnection.releaseConnection(connect2);
        }
        return classList;
        
    }
  /** metodo che passando il dottorando e il id corso restituisce 
   * le presenze a queste lezioni del dottorando
   * 
   * @param dottorando
   * @param idCorso
   * @return
   * @throws ClassNotFoundException
   * @throws SQLException
   * @throws IOException
   * @throws IdException 
     * @throws it.unisa.dottorato.presence.PhdStudentexception 
   */
   public synchronized ArrayList<Presence> getPresenceToLesson(String dottorando,int idCorso) throws 
           ClassNotFoundException, SQLException, IOException, IdException, PhdStudentexception {
         Connection connect = null;
         Presence presente= null;
         ArrayList<Presence> classList = null;
       
         try {
             classList=new ArrayList<>();
                     
             
            // Otteniamo una Connessione al DataBase
            connect = DBConnection.getConnection();

            /*
             * Prepariamo la stringa SQL per la ricerca dei record 
             * nella tabella presence
           */
            String tSql = "SELECT  presence.isPresent, presence.fkLesson, presence.fkPhdstudent " +
                            " FROM presence, lesson " +
                            " where presence.fkLesson = lesson.idLesson " +
                            " and lesson.fkCourse ="+testid(idCorso) +" and presence.fkPhdstudent = '"
                            +testDottorando(dottorando)+"'";
            //Inviamo la Query al DataBase
            ResultSet result = Utility.queryOperation(connect, tSql);

       while (result.next()) {
                presente =new Presence();
                presente.setIsPresent(result.getBoolean("isPresent"));
                presente.setFkLesson(result.getInt("fkLesson"));
                presente.setFkPhdstudent(result.getString("fkPhdstudent"));
                classList.add(presente);
            }

           
        }finally {
            DBConnection.releaseConnection(connect);
        }
        return classList;
        
    }
  
   /**  Metodo della classe incaricato di modificare una presenza
    *  @param dottorando
    *  @param  idLesson
     * @throws java.sql.SQLException
     * @throws it.unisa.dottorato.presence.PhdStudentexception
    * @throws IdException
     * @throws java.io.IOException
    */
   public void modifyPresence(String dottorando,int idLesson ) throws 
           SQLException, PhdStudentexception, IdException, IOException {
       Connection connect = null;
            try{
                connect = DBConnection.getConnection();
            /*
             * Prepariamo la stringa SQL per inserire un nuovo record 
             * nella tabella presenze
             */
            String tSql = "UPDATE "
                   +  PresenceManager.TABLE_Presence
                    +" set isPresent = "
                    + changeSignatura(dottorando,idLesson)
                     + " WHERE fkPhdstudent = '"
                    + testDottorando(dottorando)+"' "
                    + " and fkLesson = "
                    + testid(idLesson); 
            
            

            //Inviamo la Query al DataBase
            if(Utility.executeOperation(connect, tSql)==0)
                throw new PhdStudentexception();

            connect.commit();
        }finally {
            DBConnection.releaseConnection(connect);
        }
   }
   /** Metodo della classe incaricato di testare il parametro email del dottorando
    *
     * @param email email da testare
     * @return ritorna l' email del dottorando se non ci sono errori sul test del paramentro
     * @throws it.unisa.dottorato.presence.PhdStudentexception
   */
   public String testDottorando(String email) throws PhdStudentexception{
        if((email.length()<10) || (email.length()>50) || (!email.contains("@"))){
            
            throw new PhdStudentexception("l'email del dottorando e' sbagliata "); 
        }
        return email;
    }
   /**
    * 
    * @param id id della presenza da testare
    * @return ritorna l' id de non trova eccezioni
    * @throws IdException 
    */
   public int testid(int id) throws IdException {
        if(id<0 || id>999999){
            throw new IdException("l'id non puo' essere minore di 0");
        }
        return id;
    }  
    /** Metodo della classe che verifica se una presenza da parte di un dottorando ad 
     * una lezione è tru o false
     * 
     * @param dottorando email del dottornado di cui bisogna cambiare la presenza
     * @param idLesson lezione alla quale bisogna cambiare la presenza
     * @return ritorna true se la presenza è false o ritorna false se la presenza è true
     * @throws SQLException
     * @throws IOException
     * @throws PhdStudentexception
     * @throws IdException 
     */
    private  boolean changeSignatura(String dottorando,int idLesson)  throws SQLException, IOException, PhdStudentexception, IdException {
          boolean controllo=false;
        try (Connection connect = DBConnection.getConnection()) {
           
             String tSql = "SELECT isPresent FROM  "
                    + PresenceManager.TABLE_Presence
                    + " where fkPhdstudent = '"
                    + testDottorando(dottorando)+"' and fkLesson = "
                     + testid(idLesson);
            //Inviamo la Query al DataBase
             ResultSet result = Utility.queryOperation(connect, tSql);
            
            if (result.next()){
             controllo=result.getBoolean("isPresent");
               if(controllo!=true){  
                 
             controllo=true;}
            
             else controllo=false;
            }
            connect.commit();
            return controllo;
        }
    }
}
