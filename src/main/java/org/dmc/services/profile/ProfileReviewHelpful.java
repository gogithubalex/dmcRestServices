package org.dmc.services.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-08T14:26:00.636Z")
public class ProfileReviewHelpful  {
  
  private String id = null;
  private String reviewId = null;
  private String accountId = null;
  private Boolean helpfull = null;

  
  /**
   **/
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  
  /**
   **/
  @JsonProperty("reviewId")
  public String getReviewId() {
    return reviewId;
  }
  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  
  /**
   **/
  @JsonProperty("accountId")
  public String getAccountId() {
    return accountId;
  }
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  
  /**
   **/
  @JsonProperty("helpfull")
  public Boolean getHelpfull() {
    return helpfull;
  }
  public void setHelpfull(Boolean helpfull) {
    this.helpfull = helpfull;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProfileReviewHelpful profileReviewHelpful = (ProfileReviewHelpful) o;
    return Objects.equals(id, profileReviewHelpful.id) &&
        Objects.equals(reviewId, profileReviewHelpful.reviewId) &&
        Objects.equals(accountId, profileReviewHelpful.accountId) &&
        Objects.equals(helpfull, profileReviewHelpful.helpfull);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reviewId, accountId, helpfull);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProfileReviewHelpful {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  reviewId: ").append(reviewId).append("\n");
    sb.append("  accountId: ").append(accountId).append("\n");
    sb.append("  helpfull: ").append(helpfull).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
