package com.vistagram.app.utils;

public class Constants {

    public static class ApiRoutes {

            public static final String API_SHELL = "/api/v1";
        public static class User {
            public static final String USER_SHELL = API_SHELL + "/users";
            public static final String GET_USER_PROFILE = "/{userId}";
            public static final String UPDATE_USER_PROFILE = "/{userId}";
            public static final String SEARCH_USER = "/search";
        }
        public static class Post {
            public static final String POST_SHELL = API_SHELL + "/posts";
            public static final String CREATE_POST = "";
            public static final String GET_TIMELINE = "/timeline";
            public static final String GET_POST_BY_ID = "/{postId}";
            public static final String DELETE_POST = "/{postId}";
            public static final String SEARCH_POST = "/search";
            public static final String GET_POST_BY_USER_ID = "/user/{userId}";
        }
        public static class Like {
            public static final String LIKE_SHELL = API_SHELL + "/likes";
            public static final String LIKE_POST = "/{postId}";
            public static final String UNLIKE_POST = "/{postId}";
            public static final String POST_STATUS = "/{postId}/status";
            public static final String LIKED_BY_USER = "/user/{userId}";
        }
        public static class Share {
            public static final String SHARE_SHELL = API_SHELL + "/shares";
            public static final String SHARE_POST = "/{postId}";
            public static final String SHARED_BY_USER = "/user/{userId}";
        }
    }
}
