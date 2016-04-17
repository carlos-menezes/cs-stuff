/*==============================================================================

    Systems Software Project

    Social Music Server

    Barnaby Keene 2016

==============================================================================*/


package socialmusicserver;

import java.util.Vector;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManagerDB
{
	public final String userTable = "users";

	private Vector<User> Users;
	private Connection c;
	
	private PreparedStatement addUser;
	private PreparedStatement remUser;
	private PreparedStatement chkUser;
	private PreparedStatement logUser;

	public void init()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:data.db");

			try (Statement stmt = c.createStatement())
			{
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "+userTable+" (" +
					"uuid INT PRIMARY KEY NOT NULL," +
					"name TEXT NOT NULL,"+
					"pass TEXT NOT NULL,"+
					"loca TEXT,"+
					"brth TEXT,"+
					"info TEXT )");
			}

			addUser = c.prepareStatement("INSERT INTO "+userTable+" (name, pass) VALUES(?, ?)");
			remUser = c.prepareStatement("DELETE FROM "+userTable+" WHERE name = ?");
			chkUser = c.prepareStatement("SELECT count(*) FROM "+userTable+" WHERE name = ? COLLATE NOCASE");
			logUser = c.prepareStatement("SELECT * FROM "+userTable+" WHERE name=? COLLATE NOCASE");
		}
		catch (ClassNotFoundException | SQLException e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}

		System.out.println("UserManager class initialised, database opened successfully.");
	}

	public boolean AccountExists(String name)
	{
		try
		{
			chkUser.executeQuery();

			ResultSet r = chkUser.getResultSet();

			return r.getInt(1) > 0;
		}
		catch(SQLException e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		
		return false;
	}
	
	public int RegisterUser(String name, String pass)
	{
		if(AccountExists(name))
		{
			return 1;
		}

		try
		{
			addUser.setString(1, name);
			addUser.setString(2, name);
			addUser.executeUpdate();
		}
		catch(SQLException e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return 2;
		}

		return 0;
	}
	
	public int LoginUser(String name, String pass)
	{
		if(!AccountExists(name))
		{
			return 1;
		}
		
		ResultSet r;
		String rpass;

		try
		{
			r = logUser.executeQuery();

			rpass = r.getString(2);
		}
		catch(SQLException e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return 2;
		}

		if(!pass.equals(rpass))
		{
			return 3;
		}
		
		return 0;
	}
	
	public void AddUser(User user)
	{
		Users.add(user);
	}

	public void RemoveUser(User user)
	{
		Users.remove(user);
	}
	
	// Singleton stuff
	
	private static UserManagerDB instance = new UserManagerDB();
	
	private UserManagerDB(){}
	
	public static UserManagerDB inst()
	{
		return instance;
	}
}