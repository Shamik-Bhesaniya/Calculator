/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;

public class MStack<E> {
    
    private LinkedList<E> s = new LinkedList<>();
    
    public void push(E e) {
        s.addLast(e);
    }
    public E pop() {
        return s.removeLast();
    }
    public E peek() {
        return s.getLast();
    }
    public int size() {
        return s.size();
    }
    public boolean isEmpty() {
        return s.isEmpty();
    }

}
