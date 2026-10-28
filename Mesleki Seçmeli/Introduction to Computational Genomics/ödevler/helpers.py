import random


def find_consensus_string(motifs: list[str]) -> list[str]:
    rows, cols = len(motifs), len(motifs[0])
    consensus_string = []

    for c in range(cols):
        char_counts = {'a': 0, 'c': 0, 'g': 0, 't': 0}
        highest_occurrence = 0
        most_repeated_char = None
        for r in range(rows):
            char_counts[motifs[r][c]] += 1
            if char_counts[motifs[r][c]] > highest_occurrence:
                highest_occurrence = char_counts[motifs[r][c]]
                most_repeated_char = motifs[r][c]

        consensus_string.append(most_repeated_char)

    return consensus_string


def get_all_kmers(dna_string: str, length: int) -> list[str]:
    selected_motifs = []

    for starting_point in range(len(dna_string) - length):
        selected_motifs.append(dna_string[starting_point:starting_point + length])

    return selected_motifs


def select_random_kmers(dna_strings: list[str], length: int) -> list[str]:
    selected_motifs = []

    for dna_str in dna_strings:
        starting_point = random.randint(0, len(dna_str) - length)
        selected_motifs.append(dna_str[starting_point:starting_point + length])

    return selected_motifs


def calculate_count_matrix(motifs: list[str], line_to_ignore: int) -> list[list[int]]:
    rows, cols = len(motifs), len(motifs[0])
    profile = [[0] * rows] * cols

    for c in range(cols):  # iterate over columns
        curr_profile = {'a': 0, 'c': 0, 'g': 0, 't': 0}
        for r in range(rows):  # iterate over rows
            if r == line_to_ignore:
                continue
            curr_profile[motifs[r][c]] += 1

        profile[c] = curr_profile

    return profile


def calculate_motifs_profile(motifs: list[str]) -> list[dict]:
    rows, cols = len(motifs), len(motifs[0])
    profile = [dict()] * cols

    for c in range(cols):  # iterate over columns
        curr_profile = {'a': 0, 'c': 0, 'g': 0, 't': 0}
        for r in range(rows):  # iterate over rows
            curr_profile[motifs[r][c]] += 1

        curr_profile['a'] /= rows
        curr_profile['c'] /= rows
        curr_profile['g'] /= rows
        curr_profile['t'] /= rows

        profile[c] = curr_profile

    return profile


def calculate_score(motifs: list[str]) -> int:
    if len(motifs) == 0:
        return 0
    rows, cols = len(motifs), len(motifs[0])
    score = [0] * cols

    for c in range(cols):
        counts = {'a': 0, 'c': 0, 'g': 0, 't': 0}
        for r in range(rows):  # iterate over rows
            counts[motifs[r][c]] += 1

        maximum = max(counts['a'], counts['c'], counts['g'], counts['t'])
        score[c] = rows - maximum

    return sum(score)


def calculate_motif_probability(motif: str, profile: list[dict]):
    probability = 1
    for i in range(len(motif)):
        if profile[i][motif[i]] == 0:
            return 0
        probability *= profile[i][motif[i]]

    return probability
