package com.base.util;

import java.util.Map;

public class Map2NodeConverter<T extends Map<String, Object>> implements NodeConverter{
	private String idSign;
	
	private String pidSign;
	
	private String sortSign;
	
	public String getIdSign() {
		return idSign;
	}

	public void setIdSign(String idSign) {
		this.idSign = idSign;
	}

	public String getPidSign() {
		return pidSign;
	}

	public void setPidSign(String pidSign) {
		this.pidSign = pidSign;
	}
	
	public String getSortSign() {
		return sortSign;
	}

	public void setSortSign(String sortSign) {
		this.sortSign = sortSign;
	}

	@Override
	public CcsTreeNode<Map<String, Object>> convert(Object obj) {
		CcsTreeNode<Map<String, Object>> node = new CcsTreeNode<Map<String, Object>>();
		Map<String, Object> map = (Map<String, Object>) obj;
		if(idSign != null) {
			node.setId(String.valueOf(map.get(idSign)));
		}
		if(pidSign != null) {
			node.setPid(String.valueOf(map.get(pidSign)));
		}
		if(sortSign != null) {
			try {
				node.setSortId(Integer.parseInt(String.valueOf(map.get(sortSign))));
			} catch (NumberFormatException e) {
				// 不做处理
			}
		}
		node.setData(map);
		return node;
	}
}
