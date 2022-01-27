package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import models.Tree;
import utils.TreeUtil;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

class TreeTest {

	@Test
	void constructorTest() {
		// initalize a new tree object
		Tree tree = new Tree(1, "Pine", "PVC", 250, "Pine trees are the most common coniferous trees in the world. Plastic made, perfect for hanging heavy decorations.", "Big Trees Limited", 50, 5, 35);
		// check if the tree id matches
		assertEquals(1, tree.getId());
		// check if the tree type matches
		assertEquals("Pine", tree.getType());
		// check if the tree material matches
		assertEquals("PVC", tree.getMaterial());
		// check if the tree height matches
		assertEquals(250, tree.getHeight());
		// check if the tree description matches
		assertEquals("Pine trees are the most common coniferous trees in the world. Plastic made, perfect for hanging heavy decorations.", tree.getDescription());
		// check if the tree supplier name matches
		assertEquals("Big Trees Limited", tree.getSupplier());
		// check if the tree price per day matches
		assertEquals(50, tree.getPricePerDay());
		// check if the tree stock level matches
		assertEquals(5, tree.getStockLevel());
		// check if the tree deposit percentage matches
		assertEquals(35, tree.getDepositPercentage());
	}
	
	@Test
	void getFromDatabaseTest() {
		// initalize a new tree object
		Tree tree = new TreeUtil().getTree(1);
		// check if the tree id matches
		assertEquals(1, tree.getId());
		// check if the tree type matches
		assertEquals("Pine", tree.getType());
		// check if the tree material matches
		assertEquals("PVC", tree.getMaterial());
		// check if the tree height matches
		assertEquals(250, tree.getHeight());
		// check if the tree description matches
		assertEquals("Pine trees are the most common coniferous trees in the world. Plastic made, perfect for hanging heavy decorations.", tree.getDescription());
		// check if the tree supplier name matches
		assertEquals("Big Trees Limited", tree.getSupplier());
		// check if the tree price per day matches
		assertEquals(50, tree.getPricePerDay());
		// check if the tree stock level matches
		assertEquals(5, tree.getStockLevel());
		// check if the tree deposit percentage matches
		assertEquals(35, tree.getDepositPercentage());
	}
	
	@Test
	void insertIntoAndDeleteFromDatabaseTest() {
		// initalize a new tree object
		Tree tree = new Tree("Pine", "PVC", 250, "Pine trees are the most common coniferous trees in the world. Plastic made, perfect for hanging heavy decorations.", "Big Trees Limited", 50, 5, 35);
		
		// insert the tree and return the id
		int insertedTreeId = new TreeUtil().insertTree(tree);
		
		// get the new tree from the database
		tree = new TreeUtil().getTree(insertedTreeId);
		// check if the tree id matches
		assertEquals(tree.getId(), tree.getId());
		// check if the tree type matches
		assertEquals("Pine", tree.getType());
		// check if the tree material matches
		assertEquals("PVC", tree.getMaterial());
		// check if the tree height matches
		assertEquals(250, tree.getHeight());
		// check if the tree description matches
		assertEquals("Pine trees are the most common coniferous trees in the world. Plastic made, perfect for hanging heavy decorations.", tree.getDescription());
		// check if the tree supplier name matches
		assertEquals("Big Trees Limited", tree.getSupplier());
		// check if the tree price per day matches
		assertEquals(50, tree.getPricePerDay());
		// check if the tree stock level matches
		assertEquals(5, tree.getStockLevel());
		// check if the tree deposit percentage matches
		assertEquals(35, tree.getDepositPercentage());
		
		// delete the tree from the database
		new TreeUtil().deleteTree(tree.getId());
		
		// try to get the tree from the database
		// will return null
		tree = new TreeUtil().getTree(tree.getId());
		// check if the tree is null
		assertNull(tree);
	}

}
