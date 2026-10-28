class Curriculum:
    __semesters = {
        1: [],
        2: [],
        3: [],
        4: [],
        5: [],
        6: [],
        7: [],
        8: [],
    }

    def __init__(self):
        pass

    def get_semester(self, semester_no):
        return self.__semesters[semester_no]

    def add_course(self, semester_no: int, course):
        self.__semesters[semester_no].append(course)

    def get_semesters(self):
        return self.__semesters
