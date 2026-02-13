package com.pushkar.postfix_evaluator.repository;

import com.pushkar.postfix_evaluator.model.Equation;
import com.pushkar.postfix_evaluator.model.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EquationRepository
 */
@DisplayName("Equation Repository Tests")
class EquationRepositoryTest {
    
    private EquationRepository repository;
    private Equation testEquation;
    
    @BeforeEach
    void setUp() {
        repository = new EquationRepository();
        testEquation = new Equation();
        testEquation.setEquationInfix("3*x + 2");
        testEquation.setRootNode(new TreeNode("+", null, null));
    }
    
    @Test
    @DisplayName("should save equation and generate unique ID")
    void testSaveEquation() {
        String id = repository.save(testEquation);
        assertNotNull(id);
        assertNotNull(testEquation.getId());
        assertEquals(id, testEquation.getId());
    }
    
    @Test
    @DisplayName("should generate unique IDs for multiple equations")
    void testUniqueIds() {
        Equation eq1 = new Equation();
        eq1.setEquationInfix("x + 1");
        eq1.setRootNode(new TreeNode());
        
        Equation eq2 = new Equation();
        eq2.setEquationInfix("y + 2");
        eq2.setRootNode(new TreeNode());
        
        String id1 = repository.save(eq1);
        String id2 = repository.save(eq2);
        
        assertNotEquals(id1, id2);
    }
    
    @Test
    @DisplayName("should find equation by ID")
    void testFindById() {
        String id = repository.save(testEquation);
        Equation found = repository.findById(id);
        
        assertNotNull(found);
        assertEquals(testEquation.getEquationInfix(), found.getEquationInfix());
    }
    
    @Test
    @DisplayName("should return null for non-existent ID")
    void testFindByIdNotFound() {
        Equation found = repository.findById("nonexistent");
        assertNull(found);
    }
    
    @Test
    @DisplayName("should find all equations")
    void testFindAll() {
        Equation eq1 = new Equation();
        eq1.setEquationInfix("eq1");
        eq1.setRootNode(new TreeNode());
        
        Equation eq2 = new Equation();
        eq2.setEquationInfix("eq2");
        eq2.setRootNode(new TreeNode());
        
        repository.save(eq1);
        repository.save(eq2);
        
        Collection<Equation> all = repository.findAll();
        assertEquals(2, all.size());
    }
    
    @Test
    @DisplayName("should check if equation exists")
    void testExistsById() {
        String id = repository.save(testEquation);
        assertTrue(repository.existsById(id));
        assertFalse(repository.existsById("nonexistent"));
    }
    
    @Test
    @DisplayName("should delete equation by ID")
    void testDeleteById() {
        String id = repository.save(testEquation);
        assertTrue(repository.deleteById(id));
        assertFalse(repository.existsById(id));
    }
    
    @Test
    @DisplayName("should return false when deleting non-existent equation")
    void testDeleteByIdNotFound() {
        boolean deleted = repository.deleteById("nonexistent");
        assertFalse(deleted);
    }
    
    @Test
    @DisplayName("should clear all equations")
    void testClear() {
        repository.save(testEquation);
        Equation eq2 = new Equation();
        eq2.setEquationInfix("eq2");
        eq2.setRootNode(new TreeNode());
        repository.save(eq2);
        
        assertEquals(2, repository.count());
        repository.clear();
        assertEquals(0, repository.count());
    }
    
    @Test
    @DisplayName("should count equations correctly")
    void testCount() {
        assertEquals(0, repository.count());
        
        repository.save(testEquation);
        assertEquals(1, repository.count());
        
        Equation eq2 = new Equation();
        eq2.setEquationInfix("eq2");
        eq2.setRootNode(new TreeNode());
        repository.save(eq2);
        assertEquals(2, repository.count());
    }
}
