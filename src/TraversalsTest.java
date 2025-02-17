import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TraversalsTest {

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
        /*
         *            13
         *           /  \
         *         -5    42
         *         / \   /
         *       9   9  100
         *          / \
         *         31  7
         *
         * Leaf nodes: 9, 31, 7, 100
         * Sum = 9 + 31 + 7 + 100 = 147
         */
        TreeNode<Integer> root = new TreeNode<>(
            13,
            new TreeNode<>(
                -5,
                new TreeNode<>(9),
                new TreeNode<>(
                    9,
                    new TreeNode<>(31),
                    new TreeNode<>(7)
                )
            ),
            new TreeNode<>(
                42,
                new TreeNode<>(100),
                null
            )
        );
        int expected = 147;
        assertEquals(expected, Traversals.sumLeafNodes(root));
    }

    @Test
    void testSumLeafNodes_unbalancedTree() {
        /*
         * 10
         *   \
         *    11
         *      \
         *       12
         *         \
         *          13
         *
         * Leaf node is just 13
         * Sum = 13
         */
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
        /*
         *            8
         *           / \
         *          4   15
         *         / \   / \
         *       -2  5  10  20
         *
         * Internal nodes: 8, 4, 15
         * count = 3
         */
        TreeNode<Integer> root = new TreeNode<>(
            8,
            new TreeNode<>(
                4,
                new TreeNode<>(-2),
                new TreeNode<>(5)
            ),
            new TreeNode<>(
                15,
                new TreeNode<>(10),
                new TreeNode<>(20)
            )
        );
        int expected = 3;
        assertEquals(expected, Traversals.countInternalNodes(root));
    }

    @Test
    void testCountInternalNodes_withDuplicates() {
        /*
         *       7
         *      / \
         *     7   7
         *    /   / \
         *   7   7   10
         *
         * Internal: root(7), left(7), right(7)
         * => count = 3
         */
        TreeNode<Integer> root = new TreeNode<>(
            7,
            new TreeNode<>(
                7,
                new TreeNode<>(7),
                null
            ),
            new TreeNode<>(
                7,
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
        /*
         *            "A1"
         *           /    \
         *        "Bb2"   "C3C"
         *        /   \       \
         *   "Xx9"   "Kk1"    "ZzZ"
         *
         * Post-order: Xx9, Kk1, Bb2, ZzZ, C3C, A1
         * => "Xx9Kk1Bb2ZzZC3CA1"
         */
        TreeNode<String> root = new TreeNode<>(
            "A1",
            new TreeNode<>(
                "Bb2",
                new TreeNode<>("Xx9"),
                new TreeNode<>("Kk1")
            ),
            new TreeNode<>(
                "C3C",
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
        /*
         *        "Alpha"
         *         /    \
         *   "Beta7"     "Gamma1"
         *    /  \
         * "D#4"  "E9"
         *
         * Level order: Alpha, Beta7, Gamma1, D#4, E9
         */
        TreeNode<String> root = new TreeNode<>(
            "Alpha",
            new TreeNode<>(
                "Beta7",
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
        /*
         *              13
         *             /  \
         *           13    9
         *          /     / \
         *         9     13  7
         *
         * Distinct values: 13, 9, 7 => 3
         */
        TreeNode<Integer> root = new TreeNode<>(
            13,
            new TreeNode<>(
                13,
                new TreeNode<>(9),
                null
            ),
            new TreeNode<>(
                9,
                new TreeNode<>(13),
                new TreeNode<>(7)
            )
        );
        int expected = 3;
        assertEquals(expected, Traversals.countDistinctValues(root));
    }

    @Test
    void testCountDistinctValues_negativeAndPositive() {
        /*
         *         0
         *        / \
         *      -1   1
         *      /   / \
         *    -1   10  0
         *
         * Distinct: -1, 0, 1, 10 => 4
         */
        TreeNode<Integer> root = new TreeNode<>(
            0,
            new TreeNode<>(
                -1,
                new TreeNode<>(-1),
                null
            ),
            new TreeNode<>(
                1,
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
        /*
         * Single node: path of length 1 is trivially strictly increasing
         */
        boolean expected = true;
        TreeNode<Integer> root = new TreeNode<>(5);
        assertEquals(expected, Traversals.hasStrictlyIncreasingPath(root));
    }

    @Test
    void testHasStrictlyIncreasingPath_trueScenario() {
        /*
         *         3
         *       /   \
         *      5     2
         *     / \
         *    10  1
         *   /
         *  11
         *
         * Path 3 -> 5 -> 10 -> 11 is strictly increasing => true
         */
        TreeNode<Integer> root = new TreeNode<>(
            3,
            new TreeNode<>(
                5,
                new TreeNode<>(
                    10,
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
        /*
         *         7
         *        / \
         *       6   6
         *           / \
         *          5   4
         *
         * No strictly increasing path => false
         */
        TreeNode<Integer> root = new TreeNode<>(
            7,
            new TreeNode<>(6),
            new TreeNode<>(
                6,
                new TreeNode<>(5),
                new TreeNode<>(4)
            )
        );
        boolean expected = false;
        assertEquals(expected, Traversals.hasStrictlyIncreasingPath(root));
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
        /*
         * Tree A:            5               Tree B:            99
         *                  /    \                             /    \
         *                3       10                          100    0
         *                       /                                   /
         *                      8                                   8
         *
         * Same shape => true
         */
        TreeNode<Integer> a = new TreeNode<>(
            5,
            new TreeNode<>(3),
            new TreeNode<>(10, new TreeNode<>(8), null)
        );
        TreeNode<Integer> b = new TreeNode<>(
            99,
            new TreeNode<>(100),
            new TreeNode<>(0, new TreeNode<>(8), null)
        );
        assertTrue(Traversals.haveSameShape(a, b));
    }

    @Test
    void testHaveSameShape_differentShape() {
        /*
         * Tree A:           7
         *                 /   \
         *                6    15
         * Tree B:         7
         *                /
         *               6
         *
         * => false
         */
        TreeNode<Integer> a = new TreeNode<>(
            7,
            new TreeNode<>(6),
            new TreeNode<>(15)
        );
        TreeNode<Integer> b = new TreeNode<>(
            7,
            new TreeNode<>(6),
            null
        );
        assertFalse(Traversals.haveSameShape(a, b));
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
        /*
         *       "R"
         *      /   \
         *   "L"    "RightNode"
         *    / \
         * "LL" "LR"
         *
         * Paths:
         *   R -> L -> LL
         *   R -> L -> LR
         *   R -> RightNode
         */
        TreeNode<String> root = new TreeNode<>(
            "R",
            new TreeNode<>(
                "L",
                new TreeNode<>("LL"),
                new TreeNode<>("LR")
            ),
            new TreeNode<>("RightNode")
        );
        List<List<String>> expected = new ArrayList<>();
        expected.add(List.of("R", "L", "LL"));
        expected.add(List.of("R", "L", "LR"));
        expected.add(List.of("R", "RightNode"));

        // Order of paths in the output may differ, but let's compare directly here.
        assertEquals(expected, Traversals.findAllRootToLeafPaths(root));
    }

    @Test
    void testFindAllRootToLeafPaths_integerTree() {
        /*
        *         1
        *        / \
        *       2   3
        *      / \    \
        *     4   5    6
        *
        * Expected Paths (in pre-order traversal):
        *   1 -> 2 -> 4
        *   1 -> 2 -> 5
        *   1 -> 3 -> 6
        */
        TreeNode<Integer> root = new TreeNode<>(
            1,
            new TreeNode<>(
                2,
                new TreeNode<>(4),
                new TreeNode<>(5)
            ),
            new TreeNode<>(
                3,
                null,
                new TreeNode<>(6)
            )
        );

        List<List<Integer>> expected = new ArrayList<>();
        expected.add(List.of(1, 2, 4));
        expected.add(List.of(1, 2, 5));
        expected.add(List.of(1, 3, 6));

        assertEquals(expected, Traversals.findAllRootToLeafPaths(root));
    }
}
