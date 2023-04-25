/*
 * Andrea 
 */

 package Triangle.AbstractSyntaxTrees;

 import Triangle.SyntacticAnalyzer.SourcePosition;
 
 
 public class SequentialPackageDeclaration extends Declaration {
 
    public SequentialPackageDeclaration (Declaration dAST1, Declaration dAST2, SourcePosition thePosition) {
       super (thePosition); 
       D1 = dAST1;
       D2 = dAST2;
    }
   
    public Object visit(Visitor v, Object o) {
        return v.visitSequentialPackageDeclaration(this, o);
    }

    public Declaration D2;
    public Declaration D1;
   }
 