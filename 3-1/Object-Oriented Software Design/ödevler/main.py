import json
from random import random

from department import Department
from lab_course import LabCourse

from lecturer import Lecturer
from teaching_assistant import TeachingAssistant


def parse_json(json_file):
    with open(json_file) as f:
        json_data = json.load(f)
    return json_data


if __name__ == "__main__":
    data = parse_json('input.json')
    department = Department(data['departmentName'], data['departmentId'], data['faculty'], data['currentSemester'],
                            data['departmentNumber'], data['numberOfLectureDays'], data['numberOfLectureHours'])
    department.set_course_take_limit(data['courseTakeLimit'])
    department.set_lab_capacity_limit(data['labCapacityLimit'])
    department.set_elective_capacity_limit(data['electiveCapacityLimit'])
    department.set_maximum_credits(data['maximumCredits'])
    for lecturer in data['lecturers']:
        department.add_lecturer(Lecturer(lecturer['name'], lecturer['department']))
    for assistant in data['teachingAssistants']:
        department.add_teaching_assistant(TeachingAssistant(assistant['name'], assistant['department']))
    for course in data['courses']:
        random_capacity = int(random() * department.get_elective_capacity_limit() + 1)
        course_object = department.create_course(course)
        department.add_course(course_object)
        if "lecturer" in course:
            # check is integer
            if isinstance(course['lecturer'], int):
                lecturer_id = int(course['lecturer'])
                lecturer = department.get_lecturer(lecturer_id - 1)
                course_object.set_lecturer(lecturer)
                lecturer.add_course(course_object)
            else:
                lecturer = Lecturer(course['lecturer'], None)
                course_object.set_lecturer(lecturer)
                lecturer.add_course(course_object)
        if "teachingAssistant" in course:
            # check is integer
            if isinstance(course['teachingAssistant'], int):
                ta_id = int(course['lecturer'])
                teaching_assistant = department.get_lecturer(ta_id - 1)
                course_object.set_lecturer(teaching_assistant)
                teaching_assistant.add_course(course_object)
        if "minCompletedCredits" in course:
            course_object.set_min_completed_credits(course['minCompletedCredits'])
        if "schedule" in course:
            course_object.set_schedule(course['schedule'])
        if "labHours" in course and course['labHours'] > 0:
            course_object.set_lab_hours(course['labHours'])
            random_capacity = int(random() * department.get_lab_capacity_limit() + 1)
            lab_course = LabCourse(course_object)
            lab_course.set_capacity(random_capacity)
