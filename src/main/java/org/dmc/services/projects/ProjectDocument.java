package org.dmc.services.projects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-07T17:42:57.404Z")
public class ProjectDocument  {
  
  private int id;
  private int projectId;
  private int projectDocumentId;
  private String owner = null;
  private int ownerId;
  private String title = null;
  private int modifed;
  private int size;
  private String file = null;

  
  /**
   **/
  @JsonProperty("id")
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  
  /**
   **/
  @JsonProperty("projectId")
  public int getProjectId() {
    return projectId;
  }
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  
  /**
   **/
  @JsonProperty("project-documentId")
  public int getProjectDocumentId() {
    return projectDocumentId;
  }
  public void setProjectDocumentId(int projectDocumentId) {
    this.projectDocumentId = projectDocumentId;
  }

  
  /**
   **/
  @JsonProperty("owner")
  public String getOwner() {
    return owner;
  }
  public void setOwner(String owner) {
    this.owner = owner;
  }

  
  /**
   **/
  @JsonProperty("ownerId")
  public int getOwnerId() {
    return ownerId;
  }
  public void setOwnerId(int ownerId) {
    this.ownerId = ownerId;
  }

  
  /**
   **/
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  
  /**
   **/
  @JsonProperty("modifed")
  public int getModifed() {
    return modifed;
  }
  public void setModifed(int modifed) {
    this.modifed = modifed;
  }

  
  /**
   **/
  @JsonProperty("size")
  public int getSize() {
    return size;
  }
  public void setSize(int size) {
    this.size = size;
  }

  
  /**
   **/
  @JsonProperty("file")
  public String getFile() {
    return file;
  }
  public void setFile(String file) {
    this.file = file;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectDocument projectDocument = (ProjectDocument) o;
    return Objects.equals(id, projectDocument.id) &&
        Objects.equals(projectId, projectDocument.projectId) &&
        Objects.equals(projectDocumentId, projectDocument.projectDocumentId) &&
        Objects.equals(owner, projectDocument.owner) &&
        Objects.equals(ownerId, projectDocument.ownerId) &&
        Objects.equals(title, projectDocument.title) &&
        Objects.equals(modifed, projectDocument.modifed) &&
        Objects.equals(size, projectDocument.size) &&
        Objects.equals(file, projectDocument.file);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, projectId, projectDocumentId, owner, ownerId, title, modifed, size, file);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectDocument {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  projectId: ").append(projectId).append("\n");
    sb.append("  projectDocumentId: ").append(projectDocumentId).append("\n");
    sb.append("  owner: ").append(owner).append("\n");
    sb.append("  ownerId: ").append(ownerId).append("\n");
    sb.append("  title: ").append(title).append("\n");
    sb.append("  modifed: ").append(modifed).append("\n");
    sb.append("  size: ").append(size).append("\n");
    sb.append("  file: ").append(file).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
