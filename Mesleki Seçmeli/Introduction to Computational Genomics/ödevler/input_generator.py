import json
import random


def generate_dna_string(length: int):
    nucleotides = ('a', 't', 'g', 'c')
    string = []

    for i in range(length):
        string.append(random.choice(nucleotides))

    return ''.join(string)


def apply_mutation(kmer: str, count: int):
    nucleotides = ('a', 't', 'g', 'c')
    changed_index = {}
    chars = list(kmer)
    size = len(chars)

    for i in range(count):
        # pick out a random position in the k-mer
        random_index = random.randint(0, size - 1)

        # if this index is changed before try different one
        while random_index in changed_index:
            random_index = random.randint(0, size - 1)

        if random_index not in changed_index:
            # mark the current index as changed
            changed_index[random_index] = True
            # pick out a random nucleotide
            random_char = random.choice(nucleotides)

            # if the generated nucleotide is the same as the current one find a new one
            while random_char == chars[random_index]:
                random_char = random.choice(nucleotides)

            # change the nucleotide
            chars[random_index] = random_char

    return ''.join(chars)


dna_strings = []
N = 490  # size of the DNA string
R = 10  # number of DNA string
I = 10  # number of k-mers will be inserted
K = 10  # length of k-mer to insert random positions
M = 4  # number of mutations for generated k-mer

for i in range(R):
    dna_strings.append(generate_dna_string(N))

k_mer = generate_dna_string(K)

mutated_k_mers = []

final_strings = []

for i in range(I):
    mutated_k_mer = apply_mutation(kmer=k_mer, count=M)
    mutated_k_mers.append(mutated_k_mer)
    random_position = random.randint(0, N)
    if random_position == N:
        final_strings.append(dna_strings[i] + mutated_k_mer)
    else:
        final_strings.append(dna_strings[i][:random_position] + mutated_k_mer + dna_strings[i][random_position:])

print(dna_strings)
print(k_mer)
print(mutated_k_mers)
print(final_strings)

json_output = {'dna_strings': dna_strings, 'k_mer': k_mer, 'mutated_kmers': mutated_k_mers,
               'final_strings': final_strings}

with open("output.json", "w") as outfile:
    json.dump(json_output, outfile, indent=4)

with open('output.txt', 'w') as f:
    for string in final_strings:
        f.write(f"{string}\n")
