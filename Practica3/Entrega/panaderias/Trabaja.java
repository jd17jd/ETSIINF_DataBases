package panaderias;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Trabaja extends DBTable {
	static DBConnection dbConnection;
	static boolean isConnected;
	
	private int id_empleado;
	private int id_local;
	private java.sql.Date fecha_inicio;
	private java.sql.Date fecha_fin;

	
	public Trabaja(int id_empleado, int id_local, java.sql.Date fecha_inicio, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado = id_empleado;
		this.id_local = id_local;
		this.fecha_inicio = fecha_inicio;
		dbConnection = conn;
		if(DBSync) {
			createTable();
			getEntryChanges();
		}
	}
	
	public Trabaja(int id_empleado, int id_local, java.sql.Date fecha_inicio, java.sql.Date fecha_fin, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado = id_empleado;
		this.id_local = id_local;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		dbConnection = conn;
		if (DBSync) {
            createTable();
            if (!isTrabajaExistente()) {
                insertEntry();
            }else {
            	this.DBSync = false;
            	updateEntry();
            }
        }
	}

	public int getId_empleado() {
		if (DBSync) {
            getEntryChanges();
        }
		return id_empleado;
	}

	public int getId_local() {
		if (DBSync) {
            getEntryChanges();
        }
		return id_local;
	}

	public java.sql.Date getFecha_inicio() {
		if (DBSync) {
            getEntryChanges();
        }
		return fecha_inicio;
	}

	public java.sql.Date getFecha_fin() {
		if (DBSync) {
            getEntryChanges();
        }
		return fecha_fin;
	}

	public void setFecha_fin(java.sql.Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	
	public void destroy() {
		if (DBSync) {
	        deleteEntry();
	    } 
		id_empleado = Integer.MIN_VALUE;
		id_local = Integer.MIN_VALUE;
		fecha_inicio = DBConnection.NULL_SENTINEL_DATE;
		fecha_fin = DBConnection.NULL_SENTINEL_DATE; 
	    DBSync = false;
	}
	
	
	boolean createTable() {
		boolean conectado = conn();
        if (conectado) {
        	String tableName = "trabaja";
        	String sql = "CREATE TABLE trabaja (\r\n"
    				+ "id_empleado INT UNIQUE NOT NULL,\r\n"
    				+ "id_local INT UNIQUE NOT NULL,\r\n"
    				+ "fecha_inicio DATE,\r\n"
    				+ "fecha_fin DATE,\r\n"
    				+ "PRIMARY KEY (id_local,id_empleado,fecha_inicio),\r\n"
    				+ "    FOREIGN KEY (id_local) REFERENCES local (id_local)\r\n"
    				+ "        ON DELETE CASCADE ON UPDATE CASCADE,\r\n"
    				+ "    FOREIGN KEY (id_empleado) REFERENCES empleado (id_empleado)\r\n"
    				+ "        ON DELETE CASCADE ON UPDATE CASCADE\r\n"
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
        	
        	if(id_empleado == Integer.MIN_VALUE || id_local == Integer.MIN_VALUE || fecha_inicio == null ) {
        		return false;
        	}
        	
        	if (fecha_fin == null)fecha_fin=DBConnection.NULL_SENTINEL_DATE;
        	
        	ArrayList<Object> local = new ArrayList<Object>();
   		 String sql = "INSERT INTO trabaja (id_empleado, id_local, fecha_inicio, fecha_fin) " +
                    "VALUES (?, ?, ?, ?)";
   		 local.add(id_empleado);
   		 local.add(id_local);
   		 local.add(fecha_inicio);
   		 local.add(fecha_fin);
   		 
   		 int rowsAffected = dbConnection.update(sql, local);

   		 if (rowsAffected == -1) {
                System.out.println("Ocurrió una excepción al ejecutar el comando SQL");
                id_empleado = Integer.MIN_VALUE;
        		id_local = Integer.MIN_VALUE;
        		fecha_inicio = DBConnection.NULL_SENTINEL_DATE;
        		fecha_fin = DBConnection.NULL_SENTINEL_DATE;
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
        	if (id_local == Integer.MIN_VALUE || id_empleado == Integer.MIN_VALUE || fecha_inicio == null) {
                return false;
            }
    		
    		if (DBSync) {
    	        getEntryChanges();
    	    }
    		
        	if (fecha_fin == null)fecha_fin=DBConnection.NULL_SENTINEL_DATE;
        	
    		 ArrayList<Object> trabaja = new ArrayList<Object>();
    		 String sql = "UPDATE trabaja SET fecha_fin = ? "
    			 		+ "WHERE id_empleado = ? AND id_local = ? AND fecha_inicio = ?";
    		 trabaja.add(fecha_fin);
    		 trabaja.add(id_empleado);
    		 trabaja.add(id_local);
    		 trabaja.add(fecha_inicio);
    		 
    		 
    		 int rowsAffected = dbConnection.update(sql, trabaja);

    		 if (rowsAffected == -1) {
                 System.out.println("Ocurrió una excepción al ejecutar el comando SQL");
                 id_empleado = Integer.MIN_VALUE;
         		id_local = Integer.MIN_VALUE;
         		fecha_inicio = DBConnection.NULL_SENTINEL_DATE;
         		fecha_fin = DBConnection.NULL_SENTINEL_DATE;
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
        	if (id_local == Integer.MIN_VALUE || id_empleado == Integer.MIN_VALUE || fecha_inicio == null) {
                return false;
            }
    		
    		 ArrayList<Object> trabaja = new ArrayList<Object>();
    		 String sql = "DELETE FROM trabaja "
    			 		+ "WHERE id_empleado = ? AND id_local = ? AND fecha_inicio = ?";
    		 trabaja.add(id_empleado);
    		 trabaja.add(id_local);
    		 trabaja.add(fecha_inicio);
    		  
    		 int rowsAffected = dbConnection.update(sql, trabaja);

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
				if (id_local == Integer.MIN_VALUE || id_empleado == Integer.MIN_VALUE || fecha_inicio == null) {
		            return;
		        }
				
				ArrayList<Object> listatrabaja = new ArrayList<Object>();
				String sql = "SELECT * FROM trabaja "
						+ "WHERE id_empleado = ? AND id_local = ? AND fecha_inicio = ?";
				listatrabaja.add(id_empleado);
				listatrabaja.add(id_local);
				listatrabaja.add(fecha_inicio);
				
				ResultSet resultSet1 = dbConnection.query(sql, listatrabaja);
		        try {
					while (resultSet1.next()) {
						setFecha_fin(resultSet1.getDate("fecha_fin"));				
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean isTrabajaExistente() {
	    String sql = "SELECT COUNT(*) FROM trabaja WHERE id_empleado = ? AND id_local = ? AND fecha_inicio = ?";
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
