package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.BidStatus;
import com.palu_gada_be.palu_gada_be.dto.request.BidRequest;
import com.palu_gada_be.palu_gada_be.dto.response.BidResponse;
import com.palu_gada_be.palu_gada_be.mapper.BidMapper;
import com.palu_gada_be.palu_gada_be.model.Bid;
import com.palu_gada_be.palu_gada_be.model.PendingBid;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.BidRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.BidService;
import com.palu_gada_be.palu_gada_be.service.PendingBidService;
import com.palu_gada_be.palu_gada_be.service.PostService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final UserService userService;
    private final PostService postService;
    private final JwtService jwtService;
    private final PendingBidService pendingBidService;

    @Override
    public BidResponse create(BidRequest request) {
        User user = userService.findById(
                jwtService.getUserAuthenticated().getId()
        );
        Post post = postService.findById(request.getPostId());

        if (user.getId().equals(post.getUser().getId())){
            throw new RuntimeException("You can't bid to your own post");
        }

        if (!(request.getAmount() > post.getBudgetMin() && request.getAmount() < post.getBudgetMax())){
            throw new RuntimeException("Amount must be in the range budget");
        }

        Bid bid = Bid.builder()
                .user(user)
                .post(post)
                .amount(request.getAmount())
                .message(request.getMessage())
                .bidStatus(BidStatus.PENDING)
                .build();

        Bid createdBid = bidRepository.save(bid);

        return BidMapper.toBidResponse(createdBid);
    }

    @Override
    public Page<BidResponse> getAll(Pageable pageable) {
        Page<Bid> bids = bidRepository.findAll(pageable);

        return bids.map(BidMapper::toBidResponse);
    }

    @Override
    public Page<BidResponse> getAllByUserId(Pageable pageable) {
        Long id = jwtService.getUserAuthenticated().getId();
        Page<Bid> bids = bidRepository.findByUserId(id, pageable);

        return bids.map(BidMapper::toBidResponse);
    }

    @Override
    public Page<BidResponse> getAllByPostId(Long id, Pageable pageable) {
        Long postId = postService.findById(id).getId();
        Page<Bid> bids = bidRepository.findByPostId(postId, pageable);

        return bids.map(BidMapper::toBidResponse);
    }

    @Override
    @Transactional
    public BidResponse updateStatusById(Long id, String status) {
        Bid bid = findById(id);
        User user = jwtService.getUserAuthenticated();

        if (user.getAuthorities().stream().noneMatch((authority -> authority.getAuthority().equals("ROLE_ADMIN")))){
            if (!bid.getPost().getUser().getId().equals(user.getId())){
                throw new IllegalArgumentException("Forbidden Action");
            }
        }


        try {
            if (BidStatus.ACCEPTED == BidStatus.valueOf(status.toUpperCase())){
                try {
                    PendingBid pendingBid = PendingBid.builder()
                            .bid(bid)
                            .balance(bid.getAmount())
                            .build();

                    userService.updateBalance(user.getId(), Math.abs(bid.getAmount())*-1);
                    pendingBidService.create(pendingBid);
                } catch (Exception e){
                    throw new RuntimeException("Cannot Accept Bid, Try again later");
                }

            }

            if (BidStatus.FINISH == BidStatus.valueOf(status.toUpperCase())){
                if (!bid.getBidStatus().equals(BidStatus.ACCEPTED)){
                    throw new IllegalArgumentException("Cannot Change Status to Finish Before Accept Bid");
                }
                try {
                    PendingBid existingPendingBid = bid.getPendingBids().get(0);
                    userService.updateBalance(bid.getUser().getId(), bid.getAmount());
                    pendingBidService.delete(existingPendingBid.getId());
                } catch (Exception e){
                    throw new RuntimeException("Cannot Finish Bid, Try again later");
                }
            }

            BidStatus bidStatus = BidStatus.valueOf(status.toUpperCase());
            bid.setBidStatus(bidStatus);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid status value");
        }

        Bid updatedBid = bidRepository.save(bid);
        return BidMapper.toBidResponse(updatedBid);
    }

    @Override
    public Bid findById(Long id) {
        return bidRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bid Not Found")
        );
    }

    @Override
    public BidResponse getById(Long id) {
        Bid bid = findById(id);
        return BidMapper.toBidResponse(bid);
    }

    @Override
    public void deleteById(Long id) {
        Bid bid = findById(id);
        bidRepository.delete(bid);
    }
}
