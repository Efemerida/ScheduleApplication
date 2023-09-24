package com.example.scheduleapplication.entites;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(foreignKeys = {@ForeignKey(
        entity = Day.class,
        parentColumns = "id",
        childColumns = "idDay",
        onDelete = ForeignKey.CASCADE)})
public class Lesson {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String time;
    private String office;
    private String officeM2;
    private String course;
    private String teacher;
    private String type;
    private int idDay;

    public int getIdDay() {
        return idDay;
    }

    public void setIdDay(int idDay) {
        this.idDay = idDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getOfficeM2() {
        return officeM2;
    }

    public void setOfficeM2(String officeM2) {
        this.officeM2 = officeM2;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
