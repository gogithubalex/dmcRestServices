package org.dmc.services.data.entities.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Objects;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-07T17:42:57.404Z")
public class RunStats  {
  
  private Integer success = null;
  private Integer fail = null;

  
  /**
   **/
  @JsonProperty("success")
  public Integer getSuccess() {
    return success;
  }
  public void setSuccess(Integer success) {
    this.success = success;
  }

  
  /**
   **/
  @JsonProperty("fail")
  public Integer getFail() {
    return fail;
  }
  public void setFail(Integer fail) {
    this.fail = fail;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunStats runStats = (RunStats) o;
    return Objects.equals(success, runStats.success) &&
        Objects.equals(fail, runStats.fail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, fail);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class RunStats {\n");
    
    sb.append("  success: ").append(success).append("\n");
    sb.append("  fail: ").append(fail).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
