package org.ujobs.service.data;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.ujobs.model.Tag;
import org.ujobs.service.data.dao.Dao;
import org.ujobs.service.exceptions.DataValidityException;

import com.google.common.base.Optional;


public class TagServiceImpl implements TagService {

	private Dao<Tag,ObjectId> tagDAO;
	
	public TagServiceImpl(Dao<Tag,ObjectId> tagDAO) {
		this.tagDAO = tagDAO;
	}
	
	@Override
	public Optional<Tag> getTagById(ObjectId abilityId) {
		return Optional.fromNullable(tagDAO.get(abilityId));
	}

	@Override
	public Tag createTag(Tag tag) throws DataValidityException {
		if (StringUtils.isEmpty(tag.getName())) {
			throw new DataValidityException("Ability name can not be empty!");
		}
		// Remove id if set
		tag.setId(null);
		// Check that there is not other ability with the same name
		if (findTagByName(tag.getName()).isPresent() == false) 
		{
			Key<Tag> tagKey = tagDAO.save(tag);
			tag.setId((ObjectId) tagKey.getId());
		} else {
			throw new DataValidityException("Tag with " + tag.getName() + " already exists");
		}
		return tag;
	}

	@Override
	public Optional<Tag> findTagByName(String tagName) {
		Query<Tag> query = tagDAO.createQuery();
		query = query.field("name").equal(tagName);
		return Optional.fromNullable(tagDAO.findOne(query));
	}

}
