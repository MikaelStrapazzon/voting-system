package com.example.votingsystem.repository;

import com.example.votingsystem.dto.repository.VotesSumDto;
import com.example.votingsystem.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
  Boolean existsByVoteSessionIdAndUserId(Integer voteSessionId, Integer userId);

  @Query(
      """
    SELECT
        new com.example.votingsystem.dto.repository.VotesSumDto(
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
