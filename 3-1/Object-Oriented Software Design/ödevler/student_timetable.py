import course
import student


class StudentTimetable:
    number_of_days: int
    number_of_hours: int
    timetable = {}

    def __init__(self, student: student):
        self.number_of_days = student.get_department().get_number_of_lecture_days()
        self.number_of_hours = student.get_department().get_number_of_lecture_hours()

        for i in range(0, self.number_of_days):
            for j in range(0, self.number_of_hours):
                self.timetable[i + 1][j + 1] = []

    def assign_course(self, day, hour, course: course):
        self.timetable[day][hour] = course

    # Python does not support function overloading
    def assign_by_course(self, course: course):
        for lecture_hours in course.get_course_schedule():
            self.assign_course(lecture_hours[0], lecture_hours[1], course)

    def course_has_collision(self, course: course):
        for lecture_time in course.get_course_schedule():
            if self.is_course_assigned(lecture_time[0], lecture_time[1]):
                return True
        return False

    def is_course_assigned(self, day, hour):
        return len(self.timetable[day][hour]) > 0

    def number_of_assigned_courses(self, day, hour):
        return len(self.timetable[day][hour])

    def get_number_of_days(self):
        return self.number_of_days

    def get_number_of_hours(self):
        return self.number_of_hours

    def get_timetable(self):
        return self.timetable