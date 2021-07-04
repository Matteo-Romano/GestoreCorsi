/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.corsi;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.corsi.db.Studente;
import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtPeriodo"
	private TextField txtPeriodo; // Value injected by FXMLLoader

	@FXML // fx:id="txtCorso"
	private TextField txtCorso; // Value injected by FXMLLoader

	@FXML // fx:id="btnCorsiPerPeriodo"
	private Button btnCorsiPerPeriodo; // Value injected by FXMLLoader

	@FXML // fx:id="btnNumeroStudenti"
	private Button btnNumeroStudenti; // Value injected by FXMLLoader

	@FXML // fx:id="btnStudenti"
	private Button btnStudenti; // Value injected by FXMLLoader

	@FXML // fx:id="btnDivisioneStudenti"
	private Button btnDivisioneStudenti; // Value injected by FXMLLoader

	@FXML // fx:id="txtRisultato"
	private TextArea txtRisultato; // Value injected by FXMLLoader

	@FXML
	void corsiPerPeriodo(ActionEvent event) {

		txtRisultato.clear();

		String periodoStringa = txtPeriodo.getText();
		int periodo;

		try {
			periodo = Integer.parseInt(periodoStringa);
		} catch (NumberFormatException ne) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
			return;
		} catch (NullPointerException npe) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
			return;
		}
		if (periodo < 1 || periodo > 2) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
			return;
		}

		List<Corso> corsi = this.model.getCorsoByPeriodo(periodo);

		/*
		 * for (Corso c: corsi) { txtRisultato.appendText(c.toString() + "\n"); }
		 */

		StringBuilder sb = new StringBuilder();
		for (Corso c : corsi) {
			sb.append(String.format("%-8s", c.getCodins()));// Stiamo dando il formato della stringa:
			// il % serve a dire che lì metteremo un placeholder
			// Il - formatta tutto a sinistra
			// Il numero 8 dice quanti caratteri sarà la colonna, e noi sappiamo che il
			// codins non può superare gli 8
			// La s indica il tipo di dato che andremo a inserire
			sb.append(String.format("%-4d ", c.getCrediti()));// d sta per intero
			sb.append(String.format("%-50s ", c.getNome()));
			sb.append(String.format("%-4d\n", c.getPd()));
		}

		txtRisultato.appendText(sb.toString());
	}

	@FXML
	void numeroStudenti(ActionEvent event) {

		txtRisultato.clear();

		String periodoStringa = txtPeriodo.getText();
		int periodo;

		try {
			periodo = Integer.parseInt(periodoStringa);
		} catch (NumberFormatException ne) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
			return;
		} catch (NullPointerException npe) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
			return;
		}
		if (periodo < 1 || periodo > 2) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
			return;
		}

		Map<Corso, Integer> corsiIscrizioni = this.model.getIscrittiByPeriodo(periodo);

		StringBuilder sb = new StringBuilder();

		for (Corso c : corsiIscrizioni.keySet()) {
			Integer n = corsiIscrizioni.get(c);
			sb.append(String.format("%-8s", c.getCodins()));// Stiamo dando il formato della stringa:
			sb.append(String.format("%-4d ", c.getCrediti()));// d sta per intero
			sb.append(String.format("%-50s ", c.getNome()));
			sb.append(String.format("%-4d", c.getPd()));
			sb.append(String.format("%-4d\n", n));
		}

		txtRisultato.appendText(sb.toString());
	}

	
	@FXML
	void stampaDivisione(ActionEvent event) {

		txtRisultato.clear();
		String codice = txtCorso.getText();

		if (!model.esisteCorso(codice)) {
			txtRisultato.setText("Il codice corso digitato non appartiene a nessun corso");
		}
		List<Studente> studenti = model.getStudentiByCorso(codice);

		if (studenti.size() == 0) {
			txtRisultato.setText("Non ci sono studenti per questo corso");
			return;
		}

		Map<String, Integer> divisione = model.getDivisioneCDS(codice);

		for (String cds : divisione.keySet()) {
			txtRisultato.appendText(cds + " " + divisione.get(cds)+ "\n");
		}
	}
	
	
	@FXML
	void stampaStudenti(ActionEvent event) {

		txtRisultato.clear();
		String codice = txtCorso.getText();

		if (!model.esisteCorso(codice)) {
			txtRisultato.setText("Il codice corso digitato non appartiene a nessun corso");
		}
		List<Studente> studenti = model.getStudentiByCorso(codice);

		if (studenti.size() == 0) {
			txtRisultato.setText("Non ci sono studenti per questo corso");
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (Studente s : studenti) {

			sb.append(String.format("%-8d", s.getMatricola()));
			sb.append(String.format("%-25s", s.getNome()));
			sb.append(String.format("%-25s", s.getCognome()));
			sb.append(String.format("%-10s", s.getCDS()) + "\n");

		}

		txtRisultato.appendText(sb.toString());

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnCorsiPerPeriodo != null
				: "fx:id=\"btnCorsiPerPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnNumeroStudenti != null
				: "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnDivisioneStudenti != null
				: "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		// Dobbiamo specificare lo stile della text area, altrimenti non riconosce le
		// colonne ed esce uno schifo
		txtRisultato.setStyle("-fx-font-family: monospace");
	}

}