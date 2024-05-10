package tunihealth.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tunihealth.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

	List<Post> findAll();
}
