import time

import helpers
import json
from randomized_motif_search import randomized_motif_search
from gibbs_sampler import gibbs_sampler

# Opening JSON file
f = open('output.json')
data = json.load(f)
f.close()

dna_strings = data['final_strings']
k_sizes = [9, 10, 11]


for k in k_sizes:
    start = time.time()
    randomized = randomized_motif_search(dna_strings, k)
    print("Randomized motif search (k=%2d): " % k, helpers.calculate_score(randomized), randomized)
    end = time.time()
    print("Time spent (ms): ", (end-start) * 10**3)

    start = time.time()
    gibbs = gibbs_sampler(dna_strings, k)
    print("Gibbs sampler (k=%2d): " % k, helpers.calculate_score(gibbs), gibbs)
    end = time.time()
    print("Time spent (ms): ", (end - start) * 10 ** 3)

    print("\n")

