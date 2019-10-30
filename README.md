Block Stacking Problem, by Alex Franklin and Xander Koo

**src/ps9q3/BlockDimensions.java:**

This class represents a block with three dimensions, length, width, and height. It is used in the BlockStacking class to keep track of all the blocks and call methods to check if blocks fit on one another and to compare them.

**src/ps9q3/BlockStacking.java:**

This class runs the actual algorithm. It takes an input file and creates a list representing all of the possible orientations of the blocks. Then it runs the algorithm and puts an optimal solution into the output file it is given.

**To compile and run:**

```
cd path/to/file/cs140_ps9q3/src
javac ps9q3/*.java
java BlockStacking <in_path> <out_path>
```

[Click here for documentation](https://docs.google.com/document/d/1uuO0SaK7CmO7X_I1ysrdSvurMDeQMK3vQpYrLzkpiyY/edit?ts=5db91426)
