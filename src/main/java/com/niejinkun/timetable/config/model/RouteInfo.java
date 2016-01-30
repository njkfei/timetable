package com.niejinkun.timetable.config.model;

import org.apache.log4j.Logger;

/**
 * 列车路径信息
 * @author sanhao
 *　demo: last: false, stazioneCorrente: true, id: "S00219", stazione: "TORINO P.NUOVA",
 */
public class RouteInfo {
	private boolean last;
	private boolean stazioneCorrente;
	private String id;
	private String stazione;
	
	
	
	
	public RouteInfo() {
		super();
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
	public RouteInfo(boolean last, boolean stazioneCorrente, String id, String stazione) {
		super();
		this.last = last;
		this.stazioneCorrente = stazioneCorrente;
		this.id = id;
		this.stazione = stazione;
	}
	@Override
	public String toString() {
		return "RouteInfo [last=" + last + ", stazioneCorrente=" + stazioneCorrente + ", id=" + id + ", stazione="
				+ stazione + "]";
	}
	
	
	
}
