package com.niejinkun.timetable.model;

/**
 * 列车路径信息
 * @author sanhao
 *　demo: last: false, stazioneCorrente: true, id: "S00219", stazione: "TORINO P.NUOVA",
 */
public class RouteInfo {
	private String trainno;
	private boolean last;
	private boolean stazioneCorrente;
	private int seq;
	private String id;
	private String stazione;
	private boolean first;
	private Fermate fermate;

	
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTrainno() {
		return trainno;
	}
	public void setTrainno(String trainno) {
		this.trainno = trainno;
	}
	public Fermate getFermate() {
		return fermate;
	}
	public void setFermate(Fermate fermate) {
		this.fermate = fermate;
	}

	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public boolean isStazioneCorrente() {
		return stazioneCorrente;
	}
	public void setStazioneCorrente(boolean stazioneCorrente) {
		this.stazioneCorrente = stazioneCorrente;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStazione() {
		return stazione;
	}
	public void setStazione(String stazione) {
		this.stazione = stazione;
	}
	
	
	public String getOrientamento() {
		return this.fermate.getOrientamento();
	}
	public void setOrientamento(String orientamento) {
		this.fermate.setOrientamento(orientamento);
	}
	public String getProgrammata() {
		return this.fermate.getProgrammata();
	}
	public void setProgrammata(String programmata) {
		this.fermate.setOrientamento(programmata);
	}
	public String getPartenza_teorica() {
		return this.fermate.getPartenza_teorica();
	}
	public void setPartenza_teorica(String partenza_teorica) {
		this.fermate.setOrientamento(partenza_teorica);
	}
	public String getArrivo_teorico() {
		return this.fermate.getArrivo_teorico();
	}
	public void setArrivo_teorico(String arrivo_teorico) {
		this.fermate.setOrientamento(arrivo_teorico);
	}
	public String getTipoFermata() {
		return this.fermate.getTipoFermata();
	}
	public void setTipoFermata(String tipoFermata) {
		this.fermate.setOrientamento(tipoFermata);
	}
	
	public int getProgressivo() {
		return this.fermate.getProgressivo();
	}
	public void setProgressivo(int progressivo) {
		this.fermate.setProgressivo(progressivo);
	}
	
	public RouteInfo(boolean last, boolean stazioneCorrente, String id, String stazione) {
		super();
		this.last = last;
		this.stazioneCorrente = stazioneCorrente;
		this.id = id;
		this.stazione = stazione;
		
		this.fermate = new Fermate();
	}
	
	public  RouteInfo(){
		this.fermate = new Fermate();
	}
	
	public boolean getFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	@Override
	public String toString() {
		return "RouteInfo [trainno=" + trainno + ", last=" + last + ", stazioneCorrente=" + stazioneCorrente
				+ ", seq=" + seq + ", id=" + id + ", stazione=" + stazione + ", first=" + first + ", fermate="
				+ fermate + "]";
	}
	
	
	
}
