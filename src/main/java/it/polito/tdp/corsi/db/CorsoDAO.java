package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(Integer periodo) {

		String sql = "SELECT * FROM corso WHERE pd = ?";

		List<Corso> result = new ArrayList<Corso>();

		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Corso c = new Corso (rs.getString("codins"), rs.getString("nome"), rs.getInt("crediti"), rs.getInt("pd") );
				result.add(c);
			}
			
			rs.close();
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
		return result;
	}

	//Potremmo creare un'altra classe oggetto, uguale a Corso ma con in più il numero di iscritti
	//Ma è più facile usare le mappe
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer periodo) { 
		
		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot FROM corso c, iscrizione i WHERE c.codins = i.codins AND c.pd= ? GROUP BY c.codins, c.nome, c.crediti, c.pd ORDER BY tot DESC";

		Map<Corso, Integer> result = new HashMap<Corso, Integer>();

		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Corso c = new Corso (rs.getString("codins"), rs.getString("nome"), rs.getInt("crediti"), rs.getInt("pd") );
				Integer n = rs.getInt("tot");
				result.put(c, n);
				
			}
			
			rs.close();
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
		return result;
	}
}
