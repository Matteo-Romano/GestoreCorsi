package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
				Corso c = new Corso(rs.getString("codins"), rs.getString("nome"), rs.getInt("crediti"),
						rs.getInt("pd"));
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

	// Potremmo creare un'altra classe oggetto, uguale a Corso ma con in più il
	// numero di iscritti
	// Ma è più facile usare le mappe

	public Map<Corso, Integer> getIscrittiByPeriodo(Integer periodo) {

		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot FROM corso c, iscrizione i WHERE c.codins = i.codins AND c.pd= ? GROUP BY c.codins, c.nome, c.crediti, c.pd ORDER BY tot DESC";

		Map<Corso, Integer> result = new HashMap<Corso, Integer>();

		try {

			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getString("nome"), rs.getInt("crediti"),
						rs.getInt("pd"));
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

	public List<Studente> getStudentiByCorso(Corso corso) {

		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS " + "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = ? ";

		List<Studente> result = new LinkedList<Studente>();

		try {

			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"),
						rs.getString("CDS"));
				result.add(s);
			}

			rs.close();
			conn.close();
			st.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return result;
	}

	public boolean esisteCorso(Corso corso) {
		String sql = "SELECT DISTINCT codins FROM corso c WHERE c.codins = ?";

		try {

			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				
				rs.close();
				conn.close();
				st.close();
				return true;
				
			} else {
				
				rs.close();
				conn.close();
				st.close();
				return false;
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public Map<String, Integer> getDivisioneStudenti(Corso corso) {
		String sql = "SELECT s.CDS, COUNT(*) AS tot "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola AND i.codins = ? AND s.CDS <> '' "
				+ "GROUP BY s.CDS";
		Map<String, Integer> divisione = new HashMap<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				divisione.put(rs.getString("CDS"), rs.getInt("tot"));
			}

			rs.close();
			conn.close();
			st.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return divisione;
	}
}
