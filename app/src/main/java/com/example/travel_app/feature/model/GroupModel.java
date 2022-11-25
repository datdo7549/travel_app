package com.example.travel_app.feature.model;

import java.util.ArrayList;

public class GroupModel {
    private String groupID;
    private String groupName;
    private ArrayList<UserProfileLite> memberList;

    public GroupModel() {}

    public ArrayList<UserProfileLite> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<UserProfileLite> memberList) {
        this.memberList = memberList;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
