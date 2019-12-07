package com.sxt.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author sunshuaiyong
* @version 创建时间：2019年12月7日 上午10:12:22
* @Description 类描述
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private String address;
	
}

