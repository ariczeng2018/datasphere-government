/*
 * Copyright 2019, Huahuidata, Inc.
 * DataSphere is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v1 for more details.
 */

package com.datasphere.government.datalineage.gsp.dataflow.model;

import java.util.ArrayList;
import java.util.List;

import com.datasphere.government.datalineage.gsp.util.Pair;

import gudusoft.gsqlparser.TSourceToken;
import gudusoft.gsqlparser.nodes.TConstant;
import gudusoft.gsqlparser.nodes.TObjectName;

public class TableColumn
{

	public static int TABLE_COLUMN_ID = 0;

	private Table table;

	private int id;
	private String name;

	private Pair<Long, Long> startPosition;
	private Pair<Long, Long> endPosition;

	private TObjectName columnObject;
	private List<TObjectName> starLinkColumns = new ArrayList<TObjectName>( );

	public TableColumn( Table table, TObjectName columnObject )
	{
		if ( table == null || columnObject == null )
			throw new IllegalArgumentException( "TableColumn arguments can't be null." );

		id = ++TABLE_COLUMN_ID;

		this.columnObject = columnObject;

		TSourceToken startToken = columnObject.getStartToken( );
		TSourceToken endToken = columnObject.getEndToken( );
		this.startPosition = new Pair<Long, Long>( startToken.lineNo,
				startToken.columnNo );
		this.endPosition = new Pair<Long, Long>( endToken.lineNo,
				endToken.columnNo + endToken.astext.length( ) );

		if ( columnObject.getColumnNameOnly( ) != null
				&& !"".equals( columnObject.getColumnNameOnly( ) ) )
		{
			this.name = columnObject.getColumnNameOnly( );
		}
		else
		{
			this.name = columnObject.toString( );
		}

		this.table = table;
		table.addColumn( this );
	}

	public TableColumn( Table table, TConstant columnObject, int columnIndex )
	{
		if ( table == null || columnObject == null )
			throw new IllegalArgumentException( "TableColumn arguments can't be null." );

		id = ++TABLE_COLUMN_ID;

		TSourceToken startToken = columnObject.getStartToken( );
		TSourceToken endToken = columnObject.getEndToken( );
		this.startPosition = new Pair<Long, Long>( startToken.lineNo,
				startToken.columnNo );
		this.endPosition = new Pair<Long, Long>( endToken.lineNo,
				endToken.columnNo + endToken.astext.length( ) );

		this.name = "DUMMY" + columnIndex;

		this.table = table;
		table.addColumn( this );
	}

	public Table getTable( )
	{
		return table;
	}

	public int getId( )
	{
		return id;
	}

	public String getName( )
	{
		return name;
	}

	public Pair<Long, Long> getStartPosition( )
	{
		return startPosition;
	}

	public Pair<Long, Long> getEndPosition( )
	{
		return endPosition;
	}

	public TObjectName getColumnObject( )
	{
		return columnObject;
	}

	public void bindStarLinkColumns( List<TObjectName> starLinkColumns )
	{
		if ( starLinkColumns != null && !starLinkColumns.isEmpty( ) )
		{
			this.starLinkColumns = starLinkColumns;
		}
	}

	public List<TObjectName> getStarLinkColumns( )
	{
		return starLinkColumns;
	}

}
