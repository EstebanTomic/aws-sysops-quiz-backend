/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysop.restservice;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author etomi
 */

@RestController
public class PreguntaController {
  
    private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

        @CrossOrigin(origins = {"http://localhost:4200", "https://aws.sorprend.io"})
	@GetMapping("/pregunta")
	public Pregunta pregunta(@RequestParam(value = "name", defaultValue = "World") String name) throws SQLException {
            int valorDado = (int) Math.floor(Math.random()*777+1);
            System.out.format("%s\n",valorDado);
            Pregunta preguntaData = selectPregunta(valorDado);
          
              // print the results
              System.out.format("%s, %s\n", preguntaData.getIdPregunta(), preguntaData.getPregunta());
            
            return preguntaData;
        }
        
        public void insertPregunta(String pregunta, String respuestaA, String respuestaB, String respuestaC, String respuestaD, String respuestaE, String opcionCorrecta, String correcta, int numeroPregunta) throws SQLException{
        
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } 
            catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found !!");
            }
            System.out.println("MySQL JDBC Driver Registered!");
            Connection connection = null;

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/awssysops?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin", "Pa$$w0rd123");
            System.out.println("SQL Connection to database established!");
 
              String query = " insert into preguntas (pregunta, respuestaA, respuestaB, respuestaC, respuestaD, respuestaE, opcionCorrecta, correcta, numeroPregunta)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, pregunta);
            preparedStmt.setString (2, respuestaA);
            preparedStmt.setString (3, respuestaB);
            preparedStmt.setString (4, respuestaC);
            preparedStmt.setString (5, respuestaD);
            preparedStmt.setString (6, respuestaE);
            preparedStmt.setString (7, opcionCorrecta);
            preparedStmt.setString (8, correcta);
            preparedStmt.setInt (9, numeroPregunta);
       
            preparedStmt.execute();
            connection.close();
        
        }
        
        static public Pregunta selectPregunta(int numPregunta) throws SQLException{
        
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } 
            catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found !!");
            }
            System.out.println("MySQL JDBC Driver Registered!");
            Connection connection = null;

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/awssysops?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin", "Pa$$w0rd123");
            System.out.println("SQL Connection to database established!");
 
            String query = "SELECT * FROM preguntas WHERE idPreguntas = '"+numPregunta+"'";

            // create the java statement
            Statement st = connection.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            
           Pregunta pregunta = new Pregunta();
           
           while(rs.next()){
                pregunta.setIdPregunta(rs.getInt("idPreguntas"));
                pregunta.setTopic(rs.getString("topic"));
                pregunta.setPregunta(rs.getString("pregunta"));
                pregunta.setRespuestaA(rs.getString("respuestaA"));
                pregunta.setRespuestaB(rs.getString("respuestaB"));
                pregunta.setRespuestaC(rs.getString("respuestaC"));
                pregunta.setRespuestaD(rs.getString("respuestaD"));
                pregunta.setRespuestaE(rs.getString("respuestaE"));
                pregunta.setOpcionCorrecta(rs.getString("opcionCorrecta"));
                pregunta.setCorrecta(rs.getString("correcta"));
                pregunta.setNumeroPregunta(rs.getInt("numeroPregunta"));
            }

            connection.close();
            return pregunta;

          }
}
