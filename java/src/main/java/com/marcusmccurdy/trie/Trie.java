package com.marcusmccurdy.trie;

import java.util.*;

/**
 *
 * @author Marcus McCurdy <marcus.mccurdy@gmail.com>
 */
public class Trie {

    protected final NavigableMap<Character, Trie> children;
    protected boolean terminal = false;

    public Trie() {
        children = new TreeMap<Character, Trie>();
    }

    protected void add(char c) {
        if (!children.containsKey(c)) {
            children.put(c, new Trie());
        }

    }

    public void insert(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Cannot add null to a Trie");
        }
        Trie node = this;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.add(c);
            }
            node = node.children.get(c);
        }
        node.terminal = true;
    }

    private void ac(Character currentChar, Trie node, StringBuilder currentString, Collection<String> results) {
        currentString.append(currentChar);
        if (node.terminal) {
            results.add(currentString.toString());
        }
        for (Map.Entry<Character, Trie> e : node.children.entrySet()) {
            ac(e.getKey(), e.getValue(), currentString, results);
        }
        currentString.deleteCharAt(currentString.length() - 1);
    }

    public Collection<String> autoComplete(String prefix) {
        final List<String> results = new ArrayList<String>();
        final StringBuilder sb = new StringBuilder(prefix);
        Trie node = this;
        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return Collections.emptyList();
            }
        }
        for (Map.Entry<Character, Trie> e : node.children.entrySet()) {
            ac(e.getKey(), e.getValue(), sb, results);
        }
        return results;
    }

}
