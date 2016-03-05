package com.niejinkun.timetable.model;

/**
 * 车次详细信息
 * @author sanhao
 *　demo: 
 *anormalita: null
binarioEffettivoArrivoCodice: null
binarioEffettivoArrivoDescrizione: null
binarioEffettivoArrivoTipo: null
binarioEffettivoPartenzaCodice: null
binarioEffettivoPartenzaDescrizione: null
binarioEffettivoPartenzaTipo: null
binarioProgrammatoArrivoCodice: null
binarioProgrammatoArrivoDescrizione: null
binarioProgrammatoPartenzaCodice: null
binarioProgrammatoPartenzaDescrizione: null
cambiNumero: []
categoria: "ES*"
categoriaDescrizione: null
circolante: true
codDestinazione: null
codOrigine: null
codiceCliente: 1
compClassRitardoLine: "regolare_line"
compClassRitardoTxt: ""
compDurata: "4:30"
compImgCambiNumerazione: "&nbsp;&nbsp;"
compImgRitardo: "/vt_static/img/legenda/icone_legenda/regolare.png"
compImgRitardo2: ""
compInStazioneArrivo: ["", "", "", "", "", "", "", "", ""]
compInStazionePartenza: ["", "", "", "", "", "", "", "", ""]
compNumeroTreno: "ES* FR 9559"
compOraUltimoRilevamento: "--"
compOrarioArrivo: "23:40"
compOrarioArrivoZero: "23:40"
compOrarioArrivoZeroEffettivo: "23:40"
compOrarioEffettivoArrivo: "/vt_static/img/legenda/icone_legenda/regolare.png23:40"
compOrarioPartenza: "19:10"
compOrarioPartenzaZero: "19:10"
compOrarioPartenzaZeroEffettivo: "19:10"
compOrientamento: ["--", "--", "--", "--", "--", "--", "--", "--", "--"]
compRitardo: ["in orario", "on time", "p&#252;nktlich", "&agrave; l'heure", "en horario", "conform orarului", "定刻",…]
compRitardoAndamento: ["in orario", "on time", "p&#252;nktlich", "&agrave; l'heure", "en horario", "conform orarului", "定刻",…]
compTipologiaTreno: "nazionale"
corrispondenze: null
dataPartenza: null
descOrientamento: ["--", "--", "--", "--", "--", "--", "--", "--", "--"]
descrizioneVCO: ""
destinazione: "ROMA TERMINI"
destinazioneEstera: null
destinazioneZero: "ROMA TERMINI"
esisteCorsaZero: "0"
fermate: [{orientamento: "B", kcNumTreno: null, stazione: "TORINO P.NUOVA", id: "S00219",…},…]
fermateSoppresse: null
haCambiNumero: false
hasProvvedimenti: false
idDestinazione: "S08409"
idOrigine: "S00219"
inStazione: false
motivoRitardoPrevalente: null
nonPartito: false
numeroTreno: 9559
oraArrivoEstera: null
oraPartenzaEstera: null
oraUltimoRilevamento: null
orarioArrivo: 1454193600000
orarioArrivoZero: 1454193600000
orarioPartenza: 1454177400000
orarioPartenzaZero: 1454177400000
orientamento: ""
origine: "TORINO P.NUOVA"
origineEstera: null
origineZero: "TORINO P.NUOVA"
provvedimenti: null
provvedimento: 0
regione: 0
riprogrammazione: null
ritardo: 0
segnalazioni: null
servizi: []
statoTreno: null
stazioneArrivo: null
stazionePartenza: null
stazioneUltimoRilevamento: "--"
subTitle: ""
tipoProdotto: "100"
tipoTreno: "PG"
tratta: 0
 */
public class TrainDetailInfo {
	private String numeroTreno;
	private String categoria;
	private String idOrigine;
	private String idDestinazione;
	
	private String compOrarioPartenza;
	private String compOrarioArrivo;
	
	private String origine;
	private String destinazione;
	public String getNumeroTreno() {
		return numeroTreno;
	}
	public void setNumeroTreno(String numeroTreno) {
		this.numeroTreno = numeroTreno;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getIdOrigine() {
		return idOrigine;
	}
	public void setIdOrigine(String idOrigine) {
		this.idOrigine = idOrigine;
	}
	public String getIdDestinazione() {
		return idDestinazione;
	}
	public void setIdDestinazione(String idDestinazione) {
		this.idDestinazione = idDestinazione;
	}
	public String getCompOrarioPartenza() {
		return compOrarioPartenza;
	}
	public void setCompOrarioPartenza(String compOrarioPartenza) {
		this.compOrarioPartenza = compOrarioPartenza;
	}
	public String getCompOrarioArrivo() {
		return compOrarioArrivo;
	}
	public void setCompOrarioArrivo(String compOrarioArrivo) {
		this.compOrarioArrivo = compOrarioArrivo;
	}
	public String getOrigine() {
		return origine;
	}
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	public String getDestinazione() {
		return destinazione;
	}
	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}
	@Override
	public String toString() {
		return "TrainDetailInfo [numeroTreno=" + numeroTreno + ", categoria=" + categoria + ", idOrigine=" + idOrigine
				+ ", idDestinazione=" + idDestinazione + ", compOrarioPartenza=" + compOrarioPartenza
				+ ", compOrarioArrivo=" + compOrarioArrivo + ", origine=" + origine + ", destinazione=" + destinazione
				+ "]";
	}
	
	
}
