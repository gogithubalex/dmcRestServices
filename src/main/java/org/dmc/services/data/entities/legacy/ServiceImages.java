package org.dmc.services.data.entities.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;



/* 
 * A Java Object to Encapsulate the URLs of uploaded pictures to S3
 * 
 */

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-07T17:42:57.404Z")
public class ServiceImages  {
  
  private int id;
  private int serviceId;
  private String url = null;

  
  
  public ServiceImages() {
  	this.id = -1;
  	this.serviceId = -1;
  	this.url =  new String();
  }
 
  
 
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
  @JsonProperty("serviceId")
  public int getServiceId() {
    return serviceId;
  }
  public void setServiceId(int serviceId) {
    this.serviceId = serviceId;
  }

  
  /**
   **/
  @JsonProperty("url")
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceImages serviceImages = (ServiceImages) o;
    return Objects.equals(id, serviceImages.id) &&
        Objects.equals(serviceId, serviceImages.serviceId) &&
        Objects.equals(url, serviceImages.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, serviceId, url);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceImages {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  serviceId: ").append(serviceId).append("\n");
    sb.append("  url: ").append(url).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
  

}
