package com.test.mytest.base;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User ID request info.
 */
public class UserIDRequest extends BaseRequest {

  @JsonProperty("id")
  @NotNull(message = "id param is null")
  @Min(value = 1, message = "id param must be great or equal than 1")
  protected long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "UserIDRequest [id=" + id + "]";
  }

}
