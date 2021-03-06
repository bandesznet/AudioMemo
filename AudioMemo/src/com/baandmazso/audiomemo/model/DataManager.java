package com.baandmazso.audiomemo.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.stmt.QueryBuilder;

public class DataManager {
	public static final String SHAREDPREF_NAME = "audiomemo";
	private static DataManager dm = null;
	private Context context = null;
	private SharedPreferences sharedpref = null;
	private DatabaseHelper databaseHelper = null;

	public SharedPreferences getSharedprefs() {
		return sharedpref;
	}
	
	public static DataManager getInstance(Context context) {
		if (dm == null) {
			dm = new DataManager();
			if (dm.context == null) {
				dm.context = context;
			}
			if (dm.sharedpref == null) {
				dm.sharedpref = dm.context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
			}
		}
		return dm;
	}

	public DatabaseHelper getDatabaseHelper() {
		openDatabaseHelper();
		return dm.databaseHelper;
	}

	public DatabaseHelper openDatabaseHelper() {
		if (dm.databaseHelper == null) {
			dm.databaseHelper = OpenHelperManager.getHelper(dm.context, DatabaseHelper.class);
		}
		return dm.databaseHelper;
	}

	public void closeDatabaseHelper() {
		if (dm.databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			dm.databaseHelper = null;
		}
	}
	
	public static Context getContext() {
		return dm.context;
	}

	public Card getCard(String domain_name) {
		/*try {
			Dao<Card, Integer> dao = dm.getDatabaseHelper().getCardDao();
			QueryBuilder<Card, Integer> querybuilder = dao.queryBuilder();
			querybuilder.where().eq(Card.FIELD_DOMAIN_NAME, domain_name);
			querybuilder.limit(1l);
			return dao.queryForFirst(querybuilder.prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return null;
	}

	public boolean addDomain(Card domain) {
		/*try {
			Dao<Card, Integer> dao = dm.getDatabaseHelper().getCardDao();
			CreateOrUpdateStatus status = dao.createOrUpdate(domain);
			if (status.isCreated() || status.isUpdated()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return false;
	}
	
	public void saveGame(Game game) throws SQLException {
		Dao<Card, Integer> cardDao = getDatabaseHelper().getCardDao();
		Dao<Game, Integer> gameDao = getDatabaseHelper().getGameDao();
		Dao<Player, Integer> playerDao = getDatabaseHelper().getPlayerDao();
		Dao<Table, Integer> tableDao = getDatabaseHelper().getTableDao();
		Dao<Pair, Integer> pairDao = getDatabaseHelper().getPairDao();
		
		tableDao.createOrUpdate(game.getTable());
		
		gameDao.createOrUpdate(game);
		
		ArrayList<Player> players = (ArrayList<Player>) game.getPlayers();
		for (Player player : players) {
			playerDao.createOrUpdate(player);
		}
		
		ArrayList<Card> cards = (ArrayList<Card>) game.getTable().getCards();
		for (Card card : cards) {
			cardDao.createOrUpdate(card);
		}
		
		ArrayList<Pair> pairs = (ArrayList<Pair>) game.getTable().getPairs();
		for (Pair pair : pairs) {
			pairDao.createOrUpdate(pair);
		}
	}
	
	public void addUser(User user) throws SQLException {
		Dao<User, Integer> userDao = getDatabaseHelper().getUserDao();

		userDao.create(user);
		
	}
	
	public User getUser(int userID) throws SQLException {
		Dao<User, Integer> userDao = getDatabaseHelper().getUserDao();

		QueryBuilder<User, Integer> builder = userDao.queryBuilder();
		builder.where().eq(User.FIELD_ID, userID);
		
		// növekvőbe kérjük le a legfitalabb usert
		builder.orderBy(User.FIELD_BIRTH_YEAR, true);
		
		// max 1 lekérése
		builder.limit(1l);
		
		return userDao.queryForFirst(builder.prepare());
	}
	
	// legfiatalabb user lekérése a megadott névvel
	public User getYoungestUser(String user_name) throws SQLException {
		Dao<User, Integer> userDao = getDatabaseHelper().getUserDao();

		QueryBuilder<User, Integer> builder = userDao.queryBuilder();
		builder.where().eq(User.FIELD_NAME, user_name);
		
		// növekvőbe kérjük le a legfitalabb usert
		builder.orderBy(User.FIELD_BIRTH_YEAR, true);
		
		// max 1 lekérése
		builder.limit(1l);
		
		return userDao.queryForFirst(builder.prepare());
	}
	
	public List getUsers() throws SQLException{
		 List<User> list;
		 Dao<User, Integer> userDao = getDatabaseHelper().getUserDao();
		 list = userDao.queryForAll();
		return list;	
	};
	
	//Felhasználó törlése
	public void deleteUser(User user) throws SQLException{
		 Dao<User, Integer> userDao = getDatabaseHelper().getUserDao();
		 userDao.deleteById(user.getId());
	};

}
