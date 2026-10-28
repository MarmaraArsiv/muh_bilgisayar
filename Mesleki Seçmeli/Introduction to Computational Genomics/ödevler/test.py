import json
import time
import median_string

motifs = ['taac', 'gtct', 'ccgg', 'acta', 'aggt']

f = open('output.json')
data = json.load(f)
f.close()

dna_strings = data['final_strings']

# GGCTAT is the expected result
listofdna = ['CCTCCCCGCGTCGGATATTCCCTATGTGCTTTATGAAGCGTC',
             'TGAACGGGTGAAGGGTATAATCAGTGTGGCTGGGGACGCAAG',
             'TCAAAGGCGGAGTTAGGTTTCCACTTCAGCATCTAGGGCTAT',
             'TGGGGATAGACTAGGTACACTTCACGGCGGGGCTATGTAACT',
             'CAGGGGCCGGTGCCATATGGTTATTAAAAAGCAATGTCCTGG',
             'CTGACGTCAAATGGACGAATTTGGTTAGCACCCACTGGCAGC',
             'GGCTATAGTCAGAGTAACAGTTCCCGATTGTCAGACACAACA',
             'CCTGTCGGCTATACCCCAAGGGAAAGCGGAGCATTAGAAATG',
             'GGATATAATATGCCCAGTGCGAGCATTGTAGTCGATATACGT',
             'GGCTATCCTTTATAAGAGAACGGAGATGTCGGGCCCGGAAAG']

start = time.time()
print(median_string.find_median_string(6, listofdna))
end = time.time()
msg = "The time of execution of above program is :" + str((end - start) * 10 ** 3) + "ms"
print(msg)
