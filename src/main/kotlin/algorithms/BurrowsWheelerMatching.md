# Burrows Wheeler Matching explanation

https://rosalind.info/problems/ba9m/

This is roughly the algorithm as implemented.

    BETTERBWMATCHING(FirstOccurrence, LastColumn, Pattern, Count)
        top ← 0
        bottom ← |LastColumn| − 1

        while top ≤ bottom
            if Pattern is nonempty
                symbol ← last letter in Pattern
                remove last letter from Pattern
                if positions from top to bottom in LastColumn contain an occurrence of symbol
                    top ← FirstOccurrence(symbol) + Countsymbol(top, LastColumn)
                    bottom ← FirstOccurrence(symbol) + Countsymbol(bottom + 1, LastColumn) − 1
                else
                    return 0
            else
                return bottom − top + 1 