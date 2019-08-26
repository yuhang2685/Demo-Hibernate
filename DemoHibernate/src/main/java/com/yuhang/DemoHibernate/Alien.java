package com.yuhang.DemoHibernate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author yuhang
 * @Entity may specify the entity name as well as the table name, because table name is derived from entity name, 
 * e.g., @Entity(name="alien_table");
 * @Table specifies table name, e.g., @Table(name="alien_table");
 * @Column specifies the column name, e.g., @Column(name="alien_name");
 * @Transient makes the field not being stored as a column
 * 
 */

@Entity
public class Alien {

	@Id
	private int aid;
	private String aname;
	private int points;
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	@Override
	public String toString() {
		return "Alien [aid=" + aid + ", aname=" + aname + ", points=" + points + "]";
	}
	
	
}
