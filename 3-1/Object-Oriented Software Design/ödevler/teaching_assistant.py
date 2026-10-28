import person


class TeachingAssistant(person.Person):
    def __init__(self, name, department):
        super().__init__(name, department)

    def assign_lab_course(self, course):
        pass