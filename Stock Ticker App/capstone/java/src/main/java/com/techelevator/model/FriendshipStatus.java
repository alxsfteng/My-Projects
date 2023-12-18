package com.techelevator.model;

import java.util.Objects;

public class FriendshipStatus {
    private int friendshipStatusId;
    private String friendshipStatusDesc;

    public FriendshipStatus(int friendshipStatusId, String friendshipStatusDesc) {
        this.friendshipStatusId = friendshipStatusId;
        this.friendshipStatusDesc = friendshipStatusDesc;
    }

    public int getFriendshipStatusId() {
        return friendshipStatusId;
    }

    public void setFriendshipStatusId(int friendshipStatusId) {
        this.friendshipStatusId = friendshipStatusId;
    }

    public String getFriendshipStatusDesc() {
        return friendshipStatusDesc;
    }

    public void setFriendshipStatusDesc(String friendshipStatusDesc) {
        this.friendshipStatusDesc = friendshipStatusDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendshipStatus that = (FriendshipStatus) o;
        return friendshipStatusId == that.friendshipStatusId && Objects.equals(friendshipStatusDesc, that.friendshipStatusDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendshipStatusId, friendshipStatusDesc);
    }

    @Override
    public String toString() {
        return "FriendshipStatus{" +
                "friendshipStatusId=" + friendshipStatusId +
                ", friendshipStatusDesc='" + friendshipStatusDesc + '\'' +
                '}';
    }
}
