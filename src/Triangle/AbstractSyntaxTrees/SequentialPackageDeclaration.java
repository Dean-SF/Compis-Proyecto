/*
 * Andrea 
 */

 package Triangle.AbstractSyntaxTrees;

 import Triangle.SyntacticAnalyzer.SourcePosition;
 
 
 public class SequentialPackageDeclaration extends Package {
 
    public SequentialPackageDeclaration (Package pAST1, Package pAST2, SourcePosition thePosition) {
       super (thePosition); 
       P1 = pAST1;
       P2 = pAST2;
    }
   
    public Object visit(Visitor v, Object o) {
        return v.visitSequentialPackageDeclaration(this, o);
    }

    public Package P2;
    public Package P1;
   }
 