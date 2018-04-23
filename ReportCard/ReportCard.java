package com.example.android.boletimescolar;

/**
 * Report Card
 * Created by Anderson on 12/02/2018.
 */

public class ReportCard {

    private int schoolYear;
    private int curseCode;
    private int disciplineCode;
    private int studentEnrollmentNumber;
    private double firstQuarterNote;
    private double secondQuarterNote;
    private double thirdQuarterNote;

    /**
     * Report Card constructor method.
     *
     * @param schoolYear              - School year.
     * @param curseCode               - Curse code.
     * @param disciplineCode          - Discipline code.
     * @param studentEnrollmentNumber - Student enrollment number.
     * @param firstQuarterNote        - Discipline note referring to first quarter.
     * @param secondQuarterNote       - Discipline note referring to second quarter.
     * @param thirdQuarterNote        - Discipline note referring to third quarter.
     */

    public ReportCard(int schoolYear, int curseCode, int disciplineCode, int studentEnrollmentNumber,
                      double firstQuarterNote, double secondQuarterNote, double thirdQuarterNote) {

        this.schoolYear = schoolYear;
        this.curseCode = curseCode;
        this.disciplineCode = disciplineCode;
        this.studentEnrollmentNumber = studentEnrollmentNumber;
        this.firstQuarterNote = firstQuarterNote;
        this.secondQuarterNote = secondQuarterNote;
        this.thirdQuarterNote = thirdQuarterNote;
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "schoolYear=" + this.schoolYear +
                ", curseCode=" + this.curseCode +
                ", disciplineCode=" + this.disciplineCode +
                ", studentEnrollmentNumber=" + this.studentEnrollmentNumber +
                ", firstQuarterNote=" + this.firstQuarterNote +
                ", secondQuarterNote=" + this.secondQuarterNote +
                ", thirdQuarterNote=" + this.thirdQuarterNote +
                '}';
    }

    /**
     * Returns the school year.
     *
     * @return - double type
     */

    public int getSchoolYear() {
        return this.schoolYear;
    }

    /**
     * Returns the curse code.
     *
     * @return - double type
     */

    public int getCurseCode() {
        return this.curseCode;
    }

    /**
     * Returns the discipline code.
     *
     * @return - double type
     */

    public int getDisciplineCode() {
        return this.disciplineCode;
    }

    /**
     * Returns the student enrollment number.
     *
     * @return - double type
     */

    public int getStudentEnrollmentNumber() {
        return this.studentEnrollmentNumber;
    }

    /**
     * Returns the first quarter note.
     *
     * @return - double type
     */

    public double getFirstQuarterNote() {
        return this.firstQuarterNote;
    }

    /**
     * Returns the second quarter note.
     *
     * @return - double type
     */

    public double getSecondQuarterNote() {
        return this.secondQuarterNote;
    }

    /**
     * Returns the third quarter note.
     *
     * @return - double type
     */

    public double getThirdQuarterNote() {
        return this.thirdQuarterNote;
    }

    /**
     * Set the school year.
     *
     * @param schoolYear
     */

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    /**
     * Set the curse code.
     *
     * @param curseCode
     */

    public void setCurseCode(int curseCode) {
        this.curseCode = curseCode;
    }

    /**
     * Set the discipline code.
     *
     * @param disciplineCode
     */

    public void setDiciplineCode(int disciplineCode) {
        this.disciplineCode = disciplineCode;
    }

    /**
     * Set the student enrollment number.
     *
     * @param studentEnrollmentNumber
     */

    public void setStudentEnrollmentNumber(int studentEnrollmentNumber) {
        this.studentEnrollmentNumber = studentEnrollmentNumber;
    }

    /**
     * Set fisrt quarter number.
     *
     * @param firstQuarterNote
     */

    public void setFirstQuarterNote(double firstQuarterNote) {
        this.firstQuarterNote = firstQuarterNote;
    }

    /**
     * Set second quarter number.
     *
     * @param secondQuarterNote
     */

    public void setSecondQuarterNote(double secondQuarterNote) {
        this.secondQuarterNote = secondQuarterNote;
    }

    /**
     * Set third quarter number.
     *
     * @param thirdQuarterNote
     */

    public void setThirdQuarterNote(double thirdQuarterNote) {
        this.thirdQuarterNote = thirdQuarterNote;
    }
}
