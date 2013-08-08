/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @created 2012
 */
package org.owasp.goatdroid.webservice.fourgoats.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Reward extends BaseModel {

	public String rewardID;
	ArrayList<RewardField> rewards;

	public String getRewardID() {
		return rewardID;
	}

	public void setRewardID(String rewardID) {
		this.rewardID = rewardID;
	}

	public ArrayList<RewardField> getRewards() {
		return rewards;
	}

	public void setRewards(ArrayList<RewardField> rewards) {
		this.rewards = rewards;
	}
}
