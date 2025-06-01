package com.vistagram.app.seeding;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataSeeder {

    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final ObjectMapper objectMapper;

    @PostConstruct
    @Transactional
    public void seedData() {
        try {
            // 1. File existence check
            Resource resource = new ClassPathResource("seed-data.json");
            if (!resource.exists()) {
                log.info("No seed file found - skipping");
                return;
            }

            // 2. Empty file check
            if (resource.contentLength() == 0) {
                log.warn("Empty seed file detected");
                return;
            }

            // 3. Read and validate
            SeedPostDto[] seeds = objectMapper.readValue(
                    resource.getInputStream(),
                    SeedPostDto[].class);

            if (seeds == null || seeds.length == 0) {
                log.warn("No seed records found in JSON");
                return;
            }

            // 4. Proceed only if DB is empty
            if (postRepo.count() == 0) {
                seedUsersAndPosts(seeds);
            }
        } catch (Exception e) {
            log.error("Seeding failed", e);
        }
    }
    private void seedUsersAndPosts(SeedPostDto[] seeds) {
        // Builder pattern for both
        Map<String, User> userMap = Arrays.stream(seeds)
                .map(SeedPostDto::getUsername)
                .distinct()
                .collect(Collectors.toMap(
                        username -> username,
                        username -> userRepo.save(
                                User.builder()
                                        .username(username)
                                        .build()
                        )));

        Arrays.stream(seeds).forEach(seed ->
                postRepo.save(
                        Post.builder()
                                .user(userMap.get(seed.getUsername()))
                                .caption(seed.getCaption())
                                .imageUrl(seed.getImage_url())
                                .poiName(seed.getLocation())
                                .createdAt(seed.getTimestamp())
                                .build()
                )
        );
    }
}
