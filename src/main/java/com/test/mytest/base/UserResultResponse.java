package com.test.mytest.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User modify result response info.
 */
public class UserResultResponse {

  @JsonProperty("ret")
  protected int result;
  
  @JsonProperty("ret_msg")
  protected String resultMessage;

  public UserResultResponse() {
    this.result = 0;
    this.resultMessage = "ok";
  }

  public int getResult() {
    return result;
  }

  public void setResult(int result) {
    this.result = result;
  }

  public String getResultMessage() {
    return resultMessage;
  }

  public void setResultMessage(String resultMessage) {
    this.resultMessage = resultMessage;
  }

  @Override
  public String toString() {
    return "UserResultResponse [result=" + result + ", resultMessage="
        + resultMessage + "]";
  }

}
