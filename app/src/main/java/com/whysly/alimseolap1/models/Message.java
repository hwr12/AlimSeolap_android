package com.whysly.alimseolap1.models;

import java.util.HashMap;
import java.util.Map;

public class Message {

private int id;
private String title;
private String content;
private String redirecting_url;
private String target_gender;
private String type;
private Boolean attach;
private String created_at;
private String updated_at;
private String deleted_at;
private int user;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public int getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getContent() {
return content;
}

public void setContent(String content) {
this.content = content;
}

public String getRedirecting_url() {
return redirecting_url;
}

public void setRedirecting_url(String redirecting_url) {
this.redirecting_url = redirecting_url;
}

public String getTarget_gender() {
return target_gender;
}

public void setTarget_gender(String target_gender) {
this.target_gender = target_gender;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Boolean getAttach() {
return attach;
}

public void setAttach(Boolean attach) {
this.attach = attach;
}

public String getCreated_at() {
return created_at;
}

public void setCreated_at(String created_at) {
this.created_at = created_at;
}

public String getUpdated_at() {
return updated_at;
}

public void setUpdated_at(String updated_at) {
this.updated_at = updated_at;
}

public String getDeleted_at() {
return deleted_at;
}

public void setDeleted_at(String deleted_at) {
this.deleted_at = deleted_at;
}

public int getUser() {
return user;
}

public void setUser(Integer user) {
this.user = user;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}