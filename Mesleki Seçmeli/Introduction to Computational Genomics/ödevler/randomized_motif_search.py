import helpers


def randomized_motif_search(dna_strings: list[str], k: int) -> list[str]:
    # randomly select motif from each DNA string
    motifs = helpers.select_random_kmers(dna_strings, k)
    # initially the best motifs are the first chosen ones
    best_motifs = motifs

    while True:
        profile = helpers.calculate_motifs_profile(best_motifs)
        new_motif = find_new_motifs(profile, dna_strings, k)
        motif_score = helpers.calculate_score(new_motif)
        best_score = helpers.calculate_score(best_motifs)
        if motif_score < best_score:
            best_motifs = new_motif
            best_score = motif_score
        else:
            break

    return best_motifs


def find_new_motifs(profile: list[dict], dna_strings: list[str], length: int) -> list[str]:
    best_motifs = []
    rows, cols = len(dna_strings), len(dna_strings[0])

    for r in range(rows):  # do this operation for every row
        row_best_motif = None
        row_best_motif_chance = 0
        for c in range(cols - length):  # check every kmer from starting index 0
            curr_motif = dna_strings[r][c:c + length]
            new_motif_chance = helpers.calculate_motif_probability(curr_motif, profile)
            if new_motif_chance > row_best_motif_chance:
                row_best_motif_chance = new_motif_chance
                row_best_motif = curr_motif
        best_motifs.append(row_best_motif)
    return best_motifs
