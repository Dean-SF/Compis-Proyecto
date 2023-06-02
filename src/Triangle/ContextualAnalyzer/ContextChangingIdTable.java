package Triangle.ContextualAnalyzer;

import java.util.HashMap;
import java.util.Stack;

import Triangle.AbstractSyntaxTrees.Declaration;


/*
 * Clase completamente nueva hecha por Deyan Sanabria
 */
public class ContextChangingIdTable {

    private IdEntry stdEnvironmentLatestEntry;
    private IdentificationTable programContext;
    private IdentificationTable currentContext;
    private Stack<IdentificationTable> contextStack;
    private HashMap<String,IdentificationTable> packagesContext;

    public ContextChangingIdTable() {
        programContext = new IdentificationTable();
        currentContext = programContext;
        packagesContext = new HashMap<String,IdentificationTable>();
        contextStack = new Stack<IdentificationTable>();
        stdEnvironmentLatestEntry = null;
    }
    
    public void openScope () {
        currentContext.openScope();
    }

    public void closeScope () {
        currentContext.closeScope();
    }
    
    public void privCloseScope () {
        currentContext.privCloseScope();
    }
    
    public void enter (String id, Declaration attr) {
        currentContext.enter(id, attr);
    }
    
    public Declaration retrieve (String id) {
        if(currentContext == null)
            return null;
        return currentContext.retrieve(id);
    }

    public void restoreContext() {
        currentContext = contextStack.pop();
    }

    public boolean createContext(String id) {
        if(packagesContext.containsKey(id)) {
            return false;
        }
        packagesContext.put(id, new IdentificationTable(stdEnvironmentLatestEntry));
        return true;
    }

    public boolean enterContext(String id) {
        contextStack.push(currentContext);
        currentContext = packagesContext.get(id);
        return (currentContext == null ? false : true);
    }

    public void saveStdEnvironmentLatestEntry() {
        stdEnvironmentLatestEntry = programContext.latest;
    }
}

