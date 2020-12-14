package com.larkea.boot.core.data;

import java.io.Serializable;

import com.larkea.boot.core.util.StringPool;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TableAliasField implements Serializable {

	private String tableAlias;

	private String field;

	public TableAliasField() {
	}

	public TableAliasField(String field) {
		this.tableAlias = StringPool.BLANK;
		this.field = field;
	}

	public TableAliasField(String tableAlias, String field) {
		this.tableAlias = tableAlias;
		this.field = field;
	}
}
