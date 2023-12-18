package com.techelevator.model;

import java.util.Objects;

public class Friends {
    private int userId;
    private int friendshipStatusId;

    public Friends(int userId, int friendshipStatusId) {
        this.userId = userId;
        this.friendshipStatusId = friendshipStatusId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendshipStatusId() {
        return friendshipStatusId;
    }

    public void setFriendshipStatusId(int friendshipStatusId) {
        this.friendshipStatusId = friendshipStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friends friends = (Friends) o;
        return userId == friends.userId && friendshipStatusId == friends.friendshipStatusId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendshipStatusId);
    }

    @Override
    public String toString() {
        return "Friends{" +
                "userId=" + userId +
                ", friendshipStatusId=" + friendshipStatusId +
                '}';
    }
}
