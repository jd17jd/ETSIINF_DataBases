package panaderias;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {

	static DBConnection dbConnection;
	static boolean isConnected;
	
    
	public static void main (String[] args){
		
		String server = "localhost";
	    int port = 3306;
	    String user = "panaderia_user";
	    String pass = "panaderia_pass";
	    String database = "panaderias";
	    
	    String CSVEmpleados = "";
	    String CSVLocal = "";
	    		
		dbConnection = new DBConnection(server, port, user, pass, database);
		
		 // Crear la ventana
        JFrame ventana = new JFrame("P3 - Panaderias");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout());

        // Crear los botones      
        JButton boton1 = new JButton("getEmpleadosFromCSV");
        JButton boton2 = new JButton("getLocalesFromCSV");
        JButton boton3 = new JButton("getEmpleadosFromBD");
        JButton boton4 = new JButton("getLocalesFromBD");
        
        JButton boton5 = new JButton("CreateTable_Empleados");
        JButton boton6 = new JButton("CreateTable_Local");
        JButton boton7 = new JButton("CreateTable_Trabaja");

        
        JButton boton8 = new JButton("Insert_Empleados");
        JButton boton9 = new JButton("Insert_Local");
        JButton boton10 = new JButton("Insert_Trabaja");

        
        JButton boton11 = new JButton("Update_Empleados");
        JButton boton12 = new JButton("Update_Local");
        JButton boton13 = new JButton("Update_Trabaja");

        
        JButton boton14 = new JButton("Select_Empleados");
        JButton boton15 = new JButton("Select_Local");
        JButton boton16 = new JButton("Select_Trabaja");

        JButton boton17 = new JButton("Delete_Empleados");
        JButton boton18 = new JButton("Delete_Local");
        JButton boton19 = new JButton("Delete_Trabaja");
	    
	    
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacer pruebas aquí para comprobar la funcionalidad
       	    
            	ArrayList<Empleado> empleados = DataManager.getEmpleadosFromCSV(CSVEmpleados, 
          	    		 dbConnection, true);

          	  // Verificación de los resultados
          	  if (empleados != null) {
          	      if (empleados.isEmpty()) {
          	          System.out.println("No se encontraron empleados en el archivo CSV.");
          	      } else {
          	          System.out.println("Empleados creados desde el archivo CSV:");
          	          for (Empleado empleado : empleados) {
          	              System.out.println(empleado.getId_empleado() + ": " + empleado.getNombre() + " " + empleado.getApellido1());
          	          }
          	      }
          	  } else {
          	      System.out.println("Error al leer los empleados desde el archivo CSV.");
          	  }
              
            }
        });
        
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacer pruebas aquí para comprobar la funcionalidad

            	ArrayList<Local> locales = DataManager.getLocalesFromCSV(CSVLocal, 
          	    		 dbConnection, true);

          	  // Verificación de los resultados
          	  if (locales != null) {
          	      if (locales.isEmpty()) {
          	          System.out.println("No se encontraron locales en el archivo CSV.");
          	      } else {
          	          System.out.println("Locales creados desde el archivo CSV:");
          	          for (Local local : locales) {
          	              System.out.println(local.getId_local() + ": " + local.getTiene_cafeteria() + " " + local.getDescripcion());
          	          }
          	      }
          	  } else {
          	      System.out.println("Error al leer los locales desde el archivo CSV.");
          	  }
  
            }
        });
        
        
        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacer pruebas aquí para comprobar la funcionalidad
       	    
            	ArrayList<Empleado> empleados = DataManager.getEmpleadosFromDB(dbConnection, true);

          	  // Verificación de los resultados
          	  if (empleados != null) {
          	      if (empleados.isEmpty()) {
          	          System.out.println("No hay empleados en la base de datos.");
          	      } else {
          	          System.out.println("Empleados encontrados:");
          	          for (Empleado empleado : empleados) {
          	              System.out.println(empleado.getId_empleado() + ": " + empleado.getNombre());
          	          }
          	      }
          	  } else {
          	      System.out.println("Error al obtener los empleados desde la base de datos.");
          	  }             
            }
        });
        
        
        boton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
       	    
            	ArrayList<Local> locales = DataManager.getLocalesFromDB(dbConnection, true);

          	  // Verificación de los resultados
          	  if (locales != null) {
          	      if (locales.isEmpty()) {
          	          System.out.println("No hay locales en la base de datos.");
          	      } else {
          	          System.out.println("Locales encontrados:");
          	          for (Local local : locales) {
          	              System.out.println(local.getId_local() + ": " + local.getTiene_cafeteria() + " " + local.getDescripcion());
          	          }
          	      }
          	  } else {
          	      System.out.println("Error al obtener los locales desde la base de datos.");
          	  }
              
            }
        });
        
        long milliseconds = 1653206400000L;
        java.sql.Date fecha = new java.sql.Date(milliseconds);
        		
        Empleado empleado = new Empleado(3, dbConnection, true);
        Local local = new Local(1, dbConnection, true);
        Trabaja trabaja = new Trabaja(3, 1, fecha, dbConnection, true);
        
        boton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacer pruebas aquí para comprobar la funcionalidad
       	    
                // Verificar creación de la tabla
            	Empleado empleado = new Empleado(3, dbConnection, true);
                boolean tableCreated = empleado.createTable();
                System.out.println("Tabla creada empleado: " + tableCreated);
              
            }
        });
        
        boton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacer pruebas aquí para comprobar la funcionalidad
       	    
                // Verificar creación de la tabla
                boolean tableCreated = local.createTable();
                System.out.println("Tabla creada local: " + tableCreated);
              
            }
        });
        
        boton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacer pruebas aquí para comprobar la funcionalidad
       	    
                // Verificar creación de la tabla
                boolean tableCreated = trabaja.createTable();
                System.out.println("Tabla creada trabaja: " + tableCreated);
              
            }
        });
        
        
        boton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacer pruebas aquí para comprobar la funcionalidad
       	    
            	 // Insertar entrada
                boolean entryInserted = empleado.insertEntry();
                System.out.println("Entrada insertada empleado: " + entryInserted);
              
            }
        });
        
        boton9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacer pruebas aquí para comprobar la funcionalidad
       	    
            	 // Insertar entrada
                local.setTiene_cafeteria(true);
                local.setDescripcion("La Señorita Lexiquetos");
                local.setDireccion("Al lado de tu corazon");

                boolean entryInserted = local.insertEntry();
                System.out.println("Entrada insertada local: " + entryInserted);
              
            }
        });
        
        boton10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Hacer pruebas aquí para comprobar la funcionalidad
       	    
            	 // Insertar entrada
     
                trabaja.setFecha_fin(null);

                boolean entryInserted = trabaja.insertEntry();
                System.out.println("Entrada insertada trabaja: " + entryInserted);
              
            }
        });
        
        
        boton11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Obtener cambios de la entrada

            	empleado.setNombre("Prueba1");
                boolean entryInserted = empleado.updateEntry();
                System.out.println("Entrada insertada empleado: " + entryInserted);
            }
        });
        
        boton12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
      
            	local.setDescripcion("El mejor Team del mundo");
                local.setDireccion("En el cielo");

                boolean entryInserted = local.updateEntry();
                System.out.println("Entrada insertada local: " + entryInserted);
              
            }
        });
        
        boton13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Obtener cambios de la entrada
            	trabaja.setFecha_fin(null);;
   
                boolean entryInserted = trabaja.updateEntry();
                System.out.println("Entrada insertada trabaja: " + entryInserted);
            }
        });
        
        
        boton14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Obtener cambios de la entrada
                empleado.getEntryChanges();
                System.out.println("Nombre: " + empleado.getNombre());
                System.out.println("Apellido1: " + empleado.getApellido1());
                System.out.println("Apellido2: " + empleado.getApellido2());
                System.out.println("N_SS: " + empleado.getN_ss());
              
            }
        });
        
        boton15.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	local.getEntryChanges();
                System.out.println("ID: " + local.getId_local());
                System.out.println("Apellido1: " + local.getTiene_cafeteria());
                System.out.println("Apellido2: " + local.getDireccion());
                System.out.println("N_SS: " + local.getDescripcion());
              
            }
        });
        
        boton16.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	trabaja.getEntryChanges();
                System.out.println("Empleado: " + trabaja.getId_empleado());
                System.out.println("Local: " + trabaja.getId_local());
                System.out.println("Fecha ini: " + trabaja.getFecha_inicio());
                System.out.println("Fecha fin: " + trabaja.getFecha_fin());
            }
        });
        
        boton17.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Eliminar entrada
                boolean entryDeleted = empleado.deleteEntry();
                System.out.println("Entrada eliminada empleado: " + entryDeleted);

            }
        });
        
        boton18.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean entryDeleted = local.deleteEntry();
                System.out.println("Entrada eliminada local: " + entryDeleted);
            }
        });
        
        boton19.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean entryDeleted = trabaja.deleteEntry();
                System.out.println("Entrada eliminada trabaja: " + entryDeleted);
            }
        });

        ventana.add(boton1);
        ventana.add(boton2);
        ventana.add(boton3);
        ventana.add(boton4);
        
        ventana.add(boton5);
        ventana.add(boton6);
        ventana.add(boton7);
        
        ventana.add(boton8);
        ventana.add(boton9);
        ventana.add(boton10);
        
        ventana.add(boton11);
        ventana.add(boton12);
        ventana.add(boton13);
        
        ventana.add(boton14);
        ventana.add(boton15);
        ventana.add(boton16);
        
        ventana.add(boton17);
        ventana.add(boton18);
        ventana.add(boton19);

        ventana.pack();
        ventana.setVisible(true);
			     
	               
       Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
            	boolean cierre = dbConnection.close();
                System.out.println("Programa finalizando..." + cierre);
            }
        });
		
	}
	
}
      