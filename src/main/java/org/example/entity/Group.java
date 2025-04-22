package org.example.entity;

import java.util.Objects;

public class Group {
    private int groupId;
    private final String groupName;
    private int studentCount;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setStudentCount(int studentsCount) {
        this.studentCount = studentsCount;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getStudentCount() {
        return studentCount;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", studentsCount=" + studentCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupName, group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(groupName);
    }
}
