package org.dmc.services.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "organization_dmdii_type")
public class DMDIIType extends BaseEntity {
	
	@Id
	@SequenceGenerator(name = "dmdiiTypeSeqGen", sequenceName = "organization_dmdii_type_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dmdiiTypeSeqGen")
	private Integer id;
	
	@Column(name = "dmdii_member_desc")
	private String memberDescription;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMemberDescription() {
		return this.memberDescription;
	}
	
	public void setMemberDescription(String memberDescription) {
		this.memberDescription = memberDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((memberDescription == null) ? 0 : memberDescription.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DMDIIType other = (DMDIIType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (memberDescription == null) {
			if (other.memberDescription != null)
				return false;
		} else if (!memberDescription.equals(other.memberDescription))
			return false;
		return true;
	}
	
}
