package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.BidStatus;
import com.palu_gada_be.palu_gada_be.dto.request.ReviewRequest;
import com.palu_gada_be.palu_gada_be.dto.response.ReviewResponse;
import com.palu_gada_be.palu_gada_be.mapper.ReviewMapper;
import com.palu_gada_be.palu_gada_be.model.Bid;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.Review;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.ReviewRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.BidService;
import com.palu_gada_be.palu_gada_be.service.PostService;
import com.palu_gada_be.palu_gada_be.service.ReviewService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.palu_gada_be.palu_gada_be.specification.ReviewSpecification.*;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final JwtService jwtService;
    private final PostService postService;
    private final UserService userService;
    private final BidService bidService;

    @Override
    public ReviewResponse create(ReviewRequest request) {
        User user = jwtService.getUserAuthenticated();
        User worker = userService.findById(request.getUserId());
        Post post = postService.findById(request.getPostId());

//        if (post.getBids().stream().noneMatch((bid) -> (bid.getUser().getId().equals(user.getId())) && (bid.getBidStatus().equals(BidStatus.FINISH))))
//        {
//            throw new RuntimeException("You cannot create review when you are not bid post and finish bid");
//        }

        if (!post.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You cannot create review when it's not your own post");
        }

        if (post.getBids().stream().noneMatch(bid -> bid.getUser().getId().equals(worker.getId()) && bid.getBidStatus().equals(BidStatus.FINISH))){
            throw new RuntimeException("You cannot create review while not finish bid");
        }

        Review newReview = Review.builder()
                .user(worker)
                .post(post)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        Bid searchBid = post.getBids().stream().filter(bid -> bid.getUser().getId().equals(worker.getId())).toList().get(0);

        try {
            bidService.updateStatusById(searchBid.getId(), "REVIEWED");
        } catch (Exception ex){
            throw new RuntimeException("Forbidden Action");
        }

        Review createdReview = reviewRepository.save(newReview);

        return ReviewMapper.toReviewResponse(createdReview);
    }

    @Override
    public Page<ReviewResponse> getAll(String commentLikeFilter, List<Long> ratings, String sortField, String sortDirection, Pageable pageable) {
        Specification<Review> spec = Specification.where(StringUtils.isBlank(commentLikeFilter) ? null : commentLike(commentLikeFilter))
                .and(CollectionUtils.isEmpty(ratings) ? null : inRating(ratings))
                .and(StringUtils.isBlank(sortField) ? null : sortByField(sortField, sortDirection));

        Page<Review> reviews = reviewRepository.findAll(spec, pageable);

        return reviews.map(ReviewMapper::toReviewResponse);
    }

    @Override
    public Page<ReviewResponse> getByPostId(Long id, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByPostId(id, pageable);

        return reviews.map(ReviewMapper::toReviewResponse);
    }

    @Override
    public Page<ReviewResponse> getByUserId(Long id, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByUserId(id, pageable);

        return reviews.map(ReviewMapper::toReviewResponse);
    }

    @Override
    public Page<ReviewResponse> getAllUserAuthenticated(Pageable pageable) {
        Long id = jwtService.getUserAuthenticated().getId();

        return getByUserId(id, pageable);
    }

    @Override
    public ReviewResponse getById(Long id) {
        Review review = findById(id);

        return ReviewMapper.toReviewResponse(review);
    }

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Review not found")
        );
    }

    @Override
    public ReviewResponse updateById(Long id, ReviewRequest request) {
        Review review = findById(id);
        User user = jwtService.getUserAuthenticated();

        if (!user.getId().equals(review.getPost().getUser().getId())){
            throw new RuntimeException("Forbidden Action");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review updatedReview = reviewRepository.save(review);

        return ReviewMapper.toReviewResponse(updatedReview);
    }

    @Override
    public void delete(Long id) {
        Review review = findById(id);
        User user = jwtService.getUserAuthenticated();

        if (user.getAuthorities().stream().noneMatch((authority) -> authority.getAuthority().equals("ROLE_ADMIN"))){
            if (!review.getUser().getId().equals(user.getId())){
                throw new RuntimeException("You cannot delete review when its not yours");
            }
        }

        reviewRepository.delete(findById(id));
    }
}
