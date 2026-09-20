package com.klef.ms.sdp.dto;

public class UserResponse
{
private Long id;
private String name;
private String email;
private String contact;
private String role;

public Long getId()
{
return id;
}

public void setId(Long id)
{
this.id = id;
}

public String getName()
{
return name;
}

public void setName(String name)
{
this.name = name;
}

public String getEmail()
{
return email;
}

public void setEmail(String email)
{
this.email = email;
}

public String getContact()
{
return contact;
}

public void setContact(String contact)
{
this.contact = contact;
}

public String getRole()
{
return role;
}

public void setRole(String role)
{
this.role = role;
}

@Override
public String toString()
{
return "UserResponse [id=" + id
+ ", name=" + name
+ ", email=" + email
+ ", contact=" + contact
+ ", role=" + role + "]";
}
}