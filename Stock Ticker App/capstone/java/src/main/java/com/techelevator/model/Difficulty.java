package com.techelevator.model;

import java.util.Objects;

public class Difficulty {

    private int difficultyId;
    private String difficultySettingDesc;

    public Difficulty(int difficultyId, String difficultySettingDesc) {
        this.difficultyId = difficultyId;
        this.difficultySettingDesc = difficultySettingDesc;
    }

    public int getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(int difficultyId) {
        this.difficultyId = difficultyId;
    }

    public String getDifficultySettingDesc() {
        return difficultySettingDesc;
    }

    public void setDifficultySettingDesc(String difficultySettingDesc) {
        this.difficultySettingDesc = difficultySettingDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Difficulty that = (Difficulty) o;
        return difficultyId == that.difficultyId && Objects.equals(difficultySettingDesc, that.difficultySettingDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(difficultyId, difficultySettingDesc);
    }

    @Override
    public String toString() {
        return "Difficulty{" +
                "difficultyId=" + difficultyId +
                ", difficultySettingDesc='" + difficultySettingDesc + '\'' +
                '}';
    }
}
