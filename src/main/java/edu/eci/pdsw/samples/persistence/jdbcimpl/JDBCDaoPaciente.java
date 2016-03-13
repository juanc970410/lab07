/*
 * Copyright (C) 2015 hcadavid
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
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoPaciente implements DaoPaciente {

    Connection con;

    public JDBCDaoPaciente(Connection con) {
        this.con = con;
    }
        

    @Override
    public Paciente load(int idpaciente, String tipoid) throws PersistenceException {
        PreparedStatement ps;
        Paciente p = null;
        /*try {
            
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading "+idpaciente,ex);
        }*/
        return p;
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        /*try {
        
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }*/
        
        throw new RuntimeException("No se ha implementado el metodo 'load' del DAOPAcienteJDBC");

    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        /*try {
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        } */
        throw new RuntimeException("No se ha implementado el metodo 'load' del DAOPAcienteJDBC");
    }
    
}
