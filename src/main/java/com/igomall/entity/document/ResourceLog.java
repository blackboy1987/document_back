package com.igomall.entity.document;

import com.igomall.entity.BaseEntity;
import com.igomall.entity.User;

import javax.persistence.*;

@Entity
@Table(name = "document_resource_log")
public class ResourceLog extends BaseEntity<Long> {

  private String action;

  private String ip;

  private String username;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @Lob
  private String parameter;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }
}
