package com.niejinkun.timetable.config.model;

import org.apache.log4j.Logger;

/**
 * 车站基本信息
 * @author sanhao
 *　demo: 9559 - TORINO PORTA NUOVA|9559-S00219
 */
public class TrainBaseInfo {
	private static Logger logger = Logger.getLogger(TrainBaseInfo.class);
	private String train_no;
	private String station;
	private String station_no;
	
	
	
	public TrainBaseInfo(String line) {
		String[] parts = line.replaceAll("(\r\n|\n\r|\r|\n)", "").split("\\|");
		
		if(parts.length == 2){
			
			
			String[] subparts = parts[0].split("-");
			
			if(subparts.length == 2){
				this.train_no = subparts[0].trim();
				this.station = subparts[1].trim();
			}else{
				logger.error("parse error : " + line);
			}
			
			subparts = parts[1].split("-");
			
			if(subparts.length == 2){
				this.station_no = parts[1].trim();
			}else{
				logger.error("parse error : " + line);
			}
			
			this.station_no = parts[1].replaceFirst(".*-", "");
			
		}else{
			logger.error("parse error : " + line);
		}
	}
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getStation_no() {
		return station_no;
	}
	public void setStation_no(String station_no) {
		this.station_no = station_no;
	}
	
	@Override
	public String toString() {
		return "TrainBaseInfo [train_no=" + train_no + ", station=" + station + ", station_no=" + station_no + "]";
	}
	
	public static  void main(String[] args){
		String line = "9559 - TORINO PORTA NUOVA|9559-S00219\r\n";
		
		System.out.println(line);
		
		TrainBaseInfo info = new TrainBaseInfo(line);
		
		System.out.println(info.toString());
	}
	
}
