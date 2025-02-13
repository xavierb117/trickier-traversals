import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TraversalsTest {

    /**
     * Utility method to easily build a TreeNode with left and right subtrees.
     */
    private TreeNode<Integer> node(Integer value, TreeNode<Integer> left, TreeNode<Integer> right) {
        return new TreeNode<>(value, left, right);
    }

    /**
     * Utility method to build a TreeNode of type String (for post-order tests).
     */
    private TreeNode<String> nodeS(String value, TreeNode<String> left, TreeNode<String> right) {
        return new TreeNode<>(value, left, right);
    }

    // ---------------------------------------------------------
    // Test: sumLeafNodes
    // ---------------------------------------------------------

    @Test
    void testSumLeafNodes_nullTree() {
        TreeNode<Integer> root = null;
        int expected = 0; // If tree is null, sum of leaf nodes should be 0
        assertEquals(expected, Traversals.sumLeafNodes(root));
    }

    @Test
    void testSumLeafNodes_singleNode() {
        TreeNode<Integer> root = new TreeNode<>(47);
        int expected = 47; // Single node is a leaf, so sum = node's value
        assertEquals(expected, Traversals.sumLeafNodes(root));
    }

    @Test
    void testSumLeafNodes_multipleLevels() {
        // Construct a tree:
        //
        //            13
        //           /  \
        //         -5    42
        //         / \   /
        //       9   9  100
        //          / \
        //         31  7
        //
        // Leaf nodes: 9, 31, 7, 100
        // Sum: 9 + 31 + 7 + 100 = 147
        TreeNode<Integer> root = node(
            13,
            node(-5,
                 new TreeNode<>(9),
                 node(9,
                      new TreeNode<>(31),
                      new TreeNode<>(7))
            ),
            node(42, new TreeNode<>(100), null)
        );
        int expected = 147;
        assertEquals(expected, Traversals.sumLeafNodes(root));
    }

    @Test
    void testSumLeafNodes_unbalancedTree() {
        // Construct a highly unbalanced tree:
        //
        // 10
        //   \
        //    11
        //      \
        //       12
        //         \
        //          13
        //
        // Leaf node is just 13
        // Sum = 13
        TreeNode<Integer> root = new TreeNode<>(10,
            null,
            new TreeNode<>(11,
                null,
                new TreeNode<>(12,
                    null,
                    new TreeNode<>(13)
                )
            )
        );
        int expected = 13;
        assertEquals(expected, Traversals.sumLeafNodes(root));
    }

    // ---------------------------------------------------------
    // Test: countInternalNodes
    // ---------------------------------------------------------

    @Test
    void testCountInternalNodes_nullTree() {
        TreeNode<Integer> root = null;
        int expected = 0;
        assertEquals(expected, Traversals.countInternalNodes(root));
    }

    @Test
    void testCountInternalNodes_singleNode() {
        TreeNode<Integer> root = new TreeNode<>(99);
        int expected = 0; // Single node with no children => no internal nodes
        assertEquals(expected, Traversals.countInternalNodes(root));
    }

    @Test
    void testCountInternalNodes_balancedTree() {
        //            8
        //           / \
        //          4   15
        //         / \   / \
        //       -2  5  10  20
        //
        // Internal nodes: 8, 4, 15
        // count = 3
        TreeNode<Integer> root = node(
            8,
            node(4,
                new TreeNode<>(-2),
                new TreeNode<>(5)
            ),
            node(15,
                new TreeNode<>(10),
                new TreeNode<>(20)
            )
        );
        int expected = 3;
        assertEquals(expected, Traversals.countInternalNodes(root));
    }

    @Test
    void testCountInternalNodes_withDuplicates() {
        //       7
        //      / \
        //     7   7
        //    /   / \
        //   7   7   10
        // (Internal nodes: 7 [root], 7 [left], 7 [right], 7 [right->left]?)
        // Actually let's label them for clarity:
        // root(7) -> left(7) -> left(7) [leaf], right == null => left(7) is internal because it has a left child
        // root(7) -> right(7) -> left(7) [leaf], right(10) [leaf] => the right(7) is internal
        //
        // Internal: root(7), left(7), right(7)
        // => count = 3
        TreeNode<Integer> root = node(
            7,
            node(7,
                new TreeNode<>(7),
                null
            ),
            node(7,
                new TreeNode<>(7),
                new TreeNode<>(10)
            )
        );
        int expected = 3;
        assertEquals(expected, Traversals.countInternalNodes(root));
    }

    // ---------------------------------------------------------
    // Test: buildPostOrderString
    // ---------------------------------------------------------

    @Test
    void testBuildPostOrderString_nullTree() {
        TreeNode<String> root = null;
        String expected = "";
        assertEquals(expected, Traversals.buildPostOrderString(root));
    }

    @Test
    void testBuildPostOrderString_singleNode() {
        TreeNode<String> root = new TreeNode<>("Xyz");
        String expected = "Xyz"; // Only one node => post-order is just that value
        assertEquals(expected, Traversals.buildPostOrderString(root));
    }

    @Test
    void testBuildPostOrderString_multiLevel() {
        //            "A1"
        //           /    \
        //        "Bb2"   "C3C"
        //        /   \       \
        //   "Xx9"   "Kk1"    "ZzZ"
        //
        // Post-order should be: Xx9 Kk1 Bb2 ZzZ C3C A1
        // Concatenate => "Xx9Kk1Bb2ZzZC3CA1"
        TreeNode<String> root = nodeS(
            "A1",
            nodeS("Bb2",
                  new TreeNode<>("Xx9"),
                  new TreeNode<>("Kk1")
            ),
            nodeS("C3C",
                  null,
                  new TreeNode<>("ZzZ")
            )
        );
        String expected = "Xx9Kk1Bb2ZzZC3CA1";
        assertEquals(expected, Traversals.buildPostOrderString(root));
    }

    // ---------------------------------------------------------
    // Test: collectLevelOrderValues
    // ---------------------------------------------------------

    @Test
    void testCollectLevelOrderValues_nullTree() {
        TreeNode<String> root = null;
        List<String> expected = Collections.emptyList();
        assertEquals(expected, Traversals.collectLevelOrderValues(root));
    }

    @Test
    void testCollectLevelOrderValues_singleNode() {
        TreeNode<String> root = new TreeNode<>("RootOnly");
        List<String> expected = List.of("RootOnly");
        assertEquals(expected, Traversals.collectLevelOrderValues(root));
    }

    @Test
    void testCollectLevelOrderValues_generalTree() {
        //        "Alpha"
        //         /    \
        //   "Beta7"     "Gamma1"
        //    /  \
        // "D#4"  "E9"
        //
        // Level order: Alpha, Beta7, Gamma1, D#4, E9
        TreeNode<String> root = nodeS(
            "Alpha",
            nodeS("Beta7",
                  new TreeNode<>("D#4"),
                  new TreeNode<>("E9")
            ),
            new TreeNode<>("Gamma1")
        );
        List<String> expected = List.of("Alpha", "Beta7", "Gamma1", "D#4", "E9");
        assertEquals(expected, Traversals.collectLevelOrderValues(root));
    }

    // ---------------------------------------------------------
    // Test: countDistinctValues
    // ---------------------------------------------------------

    @Test
    void testCountDistinctValues_nullTree() {
        TreeNode<Integer> root = null;
        int expected = 0;
        assertEquals(expected, Traversals.countDistinctValues(root));
    }

    @Test
    void testCountDistinctValues_singleNode() {
        TreeNode<Integer> root = new TreeNode<>(111);
        int expected = 1;
        assertEquals(expected, Traversals.countDistinctValues(root));
    }

    @Test
    void testCountDistinctValues_withDuplicates() {
        //              13
        //             /  \
        //           13    9
        //          /     / \
        //         9     13  7
        // Distinct values: 13, 9, 7
        // => 3
        TreeNode<Integer> root = node(
            13,
            node(13,
                new TreeNode<>(9),
                null
            ),
            node(9,
                new TreeNode<>(13),
                new TreeNode<>(7)
            )
        );
        int expected = 3;
        assertEquals(expected, Traversals.countDistinctValues(root));
    }

    @Test
    void testCountDistinctValues_negativeAndPositive() {
        //         0
        //        / \
        //      -1   1
        //      /   / \
        //    -1   10  0
        // Distinct: -1, 0, 1, 10
        // => 4
        TreeNode<Integer> root = node(
            0,
            node(-1,
                new TreeNode<>(-1),
                null
            ),
            node(1,
                new TreeNode<>(10),
                new TreeNode<>(0)
            )
        );
        int expected = 4;
        assertEquals(expected, Traversals.countDistinctValues(root));
    }

    // ---------------------------------------------------------
    // Test: hasStrictlyIncreasingPath
    // ---------------------------------------------------------

    @Test
    void testHasStrictlyIncreasingPath_nullTree() {
        TreeNode<Integer> root = null;
        boolean expected = false;
        assertEquals(expected, Traversals.hasStrictlyIncreasingPath(root));
    }

    @Test
    void testHasStrictlyIncreasingPath_singleNode() {
        // Single node: no child to continue, but it *is* a root-to-leaf path in itself.
        // Strictly increasing typically implies at least consecutive nodes, 
        // but depending on your interpretation, a single node might be trivially strictly increasing.
        // Let's assume we define a single-node path as valid => expected = true OR false 
        // This is a design choice. Let's say we interpret "strictly > previous" requires at least 2 nodes,
        // so let's assume expected = true for single node => path of length 1 is trivially increasing
        boolean expected = true;
        TreeNode<Integer> root = new TreeNode<>(5);
        assertEquals(expected, Traversals.hasStrictlyIncreasingPath(root));
    }

    @Test
    void testHasStrictlyIncreasingPath_trueScenario() {
        // Tree with an increasing path 3 -> 5 -> 10 -> 11
        //
        //         3
        //       /   \
        //      5     2
        //     / \
        //    10  1
        //   /
        //  11
        //
        // There's a path: 3 -> 5 -> 10 -> 11 (strictly increasing).
        // So this should return true.
        TreeNode<Integer> root = node(
            3,
            node(5,
                node(10,
                    new TreeNode<>(11),
                    null
                ),
                new TreeNode<>(1)
            ),
            new TreeNode<>(2)
        );
        boolean expected = true;
        assertEquals(expected, Traversals.hasStrictlyIncreasingPath(root));
    }

    @Test
    void testHasStrictlyIncreasingPath_falseScenario() {
        //         7
        //        / \
        //       6   6
        //           / \
        //          5   4
        //
        // No root-to-leaf path that strictly increases each step.
        // Best path: 7 -> 6 -> 5 => 7 > 6 but 6 > 5 => not strictly increasing
        // => expected false
        TreeNode<Integer> root = node(
            7,
            new TreeNode<>(6),
            node(6,
                new TreeNode<>(5),
                new TreeNode<>(4)
            )
        );
        boolean expected = false;
        assertEquals(expected, Traversals.hasStrictlyIncreasingPath(root));
    }

    // ---------------------------------------------------------
    // Test (Challenge): findAllRootToLeafPaths
    // ---------------------------------------------------------

    @Test
    void testFindAllRootToLeafPaths_nullTree() {
        TreeNode<String> root = null;
        List<List<String>> expected = Collections.emptyList();
        assertEquals(expected, Traversals.findAllRootToLeafPaths(root));
    }

    @Test
    void testFindAllRootToLeafPaths_singleNode() {
        TreeNode<String> root = new TreeNode<>("Single");
        // Only one root-to-leaf path: ["Single"]
        List<List<String>> expected = List.of(List.of("Single"));
        assertEquals(expected, Traversals.findAllRootToLeafPaths(root));
    }

    @Test
    void testFindAllRootToLeafPaths_multiplePaths() {
        //       "R"
        //      /   \
        //   "L"    "RightNode"
        //    / \
        // "LL" "LR"
        // 
        // Paths:
        //   R -> L -> LL
        //   R -> L -> LR
        //   R -> RightNode
        //
        // As lists:
        //   ["R", "L", "LL"]
        //   ["R", "L", "LR"]
        //   ["R", "RightNode"]
        TreeNode<String> root = nodeS(
            "R",
            nodeS("L",
                  new TreeNode<>("LL"),
                  new TreeNode<>("LR")
            ),
            new TreeNode<>("RightNode")
        );
        List<List<String>> expected = new ArrayList<>();
        expected.add(List.of("R", "L", "LL"));
        expected.add(List.of("R", "L", "LR"));
        expected.add(List.of("R", "RightNode"));

        // The order of the paths in your returned List may differ
        // If you're enforcing a certain DFS/left-to-right approach, 
        // you can sort them before compare or just rely on that approach.
        // For simplicity here, let's do direct equality check.
        assertEquals(expected, Traversals.findAllRootToLeafPaths(root));
    }

    // ---------------------------------------------------------
    // Test (Challenge): haveSameShape
    // ---------------------------------------------------------

    @Test
    void testHaveSameShape_bothNull() {
        TreeNode<Integer> a = null;
        TreeNode<Integer> b = null;
        assertTrue(Traversals.haveSameShape(a, b));
    }

    @Test
    void testHaveSameShape_oneNullOneNonNull() {
        TreeNode<Integer> a = null;
        TreeNode<Integer> b = new TreeNode<>(10);
        assertFalse(Traversals.haveSameShape(a, b));
    }

    @Test
    void testHaveSameShape_sameShapeDifferentValues() {
        // Tree A:            5               Tree B:            99
        //                  /    \                             /    \
        //                3       10                          100    0
        //                       /                                   /
        //                      8                                   8
        //
        // They have the same shape, but different values.
        // => should return true for shape equality.
        TreeNode<Integer> a = new TreeNode<>(5,
            new TreeNode<>(3),
            new TreeNode<>(10, new TreeNode<>(8), null)
        );

        TreeNode<Integer> b = new TreeNode<>(99,
            new TreeNode<>(100),
            new TreeNode<>(0, new TreeNode<>(8), null)
        );

        assertTrue(Traversals.haveSameShape(a, b));
    }

    @Test
    void testHaveSameShape_differentShape() {
        // Tree A:           7
        //                 /   \
        //                6    15
        // Tree B:         7
        //                / 
        //               6
        // 
        // Different shape => false
        TreeNode<Integer> a = new TreeNode<>(7,
            new TreeNode<>(6),
            new TreeNode<>(15)
        );
        TreeNode<Integer> b = new TreeNode<>(7,
            new TreeNode<>(6),
            null
        );
        assertFalse(Traversals.haveSameShape(a, b));
    }
}
