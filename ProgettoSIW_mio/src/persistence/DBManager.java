package persistence;

import java.util.ArrayList;
import java.util.List;

import dao.UserDAO;

public class DBManager {
	// Host: sql8.freesqldatabase.com
	// Database name: sql8154408
	// Database user: sql8154408
	// Database password: SeJ9HdcTZJ
	// Port number: 3306
	private static DBManager instance = null;
	private UserDAO userDAO;
//	ContattoDAO contattoDao;
//	MessaggioDAO messaggioDao;
//	TelefonoDAO telefonoDao;


	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() {
		// DAOFactory daoFactory = new
		// MySQLDAOFactory("sql8.freesqldatabase.com", "sql8154408", "3306",
		// "sql8154408", "SeJ9HdcTZJ");

		DAOFactory daoFactory = new MySQLDAOFactory("10.0.0.2", "progetto_siw", "3306", "root", "root");

		this.userDAO = daoFactory.getUserDAO();

		// contattoDao = daoFactory.getContattoDAO();
		// messaggioDao = daoFactory.getMessaggioDAO();
		// telefonoDao = daoFactory.getTelefonoDAO();

	}
	
	public UserDAO getUserDAO(){
		return this.userDAO;
	}

//	public ContattoDAO getContattoDao() {
//		return contattoDao;
//	}
//
//	public TelefonoDAO getTelefonoDao() {
//		return telefonoDao;
//	}
//
//	public MessaggioDAO getMessaggioDao() {
//		return messaggioDao;
//	}

	// public List<Contatto> contatti = new ArrayList<Contatto>();
	// public List<Telefono> telefoni = new ArrayList<Telefono>();
	// public List<Messaggio> messaggi = new ArrayList<Messaggio>();
	// public List<ContattiTelefono> contattiTelefono = new
	// ArrayList<ContattiTelefono>();
}