package com.example.votingsystem.repository;

import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.repository.dto.VotesSumDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
  Boolean existsByVoteSessionIdAndUserId(Integer voteSessionId, Integer userId);

  @Query(
      """
    SELECT
        new com.example.votingsystem.repository.dto.VotesSumDto(
            CAST(COALESCE(SUM(CASE WHEN v.vote = true THEN 1L ELSE 0L END), 0L) AS LONG),
            CAST(COALESCE(SUM(CASE WHEN v.vote = false THEN 1L ELSE 0L END), 0L) AS LONG)
        )
    FROM
        Vote v
    WHERE
        v.voteSessionId = :voteSessionId
  """)
  VotesSumDto countVotes(Integer voteSessionId);
}
