import department


class Person:
    __name: str
    __department: department

    def __init__(self, name, department):
        self.__name = name
        self.__department = department

    def get_name(self):
        return self.__name

    def get_department(self):
        return self.__department

    def get_faculty(self):
        return self.__department.get_faculty()
