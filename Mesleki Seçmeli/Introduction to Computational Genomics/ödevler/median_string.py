from itertools import product
from typing import List


def hamming_distance(s1: str, s2: str) -> int:
    distance = 0
    n = len(s1)

    for i in range(n):
        if s1[i] != s2[i]:
            distance += 1

    return distance


def min_hamming_distance(kmer: str, dna_string: str) -> int:
    min_distance = len(kmer)
    for i in range(len(dna_string) - len(kmer) + 1):
        distance = hamming_distance(kmer, dna_string[i:i + len(kmer)])
        if distance < min_distance:
            min_distance = distance
    return min_distance


def all_kmers(k: int) -> List[str]:
    bases = ['A', 'C', 'G', 'T']
    # Get all combinations of bases at length k by using Python's itertools library
    result = [''.join(e) for e in product(bases, repeat=k)]
    return result


def find_median_string(k: int, dna_strings: List[str]) -> str:
    # Find all possible kmers at length k
    kmers = all_kmers(k)
    min_sum = len(dna_strings) * pow(4, k)
    result = None

    for kmer in kmers:
        sum_distance = 0

        for j in range(len(dna_strings)):
            sum_distance += min_hamming_distance(kmer, dna_strings[j])

        # If the current kmer gives has a lower sum of distance choose it
        if sum_distance < min_sum:
            min_sum = sum_distance
            result = kmer

    return result
