/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class PacientePersistenceTest {
    
    public PacientePersistenceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    /*@Test
    public void databaseConnectionTest() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        daof.beginSession();
        
        daof.commitTransaction();
        daof.endSession();        
    }*/
    
    @Test
    public void pacienteConMasDeUnaConsulta() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        daof.beginSession();
        
        
        DaoPaciente daoPaciente = daof.getDaoPaciente();
        
        Paciente p = new Paciente(1019129303, "CC", "Juan Camilo", java.sql.Date.valueOf("1997-04-10"));
        Consulta c1 = new Consulta(java.sql.Date.valueOf("2016-03-05"), "Consulta general");
        Consulta c2 = new Consulta(java.sql.Date.valueOf("2016-04-10"), "Presenta un cuadro viral");
        p.getConsultas().add(c1);
        p.getConsultas().add(c2);
        
        daoPaciente.save(p);
        
        Paciente p2 = daoPaciente.load(1019129303, "CC");
        
        
        assertTrue("Los pacientes no son iguales",p.equals(p2));
                
        daof.commitTransaction();
        daof.endSession();
    }
    @Test
    public void pacienteSinConsultas() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        
        
        DaoPaciente daoPaciente = daof.getDaoPaciente();
        
        Paciente p = new Paciente(2103021, "CC", "Juan Camilo", java.sql.Date.valueOf("1997-04-10"));
        
        daoPaciente.save(p);
        
        Paciente p2 = daoPaciente.load(2103021, "CC");
        
        assertTrue("No se pudo guardar el paciente",p.equals(p2));
        
        daof.commitTransaction();
        daof.endSession();
    }
    @Test
    public void pacienteConUnaConsulta() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        
        DaoPaciente nuevodao = daof.getDaoPaciente();
        Paciente paciente = new Paciente(1019093806, "CC","jairo",java.sql.Date.valueOf("1994-04-10"));
        Consulta consul = new Consulta(java.sql.Date.valueOf("2016-03-15"), "Infeccion");
        
        paciente.getConsultas().add(consul);
        nuevodao.save(paciente);
        Paciente pacienteDos = nuevodao.load(1019093806,"CC");
        assertTrue("El paciente no quedo guardado",paciente.equals(pacienteDos));
        
        daof.commitTransaction();
        daof.endSession();
        
    }
    
    @Test
    public void pacienteYaExitenteConMasDeUnaConsulta() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        
        DaoPaciente nuevoDao = daof.getDaoPaciente();
        Paciente paciente = new Paciente(2090540, "CC", "Jairo", java.sql.Date.valueOf("1994-04-10"));
        Consulta consultaUno = new Consulta(java.sql.Date.valueOf("2016-03-15"), "Incapacidad por 3 dias");
        Consulta consultaDos = new Consulta(java.sql.Date.valueOf("2016-04-15"), "Retirar puntos");
        
        paciente.getConsultas().add(consultaUno);
        paciente.getConsultas().add(consultaDos);
        
        nuevoDao.save(paciente);
        
        boolean band = false;
        
        try{
            nuevoDao.save(paciente);
        }catch (Exception e){
            
            band = true;
        }
        
        assertTrue("Guardo un paciente que ya estaba guaardado", band);
        
        daof.commitTransaction();
        daof.endSession();
        
    }
    
}
