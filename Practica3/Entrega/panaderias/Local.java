package panaderias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Local extends DBTable {
	static DBConnection dbConnection;
	static boolean isConnected;
	
	private int id_local;
	private boolean tiene_cafeteria;
	private String direccion;
	private String descripcion;
	
	public Local(int id_local, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_local = id_local;
		this.tiene_cafeteria = false;
        this.direccion = DBConnection.NULL_SENTINEL_VARCHAR;
        this.descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
        dbConnection = conn;
		if(DBSync) {
			createTable();
			getEntryChanges();
		}
	}
	
	public Local(int id_local, boolean tiene_cafeteria, String direccion, String descripcion, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_local = id_local;
		this.tiene_cafeteria = tiene_cafeteria;
		this.direccion = direccion;
		this.descripcion = descripcion;
		dbConnection = conn;
		if (DBSync) {
            createTable();
            if (!isLocalExistente()) {
                insertEntry();
            }else {
            	this.DBSync = false;
            	updateEntry();
            }
        }
	}
	
	public int getId_local() {
		if (DBSync) {
            getEntryChanges();
        }
		return id_local;
	}
	
	public boolean getTiene_cafeteria() {
		if (DBSync) {
            getEntryChanges();
        }
		return tiene_cafeteria;
	}

	public void setTiene_cafeteria(boolean tiene_cafeteria) {
		this.tiene_cafeteria = tiene_cafeteria;
	}

	public String getDireccion() {
		if (DBSync) {
            getEntryChanges();
        }
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDescripcion() {
		if (DBSync) {
            getEntryChanges();
        }
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void destroy() {
		if (DBSync) {
	        deleteEntry();
	    }
		id_local = Integer.MIN_VALUE;
		tiene_cafeteria = false;
		direccion = DBConnection.NULL_SENTINEL_VARCHAR;
		descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
	    
	    DBSync = false;
	}
	
	
	
	boolean createTable() {
		
		boolean conectado = conn();
        if (conectado) {
        	String tableName = "local";
        	
        	String sql = "CREATE TABLE local (\r\n"
    				+ "id_local INT AUTO_INCREMENT,\r\n"
    				+ "tiene_cafeteria INT,\r\n"
    				+ "direccion VARCHAR (100), \r\n"
    				+ "descripcion VARCHAR (100), \r\n"
    				+ "PRIMARY KEY (id_local)\r\n"
    				+ ")";

    		boolean tabla = dbConnection.tableExists(tableName);
    		if (!tabla) {
    			int creartabla = dbConnection.update(sql);
					if (creartabla == -1) {
						return false;
					}return true;
            }return false;
        }else {
        	return false;
        }	
	}
	
	boolean insertEntry() {
		boolean conectado = conn();
        if (conectado) {
        	 if (direccion == null) direccion = DBConnection.NULL_SENTINEL_VARCHAR;
        	 if(descripcion == null) descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
 
    		 String sql = "INSERT INTO local (tiene_cafeteria, direccion, descripcion) " +
                     "VALUES (?, ?, ?)";
    		 
    		 ArrayList<Object> local = new ArrayList<Object>();
    		 local.add(tiene_cafeteria);
    		 local.add(direccion);
    		 local.add(descripcion);
    		 
    		 int rowsAffected = dbConnection.update(sql, local);

    		 if (rowsAffected == -1) {
                 System.out.println("Ocurrió una excepción al ejecutar el comando SQL");
                 id_local = Integer.MIN_VALUE;
     			tiene_cafeteria = false;
     			direccion = DBConnection.NULL_SENTINEL_VARCHAR;
     			descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
                 return false;
             } else {
                 System.out.println("Filas afectadas: " + rowsAffected);
                 return true;
             }	
        }else {
        	return false;
        }	
	}
	
	boolean updateEntry() {
		boolean conectado = conn();
        if (conectado) {
        	if (id_local == Integer.MIN_VALUE) {
                return false;
            }
    		
    		if (DBSync) {
    	        getEntryChanges();
    	    }
    		
       	 if (direccion == null) direccion = DBConnection.NULL_SENTINEL_VARCHAR;
       	 if(descripcion == null) descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
       	 
    		 String sql = "UPDATE local SET tiene_cafeteria = ?, direccion = ?, descripcion = ? "
    			 		+ "WHERE id_local = ?";
    		 
    		 ArrayList<Object> local = new ArrayList<Object>();
    		 local.add(tiene_cafeteria);
    		 local.add(direccion);
    		 local.add(descripcion);
    		 local.add(id_local);
    		 
    		 int rowsAffected = dbConnection.update(sql, local);

    		 if (rowsAffected == -1) {
                 System.out.println("Ocurrió una excepción al ejecutar el comando SQL");
                 tiene_cafeteria = false;
      			 direccion = DBConnection.NULL_SENTINEL_VARCHAR;
      			 descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
                 return false;
             } else {
                 System.out.println("Filas afectadas: " + rowsAffected);
                 DBSync = true;
                 return true;
             }	
        }else {
        	return false;
        }	
	}
	
	boolean deleteEntry() {
		boolean conectado = conn();
        if (conectado) {
        	if (id_local == Integer.MIN_VALUE) {
                return false;
            }		

    		 String sql = "DELETE FROM local WHERE id_local = ?";		 
    		 ArrayList<Object> local = new ArrayList<Object>();
    		 local.add(id_local);

    		 int rowsAffected = dbConnection.update(sql, local);
    		 
    		 if (rowsAffected == -1) {
                 System.out.println("Ocurrió una excepción al ejecutar el comando SQL");
                 return false;
             } else {
                 System.out.println("Filas afectadas: " + rowsAffected);
                 return true;
             }	
        }else {
        	return false;
        }	
	}
	
	void getEntryChanges() {
		if (DBSync) {
			boolean conectado = conn();
			if (conectado) {
				if (id_local == Integer.MIN_VALUE) {
		            return;
		        }	
				
				ArrayList<Object> listalocal = new ArrayList<Object>();
				String sql = "SELECT * FROM local "
						+ "WHERE id_local = ?";
				listalocal.add(id_local);

				ResultSet resultSet1 = dbConnection.query(sql, listalocal);
		        try {
					while (resultSet1.next()) {
						setTiene_cafeteria(resultSet1.getBoolean("tiene_cafeteria"));
						setDireccion(resultSet1.getString("direccion"));
						setDescripcion(resultSet1.getString("descripcion"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean isLocalExistente() {
	    String sql = "SELECT COUNT(*) FROM local WHERE id_local = ?";
	    ArrayList<Object> params = new ArrayList<>();
	    params.add(id_local);
	    ResultSet resultSet = dbConnection.query(sql, params);

	    try {
	        if (resultSet.next()) {
	            int count = resultSet.getInt(1);
	            return count > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}

	private static boolean conn() {
	    
	    if (!isConnected) {
		    isConnected = dbConnection.connect();
	    }
	    return isConnected;
	}
}
