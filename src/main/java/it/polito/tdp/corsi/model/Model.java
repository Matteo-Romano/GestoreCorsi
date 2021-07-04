package it.polito.tdp.corsi.model;

//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;
import it.polito.tdp.corsi.db.Studente;

public class Model {

	private CorsoDAO corsoDao;

	public Model() {
		corsoDao = new CorsoDAO();
	}

	public List<Corso> getCorsoByPeriodo(Integer pd) {
		return corsoDao.getCorsiByPeriodo(pd);
	}

	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd) {
		return corsoDao.getIscrittiByPeriodo(pd);
	}

	public List<Studente> getStudentiByCorso(String codice) {
		return corsoDao.getStudentiByCorso(new Corso(codice, null, null, null));
	}

	public Map<String, Integer> getDivisioneCDS(String codice) {
	/*	Map<String, Integer> divisione = new HashMap<String, Integer>();
		List<Studente> studenti = this.getStudentiByCorso(codice);
		for (Studente s : studenti) {
			if (s.getCDS() != null) {
				if (divisione.get(s.getCDS()) == null) {
					divisione.put(s.getCDS(), 1);
				} else {
					divisione.put(s.getCDS(), divisione.get(s.getCDS()) + 1);
				}
			}
		}
		return divisione; ERA OBSOLETO*/ 
		return corsoDao.getDivisioneStudenti(new Corso(codice, null, null, null));
	}

	public boolean esisteCorso(String codice) {
		return corsoDao.esisteCorso(new Corso(codice, null, null, null));
	}
}