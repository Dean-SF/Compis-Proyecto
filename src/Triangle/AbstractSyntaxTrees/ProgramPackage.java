/*
 * Andrea
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class ProgramPackage extends Program {

  public ProgramPackage (Declaration dAST, Command cAST, SourcePosition thePosition) {
    super (cAST, thePosition);
    
    D = dAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitProgramPackage(this, o);
  }

  public Declaration D;
}