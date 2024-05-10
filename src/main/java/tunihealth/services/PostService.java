package tunihealth.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tunihealth.models.Post;
import tunihealth.repositories.PostRepository;

@Service
public class PostService {

	// inject PostRepository using @Autowired annotation
	@Autowired
	private PostRepository repo;
	
	// add new post
	public Post create(Post post) {
		return this.repo.save(post);
	}
	
	// get all posts
	public List<Post> getAll(){
		return this.repo.findAll();
	}
	
	// get post by id
	public Post getById(Long id) {
		Optional<Post> optionalPost=this.repo.findById(id);
		if(optionalPost.isPresent()) {
			return optionalPost.get();
		}
		return null;
	}
	
	
}
