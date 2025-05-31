package com.vistagram.app.utils;

public class Constants {

    public static class ApiRoutes {

        public static final String API_SHELL_LIKE = "api/v1/like";
        public static final String API_SHELL_POST = "api/v1/posts";
        public static final String API_SHELL_SHARE = "api/v1/shares";
        public static final String LIKE_POST = "/{postId}";
        public static final String UNLIKE_POST = "/{postId}";
        public static final String GET_POST = "/{postId}";
        public static final String SHARE_POST = "/{postId}";
        public static final String IS_LIKED_BY = "/{postId}/status";
        public static final String GET_USER_LIKED_POSTS = "/user/{userId}";
        public static final String SEARCH_POST = "/search";
        public static final String DELETE_POST = "/{postId}";
        public static final String GET_USER_SHARED_POST = "/user/{userId}";
        public static final String API_SHELL_USER = "api/v1/users";
        public static final String GET_USER_PROFILE = "/{userId}";
        public static final String UPDATE_USER_PROFILE = "/{userId}";
        public static final String SEARCH = "/search";

    }
}
