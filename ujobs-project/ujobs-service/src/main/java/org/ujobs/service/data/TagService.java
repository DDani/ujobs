package org.ujobs.service.data;

import org.bson.types.ObjectId;
import org.ujobs.model.Tag;
import org.ujobs.service.exceptions.DataValidityException;

import com.google.common.base.Optional;

public interface TagService {
	public Optional<Tag> getTagById(ObjectId abilityId);
	public Tag createTag(Tag tag) throws DataValidityException;
	public Optional<Tag> findTagByName(String tagName);
	
}
