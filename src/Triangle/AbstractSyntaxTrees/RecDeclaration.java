/*
 * Ericka 
 */

 package Triangle.AbstractSyntaxTrees;

 import Triangle.SyntacticAnalyzer.SourcePosition;

public class RecDeclaration extends Declaration{
    public RecDeclaration (Proc_Funcs pfsAST, SourcePosition thePosition) {
        super (thePosition);
        PFs = pfsAST;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitRecDeclaration(this, o);
    }

    public Proc_Funcs PFs;
}


