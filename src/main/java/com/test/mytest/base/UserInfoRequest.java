package com.test.mytest.base;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User info request info.
 */
public class UserInfoRequest extends UserIDRequest {

  @JsonProperty("name")
  @NotNull(message = "name param is null")
  @Size(min = 1, message = "name param is empty")
  protected String userName; // 变量名与请求参数名不一样，在@RequestBody中用到

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "UserInfoRequest [userName=" + userName + ", id=" + id + "]";
  }

}
