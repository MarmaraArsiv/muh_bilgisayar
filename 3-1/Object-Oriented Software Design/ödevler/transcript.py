from datetime import datetime

import student


class Transcript:
    __transcript = []
    __cumulativeGpa = float
    __gpas = []
    __currentSemester = int
    __student = student
    __dateOfCreation = str
    __transcriptJSONObject = {}
    __cumulativeTakenCredits = int
    __cumulativeCompletedCredits = int
    __currentYear = str

    def __init__(self, student, includeNewCourses):
        self.__student = student
        self.__dateOfCreation = str(datetime.date(datetime.now))
        self.__currentYear = datetime.now.year
        self.__transcript = [student.get_semester()][student.get_department().get_course_take_limit()][5]
        self.__gpas = [student.get_semester]
        self.__cumulativeGpa = 0
        self.__currentSemester = student.get_semester()
        self.add_courses(student.get_previous_courses())
        if includeNewCourses == True:
            self.add_courses(student.get_newly_taken_courses())
        self.__transcriptJSONObject = self.create_transcript()

    def add_courses(self, courses):
        for departmentCourse in courses:
            course = courses(departmentCourse)
            semesterIndex = course.get_main_course().get_semester() - 1
            if isinstance(course.get_main_course, course.ElectiveCourse):
                semesterIndex = self.__student.get_elective_semester(
                    course.ElectiveCourse(course.get_main_course())) - 1
            if semesterIndex >= len(self.__transcript) or semesterIndex < 0:
                continue
            i = 0
            while self.__transcript[semesterIndex][i][0] is None and i < len(self.__transcript[semesterIndex] - 1):
                i = i + 1
            self.__transcript[semesterIndex][i][0] = course.get_main_course().get_course_id()
            self.__transcript[semesterIndex][i][0] = course.get_main_course().get_course_name()
            self.__transcript[semesterIndex][i][2] = str(course.get_main_course().get_credits())
            if course.get_registered_semester() == self.__currentSemester:
                self.__transcript[semesterIndex][i][3] = ""
                self.__transcript[semesterIndex][i][4] = ""
            else:
                self.__transcript[semesterIndex][i][3] = course.get_letter_grade()
                self.__transcript[semesterIndex][i][4] = str(course.get_point())

    def create_transcript(self):
        transcriptObject = {}
        semesters = []
        for semesterNumber in range(1, len(self.__transcript)):
            semesters.append(self.create_semester_json_object(semesterNumber, semesterNumber is len(self.__transcript)))
        transcriptObject.append("date", self.__dateOfCreation)
        transcriptObject.append("name", self.__student.get_name())
        transcriptObject.append("id", self.__student.get_student_id())
        transcriptObject.append("level", self.__student.get_education_level())
        transcriptObject.append("faculty", self.__student.get_faculty())
        transcriptObject.append("department", self.__student.get_department().get_mame())
        transcriptObject.append("semesters", semesters)
        return transcriptObject

    def create_semester_json_object(self, semesterNumber, lastSemester):
        semesterObj = {}
        courseList = []
        takenCredits = 0
        completedCredits = 0
        gpa = 0
        semester = self.__transcript[semesterNumber - 1]

        for courseDetails in semester:
            if not courseDetails[0] is None:
                courseCredits = int(courseDetails[2])
                takenCredits += courseCredits
                if not courseDetails[3] is "FF" or courseDetails[3] is "FD":
                    completedCredits += courseCredits
                if courseDetails[4] is None:
                    courseDetails[4] = "0.0"
                gpa += courseCredits * float(courseDetails[4])
                self.__gpas[semesterNumber - 1] = gpa
                course = self.create_course_json_object(courseDetails[4])
                courseList.append(course)
        gpa = self.round_gpa(gpa / takenCredits)
        self.__cumulativeGpa += gpa
        self.__cumulativeTakenCredits += takenCredits
        self.__cumulativeCompletedCredits += completedCredits
        semesterObj.append("semester", semesterNumber)
        semesterObj.append("semesterName",
                           self.get_semester_name(semesterNumber) + self.get_semester_year(semesterNumber))
        semesterObj.append("takenCredits", takenCredits)
        if not lastSemester:
            semesterObj.append("completedCredits", completedCredits)
            semesterObj.append("gpa", gpa)
            semesterObj.append("cumulativeGpa", self.round_gpa(self.cumulativeGPA / semesterNumber))
        semesterObj.append("cumulativeTakenCredits", self.__cumulativeTakenCredits)
        semesterObj.append("cumulativeCompletedCredits", self.__cumulativeCompletedCredits)
        semesterObj.append("courses", courseList)
        return semesterObj

    def create_course_json_object(self, courseDetails):
        result = {}
        for i in range(0, len(courseDetails)):
            result.append("courseId", courseDetails[0]);
            result.append("courseName", courseDetails[1]);
            result.append("credits", int(courseDetails[2]));
            result.append("grade", courseDetails[3]);
            result.append("points", float(courseDetails[4]));
        return result

    def get_semester_year(self, semesterNumber):
        currentYear = int(self.__currentYear)
        semesterYear = currentYear - ((self.__student.get_semester() - semesterNumber) / 2)
        return str(semesterYear)

    def round_gpa(self, gpa):
        return round(100 * gpa) / 100.0

    def get_semester_name(self, semesterNumber):
        if semesterNumber % 2 == 0:
            return "Spring"
        return "Fall"

    def get_transcript_object(self):
        return self.__transcriptJSONObject

    def get_transcript(self):
        return self.__transcript

    def get_student(self):
        return self.__student
