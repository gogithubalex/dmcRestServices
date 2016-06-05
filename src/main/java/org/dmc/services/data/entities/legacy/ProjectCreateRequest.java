package org.dmc.services.data.entities.legacy;

//import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// JSON sent by frontend as of 10-Mar-2016
// {
//   description: "adfa"
//   dueDate: "1457413200000"
//   featureImage: {thumbnail: "/images/project_relay_controller.png", large: "/images/project_relay_controller.png"}
//   projectManager: "DMC member"
//   title: "cathy3"
//   type: "private"
// }

public class ProjectCreateRequest {
	private String title = null;
	private String description = null; 
	private String projectType = null;
	private long dueDate = 0;
	
//	@JsonCreator
//	public ProjectCreateRequest(@JsonProperty("name") String name, @JsonProperty("description") String description)
//	{
//		this.name = name;
//		this.description = description;
//	}
	
	public ProjectCreateRequest() { 
	}

	@JsonProperty("title")
    public String getTitle(){
		return title;
	}
	
	@JsonProperty("title")
	public void setTitle(String value){
		title = value;
	}
	
	@JsonProperty("description")
	public String getDescription(){
		return description;
	}
	
	@JsonProperty("description")
	public void setDescription(String value){
		description = value;
	}

	@JsonProperty("dueDate")
	public long getDueDate(){
		return dueDate;
	}

	@JsonProperty("dueDate")
	public void setDueDate(long value){
		dueDate = value;
	}
	@JsonProperty("type")
	public String getProjectType(){
		return projectType;
	}

	@JsonProperty("type")
	public void setProjectType(String value){
		projectType = value;
	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {}
		
		return null;
	}
}
