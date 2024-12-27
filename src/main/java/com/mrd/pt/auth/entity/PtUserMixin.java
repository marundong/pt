package com.mrd.pt.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@Data
public abstract class PtUserMixin {
	@JsonProperty
	private Long id;
	@JsonProperty
	private String username;
	@JsonProperty
	private String email;
	@JsonProperty
	private String password;


}