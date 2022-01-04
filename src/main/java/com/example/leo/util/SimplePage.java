package com.example.leo.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimplePage {
	@ApiModelProperty(value = "当前页面",example = "1")
	private long current;
	@ApiModelProperty(value = "每页行数",example = "20")
	private long size;
	@ApiModelProperty(value = "排序字段",example = "id")
	private String orderBy;
	@ApiModelProperty(value = "升序、降序",example = "desc")
	private String orderType;
	@ApiModelProperty(value = "是否排序",example = "true")
	private Boolean isOrder;

	public Boolean getIsOrder(){
		if (this.isOrder == null) {
			return true;
		}
		return isOrder;
	}

	public void setIsOrder(Boolean isOrder){
		if (isOrder == null) {
			this.isOrder = true;
		} else {
			this.isOrder = isOrder;
		}
	}
}
