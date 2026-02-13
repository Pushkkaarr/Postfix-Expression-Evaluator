package com.pushkar.postfix_evaluator.repository;

import com.pushkar.postfix_evaluator.model.Equation;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory repository for storing and retrieving equations
 */
@Repository
public class EquationRepository {
    
    private final Map<String, Equation> equations = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);
    
    /**
     * Saves an equation and generates a unique ID
     * 
     * @param equation the equation to save
     * @return the generated equation ID
     */
    public String save(Equation equation) {
        String id = String.valueOf(idCounter.incrementAndGet());
        equation.setId(id);
        equations.put(id, equation);
        return id;
    }
    
    /**
     * Retrieves an equation by ID
     * 
     * @param id the equation ID
     * @return the equation or null if not found
     */
    public Equation findById(String id) {
        return equations.get(id);
    }
    
    /**
     * Gets all stored equations
     * 
     * @return collection of all equations
     */
    public Collection<Equation> findAll() {
        return equations.values();
    }
    
    /**
     * Checks if an equation with the given ID exists
     * 
     * @param id the equation ID
     * @return true if exists, false otherwise
     */
    public boolean existsById(String id) {
        return equations.containsKey(id);
    }
    
    /**
     * Deletes an equation by ID
     * 
     * @param id the equation ID
     * @return true if deleted, false if not found
     */
    public boolean deleteById(String id) {
        return equations.remove(id) != null;
    }
    
    /**
     * Clears all equations (useful for testing)
     */
    public void clear() {
        equations.clear();
        idCounter.set(0);
    }
    
    /**
     * Gets the count of stored equations
     * 
     * @return number of equations
     */
    public int count() {
        return equations.size();
    }
}
