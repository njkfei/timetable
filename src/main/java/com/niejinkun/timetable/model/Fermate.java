package com.niejinkun.timetable.model;

/**
 * 停站信息
 * @author sanhao
 * demo:
 *       "fermata":{  
         "orientamento":"B",
         "kcNumTreno":null,
         "stazione":"MILANO ROGOREDO",
         "id":"S01820",
         "listaCorrispondenze":null,
         "programmata":1457194020000,
         "programmataZero":null,
         "effettiva":null,
         "ritardo":0,
         "partenzaTeoricaZero":null,
         "arrivoTeoricoZero":null,
         "partenza_teorica":1457194140000,
         "arrivo_teorico":1457194020000,
         "isNextChanged":false,
         "partenzaReale":null,
         "arrivoReale":null,
         "ritardoPartenza":0,
         "ritardoArrivo":0,
         "progressivo":23,
         "binarioEffettivoArrivoCodice":null,
         "binarioEffettivoArrivoTipo":null,
         "binarioEffettivoArrivoDescrizione":null,
         "binarioProgrammatoArrivoCodice":null,
         "binarioProgrammatoArrivoDescrizione":"8              ",
         "binarioEffettivoPartenzaCodice":null,
         "binarioEffettivoPartenzaTipo":null,
         "binarioEffettivoPartenzaDescrizione":null,
         "binarioProgrammatoPartenzaCodice":null,
         "binarioProgrammatoPartenzaDescrizione":"8              ",
         "tipoFermata":"F",
         "visualizzaPrevista":true,
         "nextChanged":false,
         "nextTrattaType":2,
         "actualFermataType":0
      },
 */
public class Fermate {
	private String orientamento;
	private String programmata;
	private String partenza_teorica;
	private String arrivo_teorico;
	private String tipoFermata;
	private int progressivo;
	
	
	
	public int getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}
	public String getOrientamento() {
		return orientamento;
	}
	public void setOrientamento(String orientamento) {
		this.orientamento = orientamento;
	}
	public String getProgrammata() {
		return programmata;
	}
	public void setProgrammata(String programmata) {
		this.programmata = programmata;
	}
	public String getPartenza_teorica() {
		return partenza_teorica;
	}
	public void setPartenza_teorica(String partenza_teorica) {
		this.partenza_teorica = partenza_teorica;
	}
	public String getArrivo_teorico() {
		return arrivo_teorico;
	}
	public void setArrivo_teorico(String arrivo_teorico) {
		this.arrivo_teorico = arrivo_teorico;
	}
	public String getTipoFermata() {
		return tipoFermata;
	}
	public void setTipoFermata(String tipoFermata) {
		this.tipoFermata = tipoFermata;
	}
	@Override
	public String toString() {
		return "Fermate [orientamento=" + orientamento + ", programmata=" + programmata + ", partenza_teorica="
				+ partenza_teorica + ", arrivo_teorico=" + arrivo_teorico + ", tipoFermata=" + tipoFermata
				+ ", progressivo=" + progressivo + "]";
	}
	
	
	
}
