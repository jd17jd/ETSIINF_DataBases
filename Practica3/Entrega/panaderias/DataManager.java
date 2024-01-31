package panaderias;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataManager {

	static DBConnection dbConnection1;
	static boolean isConnected;
	
	public static ArrayList<Empleado> getEmpleadosFromDB(DBConnection dbConnection, boolean sync) {
		
		dbConnection1 = dbConnection;
		boolean conectado = conn();
        if (conectado) {
        	ArrayList<Empleado> empleados = new ArrayList<>();
    	    try {
    	        String sql = "SELECT * FROM empleado";   
    	        boolean tabla = dbConnection1.tableExists("empleado");
    	        if (tabla) {
    	        	 ResultSet resultSet = dbConnection1.query(sql);
    	    	        while (resultSet.next()) {
    	    	            int idEmpleado = resultSet.getInt("id_empleado");
    	    	            String nSS = resultSet.getString("n_ss");
    	    	            String nombre = resultSet.getString("nombre");
    	    	            String apellido1 = resultSet.getString("apellido1");
    	    	            String apellido2 = resultSet.getString("apellido2");

    	    	            Empleado empleado = new Empleado(idEmpleado, dbConnection1, false);
    	    	            empleado.setN_ss(nSS);
    	    	            empleado.setNombre(nombre);
    	    	            empleado.setApellido1(apellido1);
    	    	            empleado.setApellido2(apellido2);
    	    	            empleado.setSync(true);

    	    	            empleados.add(empleado);
    	    	        } 
    	    	        return empleados;
    	        }else {
    	        	return null;
    	        }    	    	
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        return null;
    	    }		
        }else {
        	return null;
        }
        
	}
	
	public static ArrayList<Empleado> getEmpleadosFromCSV(String filename, DBConnection dbConnection, boolean sync) {
		
		dbConnection1 = dbConnection;
		boolean conectado = conn();
        if (conectado) {
        	ArrayList<Empleado> empleados = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                boolean isFirstLine = true;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Ignorar la primera línea (cabecera)
                    }

                    String[] values = line.split(";");

                    if (values.length >= 1) {
                        int id_empleado = Integer.parseInt(values[0]);
                        String n_ss = values.length > 1 ? values[1] : DBConnection.NULL_SENTINEL_VARCHAR;
                        String nombre = values.length > 2 ? values[2] : DBConnection.NULL_SENTINEL_VARCHAR;
                        String apellido1 = values.length > 3 ? values[3] : DBConnection.NULL_SENTINEL_VARCHAR;
                        String apellido2 = values.length > 4 ? values[4] : DBConnection.NULL_SENTINEL_VARCHAR;
                        
                        Empleado empleado = new Empleado(id_empleado, n_ss, nombre, apellido1, apellido2, dbConnection1, sync);
                        empleados.add(empleado);
                    }
                }
                if (sync) {
                    for (Empleado empleado : empleados) {
                        empleado.setSync(true);
                    }
                }

                return empleados;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else {
        	return null;
        }
		
    }
	
	
	public static ArrayList<Local> getLocalesFromDB(DBConnection dbConnection, boolean sync) {
		
		dbConnection1 = dbConnection;
		boolean conectado = conn();
        if (conectado) {
        	ArrayList<Local> locales = new ArrayList<>();

    	    try {
    	        String sql = "SELECT * FROM local";      
    	        boolean tabla = dbConnection1.tableExists("local");
    	        if (tabla) {
    	        	ResultSet resultSet = dbConnection1.query(sql);

        	        while (resultSet.next()) {
        	            int id_local = resultSet.getInt("id_local");
        	            boolean tiene_cafeteria = resultSet.getBoolean("tiene_cafeteria");
        	            String direccion = resultSet.getString("direccion");
        	            String descripcion = resultSet.getString("descripcion");

        	            Local local = new Local(id_local, dbConnection1, false);
        	            local.setTiene_cafeteria(tiene_cafeteria);
        	            local.setDireccion(direccion);
        	            local.setDescripcion(descripcion);
        	            local.setSync(true);
        	            locales.add(local);    	    
        	        } 
        	        return locales;
    	        }else {
    	        	return null;
    	        }	        
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        return null;
    	    }
        }else {
        	return null;
        }
	}
	
	public static ArrayList<Local> getLocalesFromCSV(String filename, DBConnection dbConnection, boolean sync) {
		
		dbConnection1 = dbConnection;
		boolean conectado = conn();
        if (conectado) {
        	ArrayList<Local> locales = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                boolean isFirstLine = true;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Ignorar la primera línea (cabecera)
                    }

                    String[] values = line.split(";");

                    if (values.length >= 4) {
                        int id_local = Integer.parseInt(values[0]);
                        boolean tiene_cafeteria = Boolean.parseBoolean(values[1]);
                        String direccion = values[2];
                        String descripcion = values[3];

                        Local local = new Local(id_local, tiene_cafeteria, direccion, descripcion, dbConnection1, sync);
                        locales.add(local);
                    }
                }

                if (sync) {
                    for (Local local : locales) {
                    	local.setSync(true);
                    }
                }

                return locales;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else {
        	return null;
        }
		
	}
	
	private static boolean conn() {
	    
	    if (!isConnected) {
		    isConnected = dbConnection1.connect();
	    }
	    return isConnected;
	}
}
