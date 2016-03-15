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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Set<Consulta> consultas = new HashSet<Consulta>();
        Paciente p = null;
        try {
            PreparedStatement load = null;
            String cargarPaciente = "select pac.nombre, pac.fecha_nacimiento, con.idCONSULTAS idcon, con.fecha_y_hora, con.resumen from PACIENTES as pac left join CONSULTAS as con on con.PACIENTES_id=pac.id and con.PACIENTES_tipo_id=pac.tipo_id where pac.id=? and pac.tipo_id=?";
            con.setAutoCommit(false);
            load = con.prepareStatement(cargarPaciente);
            load.setInt(1, idpaciente);
            load.setString(2, tipoid);
            ResultSet resp = load.executeQuery();
            con.commit();
            if(resp.next()){
                
                p = new Paciente(idpaciente, tipoid,resp.getString("nombre"),resp.getDate("fecha_nacimiento"));
                
                if (resp.getString("idcon")!=null){
                    
                    Consulta c = new Consulta(resp.getDate("fecha_y_hora"), resp.getString("resumen"));
                    consultas.add(c);
                }
            }
            while(resp.next()){
                p = new Paciente(idpaciente, tipoid,resp.getString("nombre"),resp.getDate("fecha_nacimiento"));
                Consulta c = new Consulta(resp.getDate("fecha_y_hora"), resp.getString("resumen"));
                consultas.add(c);
            }
            p.setConsultas(consultas);
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading "+idpaciente,ex);
        }
        return p;
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        try {
            PreparedStatement guardar = null;
            String insertPaciente = "INSERT INTO PACIENTES VALUES (?,?,?,?)";
            con.setAutoCommit(false);
            guardar = con.prepareStatement(insertPaciente);
            guardar.setInt(1, p.getId());
            guardar.setString(2, p.getTipo_id());
            guardar.setString(3, p.getNombre());
            guardar.setDate(4, p.getFechaNacimiento());
            guardar.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while saving a paciente.",ex);
        }
        
        try{
            
            Set<Consulta> consultas = p.getConsultas();
            for (Consulta consul : consultas){
                PreparedStatement guardarCon = null;
                String insertConsulta = "INSERT INTO CONSULTAS (fecha_y_hora,resumen,PACIENTES_id, PACIENTES_tipo_id) VALUES (?,?,?,?)";
                con.setAutoCommit(false);
                guardarCon = con.prepareStatement(insertConsulta);
                
                guardarCon.setDate(1, consul.getFechayHora());
                guardarCon.setString(2, consul.getResumen());
                guardarCon.setInt(3, p.getId());
                guardarCon.setString(4, p.getTipo_id());
                guardarCon.executeUpdate();
                con.commit();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        /*try {
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        } */
        
    }
    
}
