package org.dmc.services.data.entities.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

import java.util.Objects;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-07T17:42:57.404Z")
public class UsageStats  {
  
  private Integer added = null;
  private Integer members = null;

  
  /**
   **/
  @JsonProperty("added")
  public Integer getAdded() {
    return added;
  }
  public void setAdded(Integer added) {
    this.added = added;
  }

  
  /**
   **/
  @JsonProperty("members")
  public Integer getMembers() {
    return members;
  }
  public void setMembers(Integer members) {
    this.members = members;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsageStats usageStats = (UsageStats) o;
    return Objects.equals(added, usageStats.added) &&
        Objects.equals(members, usageStats.members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(added, members);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsageStats {\n");
    
    sb.append("  added: ").append(added).append("\n");
    sb.append("  members: ").append(members).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
