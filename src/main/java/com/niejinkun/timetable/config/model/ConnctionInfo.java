package com.niejinkun.timetable.config.model;

import org.apache.log4j.Logger;

/**
 * 车站中转信息
 * @author sanhao
 *　demo: numeroTreno: 4332, categoria: "REG", categoriaDescrizione: null, origine: null, codOrigine: "S00219"
 */
public class ConnctionInfo {
	private static Logger logger = Logger.getLogger(ConnctionInfo.class);
	private String numeroTreno; // 车次号
	private String categoria; // 列车类型
	private String codOrigine; // 发站编号
	private String compOrarioPartenza; // 发车时间
	private long orarioPartenza; // 发车时间
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		ConnctionInfo.logger = logger;
	}
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
	public String getCodOrigine() {
		return codOrigine;
	}
	public void setCodOrigine(String codOrigine) {
		this.codOrigine = codOrigine;
	}
	public String getCompOrarioPartenza() {
		return compOrarioPartenza;
	}
	public void setCompOrarioPartenza(String compOrarioPartenza) {
		this.compOrarioPartenza = compOrarioPartenza;
	}
	public long getOrarioPartenza() {
		return orarioPartenza;
	}
	public void setOrarioPartenza(long orarioPartenza) {
		this.orarioPartenza = orarioPartenza;
	}
	@Override
	public String toString() {
		return "ConnctionInfo [numeroTreno=" + numeroTreno + ", categoria=" + categoria + ", codOrigine=" + codOrigine
				+ ", compOrarioPartenza=" + compOrarioPartenza + ", orarioPartenza=" + orarioPartenza + "]";
	}
	
	
	
	
	
}
