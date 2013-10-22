package org.ujobs.service.data;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.ujobs.model.Tag;
import org.ujobs.service.exceptions.DataValidityException;

import com.google.common.base.Optional;

public class TagServiceTest extends DaoDependentTest {
	
	@Test
	public void saveTag() throws DataValidityException
	{
		TagService tagService = new TagServiceImpl(this.getTagDao());
		Tag tag = new Tag();
		ObjectId tagId = ObjectId.get();
		tag.setName("Joder");
		tag.setId(tagId);
		Tag savedTag = tagService.createTag(tag);
		
		Assert.assertNotEquals("Created tag id has not been replaced!",tagId,savedTag.getId()); 
		
	}
	
	@Test(expected=DataValidityException.class)
	public void saveTagNoName() throws DataValidityException {
		TagService tagService = new TagServiceImpl(this.getTagDao());
		Tag tag = new Tag();
		ObjectId tagId = ObjectId.get();
		tag.setName(null);
		tag.setId(tagId);
		tagService.createTag(tag);
	}
	
	@Test(expected=DataValidityException.class)
	public void saveAbilityAlreadyExisting() throws DataValidityException {
		TagService tagService = new TagServiceImpl(this.getTagDao());
		Tag tag = new Tag();
		tag.setName("TestName");
		tagService.createTag(tag);
		
		Tag sameTag = new Tag();
		sameTag.setName("TestName");
		tagService.createTag(sameTag);
		
	}
	

	@Test
	public void findAbility() throws DataValidityException {
		TagService tagService = new TagServiceImpl(this.getTagDao());
		Tag tag = new Tag();
		tag.setName("findTagTestName");
		tag = tagService.createTag(tag);
		
		// Now find the ability
		Optional<Tag> recoveredTag = tagService.findTagByName("findTagTestName");
		Assert.assertTrue("Ability with name TestName has not been found!", recoveredTag.isPresent());
		Assert.assertEquals("Recovered tag is not the same than saved", tag, recoveredTag.get());
	}
	
	@Test
	public void findNonExistingAbility() {
		TagService tagService = new TagServiceImpl(this.getTagDao());
		Optional<Tag> recoveredTag =  tagService.findTagByName("nonExistingTag");
		Assert.assertFalse("Tag with name nonExistingAbility has been found!", recoveredTag.isPresent());
	}
}
