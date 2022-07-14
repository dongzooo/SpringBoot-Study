package com.kwak.movie;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.kwak.movie.model.Member;
import com.kwak.movie.model.Movie;
import com.kwak.movie.model.MovieImage;
import com.kwak.movie.model.Review;
import com.kwak.movie.repository.MemberRepository;
import com.kwak.movie.repository.MovieImageRepository;
import com.kwak.movie.repository.MovieRepository;
import com.kwak.movie.repository.ReviewRepository;

@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieImageRepository movieImageRepository;
	

	
//	@Test
//	@Transactional
//	@Commit
	public void insertMovie() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Movie movie = Movie.builder().title("Movie..." + i).build();
			movieRepository.save(movie);
			
			int count = (int)(Math.random() *5)+1;
			for(int j=0; j <count;j++) {
				MovieImage movieImage = MovieImage.builder()
						.uuid(UUID.randomUUID().toString())
						.movie(movie)
						.imgName("test"+j+".jpg")
						.build();
				movieImageRepository.save(movieImage);
			}
		});
	}
	
	@Autowired
	private MemberRepository memberRepository;
	
//	@Test
//	@Transactional
//	@Commit
    public void insertMembers() {
            IntStream.rangeClosed(1,100).forEach(i -> {
                Member member = Member.builder()
                        .email("r"+i +"@gmail.com")
                        .pw("1111")
                        .nickname("reviewer"+i).build();
                memberRepository.save(member);
            });
    }
	
	
	
	@Autowired
	private ReviewRepository reviewRepository;
	//Many to one이 적용되어 삽일할 때 조심해야 한다!
	@Test
	public void insertMoviewReviews() {
	            //200개의 리뷰를 등록
	            IntStream.rangeClosed(1,200).forEach(i -> {
	                //영화 번호
	                Long mno = (long)(Math.random()*100) + 1;
	                //리뷰어 번호
	                Long mid  =  ((long)(Math.random()*100) + 1 );
	                Member member = Member.builder().mid(mid).build();
	                Review movieReview = Review.builder()
	                        .member(member)
	                        .movie(Movie.builder().mno(mno).build())
	                        .grade((int)(Math.random()* 5) + 1)
	                        .text("이 영화에 대한 느낌..."+i)
	                        .build();
	                reviewRepository.save(movieReview);
	            });
	}
	
	
//	@Test
	public void testListPage(){
	            PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "mno"));
	            Page<Object[]> result = movieRepository.getListPage(pageRequest);
	            for (Object[] objects : result.getContent()) {
	                System.out.println(Arrays.toString(objects));
	            }
	}
	
//	@Test
	public void testGetMovieWithAll() {
        List<Object[]> result = movieRepository.getMovieWithAll(92L);
        System.out.println(result);
        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
	}
	
//	@Test
	public void testGetMovieReviews() {

	        Movie movie = Movie.builder().mno(92L).build();

	        List<Review> list = reviewRepository.findByMovie(movie);
	        
	        for(Review review : list) {
	        	System.out.println(review.getMember().getEmail());
	        }
//	        list.forEach(movieReview -> {
//
//	            System.out.print(movieReview.getReviewnum());
//	            System.out.print("\t"+movieReview.getGrade());
//	            System.out.print("\t"+movieReview.getText());
//	            System.out.print("\t"+movieReview.getMember().getEmail());
//	            System.out.println("---------------------------");
//	        });
	}
	
//	@Test
//	@Transactional
//	@Commit
	public void testDeleteMember() {
		Long mid = 92L;
		Member member = Member.builder().mid(mid).build();
		reviewRepository.deleteByMember(member);
		memberRepository.deleteById(mid);
	}
	
	
	
}
