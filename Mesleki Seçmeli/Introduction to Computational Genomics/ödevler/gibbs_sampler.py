import random
import helpers


def gibbs_sampler(dna_strings: list[str], k: int, iterations=50) -> list[str]:
    # randomly select motif from each DNA string
    motifs = helpers.select_random_kmers(dna_strings, k)

    # Consensus motif score
    best_motifs_score = helpers.calculate_score(motifs)
    best_motifs = motifs

    # Iterate gibbs sampler by given number
    iteration = 0

    while iteration < iterations:
        line_to_ignore = random.randint(0, len(dna_strings) - 1)

        # Create count matrix
        # 0 <= line_to_ignore < len(motifs)
        count_matrix = helpers.calculate_count_matrix(motifs, line_to_ignore)

        # Update count matrix with pseudo counts
        count_matrix = update_count_matrix(count_matrix)

        # Create profile matrix
        motifs_profile = create_profile_matrix(count_matrix, len(count_matrix) * 2)

        # Find all k-mers in the deleted line
        kmers = helpers.get_all_kmers(dna_strings[line_to_ignore], k)

        # Find the probabilities of k-mers found in the previous step
        kmer_probabilities = []
        best_probability = 0
        best_probability_kmer = None

        for kmer in kmers:
            kmer_probability = helpers.calculate_motif_probability(kmer, motifs_profile)
            kmer_probabilities.append(kmer_probability)
            if kmer_probability > best_probability:
                best_probability = kmer_probability
                best_probability_kmer = kmer

        # Add the most probable kmer to the position of removed motif
        motifs[line_to_ignore] = best_probability_kmer

        # Calculate the score of motifs again
        curr_score = helpers.calculate_score(motifs)
        if curr_score < best_motifs_score:
            best_motifs_score = curr_score
            best_motifs = motifs

        iteration += 1

    return best_motifs


def update_count_matrix(count_matrix):
    rows = len(count_matrix)
    for r in range(rows):
        count_matrix[r]['a'] += 1
        count_matrix[r]['c'] += 1
        count_matrix[r]['g'] += 1
        count_matrix[r]['t'] += 1

    return count_matrix


def create_profile_matrix(count_matrix, denominator: int):
    rows = len(count_matrix)
    for r in range(rows):
        count_matrix[r]['a'] /= denominator
        count_matrix[r]['c'] /= denominator
        count_matrix[r]['g'] /= denominator
        count_matrix[r]['t'] /= denominator

    return count_matrix
