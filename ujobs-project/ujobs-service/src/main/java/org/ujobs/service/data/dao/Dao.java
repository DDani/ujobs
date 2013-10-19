package org.ujobs.service.data.dao;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.mongodb.Mongo;

public class Dao<T, K> extends BasicDAO<T, K>
{
	@Inject
    public Dao(TypeLiteral<T> dtoType, Mongo mongo, Morphia morphia, @Named("databaseName") String dbName)
    {
        super((Class<T>)dtoType.getRawType(), mongo, morphia, dbName);
    }

	public UpdateResults<T> update(Query<T> q, UpdateOperations<T> ops, boolean createIfMissing)
	{
		return getDatastore().update(q, ops, createIfMissing);
	}
	
	
	public UpdateResults<T> update(T entity, UpdateOperations<T> ops)
	{
		return getDatastore().update(entity, ops);
	}
	

	/**
	 * Find the first Entity from the Query, and modify it.
	 * @param query the query to find the Entity with; You are not allowed to offset/skip in the query.
	 * @param oldVersion indicated the old version of the Entity should be returned
	 * @return The Entity (the result of the update if oldVersion is false)
	 */
	public T findAndModify(Query<T> query, UpdateOperations<T> update,  boolean oldVersion)
	{
		return (T) getDatastore().findAndModify(query, update, oldVersion);
	}

	/**
	 * Find the first Entity from the Query, and modify it.
	 * @param query the query to find the Entity with; You are not allowed to offset/skip in the query.
	 * @param oldVersion indicated the old version of the Entity should be returned
	 * @param createIfMissing if the query returns no results, then a new object will be created (sets upsert=true)
	 * @return The Entity (the result of the update if oldVersion is false)
	 */
	public T findAndModify(Query<T> query, UpdateOperations<T> update,  boolean oldVersion, boolean createIfMissing)
	{
		return (T) getDatastore().findAndModify(query, update, oldVersion, createIfMissing);
	}
}
