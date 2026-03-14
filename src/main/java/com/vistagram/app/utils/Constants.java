package com.vistagram.app.utils;

public class Constants {

    public static final String IMG_URL = "https://vistagram.app/posts/";

    public static class ApiRoutes {

        public static final String API_SHELL = "/api/v1";
        public static class User {
            public static final String USER_SHELL = API_SHELL + "/users";
            public static final String GET_USER_PROFILE = "/{userId}";
            public static final String UPDATE_USER_PROFILE = "/{userId}";
            public static final String SEARCH_USERS = "/search";

            public static final String USER_POSTS = "/{userId}/posts";
            public static final String USER_LIKED_POSTS = "/{userId}/likes";
            public static final String USER_SHARED_POSTS = "/{userId}/shares";
        }
        public static class Post {
            public static final String POST_SHELL = API_SHELL + "/posts";
            public static final String CREATE_POST = "";
            public static final String GET_TIMELINE = "/timeline";
            public static final String GET_POST_BY_ID = "/{postId}";
            public static final String DELETE_POST = "/{postId}";
            public static final String SEARCH_POSTS = "/search";
        }
        public static class Like {
            public static final String LIKE_POST = "/{postId}/likes";
            public static final String UNLIKE_POST = "/{postId}/likes";
            public static final String LIKE_STATUS = "/{postId}/likes/status";
        }
        public static class Share {
            public static final String SHARE_POST = "/{postId}/shares";
        }
        public static class Comment {
            public static final String ADD_COMMENT = "/{postId}/comments";
            public static final String GET_POST_COMMENTS = "/{postId}/comments";
        }
    }
}
