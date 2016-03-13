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

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    
    @Test
    public void databaseConnectionTest() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        daof.beginSession();
        
        //IMPLEMENTACION DE LAS PRUEBAS
        //fail("Pruebas no implementadas");
        
        daof.commitTransaction();
        daof.endSession();        
    }
    @Test
    public void pacienteConMasDeUnaConsulta() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        
        
        DaoPaciente daoPaciente = daof.getDaoPaciente();
        
        Paciente p = new Paciente(1019129303, "CC", "Juan Camilo", new Date(1997, 04, 10));
        Consulta c1 = new Consulta(new Date(2016, 03, 15), "Consulta general");
        Consulta c2 = new Consulta(new Date(2016, 04, 10), "Presenta un cuadro viral");
        p.getConsultas().add(c1);
        p.getConsultas().add(c2);
        daoPaciente.save(p);
        
        Connection con = null;
        try{
            String url = properties.getProperty("url");
            String driver = properties.getProperty("driver");
            
            Class.forName(driver);
            
            con = DriverManager.getConnection(url);
            con.setAutoCommit(false);
            
            PreparedStatement st = null;
            String query = "select pac.nombre, pac.fecha_nacimiento, con.idCONSULTAS, con.fecha_y_hora, con.resumen from PACIENTES as pac inner join CONSULTAS as con on con.PACIENTES_id=pac.id and con.PACIENTES_tipo_id=pac.tipo_id where pac.id=? and pac.tipo_id=?";
            
            st=con.prepareStatement(query);
            st.setInt(1, 1019129303);
            st.setString(2, "CC");
            ResultSet executeQuery = st.executeQuery();
            while (executeQuery.next()){
                
            }
            
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
