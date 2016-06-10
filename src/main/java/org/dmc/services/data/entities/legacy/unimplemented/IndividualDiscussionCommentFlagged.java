package org.dmc.services.data.entities.legacy.unimplemented;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-08T14:26:00.636Z")
public class IndividualDiscussionCommentFlagged  {
  
  private String id = null;
  private String commentId = null;
  private String accountId = null;

  
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
  @JsonProperty("commentId")
  public String getCommentId() {
    return commentId;
  }
  public void setCommentId(String commentId) {
    this.commentId = commentId;
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

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IndividualDiscussionCommentFlagged individualDiscussionCommentFlagged = (IndividualDiscussionCommentFlagged) o;
    return Objects.equals(id, individualDiscussionCommentFlagged.id) &&
        Objects.equals(commentId, individualDiscussionCommentFlagged.commentId) &&
        Objects.equals(accountId, individualDiscussionCommentFlagged.accountId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, commentId, accountId);
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class IndividualDiscussionCommentFlagged {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  commentId: ").append(commentId).append("\n");
    sb.append("  accountId: ").append(accountId).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
