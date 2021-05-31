package com.covid19army.MobileOtpService.models;

import java.io.Serializable;
import javax.persistence.*;

import com.covid19army.MobileOtpService.modelListeners.MobileVerificationQueueModelListener;

import java.util.Date;



/**
 * The persistent class for the mobileverificationqueue database table.
 * 
 */
@EntityListeners(MobileVerificationQueueModelListener.class)
@Entity
@Table(name="mobileverificationqueue")
@NamedQuery(name="MobileVerificationQueue.findAll", query="SELECT m FROM MobileVerificationQueue m")
public class MobileVerificationQueue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long itemid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_expiry")
	private Date dateExpiry;

	private long entityid;

	private String entitytype;

	private String mobilenumber;

	private int otp;
	
	private boolean isprocessed;

	public boolean getIsprocessed() {
		return isprocessed;
	}

	public void setIsprocessed(boolean isprocessed) {
		this.isprocessed = isprocessed;
	}

	public MobileVerificationQueue() {
	}

	public long getItemid() {
		return this.itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateExpiry() {
		return this.dateExpiry;
	}

	public void setDateExpiry(Date dateExpiry) {
		this.dateExpiry = dateExpiry;
	}

	public long getEntityid() {
		return this.entityid;
	}

	public void setEntityid(long entityid) {
		this.entityid = entityid;
	}

	public String getEntitytype() {
		return this.entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public String getMobilenumber() {
		return this.mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public int getOtp() {
		return this.otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

}