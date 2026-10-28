using Console = System.Console;
using Math = System.Math;
using System.IO;
using System.Collections.Generic;

namespace GenomHW2
{
    internal class MainClass
    {
        private static void Main(string[] args)
        {
            Pairwise pairwise = new Pairwise();

            pairwise.InitAlignment("test1.seq");
        }
    }

    class Pairwise
    {
        private string s1;

        private string s2;

        private float match_score = 3;

        private float mismatch_score = -1;

        private float gap_open = -1;

        private float gap_extend = -0.5f;

        public void InitAlignment(string filePath)
        {
            string[] lines = File.ReadAllLines(filePath);
            s1 = lines[0];
            s2 = lines[1];

            int len1 = s1.Length;
            int len2 = s2.Length;

            // matrix to store scores for matches and missmatches
            float[,] middleMatrix = new float[len1 + 1, len2 + 1];

            // matrix to store scores for insertion in seq1
            float[,] upperMatrix = new float[len1 + 1, len2 + 1];
            for (int i = 0; i < len1 + 1; i++)
            {
                for (int j = 0; j < len2 + 1; j++)
                {
                    upperMatrix[i, j] = float.NegativeInfinity;
                }
            }

            // matrix to store scores for deletion in seq2
            float[,] lowerMatrix = new float[len1 + 1, len2 + 1];
            for (int i = 0; i < len1 + 1; i++)
            {
                for (int j = 0; j < len2 + 1; j++)
                {
                    lowerMatrix[i, j] = float.NegativeInfinity;
                }
            }

            // initialize matrices with using gap penalties and -infinity because there is no possible value smaller than -infinity
            middleMatrix[0, 0] = 0;
            for (int i = 1; i <= len1; i++)
            {
                middleMatrix[i, 0] = gap_open + ((i - 1) * gap_extend);
                upperMatrix[i, 0] = middleMatrix[i, 0];
                lowerMatrix[i, 0] = float.NegativeInfinity;
            }

            for (int j = 1; j <= len2; j++)
            {
                middleMatrix[0, j] = gap_open + ((j - 1) * gap_extend);
                upperMatrix[0, j] = float.NegativeInfinity;
                lowerMatrix[0, j] = middleMatrix[0, j];
            }

            // fill matrices with highest possible aligment score
            for (int i = 1; i <= len1; i++)
            {
                // fill matrices with checking score of previous nodes
                for (int j = 1; j <= len2; j++)
                {
                    upperMatrix[i, j] =
                        Math.Max(middleMatrix[i - 1, j] + gap_open,
                            upperMatrix[i - 1, j] + gap_extend);
                    

                    lowerMatrix[i, j] =
                        Math.Max(middleMatrix[i, j - 1] + gap_open,
                            lowerMatrix[i, j - 1] + gap_extend);

                    middleMatrix[i, j] =
                          Math.Max(middleMatrix[i - 1, j - 1] +
                              (s1[i - 1] == s2[j - 1] ? match_score : mismatch_score),
                              Math.Max(upperMatrix[i, j], lowerMatrix[i, j]));
                }
            }

            // aligned sequences
            List<char> align1 = new List<char>();
            List<char> align2 = new List<char>();

            // initialize indices to be at the bottom right corner of the matrix
            int a = len1;
            int b = len2;

            // find the score with tracebacking from bottom right corner until we reach the top left corner of the matrix
            while (a > 0 || b > 0)
            {
                // if current node reached by matching or mismatching
                if (
                    a > 0 && b > 0 && middleMatrix[a, b] == middleMatrix[a - 1, b - 1]
                    + (s1[a - 1] == s2[b - 1] ? match_score : mismatch_score)
                )
                {
                    //adds characters from both sequences to new alignments
                    align1.Add(s1[a - 1]);
                    align2.Add(s2[b - 1]);
                    a -= 1;
                    b -= 1;
                }
                // if current node is reached from upper matrix (insertion)
                else if (a > 0 && middleMatrix[a, b] == upperMatrix[a, b])
                {
                    //adds only a character from sequence 1 and adds a gap to sequence 2
                    align1.Add(s1[a - 1]);
                    align2.Add('-');
                    a -= 1;
                }
                // if current node is reached from lower matrix (deletion)
                else if (b > 0 && middleMatrix[a, b] == lowerMatrix[a, b])
                {
                    //adds only a character from sequence 2 and adds a gap to sequence 1
                    align1.Add('-');
                    align2.Add(s2[b - 1]);
                    b -= 1;
                }
            }

            Console.WriteLine(new string(align1.ToArray()) + "\n\n" + new string(align2.ToArray()) + "\n\n" + middleMatrix[len1, len2]);
        }
    }
}