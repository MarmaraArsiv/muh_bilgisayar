import datetime

CURRENT_YEAR = 2026

class Person:
    def __init__(self, name, gender, birth_year, death_year="none"):
        self.name = name
        self.gender = gender.lower()
        self.birth_year = int(birth_year)
        self.death_year = int(death_year) if death_year != "none" else "none"
        self.father = None
        self.mother = None
        self.spouse = None
        self.children = []
        self.level = 0
        
    @property
    def age(self):
        end_year = CURRENT_YEAR if self.is_alive else self.death_year
        return end_year - self.birth_year

    @property
    def is_alive(self):
        return self.death_year == "none"
        

class FamilyTree:
    def __init__(self):
        self.members = {}
        self.roots = []

    def add_person_to_registry(self, person):
        self.members[person.name] = person
    
    def is_kardes(self, p1, p2):
        if p1 == p2:
            return False
        return (p1.father and p1.father == p2.father) or (p1.mother and p1.mother == p2.mother)        
        
    def is_ogul(self, p1, p2):
        return p1 in p2.children and p1.gender == 'm'
        
    def is_anne(self, p1, p2):
        return p2.mother == p1
        
    def is_baba(self, p1, p2):
        return p2.father == p1

    def is_kiz(self, p1, p2):
        return p1 in p2.children and p1.gender == 'f'
    
    def is_abi(self, p1, p2):
        return self.is_kardes(p1, p2) and p1.gender == 'm' and p1.birth_year < p2.birth_year
        
    def is_abla(self, p1, p2):
        return self.is_kardes(p1, p2) and p1.gender == 'f' and p1.birth_year < p2.birth_year
        
    def is_erkek_kardes(self, p1, p2):
        return self.is_kardes(p1, p2) and p1.gender == 'm' and p1.birth_year >= p2.birth_year
        
    def is_kiz_kardes(self, p1, p2):
        return self.is_kardes(p1, p2) and p1.gender == 'f' and p1.birth_year >= p2.birth_year

    def is_buyukbaba(self, p1, p2):
        return (p2.father and p2.father.father == p1) or (p2.mother and p2.mother.father == p1)
        
    def is_buyukanne(self, p1, p2):
        return (p2.father and p2.father.mother == p1) or (p2.mother and p2.mother.mother == p1)
        
    def is_torun(self, p1, p2):
        for child in p2.children:
            if p1 in child.children:
                return True
        return False

    def is_amca(self, p1, p2):
        return p2.father and self.is_kardes(p1, p2.father) and p1.gender == 'm'
        
    def is_hala(self, p1, p2):
        return p2.father and self.is_kardes(p1, p2.father) and p1.gender == 'f'
        
    def is_dayi(self, p1, p2):
        return p2.mother and self.is_kardes(p1, p2.mother) and p1.gender == 'm'
        
    def is_teyze(self, p1, p2):
        return p2.mother and self.is_kardes(p1, p2.mother) and p1.gender == 'f'

    def is_yegen(self, p1, p2):
        if p1.father and self.is_kardes(p1.father, p2):
            return True
        if p1.mother and self.is_kardes(p1.mother, p2):
            return True
        return False

    def is_kuzen(self, p1, p2):
        if p1.father and p2.father and self.is_kardes(p1.father, p2.father):
            return True
        if p1.father and p2.mother and self.is_kardes(p1.father, p2.mother):
            return True
        if p1.mother and p2.father and self.is_kardes(p1.mother, p2.father):
            return True
        if p1.mother and p2.mother and self.is_kardes(p1.mother, p2.mother):
            return True
        return False

    def is_bacanak(self, p1, p2):
        if p1.gender != 'm' or p2.gender != 'm': return False
        if not p1.spouse or not p2.spouse: return False
        return self.is_kardes(p1.spouse, p2.spouse)

    def is_elti(self, p1, p2):
        if p1.gender != 'f' or p2.gender != 'f': return False
        if not p1.spouse or not p2.spouse: return False
        return self.is_kardes(p1.spouse, p2.spouse)

    def is_baldiz(self, p1, p2):
        if p1.gender != 'f' or p2.gender != 'm': return False
        if not p2.spouse: return False
        return self.is_kardes(p1, p2.spouse)

    def is_kayinbirader(self, p1, p2):
        if p1.gender != 'm': return False
        if not p2.spouse: return False
        return self.is_kardes(p1, p2.spouse)

    def is_yenge(self, p1, p2):
        if not p1.spouse or p1.gender != 'f':
            return False
        return self.is_amca(p1.spouse, p2) or self.is_dayi(p1.spouse, p2) or self.is_kardes(p1.spouse, p2)

    def is_eniste(self, p1, p2):
        if not p1.spouse or p1.gender != 'm':
            return False
        return self.is_teyze(p1.spouse, p2) or self.is_hala(p1.spouse, p2) or self.is_kardes(p1.spouse, p2)

    def is_kayinpeder(self, p1, p2):
        return p2.spouse and p2.spouse.father == p1
        
    def is_kayinvalide(self, p1, p2):
        return p2.spouse and p2.spouse.mother == p1
        
    def is_gelin(self, p1, p2):
        return p1.spouse and (p1.spouse in p2.children) and p1.gender == 'f'
        
    def is_damat(self, p1, p2):
        return p1.spouse and (p1.spouse in p2.children) and p1.gender == 'm'

    def find_relation(self, name1, name2):
        if name1 not in self.members or name2 not in self.members:
            return "No Kinship Relationship"
        
        p1 = self.members[name1]
        p2 = self.members[name2]

        if self.is_baba(p1, p2): return "Baba"
        if self.is_anne(p1, p2): return "Anne"
        if self.is_ogul(p1, p2): return "Oğul"
        if self.is_kiz(p1, p2): return "Kız"
        if self.is_abi(p1, p2): return "Abi"
        if self.is_abla(p1, p2): return "Abla"
        if self.is_erkek_kardes(p1, p2): return "Erkek Kardeş"
        if self.is_kiz_kardes(p1, p2): return "Kız Kardeş"
        if self.is_buyukbaba(p1, p2): return "Büyükbaba"
        if self.is_buyukanne(p1, p2): return "Büyükanne"
        if self.is_torun(p1, p2): return "Torun"
        if self.is_amca(p1, p2): return "Amca"
        if self.is_hala(p1, p2): return "Hala"
        if self.is_dayi(p1, p2): return "Dayı"
        if self.is_teyze(p1, p2): return "Teyze"
        if self.is_yegen(p1, p2): return "Yeğen"
        if self.is_kuzen(p1, p2): return "Kuzen"
        if self.is_eniste(p1, p2): return "Enişte"
        if self.is_yenge(p1, p2): return "Yenge"
        if self.is_kayinpeder(p1, p2): return "Kayınpeder"
        if self.is_kayinvalide(p1, p2): return "Kayınvalide"
        if self.is_gelin(p1, p2): return "Gelin"
        if self.is_damat(p1, p2): return "Damat"
        if self.is_bacanak(p1, p2): return "Bacanak"
        if self.is_elti(p1, p2): return "Elti"
        if self.is_baldiz(p1, p2): return "Baldız"
        if self.is_kayinbirader(p1, p2): return "Kayınbirader"
        
        return "No Kinship Relationship"

    def invalid_marriage_check(self, p1, p2):
        if p1.age < 18 or p2.age < 18:
            return f"Error: Marriage under age 18 not valid! {p1.name} or {p2.name} under age 18."
            
        # Needs to check BOTH directions for forbidden relations (e.g. uncle to niece, niece to uncle)
        rel1 = self.find_relation(p1.name, p2.name)
        rel2 = self.find_relation(p2.name, p1.name)
        
        forbidden = ["Baba", "Anne", "Oğul", "Kız", "Abi", "Abla", "Erkek Kardeş", "Kız Kardeş", 
                     "Büyükbaba", "Büyükanne", "Torun", "Amca", "Hala", "Dayı", "Teyze"]
                     
        if rel1 in forbidden or rel2 in forbidden:
            return f"Error: {rel1}-{rel2} invalid marriage! ({p1.name} {p2.name})"
            
        return "valid"

    def print_tree(self):
        if not self.roots:
            print("Empty Family Tree.")
            return

        levels_dict = {}
        for name, person in self.members.items():
            if person.level not in levels_dict:
                levels_dict[person.level] = []
            
            # Determine if this person belongs to the main bloodline (has parents or is root)
            is_bloodline = bool(person.father or person.mother or person in self.roots)
            
            if person.spouse:
                is_spouse_bloodline = bool(person.spouse.father or person.spouse.mother or person.spouse in self.roots)
                
                # Logic to ensure the couple is only printed ONCE and strictly ordered (Bloodline-Spouse)
                should_print = False
                if is_bloodline and not is_spouse_bloodline:
                    should_print = True
                elif is_bloodline and is_spouse_bloodline:
                    # If BOTH are bloodline (e.g., cousins, roots), default to male to avoid duplicate prints
                    if person.gender == 'm':
                        should_print = True
                
                if should_print:
                    levels_dict[person.level].append(f"{person.name}-{person.spouse.name}")
            else:
                if is_bloodline:
                    levels_dict[person.level].append(f"{person.name}")

        for lvl in sorted(levels_dict.keys()):
            print(f"---LEVEL {lvl}---")
            for entry in levels_dict[lvl]:
                print(entry)

def display_menu():
    print("1-) Ask relation                  2-) Add/Update person")
    print("3-) Get info of person            4-) Print the family tree")
    print("5-) Add marriage                  6-) Terminate program")
    print("=======================================================")

def main():
    tree = FamilyTree()
    
    while True:
        display_menu()
        choice = input("Please choose an operation: ")
        
        if choice == '1': # Ask Relation
            name1 = input("1. Please type name and surname of the first person: ")
            name2 = input("2. Please type name and surname of the second person: ")
            relation = tree.find_relation(name1, name2)
            print(f"> {relation}")
            
        elif choice == '2': # Add/Update Person
            print("1-) Add Person | 2-) Update Person")
            sub_choice = input("Please choose an operation: ")
            
            if sub_choice == '1':
                if not tree.roots:
                    print("Empty Family tree. Adding root members")
                    print("Father")
                    f_name = input("Enter Name and Surname: ")
                    f_birth = input("Enter Birthdate: ")
                    father = Person(f_name, 'm', f_birth)
                    
                    print(f"{f_name} added.")
                    print("Mother")
                    m_name = input("Enter Name and Surname: ")
                    m_birth = input("Enter Birthdate: ")
                    mother = Person(m_name, 'f', m_birth)
                    print(f"{m_name} added.")
                    
                    father.spouse = mother
                    mother.spouse = father
                    
                    tree.add_person_to_registry(father)
                    tree.add_person_to_registry(mother)
                    tree.roots.extend([father, mother])
                    print(f"success: {f_name} and {m_name} married.")
                else:
                    print("Adding new family member")
                    f_name = input("Please type the father name and surname: ")
                    m_name = input("please type the mother name and surname: ")
                    
                    if f_name not in tree.members or m_name not in tree.members:
                        print("Error: Mother or Father not found in the family tree.")
                        continue
                        
                    c_name = input("Enter Child Name and Surname: ")
                    c_birth = input("Enter Birthdate: ")
                    c_death = input("Enter Death date ('none' if alive): ")
                    c_gender = input("Enter Gender (m/f): ")
                    
                    father = tree.members[f_name]
                    mother = tree.members[m_name]
                    
                    if father.death_year != "none" and father.death_year < int(c_birth):
                        print(f"Error: Father ({father.name}) died before the birth of the child.")
                        continue
                    if mother.death_year != "none" and mother.death_year < int(c_birth):
                        print(f"Error: Mother ({mother.name}) died before the birth of the child.")
                        continue
                        
                    child = Person(c_name, c_gender, c_birth, c_death)
                    child.father = father
                    child.mother = mother
                    child.level = max(father.level, mother.level) + 1
                    
                    father.children.append(child)
                    mother.children.append(child)
                    
                    tree.add_person_to_registry(child)
                    print(f"{c_name} added.")
                    
            elif sub_choice == '2':
                sub_choice_update = input("1-Update Birth Year / 2-Update Death Year / 0-Cancel: ")
                if sub_choice_update in ['1', '2']:
                    name = input("Enter Name and Surname of the person: ")
                    if name in tree.members:
                        year = input("Enter Year: ")
                        if sub_choice_update == '1':
                            tree.members[name].birth_year = int(year)
                            print(f"{name} Year of birth updated.")
                        else:
                            tree.members[name].death_year = int(year)
                            print(f"{name} Year of death updated.")
                    else:
                        print(f"Error: {name} not found.")

        elif choice == '3': # Get Info
            name = input("Enter Name and Surname: ")
            if name in tree.members:
                p = tree.members[name]
                print(f"Age: {p.age}")
                print(f"Level = {p.level}")
                print(f"Total Children: {len(p.children)}")
                print(f"Status: {'Alive.' if p.is_alive else 'Dead.'}") 
            else:
                print("Person not found.")
                
        elif choice == '4': # Print Tree
            tree.print_tree()
            
        elif choice == '5': # Add Marriage
            name1 = input("Enter Name and Surname of the first person: ")
            name2 = input("Enter Name and Surname of the second person: ")
            
            p1, p2 = None, None
            is_p1_new, is_p2_new = False, False
            
            if name1 in tree.members: p1 = tree.members[name1]
            if name2 in tree.members: p2 = tree.members[name2]
            
            if not p1 and p2:
                print(f"'{name1}' not found in the tree. Asking details to add as a new spouse...")
                g = input(f"Gender of {name1} (m/f): ")
                b = input(f"Birth year of {name1}: ")
                d = input(f"Death year of {name1} ('none' if alive): ")
                p1 = Person(name1, g, b, d)
                p1.level = p2.level
                is_p1_new = True
                
            elif not p2 and p1:
                print(f"'{name2}' not found in the tree. Asking details to add as a new spouse...")
                g = input(f"Gender of {name2} (m/f): ")
                b = input(f"Birth year of {name2}: ")
                d = input(f"Death year of {name2} ('none' if alive): ")
                p2 = Person(name2, g, b, d)
                p2.level = p1.level
                is_p2_new = True
                
            elif not p1 and not p2:
                print("Error: Neither person exists in the family tree.")
                continue

            # Validate FIRST, insert to registry SECOND
            validity = tree.invalid_marriage_check(p1, p2)
            if validity == "valid":
                p1.spouse = p2
                p2.spouse = p1
                if is_p1_new:
                    tree.add_person_to_registry(p1)
                    print(f"{name1} added to the tree as a new spouse.")
                if is_p2_new:
                    tree.add_person_to_registry(p2)
                    print(f"{name2} added to the tree as a new spouse.")
                    
                print(f"success: {p1.name} and {p2.name} married.")
            else:
                print(validity) # Prints rejection reason, leaves the "ghost" out of the registry.
                
        elif choice == '6': # Terminate
            print("Terminating!")
            break

if __name__ == "__main__":
    main()