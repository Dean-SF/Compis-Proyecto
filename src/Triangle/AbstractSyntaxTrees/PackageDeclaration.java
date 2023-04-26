/*
 * Andrea 
 */

 package Triangle.AbstractSyntaxTrees;

 import Triangle.SyntacticAnalyzer.SourcePosition;
 
 
 public class PackageDeclaration extends Package {
 
    public PackageDeclaration (Identifier iAST, Declaration dAST, SourcePosition thePosition) {
       super (thePosition);
       I = iAST;
       D = dAST;
    }
   
    public Object visit(Visitor v, Object o) {
        return v.visitPackageDeclaration(this, o);
    }

    public Identifier I;
    public Declaration D;
   }
 