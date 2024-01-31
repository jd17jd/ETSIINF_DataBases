package panaderias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Empleado extends DBTable {
	static DBConnection dbConnection;
	static boolean isConnected;
	
	private int id_empleado;
	private String n_ss;
	private String nombre;
	private String apellido1;
	private String apellido2;
	
	public Empleado(int id_empleado, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado = id_empleado;
		this.n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
        this.nombre = DBConnection.NULL_SENTINEL_VARCHAR;
        this.apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
        this.apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
        dbConnection = conn;
		if(DBSync) {
			createTable();
			getEntryChanges();
		}
		
	}
	
	public Empleado(int id_empleado, String n_ss, String nombre, String apellido1, String apellido2, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado = id_empleado;
		this.n_ss = n_ss;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		dbConnection = conn;
		if (DBSync) {
            createTable();
            if (!isEmpleadoExistente()) {
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
	
	public String getN_ss() {
		if (DBSync) {
            getEntryChanges();
        }
		return n_ss;
	}

	public void setN_ss(String n_ss) {
		this.n_ss = n_ss;
	}

	public String getNombre() {
		if (DBSync) {
            getEntryChanges();
        }
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		if (DBSync) {
            getEntryChanges();
        }
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		if (DBSync) {
            getEntryChanges();
        }
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	
	public void destroy() {
		 if (DBSync) {
		        deleteEntry();
		    }
		    id_empleado = Integer.MIN_VALUE;
		    n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
		    nombre = DBConnection.NULL_SENTINEL_VARCHAR;
		    apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
		    apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
		    
		    DBSync = false;
	}
	

	boolean createTable() {
		
		boolean conectado = conn();
        if (conectado) {

        	String tableName = "empleado";
        	String sql = "CREATE TABLE IF NOT EXISTS empleado (\r\n"
    		 		+ "id_empleado INT AUTO_INCREMENT,\r\n"
    		 		+ "nombre VARCHAR (100) NOT NULL,\r\n"
    		 		+ "apellido1 VARCHAR (100),\r\n"
    		 		+ "apellido2 VARCHAR (100),\r\n"
    		 		+ "n_ss VARCHAR (100) NOT NULL,\r\n"
    		 		+ "PRIMARY KEY (id_empleado)\r\n"
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
     
        	if (n_ss == null)n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
        	if (nombre == null)  nombre = DBConnection.NULL_SENTINEL_VARCHAR;
        	if (apellido1 == null) apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
	        if (apellido2 == null) apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;

		
		 String sql = "INSERT INTO empleado (n_ss, nombre, apellido1, apellido2) " +
                 "VALUES (?, ?, ?, ?)";
		 
		 ArrayList<Object> empleado = new ArrayList<Object>();
		 empleado.add(n_ss);
		 empleado.add(nombre);
		 empleado.add(apellido1);
		 empleado.add(apellido2);
		 
		 int rowsAffected = dbConnection.update(sql, empleado);

		 if (rowsAffected == -1) {
             System.out.println("Ocurrió una excepción al ejecutar el comando SQL");
             id_empleado =  Integer.MIN_VALUE;
             n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
             nombre = DBConnection.NULL_SENTINEL_VARCHAR;
             apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
             apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
             DBSync = false;
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
        	if (id_empleado == Integer.MIN_VALUE) {
                return false;
            }
    		if (DBSync) {
    	        getEntryChanges();
    	    }  		
    		
    		if (n_ss == null)n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
        	if (nombre == null)  nombre = DBConnection.NULL_SENTINEL_VARCHAR;
        	if (apellido1 == null) apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
	        if (apellido2 == null) apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
	        
    		 String sql = "UPDATE empleado SET n_ss = ?, nombre = ?, apellido1 = ?, apellido2 = ? "
    			 		+ "WHERE id_empleado = ?";
    		 
    		 ArrayList<Object> empleado = new ArrayList<Object>();
    		 empleado.add(n_ss);
    		 empleado.add(nombre);
    		 empleado.add(apellido1);
    		 empleado.add(apellido2);
    		 empleado.add(id_empleado);
    		 int rowsAffected = dbConnection.update(sql, empleado);
    		 
    		 if (rowsAffected == -1) {
                 System.out.println("Ocurrió una excepción al ejecutar el comando SQL");
                 id_empleado = Integer.MIN_VALUE;
                 n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
                 nombre = DBConnection.NULL_SENTINEL_VARCHAR;
                 apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
                 apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
                 DBSync = false;
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
        	if (id_empleado == Integer.MIN_VALUE) {
                return false;
            }
    		
    		 String sql = "DELETE FROM empleado WHERE id_empleado = ?";
    		 	 
    		 ArrayList<Object> empleado = new ArrayList<Object>();
    		 empleado.add(id_empleado);
    		 
    		 int rowsAffected = dbConnection.update(sql, empleado);
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
				if (id_empleado == Integer.MIN_VALUE) {
					return;
				}
				ArrayList<Object> listaempleado = new ArrayList<Object>();
				String sql = "SELECT * FROM empleado WHERE id_empleado = ?";
				listaempleado.add(id_empleado);
				ResultSet resultSet1 = dbConnection.query(sql, listaempleado);
				try {
					while (resultSet1.next()) {
						setNombre(resultSet1.getString("nombre"));
						setApellido1(resultSet1.getString("apellido1"));
						setApellido2(resultSet1.getString("apellido2"));
						setN_ss(resultSet1.getString("n_ss"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		}	
	}
	
	private boolean isEmpleadoExistente() {
	    // Comprueba si el empleado ya existe en la base de datos utilizando una consulta SELECT
	    String sql = "SELECT COUNT(*) FROM empleado WHERE id_empleado = ?";
	    ArrayList<Object> params = new ArrayList<>();
	    params.add(id_empleado);
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
